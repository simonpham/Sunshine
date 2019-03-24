package com.github.simonpham.sunshine.model;

import com.github.simonpham.sunshine.SingletonIntances;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */
public class Forecast {

    private long date;
    private Main main;
    private Weather weather;
    private Clouds clouds;
    private Wind wind;
    private Rain rain;
    private Snow snow;
    private String displayDate;

    public Forecast(long date, Main main, Weather weather, Clouds clouds, Wind wind, Rain rain, Snow snow, String displayDate) {
        this.date = date;
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.rain = rain;
        this.snow = snow;
        this.displayDate = displayDate;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Main getMain() {
        return this.main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Weather getWeather() {
        return this.weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return this.clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return this.wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return this.rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return this.snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "Forecast for %s (%s):" + "\nWeather: %s" + "\nHighest temperature: %.0f°" + SingletonIntances.getSharedPrefs().getDisplayMetric() + "\nLowest temperature: %.0f°" + SingletonIntances.getSharedPrefs().getDisplayMetric() + "\nHumidity: %d%%" + "\nPressure (sea-level): %shPa" + "\nPressure (ground-level): %shPa" + "\nCloudiness: %s" + "\nWind: %s - %s" + "\nRain: %s" + "\nSnow: %s"
                , getDisplayDate()
                , new SimpleDateFormat("MMMM dd", Locale.getDefault()).format(new Date(getDate() * 1000))
                , getWeather().getDescription()
                , getMain().getTempMax()
                , getMain().getTempMin()
                , getMain().getHumidity()
                , getMain().getSeaLevel()
                , getMain().getGroundLevel()
                , getClouds() != null ? getClouds().getAll() + "%" : "N/A"
                , getWind() != null ? getWind().getDeg() + "°" : "N/A"
                , getWind() != null ? getWind().getSpeed() + "m/s" : "N/A"
                , getRain() != null ? getRain().getVolumn() + "mm" : "N/A"
                , getSnow() != null ? getSnow().getVolumn() + "mm" : "N/A"
        );
    }
}
