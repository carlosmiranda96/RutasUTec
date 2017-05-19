package com.example.alexander.rutasutec;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class inicio extends AppCompatActivity {
    Button inicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        inicio = (Button)findViewById(R.id.btnstart);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objeto = new Intent(inicio.this,Sesion.class);
                startActivity(objeto);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder objeto = new AlertDialog.Builder(this);
        objeto.setMessage("¿Desea Salir del sistema?");
        objeto.setTitle("Salir");
        objeto.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu_crear)
        {
            //Toast.makeText(this,"Crear nueva cuenta",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CrearUsuario.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
