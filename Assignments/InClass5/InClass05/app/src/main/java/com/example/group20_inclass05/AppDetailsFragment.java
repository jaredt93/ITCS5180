package com.example.group20_inclass05;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.group20_inclass05.databinding.FragmentAppCategoriesBinding;
import com.example.group20_inclass05.databinding.FragmentAppDetailsBinding;

import java.util.ArrayList;

public class AppDetailsFragment extends Fragment {

    FragmentAppDetailsBinding binding;

    ArrayList<String> genres = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private static final String ARG_PARAM_APP = "ARG_PARAM_APP";
    private DataServices.App app;

    public AppDetailsFragment() {
        // Required empty public constructor
    }

    public static AppDetailsFragment newInstance(DataServices.App app) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_APP, app);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            app = (DataServices.App) getArguments().getSerializable(ARG_PARAM_APP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppDetailsBinding.inflate(inflater, container, false);
        getActivity().setTitle("App Details");

        binding.textViewAppName.setText(app.name);
        binding.textViewArtistName.setText(app.artistName);
        binding.textViewReleaseDate.setText(app.releaseDate);

        genres = app.genres;
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, genres);
        binding.listView.setAdapter(adapter);

        return binding.getRoot();
    }
}