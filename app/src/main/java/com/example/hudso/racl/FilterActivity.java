package com.example.hudso.racl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hudso.racl.outro.MapsActivity;

public class FilterActivity extends AppCompatActivity {

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        View button = findViewById(R.id.btnView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Para abrir um AppCompatActivity **/
                Intent it = new Intent(FilterActivity.this, RouteActivity.class);
                startActivity(it);
            }
        });
    }
}
