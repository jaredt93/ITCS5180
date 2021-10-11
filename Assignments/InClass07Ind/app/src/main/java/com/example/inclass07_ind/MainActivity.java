package com.example.inclass07_ind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.inclass07_ind.databinding.ActivityMainBinding;

/*
 * Assignment: Individual In Class Assignment #07
 * File Name: InClass07_Ind
 * Student Name: Jared Tamulynas
 */
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        
    }
}