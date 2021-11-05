package com.example.group20_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.group20_hw05.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

/*
 * Assignment: Homework Assignment #05
 * File Name: Group20_HW05
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, RegisterFragment.RegisterFragmentListener, ForumsFragment.ForumsFragmentListener, CreateForumFragment.CreateForumFragmentListener {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rootView, new LoginFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rootView, new ForumsFragment())
                    .commit();
        }
    }


    @Override
    public void gotoRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegisterFragment())
                .commit();
    }

    @Override
    public void gotoForumsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ForumsFragment())
                .commit();
    }

    @Override
    public void gotoLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoCreateForumFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CreateForumFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoForumDetailsFragment(Forum forum) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ForumDetailsFragment.newInstance(forum))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack();
    }
}