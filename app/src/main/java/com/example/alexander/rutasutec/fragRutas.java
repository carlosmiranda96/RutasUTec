package com.example.alexander.rutasutec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class fragRutas extends Fragment {
    ListView Rutas;
    String[] valores = new String[]{"Ruta1", "Ruta2", "Ruta3", "Ruta4", "Ruta5"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_frag_rutas, container, false);
        Rutas = (ListView)rootView.findViewById(R.id.lista);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, valores);
        Rutas.setAdapter(adaptador);
        Rutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Posicion: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}
