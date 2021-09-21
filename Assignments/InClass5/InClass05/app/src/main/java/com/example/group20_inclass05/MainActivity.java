package com.example.group20_inclass05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.group20_inclass05.databinding.ActivityMainBinding;

/*
 * Assignment: In Class Assignment #05
 * File Name: Group20_InClass05
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity implements AppCategoriesFragment.AppCategoriesListener, AppsListFragment.AppsListListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new AppCategoriesFragment(), "fragment")
                .commit();
    }


    @Override
    public void gotoAppsList(String category) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, AppsListFragment.newInstance(category), "fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoAppDetails(DataServices.App app) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, AppDetailsFragment.newInstance(app), "fragment")
                .addToBackStack(null)
                .commit();
    }

}