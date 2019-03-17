package com.github.simonpham.sunshine;

import android.app.Application;
import android.content.Context;

import com.github.simonpham.sunshine.model.Forecast;
import com.github.simonpham.sunshine.util.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon Pham on 3/10/19.
 * Email: simonpham.dn@gmail.com
 */
public class SingletonIntances {

    private static boolean initialized = false;
    private static SingletonIntances INSTANCE = null;

    private List<Forecast> forecasts = new ArrayList<>();

    private SharedPrefs sharedPrefs = null;

    private SingletonIntances() {
    }

    static void init(Context context) {
        if (!initialized) {
            if (INSTANCE == null) {
                INSTANCE = new SingletonIntances();
            }
            INSTANCE.sharedPrefs = new SharedPrefs((Application) context);
            initialized = true;
        }
    }

    public static List<Forecast> getForecasts() {
        return INSTANCE.forecasts;
    }

    public static void setForecasts(List<Forecast> forecasts) {
        INSTANCE.forecasts = forecasts;
    }

    public static SharedPrefs getSharedPrefs() {
        return INSTANCE.sharedPrefs;
    }
}
