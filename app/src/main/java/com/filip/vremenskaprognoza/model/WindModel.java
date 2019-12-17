package com.filip.vremenskaprognoza.model;

import com.google.gson.annotations.SerializedName;

public class WindModel {

    private double speed;
    @SerializedName("deg")
    private double windDegree;

    public WindModel() {
    }

    public double getSpeed() {
        return speed;
    }

    public double getWindDegree() {
        return windDegree;
    }
}
