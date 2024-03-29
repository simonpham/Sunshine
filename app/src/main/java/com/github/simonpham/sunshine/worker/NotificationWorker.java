package com.github.simonpham.sunshine.worker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.github.simonpham.sunshine.SingletonIntances;
import com.github.simonpham.sunshine.data.RemoteFetch;
import com.github.simonpham.sunshine.model.Forecast;
import com.github.simonpham.sunshine.ui.MainActivity;
import com.github.simonpham.sunshine.util.SharedPrefs;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.github.simonpham.sunshine.Consts.NOTIFICATION_CHANNEL_ID;
import static com.github.simonpham.sunshine.util.Utils.getIconResourceForWeatherCondition;
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

        if (forecasts == null || forecasts.isEmpty()) {
            return Result.failure();
        }

        forecast = forecasts.get(0);

        int weatherId = forecast.getWeather().getId();
        String weatherCondition = forecast.getWeather().getDescription();
        String weatherTemp = String.format(Locale.US,
                "%.0f°", forecast.getMain().getTemp());

        String contentTitle = weatherTemp +
                " in " +
                sharedPrefs.getCity();

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(getIconResourceForWeatherCondition(weatherId))
                .setContentTitle(contentTitle)
                .setContentText(weatherCondition)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        SingletonIntances.getNotiManager().notify(0, builder.build());
        return Result.success();
    }
}
