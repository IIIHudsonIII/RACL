package com.example.hudso.racl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FollowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        ((TextView) findViewById(R.id.lbFollow)).setText("Hudson - Criando tela de Follow");

        /** AJUSTAR AQUI HUDSON, agora está funcionando pelo clique no ProgressBar */
        findViewById(R.id.progressPB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ocultar a ProgressBar
                view.setVisibility(View.INVISIBLE);

                // Adicionado GIF img_follow_active ao ativar o rastreio.
                ((ImageView) findViewById(R.id.imgFollow)).setVisibility(View.VISIBLE);
                Glide.with(FollowActivity.this)
                        .load(R.drawable.img_follow_active)
                        .asGif()
                        .into((ImageView) findViewById(R.id.imgFollow));

                // Modificar a mensagem
                ((TextView) findViewById(R.id.lbFollow)).setText("Hudson - Rastreio ativado com sucesso.");
            }
        });
    }
}
