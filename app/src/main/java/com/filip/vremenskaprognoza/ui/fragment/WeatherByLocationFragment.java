package com.filip.vremenskaprognoza.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.filip.vremenskaprognoza.R;
import com.filip.vremenskaprognoza.api.ApiUtils;
import com.filip.vremenskaprognoza.api.OpenWeatherApi;
import com.filip.vremenskaprognoza.common.SharedPrefs;
import com.filip.vremenskaprognoza.model.WeatherResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.filip.vremenskaprognoza.common.Common.API_KEY;
import static com.filip.vremenskaprognoza.common.Common.BASE_ICON_URL;
import static com.filip.vremenskaprognoza.common.Common.BOOLEAN_KEY;
import static com.filip.vremenskaprognoza.common.Common.CRO_LANG_CODE;
import static com.filip.vremenskaprognoza.common.Common.EMPTY_SPACE;
import static com.filip.vremenskaprognoza.common.Common.EN_LANG_CODE;
import static com.filip.vremenskaprognoza.common.Common.METRIC;
import static com.filip.vremenskaprognoza.common.Common.PICTURE_FORMAT;

public class WeatherByLocationFragment extends Fragment {

    private static final String TAG = "";
    private TextView tvCityName, tvTemperature, tvDescription, tvMaxTemp, tvMinTemp, tvPressure, tvHumidity, tvWindDegreeLocation, tvWindSpeedLocation;

    private ImageView ivWeather;
    private View progressBar;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private double latitude, longitude = 0D;

    private OpenWeatherApi mService;
    private DecimalFormat decimalFormat = new DecimalFormat("0.0");

    private SharedPrefs sharedPrefs;
    private String languageCode;

    private ConstraintLayout layout;

    public WeatherByLocationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_by_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLocationRequest();
        languageCode = Locale.getDefault().getLanguage();
        sharedPrefs = SharedPrefs.getInstance(getActivity());
        initWidgets(view);
        mService = ApiUtils.getService();
        initLanguage();
        initBackgroundColor();

    }

    private void initLocationRequest() {
        if (null != mFusedLocationProviderClient) {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(Objects.requireNonNull(getActivity()), location -> {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                progressBar.setVisibility(View.VISIBLE);
                loadData(latitude, longitude);

            });
        }
    }

    private void initWidgets(View view) {
        tvCityName = view.findViewById(R.id.tvCityName);
        tvTemperature = view.findViewById(R.id.tvTemperatureLocation);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvPressure = view.findViewById(R.id.tvPressureLocation);
        tvHumidity = view.findViewById(R.id.tvHumidityLocation);
        tvWindDegreeLocation = view.findViewById(R.id.tvWindDegreeLocation);
        tvWindSpeedLocation = view.findViewById(R.id.tvWindSpeedLocation);
        ivWeather = view.findViewById(R.id.ivWeather);
        tvMaxTemp = view.findViewById(R.id.maxTempTitleLocation);
        tvMinTemp = view.findViewById(R.id.minTempTitleLocation);
        progressBar = view.findViewById(R.id.progressBar);
        layout = view.findViewById(R.id.layout);

    }

    private void initLanguage() {
        if (languageCode.equals(CRO_LANG_CODE)) {
            languageCode = CRO_LANG_CODE;
        } else {
            languageCode = EN_LANG_CODE;
        }
    }

    private void loadData(double latitude, double longitude) {

        mService.getWeatherByLocation(latitude, longitude, METRIC, languageCode, API_KEY).enqueue(new Callback<WeatherResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    String icon = "";
                    String temperature = decimalFormat.format(response.body().getMainWeatherModel().getTemp()) + getString(R.string.celsius);
                    String minTemperature = decimalFormat.format(response.body().getMainWeatherModel().getTempMin()) + getString(R.string.celsius);
                    String maxTemperature = decimalFormat.format(response.body().getMainWeatherModel().getTempMax()) + getString(R.string.celsius);
                    String humidity = (response.body().getMainWeatherModel().getHumidity()) + getString(R.string.percent);
                    String pressure = (response.body().getMainWeatherModel().getPressure()) + " " + getString(R.string.pressureUni);
                    String weatherStatus = response.body().getWeather().get(0).getDescription();
                    String windSpeed = (response.body().getWindModel().getSpeed()) + getString(R.string.wind_speed_symbol);
                    String windDegree = (response.body().getWindModel().getWindDegree()) + getString(R.string.degrees);
                    String city = response.body().getName();
                    if (response.body().getWeather().size() > 0) {
                        icon = response.body().getWeather().get(0).getIcon();
                    }

                    tvTemperature.append(EMPTY_SPACE + temperature);
                    tvDescription.append(EMPTY_SPACE + weatherStatus);
                    tvMaxTemp.append(EMPTY_SPACE + maxTemperature);
                    tvMinTemp.append(EMPTY_SPACE + minTemperature);
                    tvPressure.append(EMPTY_SPACE + pressure);
                    tvHumidity.append(EMPTY_SPACE + humidity);
                    tvWindDegreeLocation.append(EMPTY_SPACE + windDegree);
                    tvWindSpeedLocation.append(EMPTY_SPACE + windSpeed);
                    tvCityName.setText(city);

                    initWeatherIcon(icon);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable errorMessage) {
                Toast.makeText(getActivity(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBackgroundColor() {

        if (!sharedPrefs.getColor(BOOLEAN_KEY)) {
            setWhiteBackground();
        } else {
            setBlueBackground();
        }
    }

    private void setWhiteBackground() {
        layout.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.white));
    }

    private void setBlueBackground() {
        layout.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.blue_bg));
    }

    public void initWeatherIcon(String icon) {
        String iconUrl = BASE_ICON_URL + icon + PICTURE_FORMAT;
        Picasso.get().load(iconUrl).error(R.drawable.blue_bg).into(ivWeather);
    }
}