/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package modelos;

import capipsistema.Conn;
import capipsistema.Globales;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.Format;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class PresupeModel {

    /**
     * Rev 28/11/2016
     *
     * @param _codpresu
     * @param _monto
     * @return
     * @throws Exception
     */
    public static boolean checkMontoDescontar(final String _codpresu, final BigDecimal _monto) throws Exception {

        if (_codpresu.isEmpty() || (_monto.compareTo(BigDecimal.ZERO) < 0)) {
            throw new Exception("Parámetros inválidos");
        }

        final ResultSet rs = Conn.executeQuery("SELECT * FROM presupe WHERE codigo= '" + _codpresu + "' AND ejefis= " + Globales.getEjeFisYear() + " AND anulado_sn= 'N'");
        if (rs.next()) {
            final BigDecimal montoAct = Format.toBigDec(rs.getDouble("monto"));

            if (montoAct.compareTo(_monto) >= 0) {
                return true;
            }
        } else {
            throw new Exception("Partida no encontrada: " + _codpresu + ", ejercicio fiscal: " + Globales.getEjeFisYear());
        }

        return false;
    }

    /**
     * Rev 07/11/2016
     *
     * @param _codpresu
     * @param _monto
     * @throws Exception
     */
    public static void genComprPttoE(final String _codpresu, final BigDecimal _monto) throws Exception {

        if (_codpresu.isEmpty() || (_monto.compareTo(BigDecimal.ZERO) < 0)) {
            throw new Exception("Parámetros inválidos");
        }

        final long id;
        final long ejefis = Globales.getEjeFisYear();
        final BigDecimal bPeMontoIni;
        try (final ResultSet rs = Conn.executeQuery("SELECT * FROM presupe WHERE codigo='" + _codpresu + "' AND ejefis= " + ejefis + " AND anulado_sn= 'N' FOR UPDATE")) {
            if (rs.next()) {
                id = rs.getLong("id");
                bPeMontoIni = new BigDecimal(rs.getDouble("monto")).setScale(2, RoundingMode.HALF_UP);
            } else {
                throw new Exception("Partida no encontrada: " + _codpresu + ", ejercicio fiscal: " + ejefis);
            }

            final BigDecimal montoFinal = bPeMontoIni.subtract(_monto).setScale(2, RoundingMode.HALF_UP);

            if (montoFinal.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("El saldo final de la partida " + _codpresu + ", resulta negativo");
            }

            try (final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE presupe SET monto= '" + montoFinal.toString() + "' WHERE id = " + id)) {
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error en la cantidad de registros presupe actualizados");
                }
            }
        }
    }

    /**
     * Rev 07/11/2016
     *
     * @param _codpresu
     * @param _monto
     * @throws Exception
     */
    public static void anuComprPttoE(final String _codpresu, final BigDecimal _monto) throws Exception {

        if (_codpresu.isEmpty() || (_monto.compareTo(BigDecimal.ZERO) < 0)) {
            throw new Exception("Parámetros inválidos");
        }

        final long id;
        final long ejefis = Globales.getEjeFisYear();
        final BigDecimal bPeMontoIni;
        try (final ResultSet rs = Conn.executeQuery("SELECT * FROM presupe WHERE codigo= '" + _codpresu + "' AND ejefis= " + ejefis + " AND anulado_sn= 'N' FOR UPDATE")) {
            if (rs.next()) {
                id = rs.getLong("id");
                bPeMontoIni = new BigDecimal(rs.getDouble("monto")).setScale(2, RoundingMode.HALF_UP);
            } else {
                throw new Exception("Partida no encontrada: " + _codpresu + ", ejercicio fiscal: " + ejefis);
            }

            final BigDecimal montoFinal;
            montoFinal = bPeMontoIni.add(_monto).setScale(2, RoundingMode.HALF_UP);

            try (final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE presupe SET monto= '" + montoFinal.toString() + "' WHERE id= " + id)) {
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error en la cantidad de registros presupe actualizados");
                }
            }
        }
    }

}
