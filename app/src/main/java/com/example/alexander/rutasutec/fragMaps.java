package com.example.alexander.rutasutec;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class fragMaps extends SupportMapFragment implements OnMapReadyCallback {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rooteView = super.onCreateView(inflater,container,savedInstanceState);
        getMapAsync(this);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_frag_maps, container, false);
        return  rooteView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Posicionar el mapa en una localización y con un nivel de zoom
        LatLng latLng = new LatLng(13.7005071, -89.2015087);
        //Mapa normal y que aparezca boton de zoom
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettin = map.getUiSettings();
        uiSettin.setZoomControlsEnabled(true);
        // Un zoom mayor que 13 hace que el emulador falle, pero un valor deseado para
        // callejero es 17 aprox.
        map.addMarker(new MarkerOptions().position(latLng).title("Universidad Tecnologica").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        float ZoomLevel = 17;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,ZoomLevel));

        //Agrega la localizacion
        if ( ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            map.setMyLocationEnabled(true);
        }


        // Más opciones para el marcador en:
        // https://developers.google.com/maps/documentation/android/marker

        // Otras configuraciones pueden realizarse a través de UiSettings
        // UiSettings settings = getMap().getUiSettings();

    }
}
