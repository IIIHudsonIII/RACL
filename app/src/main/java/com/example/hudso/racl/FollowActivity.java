package com.example.hudso.racl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hudso.racl.bean.DeviceBean;
import com.example.hudso.racl.outro.DeviceServices;
import com.example.hudso.racl.singleton.SingletonDevice;

import org.json.JSONObject;

import static android.view.View.VISIBLE;

public class FollowActivity extends AppCompatActivity {

    private ImageView follow_img_follow;
    private ProgressBar follow_pb_loading;
    private TextView follow_tv_message;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        initialize();

        new DeviceService().execute(ActionEnum.FIND, SingletonDevice.getInstance().getDeviceBean());
    }

    protected void initialize() {
        follow_img_follow = ((ImageView) findViewById(R.id.follow_img_follow));
        follow_pb_loading = (ProgressBar) findViewById(R.id.follow_pb_loading);
        follow_tv_message = (TextView) findViewById(R.id.follow_tv_message);
    }
/*
    // TODO Hudson
    // Criar thread para realizar update da posição do dispositivo
    private void createTimerPosition() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            String id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                            Location newLocation = new Location(LocationManager.PASSIVE_PROVIDER);
                            System.out.println("Hudson - Posição do dispositivo " + id + ": " + newLocation.getLatitude() + " / " + newLocation.getLongitude());

                            // TODO Hudson
                            new DeviceService().execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000); //execute in every 3000 ms
    }
*/

    enum ActionEnum {
        FIND, UPDATE
    }

    // TODO Hudson
    // Realizar update da nova localização do dispositivo
    public final class DeviceService extends AsyncTask<Pair<ActionEnum, DeviceBean>, Void, DeviceBean> {

        protected void execute(@NonNull ActionEnum action, DeviceBean deviceBean) {
            if (deviceBean != null) {
                System.out.println("Hudson - ID do dispositivo: " + deviceBean.getId());
                super.execute(new Pair<>(action, deviceBean));
            } else {
                desactiveFollow();
            }
        }

        @Override
        protected DeviceBean doInBackground(Pair<ActionEnum, DeviceBean>... params) {
            DeviceServices ds = new DeviceServices();
            switch (params[0].first) {
                case FIND:
                    return ds.findById(params[0].second);
                case UPDATE:
                    return ds.update(params[0].second);
                default:
            }
            return null;
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(DeviceBean deviceBean) {
            System.out.println("Hudson.DeviceService - Dispositivo autenticado: " + deviceBean);
            SingletonDevice.getInstance().setDeviceBean(deviceBean);

            if (deviceBean != null) {
                if (runGPSPosition()) {
                    activateFollow();
                }
            } else {
                desactiveFollow();
            }
        }
    }

    protected void activateFollow() {
        follow_pb_loading.setVisibility(View.INVISIBLE);

        follow_img_follow.setImageResource(R.drawable.img_ok);
        follow_img_follow.setVisibility(VISIBLE);

        follow_tv_message.setText(FollowActivity.this.getResources().getText(R.string.rastreio_ativado_msg));
    }

    protected final void desactiveFollow() {
        follow_pb_loading.setVisibility(View.INVISIBLE);

        follow_img_follow.setImageResource(R.drawable.img_follow_failed);
        follow_img_follow.setVisibility(VISIBLE);

        follow_tv_message.setText(FollowActivity.this.getResources().getText(R.string.rastreio_nao_ativado_msg));
    }

    // TODO Hudson
    // Cria thread para receber cada nova localização do GPS
    protected boolean runGPSPosition() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(FollowActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FollowActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return false;
        }

        locationManager.requestLocationUpdates("gps", 5000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("Hudson >>> NewPosition: " + location.getLongitude() + " / " + location.getLatitude());

                DeviceBean deviceBean = SingletonDevice.getInstance().getDeviceBean();
                deviceBean.setLast_latitude(location.getLatitude());
                deviceBean.setLast_longitude(location.getLongitude());
                SingletonDevice.getInstance().setDeviceBean(deviceBean);

                //new DeviceService().execute(ActionEnum.FIND, deviceBean);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        });
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grant : grantResults) {
            if (grant == -1) {
                desactiveFollow();
                return;
            }
        }
        if (runGPSPosition()) {
            activateFollow();
        }
    }
}
