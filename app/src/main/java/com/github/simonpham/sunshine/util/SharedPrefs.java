package com.github.simonpham.sunshine.util;

import android.app.Application;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import static com.github.simonpham.sunshine.Consts.DEFAULT_CITY;
import static com.github.simonpham.sunshine.Consts.KEY_CURRENT_LOCATION;
import static com.github.simonpham.sunshine.Consts.KEY_LAST_WEATHER_DATA;
import static com.github.simonpham.sunshine.Consts.KEY_UPDATE_INTERVAL;
import static com.github.simonpham.sunshine.Consts.KEY_USE_CELSIUS;
import static com.github.simonpham.sunshine.Consts.PACKAGE_NAME;

/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */

public class SharedPrefs {

    private SharedPreferences prefs;

    public SharedPrefs(Application application) {
        prefs = application.getSharedPreferences(PACKAGE_NAME + "_preferences", Application.MODE_PRIVATE);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public String getCity() {
        return prefs.getString(KEY_CURRENT_LOCATION, DEFAULT_CITY);
    }

    public void setCity(String city) {
        prefs.edit().putString(KEY_CURRENT_LOCATION, city).apply();
    }

    public String getMetric() {
        return prefs.getBoolean(KEY_USE_CELSIUS, true) ? "metric" : "imperial";
    }

    public String getDisplayMetric() {
        return prefs.getBoolean(KEY_USE_CELSIUS, true) ? "C" : "F";
    }

    public int getUpdateInterval() {
        return Integer.parseInt(prefs.getString(KEY_UPDATE_INTERVAL, "30"));
    }

    public JSONObject getLastWeatherData() {
        String strJson = prefs.getString(KEY_LAST_WEATHER_DATA, "0");
        JSONObject weatherData = new JSONObject();
        try {
            weatherData = new JSONObject(strJson);
        } catch (JSONException ignored) {
            return null;
        }
        return weatherData;
    }

    public void setLastWeatherData(JSONObject jsonObject) {
        prefs.edit().putString(KEY_LAST_WEATHER_DATA, jsonObject.toString()).apply();
    }

}