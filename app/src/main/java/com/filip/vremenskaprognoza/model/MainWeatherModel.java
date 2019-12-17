package com.filip.vremenskaprognoza.model;

import com.google.gson.annotations.SerializedName;

public class MainWeatherModel {

    private double temp;
    private double pressure;
    private int humidity;
    @SerializedName("temp_min")
    private double tempMin;
    @SerializedName("temp_max")
    private double tempMax;

    public MainWeatherModel() {
    }

    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

}