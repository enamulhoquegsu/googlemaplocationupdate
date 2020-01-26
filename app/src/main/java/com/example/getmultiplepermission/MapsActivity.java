package com.example.getmultiplepermission;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity" ;
    private GoogleMap mMap;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        fusedLocationClient = new FusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setFastestInterval(2000);
        locationRequest.setInterval(10000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    Log.d(TAG, "onLocationResult: " + location.getLatitude() + "" + location.getLongitude());

                    if (mMap != null){

                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                        mMap.addMarker(createMarkerOptions(latLng));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    }



                }
            };
        };

        //startLocationUpdates();
        
    } // End of onCreate method


   
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(createMarkerOptions(sydney));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public MarkerOptions createMarkerOptions (LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("my current location");
        markerOptions.snippet("some other information");
        return markerOptions.position(latLng);
    }


    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback );
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }
}
