package com.github.simonpham.sunshine;

import com.github.simonpham.sunshine.model.Forecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon Pham on 3/10/19.
 * Email: simonpham.dn@gmail.com
 */
public class SingletonIntances {

    private static SingletonIntances INSTANCE = null;

    private List<Forecast> forecasts = new ArrayList<>();

    private SingletonIntances() {
    }

    public static SingletonIntances getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonIntances();
        }
        return (INSTANCE);
    }

    public List<Forecast> getForecasts() {
        return this.forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
