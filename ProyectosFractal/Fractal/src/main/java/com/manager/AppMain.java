/*
 * Aplicacion para ver y experimentar con varios tipos de graficos Fractales
 *
 */
 /*
NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
MATRICULA: 20200967
CARRERA: TECNOLOGÍAS DE LA INFORMACIÓN 
CUATRIMESTRE: 4TO
GRUPO: C
 */
package com.manager;

/**
 * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
 *
 * Punto de entrada y ejecucion de la aplicacion
 *
 * @author manager
 */
public class AppMain {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                (new frmAppMenu()).setVisible(true);
            }
        });
    }
}
