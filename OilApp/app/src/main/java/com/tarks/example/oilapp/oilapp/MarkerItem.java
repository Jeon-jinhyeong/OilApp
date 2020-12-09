package com.tarks.example.oilapp.oilapp;

/**
 * Created by jinhyeong on 2016-11-16.
 */

public class MarkerItem {


    static String lat;
    static String lon;
    static String price;

    public MarkerItem(String lat, String lon, String price) {
        this.lat = lat;
        this.lon = lon;
        this.price = price;
    }

    public static String getLat() { return lat; }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public static String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public static String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
