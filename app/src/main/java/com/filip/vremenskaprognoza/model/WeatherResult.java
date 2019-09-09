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

    public void setCoordinatesModel(CoordinatesModel coordinatesModel) {
        this.coordinatesModel = coordinatesModel;
    }

    public ArrayList<WeatherModel> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<WeatherModel> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainWeatherModel getMainWeatherModel() {
        return mainWeatherModel;
    }

    public void setMainWeatherModel(MainWeatherModel mainWeatherModel) {
        this.mainWeatherModel = mainWeatherModel;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public WindModel getWindModel() {
        return windModel;
    }

    public void setWindModel(WindModel windModel) {
        this.windModel = windModel;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
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

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
