package com.example.hudso.racl;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hudso.racl.outro.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class FirstMapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                FirstMapFragment.this.onMapReady(mMap);
            }
        });

        return rootView;

    }

    /**
     * Manipulates the mapView2 once available.
     * This callback is triggered when the mapView2 is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMapClickListener(
                new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        FirstMapFragment.this.onMapClick(latLng);
                    }
                }
        );

        drawLinesRoute();

//        Metodos.moveToMarkerOnSimpleView(mapView2, new LatLng(-26.88665791301889, -49.09618478268385));
//        mapView2.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mapView2.setMinZoomPreference(5);
//        mapView2.getUiSettings().setAllGesturesEnabled(true);
//        mapView2.setBuildingsEnabled(true);
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
//        mapView2.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));

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


    public List<LatLng> getRouteLines(String idRoute) {
        return null;
    }

    public void drawLinesRoute() {
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

    //@Override
    public void onMapClick(LatLng latLng) {
        String text = "decodedPath.add(new LatLng(" + latLng.latitude + ", " + String.valueOf(latLng.longitude) + "));";
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        // Adicionar o marcador ao mapa
        map.addMarker(markerOptions);

        drawLinesRoute();

        Log.println(Log.WARN, "Click ", text);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}