package Controller;

import Model.UsuarioDao;
import View.Usuario.UsuarioCrudDialog;
import java.awt.Window;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 */
public class UsuarioCrudController {

    public static void actionSalir(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        }
    }

    public static void CrearUsuario(final java.awt.Window inWin) {

        // Procedemos activar la ventana para crear un Usuario
        UsuarioCruController.CrearModificarView(inWin, Optional.empty(), true);

    }

    public static void ModificarUsuario(UsuarioCrudDialog inWin) {

        Integer pk = inWin.getPK();

        // Validar
        if ((pk == null) || (pk <= 0)) {
            return;
        }

        UsuarioDao dao = new UsuarioDao();

        // recupera el usuario de la base datos
        Optional usuarioOpt;
        try {
            usuarioOpt = dao.findId(pk);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(inWin, "Error\n" + ex.toString());
            return;
        }

        // retorna si no existe el usuario
        if (!usuarioOpt.isPresent()) {
            JOptionPane.showMessageDialog(inWin, "No encontrado");
            return;
        }

        // Procedemos activar la ventana para modificar un Usuario
        UsuarioCruController.CrearModificarView(inWin, usuarioOpt, true);

    }

    public static void EliminarUsuario(UsuarioCrudDialog inWin) {
        Integer pk = inWin.getPK();

        // Validar
        if (pk == null || (pk <= 0)) {
            return;
        }

        UsuarioDao dao = new UsuarioDao();

        // recupera el usuario de la base datos
        Optional usuarioOpt;
        try {
            usuarioOpt = dao.delete(pk);
            if (usuarioOpt.isPresent()) {
                JOptionPane.showMessageDialog(inWin, "Eliminado con exito");
            } else {
                JOptionPane.showMessageDialog(inWin, "NO Eliminado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(inWin, "Error\n" + ex.toString());
        }

    }

    public static void ConsultarUsuario(UsuarioCrudDialog inWin) {
        Integer pk = inWin.getPK();

        // Validar
        if (pk == null || (pk <= 0)) {
            return;
        }

        UsuarioDao dao = new UsuarioDao();

        // recupera el usuario de la base datos
        Optional usuarioOpt;
        try {
            usuarioOpt = dao.findId(pk);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(inWin, "Error\n" + ex.toString());
            return;
        }

        // retorna si no existe el usuario
        if (!usuarioOpt.isPresent()) {
            return;
        }

        // Procedemos activar la ventana para consultar un Usuario
        UsuarioCruController.CrearModificarView(inWin, usuarioOpt, false);

    }

}
