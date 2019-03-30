package com.github.simonpham.sunshine.util;

import android.content.Context;
import android.content.Intent;

import com.github.simonpham.sunshine.R;
import com.github.simonpham.sunshine.SingletonIntances;
import com.github.simonpham.sunshine.model.Forecast;
import com.github.simonpham.sunshine.worker.NotificationWorker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */

public class Utils {

    public static int julianDay(long dateInMillis) {
        Date date = new Date(dateInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int a = (14 - month) / 12;
        int y = year + 4800 - a;
        int m = month + 12 * a - 3;
        return day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
    }

    /**
     * Given a day, returns just the name to use for that day.
     * E.g "today", "tomorrow", "wednesday".
     *
     * @param context      Context to use for resource localization
     * @param dateInMillis The date in milliseconds
     * @return
     */
    public static String getDayName(Context context, long dateInMillis) {
        // If the date is today, return the localized version of "Today" instead of the actual
        // day name.
        long currentDate = System.currentTimeMillis();
        int julianDay = julianDay(dateInMillis * 1000);
        int currentJulianDay = julianDay(currentDate);
        if (julianDay == currentJulianDay) {
            return context.getString(R.string.today);
        } else if (julianDay == currentJulianDay + 1) {
            return context.getString(R.string.tomorrow);
        } else {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            return dayFormat.format(dateInMillis * 1000);
        }
    }

    /**
     * Helper method to provide the icon resource id according to the weather condition id returned
     * by the OpenWeatherMap call.
     *
     * @param weatherId from OpenWeatherMap API response
     * @return resource id for the corresponding icon. -1 if no relation is found.
     */
    public static int getIconResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // https://openweathermap.org/weather-conditions
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherId == 771 || weatherId == 781) {
            return R.drawable.ic_storm;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear;
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        }
        return -1;
    }

    /**
     * Helper method to provide the art resource id according to the weather condition id returned
     * by the OpenWeatherMap call.
     *
     * @param weatherId from OpenWeatherMap API response
     * @return resource id for the corresponding icon. -1 if no relation is found.
     */
    public static int getArtResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // https://openweathermap.org/weather-conditions
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        } else if (weatherId == 511) {
            return R.drawable.art_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.art_fog;
        } else if (weatherId == 771 || weatherId == 781) {
            return R.drawable.art_storm;
        } else if (weatherId == 800) {
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds;
        }
        return -1;
    }

    public static int getStringResourceForTodayWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // https://openweathermap.org/weather-conditions
        if (weatherId >= 200 && weatherId <= 232) {
            return R.string.notification_content_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.string.notification_content_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.string.notification_content_rain;
        } else if (weatherId == 511) {
            return R.string.notification_content_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.string.notification_content_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.string.notification_content_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.string.notification_content_fog;
        } else if (weatherId == 771 || weatherId == 781) {
            return R.string.notification_content_storm;
        } else if (weatherId == 800) {
            return R.string.notification_content_clear;
        } else if (weatherId == 801) {
            return R.string.notification_content_light_cloud;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.string.notification_content_cloudy;
        }
        return -1;
    }

    public static int getStringResourceForTomorrowWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // https://openweathermap.org/weather-conditions
        if (weatherId >= 200 && weatherId <= 232) {
            return R.string.notification_content_storm_tomorrow;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.string.notification_content_light_rain_tomorrow;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.string.notification_content_rain_tomorrow;
        } else if (weatherId == 511) {
            return R.string.notification_content_snow_tomorrow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.string.notification_content_rain_tomorrow;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.string.notification_content_snow_tomorrow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.string.notification_content_fog_tomorrow;
        } else if (weatherId == 771 || weatherId == 781) {
            return R.string.notification_content_storm_tomorrow;
        } else if (weatherId == 800) {
            return R.string.notification_content_clear_tomorrow;
        } else if (weatherId == 801) {
            return R.string.notification_content_light_cloud_tomorrow;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.string.notification_content_cloudy_tomorrow;
        }
        return -1;
    }

    public static void shareForecast(Context context, Forecast forecast) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, forecast.toString());
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public static void setupNotificationRequest() {
        SharedPrefs sharedPrefs = SingletonIntances.getSharedPrefs();

        PeriodicWorkRequest notificationRequest = new PeriodicWorkRequest
                .Builder(NotificationWorker.class, sharedPrefs.getUpdateInterval(), TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance().cancelAllWork();
        WorkManager.getInstance().enqueue(notificationRequest);
    }
}
