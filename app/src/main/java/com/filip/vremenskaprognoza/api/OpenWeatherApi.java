package com.filip.vremenskaprognoza.api;

import com.filip.vremenskaprognoza.model.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("weather")
    Call<WeatherResult> getWeather(
            @Query("q") String location,
            @Query("units") String unit,
            @Query("lang") String language,
            @Query("appid") String apiKey
    );

    @GET("weather")
    Call<WeatherResult> getWeatherByLocation(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("units") String unit,
            @Query("lang") String language,
            @Query("appid") String apiKey
    );
}