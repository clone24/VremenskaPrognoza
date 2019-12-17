package com.filip.vremenskaprognoza.model;

public class WeatherModel {

    private int id;
    private String main;
    private String description;
    private String icon;

    public WeatherModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
