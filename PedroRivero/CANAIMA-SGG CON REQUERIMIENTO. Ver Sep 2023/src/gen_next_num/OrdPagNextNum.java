/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package gen_next_num;

import connection.ConnCapip;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelos.CuentaModel;

/**
 * Clase Padre para la generacion de ID de la base de datos, es parecido al autoincrement, la diferencia es que es rollbackable, es decir se puede hechar para atras.
 *
 * @author Baba
 */
public class OrdPagNextNum {

    /**
     * @return the claveAbs
     */
    public static String getClave_xPag() {
        return clave_xPag;
    }

    private final static String clave_xPag;

    static {
        clave_xPag = String.valueOf(Globales.getEjeFisYear()) + "-PAG";
    }

    private String clave_xCuenta;
    private long id_cuenta;
    private long num_xPag;
    private long num_xCuenta;

    /**
     * Inicializa el ID Rev 24-02-2017
     *
     */
    public OrdPagNextNum() {
        clave_xCuenta = null;
        id_cuenta = 0;
        num_xPag = 0;
        num_xCuenta = 0;
    }

    /**
     * Rev 24-02-2017
     *
     * @param inregCuenta
     * @return
     * @throws Exception
     */
    public long checkNum(CuentaModel inregCuenta) throws Exception {
        setClave_xCuenta( inregCuenta);
        id_cuenta = 0;
        num_xPag = 0;
        num_xCuenta = 0;

        return 0;
    }

    /**
     * Genera el siguiente ID para la tabla Rev 24-02-2017
     *
     * @param inregCuenta
     * @throws Exception
     */
    public void nextNum(CuentaModel inregCuenta) throws Exception {

        num_xPag = 0;
        num_xCuenta = 0;

        if ( inregCuenta != null) {
            id_cuenta = inregCuenta.getId_cuenta();
            clave_xCuenta = String.valueOf(Globales.getEjeFisYear()) + "-PAG-" + inregCuenta.getCuenta();
        } else {
            id_cuenta = 0;
            clave_xCuenta = String.valueOf(Globales.getEjeFisYear()) + "-PAG-" + "00000000000000000000";
        }

        do {
            try (final PreparedStatement pstAbs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM map_next_id WHERE clave = ? FOR UPDATE")) {
                pstAbs.setString(1, clave_xPag);

                try (final ResultSet rsAbs = pstAbs.executeQuery()) {
                    if (rsAbs.next()) {
                        do {
                            try (final PreparedStatement pstRel = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM map_next_id WHERE clave = ? FOR UPDATE")) {
                                pstRel.setString(1, clave_xCuenta);

                                try (final ResultSet rsRel = pstRel.executeQuery()) {
                                    if (rsRel.next()) {
                                        num_xPag = rsAbs.getLong("valor") + 1;
                                        num_xCuenta = rsRel.getLong("valor") + 1;
                                    } else {
                                        // no existe la clave, hay que generarla
                                        try (final PreparedStatement pstNew = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO map_next_id"
                                            + "(clave, valor, id_user) VALUES (?, ?, ?)")) {
                                            pstNew.setString(1, clave_xCuenta);
                                            pstNew.setLong(2, 0L);
                                            pstNew.setLong(3, UserPassIn.getIdUser());

                                            if (pstNew.executeUpdate() != 1) {
                                                throw new Exception("Error al tratar de crear un nuevo map_next_id, relativo");
                                            }
                                        }
                                    }
                                }
                            }
                        } while ((num_xPag == 0) && (num_xCuenta == 0));

                    } else {
                        // no existe la clave, hay que generarla
                        try (final PreparedStatement pstNew = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO map_next_id"
                            + "(clave, valor, id_user) VALUES (?, ?, ?)")) {
                            pstNew.setString(1, clave_xPag);
                            pstNew.setLong(2, 0L);
                            pstNew.setLong(3, UserPassIn.getIdUser());

                            if (pstNew.executeUpdate() != 1) {
                                throw new Exception("Error al tratar de crear un nuevo map_next_id, absoluto");
                            }
                        }
                    }
                }
            }

        } while ((num_xPag == 0) && (num_xCuenta == 0));

    }

    /**
     * Actualiza el siguiente ID para la tabla Rev 24-02-2017
     *
     * @throws Exception
     */
    public final void update() throws Exception {
        if ((num_xPag == 0) || (clave_xCuenta != null) && (num_xCuenta <= 0)) {
            throw new Exception("ID inválido");
        }

        final String clave_xCuentaAux = clave_xCuenta;
        final long num_xCuentaAux = num_xCuenta;
        final long num_xPagAux = num_xPag;

        clave_xCuenta = null;
        num_xCuenta = 0;
        id_cuenta = 0;
        num_xPag = 0;

        final PreparedStatement pstAbs = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE map_next_id set valor= ? WHERE clave= ?");
        pstAbs.setLong(1, num_xPagAux);
        pstAbs.setString(2, clave_xPag);
        if (pstAbs.executeUpdate() != 1) {
            throw new Exception("Número por la Orden de Pago, no actualizado");
        }

        if ((num_xCuentaAux > 0) && (clave_xCuentaAux != null)) {
            final PreparedStatement pstRel = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE map_next_id set valor= ? WHERE clave= ?");
            pstRel.setLong(1, num_xCuentaAux);
            pstRel.setString(2, clave_xCuentaAux);
            if (pstRel.executeUpdate() != 1) {
                throw new Exception("Número por Cuenta de la Orden de Pago, no actualizado");
            }
        }

    }

    /**
     * @return the claveRel
     */
    public String getClave_xCuenta() {
        return clave_xCuenta;
    }

    /**
     * @param inregCuenta
     */
    public void setClave_xCuenta(CuentaModel inregCuenta) {
        if ( inregCuenta == null) {
            clave_xCuenta = String.valueOf(Globales.getEjeFisYear()) + "-PAG-" + "00000000000000000000";
        } else {
            clave_xCuenta = String.valueOf(Globales.getEjeFisYear()) + "-PAG-" + inregCuenta.getCuenta();
        }
    }

    /**
     * @return the num_xPag
     */
    public long getNum_xPag() {
        return num_xPag;
    }

    /**
     * @return the num_xCuenta
     */
    public long getNum_xCuenta() {
        return num_xCuenta;
    }

    /**
     * @return the id_cuenta
     */
    public long getId_cuenta() {
        return id_cuenta;
    }

}
