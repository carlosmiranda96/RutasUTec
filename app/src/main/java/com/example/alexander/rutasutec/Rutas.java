package com.example.alexander.rutasutec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Rutas extends AppCompatActivity {
    ListView Rutas;
    String []valores = new String[]{"Ruta1","Ruta2","Ruta3","Ruta4","Ruta5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);
        Rutas = (ListView) findViewById(R.id.listaRutas);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, valores);
        Rutas.setAdapter(adaptador);
        Rutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Posicion: "+position,Toast.LENGTH_SHORT).show();
            }
        });
        }
    }