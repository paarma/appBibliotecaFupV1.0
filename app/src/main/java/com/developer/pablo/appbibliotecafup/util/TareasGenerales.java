package com.developer.pablo.appbibliotecafup.util;

import android.util.Log;

import com.developer.pablo.appbibliotecafup.modelo.Libro;
import com.developer.pablo.appbibliotecafup.modelo.Rol;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 1/04/15.
 * Clase encargada de contener tareas generales (consultas a la BD) para el sistema
 */
public class TareasGenerales {

    /**
     * Objeto de la clase configuracion la cual contiene atributos
     * generales y conf. para conexion al server
     */
    Configuracion conf = new Configuracion();

    /**
     * Metodo encargado de retornar un Rol dependiendo de su idRol
     * @param idRol
     * @return Rol segun idRol
     */
    public Rol buscarRol(int idRol){

        final String SOAP_ACTION = conf.getUrl()+"/buscarRol";
        final String METHOD_NAME = "buscarRol";
        final String NAMESPACE = conf.getNamespace();
        final String URL = conf.getUrl();
        Rol rolEncontrado = null;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("idRol", idRol);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;

        HttpTransportSE transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            java.util.Vector<SoapObject> rs = (java.util.Vector<SoapObject>) envelope.getResponse();

            if (rs != null)
            {
                for (SoapObject rolObject : rs)
                {
                    rolEncontrado = new Rol();
                    rolEncontrado.setIdRol((Integer.parseInt(rolObject.getProperty("ID_ROL").toString())));
                    rolEncontrado.setDescripcion(rolObject.getProperty("DESCRIPCION").toString());
                    Log.i("TareaWSRol", ">>>>>>>>>>>> rolEncontrado: " + rolEncontrado.getDescripcion());
                    break;
                }
            }
        }catch (Exception e){
            Log.d("TareasGenerales.java ", "xxx Error buscarRol(): "+e.getMessage());
        }
        return rolEncontrado;
    }

    /**
     * Metodo encargado de retornar el listado de libros segun busqueda
     * @return ListadoLibros
     */
    public List<Libro> buscarLibros(){

        final String SOAP_ACTION = conf.getUrl()+"/listadoLibros";
        final String METHOD_NAME = "listadoLibros";
        final String NAMESPACE = conf.getNamespace();
        final String URL = conf.getUrl();
        List<Libro> listaLibro = new ArrayList<Libro>();

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
                    lib.setIdLibro(Integer.parseInt(libroSoap.getProperty("ID_LIBRO").toString()));
                    lib.setIsbn(libroSoap.getProperty("ISBN").toString());
                    lib.setTitulo(libroSoap.getProperty("TITULO").toString());
                    lib.setCantidad(Integer.parseInt(libroSoap.getProperty("CANTIDAD").toString()));
                    listaLibro.add(lib);
                }
            }
        }catch (Exception e){
            Log.d("TareasGenerales.java ", "xxx Error buscarLibros(): "+e.getMessage());
        }
        return listaLibro;
    }

}
