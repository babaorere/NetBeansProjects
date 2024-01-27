package com.webapp.elegircandidato;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Candidato {

    private static int nextId;

    private static final List<Candidato> list;

    static {
        nextId = 0;
        list = new ArrayList<>();

        list.add(new Candidato("Jose", "M"));
        list.add(new Candidato("Maria", "F"));
        list.add(new Candidato("Pedro", "M"));
        list.add(new Candidato("Mario", "M"));
    }

    private final int id;
    private final String nombre;
    private final String genero;
    private int votos;

    // contructor
    public Candidato(String inNombre, String inGenero) {
        id = nextId++;
        nombre = inNombre;

        // verificar que el genero sea valido
        if ((inGenero.length() == 1) && "FM".contains(inGenero)) {
            genero = inGenero;
        } else {
            genero = null;
        }

        // inicializar el contador de votos
        votos = 0;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    public static Candidato agregar(Candidato reg) {
        list.add(reg);
        return reg;
    }

    public static List<Candidato> getList() {
        return list;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @return the votos
     */
    public int getVotos() {
        return votos;
    }

    /**
     *
     */
    public void setVotos() {
        this.votos++;
    }

}
