package com.example.getmultiplepermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnSetting;

    private static final int MAP_PERMISSION = 100;

    private boolean locationSetting ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSetting = findViewById(R.id.btn_setting);
        btnSetting.setVisibility(View.INVISIBLE);

        if (locationSetting == false){
            requestPermissionForApp();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case MAP_PERMISSION :
                if (grantResults.length>0 && grantResults[0] +  grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    locationSetting = true;
                    Toast.makeText(this, "Permission is given", Toast.LENGTH_SHORT).show();
                    btnSetting.setVisibility(View.INVISIBLE);
                    Intent nextActivity = new Intent(MainActivity.this, LocationDataActivity.class);
                    startActivity(nextActivity);

                }
                else{

                    locationSetting = false;
                    btnSetting.setVisibility(View.VISIBLE);
                    btnSetting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS); // I can use it as constructor or setAction method . Both do the same things...
                            //intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });

                }


                break;
        }
    }

    public void requestPermissionForApp (){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission .ACCESS_COARSE_LOCATION +
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))== PackageManager.PERMISSION_DENIED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION )
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Permission Request")
                        .setMessage("This permission is required ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MAP_PERMISSION);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MAP_PERMISSION);
            }

        }else{
            locationSetting = true;
            Toast.makeText(this, "Permissions are already given..", Toast.LENGTH_SHORT).show();
            btnSetting.setVisibility(View.INVISIBLE);
        }
    }
}
