package com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class Producto {

    // Aqui guardaremos la lista de categorias
    private static final List<Producto> list = new ArrayList<>();

    private Integer id;
    private String descripcion;
    private Categoria categoria;
    private Boolean estado;

    public Producto(String descripcion, Categoria categoria, Boolean estado) {
        this.id = null;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "{ \t\"id\": " + id + ",\n\t\"descripcion\": " + descripcion + ",\n\t\"categoria\": " + categoria + ",\n\t\"estado\": " + estado + " }";
    }

    public static String toStringAll(Boolean estado) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getList().size(); i++) {
            Producto producto = list.get(i);
            if (!estado || estado && producto.getEstado()) {
                sb.append(getList().get(i).toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public static void add(Producto producto) {
        list.add(producto);
        producto.setId(list.size());
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
     * @return the list
     */
    public static List<Producto> getList() {
        return list;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
