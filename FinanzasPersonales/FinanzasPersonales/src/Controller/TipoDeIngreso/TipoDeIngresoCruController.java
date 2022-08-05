package Controller.TipoDeIngreso;

import Model.TipoDeIngreso;
import Model.TipoDeIngresoDao;
import View.TipoDeIngreso.TipoDeIngresoCruDialog;
import java.awt.Window;
import java.util.Optional;

/**
 *
 */
public class TipoDeIngresoCruController {

    public TipoDeIngresoCruController() {
    }

    public static void actionSalir(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        }
    }

    public static void CruView(final java.awt.Window inWin, Optional<TipoDeIngreso> registroOpt, boolean inEditar) {
        // Activamos la ventana para crear un TipoDeGastos
        java.awt.EventQueue.invokeLater(() -> {
            new TipoDeIngresoCruDialog(inWin, registroOpt, inEditar).setVisible(true);
        });
    }

    public static Optional<Integer> Crear(TipoDeIngreso inItem) throws Exception {
        return (new TipoDeIngresoDao()).create(inItem);
    }

    public static Optional<Integer> Modificar(TipoDeIngreso inItem) throws Exception {
        return (new TipoDeIngresoDao()).update(inItem);
    }

}
