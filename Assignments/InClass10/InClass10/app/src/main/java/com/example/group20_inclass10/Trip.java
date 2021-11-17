package com.example.group20_inclass10;

import java.util.ArrayList;
import java.util.HashMap;

public class Trip {
    ArrayList<HashMap<String, Double>> points = new ArrayList<>();
    String title;

    public ArrayList<HashMap<String, Double>> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<HashMap<String, Double>> points) {
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
