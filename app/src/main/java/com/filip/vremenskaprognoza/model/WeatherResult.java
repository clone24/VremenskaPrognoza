package com.filip.vremenskaprognoza.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResult {

    @SerializedName("coord")
    private CoordinatesModel coordinatesModel;
    private ArrayList<WeatherModel> weather;
    private String base;
    @SerializedName("main")
    private MainWeatherModel mainWeatherModel;
    private int visibility;
    @SerializedName("wind")
    private WindModel windModel;
    private int timezone;
    private long id;
    private String name;
    private int cod;

    public CoordinatesModel getCoordinatesModel() {
        return coordinatesModel;
    }

    public ArrayList<WeatherModel> getWeather() {
        return weather;
    }

    public MainWeatherModel getMainWeatherModel() {
        return mainWeatherModel;
    }

    public WindModel getWindModel() {
        return windModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}