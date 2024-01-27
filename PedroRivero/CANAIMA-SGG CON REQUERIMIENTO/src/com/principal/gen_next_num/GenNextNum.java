/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.gen_next_num;

import com.principal.capipsistema.Globales;
import com.principal.capipsistema.UserPassIn;
import com.principal.connection.ConnCapip;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase Padre para la generacion de ID de la base de datos, es parecido al autoincrement,
 * la diferencia es que es rollbackable, es decir se puede hechar para atras.
 *
 * @author Baba
 */
public final class GenNextNum {

    static {
    }

    private final String CLAVE;

    private long id;

    /**
     * Inicializa el ID
     * Rev 12-2017
     *
     * @param insub_clave
     */
    public GenNextNum(final String insub_clave) {
        CLAVE = String.valueOf(Globales.getEjeFisYear()) + "-" + insub_clave.trim();
        id = 0;
    }

    /**
     * Rev 24-02-2017
     *
     * @return
     * @throws Exception
     */
    public long checkNum() throws Exception {
        do {
            try (final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM map_next_id WHERE clave = ?")) {
                pst.setString(1, CLAVE);
                try (final ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getLong("valor") + 1;
                    } else {
                        id = 0;
                        // no existe la clave, hay que generarla
                        try (final PreparedStatement pstNew = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO map_next_id" + "(clave, valor, id_user) VALUES (?, ?, ?)")) {
                            pstNew.setString(1, CLAVE);
                            pstNew.setLong(2, 0L);
                            pstNew.setLong(3, UserPassIn.getIdUser());
                            if (pstNew.executeUpdate() != 1) {
                                throw new Exception("Error al tratar de crear un nuevo map_next_id");
                            }
                        }
                    }
                }
            }
            // El ciclo se utiliza, para reiniciar el "select", en caso de haber un "insert"
            // El ciclo se utiliza, para reiniciar el "select", en caso de haber un "insert"
        } while (id == 0);
        return id;
    }

    /**
     * Genera el siguiente ID para la tabla
     * Rev 24-02-2017
     *
     * @throws Exception
     */
    public long nextNum() throws Exception {
        do {
            try (final PreparedStatement pstAbs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM map_next_id WHERE clave = ? FOR UPDATE")) {
                pstAbs.setString(1, CLAVE);
                try (final ResultSet rs = pstAbs.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getLong("valor") + 1;
                    } else {
                        id = 0;
                        // no existe la clave, hay que generarla
                        try (final PreparedStatement pstNew = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO map_next_id" + "(clave, valor, id_user) VALUES (?, ?, ?)")) {
                            pstNew.setString(1, CLAVE);
                            pstNew.setLong(2, 0L);
                            pstNew.setLong(3, UserPassIn.getIdUser());
                            if (pstNew.executeUpdate() != 1) {
                                throw new Exception("Error al tratar de crear un nuevo map_next_id");
                            }
                        }
                    }
                }
            }
        } while (id <= 0);
        return id;
    }

    /**
     * Actualiza el siguiente ID para la tabla
     * Rev 24-02-2017
     *
     * @throws Exception
     */
    public final void update() throws Exception {
        if (id == 0) {
            throw new Exception("ID inválido");
        }
        final PreparedStatement pstAbs = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE map_next_id set valor= ? WHERE clave= ?");
        pstAbs.setLong(1, id);
        pstAbs.setString(2, CLAVE);
        if (pstAbs.executeUpdate() != 1) {
            throw new Exception("Número no actualizado");
        }
    }

    /**
     * @return the CLAVE
     */
    public String getCLAVE() {
        return CLAVE;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    private static final Logger logger = LogManager.getLogger(GenNextNum.class);
}
