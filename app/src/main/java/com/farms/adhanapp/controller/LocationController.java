package com.farms.adhanapp.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.batoulapps.adhan2.Coordinates;
import com.farms.adhanapp.services.PermissionUtils;

import java.util.List;
import java.util.Locale;

public  class   LocationController {

    private static Location location =new Location(LocationManager.GPS_PROVIDER);
    private static String cityName=null;

    public static boolean requestPermission(AppCompatActivity appCompatActivity, String permissionString) {
        return PermissionUtils.requestPermission(appCompatActivity, permissionString);
    }

    @SuppressLint("MissingPermission")
    public static boolean fetchLocationUpdates(Context context, CurrentLocation updateLocation) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);



        LocationListener locationListener = locationUpdate->{
            location.set(locationUpdate);
            cityName=searchCityName(context);
            updateLocation.update();
        };

        if (requestPermission((AppCompatActivity) context, "android.permission.ACCESS_FINE_LOCATION")) {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,  locationListener, Looper.getMainLooper());
            return true;
        }
        else
        return false;

    }
    static String searchCityName(Context context){
        String cityName = "";

        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            assert addresses != null;
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                cityName = address.getLocality();
            }
            return cityName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public static void setCityName(String cityName) {
        LocationController.cityName=cityName;
    }

    public static void setLatLocation(float lat) {
        location.setLatitude(lat);
    }
    public static void setLonLocation(float lon) {
        location.setLongitude(lon);
    }

    public static Location getLocation() {
        return location;
    }


    public interface CurrentLocation{
        void update();
    }


}