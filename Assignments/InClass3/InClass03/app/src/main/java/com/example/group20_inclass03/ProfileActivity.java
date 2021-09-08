package com.example.group20_inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    TextView nameValue;
    TextView emailValue;
    TextView idValue;
    TextView departmentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        nameValue = findViewById(R.id.nameValue);
        emailValue = findViewById(R.id.emailValue);
        idValue = findViewById(R.id.idValue);
        departmentValue = findViewById(R.id.departmentValue);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.PROFILE_KEY)) {
            Profile profile = getIntent().getParcelableExtra(MainActivity.PROFILE_KEY);

            nameValue.setText(profile.name);
            emailValue.setText(profile.email);
            idValue.setText(profile.id);
            departmentValue.setText(profile.department);
        }

    }
}