package com.github.simonpham.sunshine.worker;

import android.content.Context;

import com.github.simonpham.sunshine.R;
import com.github.simonpham.sunshine.SingletonIntances;
import com.github.simonpham.sunshine.data.RemoteFetch;
import com.github.simonpham.sunshine.model.Forecast;
import com.github.simonpham.sunshine.util.SharedPrefs;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.github.simonpham.sunshine.Consts.NOTIFICATION_CHANNEL_ID;
import static com.github.simonpham.sunshine.util.WeatherJsonHelper.setWeatherDataFromJson;

/**
 * Created by Simon Pham on 3/27/19.
 * Email: simonpham.dn@gmail.com
 */
public class NotificationWorker extends Worker {

    private Context context;

    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        SharedPrefs sharedPrefs = SingletonIntances.getSharedPrefs();
        Forecast forecast;

        JSONObject json = RemoteFetch.getJSON(context);

        if (json == null) {
            json = sharedPrefs.getLastWeatherData();
        }

        List<Forecast> forecasts = setWeatherDataFromJson(context, json);

        if (forecasts == null || forecasts.size() < 2) {
            return Result.failure();
        }

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String day = " weather today!";

        if (timeOfDay >= 21 && timeOfDay < 24) {
            // notify forecast for tomorrow
            forecast = forecasts.get(0);
            day = " weather tomorrow!";
        } else {
            // notify forecast for today
            forecast = forecasts.get(1);
        }

        String weatherCondition = forecast.getWeather().getDescription();
        String weatherTemp = String.format(Locale.US,
                "%.0f°", forecast.getMain().getTemp());

        String subText = sharedPrefs.getCity() +
                " • " +
                weatherTemp +
                sharedPrefs.getDisplayMetric();
        String contentTitle = weatherCondition + day;
        String contentText = "Let's go to the park and code!";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSubText(subText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        SingletonIntances.getNotiManager().notify(0, builder.build());
        return Result.success();
    }
}
