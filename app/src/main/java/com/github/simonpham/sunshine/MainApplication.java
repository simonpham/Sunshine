package com.github.simonpham.sunshine;

import android.app.Application;

import com.github.simonpham.sunshine.worker.NotificationWorker;

import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

/**
 * Created by Simon Pham on 3/17/19.
 * Email: simonpham.dn@gmail.com
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SingletonIntances.init(this);

        PeriodicWorkRequest notificationRequest = new PeriodicWorkRequest
                .Builder(NotificationWorker.class, 15, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance().cancelAllWork();
        WorkManager.getInstance().enqueue(notificationRequest);
    }
}
