package com.example.hudso.racl;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
        mMap = googleMap;

//        // TODO Hudson - Adicionar marcador em Sydney, mapa simples (plano)
//        addSydneyMarkerOnMap();

//        // TODO Hudson - Adicionar marcador na Estátua da Liberdade, mapa customizado
//        addStatueOfLibertyMarkerOnMap();
        CameraPosition liberty =
                CameraPosition.builder().target(new LatLng(40.689247, -74.044502))
                        .zoom(20)
                        .bearing(8)
                        .tilt(45)
                        .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));

    }

    /**
     * Adicionar marcador na Estátua da Liberdade no mapa.
     */
    private void addStatueOfLibertyMarkerOnMap() {
        MapsInitializer.initialize(getBaseContext());

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        LatLng latLngStatue = new LatLng(40.689247, -74.044502);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLngStatue);
        // Título do marcador
        markerOptions.title("Statue of liberty");
        // Descrição complementar do marcador
        markerOptions.snippet("I hope to go there");

        // Adicionar o marcador ao mapa
        mMap.addMarker(markerOptions);

        moveToMarkerOnCustomView(latLngStatue);
    }

    /**
     * Adicionar marcador em Sydney no mapa.
     */
    private void addSydneyMarkerOnMap() {
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        moveToMarkerOnSimpleView(sydney);
    }

    /**
     * Mover a posição do mapa, para a view simples (plana) na posição indicada.
     * @param position
     */
    private void moveToMarkerOnSimpleView(LatLng position) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    /**
     * Mover a posição do mapa, para view customizada na posição indicada.
     * @param position
     */
    private void moveToMarkerOnCustomView(LatLng position) {
        CameraPosition liberty =
                CameraPosition.builder().target(position)
                        .zoom(16)
                        .bearing(8)
                        .tilt(45)
                        .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
    }
}
