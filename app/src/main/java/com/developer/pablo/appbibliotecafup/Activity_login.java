package com.developer.pablo.appbibliotecafup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.pablo.appbibliotecafup.util.TextChangeListener;


public class Activity_login extends ActionBarActivity {

    private EditText tbxUsuario, tbxClave;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        tbxUsuario = (EditText) findViewById(R.id.tbxUsuario);
        tbxClave = (EditText) findViewById(R.id.tbxClave);

        //Funcionalidad que habilita el boton Aceptar cuando se ingrese texto
        //en el campo Usuario. Se sobreescribe el metodo onTextChanged con la clase creada TextChangeListener
        tbxUsuario.addTextChangedListener(new TextChangeListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnLogin = (Button) findViewById(R.id.btnLogin);
                btnLogin.setEnabled(!s.toString().trim().isEmpty());
            }
        });
    }

    public void login(View view){
        String msn = String.format(" Usuario ingresado: %s",tbxUsuario.getText());
        //Mensaje visual
        Toast.makeText(this, msn, Toast.LENGTH_SHORT).show();

        //***ojo pendiente llamado tarea login
        Intent goInicial = new Intent(Activity_login.this, Activity_admin.class);
        startActivity(goInicial);
        //*** termina llamado tarea login

        btnLogin.setEnabled(false);
        limpiar();
    }

    private void limpiar() {
        tbxUsuario.getText().clear();
        tbxClave.getText().clear();

        //Regresa el foco al campo Usuario
        tbxUsuario.requestFocus();
    }

}
