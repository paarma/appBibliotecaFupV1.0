package com.developer.pablo.appbibliotecafup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.developer.pablo.appbibliotecafup.modelo.Libro;

/**
 * Created by pablo on 5/04/15.
 */
public class BuscadorLibroAvanzado extends Activity {

    private EditText tbxBusTitulo, tbxBusIsbn;
    private Button btnBuscarLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscador_avanzado_libro);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        tbxBusTitulo = (EditText) findViewById(R.id.tbxBusTitulo);
        tbxBusIsbn = (EditText) findViewById(R.id.tbxBusIsbn);
        btnBuscarLibro = (Button) findViewById(R.id.btnBuscarLibro);

        limpiar();
    }

    private void limpiar() {
        tbxBusTitulo.getText().clear();
        tbxBusIsbn.getText().clear();
    }

    /**
     * metodo encargado de redireccionar a la pagina de
     * listadoLibros con los parametros de busqueda seteados
     */
    public void redirecciconarResultadoBusqueda(View view){

        Libro libroBuscar = new Libro();

        if(!tbxBusTitulo.getText().toString().trim().isEmpty()){
            libroBuscar.setTitulo(tbxBusTitulo.getText().toString());
            Log.i("BuscadorLibroAvanzado.java", " buscando por Titulo: " + tbxBusTitulo.getText().toString());
        }

        if(!tbxBusIsbn.getText().toString().trim().isEmpty()){
            libroBuscar.setIsbn(tbxBusIsbn.getText().toString());
            Log.i("BuscadorLibroAvanzado.java", " buscando por ISBN: " + tbxBusIsbn.getText().toString());
        }

        Intent goBuscadorLibro = new Intent(BuscadorLibroAvanzado.this, Activity_listado_libros.class);
        //Se envian los parametros de busqueda
        goBuscadorLibro.putExtra("libroBuscar",libroBuscar);
        startActivity(goBuscadorLibro);
    }

}
