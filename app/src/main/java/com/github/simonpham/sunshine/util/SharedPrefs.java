package com.github.simonpham.sunshine.util;

import android.app.Activity;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import static com.github.simonpham.sunshine.Consts.DEFAULT_CITY;

/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */

public class SharedPrefs {

    private SharedPreferences prefs;

    public SharedPrefs(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity() {
        return prefs.getString("city", DEFAULT_CITY);
    }

    public void setCity(String city) {
        prefs.edit().putString("city", city).apply();
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