package com.manager.actions;

import com.manager.dlgAcercaDe;
import javax.swing.JFrame;

/**
 *
 * @author manager
 */
public class ActionAcercaDe implements IAction {

    @Override
    public void Exec(JFrame frame) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                dlgAcercaDe dialog = new dlgAcercaDe(frame);
                dialog.setVisible(true);
            }
        });

    }
}
