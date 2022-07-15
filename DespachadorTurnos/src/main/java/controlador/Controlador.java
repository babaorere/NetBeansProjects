package controlador;

import com.app.DlgDespachador;
import java.awt.Window;


/**
 *
 *
 */
public final class Controlador {

    public static boolean actionSalir(Window win) {

        win.setVisible(false);
        win.dispose();

        final Window parent = win.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        } else {
            System.exit(0);
        }

        return true;
    }


    public static void actionDespachador(java.awt.Window inaThis) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new DlgDespachador(inaThis).setVisible(true);
        });
    }

}
