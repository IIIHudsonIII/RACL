package com.example.hudso.racl.outro;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by hudso on 16/10/2017.
 */

public class SingletonTeste {

    private static SingletonTeste instance;

    private SingletonTeste() {}

    public static SingletonTeste getInstance() {
        if (instance == null) {
            instance = new SingletonTeste();
        }
        return instance;
    }

    private RouteBean route;
    private GoogleMap map;

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
}
