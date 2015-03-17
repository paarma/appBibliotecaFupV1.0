package com.developer.pablo.appbibliotecafup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.developer.pablo.appbibliotecafup.modelo.Libro;
import com.developer.pablo.appbibliotecafup.util.LibroListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 16/03/15.
 */
public class Activity_listado_libros extends Activity {

    //private List<Libro> listaLibros;
    private ArrayAdapter<Libro> adapterLibro;
    private ListView libroListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_libros);

        inicializarComponentes();
        inicializarListaLibros();
    }

    private void inicializarComponentes() {
        libroListView = (ListView) findViewById(R.id.listView);
    }

    private void inicializarListaLibros(){
        //////////////ojooooo inicializar el "new ArrayList<Libro>()" con el listado de libros de la bd
        ///////////// crear tarea listado libros
        //adapterLibro = new LibroListAdapter(this, new ArrayList<Libro>());

        List<Libro> listaLibro = new ArrayList<Libro>();

        Libro lib1 = new Libro();
        lib1.setTitulo("Libro 1");
        lib1.setIsbn("123");
        lib1.setCantidad(1);
        listaLibro.add(lib1);

        Libro lib2 = new Libro();
        lib2.setTitulo("Libro 2");
        lib2.setIsbn("456");
        lib2.setCantidad(2);
        listaLibro.add(lib2);

        adapterLibro = new LibroListAdapter(this, listaLibro);
        libroListView.setAdapter(adapterLibro);
    }

}
