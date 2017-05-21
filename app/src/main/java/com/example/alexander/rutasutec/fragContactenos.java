package com.example.alexander.rutasutec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class fragContactenos extends Fragment {

    EditText asunto,mensaje;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_frag_contactenos, container, false);
        asunto = (EditText)rootView.findViewById(R.id.txtAsunto);
        mensaje = (EditText)rootView.findViewById(R.id.txtMensaje);
        return rootView;
    }

}
