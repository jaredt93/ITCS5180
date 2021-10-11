package com.example.group20_hw03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.group20_hw03.databinding.ActivityMainBinding;

/*
 * Assignment: Homework #03
 * File Name: Group20_HW03
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesListener, CurrentWeatherFragment.CurrentWeatherListener {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CitiesFragment(), "cities-fragment")
                .commit();
    }

    @Override
    public void gotoCurrentWeather(Data.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, CurrentWeatherFragment.newInstance(city), "currentWeather-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoWeatherForecast(Data.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, WeatherForecastFragment.newInstance(city), "WeatherForecast-fragment")
                .addToBackStack(null)
                .commit();
    }
}