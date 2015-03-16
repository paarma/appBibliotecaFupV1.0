package com.developer.pablo.appbibliotecafup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.developer.pablo.appbibliotecafup.modelo.Libro;

/**
 * Created by pablo on 16/03/15.
 */
public class Activity_crear_libro extends Activity {

    EditText tbxTitulo, tbxIsbn, tbxCantidad;
    Button btnGuardarLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_libro);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        tbxTitulo = (EditText) findViewById(R.id.tbxTitlulo);
        tbxIsbn = (EditText) findViewById(R.id.tbxIsbn);
        tbxCantidad = (EditText) findViewById(R.id.tbxCantidad);

        btnGuardarLibro = (Button) findViewById(R.id.btnGuardarLibro);

    }

    public void crearLibro(View view){
        Libro libro = new Libro();
        libro.setTitulo(tbxTitulo.getText().toString());
        libro.setIsbn(tbxIsbn.getText().toString());
        libro.setCantidad(Integer.valueOf(tbxCantidad.getText().toString()));
    }

}
