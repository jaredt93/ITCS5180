package com.example.group20_hw03;

public class Forecast {
    private String dateTime;
    private String weatherIcon;
    private double temp;
    private double tempMax;
    private double tempMin;
    private int humidity;
    private String description;

    public Forecast() {
        // Empty constructor
    }

    public Forecast(String dateTime, String weatherIcon, double temp, double tempMax, double tempMin, int humidity, String description) {
        this.dateTime = dateTime;
        this.weatherIcon = weatherIcon;
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.humidity = humidity;
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
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
}
