package com.example.hudso.racl.outro;

import android.util.Pair;

import com.example.hudso.racl.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class RouteServices extends Services {

    public RouteServices() {
        this.direction = ROUTE;
    }

    public JSONObject findById(String id) {
        String url = getEvaluatedURL(new Pair("id", id));
        String response = NetworkUtils.getJSONFromAPI(url, NetworkUtils.PUT);
        if (response != null && !"".equals(response.trim())) {
            try {
                return new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
