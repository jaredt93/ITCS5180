package com.example.group20_hw03;

public class Forecast {
    private String dateTime;
    private String weatherIcon;
    private String temp;
    private String tempMax;
    private String tempMin;
    private String humidity;
    private String description;

    public Forecast(String dateTime, String weatherIcon, String temp, String tempMax, String tempMin, String humidity, String description) {
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

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getTemp() {
        return temp;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

}
