package com.developer.pablo.appbibliotecafup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

    private Libro libroSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_libros);

        inicializarComponentes();
        inicializarListaLibros();
    }

    /**
     * Se inicializan los componentes visuales
     */
    private void inicializarComponentes() {
        libroListView = (ListView) findViewById(R.id.listView);
    }

    /**
     * Se carga el listado de libros provenientes de la BD,
     * ademas contiene el evento onclick del item para capturar el mismo
     */
    private void inicializarListaLibros(){
        //////////////ojooooo inicializar el "new ArrayList<Libro>()" con el listado de libros de la bd
        ///////////// crear tarea listado libros
        //adapterLibro = new LibroListAdapter(this, new ArrayList<Libro>());

        final List<Libro> listaLibro = new ArrayList<Libro>();

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

        //Evento al seleccionar un elemento de la lista
        libroListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> padre, View vista, int posicion, long id) {
                libroSeleccionado = listaLibro.get(posicion);
                String msn = "Seleccionado :"+libroSeleccionado.getTitulo();
                Toast.makeText(Activity_listado_libros.this, msn, Toast.LENGTH_SHORT).show();
                redireccionaDetalleLibro();
            }
        });
    }

    /**
     * Metodo encargado de redireccionar a la pantalla de detalle libro
     */
    public void redireccionaDetalleLibro(){
        Intent goDetalleLibro = new Intent(Activity_listado_libros.this, Activity_detalle_libro.class);

        //Se envia el libroSeleccionado como parametro para la pagina detalleLibro
        goDetalleLibro.putExtra("libroSeleccionado",libroSeleccionado);
        startActivity(goDetalleLibro);
    }

}
