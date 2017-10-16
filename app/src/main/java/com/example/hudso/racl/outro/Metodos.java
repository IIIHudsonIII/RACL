package com.example.hudso.racl.outro;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudso on 24/09/2017.
 */

public class Metodos {

    private static Metodos instance;

    // Singleton
    private Metodos() {}

    public static Metodos getInstance() {
        if (instance == null) {
            instance = new Metodos();
        }
        return instance;
    }

    public LatLng getStatueOfLibertyPosition() {
        return new LatLng(40.689247, -74.044502);
    }

    public LatLng getSydneyPosition() {
        return new LatLng(-34, 151);
    }

    /**
     * Adicionar marcador na Estátua da Liberdade no mapa.
     */
    public void addStatueOfLibertyMarkerOnMap(GoogleMap map) {
//        // TODO Hudson - Descobrir por quê existe esta linha de comando
//        MapsInitializer.initialize(getBaseContext());

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(getStatueOfLibertyPosition());
        // Título do marcador
        markerOptions.title("Statue of liberty");
        // Descrição complementar do marcador
        markerOptions.snippet("I hope to go there");

        // Adicionar o marcador ao mapa
        map.addMarker(markerOptions);

        moveToMarkerOnCustomView(map, markerOptions.getPosition());
    }

    /**
     * Adicionar marcador em Sydney no mapa.
     */
    public void addSydneyMarkerOnMap(GoogleMap map) {
        // Add a marker in Sydney and move the camera
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        MarkerOptions markerOptions = new MarkerOptions()
                .position(getSydneyPosition())
                .title("Marker in Sydney");

        map.addMarker(markerOptions);

        moveToMarkerOnSimpleView(map, markerOptions.getPosition());
    }

    /**
     * Mover a posição do mapa, para a view simples (plana) na posição indicada.
     * @param position
     */
    public void moveToMarkerOnSimpleView(GoogleMap map, LatLng position) {
        map.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    /**
     * Mover a posição do mapa, para view customizada na posição indicada.
     * @param position
     */
    public void moveToMarkerOnCustomView(GoogleMap map, LatLng position) {
        // Customizar posição
        CameraPosition liberty =
                CameraPosition.builder().target(position)
                        // Zoom do mapa
                        .zoom(16)
                        //
                        .bearing(8)
                        // Inclinação
                        .tilt(45)
                        .build();

        map.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
    }


    public void drawDynamicRoute() {

        List<LatLng> decodedPath = new ArrayList<>();

        decodedPath.add(new LatLng(-26.90029, -49.08502));
        decodedPath.add(new LatLng(-26.90015,-49.08482));
        decodedPath.add(new LatLng(-26.8999,-49.08452));
        decodedPath.add(new LatLng(-26.8997,-49.08425));
        decodedPath.add(new LatLng(-26.89951,-49.08399));
        decodedPath.add(new LatLng(-26.89951,-49.08399));
        decodedPath.add(new LatLng(-26.89923,-49.08422));
        decodedPath.add(new LatLng(-26.89916,-49.08427));
        decodedPath.add(new LatLng(-26.89893,-49.08439));
        decodedPath.add(new LatLng(-26.89873,-49.08451));
        decodedPath.add(new LatLng(-26.89867,-49.08453));
        decodedPath.add(new LatLng(-26.89867,-49.08453));
        decodedPath.add(new LatLng(-26.8989,-49.08484));

        drawLinesRoute(decodedPath);
    }

    public void drawDynamicRoute(RouteBean route) {
        List<LatLng> decodedPath = new ArrayList<>(30);

        for (PointBean point : route.getPoints()) {
            decodedPath.add(new LatLng(point.getLat(), point.getLng()));
        }
        /**
         ArrayList<LatLng> markerPoints = new ArrayList<LatLng>();
         markerPoints.add(point);

         // Creating MarkerOptions
         MarkerOptions options = new MarkerOptions();

         // Setting the position of the marker
         options.position(point);
         **/

        drawLinesRoute(decodedPath);
    }

    public void drawLinesRoute(List<LatLng> decodedPath) {

        GoogleMap map = SingletonTeste.getInstance().getMap();
        if (map == null || decodedPath.size() == 0) {
            return;
        }
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Adicionar o marcador ao mapa
        MarkerOptions initialMarker = new MarkerOptions();
        initialMarker.position(decodedPath.get(0));
        map.addMarker(initialMarker);

        MarkerOptions finalMarker = new MarkerOptions();
        finalMarker.position(decodedPath.get(decodedPath.size()-1));
        map.addMarker(finalMarker);

        map.addPolyline(
                new PolylineOptions()
                        .addAll(decodedPath)
                        .color(Color.RED)
                        .width(10)
//                        .geodesic(true)
        );

        CameraPosition liberty =
                CameraPosition.builder().target(decodedPath.get(0))
                        // Zoom do mapa
                        .zoom(16)
                        //
                        .bearing(8)
                        // Inclinação
                        .tilt(45)
                        .build();

        map.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
    }

//    public void onMapClick(LatLng latLng) {
//        String text = "decodedPath.add(new LatLng(" + latLng.latitude + ", " + String.valueOf(latLng.longitude) + "));";
//        Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//
//        // Adicionar o marcador ao mapa
//        map.addMarker(markerOptions);
//
//        drawLinesRoute();
//
//        Log.println(Log.WARN, "Click ", text);
//    }
}
