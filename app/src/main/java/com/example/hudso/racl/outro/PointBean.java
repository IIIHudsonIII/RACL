package com.example.hudso.racl.outro;

/**
 * Created by hudso on 16/10/2017.
 */

public class PointBean {
    private double lat;
    private double lng;
    private String description;

    public PointBean(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        this.description = "";
    }

    public PointBean(double lat, double lng, String description) {
        this.lat = lat;
        this.lng = lng;
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        if (description == null) {

        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
