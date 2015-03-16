package com.developer.pablo.appbibliotecafup.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developer.pablo.appbibliotecafup.R;
import com.developer.pablo.appbibliotecafup.modelo.Libro;

import java.util.List;

/**
 * Created by pablo on 16/03/15.
 */
public class LibroListAdapter extends ArrayAdapter<Libro> {

    private Activity ctx;

    public LibroListAdapter(Activity context, List<Libro> libros) {
        super(context, R.layout.list_view_item,libros);
        this.ctx = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null){
             view = ctx.getLayoutInflater().inflate(R.layout.list_view_item,parent,false);
        }

        Libro  libroActual = this.getItem(position);
        inicializarCamposTexto(view, libroActual);
        return view;
    }

    private void inicializarCamposTexto(View view, Libro libroActual) {
        TextView textView = (TextView) view.findViewById(R.id.idTvtitulo);
        textView.setText(libroActual.getTitulo());

        textView = (TextView) view.findViewById(R.id.idTvIsbn);
        textView.setText(libroActual.getIsbn());

        textView = (TextView) view.findViewById(R.id.idTvCantidad);
        textView.setText(libroActual.getCantidad());
    }
}
