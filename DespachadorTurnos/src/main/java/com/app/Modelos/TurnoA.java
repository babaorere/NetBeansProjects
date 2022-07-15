package com.app.Modelos;

/**
 *
 */
public class TurnoA extends Turno {

    static int nextId = 1;

    public TurnoA() {
        super("A", nextId++);
    }

}
