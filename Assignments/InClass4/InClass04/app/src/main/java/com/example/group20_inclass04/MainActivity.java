package com.example.group20_inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 * Assignment: In Class Assignment #04
 * File Name: Group20_InClass04
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, RegisterFragment.RegisterListener, AccountFragment.AccountListener, UpdateAccountFragment.UpdateAccountListener {

    Button buttonCreate;
    DataServices.Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment(), "fragment")
                .commit();

    }


    @Override
    public void gotoRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new RegisterFragment(), "fragment")
                .commit();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment(), "fragment")
                .commit();
    }

    @Override
    public void gotoAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AccountFragment.newInstance(account), "fragment")
                .commit();
    }

    @Override
    public void popBack() {
        getSupportFragmentManager().popBackStack();
        gotoAccount();
    }

    @Override
    public void setAccount(DataServices.Account account) {
        this.account = account;
        AccountFragment.newInstance(account);
    }

    @Override
    public void gotoUpdateAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, UpdateAccountFragment.newInstance(account), "fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteAccount() {
        this.account = null;
    }

}