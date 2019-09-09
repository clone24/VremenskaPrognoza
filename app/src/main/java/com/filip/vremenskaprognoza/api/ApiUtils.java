package com.filip.vremenskaprognoza.api;

public class ApiUtils {

    public static OpenWeatherApi getService(){
        return RetrofitClient.getClient().create(OpenWeatherApi.class);
    }
}
