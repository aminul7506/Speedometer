package com.example.user.speedometer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by User on 4/18/2016.
 */
public class Velocity extends AppCompatActivity implements LocationListener {
    TextView speed;
    Button refresh;
    LocationManager locationManager;
    Location location;
    String provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.velocity);
        speed = (TextView) findViewById(R.id.speed);
        refresh = (Button) findViewById(R.id.refresh);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
            double s = (location.getSpeed() * 3600) / 1000;
            speed.setText(new DecimalFormat("#0.00").format(s) + " km/hour");
        }
        else {
            speed.setText("GPS is not available!");
            Toast.makeText(Velocity.this, "GPS is not available! Please ensure GPS connection.", Toast.LENGTH_LONG).show();
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                    0, this);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                provider = locationManager.getBestProvider(criteria, true);
                ContextCompat.checkSelfPermission(Velocity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    onLocationChanged(location);
                    double s = (location.getSpeed() * 3600) / 1000;
                    speed.setText(new DecimalFormat("#0.00").format(s) + " km/hour");
                }
                else {
                    speed.setText("GPS is not available!");
                    Toast.makeText(Velocity.this, "GPS is not available! Please ensure GPS connection.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}
