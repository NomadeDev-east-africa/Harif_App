package com.example.hariif;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.turf.TurfMeasurement;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.turf.TurfConstants.UNIT_KILOMETERS;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener, View.OnClickListener  {
    RadioGroup radioGroup;
    Point destinationPoint;
    private MapboxDirections client;
    Style style2;
    ImageView driverimage;
    private static final String DISTANCE_UNITS = UNIT_KILOMETERS; // DISTANCE_UNITS must be equal to a
    LinearLayout Infodriver, ServiceDriver;
    String Destinationame;
    TextView Drivername, DriverNumber, PlatesDriver;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    LocationComponent locationComponent;
    Point DestinationPoint, originPoint;
    private NavigationMapRoute navigationMapRoute;
    private DirectionsRoute currentRoute;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    String destinationname;
    TextView CALLUBER;
    LinearLayout ll1, ll2, ll3;
    String TAG = "";
    LinearLayout serviceinfo,dashboardinfo;
    TextView PriceCab1,PriceCab2,PriceCab3;
    static final float END_SCALE = 0.7f;
    CardView viewcolor;
    LinearLayout statusInfo;
    TextView infotextStatus, Okbutton;
     String TypeCar;
     ImageView btncall;
 String finalPrice;
    Double DistanceBetweenPoint;

    private static final int REQUEST_CODE = 56789;
  TextView username;
  String Pricefinalecab1,Pricefinalecab2,Pricefinalcab3;
  EditText pickup,destinationchoose;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Mapbox.getInstance(this, getString(R.string.acces_token));
         setContentView(R.layout.activity_main);
        CALLUBER = findViewById(R.id.CallUber);
        pickup=findViewById(R.id.edtpickup);
        destinationchoose=findViewById(R.id.edtdestination);
         ServiceDriver = findViewById(R.id.ServiceInfo);
         viewcolor = findViewById(R.id.infocardviewcolor);
        statusInfo = findViewById(R.id.Statutsinfo);
        infotextStatus = findViewById(R.id.Textinfostatu);
        Okbutton = findViewById(R.id.Okaybutton);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        dashboardinfo=findViewById(R.id.infodashboard);
        serviceinfo=findViewById(R.id.ServiceInfo);
 PriceCab1=findViewById(R.id.priceCab1);
         PriceCab2=findViewById(R.id.priceCab2);
         PriceCab3=findViewById(R.id.priceCab3);
       btncall = findViewById(R.id.CallButton);
         Infodriver = findViewById(R.id.Infodriver);

        Drivername = findViewById(R.id.Drivername);


         Intent intent = getIntent();


         String  name = intent.getStringExtra("name");
         if (name != null){
             pickup.setText(name);
         }


        driverimage = findViewById(R.id.profileDriver);
        PlatesDriver = findViewById(R.id.platesDrivers);

        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);
        ll3 = findViewById(R.id.ll3);

         DestiEdt();


        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);

     }

    private void DestiEdt() {
         destinationchoose.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent coco = new PlacePicker.IntentBuilder()
                         .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.acces_token))
                         .placeOptions(PlacePickerOptions.builder()
                                 .statingCameraPosition(new CameraPosition.Builder()

                                         .target(new LatLng(locationComponent.getLastKnownLocation().getLatitude(),locationComponent.getLastKnownLocation().getLongitude())).zoom(16).build())

                                 .build())
                         .build(MainActivity.this);
                 startActivityForResult(coco, REQUEST_CODE);
             }
         });


     }


    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {


        MainActivity.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjerxnqt3cgvp2rmyuxbeqme7"),
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);

                        mapboxMap.addOnMapClickListener(MainActivity.this);
                        addDestinationIcon(style);

                        addicon(style);
                     getstart();

                    }
                });


    }

    private void startRIDE() {

     }

    private void getstart() {

        CALLUBER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DestinationPoint != null && TypeCar != null) {

                    Toast.makeText(MainActivity.this, TypeCar, Toast.LENGTH_SHORT).show();

                    mapboxMap.removeOnMapClickListener(MainActivity.this);

                    senddata();
                } else{
                    Toast.makeText(MainActivity.this, "Ride Info not complete", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void addicon(Style style) {

        style.addImage("destination-icon-id2",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.map_default_map_marker));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id2");
        style.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer2 = new SymbolLayer("destination-symbol-layer-id2", "destination-source-id2");
        destinationSymbolLayer2.withProperties(
                iconImage("destination-icon-id2"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        style.addLayer(destinationSymbolLayer2);
    }


    private int radius = 1;
    private Boolean driverfound = false;
    String DriverId;

    private void GetcloserDriver() {


        DatabaseReference geoDriver = FirebaseDatabase.getInstance().getReference().child("DriverAvailable");
        GeoFire geoFire = new GeoFire(geoDriver);
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude()), radius);
        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(final String key, GeoLocation location) {


                if (!driverfound) {

                    DatabaseReference Servicereference = FirebaseDatabase.getInstance().getReference().child("User_application").child("DriverApp").child(key);
                    Servicereference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {



                                    String Service1 = dataSnapshot.child("service").getValue().toString();

                                    String service2 = TypeCar;

                                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                                if (map.get("service") != null) {
                                    String  SERVICEINFO= map.get("service").toString();

                                }
                                    if (Service1.equals(TypeCar)) {


                                        Double goLatitude = DestinationPoint.latitude();
                                        Double golongitude = DestinationPoint.longitude();
                                        DriverId = dataSnapshot.getKey();
                                        driverfound = true;
                                        String Service = map.get("service").toString();
                                        DatabaseReference driverreference = FirebaseDatabase.getInstance().getReference().child("RideOrder").child("Driver").child(DriverId);
                                        String client = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        HashMap hashMap = new HashMap();
                                        hashMap.put("CustomerRideID", client);
                                        hashMap.put("Destination", destinationname);
                                        hashMap.put("DestinationLatitude", goLatitude);
                                        hashMap.put("DestinationLongitude", golongitude);
                                        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        driverreference.updateChildren(hashMap);
                                        GeoFire geoFire = new GeoFire(driverreference);
                                        geoFire.setLocation(user, new GeoLocation(DestinationPoint.longitude(), DestinationPoint.latitude()), new GeoFire.CompletionListener() {
                                            @Override
                                            public void onComplete(String key, DatabaseError error) {


                                            }
                                        });
                                        CALLUBER.setText("Search Driver ... ");

                                        getDriverlocation();
                                        getDriverInfo();
                                        getStatusRide();

                                        checkifDriverisHere();
                                    }
                                }

                            }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });


                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

                if (!driverfound) {
                    radius = radius + 1;
                    GetcloserDriver();
                }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }

    private void getStatusRide() {
        String userId = FirebaseAuth.getInstance().getUid();
        final DatabaseReference SendInfo = FirebaseDatabase.getInstance().getReference().child("SendInfo").child(userId);

        SendInfo.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {


                    statusInfo.setVisibility(View.VISIBLE);

                    ServiceDriver.setVisibility(View.INVISIBLE);
                    Infodriver.setVisibility(View.INVISIBLE);
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("Message") != null) {
                        String name = map.get("Message").toString();

                        statusInfo.setBackgroundColor(R.color.colorFeu);

                        infotextStatus.setText(name);

                        Okbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SendInfo.removeValue();
                                finish();
                                startActivity(getIntent());

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getDriverInfo() {

        DatabaseReference costu = FirebaseDatabase.getInstance().getReference().child("User_application").child("DriverApp").child(DriverId);
        costu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                ServiceDriver.setVisibility(View.INVISIBLE);
                Infodriver.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {


                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null) {
                        String name = map.get("name").toString();
                        Drivername.setText(name);
                    }


                    if (map.get("number") != null) {
                        final String number = map.get("number").toString();
                        btncall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent call = new Intent(Intent.ACTION_CALL);


                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
                                startActivity(intent);
                            }
                        });

                    }

                    if (map.get("rating") != null) {
                        String rating = map.get("rating").toString();

                        //ratingBarDebut.setRating(Integer.valueOf(rating));
                    }


                    if (map.get("imageurl") != null) {
                        String image = map.get("imageurl").toString();
                        Picasso.get().load(image).into(driverimage);
                    }


                    if (map.get("platesnumber") != null) {
                        String kkk = map.get("platesnumber").toString();

                        PlatesDriver.setText(kkk);
                    }

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkifDriverisHere() {
        DatabaseReference SendInfo = FirebaseDatabase.getInstance().getReference().child("SendInfo").child("DriverArrived").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        SendInfo.addValueEventListener(new ValueEventListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {


                    statusInfo.setVisibility(View.VISIBLE);

                    ServiceDriver.setVisibility(View.INVISIBLE);
                    Infodriver.setVisibility(View.INVISIBLE);
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("Message") != null) {
                        String name = map.get("Message").toString();

                        statusInfo.setBackgroundColor(R.color.colorAccent);

                        infotextStatus.setText(name);

                        Okbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                statusInfo.setVisibility(View.INVISIBLE);
                Intent lol = new Intent(MainActivity.this,PaymentActivity.class);
                lol.putExtra("keydriver",DriverId);
                lol.putExtra("pricefinal",finalPrice);
                                lol.putExtra("Destinationame",destinationname);


                                startActivity(lol);
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDriverlocation() {


        DatabaseReference driverlocation = FirebaseDatabase.getInstance().getReference().child("DriverAvailable").child(DriverId).child("l");

        driverlocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    List<Object> list = (List<Object>) dataSnapshot.getValue();
                    double lont = 0;
                    double lati = 0;
                    CALLUBER.setText("DRIVER FOUND");
                    if (list.get(0) != null) {
                        lati = Double.parseDouble(String.valueOf(list.get(0)));
                    }

                    if (list.get(1) != null) {
                        lont = Double.parseDouble(String.valueOf(list.get(1)));
                    }


                    LatLng driverLalong = new LatLng(lati, lont);

                    Location location1 = new Location("");

                    location1.setLatitude(locationComponent.getLastKnownLocation().getLatitude());
                    location1.setLongitude(locationComponent.getLastKnownLocation().getLongitude());


                    Location location2 = new Location("");
                    location2.setLatitude(driverLalong.getLatitude());
                    location2.setLongitude(driverLalong.getLongitude());

                    float distance = location1.distanceTo(location2);
                    final Point user = Point.fromLngLat(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude());
                    Point driv = Point.fromLngLat(lati, lont);

                    double totalLineDistance = 0;
                    double distanceBetweenLastAndSecondToLastClickPoint = 0;
                    distanceBetweenLastAndSecondToLastClickPoint = TurfMeasurement.distance(user, driv, UNIT_KILOMETERS);
                    totalLineDistance += distanceBetweenLastAndSecondToLastClickPoint;

                    String yespapa = String.valueOf(totalLineDistance);


                    if (totalLineDistance < 1000) {

                    } else {

                    }


                    Point driver2 = Point.fromLngLat(lati, lont);
                    GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id2");
                    if (source != null) {
                        source.setGeoJson(Feature.fromGeometry(driver2));
                    }
                    // create symbol manager

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void addDestinationIcon(Style style) {

        style.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        style.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        style.addLayer(destinationSymbolLayer);
    }



    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

// Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());



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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }


    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
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
               destinationchoose.setText(carmenFeature.placeName());
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
            destinationname = PlaceAutocomplete.getPlace(data).placeName();
            destinationchoose.setText(destinationname);
            // Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
// Then retrieve and update the source designated for showing a selected location's symbol layer icon
            Toast.makeText(this, destinationname, Toast.LENGTH_SHORT).show();
            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())}));
                 destinationname = PlaceAutocomplete.getPlace(data).placeName();
                      DatabaseReference driverreference = FirebaseDatabase.getInstance().getReference().child("RideOrder").child("Driver").child(DriverId);
                        String client = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        HashMap hashMap = new HashMap();
                        hashMap.put("Destination", destinationname);

                        driverreference.updateChildren(hashMap);
                    }



                    // Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);

                }

            }

        }
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        DestinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(DestinationPoint));

        }
DistanceBetweenPoint = TurfMeasurement.distance(
        originPoint, DestinationPoint);
        getRoute(originPoint, DestinationPoint);

     double Basefare = 300;
      double priceforkmEco = 100;
        double priceforkmLux= 200;
        double priceforkmFam = 170;

        if (DistanceBetweenPoint != null) {

            double priceforCab1 = Basefare + (DistanceBetweenPoint * priceforkmEco);
            Pricefinalecab1=new DecimalFormat("##").format(priceforCab1);

            PriceCab1.setText(
                    "Price : " +  new DecimalFormat("##").format(priceforCab1) +" Fdj ");

            double priceforCab2 = Basefare + (DistanceBetweenPoint * priceforkmLux);
            Pricefinalecab2=new DecimalFormat("##").format(priceforCab2);
            PriceCab2.setText(
                    "Price : " + new DecimalFormat("##").format(priceforCab2) +" Fdj ");

            double priceforCab3 = Basefare + (DistanceBetweenPoint * priceforkmFam);
            Pricefinalcab3=new DecimalFormat("##").format(priceforCab3);

            PriceCab3.setText(
                    "Price : " + new DecimalFormat("##").format(priceforCab3) +" Fdj ");


        }

        return true;
    }

    private void senddata() {

        final DatabaseReference desti = FirebaseDatabase.getInstance().getReference().child("RequestPickup");
        GeoFire del = new GeoFire(desti);
        String key = desti.getKey();
        String User = FirebaseAuth.getInstance().getCurrentUser().getUid();
        del.setLocation(User, new GeoLocation(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {

                Toast.makeText(MainActivity.this, "Bravo", Toast.LENGTH_SHORT).show();
                GetcloserDriver();


            }
        });
    }
    private void getRoute(Point originPoint, Point destinationPoint) {


        NavigationRoute.builder(MainActivity.this)
                .accessToken(Mapbox.getAccessToken())
                .origin(originPoint)
                .destination(destinationPoint)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

// Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.AppTheme);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ll1:
  TypeCar = "Economy";
                ll1.setBackgroundResource(R.drawable.green_border);
                ll2.setBackgroundResource(R.drawable.white_gray_border);
                ll3.setBackgroundResource(R.drawable.white_gray_border);
 finalPrice = Pricefinalecab1;
                break;


            case R.id.ll2:
TypeCar = "Luxury";
                finalPrice = Pricefinalecab2;

                ll1.setBackgroundResource(R.drawable.white_gray_border);
                ll2.setBackgroundResource(R.drawable.green_border);
                ll3.setBackgroundResource(R.drawable.white_gray_border);
                break;

            case R.id.ll3:
       TypeCar = "Family";
                finalPrice = Pricefinalcab3;

                ll1.setBackgroundResource(R.drawable.white_gray_border);
                ll2.setBackgroundResource(R.drawable.white_gray_border);
                ll3.setBackgroundResource(R.drawable.green_border);
                break;
        }
    }





}



