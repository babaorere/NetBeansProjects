package controlador;

import java.awt.Window;
import vista.DlgVender;
import vista.DlgEliminar;
import vista.DlgListar;


/**
 *
 *
 */
public final class Control {

    public static boolean actionSalir(Window win) {

        Window parent = win.getOwner();

        if (parent != null) {
            win.setVisible(false);
            win.dispose();
            parent.setVisible(true);
        } else {
            System.exit(0);
        }

        return true;
    }


    public static void actionAgregar(java.awt.Window inaThis) {
        java.awt.EventQueue.invokeLater(() -> {
            new DlgVender(inaThis).setVisible(true);
        });
    }


    public static void actionEliminar(java.awt.Window inaThis) {
        java.awt.EventQueue.invokeLater(() -> {
            new DlgEliminar(inaThis).setVisible(true);
        });
    }


    public static void actionListar(java.awt.Window inaThis) {
        java.awt.EventQueue.invokeLater(() -> {
            new DlgListar(inaThis).setVisible(true);
        });
    }

}
