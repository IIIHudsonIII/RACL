package com.example.hudso.racl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
//                fragmentTrasaction.replace(R.id.layoutFragment, InternalMapFragment.instantiate(MainActivity.this, "teste"));
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
//                Intent it = new Intent(MainActivity.this, MapsActivity.class);
                Intent it = new Intent(MainActivity.this, FollowActivity.class);
                startActivity(it);
            }
        });
    }
}
