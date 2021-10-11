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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
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

        DecimalFormat df1 = new DecimalFormat(".#");

        binding.textViewDateTime.setText(forecast.getDateTime());
        String urlIcon = "https://openweathermap.org/img/wn/" + forecast.getWeatherIcon() +"@2x.png";
        Picasso.get().load(urlIcon).into(binding.imageViewWeatherIcon);
        binding.temp.setText(df1.format(forecast.getTemp()) + " °F");
        binding.tempMax.setText("Max: " + df1.format(forecast.getTempMax()) + " °F");
        binding.tempMin.setText("Min: " + df1.format(forecast.getTempMin()) + " °F");
        binding.humidity.setText("Humidity: " + forecast.getHumidity() + "%");
        binding.description.setText(forecast.getDescription());
        return convertView;
    }
}
