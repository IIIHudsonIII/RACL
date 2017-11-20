package com.example.hudso.racl.outro;

import android.os.AsyncTask;

import com.example.hudso.racl.singleton.SingletonMaps;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class TestePerformBackgroundTask extends AsyncTask<Void, Void, LatLng> {
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
        if (SingletonMaps.getInstance().getMarkerCollector() != null) {
            SingletonMaps.getInstance().getMarkerCollector().remove();
        }
        if (latLng != null) {
            SingletonMaps.getInstance().setMarkerCollector(
                    Metodos.getInstance().addMarkerToMap(
                            Metodos.getInstance().createCustomMarkerOptions(latLng, null, 0), true));
        }
        super.onPostExecute(latLng);
    }
}
