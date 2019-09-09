package com.filip.vremenskaprognoza.model;

import com.google.gson.annotations.SerializedName;

public class WindModel {

    private double speed;
    @SerializedName("deg")
    private double windDegree;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }
}
