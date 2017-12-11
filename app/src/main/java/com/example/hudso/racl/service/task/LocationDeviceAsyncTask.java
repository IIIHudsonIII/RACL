package com.example.hudso.racl.service.task;

import android.os.AsyncTask;

import com.example.hudso.racl.bean.DeviceBean;
import com.example.hudso.racl.service.DeviceServices;
import com.example.hudso.racl.util.MapUtils;
import com.example.hudso.racl.singleton.SingletonDevice;
import com.example.hudso.racl.singleton.SingletonMaps;
import com.google.android.gms.maps.model.LatLng;

/**
 * Class to define new device location.
 * Use information collected on <code>{@link DeviceServices}</code> by class <code>{@link DeviceServiceAsyncTask}</code>.
 *
 * @author Hudson Henrique Lopes
 * @since 03/12/2017
 */
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
        System.out.println("RACL.LOG - New device location: " + latLng);

        if (SingletonMaps.getInstance().getMarkerCollector() != null) {
            SingletonMaps.getInstance().getMarkerCollector().remove();
        }

        if (latLng != null && latLng.longitude != 0 && latLng.latitude != 0) {
            MapUtils mu = new MapUtils();
            // TEXTO FIXO
            SingletonMaps.getInstance().setMarkerCollector(
                    mu.addMarkerToMap( mu.createCustomMarkerOptions(latLng, "Coletor", 0), true )
            );
        }
        super.onPostExecute(latLng);
    }
}
