package com.filip.vremenskaprognoza.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.filip.vremenskaprognoza.R;
import com.filip.vremenskaprognoza.ui.fragment.InfoFragment;
import com.filip.vremenskaprognoza.ui.fragment.SettingsFragment;
import com.filip.vremenskaprognoza.ui.fragment.WeatherByLocationFragment;
import com.filip.vremenskaprognoza.ui.fragment.WeatherSearchCityFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        initFragmentToOpen();
        initListeners();

    }

    private void initWidgets() {
        drawerLayout = findViewById(R.id.activity_main);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView = findViewById(R.id.navigationView);
    }

    private void initFragmentToOpen() {
        WeatherSearchCityFragment newFragment = new WeatherSearchCityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initListeners() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.city:
                        WeatherSearchCityFragment newFragment = new WeatherSearchCityFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.settings:
                        SettingsFragment settingsFragment = new SettingsFragment();
                        FragmentTransaction transactionSettings = getSupportFragmentManager().beginTransaction();
                        transactionSettings.replace(R.id.container, settingsFragment);
                        transactionSettings.addToBackStack(null);
                        transactionSettings.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.info:
                        InfoFragment infoFragment = new InfoFragment();
                        FragmentTransaction transactionInfo = getSupportFragmentManager().beginTransaction();
                        transactionInfo.replace(R.id.container, infoFragment);
                        transactionInfo.addToBackStack(null);
                        transactionInfo.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.location:
                        WeatherByLocationFragment locationFragment = new WeatherByLocationFragment();
                        FragmentTransaction transactionLocation = getSupportFragmentManager().beginTransaction();
                        transactionLocation.replace(R.id.container, locationFragment);
                        transactionLocation.addToBackStack(null);
                        transactionLocation.commit();
                        drawerLayout.closeDrawers();
                    default:
                        return true;
                }
                return true;
            }
        });
    }
}