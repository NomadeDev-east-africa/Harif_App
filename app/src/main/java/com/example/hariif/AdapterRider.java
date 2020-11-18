package com.example.hariif;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterRider extends RecyclerView.Adapter<AdapterRider.MyviewHolder> {
    List<RideInfo> rideclasses;

    public AdapterRider(List<RideInfo> rideclasses, Context context) {
        this.rideclasses = rideclasses;
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.items_list_rides,parent,false);


        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.depart.setText("Non specifié");
        holder.destination.setText(rideclasses.get(position).getDestinationname());
        holder.date.setText(rideclasses.get(position).getDateofride());
        holder.price.setText(rideclasses.get(position).getPricefinale() + "Fdj");

    }

    @Override
    public int getItemCount() {
        return rideclasses.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView depart,destination,price,date;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
             price=itemView.findViewById(R.id.pricerecycle);
            destination=itemView.findViewById(R.id.destinationerecycle);
            date =itemView.findViewById(R.id.dateandtime);
            depart=itemView.findViewById(R.id.Depart);


        }
    }
}
