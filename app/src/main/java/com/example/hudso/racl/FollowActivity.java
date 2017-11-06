package com.example.hudso.racl;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hudso.racl.outro.DeviceServices;
import com.example.hudso.racl.outro.PerformBackgroundTask;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class FollowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        ((TextView) findViewById(R.id.lbFollow)).setText("Validando dispositivo, aguarde...");

        GetJson validateDevice = new GetJson();
        validateDevice.execute();
    }

    /** AJUSTAR AQUI HUDSON, agora está funcionando pelo clique no ProgressBar */
    public void ativar() {
        View view = findViewById(R.id.progressPB);
        // Ocultar a ProgressBar
        view.setVisibility(View.INVISIBLE);

        // Adicionado GIF img_follow_active ao ativar o rastreio.
        ((ImageView) findViewById(R.id.imgFollow)).setVisibility(View.VISIBLE);
        Glide.with(FollowActivity.this)
                .load(R.drawable.img_follow_active)
                .asGif()
                .into((ImageView) findViewById(R.id.imgFollow));

        // Modificar a mensagem
        ((TextView) findViewById(R.id.lbFollow)).setText("Rastreio ativado com sucesso!");

        callAsynchronousTask();
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

    private final class GetJson extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            String id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            return new DeviceServices().findById(id);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(JSONObject device) {
            View view = findViewById(R.id.progressPB);
            // Ocultar a ProgressBar
            view.setVisibility(View.INVISIBLE);

            System.out.println("Hudson - Retorno do dispositivo:\n" + device);
            if (device != null) {
                ativar();
            } else {
                ((TextView) findViewById(R.id.lbFollow)).setText("Não foi hoje BUDDY! :'( ");
            }
        }
    }

}
