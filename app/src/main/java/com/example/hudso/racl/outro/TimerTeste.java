package com.example.hudso.racl.outro;

import com.example.hudso.racl.R;
import com.example.hudso.racl.RouteActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by hudso on 02/11/2017.
 */

public class TimerTeste extends Thread {

    static int i = 0;

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        if (1 == 1) {
            return;
        }
        try {
            //sleep(5000);
            List<LatLng> list = Metodos.getInstance().getDefaultListPoints();

            //for (int x = 0; x < list.size(); x++) {

            //load
            if (SingletonTeste.getInstance().getMarkerCollector() != null) {
                SingletonTeste.getInstance().getMarkerCollector().remove();
            }
            System.out.println("Hudson - BEGIN " + list.get(i));
            try {
                MarkerOptions collectorMarker = Metodos.getInstance().createCustomMarkerOptions(list.get(i++), "Coletor", R.drawable.garbage_collector);
                SingletonTeste.getInstance().setMarkerCollector(Metodos.getInstance().addMarkerToMap(collectorMarker, true));
                //RouteActivity.teste(collectorMarker);
            } catch (Exception e) {
                System.out.println("Hudson - >>>ERROR<<< -  " + e.getMessage());
            }

            System.out.println("Hudson - END " + list.get(i));
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
        //super.run();
    }
}
