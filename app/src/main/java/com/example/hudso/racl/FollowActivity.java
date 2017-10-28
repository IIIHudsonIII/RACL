package com.example.hudso.racl;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hudso.racl.outro.TentandoEnviarLocalizacao;

public class FollowActivity extends TentandoEnviarLocalizacao {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        /** AJUSTAR AQUI HUDSON, agora está funcionando pelo clique no ProgressBar */
        findViewById(R.id.progressPB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ocultar a ProgressBar
                view.setVisibility(View.INVISIBLE);

                // Adicionado GIF img_follow_active ao ativar o rastreio.
                findViewById(R.id.iv_follow).setVisibility(View.VISIBLE);
                Glide.with(FollowActivity.this)
                        .load(R.drawable.img_follow_active)
                        .asGif()
                        .into((ImageView) findViewById(R.id.iv_follow));

                // Modificar a mensagem
                ((TextView) findViewById(R.id.msgPB)).setText("Rastreio ativado com sucesso.");
            }
        });
    }
}