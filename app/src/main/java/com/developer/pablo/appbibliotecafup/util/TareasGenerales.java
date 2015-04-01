package com.developer.pablo.appbibliotecafup.util;

import android.util.Log;

import com.developer.pablo.appbibliotecafup.modelo.Rol;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by pablo on 1/04/15.
 * Clase encargada de contener tareas generales (consultas a la BD) para el sistema
 */
public class TareasGenerales {

    //Objeto de la clase configuracion la cual contiene atributos generales y conf. para conexion al server
    Configuracion conf = new Configuracion();

    //Metodo encargado de obtener el rol seun ID
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
                    rolEncontrado.setIdRol((Integer.parseInt(rolObject.getProperty(0).toString())));
                    rolEncontrado.setDescripcion(rolObject.getProperty(1).toString());
                    Log.i("TareaWSRol", ">>>>>>>>>>>> rolEncontrado: " + rolEncontrado.getDescripcion());
                    break;
                }
            }
        }catch (Exception e){
            Log.d("TareasGenerales.java ", "xxx Error TareaWSRol: "+e.getMessage());
        }
        return rolEncontrado;
    }

}
