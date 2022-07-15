package controlador;

import java.awt.Window;
import vista.DlgArriendoConCuotas;
import vista.DlgCliente;
import vista.DlgPagarCuotasArriendo;
import vista.DlgVehiculo;
import vista.FrmAppMain;


/**
 *
 *
 */
public final class ControlAppMain {

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


    public static void actionDlgArriendoConCuotas(java.awt.Window inaThis) {
        java.awt.EventQueue.invokeLater(() -> {
            new DlgArriendoConCuotas(inaThis).setVisible(true);
        });
    }


    public static void actionDlgPagarCuotasArriendo(java.awt.Window inaThis) {
        java.awt.EventQueue.invokeLater(() -> {
            new DlgPagarCuotasArriendo(inaThis).setVisible(true);
        });
    }


    public static void actionDlgCliente(java.awt.Window inaThis) {
        java.awt.EventQueue.invokeLater(() -> {
            new DlgCliente(inaThis).setVisible(true);
        });
    }


    public static void actionDlgVehiculo(FrmAppMain inaThis) {
        java.awt.EventQueue.invokeLater(() -> {
            new DlgVehiculo(inaThis).setVisible(true);
        });
    }

}
