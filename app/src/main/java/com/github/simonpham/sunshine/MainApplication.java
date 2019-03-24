package com.github.simonpham.sunshine;

import android.app.Application;

/**
 * Created by Simon Pham on 3/17/19.
 * Email: simonpham.dn@gmail.com
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SingletonIntances.init(this);
    }
}
