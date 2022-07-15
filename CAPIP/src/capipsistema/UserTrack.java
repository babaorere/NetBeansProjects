/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package capipsistema;

import java.sql.PreparedStatement;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class UserTrack {

    public UserTrack() {
        super();

        try {
            UserTrack.trackUser(getClass().getName(), "INIT", "Tracking");
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

    }

    static public void trackUser(final String _class, final String _op, final String _obs) throws Exception {

        if ((_class == null) || (_class.isEmpty())) {
            return;
        }

        final String auxOp = _op.substring(Integer.min(_op.length(), 32));
        final String auxObs = _obs.substring(Integer.min(_obs.length(), 256));

        final Calendar fechaOp = Calendar.getInstance();
        final long ejeFis = fechaOp.get(Calendar.YEAR);
        final long ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;
        final java.sql.Timestamp timeStamp = new java.sql.Timestamp(fechaOp.getTimeInMillis());

        final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO user_menu_track(class,iduser,op,obs,ejefis,ejefismes,userdatetime) VALUES (?,?,?,?,?,?,?)");
        pst.setString(1, _class);
        pst.setLong(2, UserPassIn.getIdUser());
        pst.setString(3, auxOp == null ? "" : auxOp);
        pst.setString(4, auxObs == null ? "" : auxObs);
        pst.setLong(5, ejeFis);
        pst.setLong(6, ejeFisMes);
        pst.setTimestamp(7, timeStamp);
        pst.executeUpdate();
    }

}
