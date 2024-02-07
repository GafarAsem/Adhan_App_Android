package com.farms.adhanapp.controller;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.batoulapps.adhan2.CalculationMethod;
import com.batoulapps.adhan2.CalculationParameters;
import com.batoulapps.adhan2.Coordinates;
import com.batoulapps.adhan2.HighLatitudeRule;
import com.batoulapps.adhan2.Madhab;
import com.batoulapps.adhan2.Prayer;
import com.batoulapps.adhan2.PrayerAdjustments;
import com.batoulapps.adhan2.PrayerTimes;
import com.batoulapps.adhan2.data.DateComponents;
import com.farms.adhanapp.model.ConfigUser;
import com.farms.adhanapp.model.Pray;
import com.farms.adhanapp.services.PermissionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;


public interface Controller {


    ConfigUser configUser = new ConfigUser();






    class AdhanController {

    }


}
