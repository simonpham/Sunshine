package com.github.simonpham.sunshine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.simonpham.sunshine.R;
import com.github.simonpham.sunshine.model.ItemInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastDetailsAdapter extends RecyclerView.Adapter<ForecastDetailsAdapter.ViewHolder> {

    private Context context;
    private List<ItemInfo> forecastDetails;

    public ForecastDetailsAdapter(Context context, List<ItemInfo> forecastDetails) {
        this.context = context;
        this.forecastDetails = forecastDetails;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCaption;
        TextView tvValue;
        View layout;

        ViewHolder(@NonNull View v) {
            super(v);
            this.layout = v;

            tvCaption = v.findViewById(R.id.tvCaption);
            tvValue = v.findViewById(R.id.tvValue);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_detail_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCaption.setText(forecastDetails.get(position).caption);
        holder.tvValue.setText(forecastDetails.get(position).value);
    }

    @Override
    public int getItemCount() {
        return forecastDetails.size();
    }
}
