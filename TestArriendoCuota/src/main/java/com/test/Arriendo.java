package com.test;

import java.io.Serializable;


/**
 *
 */
public class Arriendo implements Serializable {

    private static Integer nextId = 1;

    private Integer numArr;
    private String fecArr;
    private Integer diasArr;

    private Cliente cliente;
    private Vehiculo vehiculo;


    public Arriendo(String inFecArr, int inDiasArr) {
        this.numArr = nextId++;
        this.fecArr = inFecArr;
        this.diasArr = inDiasArr;

        cliente = new Cliente("8838025", "Roger", true);
        vehiculo = new Vehiculo("123321", 'D');
    }

}
