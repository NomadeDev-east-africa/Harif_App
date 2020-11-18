package com.example.hariif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

TextView finalprice,finalnameriderm,paidbtn,dateofride;
ImageView finalviewrider;
RatingBar ratingBar;
String DriverID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    finalnameriderm=findViewById(R.id.Driverfinalname);
    finalprice=findViewById(R.id.Pricefinale);
    paidbtn=findViewById(R.id.PaidRider);
    dateofride=findViewById(R.id.dateofride);
    finalviewrider=findViewById(R.id.Driverfinalview);
     ratingBar=findViewById(R.id.ratingBar1);
        Intent intent = getIntent();


        DriverID = intent.getStringExtra("keydriver");
        final String pricefinal = intent.getStringExtra("pricefinal");
        final String Destinationname = intent.getStringExtra("Destinationame");
        finalprice.setText(pricefinal);
          getdriverdate(DriverID);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        DatabaseReference rating22 = FirebaseDatabase.getInstance().getReference().child("rating");


        String key = rating22.getKey();
        rating22.child("rating").child(DriverID).child(key).setValue(rating);


    }
});
        final String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

        final Date currentTime = Calendar.getInstance().getTime();
 dateofride.setText(currentDateTimeString);

        paidbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Historique").child("Client").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

       RideInfo rideInfo = new RideInfo(DriverID,currentDateTimeString,pricefinal,Destinationname);
          reference.push().setValue(rideInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()) {
                      Toast.makeText(PaymentActivity.this, "Working", Toast.LENGTH_SHORT).show();
                  } else {
                      Toast.makeText(PaymentActivity.this, "Not working", Toast.LENGTH_SHORT).show();
                  }
              }
          });
    }
});


    }

    private void getdriverdate(String driverID) {


        DatabaseReference costu = FirebaseDatabase.getInstance().getReference().child("User_application").child("DriverApp").child(driverID);
costu.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 ) {


    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
    if (map.get("name") != null) {
        String name = map.get("name").toString();
        finalnameriderm.setText(name);
    }

    if (map.get("imageurl") != null) {
        String image = map.get("imageurl").toString();
        Picasso.get().load(image).into(finalviewrider);
    }


}

        }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
    }
}