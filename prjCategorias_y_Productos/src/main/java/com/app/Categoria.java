package com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class Categoria {

    // Aqui guardaremos la lista de categorias
    private static List<Categoria> list = new ArrayList<>();

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

    @Override
    public String toString() {
        return "{ \t\"id\": " + getId() + ",\n\t\"nombre\": " + getNombre() + ",\n\t\"caracteristicas\": " + getCaracteristicas() + ",\n\t\"estado\": " + getEstado() + " }";
    }

    public static String toStringAll(Boolean estado) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getList().size(); i++) {
            Categoria categoria = list.get(i);
            if (!estado || categoria.getEstado()) {
                sb.append(getList().get(i).toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public static void add(Categoria categoria) {
        list.add(categoria);
        categoria.setId(list.size());
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
     * @return the list
     */
    public static List<Categoria> getList() {
        return list;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
