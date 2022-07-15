package Controller;

import Model.Usuario;
import Model.UsuarioDao;
import View.Usuario.UsuarioCruDialog;
import java.awt.Window;
import java.util.Optional;

/**
 *
 */
public class UsuarioCruController {

    public UsuarioCruController() {
    }

    public static void actionSalir(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        }
    }

    public static void CrearModificarView(final java.awt.Window inWin, Optional<Usuario> usuarioOpt, boolean inEditar) {
        // Activamos la ventana para crear un Usuario
        java.awt.EventQueue.invokeLater(() -> {
            new UsuarioCruDialog(inWin, usuarioOpt, inEditar).setVisible(true);
        });
    }

    public static Optional<Integer> CrearUsuario(Usuario inUsuario) throws Exception {
        return new UsuarioDao().create(inUsuario);
    }

    public static Optional<Integer> ModificarUsuario(Usuario inUsuario) throws Exception {
        return new UsuarioDao().update(inUsuario);
    }

}
