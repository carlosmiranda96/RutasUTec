package com.example.alexander.rutasutec;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class fragRutas extends Fragment {
    ListView Rutas;
    ArrayList<String> datos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView;
        rootView = inflater.inflate(R.layout.fragment_frag_rutas, container, false);
        Rutas = (ListView)rootView.findViewById(R.id.lista);
        cargarDatos();
        return rootView;
    }


    private void cargarDatos() {
        final ProgressDialog cargando = new ProgressDialog(getContext());
        cargando.setMessage("Cargando Datos..");
        cargando.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        String url = "http://carlosmi.heliohost.org/android/mostrarRutas.php";

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200)
                {
                    cargando.dismiss();
                    datos = new ArrayList<String>();
                    try {
                        JSONArray array = new JSONArray(new String(responseBody));
                        for(int i =0;i<array.length();i++)
                        {
                            datos.add("Ruta "+array.getJSONObject(i).getString("ruta"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,datos);
                    Rutas.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                cargando.dismiss();
                Toast.makeText(getContext()," "+error+" ",Toast.LENGTH_LONG).show();
            }
        });

        Rutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Posicion: " + datos.get(position), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
