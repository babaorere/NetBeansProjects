package com.manager.actions;

import javax.swing.JFrame;

/**
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
