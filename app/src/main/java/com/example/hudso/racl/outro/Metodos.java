package com.example.hudso.racl.outro;

import android.graphics.Color;

import com.example.hudso.racl.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    private Metodos() {
    }

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

        MarkerOptions markerOptions = new MarkerOptions().position(getSydneyPosition()).title("Marker in Sydney");

        map.addMarker(markerOptions);

        moveToMarkerOnSimpleView(map, markerOptions.getPosition());
    }

    /**
     * Mover a posição do mapa, para a view simples (plana) na posição indicada.
     *
     * @param position
     */
    public void moveToMarkerOnSimpleView(GoogleMap map, LatLng position) {
        map.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    /**
     * Mover a posição do mapa, para view customizada na posição indicada.
     *
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

    public MarkerOptions createCustomMarkerOptions(LatLng position, String title, int rDrawableIcon) {
        if (position == null) {
            position = new LatLng(-26.89951, -49.08399);
        }
        if (rDrawableIcon == 0) {
            rDrawableIcon = R.drawable.garbage_collector;
        }

        // Adicionar o marcador ao mapa
        MarkerOptions marker = new MarkerOptions();
        marker.position(position)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(rDrawableIcon))
                .snippet("(" + position.latitude + "/" + position.longitude + ")")
                .anchor(0.5f, 1)
                .zIndex(1.0f);

        return marker;
    }

    public Marker addMarkerToMap(MarkerOptions markerOptions, boolean locate) {
        GoogleMap map = SingletonTeste.getInstance().getMap();
        if (map == null) {
            return null;
        }

        // Adicionar marcador customizado ao mapa
        Marker marker = map.addMarker(markerOptions);

        if (locate) {
            CameraPosition liberty =
                    CameraPosition.builder().target(marker.getPosition())
                            // Zoom do mapa
                            .zoom(20)
                            //
                            .bearing(8)
                            // Inclinação
                            .tilt(45)
                            .build();

            map.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
        }

        return marker;
    }

    /**
     * Traçar a rota de pontos no mapa (trajeto).
     *
     * @param route
     */
    public void drawDynamicRoute(RouteBean route) {
        List<LatLng> decodedPath = new ArrayList<>(30);

        for (PointBean point : route.getPoints()) {
            decodedPath.add(new LatLng(point.getLat(), point.getLng()));
        }

        drawLinesRoute(decodedPath);
    }

    public void drawLinesRoute(List<LatLng> decodedPath) {
        try {
            System.out.println("Hudson - drawLinesRoute");

            GoogleMap map = SingletonTeste.getInstance().getMap();
            if (map == null || decodedPath.size() == 0) {
                return;
            }
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            // Desenha trajeto
            map.addPolyline(
                    new PolylineOptions()
                            .addAll(decodedPath)
                            .color(Color.parseColor("#3F51B5"))
                            .width(30)
            );
            System.out.println("Hudson - ROTA DESENHADA NO MAPA");

            addMarkerToMap(createCustomMarkerOptions(decodedPath.get(0), "Início", R.drawable.route_begin), true);
            addMarkerToMap(createCustomMarkerOptions(decodedPath.get(decodedPath.size() - 1), "Término", R.drawable.route_end), false);
            System.out.println("Hudson - ADICIONADOS MARCADORES DA ROTA NO MAPA");
        } catch (Exception e) {
            System.out.println("Hudson - ERRO - drawLinesRoute: " + e.getMessage());
        }
    }

    public List<LatLng> getDefaultListPoints() {
        List<LatLng> decodedPath = new ArrayList<>();

        decodedPath.add(new LatLng(-26.90029, -49.08502));
        decodedPath.add(new LatLng(-26.90015, -49.08482));
        decodedPath.add(new LatLng(-26.8999, -49.08452));
        decodedPath.add(new LatLng(-26.8997, -49.08425));
        decodedPath.add(new LatLng(-26.89951, -49.08399));
        decodedPath.add(new LatLng(-26.89951, -49.08399));
        decodedPath.add(new LatLng(-26.89923, -49.08422));
        decodedPath.add(new LatLng(-26.89916, -49.08427));
        decodedPath.add(new LatLng(-26.89893, -49.08439));
        decodedPath.add(new LatLng(-26.89873, -49.08451));
        decodedPath.add(new LatLng(-26.89867, -49.08453));
        decodedPath.add(new LatLng(-26.89867, -49.08453));
        decodedPath.add(new LatLng(-26.8989, -49.08484));

        return decodedPath;
    }
}
