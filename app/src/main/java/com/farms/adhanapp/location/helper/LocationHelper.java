package com.farms.adhanapp.location.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.farms.adhanapp.setting.helper.DataHelper;
import com.farms.adhanapp.permission.helper.PermissionHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public  class LocationHelper {

    private static Location location =new Location(LocationManager.GPS_PROVIDER);
    public static String cityName=null;

    public static boolean requestPermission(AppCompatActivity appCompatActivity, String permissionString) {
        return PermissionHelper.requestPermission(appCompatActivity, permissionString);
    }

    @SuppressLint({"MissingPermission", "SuspiciousIndentation"})
    public static void fetchLocationUpdates(Context context, CurrentLocation updateLocation) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);



        LocationListener locationListener = locationUpdate->{
            location.set(locationUpdate);
            searchCityName(context);
            DataHelper.saveData(context,(float) locationUpdate.getLatitude(),(float) locationUpdate.getLongitude(),cityName);
            updateLocation.update();
        };

        if (requestPermission((AppCompatActivity) context, "android.permission.ACCESS_FINE_LOCATION")) {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,  locationListener, Looper.myLooper());
        }

    }
    public static void searchCityName(Context context) {
//        if(!(cityName==null))
//            return cityName;
//        else if(DataHelper.isDataExist(context))
//            return DataHelper.getString(context,"city");
//
//        else {

                cityName="";
                Geocoder geocoder = new Geocoder(context, new Locale("ar"));
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            assert addresses != null;
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                cityName = address.getLocality();
            }
            if(cityName.isEmpty())
                cityName="مدينة غير معروفة";
        } catch (Exception e) {
                cityName="مدينة غير معروفة";
        }


//        }

    }

    public static void setCityName(String cityName) {
        LocationHelper.cityName=cityName;
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