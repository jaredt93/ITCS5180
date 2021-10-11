package com.example.group20_hw03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.group20_hw03.databinding.FragmentCitiesBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CitiesFragment extends Fragment {
    FragmentCitiesBinding binding;
    ArrayList<Data.City> cities = new ArrayList<>();
    CitiesAdapter adapter;

    public CitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitiesBinding.inflate(inflater, container, false);
        getActivity().setTitle("Cities");

        retrieveCities();
        adapter = new CitiesAdapter(getContext(), R.layout.city_row_item, cities);
        binding.listView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mListener.gotoCurrentWeather(cities.get(position));
            }
        });
    }

    // Retrieves the cities
    private void retrieveCities() {
        for(Data.City city: Data.cities) {
            cities.add(city);
        }
    }

    // Listener
    CitiesFragment.CitiesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof CitiesFragment.CitiesListener) {
            mListener = (CitiesFragment.CitiesListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement CitiesListener"));
        }
    }

    public interface CitiesListener {
        void gotoCurrentWeather(Data.City city);
    }

}