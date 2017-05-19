package com.example.alexander.rutasutec;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Sesion extends AppCompatActivity {

    Button inicio,cancelar;
    EditText usuario,contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);
        inicio = (Button)findViewById(R.id.btnEntrar);
        cancelar = (Button)findViewById(R.id.btnCancelar);
        usuario = (EditText)findViewById(R.id.txtusuario);
        contra = (EditText)findViewById(R.id.txtcontra);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objeto = new Intent(Sesion.this,inicio.class);
                Toast.makeText(Sesion.this,"Cancelado",Toast.LENGTH_SHORT).show();
                startActivity(objeto);
            }
        });
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuario.getText().toString().isEmpty())
                {
                    if(!contra.getText().toString().isEmpty())
                    {
                        if(isOnlineNet()!=false)
                        {
                            ProgressDialog progress = new ProgressDialog(Sesion.this);
                            progress.setMessage("Iniciando Sesión..");
                            new MyTask(progress, Sesion.this,usuario.getText().toString(),contra.getText().toString()).execute();
                        }
                        else
                        {
                            Toast.makeText(Sesion.this,"No hay Acceso a internet",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Sesion.this,"El campo Contra no puede estar vacio",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Sesion.this,"El campo Usuario no puede estar vacio",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Sesion.this, com.example.alexander.rutasutec.inicio.class));
    }
    public String InicioSesion(String usu,String pass)
    {
        URL url =null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;
        try
        {
            url = new URL("https://carlosmi.heliohost.org/android/validaLogin.php?usuario="+usu+"&contra="+pass);
            HttpURLConnection conection = (HttpURLConnection)url.openConnection();
            respuesta = conection.getResponseCode();
            resul = new StringBuilder();
            if(respuesta==HttpURLConnection.HTTP_OK)
            {
                InputStream in = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while((linea=reader.readLine())!=null)
                {
                    resul.append(linea);
                }
            }
            else
            {

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  resul.toString();
    }
    public int obtenerDatos(String respuesta)
    {
        int resp =0;
        try
        {
            JSONArray json = new JSONArray(respuesta);
            if(json.length()>0)
            {
                resp=1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public class MyTask extends AsyncTask<Void, Integer, Void> {

        ProgressDialog progress;
        Sesion act;
        String usuario,contra,mensaje;
        Boolean iniciar=false;
        int progreso = 0;
        public MyTask(ProgressDialog progress, Sesion act, String usuario, String contra) {
            this.progress = progress;
            this.act = act;
            this.usuario = usuario;
            this.contra = contra;
        }

        public void onPreExecute() {
            progreso=0;
            progress.show();
        }

        public void onPostExecute(Void unused) {
            if(iniciar==true)
            {
                Intent objeto = new Intent(getApplicationContext(),Principal.class);
                objeto.putExtra("usuario",usuario);
                startActivity(objeto);
                Toast.makeText(Sesion.this,"Bienvenido",Toast.LENGTH_SHORT).show();
                act.usuario.setText("");
                act.contra.setText("");
            }
            else
            {
                Toast.makeText(Sesion.this,mensaje,Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();
        }

        protected Void doInBackground(Void... params) {
            if(obtenerDatos(InicioSesion(usuario,contra))==1)
            {
                iniciar=true;
            }
            else
            {
                mensaje = "Usuario o contra incorrecta!";
            }
            while (progreso<100)
            {
                progreso++;
                publishProgress(progreso);
                SystemClock.sleep(10);
            }
            return null;
        }

    }

}
