package com.example.week11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up cloud storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
    }
}