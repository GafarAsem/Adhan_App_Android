package com.farms.adhanapp.controller;



import android.content.Context;
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

import java.time.*;
import java.util.*;

public class  PraysController {
    public static Pray[] prays = new Pray[5];
    public static Pray getNextPray(LocalDateTime dateTime){
        ArrayList<LocalDateTime> times=new ArrayList<>(Arrays.asList(Arrays.stream(prays).map(Pray::getDateTimePray).toArray(LocalDateTime[]::new)));
        times.add(dateTime);
        times.sort(LocalDateTime::compareTo);
        List<Pray> prayList =Arrays.asList(prays);
        // العثور على مؤشر الوقت الحالي في القائمة
        int currentIndex = times.indexOf(dateTime);

        // إذا كان الوقت الحالي هو آخر وقت في القائمة، ارجع null
        if (currentIndex == times.size() - 1) {
            return null;
        }
        return prayList.get(currentIndex);



    }
    public static Pray[] fetchPrays(Context context) {

            Coordinates coordinates = new Coordinates(LocationController.getLocation().getLatitude(), LocationController.getLocation().getLongitude());
            DateComponents dateComponents = new DateComponents(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
            CalculationParameters calculationParameters = new CalculationParameters(15.5,17, 0, CalculationMethod.MUSLIM_WORLD_LEAGUE, Madhab.SHAFI, HighLatitudeRule.MIDDLE_OF_THE_NIGHT, new PrayerAdjustments(), new PrayerAdjustments());
            PrayerTimes prayerTimes = new PrayerTimes(coordinates, dateComponents, calculationParameters);

            prays[0] = new Pray(Prayer.FAJR,getDateTime(prayerTimes.getFajr()));
            prays[1] = new Pray(Prayer.DHUHR, getDateTime(prayerTimes.getDhuhr()));
            prays[2] = new Pray(Prayer.ASR, getDateTime(prayerTimes.getAsr()));
            prays[3] = new Pray(Prayer.MAGHRIB, getDateTime(prayerTimes.getMaghrib()));
            prays[4] = new Pray(Prayer.ISHA, getDateTime(prayerTimes.getIsha()));


            return prays;
    }
    private static LocalDateTime getDateTime(kotlinx.datetime.Instant instant){
        TimeZone defaultZone = TimeZone.getDefault();
        ZonedDateTime zonedDateTime = instant.getValue$kotlinx_datetime().atZone(defaultZone.toZoneId());
        return zonedDateTime.toLocalDateTime(); // 2024-03-01T17:21:14.556789

    }
    public static Pray[] getNextSevenDaysPrayTime(Prayer prayer, Context context) {
        Pray[] praysSeven=new Pray[7];
        LocalDateTime date=LocalDateTime.now();
        Coordinates coordinates = new Coordinates(LocationController.getLocation().getLatitude(), LocationController.getLocation().getLongitude());
        CalculationParameters calculationParameters = new CalculationParameters(DataController.getFloat(context,"fajr angel"),DataController.getFloat(context,"isha angel"), 0, CalculationMethod.MUSLIM_WORLD_LEAGUE, Madhab.SHAFI, HighLatitudeRule.MIDDLE_OF_THE_NIGHT, new PrayerAdjustments(), new PrayerAdjustments());

        for (int i = 0; i < 7; i++) {
            date=date.plusDays(1);
            DateComponents dateComponents = new DateComponents(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
            PrayerTimes prayerTimes = new PrayerTimes(coordinates, dateComponents, calculationParameters);

            praysSeven[i]=new Pray(prayer,getDateTime(prayerTimes.timeForPrayer(prayer)));
        }
        return praysSeven;
    }
    public static void setPrays(Pray[] prays){
        PraysController.prays=prays;
    }





}
