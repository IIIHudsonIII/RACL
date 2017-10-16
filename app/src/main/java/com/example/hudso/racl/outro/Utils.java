package com.example.hudso.racl.outro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public String getInformacao(String end){
        String json;
        json = NetworkUtils.getJSONFromAPI(end);
        Log.i("Resultado", json);

        return json;
    }
}