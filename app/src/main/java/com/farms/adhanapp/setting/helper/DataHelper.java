package com.farms.adhanapp.setting.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.batoulapps.adhan2.CalculationMethod;
import com.farms.adhanapp.location.helper.LocationHelper;

public interface DataHelper {
    static void saveData(Context context, float lat,float lon, String cityName){
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("lat", lat);
        editor.putFloat("lon", lon);
//        editor.putFloat("fajr angle", fajrAngle);
//        editor.putFloat("isha angle", ishaAngle);
        editor.putString("city", cityName);
//        editor.putString("calculation method", calculationMethod.toString());
        editor.apply();


    }
    static void fetchData(Context context){

            SharedPreferences sharedPreferences =  context.getSharedPreferences("location", Context.MODE_PRIVATE);
            LocationHelper.setLatLocation(sharedPreferences.getFloat("lat", 0));
            LocationHelper.setLonLocation(sharedPreferences.getFloat("lon", 0));
            LocationHelper.setCityName(sharedPreferences.getString("city", "none"));

    }
    static String getString(Context context, String key){
        if(isDataExist(context)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "مدينة غير معروفة");
        }return null;
    }
    static Float getFloat(Context context, String key){
        if(isDataExist(context)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
            return sharedPreferences.getFloat(key, 0);
        }return null;
    }
    static boolean isDataExist(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        return sharedPreferences.contains("lat");

    }
}
