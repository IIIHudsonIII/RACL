package com.example.hudso.racl;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.hudso.racl.outro.GooglePlacesAutocompleteAdapter;
import com.example.hudso.racl.outro.Metodos;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.filter_btn_view)
    View filter_btn_view;

    AutoCompleteTextView filter_ac_city;
    AutoCompleteTextView filter_ac_adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ButterKnife.bind(this);

        filter_ac_city = (AutoCompleteTextView) findViewById(R.id.filter_ac_city);
        filter_ac_city.setAdapter(new GooglePlacesAutocompleteAdapter(this, android.R.layout.simple_dropdown_item_1line, GooglePlacesAutocompleteAdapter.CITY));
        filter_ac_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nmAddress = (String) parent.getItemAtPosition(position);
                LatLng latLng = Metodos.getInstance().getLocationFromAddress(FilterActivity.this, nmAddress);

                String auxHudson = " (" + latLng.latitude + "/" + latLng.longitude + ")";
                Toast.makeText(FilterActivity.this, nmAddress + auxHudson, Toast.LENGTH_SHORT).show();
            }
        });

        filter_ac_adress = (AutoCompleteTextView) findViewById(R.id.filter_ac_adress);
        filter_ac_adress.setAdapter(new GooglePlacesAutocompleteAdapter(this, android.R.layout.simple_dropdown_item_1line, GooglePlacesAutocompleteAdapter.ADDRESS, filter_ac_city));
        filter_ac_adress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nmAddress = (String) parent.getItemAtPosition(position);
                LatLng latLng = Metodos.getInstance().getLocationFromAddress(FilterActivity.this, nmAddress);

                String auxHudson = " (" + latLng.latitude + "/" + latLng.longitude + ")";
                Toast.makeText(FilterActivity.this, nmAddress + auxHudson, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    @OnClick(R.id.filter_btn_view)
    public void onClick(View v) {
        Intent it = new Intent(FilterActivity.this, RouteActivity.class);
        startActivity(it);
    }
}
