package com.github.simonpham.sunshine.ui.home;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.simonpham.sunshine.R;
import com.github.simonpham.sunshine.adapter.ForecastAdapter;
import com.github.simonpham.sunshine.model.Forecast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */
public class HomeFragment extends Fragment {

    private RecyclerView rvForecast;
    private ForecastAdapter adapter;
    private List<Forecast> forecasts = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
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

        rvForecast = view.findViewById(R.id.rvForecast);

        adapter = new ForecastAdapter(this.getContext(), forecasts);
        rvForecast.setAdapter(adapter);
    }
}
