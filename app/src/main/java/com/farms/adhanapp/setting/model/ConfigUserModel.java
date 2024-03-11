package com.farms.adhanapp.setting.model;

import com.batoulapps.adhan2.CalculationMethod;

import android.location.Location;
public class ConfigUserModel {

    private Location currentLocation;
    private String country;
    private String city;
    private CalculationMethod calculationMethod;

    public ConfigUserModel() {
    }

    public ConfigUserModel(Location currentLocation, String country, String city, CalculationMethod calculationMethod) {
        this.currentLocation = currentLocation;
        this.country = country;
        this.city = city;
        this.calculationMethod = calculationMethod;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CalculationMethod getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(CalculationMethod calculationMethod) {
        this.calculationMethod = calculationMethod;
    }
}
