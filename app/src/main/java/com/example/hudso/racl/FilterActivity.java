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
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btnView)
    View btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ButterKnife.bind(this);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(new GooglePlacesAutocompleteAdapter(this, android.R.layout.simple_dropdown_item_1line));
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address = (String) parent.getItemAtPosition(position);
                LatLng latLng = getLocationFromAddress(address);
                String desc = " (" + latLng.latitude + "/" + latLng.longitude + ")";
                Toast.makeText(FilterActivity.this, address + desc, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    @OnClick(R.id.btnView)
    public void onClick(View v) {
        Intent it = new Intent(FilterActivity.this, RouteActivity.class);
        startActivity(it);
    }

    public LatLng getLocationFromAddress(String strAddress){
        try {
            Geocoder coder = new Geocoder(this);
            List<Address> address = coder.getFromLocationName(strAddress, 1);
            if (address != null && address.size() > 0) {
                Address location;
                for (int i = 0; i < address.size(); i++) {
                    System.out.println("getLocationFromAddress("+strAddress+") >>> "+address.get(i).getAddressLine(0));
                }
                location = address.get(0);
                return new LatLng(location.getLatitude(),location.getLongitude());
            }
        } catch (Exception e) {

        }
        return new LatLng(0,0);
    }
}
