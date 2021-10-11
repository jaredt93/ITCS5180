package com.example.group20_hw03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.group20_hw03.databinding.CityRowItemBinding;
import com.example.group20_hw03.databinding.ForecastRowItemBinding;

import java.util.List;

public class ForecastAdapter extends ArrayAdapter<Forecast> {

    public ForecastAdapter(@NonNull Context context, int resource, @NonNull List<Forecast> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            ForecastRowItemBinding binding = ForecastRowItemBinding.inflate(LayoutInflater.from(getContext()), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }

        Forecast forecast = getItem(position);
        ForecastRowItemBinding binding = (ForecastRowItemBinding) convertView.getTag();

        //binding.imageViewWeatherIcon.setImageResource(null);
        binding.textViewDateTime.setText(forecast.getDateTime());
        binding.temp.setText(forecast.getTemp());
        binding.tempMax.setText(forecast.getTempMax());
        binding.tempMin.setText(forecast.getTempMin());
        binding.humidity.setText(forecast.getHumidity());
        binding.description.setText(forecast.getDescription());
        return convertView;
    }
}
