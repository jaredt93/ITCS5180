package com.example.group20_inclass05;

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
import android.widget.TextView;

import com.example.group20_inclass05.databinding.FragmentAppCategoriesBinding;

import java.util.ArrayList;

public class AppCategoriesFragment extends Fragment {

    FragmentAppCategoriesBinding binding;

    ArrayList<String> categories = new ArrayList<>();
    ArrayAdapter<String> adapter;

    public AppCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppCategoriesBinding.inflate(inflater, container, false);
        getActivity().setTitle("App Categories");

        categories = DataServices.getAppCategories();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, categories);
        binding.listView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mListener.gotoAppsList(categories.get(position));
            }
        });
    }

    AppCategoriesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof AppCategoriesListener) {
            mListener = (AppCategoriesListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement AppCategoriesListener"));
        }
    }

    public interface AppCategoriesListener {
        void gotoAppsList(String category);
    }

}