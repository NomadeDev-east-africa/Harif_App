package com.example.hariif;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;

import java.util.List;
import java.util.Map;

public class FirstStepActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, NavigationView.OnNavigationItemSelectedListener {
 MapView mapView;
 Button selectLocationButton;
 MapboxMap mapboxMapP;
    private TextView selectedLocationTextView;
    private static final int REQUEST_CODE = 56789;
    LocationComponent locationComponent;
    TextView username;
    PermissionsManager permissionsManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.acces_token));

        setContentView(R.layout.activity_first_step);


// Initialize the mapboxMap view
        mapView = findViewById(R.id.mapViewStart);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        selectLocationButton=findViewById(R.id.select_location_button);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigation_view);

        View headerView = navigationView.getHeaderView(0);
      username=headerView.findViewById(R.id.UserNamedrw);
         navigationdrawer();
        displaynameuser();
    }

    private void displaynameuser() {
        DatabaseReference Servicereference = FirebaseDatabase.getInstance().getReference().child("User_application").child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Servicereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String Service1 = dataSnapshot.child("nameclient").getValue().toString();


                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                if (map.get("nameclient") != null) {
                    String name = map.get("nameclient").toString();
                    username.setText(name);
                } else
                {
                    username.setText("Null Bro");
                }
    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void navigationdrawer() {



            navigationView.bringToFront();
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(R.id.Home22);


            if(drawerLayout.isDrawerVisible(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else drawerLayout.openDrawer(GravityCompat.START);


        }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
FirstStepActivity.this.mapboxMapP= mapboxMap;

        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull final Style style) {
                enableLocationPlugin(style);


                selectLocationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        goToPickerActivity();



                    }




                });

            }
        });
    }

    private void goToPickerActivity() {
        Intent coco = new PlacePicker.IntentBuilder()
                .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.acces_token))
                .placeOptions(PlacePickerOptions.builder()
                        .statingCameraPosition(new CameraPosition.Builder()

                                .target(new LatLng(locationComponent.getLastKnownLocation().getLatitude(),locationComponent.getLastKnownLocation().getLongitude())).zoom(16).build())

                        .build())
                .build(FirstStepActivity.this);
        startActivityForResult(coco, REQUEST_CODE);




    }

    private void enableLocationPlugin(Style style) {
// Check if permissions are enabled and if not request


        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            locationComponent = mapboxMapP.getLocationComponent();

// Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, style).build());



// Enable to make component visible
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }


    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMapP.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationPlugin(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
// Retrieve the information from the selected location's CarmenFeature
            CarmenFeature carmenFeature = PlacePicker.getPlace(data);

// Set the TextView text to the entire CarmenFeature. The CarmenFeature
// also be parsed through to grab and display certain information such as
// its placeName, text, or coordinates.
            if (carmenFeature != null) {
     selectLocationButton.setText("Continue");
     selectLocationButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Intent kok =  new Intent(FirstStepActivity.this,MainActivity.class);

             kok.putExtra("name",carmenFeature.placeName());

             startActivity(kok);

         }
     });

            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Home22:

                break;


            case R.id.Settings:
                Intent intent = new Intent(FirstStepActivity.this,SettingsActivity.class);
                startActivity(intent);

                finish();
                break;
            case R.id.MyRyde:
                Intent intent2 = new Intent(FirstStepActivity.this, MyRideActivity.class);
                startActivity(intent2);
                break;
            case R.id.Info:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START); return true;

    }
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
}



