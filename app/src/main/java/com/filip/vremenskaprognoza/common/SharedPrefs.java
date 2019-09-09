package com.filip.vremenskaprognoza.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private static SharedPrefs sharedPrefs;
    private SharedPreferences sharedPreferences;

    public static SharedPrefs getInstance(Context context) {
        if (sharedPrefs == null) {
            sharedPrefs = new SharedPrefs(context);
        }
        return sharedPrefs;
    }

    private SharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void clear() { // Delete all shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

    public void changeColor(String key, boolean value) {

        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();

    }

    public boolean getColor(String key) {

        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, false);

        }
        return false;

    }
}
