package com.developer.pablo.appbibliotecafup.modelo;

import java.io.Serializable;

/**
 * Created by pablo on 15/03/15.
 */
@SuppressWarnings("serial")
public class Libro implements Serializable {

    private int idLibro;
    private String isbn;
    private String titulo;
    private int cantidad;

    public Libro(){
        this.idLibro = 0;
        this.isbn = "";
        this.titulo = "";
        this.cantidad = 0;
    }

    public Libro(int idLibro, String isbn, String titulo, int cantidad) {
        this.idLibro = idLibro;
        this.isbn = isbn;
        this.titulo = titulo;
        this.cantidad = cantidad;
    }

    //Getters
    public int getIdLibro() {
        return idLibro;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    //Setters
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
