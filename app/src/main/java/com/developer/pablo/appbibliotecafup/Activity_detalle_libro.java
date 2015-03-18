package com.developer.pablo.appbibliotecafup;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.developer.pablo.appbibliotecafup.modelo.Libro;

/**
 * Created by pablo on 18/03/15.
 */
public class Activity_detalle_libro extends Activity {

    TextView idTvTitulo, idTvIsbn, idTvCantidad;

    Libro libro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);

        //Se obtiene el libroSeleccionado enviado como parametro
        libro = (Libro) getIntent().getExtras().getSerializable("libroSeleccionado");
        if(libro != null){
            Log.i("Activity_detalle_libro", ">>>>> Titulo libro seleccionado: " + libro.getIsbn());
            inicializarComponentes();
        }
    }

    public void inicializarComponentes(){
        idTvTitulo = (TextView) findViewById(R.id.idTvTitulo);
        idTvIsbn = (TextView) findViewById(R.id.idTvIsbn);
        idTvCantidad = (TextView) findViewById(R.id.idTvCantidad);

        if(libro != null){
            idTvTitulo.setText(libro.getTitulo());
            idTvIsbn.setText(libro.getIsbn());
            idTvCantidad.setText(String.valueOf(libro.getCantidad()));
        }
    }

}
