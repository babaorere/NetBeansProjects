package Controller.TipoDeGasto;

import Model.TipoDeGastoDao;
import View.TipoDeGasto.TipoDeGastoCrudDialog;
import java.awt.Window;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 */
public class TipoDeGastoCrudController {

    public static void actionSalir(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        }
    }

    public static void Crear(final java.awt.Window inWin) {

        // Procedemos activar la ventana para crear un Usuario
        TipoDeGastoCruController.CruView(inWin, Optional.empty(), true);

    }

    public static void Modificar(TipoDeGastoCrudDialog inWin) {

        Integer pk = inWin.getPK();

        // Validar
        if ((pk == null) || (pk <= 0)) {
            return;
        }

        TipoDeGastoDao dao = new TipoDeGastoDao();

        // recupera el usuario de la base datos
        Optional registroOpt;
        try {
            registroOpt = dao.findId(pk);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(inWin, "Error\n" + ex.toString());
            return;
        }

        // retorna si no existe el registro
        if (!registroOpt.isPresent()) {
            JOptionPane.showMessageDialog(inWin, "No encontrado");
            return;
        }

        // Procedemos activar la ventana para modificar un Usuario
        TipoDeGastoCruController.CruView(inWin, registroOpt, true);

    }

    public static void Eliminar(TipoDeGastoCrudDialog inWin) {
        Integer pk = inWin.getPK();

        // Validar
        if (pk == null || (pk <= 0)) {
            return;
        }

        TipoDeGastoDao dao = new TipoDeGastoDao();

        // recupera el usuario de la base datos
        Optional registroOpt;
        try {
            registroOpt = dao.delete(pk);
            if (registroOpt.isPresent()) {
                JOptionPane.showMessageDialog(inWin, "Eliminado con exito");
            } else {
                JOptionPane.showMessageDialog(inWin, "NO Eliminado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(inWin, "Error\n" + ex.toString());
        }

    }

    public static void Consultar(TipoDeGastoCrudDialog inWin) {
        Integer pk = inWin.getPK();

        // Validar
        if (pk == null || (pk <= 0)) {
            return;
        }

        TipoDeGastoDao dao = new TipoDeGastoDao();

        // recupera el registro de la base datos
        Optional registroOpt;
        try {
            registroOpt = dao.findId(pk);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(inWin, "Error\n" + ex.toString());
            return;
        }

        // retorna si no existe el registro
        if (!registroOpt.isPresent()) {
            return;
        }

        // Procedemos activar la ventana para consultar un registro
        TipoDeGastoCruController.CruView(inWin, registroOpt, false);

    }

}
