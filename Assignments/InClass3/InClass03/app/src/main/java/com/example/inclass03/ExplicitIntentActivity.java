package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ExplicitIntentActivity extends AppCompatActivity {
    TextView textViewGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);
        setTitle("Explicit Intent Activity");

        // simple data passing using intents
        textViewGreeting = findViewById(R.id.textViewGreeting);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.NAME_KEY)) {
            String name = getIntent().getStringExtra(MainActivity.NAME_KEY);

            textViewGreeting.setText("Hello " + name);
        }

        // explicit intent
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}