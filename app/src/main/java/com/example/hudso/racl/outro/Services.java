package com.example.hudso.racl.outro;

import android.support.annotation.StringDef;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

abstract class Services {
    protected static final String DEVICE = "/device";
    protected static final String ROUTE = "/route";
    protected static final String ROUTE_POINT = "/route_point";
    protected static final String ROUTE_TIME_WEEK_DAY = "/route_time_week_day";
    protected static final String DEFAULT = "";

    @StringDef({DEFAULT, DEVICE, ROUTE, ROUTE_POINT, ROUTE_TIME_WEEK_DAY})
    public @interface Direction {
    }

    protected @Direction
    String direction;

    protected final String URL_DEFAULT = "http://10.0.2.2:8080/";

    public String getURL() {
        return getEvaluatedURL();
    }

    public String getEvaluatedURL(Pair... parameters) {
        StringBuffer url = new StringBuffer();
        url.append(URL_DEFAULT).append(direction);

        if (parameters != null && parameters.length > 0) {
            url.append("?");
            for (Pair p : parameters) {
                url.append(p.first).append("=").append(p.second);
            }
        }
        return url.toString();
    }
}