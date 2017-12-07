package com.example.hudso.racl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hudso.racl.bean.RouteBean;
import com.example.hudso.racl.bean.ScheduleBean;
import com.example.hudso.racl.util.MapUtils;
import com.example.hudso.racl.singleton.SingletonDevice;
import com.example.hudso.racl.singleton.SingletonMaps;
import com.example.hudso.racl.service.task.DeviceServiceAsyncTask;
import com.example.hudso.racl.service.task.LocationDeviceAsyncTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class InternalMapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_internal, container, false);

        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.setActivated(false);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                InternalMapFragment.this.onMapReady(mMap);
            }
        });

        return rootView;

    }

    /**
     * Manipulates the mapView2 once available.
     * This callback is triggered when the mapView2 is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        SingletonMaps.getInstance().setMap(googleMap);

        RouteBean route = SingletonMaps.getInstance().getRoute();
        if (route == null) {
            return;
        }

        TextView textView = getView().findViewById(R.id.tw_internal_map_name);
        if (textView != null) {
            textView.setText(route.getName());
        }

        new MapUtils().drawDynamicRoute(route);

        loadDevice(route);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void loadDevice(RouteBean route) {
        Calendar currentDate = Calendar.getInstance();
        int weekDayCurrent = currentDate.get(Calendar.DAY_OF_WEEK);

        for (ScheduleBean sb : route.getSchedules()) {
            String week_day = sb.getWeek_day();

            int weekDayCollector = ScheduleBean.WeekDay.valueOf(week_day.toUpperCase()).ordinal() + 1;
            if (weekDayCollector > -1) {
                if (weekDayCollector == weekDayCurrent) {
                    DateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");
                    try {
                        Date dCurrent = dateFormat.parse(dateFormat.format(new Date()));
                        Date dInitial = dateFormat.parse(sb.getInitial_hour() + ":00");
                        Date dFinal = dateFormat.parse(sb.getFinal_hour() + ":00");

                        if (dCurrent.after(dInitial) && dCurrent.before(dFinal)) {
                            new DeviceServiceAsyncTask(
                                    new DeviceServiceAsyncTask.Behaviour() {
                                        @Override
                                        public void success() {
                                            createTimerPosition();
                                        }

                                        @Override
                                        public void failed() {
                                        }
                                    }
                            ).find(sb.getId_device());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Timer timer;

    public void createTimerPosition() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        timer = new Timer();

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    new DeviceServiceAsyncTask(new DeviceServiceAsyncTask.Behaviour() {
                        @Override
                        public void success() {
                            new LocationDeviceAsyncTask().execute();
                        }

                        @Override
                        public void failed() {
                        }
                    }).find(SingletonDevice.getInstance().getDeviceBean().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
}