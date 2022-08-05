/**
 * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
 * MATRICULA: 20200967
 * CARRERA: TECNOLOGÍAS DE LA INFORMACIÓN
 * CUATRIMESTRE: 4TO
 * GRUPO: C
 */
package com.manager.menu_actions;

import javax.swing.JFrame;

/**
 * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
 *
 * Utilizada para tomar acciones, liberar recursos y otros, y luego cerrar/salir de la aplicacion
 *
 * @author manager
 */
public class ActionSalir implements IAction {

    @Override
    public void Exec(JFrame frame) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

}
