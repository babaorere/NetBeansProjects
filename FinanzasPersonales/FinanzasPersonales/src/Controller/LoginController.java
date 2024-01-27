package Controller;

import Model.Usuario;
import Model.UsuarioDao;
import View.MenuFrame;
import View.Usuario.UsuarioLoginDialog;
import java.awt.Window;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 */
public class LoginController {

    static {
        usuarioOpt = Optional.empty();
    }

    // Es estatico para evitar hacer un new de la clase
    private static Optional<Usuario> usuarioOpt;

    public LoginController() {
        usuarioOpt = null;
    }

    // Es estatico para evitar hacer un new de la clase
    public static Optional<Usuario> getLoginUsuario() {
        return usuarioOpt;
    }

    // Es estatico para evitar hacer un new de la clase
    public static void setLoginUsuario(Usuario inUsuario) {
        usuarioOpt = Optional.of(inUsuario);
    }

    public static void actionSalir(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        }
    }

    public static void Login(UsuarioLoginDialog inWin) {

        // Con lo metodos en la ventana de login, recuperamos los datos.
        String nickname = inWin.getNickname();
        char[] passWinAux = inWin.getPassWord();

        // Verificar que el usuario existe
        Optional<Usuario> usuario;
        UsuarioDao dao = new UsuarioDao();
        try {
            usuario = dao.findNickName(nickname);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en Controller.LoginController.Login()\n" + ex.toString());
            return;
        }

        if (!usuario.isPresent()) {
            System.out.println("Error, el usuario no existe");
            return; // Salir
        }

        // Procedemos a comparar el password del Dialog
        if (passWinAux == null) {
            JOptionPane.showMessageDialog(null, "Error el paswword no coincide");
            return;
        }

        // Codificamos el password, que viene de la ventana de dialogo
        byte[] passWin;
        try {
            passWin = MessageDigest.getInstance("MD5").digest(charToByte(passWinAux));
        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al encriptar el password\n" + ex.toString());
            return; // salir
        }

        byte passDB[] = usuario.get().getPassword();
//        if (passDB.length != passWin.length) {
//            JOptionPane.showMessageDialog(null, "Error el paswword no coincide");
//            return;
//        }

        for (int i = 0; i < passWin.length; i++) {
            if (passDB[i] != passWin[i]) {
                System.out.println("Error el paswword no coincide");
                return;
            }
        }

        // Para futura referencia dentro de la aplicacion, por ejemplo verificar que el usuario esta logeado
        usuarioOpt = usuario;

        // Procedemos a cerrar la ventana de login y activamos la ventana principal
        actionSalir(inWin);

        java.awt.EventQueue.invokeLater(() -> {
            new MenuFrame().setVisible(true);
        });

    }

    public static void CrearUsuario(final java.awt.Window inWin) {
        // Procedemos a cerrar la ventana de login y activamos la ventana principal
        actionSalir(inWin);

        // Procedemos activar la ventana para crear un Usuario
        UsuarioCruController.CrearModificarView(null, Optional.empty(), true);

        // Cerrar la ventana de login
        actionSalir(inWin);

    }

    private static byte[] charToByte(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        java.nio.ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
            byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

}
