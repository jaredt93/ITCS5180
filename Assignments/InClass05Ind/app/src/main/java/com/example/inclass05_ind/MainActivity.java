package com.example.inclass05_ind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ProfileForumFragment.ProfileForumListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new ProfileForumFragment(), "fragment")
                .commit();
    }

    @Override
    public void gotoHome(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, HomeFragment.newInstance(user), "fragment")
                .addToBackStack(null)
                .commit();
    }
}