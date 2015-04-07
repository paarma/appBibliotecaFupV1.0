package com.developer.pablo.appbibliotecafup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by pablo on 16/03/15.
 */
public class Activity_admin extends Activity {

    Button btnLibro, btnAutor;
    private int opcionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnLibro = (Button) findViewById(R.id.btnLibro);
        btnAutor = (Button) findViewById(R.id.btnAutor);

        //Se genera menu contextual
        registerForContextMenu(btnLibro);
        registerForContextMenu(btnAutor);
    }

    /**
     * Metodos para menu contextual
     * @param menu
     * @param v Componente visual el cual llama al menu contextual
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //Se agrega titulo al menu
        menu.setHeaderTitle("Opciones");

        //Se identifica que boton genera el llamado al menu contextual
        switch (v.getId()){
            case R.id.btnLibro:
                opcionMenu = 1;
                menu.add(0, v.getId(), 0, "Listado Libros");
                menu.add(1, v.getId(), 1, "Crear Libro");
                break;
            case R.id.btnAutor:
                opcionMenu = 2;
                menu.add(2, v.getId(), 0, "opcion 3");
                menu.add(3, v.getId(), 1, "opcion 4");
                break;
        }
    }

    /**
     * Metodo para especificar las acciones del menu seleccionado
     * @param item Item seleccionado del menu
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //return super.onContextItemSelected(item);

        try {

            //Se especifican las acciones para cada boton
            switch (opcionMenu){
                case 1:
                    //Se especifican las acciones de cada item

                    if(item.getGroupId() == 0){
                        Log.i("Acticity_admin.java", "*************> menu libros >>>> opcion: "+item.getTitle());
                        Intent goListadoLibros = new Intent(Activity_admin.this, Activity_listado_libros.class);
                        startActivity(goListadoLibros);
                    }

                    if(item.getGroupId() == 1){
                        Log.i("Acticity_admin.java", "*************> menu libros >>>> opcion: "+item.getTitle());
                        Intent goCrearLibro = new Intent(Activity_admin.this, Activity_crear_libro.class);
                        startActivity(goCrearLibro);
                    }
                    break;

                case 2:
                    //Se especifican las acciones de cada item

                    if(item.getGroupId() == 2){
                        Log.i("Acticity_admin.java", "*************> menu Autores >>>> opcion: "+item.getTitle());
                    }

                    if(item.getGroupId() == 3){
                        Log.i("Acticity_admin.java", "*************> menu Autores >>>> opcion: "+item.getTitle());
                    }
                    break;

            }

        }catch (Exception e){
            return false;
        }

        return true;
    }

}
