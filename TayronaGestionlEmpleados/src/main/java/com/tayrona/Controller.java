package com.tayrona;

import java.awt.Window;

/**
 *
 */
public class Controller {

    public static void actionSalir(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        } else {
            System.exit(0);
        }
    }

    public static void actionAppFrame(final java.awt.Window inWin) {
        java.awt.EventQueue.invokeLater(() -> {
            new AppFrame().setVisible(true);
        });
    }

    public static void actionRegistrarDialog(final java.awt.Window inWin) {
        java.awt.EventQueue.invokeLater(() -> {
            new RegistroDialog(inWin).setVisible(true);
        });
    }

    public static void actionTableDialog(final java.awt.Window inWin) {
        java.awt.EventQueue.invokeLater(() -> {
            new TablaDialog(inWin).setVisible(true);
        });
    }

    public static void actionConsultarForm(final java.awt.Window inWin, Integer idx) {
        java.awt.EventQueue.invokeLater(() -> {
            new ConsultarDialog(inWin, idx).setVisible(true);
        });
    }

}
