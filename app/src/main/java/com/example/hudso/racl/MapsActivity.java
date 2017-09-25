package com.example.hudso.racl;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

//        // TODO Hudson - Adicionar marcador em Sydney, mapa simples (plano)
//        addSydneyMarkerOnMap();

//        // TODO Hudson - Adicionar marcador na Estátua da Liberdade, mapa customizado
//        addStatueOfLibertyMarkerOnMap();
//
        map.setOnMapClickListener(
                new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MapsActivity.this.onMapClick(latLng);
                    }
                }
        );

        drawLinesRoute();

//        Metodos.moveToMarkerOnSimpleView(map, new LatLng(-26.88665791301889, -49.09618478268385));
//        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        map.setMinZoomPreference(5);
//        map.getUiSettings().setAllGesturesEnabled(true);
//        map.setBuildingsEnabled(true);
//
//        CameraPosition liberty =
//                CameraPosition.builder().target(new LatLng(-26.88665791301889, -49.09618478268385))
//                        // Zoom do mapa
//                        .zoom(16)
//                        //
//                        .bearing(8)
//                        // Inclinação
//                        .tilt(45)
//                        .build();
//
//        map.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));

    }

    public void drawDynamicRoute() {
        /**
        ArrayList<LatLng> markerPoints = new ArrayList<LatLng>();
        markerPoints.add(point);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(point);
         **/
    }

    public void drawLinesRoute() {
        List<LatLng> decodedPath = new ArrayList<>();

//        decodedPath.add(new LatLng(-26.88665791301889, -49.09618478268385));
//        decodedPath.add(new LatLng(-26.886526637132196, -49.09517459571362));
//        decodedPath.add(new LatLng(-26.886891159092812, -49.09469179809094));
//        decodedPath.add(new LatLng(-26.883331711814563, -49.0956449881196));
//        decodedPath.add(new LatLng(-26.88331705872923, -49.09618478268385));
//        decodedPath.add(new LatLng(-26.884531763149415, -49.0958783403039));
//        decodedPath.add(new LatLng(-26.88361610091067, -49.09616868942977));
//        decodedPath.add(new LatLng(-26.885169911348147, -49.09624245017767));
//        decodedPath.add(new LatLng(-26.886304155554605, -49.09699078649282));
//        decodedPath.add(new LatLng(-26.885434559380023, -49.095554798841476));
//        decodedPath.add(new LatLng(-26.886654324613982, -49.09627664834262));

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

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(decodedPath.get(0));

        // Adicionar o marcador ao mapa
        map.addMarker(markerOptions);
        map.addMarker(markerOptions);

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

    @Override
    public void onMapClick(LatLng latLng) {
        String text = "decodedPath.add(new LatLng(" + latLng.latitude + ", " + String.valueOf(latLng.longitude) + "));";
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        // Adicionar o marcador ao mapa
        map.addMarker(markerOptions);

        drawLinesRoute();

        Log.println(Log.WARN, "Click ", text);
    }
}
