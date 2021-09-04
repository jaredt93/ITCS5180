package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ImplicitIntentActivity extends AppCompatActivity {
    TextView textViewGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);
        setTitle("Implicit Intent Activity");

        // intents data passing using serializable objects
        textViewGreeting = findViewById(R.id.textViewGreeting);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.USER_KEY)) {
            User user = (User)getIntent().getSerializableExtra(MainActivity.USER_KEY);

            textViewGreeting.setText("Hello " + user.name);
        }

        // implicit intent
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}