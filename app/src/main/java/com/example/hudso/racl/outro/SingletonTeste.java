package com.example.hudso.racl.outro;

import com.example.hudso.racl.bean.RouteBean;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by hudso on 16/10/2017.
 */

public class SingletonTeste {

    private static SingletonTeste instance;

    private SingletonTeste() {
    }

    public static SingletonTeste getInstance() {
        if (instance == null) {
            instance = new SingletonTeste();
        }
        return instance;
    }

    private RouteBean route;
    private GoogleMap map;
    private Marker markerCollector;

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
