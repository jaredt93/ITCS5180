package com.example.group20_hw03;

import java.util.Locale;

public class Weather {
    private String weatherIcon;
    private double temp;
    private double tempMax;
    private double tempMin;
    private String description;
    private int humidity;
    private double windSpeed;
    private int windDegree;
    private int cloudiness;

    public Weather() {
        // Empty constructor
    }

    public Weather(String weatherIcon, double temp, double tempMax, double tempMin, String description, int humidity, double windSpeed, int windDegree, int cloudiness) {
        this.weatherIcon = weatherIcon;
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.cloudiness = cloudiness;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String capitalized;
        capitalized = description.substring(0,1).toUpperCase() + description.substring(1);

        if(description.contains(" ")) {
            int i = description.indexOf(" ");

            if(description.length() >= i +2) {
                capitalized = capitalized.substring(0, i + 1) + capitalized.substring(i + 1, i + 2).toUpperCase() + capitalized.substring(i + 2);
            }
        }

        this.description = capitalized;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(int windDegree) {
        this.windDegree = windDegree;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }
}
