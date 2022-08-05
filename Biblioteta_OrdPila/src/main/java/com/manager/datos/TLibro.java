package com.manager.datos;

import java.util.Objects;

/**
 * Definicion de la clase para el manejo de Libro
 *
 * @author manager
 */
public class TLibro {

    private int id;
    private String autor;
    private String titulo;
    private int nroCopias;

    public TLibro(int id, String autor, String titulo, int nroCopias) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.nroCopias = nroCopias;
    }

    public int getId() {
        return id;
    }

    public void setId(int idLibro) {
        this.id = idLibro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNroCopias() {
        return nroCopias;
    }

    public void setNroCopias(int nroCopias) {
        this.nroCopias = nroCopias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id == ((TLibro) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
