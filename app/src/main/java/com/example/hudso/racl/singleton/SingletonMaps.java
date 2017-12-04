package com.example.hudso.racl.singleton;

import com.example.hudso.racl.bean.PointBean;
import com.example.hudso.racl.bean.RouteBean;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class SingletonMaps {

    private static SingletonMaps instance;

    private SingletonMaps() {
    }

    public static SingletonMaps getInstance() {
        if (instance == null) {
            instance = new SingletonMaps();
        }
        return instance;
    }

    private RouteBean route;
    private GoogleMap map;
    private Marker markerCollector;
    private PointBean pointToLocate;

    public void setPointToLocate(PointBean pointToLocate) {
        this.pointToLocate = pointToLocate;
    }

    public PointBean getPointToLocate() {
        return pointToLocate;
    }

    public void setRoute(RouteBean route) {
        this.route = route;
    }

    public RouteBean getRoute() {
        return route;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public GoogleMap getMap() {
        return map;
    }

    public void setMarkerCollector(Marker markerCollector) {
        this.markerCollector = markerCollector;
    }

    public Marker getMarkerCollector() {
        return markerCollector;
    }
}
