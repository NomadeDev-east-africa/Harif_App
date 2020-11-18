package com.example.hariif;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterRIDE22 extends FirebaseRecyclerAdapter<RideInfo,AdapterRIDE22.myviewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterRIDE22(@NonNull FirebaseRecyclerOptions<RideInfo> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder myviewHolder, int i, @NonNull RideInfo rideInfo) {

        myviewHolder.depart.setText("Non specifié");
        myviewHolder.destination.setText(rideInfo.getDestinationname());
        myviewHolder.date.setText(rideInfo.getDateofride());
        myviewHolder.price.setText(rideInfo.getPricefinale());
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_rides, parent, false);


        return new myviewHolder(itemView);
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        TextView depart,destination,price,date;


        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            price=itemView.findViewById(R.id.pricerecycle);
            destination=itemView.findViewById(R.id.destinationerecycle);
            date =itemView.findViewById(R.id.dateandtime);
            depart=itemView.findViewById(R.id.Depart);

        }
    }
}
