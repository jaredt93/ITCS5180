package com.example.group20_inclass07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.group20_inclass07.databinding.ActivityMainBinding;

import okhttp3.OkHttpClient;

/*
 * Assignment: In Class Assignment #07
 * File Name: Group20_InClass07
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity implements ContactsListFragment.ContactsListListener, NewContactFragment.NewContactListener {
    ActivityMainBinding binding;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new ContactsListFragment(), "tag")
                .commit();
    }

    @Override
    public void gotoContactDetails(Contact contact) {
        this.contact = contact;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ContactDetailsFragment.newInstance(contact))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoNewContact() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new NewContactFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoContactsList() {
        ContactsListFragment fragment = (ContactsListFragment) getSupportFragmentManager().findFragmentByTag("tag");

        if(fragment != null) {
            fragment.getContacts();
        }

        getSupportFragmentManager().popBackStack();
    }

}