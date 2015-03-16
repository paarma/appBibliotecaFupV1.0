package com.developer.pablo.appbibliotecafup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by pablo on 16/03/15.
 */
public class Activity_admin extends Activity {

    Button btnCrearLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void crearLibro(View view){
        Intent goCrearLibro = new Intent(Activity_admin.this, Activity_crear_libro.class);
        startActivity(goCrearLibro);
    }

    public void listadoLibros(View view){
        Intent goListadoLibros = new Intent(Activity_admin.this, Activity_listado_libros.class);
        startActivity(goListadoLibros);
    }
}
