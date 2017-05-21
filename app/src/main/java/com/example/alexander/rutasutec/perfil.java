package com.example.alexander.rutasutec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class perfil extends AppCompatActivity {
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_perfil);
        TextView usuarioPerfil = (TextView)findViewById(R.id.txtUsuarioPerfl);
        usuario = getIntent().getStringExtra("usuario");
        usuarioPerfil.setText(usuario);
        usuarioPerfil.setEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
