package com.example.group20_hw04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

/*
 * Assignment: Homework #04
 * File Name: Group20_HW04
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, SignUpFragment.SignUpFragmentListener, PostsListFragment.PostsListListener, CreatePostFragment.CreatePostListener {
    UserToken mUserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
        String fullname = sharedPref.getString("fullname",null);
        String userId = sharedPref.getString("userId", null);

        if(token == null || fullname == null || userId == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rootView, new LoginFragment())
                    .commit();
        } else {
            mUserToken = new UserToken(token, fullname, userId);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rootView, PostsListFragment.newInstance(mUserToken))
                    .commit();
        }
    }

    @Override
    public void createAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SignUpFragment())
                .commit();
    }

    @Override
    public void loginSuccessfulGotoPosts(UserToken userToken) {
        mUserToken = userToken;
        setSharedPreferences();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, PostsListFragment.newInstance(mUserToken), "postsList-Fragment")
                .commit();
    }

    @Override
    public void cancelSignUp() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void registerSuccessfulGotoPosts(UserToken userToken) {
        mUserToken = userToken;
        setSharedPreferences();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, PostsListFragment.newInstance(mUserToken), "postsList-Fragment")
                .commit();
    }

    // Add user information to shared preferences
    private void setSharedPreferences() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(mUserToken != null) {
            editor.putString("token", mUserToken.getToken());
            editor.putString("fullname", mUserToken.getFullname());
            editor.putString("userId", mUserToken.getUserId());
        } else {
            editor = sharedPref.edit().clear();
        }

        editor.apply();
    }


    @Override
    public void gotoLogin() {
        mUserToken = null;
        setSharedPreferences();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoCreatePost(UserToken mUserToken) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, CreatePostFragment.newInstance(mUserToken))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBack() {
        PostsListFragment fragment = (PostsListFragment)getSupportFragmentManager().findFragmentByTag("postsList-Fragment");
        getSupportFragmentManager().popBackStack();

        if(fragment != null) {
            fragment.getPosts("1");
        }
    }
}