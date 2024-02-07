package com.farms.adhanapp.model;

import android.os.Build;

import com.batoulapps.adhan2.Prayer;

import java.time.LocalTime;

public class Pray {

    private Prayer prayer;
    private String namePray=" ";
    private LocalTime timeAdhan;
    private LocalTime timePray;
    private LocalTime endTimePray;
    private Adhan adhan;
    private boolean alarm;

    public Pray(){
this.alarm=true;
    }

    public Pray(Prayer namePray, LocalTime timeAdhan, LocalTime timePray, LocalTime endTimePray, Adhan adhan) {
        this.prayer = namePray;
        this.timeAdhan = timeAdhan;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.timeAdhan = timeAdhan.plusHours(3);
        } this.endTimePray = endTimePray;
        this.adhan = adhan;
        this.alarm=true;

    }

    public Pray(Prayer namePray, LocalTime timeAdhan) {
        this.prayer = namePray;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.timeAdhan = timeAdhan.plusHours(3);
        }
    }

    public Prayer getPrayer() {
        return prayer;
    }

    public void setPrayer(Prayer prayer) {
        this.prayer = prayer;
    }

    public LocalTime getTimeAdhan() {
        return timeAdhan;
    }

    public void setTimeAdhan(LocalTime timeAdhan) {
        this.timeAdhan = timeAdhan;
    }

    public LocalTime getTimePray() {
        return timePray;
    }

    public void setTimePray(LocalTime timeLocalTimePray) {
        this.timePray = timePray;
    }

    public LocalTime getEndTimePray() {
        return endTimePray;
    }

    public void setEndTimePray(LocalTime endTimePray) {
        this.endTimePray = endTimePray;
    }

    public Adhan getAdhan() {
        return adhan;
    }

    public void setAdhan(Adhan adhan) {
        this.adhan = adhan;
    }

    public String getNamePray() {
        switch (prayer){


            case FAJR:
                namePray="الفجر";
                break;
            case DHUHR:
                namePray="الظهر";

                break;
            case ASR:
                namePray="العصر";
                break;
            case MAGHRIB:
                namePray="المغرب";
                break;
            case ISHA:
                namePray="العشاء";
                break;
        }
        return namePray;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public void setNamePray(String namePray) {
        this.namePray = namePray;
    }
}
