package com.github.simonpham.sunshine.ui.detail;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.simonpham.sunshine.R;
import com.github.simonpham.sunshine.SingletonIntances;
import com.github.simonpham.sunshine.adapter.ForecastDetailsAdapter;
import com.github.simonpham.sunshine.model.ItemInfo;
import com.github.simonpham.sunshine.model.Forecast;
import com.github.simonpham.sunshine.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Simon Pham on 3/10/19.
 * Email: simonpham.dn@gmail.com
 */
public class DetailFragment extends Fragment {

    private ForecastDetailsAdapter adapter;
    private List<ItemInfo> forecastDetails = new ArrayList<>();

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolBar);

        RecyclerView rvForecastDetail = view.findViewById(R.id.rvForecastDetail);
        ImageView ivIcon = view.findViewById(R.id.ivIcon);
        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvForecast = view.findViewById(R.id.tvForecast);
        TextView tvHigh = view.findViewById(R.id.tvHigh);
        TextView tvLow = view.findViewById(R.id.tvLow);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });


        int forecastId = Objects.requireNonNull(getArguments()).getInt("forecastId", 0);
        Forecast forecast = SingletonIntances.getForecasts().get(forecastId);

        String displayDate = Utils.getDayName(getContext(), forecast.getDate());
        toolbar.setTitle(String.format("%s, %s", displayDate, new SimpleDateFormat("MMMM dd", Locale.getDefault()).format(new Date(forecast.getDate() * 1000))));
        tvForecast.setText(forecast.getWeather().getDescription());
        tvHigh.setText(String.format(Locale.US, "%.0f°", forecast.getMain().getTempMax()));
        tvLow.setText(String.format(Locale.US, "%.0f°", forecast.getMain().getTempMin()));
        ivIcon.setImageResource(Utils.getArtResourceForWeatherCondition(forecast.getWeather().getId()));

        forecastDetails.add(new ItemInfo("Humidity", forecast.getMain().getHumidity() + "%"));
        forecastDetails.add(new ItemInfo("Pressure", forecast.getMain().getPressure() + "hPa"));

        if (forecast.getClouds() != null) {
            forecastDetails.add(new ItemInfo("Clouds", forecast.getClouds().getAll() + "%"));
        }
        if (forecast.getWind() != null) {
            forecastDetails.add(new ItemInfo("Wind", forecast.getWind().getDeg() + "° - " + forecast.getWind().getSpeed() + "m/s"));
        }
        if (forecast.getRain() != null) {
            forecastDetails.add(new ItemInfo("Rain", forecast.getRain().getVolumn() + "mm"));
        }
        if (forecast.getSnow() != null) {
            forecastDetails.add(new ItemInfo("Snow", forecast.getSnow().getVolumn() + "mm"));
        }

        adapter = new ForecastDetailsAdapter(getContext(), forecastDetails);
        rvForecastDetail.setAdapter(adapter);
    }
}
