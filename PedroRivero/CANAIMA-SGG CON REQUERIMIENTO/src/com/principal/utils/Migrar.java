/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.utils;

import com.principal.connection.ConnCapip;
import com.principal.modelos.PagoModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Mirgrar Base de Datos de Rio Chico
 *
 * @author Baba
 */
public class Migrar {

    public static void main(String[] args) {
        // Para todos los registros en en orden_pago
        try (final ResultSet rsPag = ConnCapip.getInstance().executeQuery("SELECT * FROM orden_pago ORDER BY id_orden_pago ASC")) {
            Long num_x_cuenta;
            final HashMap<String, Long> list = new HashMap<>(101);
            while (rsPag.next()) {
                final PagoModel regPag = new PagoModel(rsPag);
                final long id_cuenta;
                try (final ResultSet rsBco = ConnCapip.getInstance().executeQuery("SELECT * FROM bancos WHERE cuenta= '" + regPag.getCuenta() + "'")) {
                    if (rsBco.next()) {
                        id_cuenta = rsBco.getLong("id");
                    } else {
                        id_cuenta = 0;
                    }
                }
                num_x_cuenta = list.get(regPag.getCuenta());
                if (num_x_cuenta != null) {
                    num_x_cuenta++;
                } else {
                    num_x_cuenta = 1L;
                }
                list.put(regPag.getCuenta(), num_x_cuenta);
                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE orden_pago " + "SET id_cuenta= ?, num_x_pag= ?, num_x_cuenta= ? " + "WHERE id_orden_pago = ?");
                pst.setLong(1, id_cuenta);
                pst.setLong(2, regPag.getId_orden_pago());
                pst.setLong(3, num_x_cuenta);
                pst.setLong(4, regPag.getId_orden_pago());
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error al actualizar");
                }
            }
            Set<String> setList = list.keySet();
            Iterator<String> s = setList.iterator();
            while (s.hasNext()) {
                final String key = s.next();
                final Long value = list.get(key);
                System.out.println(key + " - " + value);
            }
        } catch (Exception inex) {
            JOptionPane.showMessageDialog(null, "Error" + System.getProperty("line.separator") + inex);
            logger.error(inex);
        }
    }

    private static final Logger logger = LogManager.getLogger(Migrar.class);
}
