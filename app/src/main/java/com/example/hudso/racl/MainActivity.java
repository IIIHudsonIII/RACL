package com.example.hudso.racl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
//    private FragmentManager fragmentManager;
//    private FragmentActivity mapsFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btnMain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Para abrir um AppCompatActivity **/
                Intent it = new Intent(MainActivity.this, MapsActivity.class);
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

        //fragmentManager = FirstMapFragment.newInstance();


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