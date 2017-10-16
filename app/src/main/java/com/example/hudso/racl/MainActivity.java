package com.example.hudso.racl;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hudso.racl.outro.MapsActivity;

public class MainActivity extends AppCompatActivity {

//    private FragmentManager fragmentManager;
//    private FragmentActivity mapsFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View button = findViewById(R.id.btnSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Para abrir um AppCompatActivity **/
                //Intent it = new Intent(MainActivity.this, MapsActivity.class);
                Intent it = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(it);


//                LinearLayout v = ((LinearLayout) findViewById(R.id.layoutBackground));
//
//                android.support.v4.app.FragmentTransaction fragmentTrasaction = getSupportFragmentManager().beginTransaction();
//                fragmentTrasaction.replace(R.id.layoutFragment, FirstMapFragment.instantiate(MainActivity.this, "teste"));
//                fragmentTrasaction.commit();
//
//                ((TextView) findViewById(R.id.label)).setText("Clicando em " + new Date().toString());
            }
        });

        button = findViewById(R.id.btnFollow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Para abrir um AppCompatActivity **/
                //Intent it = new Intent(MainActivity.this, MapsActivity.class);
                Intent it = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(it);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_LONG).show();
    }
}
