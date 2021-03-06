package com.example.hudso.racl.service;

import android.util.Log;
import android.util.Pair;

import com.example.hudso.racl.bean.PointBean;
import com.example.hudso.racl.bean.RouteBean;
import com.example.hudso.racl.util.GoogleMapsAPIDataParser;
import com.example.hudso.racl.util.NetworkUtils;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class to define route services.
 *
 * @author Hudson Henrique Lopes
 * @since 03/12/2017
 */
public class RouteServices extends Services {

    public RouteServices() {
        this.direction = ROUTE;
    }

    @Deprecated
    public JSONObject findById(String id) {
        String url = getEvaluatedURL(new Pair("id", id));
        String response = NetworkUtils.getJSONFromAPI(url, NetworkUtils.GET);
        if (response != null && !"".equals(response.trim())) {
            try {
                return new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Search route by point informations.
     *
     * @param pointBean
     * @return
     */
    public RouteBean findByLatLng(PointBean pointBean) {
        String url = getEvaluatedURL(
                new Pair<>("latitude", pointBean.getLatitude()),
                new Pair<>("longitude", pointBean.getLongitude()));
        String response = NetworkUtils.getJSONFromAPI(url, NetworkUtils.GET);
        if (response != null && !"".equals(response.trim())) {
            try {
                JSONObject jsonRoute = new JSONObject(response);
                return jsonToRouteBean(jsonRoute);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Parse <code>{@link JSONObject}</code> informations to <code>{@link com.example.hudso.racl.bean.DeviceBean}</code>.
     *
     * @param jsonRoute
     * @return
     * @throws JSONException
     */
    private RouteBean jsonToRouteBean(JSONObject jsonRoute) throws JSONException {
        RouteBean routeBean = new RouteBean(jsonRoute.getString("id"), jsonRoute.getString("name"));
        routeBean.setLatitude_min(jsonRoute.getDouble("latitude_min"));
        routeBean.setLatitude_max(jsonRoute.getDouble("latitude_max"));
        routeBean.setLongitude_min(jsonRoute.getDouble("longitude_min"));
        routeBean.setLongitude_max(jsonRoute.getDouble("longitude_max"));
        routeBean.setWeek_days(jsonRoute.getString("week_days"));

        loadSchedules(routeBean, jsonRoute.getJSONArray("schedules"));
        loadPoints(routeBean, jsonRoute.getJSONArray("points"));

        return routeBean;
    }

    private void loadSchedules(RouteBean routeBean, JSONArray jsonSchedules) throws JSONException {
        for (int iSchedule = 0; iSchedule < jsonSchedules.length(); iSchedule++) {
            JSONObject schedule = jsonSchedules.getJSONObject(iSchedule);
            routeBean.addSchedule(
                    schedule.getString("id"),
                    schedule.getString("week_day"),
                    schedule.getString("initial_hour"),
                    schedule.getString("final_hour"),
                    schedule.getString("information"),
                    schedule.getString("id_device")
            );
        }
    }

    private void loadPoints(RouteBean routeBean, JSONArray jsonPoints) throws JSONException {
        for (int iPoint = 0; iPoint < jsonPoints.length(); iPoint++) {
            JSONObject point = jsonPoints.getJSONObject(iPoint);
            if (point.getDouble("latitude") != 0 && point.getDouble("longitude") != 0) {
                routeBean.addPoint(
                        point.getString("id"),
                        point.getString("name"),
                        point.getDouble("latitude"),
                        point.getDouble("longitude")
                );
            }
        }
        List<List<PointBean>> parts = chopped(routeBean.getPoints(), 6);
        for (List<PointBean> part : parts) {
            List<LatLng> partsBetween = getDrawPoints(part);
            if (partsBetween != null) {
                routeBean.getDrawPoints().addAll(partsBetween);
            }
        }
    }

    private static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    private List<LatLng> getDrawPoints(List<PointBean> points) {
        String json = NetworkUtils.getJSONFromAPI(getUrlGoogleDirectionsAPI(points), NetworkUtils.GET);
        Log.i("RACL.LOG - Points along", json);
        return parseJsonToListLatLng(json);
    }

    private List<LatLng> parseJsonToListLatLng(String strJson) {
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(strJson);
            Log.d("ParserTask", strJson);
            GoogleMapsAPIDataParser parser = new GoogleMapsAPIDataParser();
            Log.d("ParserTask", parser.toString());

            // Starts parsing data
            routes = parser.parse(jObject);
            Log.d("ParserTask", "Executing routes");
            Log.d("ParserTask", routes.toString());

        } catch (Exception e) {
            Log.d("ParserTask", e.toString());
            e.printStackTrace();
        }

        List<LatLng> points = new ArrayList<>();

        int i = 0;

        // Traversing through all the routes
        //for (int i = 0; i < routes.size(); i++) {

        // Fetching i-th route
        if (routes != null && !routes.isEmpty()) {
            List<HashMap<String, String>> path = routes.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }
            //}
            return points;
        }
        return null;
    }

    // Utilizar a api do google mesmo com os pontos cadastrados pelo usuário https://maps.googleapis.com/maps/api/directions
    private String getUrlGoogleDirectionsAPI(List<PointBean> points) {
        // Tipo de retorno esperado (XML ou JSON)
        String output = "json";

        // Ponto de partida da rota
        LatLng origin = points.get(0).getLatLng();
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Ponto de destino da rota
        LatLng dest = points.get(points.size() - 1).getLatLng();
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Pontos da rota entre a partida e o destino
        StringBuffer waypoints = new StringBuffer("waypoints=");
        for (int i = 1; 0 < i && i < points.size() - 1; i++) {
            PointBean point = points.get(i);
            waypoints.append(point.getLatitude()).append(",");
            waypoints.append(point.getLongitude()).append("|");
        }

        // Concatenação dos parâmetros
        String parameters = str_origin + "&" + str_dest + "&" + waypoints.substring(0, waypoints.length() - 1);

        // Construção do endereço web do serviço com parâmetros
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }
}
