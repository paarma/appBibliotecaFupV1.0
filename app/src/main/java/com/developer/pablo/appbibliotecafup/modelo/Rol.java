package com.developer.pablo.appbibliotecafup.modelo;

import java.io.Serializable;

/**
 * Created by pablo on 29/03/15.
 */
public class Rol implements Serializable {

    private int idRol;
    private String descripcion;

    public Rol() {
        this.idRol = 0;
        this.descripcion = "";
    }

    public Rol(int idRol, String descripcion) {
        this.idRol = idRol;
        this.descripcion = descripcion;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
