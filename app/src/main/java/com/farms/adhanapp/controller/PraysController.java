package com.farms.adhanapp.controller;

import android.content.Context;
import android.widget.Toast;

import com.batoulapps.adhan2.CalculationMethod;
import com.batoulapps.adhan2.CalculationParameters;
import com.batoulapps.adhan2.Coordinates;
import com.batoulapps.adhan2.HighLatitudeRule;
import com.batoulapps.adhan2.Madhab;
import com.batoulapps.adhan2.Prayer;
import com.batoulapps.adhan2.PrayerAdjustments;
import com.batoulapps.adhan2.PrayerTimes;
import com.batoulapps.adhan2.data.DateComponents;
import com.farms.adhanapp.model.Pray;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class  PraysController {
    private static Pray[] prays = new Pray[5];
    public static Pray[] fetchPrays() {

            Coordinates coordinates = new Coordinates(LocationController.getLocation().getLatitude(), LocationController.getLocation().getLongitude());
            DateComponents dateComponents = new DateComponents(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
            CalculationParameters calculationParameters = new CalculationParameters(15.5, 17, 0, CalculationMethod.MUSLIM_WORLD_LEAGUE, Madhab.SHAFI, HighLatitudeRule.MIDDLE_OF_THE_NIGHT, new PrayerAdjustments(), new PrayerAdjustments());
            PrayerTimes prayerTimes = new PrayerTimes(coordinates, dateComponents, calculationParameters);

            prays[0] = new Pray(Prayer.FAJR, LocalTime.parse(prayerTimes.getFajr().toString().substring(11, 16)));
            prays[1] = new Pray(Prayer.DHUHR, LocalTime.parse(prayerTimes.getDhuhr().toString().substring(11, 16)));
            prays[2] = new Pray(Prayer.ASR, LocalTime.parse(prayerTimes.getAsr().toString().substring(11, 16)));
            prays[3] = new Pray(Prayer.MAGHRIB, LocalTime.parse(prayerTimes.getMaghrib().toString().substring(11, 16)));
            prays[4] = new Pray(Prayer.ISHA, LocalTime.parse(prayerTimes.getIsha().toString().substring(11, 16)));



            return prays;
    }
    public static void setPrays(Pray[] prays){
        PraysController.prays=prays;
    }
    public static void setStringPrays(Set<String> times,Set<String> alarms){
        Object[] time=times.toArray();
        Object[] alarm=alarms.toArray();
        prays[0] = new Pray(Prayer.FAJR, LocalTime.parse(time[0].toString()));
        prays[0].setAlarm((Boolean) alarm[0]);
        prays[1] = new Pray(Prayer.DHUHR, LocalTime.parse(time[1].toString()));
        prays[1].setAlarm((Boolean) alarm[1]);
        prays[2] = new Pray(Prayer.ASR, LocalTime.parse(time[2].toString()));
        prays[2].setAlarm((Boolean) alarm[2]);
        prays[3] = new Pray(Prayer.MAGHRIB, LocalTime.parse(time[3].toString()));
        prays[3].setAlarm((Boolean) alarm[3]);
        prays[4] = new Pray(Prayer.ISHA, LocalTime.parse(time[4].toString()));
        prays[4].setAlarm((Boolean) alarm[4]);

    }




}
