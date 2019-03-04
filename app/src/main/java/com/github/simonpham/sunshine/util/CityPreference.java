package com.github.simonpham.sunshine.util;

import android.app.Activity;
import android.content.SharedPreferences;

import static com.github.simonpham.sunshine.Consts.DEFAULT_CITY;

/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */

public class CityPreference {

    private SharedPreferences prefs;

    public CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity() {
        return prefs.getString("city", DEFAULT_CITY);
    }

    public void setCity(String city) {
        prefs.edit().putString("city", city).apply();
    }

}