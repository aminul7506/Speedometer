package com.example.user.speedometer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 4/17/2016.
 */
public class CurrentLocation extends AppCompatActivity implements LocationListener {
    TextView country1,state1,city1,postalCode1,area1,address1,feature1,latitude1,longitude1,cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        cl = (TextView)findViewById(R.id.cl);
        country1 = (TextView) findViewById(R.id.country);
        state1 = (TextView) findViewById(R.id.state);
        city1 = (TextView) findViewById(R.id.city);
        postalCode1 = (TextView) findViewById(R.id.postalCode);
        area1 = (TextView) findViewById(R.id.area);
        address1 = (TextView) findViewById(R.id.area);
        feature1 = (TextView)findViewById(R.id.feature);
        latitude1 = (TextView) findViewById(R.id.latitude);
        longitude1 = (TextView) findViewById(R.id.longitude);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider;
        Location location;
        provider = locationManager.getBestProvider(criteria, true);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        location = locationManager.getLastKnownLocation(provider);
        Geocoder geocoder = new Geocoder(CurrentLocation.this, Locale.getDefault());
        double latitude = 0, longitude = 0;
        String city = "Not Available!", state = "Not Available!", zip = "Not Available!", country = "Not available!",
                knownName = "Not Available!", address = "Not Available!";
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            List<Address> addresses;
            try {
                if (location != null) {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    knownName = addresses.get(0).getFeatureName();
                    address = addresses.get(0).getAddressLine(0);
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    zip = addresses.get(0).getPostalCode();
                    country = addresses.get(0).getCountryName();
                    if (city == null) city = "Not Available!";
                    if (state == null) state = "Not Available!";
                    if (zip == null) zip = "Not Available!";
                    if (country == null) country = "Not Available!";
                    if (knownName == null) knownName = "Not Available!";
                    if (address == null) address = "Not Available!";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else Toast.makeText(CurrentLocation.this, "GPS is not available! Please ensure GPS and Internet connection.",Toast.LENGTH_LONG).show();

        cl.setText("Current Location ");
        country1.setText("Country : " + country);
        state1.setText("State : " + state);
        city1.setText("City : " + city);
        postalCode1.setText("Postal Code : " + zip);
        feature1.setText("Feature Name : " + knownName);
        area1.setText("Area : " + address);
        latitude1.setText("Latitude : " + new DecimalFormat("#0.00").format(latitude) + " degree");
        longitude1.setText("Longitude : " + new DecimalFormat("#0.00").format(longitude) + " degree");
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                    0, this);
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
