package modelos;

import capipsistema.Conn;
import java.sql.ResultSet;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class UserModel {

    private final Long iduser;
    private final String user;
    private final byte[] pass;
    private final String nombre;
    private final Integer ppto;
    private final Integer banco;
    private final Integer compr;
    private final Integer cau;
    private final Integer imp;
    private final Integer pago;
    private final Integer cfg;
    private final Integer rpt;
    private final String active;
    private final String lastupdate;

    /**
     *
     * @param _rs
     * @throws Exception
     */
    public UserModel(final ResultSet _rs) throws Exception {
        iduser = _rs.getLong("id_user");
        user = _rs.getString("user");
        pass = _rs.getBytes("pass");
        nombre = _rs.getString("nombre");
        ppto = _rs.getInt("ppto");
        banco = _rs.getInt("banco");
        compr = _rs.getInt("compr");
        cau = _rs.getInt("cau");
        imp = _rs.getInt("imp");
        pago = _rs.getInt("pago");
        cfg = _rs.getInt("cfg");
        rpt = _rs.getInt("rpt");
        active = _rs.getString("active");
        lastupdate = _rs.getString("lastupdate");
    }

    /**
     *
     * @param _id_user
     * @return
     */
    public static String getUser(final long _id_user) {
        String auxUser;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user= " + _id_user);
            if (rs.next()) {
                auxUser = rs.getString("user");
            } else {
                auxUser = null;
            }
        } catch (final Exception _ex) {
            auxUser = null;
        }

        return auxUser;
    }

    /**
     *
     * @param _id_user
     * @return
     */
    public static Boolean isActive(final long _id_user) {
        Boolean active;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user=" + _id_user);
            if (rs.next()) {
                active = rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception _ex) {
            active = false;
        }

        return active;
    }

    /**
     *
     * @param _iduser
     * @return
     */
    public static Boolean isPpto(final long _iduser) {
        Boolean active;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user=" + _iduser);
            if (rs.next()) {
                active = rs.getInt("ppto") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception _ex) {
            active = false;
        }

        return active;
    }

    /**
     *
     * @param _iduser
     * @return
     */
    public static Boolean isCompr(final long _iduser) {
        Boolean active;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user=" + _iduser);
            if (rs.next()) {
                active = rs.getInt("compr") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception _ex) {
            active = false;
        }

        return active;
    }

    /**
     *
     * @param _iduser
     * @return
     */
    public static Boolean isCau(final long _iduser) {
        Boolean active;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user=" + _iduser);
            if (rs.next()) {
                active = rs.getInt("cau") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception _ex) {
            active = false;
        }

        return active;
    }

    /**
     *
     * @param _iduser
     * @return
     */
    public static Boolean isImp(final long _iduser) {
        Boolean active;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user=" + _iduser);
            if (rs.next()) {
                active = rs.getInt("imp") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception _ex) {
            active = false;
        }

        return active;
    }

    /**
     *
     * @param _iduser
     * @return
     */
    public static Boolean isPago(final long _iduser) {
        Boolean active;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user=" + _iduser);
            if (rs.next()) {
                active = rs.getInt("pago") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception _ex) {
            active = false;
        }

        return active;
    }

    /**
     *
     * @param _iduser
     * @return
     */
    public static Boolean isBanco(final long _iduser) {
        Boolean active;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user=" + _iduser);
            if (rs.next()) {
                active = rs.getInt("Banco") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception _ex) {
            active = false;
        }

        return active;
    }

    /**
     *
     * @param _iduser
     * @return
     */
    public static Boolean isCfg(final long _iduser) {
        Boolean active;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user=" + _iduser);
            if (rs.next()) {
                active = rs.getInt("cfg") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception _ex) {
            active = false;
        }

        return active;
    }

    /**
     *
     * @param _iduser
     * @return
     */
    public static Boolean isRpt(final long _iduser) {
        Boolean active;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM usuario where id_user=" + _iduser);
            if (rs.next()) {
                active = rs.getInt("rpt") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception _ex) {
            active = false;
        }

        return active;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(getIdUser()) + " " + user;
    }

    /**
     * @return the iduser
     */
    public long getIdUser() {
        return iduser;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the pass
     */
    public byte[] getPass() {
        return pass;
    }

    /**
     * @return the ppto
     */
    public Integer getPpto() {
        return ppto;
    }

    /**
     * @return the banco
     */
    public Integer getBanco() {
        return banco;
    }

    /**
     * @return the compr
     */
    public Integer getCompr() {
        return compr;
    }

    /**
     * @return the cau
     */
    public Integer getCau() {
        return cau;
    }

    /**
     * @return the imp
     */
    public Integer getImp() {
        return imp;
    }

    /**
     * @return the pago
     */
    public Integer getPago() {
        return pago;
    }

    /**
     * @return the cfg
     */
    public Integer getCfg() {
        return cfg;
    }

    /**
     * @return the rpt
     */
    public Integer getRpt() {
        return rpt;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @return the lastupdate
     */
    public String getLastUpdate() {
        return lastupdate;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

}
