package com.example.demonetworking.models; // Thay bằng tên package của bạn

public class Weather {
    private String time;
    private String temp;
    private String wind;
    private String iconUrl;

    public Weather(String time, String temp, String wind, String iconUrl) {
        this.time = time;
        this.temp = temp;
        this.wind = wind;
        this.iconUrl = iconUrl;
    }

    public String getTime() { return time; }
    public String getTemp() { return temp; }
    public String getWind() { return wind; }
    public String getIconUrl() { return iconUrl; }
}