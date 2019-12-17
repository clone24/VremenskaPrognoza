package com.filip.vremenskaprognoza.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.filip.vremenskaprognoza.R;
import com.filip.vremenskaprognoza.api.ApiUtils;
import com.filip.vremenskaprognoza.api.OpenWeatherApi;
import com.filip.vremenskaprognoza.common.SharedPrefs;
import com.filip.vremenskaprognoza.model.WeatherResult;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.filip.vremenskaprognoza.common.Common.API_KEY;
import static com.filip.vremenskaprognoza.common.Common.BOOLEAN_KEY;
import static com.filip.vremenskaprognoza.common.Common.CRO_LANG_CODE;
import static com.filip.vremenskaprognoza.common.Common.EMPTY_SPACE;
import static com.filip.vremenskaprognoza.common.Common.EN_LANG_CODE;
import static com.filip.vremenskaprognoza.common.Common.METRIC;

public class WeatherSearchCityFragment extends Fragment {

    private TextView temperatureValue, humidityValue, pressureValue, latLongValue, weatherStatusTextView, tvWindSpeed, tvWindDegree, minTemperatureValue, maxTemperatureValue;

    private ImageView mapImageView;

    private View progressBar;

    private ConstraintLayout layout;

    private EditText inputCity;
    private Button getCityWeatherBtn;

    private double latitude, longitude;

    private OpenWeatherApi mService;

    private DecimalFormat decimalFormat = new DecimalFormat("0.0");

    private SharedPrefs sharedPrefs;
    private String languageCode;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_search_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkPermissions();
        languageCode = Locale.getDefault().getLanguage();
        sharedPrefs = SharedPrefs.getInstance(getActivity());
        initWidgets(view);
        mService = ApiUtils.getService();
        initListeners();
        initButtonColor();
        initLanguage();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                } else {
                    Toast.makeText(getActivity(), "your message", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initLanguage() {
        if (languageCode.equals(CRO_LANG_CODE)) {
            languageCode = CRO_LANG_CODE;
        } else {
            languageCode = EN_LANG_CODE;
        }
    }

    private void initWidgets(View view) {

        maxTemperatureValue = view.findViewById(R.id.maxTemperatureValue);
        minTemperatureValue = view.findViewById(R.id.minTemperatureValue);
        temperatureValue = view.findViewById(R.id.temperatureValue);
        humidityValue = view.findViewById(R.id.humidityValue);
        pressureValue = view.findViewById(R.id.pressureValue);
        latLongValue = view.findViewById(R.id.cityCoordinates);
        inputCity = view.findViewById(R.id.inputCity);
        weatherStatusTextView = view.findViewById(R.id.weatherStatus);
        getCityWeatherBtn = view.findViewById(R.id.getCityWeatherBtn);
        mapImageView = view.findViewById(R.id.icnMap);
        tvWindSpeed = view.findViewById(R.id.windSpeed);
        tvWindDegree = view.findViewById(R.id.windDegree);
        layout = view.findViewById(R.id.layout);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void initListeners() {
        getCityWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCity.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.enter_name_city), Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loadData(inputCity.getText().toString());
                    hideKeyboard(getActivity());
                }
            }
        });
        mapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMaps();
            }
        });
    }

    private void openGoogleMaps() {
        if (latitude != 0.0 && longitude != 0.0) {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            Objects.requireNonNull(getActivity()).startActivity(intent);
        }
    }

    private void loadData(String city) {

        mService.getWeather(city, METRIC, languageCode, API_KEY).enqueue(new Callback<WeatherResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);

                    String temperature = decimalFormat.format(response.body().getMainWeatherModel().getTemp()) + getString(R.string.celsius);
                    String minTemperature = decimalFormat.format(response.body().getMainWeatherModel().getTempMin()) + getString(R.string.celsius);
                    String maxTemperature = decimalFormat.format(response.body().getMainWeatherModel().getTempMax()) + getString(R.string.celsius);

                    latitude = response.body().getCoordinatesModel().getLat();
                    longitude = response.body().getCoordinatesModel().getLon();

                    String latitude = (response.body().getCoordinatesModel().getLat()) + getString(R.string.degrees);
                    String longitude = (response.body().getCoordinatesModel().getLon()) + getString(R.string.degrees);
                    String humidity = (response.body().getMainWeatherModel().getHumidity()) + getString(R.string.percent);
                    String pressure = (response.body().getMainWeatherModel().getPressure()) + " " + getString(R.string.pressureUni);
                    String weatherStatus = response.body().getWeather().get(0).getDescription();
                    String windSpeed = (response.body().getWindModel().getSpeed()) + getString(R.string.wind_speed_symbol);
                    String windDegree = (response.body().getWindModel().getWindDegree()) + getString(R.string.degrees);

                    temperatureValue.setText(temperature);
                    latLongValue.setText(latitude + ", " + longitude);
                    humidityValue.setText(humidity);
                    pressureValue.setText(pressure);
                    weatherStatusTextView.setText(weatherStatus);
                    tvWindSpeed.setText(windSpeed);
                    tvWindDegree.setText(windDegree);
                    minTemperatureValue.setText(minTemperature);
                    maxTemperatureValue.setText(maxTemperature);

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

    private void initButtonColor() {

        if (!sharedPrefs.getColor(BOOLEAN_KEY)) {
            setBlueColor();

        } else {
            setRedColor();

        }
    }

    private void setBlueColor() {
        getCityWeatherBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.get_weather_btn));
        layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));

    }

    private void setRedColor() {
        getCityWeatherBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.grey_button));
        layout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.blue_bg));

    }

    private static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = activity.getCurrentFocus();
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}