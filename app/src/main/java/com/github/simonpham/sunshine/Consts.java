package com.github.simonpham.sunshine;

/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */
public final class Consts {

    public static final String PACKAGE_NAME = "com.github.simonpham.sunshine";

    public static final String OPEN_WEATHER_MAP_API =
            "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=%s&appid=%s";
    public static final String API_KEY = "4c85ba37c1f1d610f0a2e51c4768d53d";

    public static final String DEFAULT_CITY = "Ho Chi Minh City, VN";

    public static final String KEY_UPDATE_ALL = "key_update_all";
    public static final String KEY_CURRENT_LOCATION = "current_location";
    public static final String KEY_USE_CELSIUS = "use_celsius";
    public static final String KEY_UPDATE_INTERVAL = "update_interval";
    public static final String KEY_LAST_WEATHER_DATA = "last_weather_data";

    public static final String EXTRA_FORECAST_ID = "FORECAST_ID";

    public static final String NOTIFICATION_CHANNEL_ID = PACKAGE_NAME + ".WEATHER_NOTIFICATION";
}
