package com.manager.menu_actions;

import com.manager.dlgTree;
import com.manager.fractals.CanvasFractalTree_1;
import javax.swing.JFrame;

/**
 *
 * Para mostrar la ventana del Fractal. La clase es declarada final, ya que no tiene sentido heredar de ella
 *
 * @author manager
 */
public final class ActionTree_1 implements IAction {

    @Override
    public void Exec(JFrame frame) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                dlgTree dialog = new dlgTree(frame, new CanvasFractalTree_1());
                dialog.setVisible(true);
            }
        });

    }
}
