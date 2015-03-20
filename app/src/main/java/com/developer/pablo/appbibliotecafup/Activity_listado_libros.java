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
import com.developer.pablo.appbibliotecafup.util.Configuracion;
import com.developer.pablo.appbibliotecafup.util.LibroListAdapter;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by pablo on 16/03/15.
 */
public class Activity_listado_libros extends Activity {

    private ArrayAdapter<Libro> adapterLibro;
    private ListView libroListView;

    private ArrayList<Libro> listaLibro = new ArrayList<Libro>();;
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
        ///////////// crear tarea listado libros
        //adapterLibro = new LibroListAdapter(this, new ArrayList<Libro>());

        /*final List<Libro> listaLibro = new ArrayList<Libro>();

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
        }); */

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


    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    //////////////////////////////////////////////////// tareas
    //Tarea Asíncrona para llamar al WS de consulta en segundo plano

    /**
     * Tarea encargada de listar los libros de la biblioteca
     */
    private class TareaWsListadoLibros extends AsyncTask<String,Integer,Boolean> {

        //Objeto de la clase configuracion la cual contiene atributos generales y conf. para conexion al server
        Configuracion conf = new Configuracion();

        private final String SOAP_ACTION = conf.getUrl()+"/listadoLibros";
        private final String METHOD_NAME = "listadoLibros";
        private final String NAMESPACE = conf.getNamespace();
        private final String URL = conf.getUrl();

        boolean resultadoTarea = true;

        @SuppressLint("LongLogTag")
        @Override
        protected Boolean doInBackground(String... params) {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            //request.addProperty("name",tbxTexto1.getText().toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);
                java.util.Vector<SoapObject> rs = (java.util.Vector<SoapObject>) envelope.getResponse();

                if (rs != null)
                {
                    for (SoapObject libroSoap : rs)
                    {
                        Libro lib = new Libro();
                        lib.setIdLibro(Integer.parseInt(libroSoap.getProperty(0).toString()));
                        lib.setIsbn(libroSoap.getProperty(1).toString());
                        lib.setTitulo(libroSoap.getProperty(2).toString());
                        lib.setCantidad(Integer.parseInt(libroSoap.getProperty(3).toString()));
                        listaLibro.add(lib);
                    }
                }
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
