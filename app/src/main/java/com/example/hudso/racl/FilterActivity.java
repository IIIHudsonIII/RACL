package com.example.hudso.racl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.hudso.racl.bean.PointBean;
import com.example.hudso.racl.adapter.GooglePlacesAutocompleteAdapter;
import com.example.hudso.racl.singleton.SingletonMaps;
import com.example.hudso.racl.service.task.RouteServiceAsyncTask;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

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
                LatLng latLng = getLocationFromAddress(FilterActivity.this, nmAddress);

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
                LatLng latLng = getLocationFromAddress(FilterActivity.this, nmAddress + " " + filter_ac_city.getText());

                pointBean = new PointBean("", nmAddress, latLng.latitude, latLng.longitude);

                String dsPosition = " (" + latLng.latitude + "/" + latLng.longitude + ")";
                Toast.makeText(FilterActivity.this, nmAddress + dsPosition, Toast.LENGTH_SHORT).show();

                // Hide keys to write
                InputMethodManager imm = (InputMethodManager) FilterActivity.this.getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(filter_ac_adress.getWindowToken(), 0);
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
            filter_btn_view.setEnabled(false);
            new RouteServiceAsyncTask(this).find(pointBean);
        } else {
            SingletonMaps.getInstance().setRoute(null);
            Toast.makeText(FilterActivity.this, FilterActivity.this.getResources().getText(R.string.msg_select_address), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void success() {
        Intent it = new Intent(FilterActivity.this, RouteActivity.class);
        startActivity(it);
        filter_btn_view.setEnabled(true);
    }

    @Override
    public void failed() {
        Toast.makeText(FilterActivity.this, FilterActivity.this.getResources().getText(R.string.msg_no_routes_found), Toast.LENGTH_SHORT).show();
        filter_btn_view.setEnabled(true);
    }

    // TODO Hudson
    // Tentar mover este método para outro local
    public LatLng getLocationFromAddress(Context context, String strAddress) {
        try {
            Geocoder coder = new Geocoder(context, Locale.getDefault());
            List<Address> address = coder.getFromLocationName(strAddress, 1);
            if (address != null && address.size() > 0) {
                Address location;
                for (int i = 0; i < address.size(); i++) {
                    System.out.println("RACL.LOG - Searching by '" + strAddress + "' was found '" + address.get(i).getAddressLine(0)+"'");
                }
                location = address.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (Exception e) {

        }
        return new LatLng(0, 0);
    }
}
