package com.example.myapplication;

import android.Manifest;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class orienteering extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener {
    private ImageButton btnHome, btnDevPass;
    private Button btnNext;
    private TextView textSpotName, textSpotDesc;
    private ImageView orienCard;
    private LocationManager locationManager;
    private static final String TAG = "Orienteering";

    private GoogleMap mMap;
    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;

    private float GEOFENCE_RADIUS = 200;
    private String GEOFENCE_ID = "SOME_GEOFENCE_ID";

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;
    private String[] orienSpots;
    public Marker spotMarker0, pos;
    public boolean first = true;
    String bestProv;
    private int btnDevPassCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orienteering);
        GlobalVariable orienLevel = (GlobalVariable)getApplicationContext();
        btnHome = (ImageButton) findViewById(R.id.imageButton12);
        btnDevPass = (ImageButton) findViewById(R.id.imageButton_devPassHidden);
        btnNext = (Button) findViewById(R.id.button_OrienNext);
        textSpotName=(TextView)findViewById(R.id.textView_OrienTitle);
        textSpotDesc=(TextView)findViewById(R.id.textView_OrienDescribe);
        orienCard=(ImageView)findViewById(R.id.imageView_orienCard);

        btnHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orienLevel.getLevel() == 7) {
                    orienLevel.setLevel(0);
                } else {
                    orienLevel.setLevel(orienLevel.getLevel() + 1);
                }
                pageRefresh();
            }
        });

        btnDevPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDevPassCount++;
                if (btnDevPassCount == 5) {
                    achieveSpot();
                }
            }
        });

        newLevel(orienLevel.getLevel());

        Handler handler = new Handler();
        final Runnable devBtnCL = new Runnable() {
            public void run() {
                btnDevPassCount = 0;
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(devBtnCL, 1000);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapOrien);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeofenceHelper(this);

    }

    public void pageRefresh() {
        //Intent intent = new Intent(this, orienteering.class);
        //startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void newLevel(int level) {
        btnNext.setText(getString(R.string.orienBtn_Default));
        btnNext.setEnabled(false);
        switch (level) {
            case 0:
                orienSpots = getResources().getStringArray(R.array.orienteering_0);
                orienCard.setImageResource(R.drawable.oriencard_00);
                break;
            case 1:
                orienSpots = getResources().getStringArray(R.array.orienteering_1);
                orienCard.setImageResource(R.drawable.oriencard_01);
                break;
            case 2:
                orienSpots = getResources().getStringArray(R.array.orienteering_2);
                orienCard.setImageResource(R.drawable.oriencard_02);
                break;
            case 3:
                orienSpots = getResources().getStringArray(R.array.orienteering_3);
                orienCard.setImageResource(R.drawable.oriencard_03);
                break;
            case 4:
                orienSpots = getResources().getStringArray(R.array.orienteering_4);
                orienCard.setImageResource(R.drawable.oriencard_04);
                break;
            case 5:
                orienSpots = getResources().getStringArray(R.array.orienteering_5);
                orienCard.setImageResource(R.drawable.oriencard_05);
                break;
            case 6:
                orienSpots = getResources().getStringArray(R.array.orienteering_6);
                orienCard.setImageResource(R.drawable.oriencard_06);
                btnNext.setText(getString(R.string.orienBtn_FinishPre));
                break;
            case 7:
                orienSpots = getResources().getStringArray(R.array.orienteering_7);
                orienCard.setImageResource(R.drawable.oriencard_07);
                btnNext.setText(getString(R.string.orienBtn_Finish));
                btnNext.setEnabled(true);
                break;
        }
        String spotTitle = orienSpots[0] + " " + orienSpots[1];
        String spotDescr = orienSpots[4];
        textSpotName.setText(spotTitle);
        textSpotDesc.setText(spotDescr);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng spotLatLng = new LatLng(Float.parseFloat(orienSpots[2]), Float.parseFloat(orienSpots[3]));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spotLatLng, 17));
        enableUserLocation();
        mMap.setOnMapLongClickListener(this);
        addGeofence(spotLatLng, 35);

        LatLng tempLatLng;
        String[] tempSpots = getResources().getStringArray(R.array.orienteering_0);

        GlobalVariable orienLevel = (GlobalVariable)getApplicationContext();
        switch(orienLevel.getLevel()){
            case(0):
                tempSpots = getResources().getStringArray(R.array.orienteering_0);  break;
            case(1):
                tempSpots = getResources().getStringArray(R.array.orienteering_1);  break;
            case(2):
                tempSpots = getResources().getStringArray(R.array.orienteering_2);  break;
            case(3):
                tempSpots = getResources().getStringArray(R.array.orienteering_3);  break;
            case(4):
                tempSpots = getResources().getStringArray(R.array.orienteering_4);  break;
            case(5):
                tempSpots = getResources().getStringArray(R.array.orienteering_5);  break;
            case(6):
                tempSpots = getResources().getStringArray(R.array.orienteering_6);  break;
            case(7):
                tempSpots = getResources().getStringArray(R.array.orienteering_7);  break;
        }
        tempLatLng = new LatLng(Float.parseFloat(tempSpots[2]), Float.parseFloat(tempSpots[3]));
        spotMarker0 = mMap.addMarker(new MarkerOptions().position(tempLatLng).title(tempSpots[1]).snippet(tempSpots[0]));
        spotMarker0.setTag(0);
        addCircle(tempLatLng, 35);

        //addCircle(spotLatLng, 80);
        //spotMarker=mMap.addMarker(new MarkerOptions().position(spotLatLng).title(orienSpots[1]).snippet(orienSpots[0]));
        //spotMarker.setTag(0);
    }

    public void achieveSpot() {
        btnNext.setEnabled(true);
        btnNext.setText(getString(R.string.orienBtn_Pass));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (first) {
            first = false;
        } else {
            pos.remove();
        }

        LatLng Point = new LatLng(location.getLatitude(), location.getLongitude());
        pos=mMap.addMarker(new MarkerOptions().title("現在位置").position(Point).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        pos.setTag(100);

        float[] results = new float[1];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), Float.parseFloat(orienSpots[2]), Float.parseFloat(orienSpots[3]), results);
        if (results[0] <= 35){
            achieveSpot();
        }
        textSpotName.setText(String.valueOf(results[0]));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Criteria criteria = new Criteria();
        bestProv = locationManager.getBestProvider(criteria, true);
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                //We do not have the permission..

            }
        }

        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Toast.makeText(this, "You can add geofences...", Toast.LENGTH_SHORT).show();
            } else {
                //We do not have the permission..
                Toast.makeText(this, "Background location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (Build.VERSION.SDK_INT >= 29) {
            //We need background permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                handleMapLongClick(latLng);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    //We show a dialog and ask for permission
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                }
            }

        } else {
            handleMapLongClick(latLng);
        }

    }

    private void handleMapLongClick(LatLng latLng) {
        mMap.clear();
        addMarker(latLng);
        addCircle(latLng, GEOFENCE_RADIUS);
        addGeofence(latLng, GEOFENCE_RADIUS);
    }


    private void addGeofence(LatLng latLng, float radius) {

        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Geofence Added...");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geofenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure: " + errorMessage);
                    }
                });
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }

    private void addCircle(LatLng latLng, float radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255, 255, 0,0));
        circleOptions.fillColor(Color.argb(64, 255, 0,0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }
}