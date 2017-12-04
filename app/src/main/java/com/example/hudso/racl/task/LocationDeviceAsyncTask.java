package com.example.hudso.racl.task;

import android.os.AsyncTask;

import com.example.hudso.racl.bean.DeviceBean;
import com.example.hudso.racl.outro.Metodos;
import com.example.hudso.racl.singleton.SingletonDevice;
import com.example.hudso.racl.singleton.SingletonMaps;
import com.google.android.gms.maps.model.LatLng;

public class LocationDeviceAsyncTask extends AsyncTask<Void, Void, LatLng> {
    @Override
    protected LatLng doInBackground(Void... params) {
        DeviceBean db = SingletonDevice.getInstance().getDeviceBean();
        if (db != null) {
            return new LatLng(db.getLast_latitude(), db.getLast_longitude());
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
                            Metodos.getInstance().createCustomMarkerOptions(latLng, "Coletor", 0), true));
        }
        super.onPostExecute(latLng);
    }
}
