package com.github.simonpham.sunshine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.simonpham.sunshine.R;
import com.github.simonpham.sunshine.model.Forecast;
import com.github.simonpham.sunshine.util.Utils;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private Context context;
    private List<Forecast> forecasts;

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvForecast;
        TextView tvHigh;
        TextView tvLow;
        View layout;

        ViewHolder(@NonNull View v) {
            super(v);
            this.layout = v;

            ivIcon = v.findViewById(R.id.ivIcon);
            tvForecast = v.findViewById(R.id.tvForecast);
            tvHigh = v.findViewById(R.id.tvHigh);
            tvLow = v.findViewById(R.id.tvLow);
        }
    }

    public ForecastAdapter(Context context, List<Forecast> forecasts) {
        this.context = context;
        this.forecasts = forecasts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Forecast forecast = forecasts.get(position);
        holder.tvForecast.setText(forecast.getWeather().getDescription());
        holder.tvHigh.setText(String.format(Locale.US, "%.0f°C", forecast.getMain().getTempMax()));
        holder.tvLow.setText(String.format(Locale.US, "%.0f°C", forecast.getMain().getTempMin()));
        holder.ivIcon.setImageResource(Utils.getArtResourceForWeatherCondition(forecast.getWeather().getId()));
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }
}
