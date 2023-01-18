package com.app;

public class Color {

    private String nombre;
    private int codigo;

    public Color() {
        this.nombre = null;
        this.codigo = -1;
    }

    public Color(String in_nombre, int in_codigo) {
        this.nombre = in_nombre;
        this.codigo = in_codigo;
    }

    public String darNombre() {
        return getNombre();
    }

    public int datCodigoColor() {
        return getCodigo();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String in_nombre) {
        this.nombre = in_nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int in_codigo) {
        this.codigo = in_codigo;
    }

}
