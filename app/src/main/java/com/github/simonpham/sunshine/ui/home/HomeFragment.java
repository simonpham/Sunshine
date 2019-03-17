package com.github.simonpham.sunshine.ui.home;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.simonpham.sunshine.R;
import com.github.simonpham.sunshine.SingletonIntances;
import com.github.simonpham.sunshine.adapter.ForecastAdapter;
import com.github.simonpham.sunshine.data.RemoteFetch;
import com.github.simonpham.sunshine.model.Clouds;
import com.github.simonpham.sunshine.model.Forecast;
import com.github.simonpham.sunshine.model.Main;
import com.github.simonpham.sunshine.model.Rain;
import com.github.simonpham.sunshine.model.Snow;
import com.github.simonpham.sunshine.model.Weather;
import com.github.simonpham.sunshine.model.Wind;
import com.github.simonpham.sunshine.util.SharedPrefs;
import com.github.simonpham.sunshine.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */
public class HomeFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvForecast;

    private LinearLayout errorLayout;
    private TextView tvErrorMessage;
    private Button reloadButton;

    private ForecastAdapter adapter;
    private List<Forecast> forecasts = SingletonIntances.getForecasts();

    private Handler handler;

    SharedPrefs sharedPrefs = SingletonIntances.getSharedPrefs();

    public HomeFragment() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rvForecast = view.findViewById(R.id.rvForecast);

        errorLayout = view.findViewById(R.id.errorLayout);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);
        reloadButton = view.findViewById(R.id.reloadButton);

        adapter = new ForecastAdapter(this.getContext(), forecasts);
        rvForecast.setAdapter(adapter);
        updateWeatherData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateWeatherData();
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeRefreshLayout.setRefreshing(true);
                updateWeatherData();
            }
        });
    }

    private void updateWeatherData() {
        new Thread() {
            public void run() {
                JSONObject json = RemoteFetch.getJSON(getActivity());
                if (json == null) {
                    json = sharedPrefs.getLastWeatherData();
                }
                if (json == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            errorLayout.setVisibility(View.VISIBLE);
                            tvErrorMessage.setText("No network/City not found!");
                        }
                    });
                } else {
                    final JSONObject finalJson = json;
                    handler.post(new Runnable() {
                        public void run() {
                            sharedPrefs.setLastWeatherData(finalJson);
                            renderWeather(finalJson);
                        }
                    });
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }.start();
    }

    private void renderWeather(JSONObject json) {
        try {
            forecasts.clear();
            JSONObject city = json.getJSONObject("city");
            JSONArray forecastList = json.getJSONArray("list");
            int i;

            String day = "";
            for (i = 0; i < forecastList.length(); i++) {
                JSONObject obj = forecastList.getJSONObject(i);

                long date = obj.getLong("dt");

                String displayDate = Utils.getDayName(getContext(), date);
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

                    Clouds clouds = new Clouds(
                            objClouds != null ? objClouds.optInt("all", 0) : 0
                    );
                    Wind wind = new Wind(
                            objWind != null ? objWind.optDouble("speed", 0.0) : 0,
                            objWind != null ? objWind.optDouble("deg", 0.0) : 0
                    );
                    Rain rain = new Rain(
                            objRain != null ? objRain.optDouble("volumn", 0.0) : 0
                    );
                    Snow snow = new Snow(
                            objSnow != null ? objSnow.optDouble("volumn", 0.0) : 0
                    );
                    Forecast forecast = new Forecast(date, main, weather, clouds, wind, rain, snow, displayDate);

                    forecasts.add(forecast);
                }

                day = displayDate;
            }

            SingletonIntances.setForecasts(forecasts);

            errorLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            // error
            errorLayout.setVisibility(View.VISIBLE);
            tvErrorMessage.setText(String.format("Error: %s", e.getMessage()));
        }
    }
}
