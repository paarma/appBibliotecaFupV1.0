package com.developer.pablo.appbibliotecafup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.pablo.appbibliotecafup.modelo.Libro;
import com.developer.pablo.appbibliotecafup.util.LibroListAdapter;
import com.developer.pablo.appbibliotecafup.util.TareasGenerales;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 16/03/15.
 */
public class Activity_listado_libros extends Activity {

    private ArrayAdapter<Libro> adapterLibro;
    private ListView libroListView;

    private List<Libro> listaLibro = new ArrayList<Libro>();
    private Libro libroSeleccionado, libroBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_libros);

        //Se obtiene el libro a buscar enviado como parametro si existe
        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null){
            if(getIntent().getExtras().getSerializable("libroBuscar") != null){
                libroBuscar = (Libro) getIntent().getExtras().getSerializable("libroBuscar");
            }
        }

        inicializarComponentes();
        inicializarListaLibros();
    }

    /**
     * Se inicializan los componentes visuales
     */
    private void inicializarComponentes() {
        libroListView = (ListView) findViewById(R.id.listView);

        /**
         * Se inicializa el objeto libroBuscar si este no llega por el
         * buscadorAvanzado de libros. Funcionalidad necesaria para
         * fijar los parametros por defecto para la busqueda de libro
         */
        if(libroBuscar == null){
            libroBuscar = new Libro();
        }
    }

    /**
     * Se carga el listado de libros provenientes de la BD,
     * ademas contiene el evento onclick del item para capturar el mismo
     */
    private void inicializarListaLibros(){

        TareaWsListadoLibros tareaListarLibro = new TareaWsListadoLibros();
        tareaListarLibro.execute();

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

    /**
     * Metodo encargado de redireccionar al buscador avanzado de libros
     */
    public void redireccionaBuscadorLibro(View view){
        Intent goBuscadorLibro = new Intent(Activity_listado_libros.this, BuscadorLibroAvanzado.class);
        startActivity(goBuscadorLibro);
    }


    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    //////////////////////////////////////////////////// tareas
    //Tarea Asíncrona para llamar al WS de consulta en segundo plano

    /**
     * Tarea encargada de listar los libros de la biblioteca
     */
    private class TareaWsListadoLibros extends AsyncTask<String,Integer,Boolean> {

        boolean resultadoTarea = true;

        @SuppressLint("LongLogTag")
        @Override
        protected Boolean doInBackground(String... params) {

            try {
                TareasGenerales tareasGenerales = new TareasGenerales();
                listaLibro = tareasGenerales.buscarLibros(libroBuscar);
            }catch (Exception e){
                resultadoTarea = false;
                Log.d("Activity_listado_libros ", "xxx Error TareaWsListadoLibros: "+e.getMessage());
            }
            return resultadoTarea;
        }

        public void onPostExecute(Boolean result){

            if(result){
                adapterLibro = new LibroListAdapter(Activity_listado_libros.this, listaLibro);
                libroListView.setAdapter(adapterLibro);
            }else{
                String msn = "Error listando libros";
                Toast.makeText(Activity_listado_libros.this, msn, Toast.LENGTH_LONG).show();
            }
        }
    }


}
