package com.example.hudso.racl.util;

import android.graphics.Color;

import com.example.hudso.racl.R;
import com.example.hudso.racl.bean.RouteBean;
import com.example.hudso.racl.singleton.SingletonMaps;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * @author Hudson Henrique Lopes
 * @since 24/09/2017
 */

public class MapUtils {

    /**
     * Criação de marcador a ser adicionado no mapa.
     * @param position
     * @param title
     * @param rDrawableIcon
     * @return
     */
    public MarkerOptions createCustomMarkerOptions(LatLng position, String title, int rDrawableIcon) {
        if (rDrawableIcon == 0) {
            rDrawableIcon = R.drawable.garbage_collector;
        }

        // Criação de marcador para o mapa
        MarkerOptions marker = new MarkerOptions();
        marker.position(position).title(title).anchor(0.5f, 1).zIndex(1.0f);
        marker.icon(BitmapDescriptorFactory.fromResource(rDrawableIcon));
        marker.snippet("(" + position.latitude + "/" + position.longitude + ")");
        return marker;
    }

    /**
     * Adição do marcado no mapa.
     * @param markerOptions
     * @param locate
     * @return
     */
    public Marker addMarkerToMap(MarkerOptions markerOptions, boolean locate) {
        GoogleMap map = SingletonMaps.getInstance().getMap();
        if (map == null) {
            return null;
        }

        // Adicionar marcador customizado ao mapa
        Marker marker = map.addMarker(markerOptions);
        if (locate) {
            CameraPosition p = CameraPosition.builder().target(marker.getPosition())
                    .zoom(20).bearing(8).tilt(45).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(p));
        }
        return marker;
    }

    /**
     * Traçar a rota de pontos no mapa (trajeto).
     *
     * @param route
     */
    public void drawDynamicRoute(RouteBean route) {
        drawLinesRoute(route.getDrawPoints());
    }

    /**
     * Desenhar rotas no mapa.
     * @param decodedPath
     */
    protected void drawLinesRoute(List<LatLng> decodedPath) {
        try {
            GoogleMap map = SingletonMaps.getInstance().getMap();
            if (map == null || decodedPath.size() == 0) {
                return;
            }
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            // Desenha a rota no mapa
            map.addPolyline(
                    new PolylineOptions().addAll(decodedPath)
                            .color(Color.parseColor("#3F51B5"))
                            .width(30));

            // Cria e adiciona o marcador inicial da rota
            LatLng initialPosition = decodedPath.get(0);
            MarkerOptions moi = createCustomMarkerOptions(initialPosition, "Início", R.drawable.route_begin);
            addMarkerToMap(moi, true);

            // Cria e adiciona o marcador final da rota
            LatLng finalPosition = decodedPath.get(decodedPath.size() - 1);
            MarkerOptions moe = createCustomMarkerOptions(finalPosition, "Término", R.drawable.route_end);
            addMarkerToMap(moe, false);
        } catch (Exception e) {
            System.out.println("Erro durante a construção e inclusão da rota no mapa: " + e.getMessage());
        }
    }

//    public List<LatLng> getDefaultListPoints() {
//        List<LatLng> decodedPath = new ArrayList<>();
//
//        decodedPath.add(new LatLng(-26.90029, -49.08502));
//        decodedPath.add(new LatLng(-26.90015, -49.08482));
//        decodedPath.add(new LatLng(-26.8999, -49.08452));
//        decodedPath.add(new LatLng(-26.8997, -49.08425));
//        decodedPath.add(new LatLng(-26.89951, -49.08399));
//        decodedPath.add(new LatLng(-26.89951, -49.08399));
//        decodedPath.add(new LatLng(-26.89923, -49.08422));
//        decodedPath.add(new LatLng(-26.89916, -49.08427));
//        decodedPath.add(new LatLng(-26.89893, -49.08439));
//        decodedPath.add(new LatLng(-26.89873, -49.08451));
//        decodedPath.add(new LatLng(-26.89867, -49.08453));
//        decodedPath.add(new LatLng(-26.89867, -49.08453));
//        decodedPath.add(new LatLng(-26.8989, -49.08484));
//
//        return decodedPath;
//    }
}
