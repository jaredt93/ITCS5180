package com.example.group20_inclass10;

import androidx.fragment.app.FragmentActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.group20_inclass10.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Assignment: In Class Assignment #10
 * File Name: Group20_InClass10
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<LatLng> tripLatLong = new ArrayList<>();
        ArrayList<HashMap<String, Double>> tripPoints = new ArrayList<>();
        tripPoints= getJson();

        LatLng start = new LatLng(tripPoints.get(0).get("latitude"), tripPoints.get(0).get("longitude"));
        mMap.addMarker(new MarkerOptions().position(start).title("Start Location"));

        LatLng end = new LatLng(tripPoints.get(tripPoints.size() - 1).get("latitude"), tripPoints.get(tripPoints.size() - 1).get("longitude"));
        mMap.addMarker(new MarkerOptions().position(end).title("End Location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for(int i = 0; i < tripPoints.size(); i++) {
            Double latitude = tripPoints.get(i).get("latitude");
            Double longitude = tripPoints.get(i).get("longitude");
            tripLatLong.add(new LatLng(latitude, longitude));
            builder.include(new LatLng(latitude, longitude));
        }

        // Add polylines to the map.
        Polyline polylinePath = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(tripLatLong));

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
            }
        });

    }


    public ArrayList<HashMap<String, Double>> getJson() {
        Gson gson = new Gson();
        Trip trip = new Trip();

        // create a reader
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("trip.json"), "UTF-8"))) {
            trip = gson.fromJson(reader, Trip.class);
            Log.d("demo", "getData: " + trip.getTitle());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trip.points;
    }
}