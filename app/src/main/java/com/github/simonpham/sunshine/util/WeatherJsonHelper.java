package com.github.simonpham.sunshine.util;

import android.content.Context;

import com.github.simonpham.sunshine.SingletonIntances;
import com.github.simonpham.sunshine.model.Clouds;
import com.github.simonpham.sunshine.model.Forecast;
import com.github.simonpham.sunshine.model.Main;
import com.github.simonpham.sunshine.model.Rain;
import com.github.simonpham.sunshine.model.Snow;
import com.github.simonpham.sunshine.model.Weather;
import com.github.simonpham.sunshine.model.Wind;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Simon Pham on 3/30/19.
 * Email: simonpham.dn@gmail.com
 */
public class WeatherJsonHelper {

    public static List<Forecast> setWeatherDataFromJson(Context context, JSONObject json) {
        try {
            List<Forecast> forecasts = new ArrayList<>();
            JSONObject city = json.getJSONObject("city");
            JSONArray forecastList = json.getJSONArray("list");
            int i;

            String day = "";
            for (i = 0; i < forecastList.length(); i++) {
                JSONObject obj = forecastList.getJSONObject(i);

                long date = obj.getLong("dt");

                String displayDate = Utils.getDayName(context, date);
                if (displayDate.equals("Today")) {
                    displayDate = String.format("Today, %s", new SimpleDateFormat("MMMM dd", Locale.getDefault()).format(new Date(date * 1000)));
                }

                if (!day.equals(displayDate)) {
                    JSONObject objMain = obj.getJSONObject("main");
                    JSONObject objWeather = obj.getJSONArray("weather").getJSONObject(0);
                    JSONObject objClouds = obj.optJSONObject("clouds");
                    JSONObject objWind = obj.optJSONObject("wind");
                    JSONObject objRain = obj.optJSONObject("rain");
                    JSONObject objSnow = obj.optJSONObject("snow");

                    Main main = new Main(
                            objMain.getDouble("temp"),
                            objMain.getDouble("temp_min"),
                            objMain.getDouble("temp_max"),
                            objMain.getDouble("pressure"),
                            objMain.getDouble("sea_level"),
                            objMain.getDouble("grnd_level"),
                            objMain.getInt("humidity")
                    );

                    String desc = objWeather.getString("description");
                    String descUppercase = desc.substring(0, 1).toUpperCase().concat(desc.substring(1));

                    Weather weather = new Weather(
                            objWeather.getInt("id"),
                            objWeather.getString("main"),
                            descUppercase,
                            objWeather.getString("icon")
                    );

                    Clouds clouds = objClouds != null ?
                            new Clouds(objClouds.optInt("all", 0)) : null;
                    Wind wind = objWind != null ?
                            new Wind(objWind.optDouble("speed", 0.0),
                                    objWind.optDouble("deg", 0.0)) : null;
                    Rain rain = objRain != null ? new Rain(
                            objRain.optDouble("volumn", 0.0)) : null;
                    Snow snow = objSnow != null ? new Snow(
                            objSnow.optDouble("volumn", 0.0)) : null;

                    Forecast forecast = new Forecast(date, main, weather, clouds, wind, rain, snow, displayDate);

                    forecasts.add(forecast);
                }

                day = displayDate;
            }

            SingletonIntances.setForecasts(forecasts);
            return forecasts;
        } catch (Exception e) {
            // error
            return null;
        }
    }
}
