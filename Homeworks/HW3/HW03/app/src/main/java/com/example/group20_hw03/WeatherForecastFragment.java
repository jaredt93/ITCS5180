package com.example.group20_hw03;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group20_hw03.databinding.FragmentWeatherForecastBinding;

import java.util.ArrayList;

public class WeatherForecastFragment extends Fragment {
    FragmentWeatherForecastBinding binding;
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private Data.City city;
    ArrayList<Forecast> forecasts = new ArrayList<>();
    ForecastAdapter adapter;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(Data.City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (Data.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        getActivity().setTitle("Weather Forecast");

        adapter = new ForecastAdapter(getContext(), R.layout.forecast_row_item, forecasts);
        binding.listView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewCity.setText(city.toString());
    }

    // Retrieves the forecasts
    private void retrieveForecasts() {

    }

}