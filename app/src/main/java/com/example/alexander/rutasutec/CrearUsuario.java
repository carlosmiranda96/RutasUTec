package com.example.alexander.rutasutec;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CrearUsuario extends AppCompatActivity {
    Button aceptar;
    CheckBox terminos;
    EditText usuario,correo,contra1,contra2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);


        aceptar = (Button)findViewById(R.id.btnRegistrar);
        terminos = (CheckBox)findViewById(R.id.chTerminos);

        usuario = (EditText)findViewById(R.id.txtCUsuario);
        correo = (EditText)findViewById(R.id.txtCCorreo);
        contra1 = (EditText)findViewById(R.id.txtCContra1);
        contra2 = (EditText)findViewById(R.id.txtCContra2);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1,pass2,correo1, usuario1,mensaje="";
                pass1=contra1.getText().toString();
                pass2=contra2.getText().toString();
                correo1 = correo.getText().toString();
                usuario1 = usuario.getText().toString();

                if(terminos.isChecked())
                {
                    if(!usuario1.isEmpty())
                    {
                        if(!correo1.isEmpty())
                        {
                            if(!pass1.isEmpty())
                            {
                                if(pass1.equals(pass2))
                                {
                                    ProgressDialog progress = new ProgressDialog(CrearUsuario.this);
                                    progress.setMessage("Registrando!!..");
                                    new MyTask(progress, CrearUsuario.this, usuario1,pass1,correo1).execute();
                                }
                                else
                                {
                                    Toast.makeText(CrearUsuario.this,"Las contrasenas no coinciden!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(CrearUsuario.this,"Contra Vacia!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(CrearUsuario.this,"Correo Vacio!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(CrearUsuario.this,"Usuario Vacio!!",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CrearUsuario.this,"Debe aceptar los terminos!!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public String Registrar(String usu,String pass,String correo)
    {
        URL url =null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;
        try
        {
            url = new URL("http://carlosmi.heliohost.org/android/addUsuario.php?usuario="+usu+"&contra="+pass+"&correo="+correo);
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
        CrearUsuario act;
        String usuario, contra, correo, mensaje;
        Boolean registro = false;
        int progreso = 0;
        public MyTask(ProgressDialog progress, CrearUsuario act, String usuario, String contra, String correo) {
            this.progress = progress;
            this.act = act;
            this.usuario = usuario;
            this.contra = contra;
            this.correo = correo;
        }

        public void onPreExecute() {
            progreso=0;
            progress.show();
        }

        public void onPostExecute(Void unused) {
            Toast.makeText(CrearUsuario.this,mensaje,Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
        AccesoInternet objeto = new AccesoInternet();
        protected Void doInBackground(Void... params) {
            mensaje = "Error";
            if(objeto.verificar()==true)
            {
                if(obtenerDatos(Registrar(usuario,contra,correo))==1)
                {
                    registro=true;
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(registro==true)
                            {
                                //Toast.makeText(CrearUsuario.this,"Su cuenta se ha creado correctamente!",Toast.LENGTH_SHORT).show();
                                Intent objeto = new Intent (getApplicationContext(),Principal.class);
                                objeto.putExtra("usuario",usuario);
                                startActivity(objeto);
                                act.usuario.setText("");
                                act.correo.setText("");
                                act.contra1.setText("");
                                act.contra2.setText("");
                            }
                        }
                    });
                }
                else
                {
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mensaje = "No se pudo registrar";
                            //Toast.makeText(CrearUsuario.this,"No se pudo registrar",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            else
            {
                mensaje = "No hay acceso a internet";
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
