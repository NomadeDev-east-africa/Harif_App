package com.example.hariif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.FirebaseAppHelper;

import java.util.ArrayList;
import java.util.List;

public class MyRideActivity extends AppCompatActivity {
RecyclerView recyclerView;
AdapterRider adapterRider;
List<RideInfo> rideInfos;
List<Rideclass> arrayListride;
AdapterRIDE22 adapterFUI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ride);

    recyclerView =findViewById(R.id.recycleview);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);

    rideInfos = new ArrayList<>();
 String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference reference =  FirebaseDatabase.getInstance()
                .getReference()
                .child("Historique").child("Client")
                .child(user);

    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot  ds : dataSnapshot.getChildren() ){

                        RideInfo rideInfo22 =
                        new RideInfo(ds.child("driverId").getValue().toString(),
                        ds.child("dateofride").getValue().toString(),
                        ds.child("pricefinale").getValue().toString(),
                        ds.child("destinationname").getValue().toString());

rideInfos.add(rideInfo22);

           }
            adapterRider= new AdapterRider(rideInfos,MyRideActivity.this);
            recyclerView.setAdapter(adapterRider);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });



        recyclerView.setAdapter(adapterFUI);

    }
}