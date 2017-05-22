package com.example.alexander.rutasutec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class perfil extends AppCompatActivity {
    EditText usuarioPerfil, correoPerfil, nombrePerfil;
    Button actualizar,modificar;
    String usuario="vacio",correo="vacio";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_perfil);

        usuarioPerfil= (EditText)findViewById(R.id.txtUsuarioPerfl);
        correoPerfil = (EditText)findViewById(R.id.txtCorreoPerfil);
        nombrePerfil = (EditText)findViewById(R.id.txtNombrePerfil);

        usuario = getIntent().getStringExtra("usuario");

        cargarDatos(usuario);

        usuarioPerfil.setEnabled(false);
        correoPerfil.setEnabled(false);
        nombrePerfil.setEnabled(false);

        modificar = (Button)findViewById(R.id.btnModificarPerfil);
        actualizar = (Button)findViewById(R.id.btnActualizarPerfil);

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioPerfil.setEnabled(true);
                correoPerfil.setEnabled(true);
                nombrePerfil.setEnabled(true);
                modificar.setVisibility(View.GONE);
                actualizar.setVisibility(View.VISIBLE);
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioPerfil.setEnabled(false);
                correoPerfil.setEnabled(false);
                nombrePerfil.setEnabled(false);
                modificar.setVisibility(View.VISIBLE);
                actualizar.setVisibility(View.GONE);
            }
        });
    }

    private void cargarDatos(String usuario) {
        //final ProgressDialog perfil = new ProgressDialog(getApplicationContext());
        //perfil.setMessage("Cargando datos");
        //perfil.show();

        String url = "http://carlosmi.heliohost.org/android/mostrarDatosUsuario.php?usuario="+usuario;

        AsyncHttpClient cliente = new AsyncHttpClient();

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200)
                {
                    //perfil.dismiss();
                    try
                    {
                        JSONArray json = new JSONArray(new String(responseBody));
                        for(int i=0;i<json.length();i++)
                        {
                            usuarioPerfil.setText(json.getJSONObject(i).getString("usuario"));
                            correoPerfil.setText(json.getJSONObject(i).getString("correo"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
