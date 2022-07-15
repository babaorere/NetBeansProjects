package Controller.TipoDeIngreso;

import Model.TipoDeIngreso;
import Model.TipoDeIngresoDao;
import View.TipoDeIngreso.TipoDeIngresoCrudDialog;
import java.awt.Window;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 */
public class TipoDeIngresoCrudController {

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
        TipoDeIngresoCruController.CruView(inWin, Optional.empty(), true);

    }

    public static void Modificar(TipoDeIngresoCrudDialog inWin) {

        Integer pk = inWin.getPK();

        // Validar
        if ((pk == null) || (pk <= 0)) {
            return;
        }

        TipoDeIngresoDao dao = new TipoDeIngresoDao();

        // recupera el usuario de la base datos
        Optional<TipoDeIngreso> registroOpt;
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
        TipoDeIngresoCruController.CruView(inWin, registroOpt, true);

    }

    public static void Eliminar(TipoDeIngresoCrudDialog inWin) {
        Integer pk = inWin.getPK();

        // Validar
        if (pk == null || (pk <= 0)) {
            return;
        }

        TipoDeIngresoDao dao = new TipoDeIngresoDao();

        // recupera el usuario de la base datos
        Optional<Integer> registroOpt;
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

    public static void Consultar(TipoDeIngresoCrudDialog inWin) {
        Integer pk = inWin.getPK();

        // Validar
        if (pk == null || (pk <= 0)) {
            return;
        }

        TipoDeIngresoDao dao = new TipoDeIngresoDao();

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
        TipoDeIngresoCruController.CruView(inWin, registroOpt, true);

    }

}
