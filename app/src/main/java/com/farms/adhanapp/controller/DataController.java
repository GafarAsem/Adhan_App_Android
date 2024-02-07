package com.farms.adhanapp.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.widget.Toast;

import com.farms.adhanapp.model.Pray;
import com.farms.adhanapp.view.SettingActivity;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public interface DataController {
    static void saveData(Context context, Location location, Pray[] prays){
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("lat", (float) location.getLatitude());
        editor.putFloat("lon", (float) location.getLongitude());
        editor.putString("city", LocationController.searchCityName(context));
        Set<String> times=new HashSet<>();
        times.add(prays[0].getTimeAdhan().toString());
        times.add(prays[1].getTimeAdhan().toString());
        times.add(prays[2].getTimeAdhan().toString());
        times.add(prays[3].getTimeAdhan().toString());
        times.add(prays[4].getTimeAdhan().toString());
        editor.putStringSet("times", times);
        Set<String> alarms=new HashSet<>();
        alarms.add(String.valueOf(prays[0].isAlarm()));
        alarms.add(String.valueOf(prays[1].isAlarm()));
        alarms.add(String.valueOf(prays[2].isAlarm()));
        alarms.add(String.valueOf(prays[3].isAlarm()));
        alarms.add(String.valueOf(prays[4].isAlarm()));
        editor.putStringSet("alarms", alarms);
        editor.apply();
    }
    static void fetchData(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        LocationController.setLatLocation(sharedPreferences.getFloat("lat",0));
        LocationController.setLonLocation(sharedPreferences.getFloat("lon",0));
        LocationController.setCityName(sharedPreferences.getString("city","none"));
        Set<String> times=sharedPreferences.getStringSet("times",null);
        Set<String> alarms=sharedPreferences.getStringSet("alarms",null);
        try {
           // PraysController.setStringPrays(times,alarms);

        }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    static boolean isDataExist(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        return sharedPreferences.contains("lat");

    }
}
