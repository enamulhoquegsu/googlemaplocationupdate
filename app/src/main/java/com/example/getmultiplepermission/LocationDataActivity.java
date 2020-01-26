package com.example.getmultiplepermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationDataActivity extends AppCompatActivity {

    private static final String TAG = "LocationDataActivity" ;
    private Button btnUpdate, btnStop, btnMapActivity;
    private TextView tv_latitude, tv_longitude;
    // google
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationResult currentLocation;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_data);

        btnUpdate = findViewById(R.id.btn_update_location);
        btnStop = findViewById(R.id.btn_stop_location);
        btnMapActivity = findViewById(R.id.btn_go_maps);
        tv_latitude = findViewById(R.id.tv_latitude_value_location);
        tv_longitude = findViewById(R.id.tv_longitude_value_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            String latitude = String.valueOf(location.getLatitude());
                            String longitude = String.valueOf(location.getLongitude());

                            tv_latitude.setText(latitude);
                            tv_longitude.setText(longitude);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });


            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_longitude.setText("");
                tv_latitude.setText("");
            }
        });

        btnMapActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationDataActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}
