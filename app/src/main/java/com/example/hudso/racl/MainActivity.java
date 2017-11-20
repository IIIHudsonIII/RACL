package com.example.hudso.racl;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.hudso.racl.bean.DeviceBean;
import com.example.hudso.racl.singleton.SingletonMaps;
import com.example.hudso.racl.singleton.SingletonDevice;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        View button = findViewById(R.id.main_btn_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Para abrir um AppCompatActivity **/
                Intent it = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(it);
            }
        });

        button = findViewById(R.id.main_btn_follow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Para abrir um AppCompatActivity **/
//                Intent it = new Intent(MainActivity.this, MapsActivity.class);
                Intent it = new Intent(MainActivity.this, FollowActivity.class);
                startActivity(it);
            }
        });
    }

    protected final void initialize() {
        String idDevice = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("Hudson >>> ID_Device: "+idDevice);
        SingletonDevice.getInstance().setDeviceBean(new DeviceBean(idDevice));

        SingletonMaps.getInstance();
    }
}
