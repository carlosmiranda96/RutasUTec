package com.example.alexander.rutasutec;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class administrador extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, comunicarFragmentos{

    String usuario = "Default";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);
        FragmentManager frag = getSupportFragmentManager();
        //Fragmento inicial
        frag.beginTransaction().replace(R.id.contenedorAdmin,new start()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_admin);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);
        //para mostrar el usuario en el Menu
        View headerView = navigationView.getHeaderView(0);

        TextView usuarioMenu = (TextView)headerView.findViewById(R.id.admin);
        usuario = getIntent().getStringExtra("usuario");
        usuarioMenu.setText(usuario);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder objeto = new AlertDialog.Builder(this);
        objeto.setMessage("¿Desea cerrar la sesión?");
        objeto.setTitle("Salir");
        objeto.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(administrador.this,Sesion.class));
            }
        });
        objeto.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = objeto.create();
        dialog.show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager frag = getSupportFragmentManager();
        if (id == R.id.nav_inicio_admin)
        {
            frag.beginTransaction().replace(R.id.contenedorAdmin,new start()).commit();
        }
        else if (id == R.id.nav_mapas_admin)
        {
            frag.beginTransaction().replace(R.id.contenedorAdmin,new fragMaps()).commit();
        }
        else if (id == R.id.nav_rutas_admin)
        {
            frag.beginTransaction().replace(R.id.contenedorAdmin,new fragRutas()).commit();
            //Toast.makeText(getApplicationContext(),"No disponible",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_contactanos_admin)
        {
            frag.beginTransaction().replace(R.id.contenedorAdmin,new fragContactenos()).commit();
        }
        else if(id == R.id.nav_admin)
        {
            frag.beginTransaction().replace(R.id.contenedorAdmin,new frag_admin()).commit();
        }
        else if (id == R.id.nav_salir_admin)
        {
            onBackPressed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_admin);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.administrador,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_ajustes)
        {
            Toast.makeText(this,"Ajustes inhabilitado",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void responder(String dato) {
        fragMaps fragment = fragMaps.newInstance(dato);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorAdmin,fragment,"tag").commit();
    }
}
