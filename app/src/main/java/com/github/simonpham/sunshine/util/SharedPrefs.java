package com.github.simonpham.sunshine.util;

import android.app.Application;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import static com.github.simonpham.sunshine.Consts.DEFAULT_CITY;
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
        return prefs.getString("current_location", DEFAULT_CITY);
    }

    public void setCity(String city) {
        prefs.edit().putString("current_location", city).apply();
    }

    public JSONObject getLastWeatherData() {
        String strJson = prefs.getString("last_weather_data", "0");
        JSONObject weatherData = new JSONObject();
        try {
            weatherData = new JSONObject(strJson);

        } catch (JSONException ignored) {

        }
        return weatherData;
    }

    public void setLastWeatherData(JSONObject jsonObject) {
        prefs.edit().putString("last_weather_data", jsonObject.toString()).apply();
    }

}