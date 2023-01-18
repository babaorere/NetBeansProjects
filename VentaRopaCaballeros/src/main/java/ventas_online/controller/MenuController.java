package ventas_online.controller;

import java.awt.Window;
import ventas_online.view.*;

/**
 *
 */
public class MenuController {

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

    public static void actionCRUD_Camisa(final java.awt.Window inWin) {
        // verificar que el usuario este logeado
            java.awt.EventQueue.invokeLater(() -> {
                new CamisaDialog(inWin).setVisible(true);
            });
    }

    public static void actionCRUD_Jean(final java.awt.Window inWin) {
            java.awt.EventQueue.invokeLater(() -> {
                new JeanDialog(inWin).setVisible(true);
            });
    }

    public static void actionCRUD_PantalonTela(final java.awt.Window inWin) {
            java.awt.EventQueue.invokeLater(() -> {
                new PantalonTelaDialog(inWin).setVisible(true);
            });
    }

    public static void actionCRUD_Playera(final java.awt.Window inWin) {
            java.awt.EventQueue.invokeLater(() -> {
                new PlayeraDialog(inWin).setVisible(true);
            });
    }

    public static void actionCRUD_RopaInterior(final java.awt.Window inWin) {
            java.awt.EventQueue.invokeLater(() -> {
                new RopaInteriorDialog(inWin).setVisible(true);
            });
    }

    public static void actionCRUD_Zapato(final java.awt.Window inWin) {
            java.awt.EventQueue.invokeLater(() -> {
                new ZapatoDialog(inWin).setVisible(true);
            });
    }

}
