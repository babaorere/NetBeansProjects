package Controller.TipoDeGasto;

import Model.TipoDeGasto;
import Model.TipoDeGastoDao;
import View.TipoDeGasto.TipoDeGastoCruDialog;
import java.awt.Window;
import java.util.Optional;

/**
 *
 */
public class TipoDeGastoCruController {

    public TipoDeGastoCruController() {
    }

    public static void actionSalir(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        }
    }

    public static void CruView(final java.awt.Window inWin, Optional<TipoDeGasto> registroOpt, boolean inEditar) {
        // Activamos la ventana para crear un TipoDeGastos
        java.awt.EventQueue.invokeLater(() -> {
            new TipoDeGastoCruDialog(inWin, registroOpt, inEditar).setVisible(true);
        });
    }

    public static Optional<Integer> Crear(TipoDeGasto inItem) throws Exception {
        return (new TipoDeGastoDao()).create(inItem);
    }

    public static Optional<Integer> Modificar(TipoDeGasto inItem) throws Exception {
        return (new TipoDeGastoDao()).update(inItem);
    }

}
