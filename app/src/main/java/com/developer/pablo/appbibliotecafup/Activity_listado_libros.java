package com.developer.pablo.appbibliotecafup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.developer.pablo.appbibliotecafup.modelo.Libro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 16/03/15.
 */
public class Activity_listado_libros extends Activity {

    private List<Libro> listaLibros;
    private ListView libroListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_libros);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        libroListView = (ListView) findViewById(R.id.listView);
        listaLibros = new ArrayList<Libro>();

    }
}
