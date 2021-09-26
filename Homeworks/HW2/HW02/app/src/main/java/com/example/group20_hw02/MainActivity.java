package com.example.group20_hw02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.group20_hw02.databinding.ActivityMainBinding;

/*
 * Assignment: Homework #02
 * File Name: Group20_HW02
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity implements UsersFragment.UsersListener, FilterByStateFragment.FilterByStateListener, SortFragment.SortListener {

    ActivityMainBinding binding;
    String state = "All States";
    String sortAttribute;
    Boolean ascending;
    final String USERS_FRAGMENT_TAG = "Fragment Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set opening fragment to UsersFragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, UsersFragment.newInstance(state, sortAttribute, ascending), USERS_FRAGMENT_TAG)
                .commit();
    }

    // Go to FilterByStateFragment and add UsersFragment to back stack
    @Override
    public void gotoFilterByState() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new FilterByStateFragment())
                .addToBackStack(null)
                .commit();
    }

    // Go to SortFragment and add UsersFragment to back stack
    @Override
    public void gotoSort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SortFragment())
                .addToBackStack(null)
                .commit();
    }

    // Show new UsersFragment and pop from back stack
    @Override
    public void filterUsers(String state) {
        this.state = state;
        getSupportFragmentManager().popBackStack();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UsersFragment.newInstance(state, sortAttribute, ascending), USERS_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void sortUsers(String sortAttribute, Boolean ascending) {
        this.sortAttribute = sortAttribute;
        this.ascending = ascending;
        getSupportFragmentManager().popBackStack();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UsersFragment.newInstance(state, sortAttribute, ascending), USERS_FRAGMENT_TAG)
                .commit();
    }

}