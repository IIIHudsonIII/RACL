package com.example.hudso.racl.outro;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
}
