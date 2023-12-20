/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package capipsistema;

import connection.ConnCapip;
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
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

    }

    static public void trackUser(final String inclass, final String inop, final String inobs) throws Exception {

        if (( inclass == null) || ( inclass.isEmpty())) {
            return;
        }

        final String auxOp = inop.substring(Integer.min( inop.length(), 32));
        final String auxObs = inobs.substring(Integer.min( inobs.length(), 256));

        final Calendar fechaOp = Calendar.getInstance();
        final long ejeFis = fechaOp.get(Calendar.YEAR);
        final long ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;
        final java.sql.Timestamp timeStamp = new java.sql.Timestamp(fechaOp.getTimeInMillis());

        final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO user_menu_track(class,iduser,op,obs,ejefis,ejefismes,userdatetime) VALUES (?,?,?,?,?,?,?)");
        pst.setString(1, inclass);
        pst.setLong(2, UserPassIn.getIdUser());
        pst.setString(3, auxOp == null ? "" : auxOp);
        pst.setString(4, auxObs == null ? "" : auxObs);
        pst.setLong(5, ejeFis);
        pst.setLong(6, ejeFisMes);
        pst.setTimestamp(7, timeStamp);
        pst.executeUpdate();
    }

}
