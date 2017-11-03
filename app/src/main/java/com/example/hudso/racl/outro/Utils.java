package com.example.hudso.racl.outro;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

    public RouteBean getInfoRoute(String url) {
        String json = NetworkUtils.getJSONFromAPI(url, NetworkUtils.GET);
        Log.i("Searching routes ...", json);
        return parseJson(json);
    }

    private RouteBean parseJson(String strJson) {
        try {
            RouteBean routeBean = null;

            JSONObject jsonObject = new JSONObject(strJson);

            JSONArray jsonRoutes = jsonObject.getJSONArray("routes");
            for (int iRoute = 0; iRoute < jsonRoutes.length(); iRoute++) {
                JSONObject jsonRoute = jsonRoutes.getJSONObject(iRoute);
                routeBean = new RouteBean(
                        jsonRoute.getString("name"),
                        jsonRoute.getString("workDay"),
                        jsonRoute.getString("initialHour"),
                        jsonRoute.getString("finalHour")
                );

//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                Date data;

                JSONArray jsonPoints = jsonRoute.getJSONArray("points");
                for (int iPoint = 0; iPoint < jsonPoints.length(); iPoint++) {
                    JSONObject point = jsonPoints.getJSONObject(iPoint);
                    if (point.getInt("lat") != 0 && point.getInt("lng") != 0) {
                        routeBean.addPoint(
                                point.getDouble("lat"),
                                point.getDouble("lng"),
                                point.getString("description")
                        );
                    }
                }
            }

            return routeBean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}