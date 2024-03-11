package com.farms.adhanapp.pray.helper;



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
import com.farms.adhanapp.setting.helper.DataHelper;
import com.farms.adhanapp.location.helper.LocationHelper;
import com.farms.adhanapp.pray.model.PrayModel;

import java.time.*;
import java.util.*;

public abstract class PraysHelper {
    public static PrayModel[] prayModels = new PrayModel[5];
    public static PrayModel getNextPray(){
        LocalDateTime dateTime=LocalDateTime.now();
        ArrayList<LocalDateTime> times=new ArrayList<>(Arrays.asList(Arrays.stream(prayModels).map(PrayModel::getDateTimePray).toArray(LocalDateTime[]::new)));
        times.add(dateTime);
        times.sort(LocalDateTime::compareTo);
        List<PrayModel> prayModelList =Arrays.asList(prayModels);
        // العثور على مؤشر الوقت الحالي في القائمة
        int currentIndex = times.indexOf(dateTime);

        // إذا كان الوقت الحالي هو آخر وقت في القائمة، ارجع null
        if (currentIndex == times.size() - 1) {
            return null;
        }
        return prayModelList.get(currentIndex);



    }
    public static PrayModel[] setPrays() {

            Coordinates coordinates = new Coordinates(LocationHelper.getLocation().getLatitude(), LocationHelper.getLocation().getLongitude());
            DateComponents dateComponents = new DateComponents(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
            CalculationParameters calculationParameters = new CalculationParameters(15.5,17, 0, CalculationMethod.MUSLIM_WORLD_LEAGUE, Madhab.SHAFI, HighLatitudeRule.MIDDLE_OF_THE_NIGHT, new PrayerAdjustments(), new PrayerAdjustments());
            PrayerTimes prayerTimes = new PrayerTimes(coordinates, dateComponents, calculationParameters);

            prayModels[0] = new PrayModel(Prayer.FAJR,getDateTime(prayerTimes.getFajr()));
            prayModels[1] = new PrayModel(Prayer.DHUHR, getDateTime(prayerTimes.getDhuhr()));
            prayModels[2] = new PrayModel(Prayer.ASR, getDateTime(prayerTimes.getAsr()));
            prayModels[3] = new PrayModel(Prayer.MAGHRIB, getDateTime(prayerTimes.getMaghrib()));
            prayModels[4] = new PrayModel(Prayer.ISHA, getDateTime(prayerTimes.getIsha()));


            return prayModels;
    }
    private static LocalDateTime getDateTime(kotlinx.datetime.Instant instant){
        TimeZone defaultZone = TimeZone.getDefault();
        ZonedDateTime zonedDateTime = instant.getValue$kotlinx_datetime().atZone(defaultZone.toZoneId());
        return zonedDateTime.toLocalDateTime();

    }
    public static PrayModel[] getNextSevenDaysPrayTime(Prayer prayer, Context context) {
        PrayModel[] praysSeven=new PrayModel[7];
        LocalDateTime date=LocalDateTime.now();
        Coordinates coordinates = new Coordinates(LocationHelper.getLocation().getLatitude(), LocationHelper.getLocation().getLongitude());
        CalculationParameters calculationParameters = new CalculationParameters(DataHelper.getFloat(context,"fajr angel"), DataHelper.getFloat(context,"isha angel"), 0, CalculationMethod.MUSLIM_WORLD_LEAGUE, Madhab.SHAFI, HighLatitudeRule.MIDDLE_OF_THE_NIGHT, new PrayerAdjustments(), new PrayerAdjustments());

        for (int i = 0; i < 7; i++) {
            date=date.plusDays(1);
            DateComponents dateComponents = new DateComponents(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
            PrayerTimes prayerTimes = new PrayerTimes(coordinates, dateComponents, calculationParameters);

            praysSeven[i]=new PrayModel(prayer,getDateTime(prayerTimes.timeForPrayer(prayer)));
        }
        return praysSeven;
    }
    public static void setPrays(PrayModel[] prayModels){
        PraysHelper.prayModels = prayModels;
    }





}
