package com.example.group20_hw03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.group20_hw03.databinding.CityRowItemBinding;

import java.util.List;

public class CitiesAdapter extends ArrayAdapter<Data.City> {

    public CitiesAdapter(@NonNull Context context, int resource, @NonNull List<Data.City> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            CityRowItemBinding binding = CityRowItemBinding.inflate(LayoutInflater.from(getContext()), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }

        Data.City city = getItem(position);
        CityRowItemBinding binding = (CityRowItemBinding) convertView.getTag();

        StringBuilder line = new StringBuilder();
        line.append(city.getCity());
        line.append(", ");
        line.append(city.getCountry());

        binding.textViewCity.setText(line);
        return convertView;
    }
}
