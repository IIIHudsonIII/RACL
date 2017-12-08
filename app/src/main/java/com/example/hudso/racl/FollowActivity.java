package com.example.hudso.racl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hudso.racl.bean.DeviceBean;
import com.example.hudso.racl.service.task.DeviceServiceAsyncTask;
import com.example.hudso.racl.singleton.SingletonDevice;

import static android.view.View.VISIBLE;

public class FollowActivity extends AppCompatActivity implements DeviceServiceAsyncTask.Behaviour {

    private ImageView follow_img_follow;
    private ProgressBar follow_pb_loading;
    private TextView follow_tv_message;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        initialize();

        new DeviceServiceAsyncTask(this).find(SingletonDevice.getInstance().getDeviceBean());
    }

    protected void initialize() {
        follow_img_follow = ((ImageView) findViewById(R.id.follow_img_follow));
        follow_pb_loading = (ProgressBar) findViewById(R.id.follow_pb_loading);
        follow_tv_message = (TextView) findViewById(R.id.follow_tv_message);
    }

    @Override
    public void success() {
        if (runGPSPosition()) {
            activateFollow();
        }
    }

    @Override
    public void failed() {
        desactiveFollow();
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

    // Cria thread para receber cada nova localização do GPS
    protected boolean runGPSPosition() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Verifica habilitação de permissões necessárias para uso do GPS
        if (ActivityCompat.checkSelfPermission(FollowActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FollowActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Questiona habilitação das permissões
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return false;
        }

        locationManager.requestLocationUpdates("gps", 3000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                DeviceBean deviceBean = SingletonDevice.getInstance().getDeviceBean();
                deviceBean.setLast_latitude(location.getLatitude());
                deviceBean.setLast_longitude(location.getLongitude());
                SingletonDevice.getInstance().setDeviceBean(deviceBean);
                // Envia as novas coordenadas da posição do dispositivo
                new DeviceServiceAsyncTask(FollowActivity.this).update(SingletonDevice.getInstance().getDeviceBean());
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
