package com.principal.modelos;

import com.principal.connection.ConnCapip;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
public class UserModel {

    private final Long iduser;

    private final String user;

    private final byte[] pass;

    private final String nombre;

    private final Integer ppto;

    private final Integer requerim;

    private final Integer banco;

    private final Integer compr;

    private final Integer cau;

    private final Integer imp;

    private final Integer pago;

    private final Integer cfg;

    private final Integer rpt;

    private final Integer account;

    private final String active;

    private final String lastupdate;

    /**
     * @param inrs
     * @throws Exception
     */
    public UserModel(final ResultSet inrs) throws Exception {
        iduser = inrs.getLong("id_user");
        user = inrs.getString("user");
        pass = inrs.getBytes("pass");
        nombre = inrs.getString("nombre");
        ppto = inrs.getInt("ppto");
        requerim = inrs.getInt("requerim");
        banco = inrs.getInt("banco");
        compr = inrs.getInt("compr");
        cau = inrs.getInt("cau");
        imp = inrs.getInt("imp");
        pago = inrs.getInt("pago");
        cfg = inrs.getInt("cfg");
        rpt = inrs.getInt("rpt");
        account = inrs.getInt("account");
        active = inrs.getString("active");
        lastupdate = inrs.getString("lastupdate");
    }

    /**
     * @param inid_user
     * @return
     */
    public static String getUser(final long inid_user) {
        String auxUser;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user= " + inid_user);
            if (rs.next()) {
                auxUser = rs.getString("user");
            } else {
                auxUser = null;
            }
        } catch (final Exception inex) {
            auxUser = null;
            logger.error(inex);
        }
        return auxUser;
    }

    /**
     * @param inid_user
     * @return
     */
    public static Boolean isActive(final long inid_user) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user=" + inid_user);
            if (rs.next()) {
                active = rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isPpto(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT ppto, active FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt(1) != 0 && rs.getString(2).equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isRequerim(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt("requerim") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isCompr(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt("compr") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isCau(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt("cau") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isImp(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt("imp") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isPago(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt("pago") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isBanco(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt("Banco") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isCfg(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt("cfg") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isRpt(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt("rpt") != 0 && rs.getString("active").equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
     * @param iniduser
     * @return
     */
    public static Boolean isAccount(final long iniduser) {
        Boolean active;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT account, active FROM usuario where id_user=" + iniduser);
            if (rs.next()) {
                active = rs.getInt(1) != 0 && rs.getString(2).equals("true");
            } else {
                active = false;
            }
        } catch (final Exception inex) {
            active = false;
            logger.error(inex);
        }
        return active;
    }

    /**
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

    private static final Logger logger = LogManager.getLogger(UserModel.class);
}
