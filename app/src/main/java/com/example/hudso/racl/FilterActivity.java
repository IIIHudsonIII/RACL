package com.example.hudso.racl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.hudso.racl.bean.PointBean;
import com.example.hudso.racl.outro.GooglePlacesAutocompleteAdapter;
import com.example.hudso.racl.outro.Metodos;
import com.example.hudso.racl.singleton.SingletonMaps;
import com.example.hudso.racl.task.RouteServiceAsyncTask;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, RouteServiceAsyncTask.Behaviour {

    @BindView(R.id.filter_btn_view)
    View filter_btn_view;

    AutoCompleteTextView filter_ac_city;
    AutoCompleteTextView filter_ac_adress;

    private PointBean pointBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ButterKnife.bind(this);

        filter_ac_city = (AutoCompleteTextView) findViewById(R.id.filter_ac_city);
        filter_ac_adress = (AutoCompleteTextView) findViewById(R.id.filter_ac_adress);

        filter_ac_city.setAdapter(new GooglePlacesAutocompleteAdapter(this, android.R.layout.simple_dropdown_item_1line, GooglePlacesAutocompleteAdapter.CITY));
        filter_ac_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pointBean = null;

                String nmAddress = (String) parent.getItemAtPosition(position);
                LatLng latLng = Metodos.getInstance().getLocationFromAddress(FilterActivity.this, nmAddress);

                String dsPosition = " (" + latLng.latitude + "/" + latLng.longitude + ")";
                Toast.makeText(FilterActivity.this, nmAddress + dsPosition, Toast.LENGTH_SHORT).show();

                filter_ac_adress.requestFocus();
            }
        });

        filter_ac_adress.setAdapter(new GooglePlacesAutocompleteAdapter(this, android.R.layout.simple_dropdown_item_1line, GooglePlacesAutocompleteAdapter.ADDRESS, filter_ac_city));
        filter_ac_adress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nmAddress = (String) parent.getItemAtPosition(position);
                LatLng latLng = Metodos.getInstance().getLocationFromAddress(FilterActivity.this, nmAddress);

                pointBean = new PointBean("", nmAddress, latLng.latitude, latLng.longitude);

                String dsPosition = " (" + latLng.latitude + "/" + latLng.longitude + ")";
                Toast.makeText(FilterActivity.this, nmAddress + dsPosition, Toast.LENGTH_SHORT).show();

                filter_btn_view.requestFocus();
            }
        });

        // Remover
        filter_ac_city.setText("Blumenau - S");
        filter_ac_adress.setText("Benjamin Constant 266");
    }

    @Override
    @OnClick(R.id.filter_btn_view)
    public void onClick(View v) {
        if (pointBean != null) {
            new RouteServiceAsyncTask(this).find(pointBean);
        } else {
            SingletonMaps.getInstance().setRoute(null);
            Toast.makeText(FilterActivity.this, "Necessário selecionar um endereço para visualização da rota.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void success() {
        //                    Toast.makeText(FilterActivity.this, "Ok", Toast.LENGTH_LONG);
        Intent it = new Intent(FilterActivity.this, RouteActivity.class);
        startActivity(it);
    }

    @Override
    public void failed() {
        Toast.makeText(FilterActivity.this, "Necessário selecionar um endereço para visualização da rota.", Toast.LENGTH_LONG).show();
    }
}
