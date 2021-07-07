package com.github.simonpham.sunshine.ui.home;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.simonpham.sunshine.R;
import com.github.simonpham.sunshine.SingletonIntances;
import com.github.simonpham.sunshine.adapter.ForecastAdapter;
import com.github.simonpham.sunshine.data.RemoteFetch;
import com.github.simonpham.sunshine.model.Forecast;
import com.github.simonpham.sunshine.util.SharedPrefs;

import org.json.JSONObject;

import java.util.List;

import static com.github.simonpham.sunshine.util.WeatherJsonHelper.setWeatherDataFromJson;


/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */
public class HomeFragment extends Fragment {

    private HomeFragment context;

    private Toolbar toolbar;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvForecast;

    private LinearLayout errorLayout;
    private TextView tvErrorMessage;
    private Button reloadButton;

    private ForecastAdapter adapter;
    private List<Forecast> forecasts = SingletonIntances.getForecasts();

    private Handler handler;

    private SharedPrefs sharedPrefs = SingletonIntances.getSharedPrefs();

    public HomeFragment() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);

        context = this;

        toolbar = view.findViewById(R.id.toolBar);
        toolbar.setOverflowIcon(ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_dots_vertical_24dp, null));

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rvForecast = view.findViewById(R.id.rvForecast);

        errorLayout = view.findViewById(R.id.errorLayout);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);
        reloadButton = view.findViewById(R.id.reloadButton);

        swipeRefreshLayout.setRefreshing(true);

        adapter = new ForecastAdapter(this.getContext(), forecasts);
        rvForecast.setAdapter(adapter);

        JSONObject lastWeatherData = sharedPrefs.getLastWeatherData();
        if (lastWeatherData != null) {
            renderWeather(lastWeatherData);
        } else {
            updateWeatherData();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateWeatherData();
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWeatherData();
            }
        });
    }

    private void updateWeatherData() {
        swipeRefreshLayout.setRefreshing(true);
        new Thread() {
            public void run() {
                JSONObject json = RemoteFetch.getJSON(getActivity());
                if (json == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            showError(context.getString(R.string.message_error_empty_response));
                        }
                    });
                } else {
                    final JSONObject finalJson = json;
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(finalJson);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json) {
        List<Forecast> forecasts = setWeatherDataFromJson(getContext(), json);
        if (forecasts != null && !forecasts.isEmpty()) {
            errorLayout.setVisibility(View.GONE);
            adapter.setData(forecasts);
            swipeRefreshLayout.setRefreshing(false);
        } else {
            showError(context.getString(R.string.message_error_json));
        }
    }

    private void showError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        errorLayout.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(message);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                Navigation.findNavController(rvForecast).navigate(R.id.actionShowPrefs);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
