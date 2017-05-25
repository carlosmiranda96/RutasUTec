package com.example.alexander.rutasutec;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;


public class fragMaps extends SupportMapFragment implements OnMapReadyCallback {
    String ruta = "1";
    List<LatLng> coordenadas = new ArrayList<LatLng>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rooteView = super.onCreateView(inflater,container,savedInstanceState);
        getMapAsync(this);

        return  rooteView;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle arg = getArguments();
        if (arg != null) {
            ruta = arg.getString("ruta");
            coordenadas.clear();
            cargarDatos(ruta);
            Toast.makeText(getContext(),"Ruta "+ ruta, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap maps) {
        // Posicionar el mapa en una localización y con un nivel de zoom
        LatLng utec = new LatLng(13.7005071,-89.2015087);
        //Mapa normal y que aparezca boton de zoom
        maps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettin = maps.getUiSettings();
        uiSettin.setZoomControlsEnabled(true);

        //Agrega la localizacion
        if ( ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            maps.setMyLocationEnabled(true);
        }

        if(!ruta.equals("1"))
        {
            PolylineOptions opcion = new PolylineOptions().width(8).color(Color.rgb(0,76,153)).geodesic(true);
            for(int i=0;i<coordenadas.size();i++)
            {
                opcion.add(coordenadas.get(i));
                //Toast.makeText(getContext(),"si ay",Toast.LENGTH_SHORT).show();
            }
            maps.addPolyline(opcion);
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(utec,17));
        }
        else
        {
            // Un zoom mayor que 13 hace que el emulador falle, pero un valor deseado para
            // callejero es 17 aprox.
            maps.addMarker(new MarkerOptions().position(utec).title("Universidad Tecnologica").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            float ZoomLevel = 13;
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(utec,ZoomLevel));
        }


        // Más opciones para el marcador en:
        // https://developers.google.com/maps/documentation/android/marker

        // Otras configuraciones pueden realizarse a través de UiSettings
        // UiSettings settings = getMap().getUiSettings();

    }

    private void cargarDatos(String ruta) {
        ruta = ruta.replace(" ","%20");
        String url = "http://carlosmi.heliohost.org/android/mostrarLatitudRuta.php?ruta="+ruta;
        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200)
                {
                    try {
                        JSONArray respuesta = new JSONArray(new String(responseBody));
                        for(int i=0;i<respuesta.length();i++)
                        {
                            coordenadas.add(new LatLng(respuesta.getJSONObject(i).getDouble("latitud"),respuesta.getJSONObject(i).getDouble("longitud")));
                        }
                        if(respuesta.length()==0)
                        {
                            Toast.makeText(getContext(),"No hay ruta",Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    Toast.makeText(getContext(),"No se pudo",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext()," "+error+" ",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static fragMaps newInstance(String variable) {
        fragMaps fragment = new fragMaps();
        Bundle args = new Bundle();
        args.putString("ruta", variable);
        fragment.setArguments(args);
        return fragment;
    }
}
