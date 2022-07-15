package com.manager.actions;

import com.manager.dlgRegUser;
import javax.swing.JFrame;

/**
 *
 * Para mostrar la ventana de "Lista Doble"/"Registro Usuario"
 *
 * @author manager
 */
public final class ActionRegUser implements IAction {

    @Override
    public void Exec(JFrame frame) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                dlgRegUser dialog = new dlgRegUser(frame);
                dialog.setVisible(true);
            }
        });

    }
}
