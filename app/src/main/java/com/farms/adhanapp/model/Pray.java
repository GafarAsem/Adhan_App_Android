package com.farms.adhanapp.model;

import android.os.Build;

import com.batoulapps.adhan2.Prayer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Pray {

    private Prayer prayer;
    private LocalDateTime dateTimePray;


    public Pray(Prayer prayer, LocalDateTime dateTimePray) {
        this.prayer = prayer;
        this.dateTimePray = dateTimePray;
    }

    public LocalDateTime getDateTimePray() {
        return dateTimePray;
    }

    public void setDateTimePray(LocalDateTime dateTimePray) {
        this.dateTimePray = dateTimePray;
    }

    public Prayer getPrayer() {
        return prayer;
    }

    public void setPrayer(Prayer prayer) {
        this.prayer = prayer;
    }


    public String getArabicNamePray() {
        if(this.getDateTimePray().getDayOfWeek().equals(DayOfWeek.FRIDAY)&&this.getPrayer().equals(Prayer.DHUHR))
            return "الجمعة";

        switch (prayer){
        case FAJR:
            return "الفجر";
        case DHUHR:
            return "الظهر";
        case ASR:
           return "العصر";
        case MAGHRIB:
            return "المغرب";
        case ISHA:
            return "العشاء";
            default:
                return "none";
        }

    }


}
