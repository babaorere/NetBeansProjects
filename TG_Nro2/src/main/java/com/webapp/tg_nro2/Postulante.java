package com.webapp.tg_nro2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Postulante {

    // esto para generar automaticamente el id del postulante
    private static int nextId = 0;

    // aqui guardamos todos los postulantes generados
    private static List<Postulante> list;

    static {
        list = new ArrayList<>();
        Postulante.agregar(new Postulante("1234", "Maria", 50, 49));
        Postulante.agregar(new Postulante("2345", "José", 50, 40));
        Postulante.agregar(new Postulante("3456", "Jesús", 50, 35));
        Postulante.agregar(new Postulante("4567", "Isabel", 50, 29));
        Postulante.agregar(new Postulante("5678", "Carlos", 50, 25));
        Postulante.agregar(new Postulante("6789", "Brayan", 50, 15));
    }

    private int id;             // el id con que se guarda en un arreglo o base de datos
    private String cedula;      // Cedula del postulante
    private String nombre;      // Nombre del postulante,
    private int cantPregRe;  // Cantidad total de preguntas que se le realizaron
    private int cantPregOk;  // Cantidad de preguntas que contestó correctamente

    public Postulante() {
        this(null, null, 0, 0);
    }

    public Postulante(String cedula, String nombre, int cantEje, int cantOk) {
        this.id = nextId++;
        this.cedula = cedula;
        this.nombre = nombre;
        this.cantPregRe = cantEje;
        this.cantPregOk = cantOk;
    }

    // para agregar el postulante, a la lista general
    public static Postulante agregar(Postulante p) {
        list.add(p);
        return p;
    }

    // para obtener un postulante, dado su id
    public static Postulante obtener(int inId) {
        Postulante p;

        if ((inId < 0) || (inId >= list.size())) {
            // valor invalido
            p = null;
        } else {
            p = list.get(inId);
        }

        return p;
    }

    // para obtener la lista de postulante
    public static List<Postulante> obtenerLista() {
        return list;
    }

    @Override
    public String toString() {
        return "{ id=> " + id + " cedula=> " + cedula + " nombre=> " + nombre + " cantPregRe=> " + cantPregRe + " cantPregOk=> " + cantPregOk + " }";
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
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
     * @return the cantEje
     */
    public int getCantPregRe() {
        return cantPregRe;
    }

    /**
     * @param cantPregRe the cantEje to set
     */
    public void setCantPregRe(int cantPregRe) {
        this.cantPregRe = cantPregRe;
    }

    /**
     * @return the cantOk
     */
    public int getCantPregOk() {
        return cantPregOk;
    }

    /**
     * @param cantPregOk the cantOk to set
     */
    public void setCantPregOk(int cantPregOk) {
        this.cantPregOk = cantPregOk;
    }

    /**
     * @return the nextId
     */
    public static int getNextId() {
        return nextId;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the id
     */
    public double getPoncentaje() {
        return Math.round(100.00 * cantPregOk / cantPregRe) / 100.00;
    }

}
