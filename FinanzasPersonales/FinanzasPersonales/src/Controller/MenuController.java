package Controller;

import View.AcercaDeDialog;
import View.TipoDeGasto.TipoDeGastoCrudDialog;
import View.TipoDeIngreso.TipoDeIngresoCrudDialog;
import View.Usuario.UsuarioCrudDialog;
import View.Resultado.ResultadoCrudDialog;
import java.awt.Window;

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

    public static void actionCRUD_Usuarios(final java.awt.Window inWin) {
        // verificar que el usuario este logeado
        if (LoginController.getLoginUsuario().isPresent()) {
            java.awt.EventQueue.invokeLater(() -> {
                new UsuarioCrudDialog(inWin).setVisible(true);
            });
        }
    }

    public static void actionCRUD_Ingresos(final java.awt.Window inWin) {
        // verificar que el usuario este logeado
        if (LoginController.getLoginUsuario().isPresent()) {
            java.awt.EventQueue.invokeLater(() -> {
                new TipoDeIngresoCrudDialog(inWin).setVisible(true);
            });
        }
    }

    public static void actionCRUD_Gastos(final java.awt.Window inWin) {
        // verificar que el usuario este logeado
        if (LoginController.getLoginUsuario().isPresent()) {
            java.awt.EventQueue.invokeLater(() -> {
                new TipoDeGastoCrudDialog(inWin).setVisible(true);
            });
        }
    }

    public static void actionCRUD_Resultados(final java.awt.Window inWin) {
        // verificar que el usuario este logeado
        if (LoginController.getLoginUsuario().isPresent()) {
            java.awt.EventQueue.invokeLater(() -> {
                new ResultadoCrudDialog(inWin).setVisible(true);
            });
        }
    }

    public static void ActionAcercaDe(final java.awt.Window inWin) {
        java.awt.EventQueue.invokeLater(() -> {
            new AcercaDeDialog(inWin).setVisible(true);
        });
    }
}
