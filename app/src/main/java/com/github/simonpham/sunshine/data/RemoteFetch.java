package com.github.simonpham.sunshine.data;

import android.content.Context;

import com.github.simonpham.sunshine.SingletonIntances;
import com.github.simonpham.sunshine.util.SharedPrefs;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.github.simonpham.sunshine.Consts.API_KEY;
import static com.github.simonpham.sunshine.Consts.OPEN_WEATHER_MAP_API;

/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */

public class RemoteFetch {

    public static JSONObject getJSON(Context context) {
        SharedPrefs sharedPrefs = SingletonIntances.getSharedPrefs();
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, sharedPrefs.getCity(), sharedPrefs.getMetric(), API_KEY));
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuilder json = new StringBuilder(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if (data.getInt("cod") != 200) {
                return null;
            }

            return data;
        } catch (Exception e) {
            return null;
        }
    }
}