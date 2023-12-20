/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package gen_next_num;

import connection.ConnCapip;
import capipsistema.UserPassIn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelos.CuentaModel;

/**
 *
 * @author Baba
 */
public final class GenNum_xCuenta extends OrdPagNextNum {

    public GenNum_xCuenta() {
        super();
    }

    /**
     * Rev 28-02-2017
     *
     * @param inregCuenta
     * @return
     * @throws Exception
     */
    @Override
    public long checkNum(CuentaModel inregCuenta) throws Exception {
        super.checkNum( inregCuenta);

        long idAux;

        // A manera de seguro, cuenta la veces que se ha producido el ciclo
        long cont = 0;
        do {
            idAux = 0;
            try (final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM map_next_id WHERE clave = ?")) {
                pst.setString(1, getClave_xCuenta());

                try (final ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        idAux = rs.getLong("valor") + 1;
                    } else {
                        // no existe la clave, hay que generarla
                        try (final PreparedStatement pstNew = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO map_next_id"
                            + "(clave, valor, id_user) VALUES (?, ?, ?)")) {
                            pstNew.setString(1, getClave_xCuenta());
                            pstNew.setLong(2, 0L);
                            pstNew.setLong(3, UserPassIn.getIdUser());

                            if (pstNew.executeUpdate() != 1) {
                                throw new Exception("Error al tratar de crear un nuevo map_next_id, absoluto");
                            }
                        }
                    }
                }
            }

            cont++;

            // El ciclo se utiliza, para reiniciar el "select", en caso de haber un "insert"
        } while ((idAux == 0) && cont < 2);

        // Sintoma de error
        if (idAux == 0) {
            setClave_xCuenta(null);
            throw new Exception("ID inválido");
        }

        return idAux;
    }

}
