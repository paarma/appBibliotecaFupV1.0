package com.developer.pablo.appbibliotecafup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.pablo.appbibliotecafup.modelo.Rol;
import com.developer.pablo.appbibliotecafup.modelo.Usuario;
import com.developer.pablo.appbibliotecafup.util.Configuracion;
import com.developer.pablo.appbibliotecafup.util.TareasGenerales;
import com.developer.pablo.appbibliotecafup.util.TextChangeListener;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Activity_login extends ActionBarActivity {

    private EditText tbxUsuario, tbxClave;
    private Button btnLogin;

    private Usuario usuario;

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
            }
        });
    }

    public void login(View view){
        TareaWSLogin tareaLogin = new TareaWSLogin();
        tareaLogin.execute();
    }

    private void limpiar() {
        tbxUsuario.getText().clear();
        tbxClave.getText().clear();

        //Regresa el foco al campo Usuario
        tbxUsuario.requestFocus();
    }

    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    //////////////////////////////////////////////////// tareas
    //Tarea Asíncrona para llamar al WS de consulta en segundo plano

    /**
     * Tarela login
     */
    private class TareaWSLogin extends AsyncTask<String,Integer,Boolean> {

        //Objeto de la clase configuracion la cual contiene atributos generales y conf. para conexion al server
        Configuracion conf = new Configuracion();

        private final String SOAP_ACTION = conf.getUrl()+"/login";
        private final String METHOD_NAME = "login";
        private final String NAMESPACE = conf.getNamespace();
        private final String URL = conf.getUrl();

        boolean resultadoTarea = true;

        protected Boolean doInBackground(String... params) {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("codigo", tbxUsuario.getText().toString());
            request.addProperty("clave",tbxClave.getText().toString());

            Log.i("Acticity_login.java",">>>>>>>>>>>> login "+tbxUsuario.getText().toString());
            Log.i("Acticity_login.java",">>>>>>>>>>>> password "+tbxClave.getText().toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;

            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                java.util.Vector<SoapObject> rs = (java.util.Vector<SoapObject>) envelope.getResponse();

                if (rs != null)
                {
                    for (SoapObject user : rs)
                    {
                        usuario = new Usuario();
                        usuario.setIdUsuario(Integer.parseInt(user.getProperty(0).toString()));
                        usuario.setCedula(Long.parseLong(user.getProperty(1).toString()));
                        usuario.setNombre(user.getProperty(2).toString());
                        usuario.setApellido(user.getProperty(3).toString());
                        usuario.setTelefono(Long.parseLong(user.getProperty(4).toString()));
                        usuario.setDireccion(user.getProperty(5).toString());
                        usuario.setEmail(user.getProperty(6).toString());
                        usuario.setCodigo(user.getProperty(7).toString());
                        usuario.setClave(user.getProperty(8).toString());

                        //Se carga el rol del usuario
                        TareasGenerales tareasGenerales = new TareasGenerales();
                        Rol rol = tareasGenerales.buscarRol(Integer.parseInt(user.getProperty("ID_ROL").toString()));

                        if(rol != null){
                            usuario.setRol(rol);
                            Log.i("Acticity_login.java",">>>>>>>>>>>> rolDeUsuario: "+usuario.getRol().getDescripcion());
                        }

                        Log.i("Acticity_login.java",">>>>>>>>>>>> idUsuario: "+usuario.getIdUsuario());
                        break;
                    }
                }
            }catch (Exception e){
                resultadoTarea = false;
                Log.d("Activity_login ", "xxx Error TareaWSLogin: "+e.getMessage());
            }
            return resultadoTarea;
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                limpiar();

                //Redireccion segun rol
                if(usuario != null){
                    Intent goInicial = null;

                    //Administrador
                    if(usuario.getRol().getDescripcion().equalsIgnoreCase("ADMIN")){
                        goInicial = new Intent(Activity_login.this, Activity_admin.class);
                    }

                    startActivity(goInicial);

                }else{
                    Toast.makeText(Activity_login.this, "Usuario o Clave incorrectos" , Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
