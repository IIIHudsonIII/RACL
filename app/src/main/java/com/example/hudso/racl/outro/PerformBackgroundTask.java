package com.example.hudso.racl.outro;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by hudso on 03/11/2017.
 */

public class PerformBackgroundTask extends AsyncTask<Void, Void, LatLng> {
    private static int i = 0;
    @Override
    protected LatLng doInBackground(Void... params) {
        List<LatLng> list = Metodos.getInstance().getDefaultListPoints();
        if (i < list.size()) {
            return list.get(i++);
        }
        return null;
    }

    @Override
    protected void onPostExecute(LatLng latLng) {
        if (SingletonTeste.getInstance().getMarkerCollector() != null) {
            SingletonTeste.getInstance().getMarkerCollector().remove();
        }
        if (latLng != null) {
            SingletonTeste.getInstance().setMarkerCollector(
                    Metodos.getInstance().addMarkerToMap(
                            Metodos.getInstance().createCustomMarkerOptions(latLng, null, 0), true));
        }
        super.onPostExecute(latLng);
    }
}
