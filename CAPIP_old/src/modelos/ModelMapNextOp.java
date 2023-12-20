package modelos;

import capipsistema.Conn;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Dada un clave, recupera el siguiente Id secuencial, en incrementos unitarios. Los primeros 4 digitos de la clave, estan conformados por el ejecicio fiscal.
 *
 * @author Capip Sistemas C.A.
 */
public final class ModelMapNextOp {

    public static long getNext(final long _ejeFis, final String _claveParcial) throws Exception {
        if (_ejeFis < 2016) {
            return 0;
        }

        if ((_claveParcial == null) || _claveParcial.isEmpty() || (_claveParcial.length() > 32)) {
            return 0;
        }

        final long secuencial;
        ResultSet rsSec = null;

        // Realizar hasta tres intentos para esperar por el bloqueo del registro
        final String strClave = String.valueOf(_ejeFis) + "-" + _claveParcial;

        for (int i = 0; i < 3; i++) {
            try {
                rsSec = Conn.getConnection().prepareStatement("SELECT * FROM map_next_id WHERE clave= '" + strClave + "' FOR UPDATE",
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE).executeQuery();
                break;
            } catch (final Exception _ex) {
                rsSec = null;
                Random rnd = new Random();
                rnd.setSeed(Globales.getServerTimeStamp().getTime());
                TimeUnit.MILLISECONDS.sleep((int) (rnd.nextDouble() * 100)); // Espera hasta 100msg, hasta volverlo a intentar
            }
        }

        if (rsSec == null) {
            throw new Exception("Error al tratar de generar el secuencial");
        }

        if (rsSec.next()) {
            secuencial = rsSec.getLong("valor") + 1;
            rsSec.updateLong("valor", secuencial);
            rsSec.updateRow();
            rsSec.close();
        } else {
            secuencial = 1;

            final PreparedStatement pstSecuencial = Conn.getConnection().prepareStatement("INSERT INTO map_next_id"
                + "(clave, valor, id_user) "
                + "VALUES (?, ?, ?)");
            pstSecuencial.setString(1, strClave);
            pstSecuencial.setLong(2, secuencial);
            pstSecuencial.setLong(3, UserPassIn.getIdUser());
            pstSecuencial.executeUpdate();
        }

        return secuencial;
    }

}
