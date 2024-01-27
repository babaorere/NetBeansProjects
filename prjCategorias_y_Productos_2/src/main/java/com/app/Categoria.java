package com.app;

import java.util.Objects;

/**
 *
 */
public class Categoria {

    // Aqui guardaremos la lista de categorias
    private static Categoria list[] = new Categoria[100];
    private static int catCant = 0;

    private Integer id;
    private String nombre;
    private String caracteristicas;
    private Boolean estado;

    public Categoria(String inNombre, String inCaracteristicas, Boolean inEstado) {
        this.id = null;
        this.nombre = inNombre;
        this.caracteristicas = inCaracteristicas;
        this.estado = inEstado;
    }

    /**
     * Descripción completa de la categoría
     *
     * @return
     */
    @Override
    public String toString() {
        return "{ \"id\": " + getId() + ",\"nombre\": " + getNombre() + ",\"caracteristicas\": " + getCaracteristicas() + ",\"estado\": " + getEstado() + " }";
    }

    /**
     * Descripción corta de la Categoría
     *
     * @return
     */
    public String toShortString() {
        return "{ \"id\": " + getId() + ",\"nombre\": " + getNombre() + " }";
    }

    /**
     * Listado de descripciones completas de las categorias
     *
     * @return
     */
    public static String toStringAll(Boolean estado) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getCatCant(); i++) {
            Categoria categoria = getList()[i];
            if (!estado || categoria.getEstado()) {
                sb.append(list[i].toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Listado de descripciones cortas de las categorias
     *
     * @return
     */
    public static String toShortStringAll(Boolean estado) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getCatCant(); i++) {
            Categoria categoria = getList()[i];
            if (!estado || categoria.getEstado()) {
                sb.append(list[i].toShortString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public static void add(Categoria categoria) {
        list[getCatCant()] = categoria;
        catCant++;
        categoria.setId(getCatCant());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Categoria) {

            Categoria tmp = (Categoria) obj;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the caracteristicas
     */
    public String getCaracteristicas() {
        return caracteristicas;
    }

    /**
     * @param caracteristicas the caracteristicas to set
     */
    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    /**
     * @return the estado
     */
    public Boolean isEstado() {
        return getEstado();
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the estado
     */
    public Boolean getEstado() {
        return estado;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the list
     */
    public static Categoria[] getList() {
        return list;
    }

    /**
     * @return the catCant
     */
    public static int getCatCant() {
        return catCant;
    }

}
