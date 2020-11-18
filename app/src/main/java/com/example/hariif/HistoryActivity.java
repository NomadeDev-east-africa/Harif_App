package com.example.hariif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HistoryActivity extends AppCompatActivity {
    private FirebaseRecyclerAdapter adapter;

    RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView =findViewById(R.id.recycleview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

  
        fetch();
    }

    private void fetch() {


        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Historique").child("Client")
                .child(user);

        FirebaseRecyclerOptions<RideInfo> options =
                new FirebaseRecyclerOptions.Builder<RideInfo>()
                        .setQuery(query, new SnapshotParser<RideInfo>() {
                            @NonNull
                            @Override
                            public RideInfo parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new RideInfo(snapshot.child("driverId").getValue().toString(),
                                        snapshot.child("dateofride").getValue().toString(),
                                        snapshot.child("destinationname").getValue().toString(),snapshot.child("pricefinal").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<RideInfo, ViewHolder>(options) {
            @NonNull

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull RideInfo rideInfo) {

                viewHolder.depart.setText("Non specifié");
                viewHolder.destination.setText(rideInfo.getDestinationname());
                viewHolder.date.setText(rideInfo.getDateofride());
                viewHolder.price.setText(rideInfo.getPricefinale());

            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.items_list_rides, parent, false);

                return new ViewHolder(view);
            }




        };
        recyclerView.setAdapter(adapter);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView txtTitle;
        public TextView txtDesc;
        TextView depart,destination,price,date;

        public ViewHolder(View itemView) {
            super(itemView);

            price=itemView.findViewById(R.id.pricerecycle);
            destination=itemView.findViewById(R.id.destinationerecycle);
            date =itemView.findViewById(R.id.dateandtime);
            depart=itemView.findViewById(R.id.Depart);

        }

        public void setTxtTitle(String string) {
            txtTitle.setText(string);
        }


        public void setTxtDesc(String string) {
            txtDesc.setText(string);

        }
    }
}