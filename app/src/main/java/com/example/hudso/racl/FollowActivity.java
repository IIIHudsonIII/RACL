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
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hudso.racl.outro.DeviceServices;
import com.example.hudso.racl.outro.PerformBackgroundTask;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.VISIBLE;

public class FollowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        ((TextView) findViewById(R.id.lbFollow)).setText("Autenticando dispositivo.\nAguarde...");

        GetJson validateDevice = new GetJson();
        validateDevice.execute();
    }

    /** AJUSTAR AQUI HUDSON, agora está funcionando pelo clique no ProgressBar */
    public void ativar() {
        View view = findViewById(R.id.progressPB);
        // Ocultar a ProgressBar
        view.setVisibility(View.INVISIBLE);
        ((ImageView) findViewById(R.id.imgFollow)).setImageResource(R.drawable.img_follow_ok);
        ((ImageView) findViewById(R.id.imgFollow)).setVisibility(VISIBLE);

        // Adicionado GIF img_follow_active ao ativar o rastreio.
//        ((ImageView) findViewById(R.id.imgFollow)).setVisibility(VISIBLE);
//        Glide.with(FollowActivity.this)
//                .load(R.drawable.img_follow_ok)
//                .asGif()
//                .into((ImageView) findViewById(R.id.imgFollow));

        // Modificar a mensagem
        ((TextView) findViewById(R.id.lbFollow)).setText("Rastreio ativado com sucesso!");

        String id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("Hudson - ID do dispositivo: "+id);

        //callAsynchronousTask();
        FazOPost post = new FazOPost();
        post.execute();
    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            String id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                            System.out.println("Hudson - ID do dispositivo: "+id);
                            Location newLocation = new Location(LocationManager.PASSIVE_PROVIDER);
                            System.out.println("Hudson - Posição do dispositivo: "+newLocation.getLatitude()+" / "+newLocation.getLongitude());

                            //LocationListener locationListener = new MyLocationListener();
                            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000); //execute in every 3000 ms
        //timer.cancel();
    }

    public static final class FazOPost extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return new DeviceServices().POST("");
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(String device) {
            System.out.println("Hudson > OI > "+device);
        }
    }

    private final class GetJson extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            String id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            return new DeviceServices().findById(id);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(JSONObject device) {

            System.out.println("Hudson - Retorno do dispositivo:\n" + device);
            if (device != null) {
                ativar();
            } else {
                View view = findViewById(R.id.progressPB);
                // Ocultar a ProgressBar
                view.setVisibility(View.INVISIBLE);

                // Adicionado GIF img_follow_active ao ativar o rastreio.
                //((ImageView) findViewById(R.id.imgFollow)).setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.imgFollow)).setImageResource(R.drawable.img_follow_failed);
                ((ImageView) findViewById(R.id.imgFollow)).setVisibility(VISIBLE);
                /*Glide.with(FollowActivity.this)
                        .load(R.drawable.img_follow_failed)
                        //.asGif()
                        .into((ImageView) findViewById(R.id.imgFollow));
*/
                ((TextView) findViewById(R.id.lbFollow)).setText("Não foi possível ativar o rastreio do dispositivo.");
            }
        }
    }

    // Aqui consegue a localização do dispositivo via GPS
    private LocationManager locationManager;
    private LocationListener listener;

    protected void setandoOPegadorDePosicao() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("Hudson >>> Localização do dispositivo: "+location.getLongitude() + " / " + location.getLatitude());
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
        };

        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
    }
}
