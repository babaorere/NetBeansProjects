package com.app;

import java.util.Objects;

/**
 *
 */
public class Producto {

    // Aqui guardaremos la lista de productos
    private static final Producto list[] = new Producto[100];
    private static int proCant = 0;

    private Integer id;
    private String descripcion;
    private Integer precio;
    private Categoria categoria;
    private Boolean estado;

    public Producto(String descripcion, Categoria categoria, Integer inPrecio, Boolean estado) {
        this.id = null;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = inPrecio;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "{ \"id\": " + id + ",\"descripcion\": " + descripcion + ",\n\t\"categoria\": " + categoria + ",\n\t\"precio\": " + precio + ",\"estado\": " + estado + " }";
    }

    public static String toStringAll(Boolean estado) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < proCant; i++) {
            Producto producto = list[i];
            if (!estado || estado && producto.getEstado()) {
                sb.append(list[i].toString()).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Descripción corta del Producto
     *
     * @return
     */
    public String toShortString() {
        return "{ \"id\": " + id + ",\"descripcion\": " + descripcion + ",\"categoria\": " + categoria.toShortString() + " }";
    }

    /**
     * Listado de descripciones cortas de productos
     *
     * @return
     */
    public static String toShortStringAll(Boolean estado) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < proCant; i++) {
            Producto producto = list[i];
            if (!estado || estado && producto.getEstado()) {
                sb.append(list[i].toShortString()).append("\n");
            }
        }

        return sb.toString();
    }

    public static void add(Producto producto) {
        list[proCant] = producto;
        proCant++;
        producto.setId(proCant);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        if (obj instanceof Producto) {

            Producto tmp = (Producto) obj;
            return super.equals(tmp) && this.getId().equals(tmp.getId());

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the estado
     */
    public Boolean getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the precio
     */
    public Integer getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    /**
     * @return the list
     */
    public static Producto[] getList() {
        return list;
    }

    /**
     * @return the proCant
     */
    public static int getProCant() {
        return proCant;
    }

}
