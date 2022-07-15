package Controller.Resultado;

import Model.Resultado;
import View.Resultado.ResultadoCruDialog;
import java.awt.Window;
import java.util.Optional;

/**
 *
 */
public class ResultadoCruController {

    public ResultadoCruController() {
    }

    public static void actionSalir(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        }
    }

    public static void CrearModificarView(final java.awt.Window inWin, Optional<Resultado> registroOpt, boolean inEditar) {
        // Activamos la ventana para crear un TipoDeGastos
        java.awt.EventQueue.invokeLater(() -> {
            new ResultadoCruDialog(inWin, registroOpt, inEditar).setVisible(true);
        });
    }

    public static Optional<Integer> Crear(Resultado inItem) throws Exception {
//        return new ResultadoDao().create(inItem);
        return Optional.empty();
    }

    public static Optional<Integer> Modificar(Resultado inItem) throws Exception {
//        return new ResultadoDao().update(inItem);
        return Optional.empty();
    }

}
