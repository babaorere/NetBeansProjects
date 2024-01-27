package com.principal.modelos;

import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
public final class UserTrackModel {

    private final Long id;

    private final Integer iduser;

    private final String mnuClass;

    private final String op;

    private final String obs;

    private final Integer ejefis;

    private final Integer ejefismes;

    private final Timestamp userDateTime;

    private final Timestamp servDateTime;

    public UserTrackModel() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public UserTrackModel(final Long inid, final Integer iniduser, final String inmnuClass, final String inop, final String inobs, final Integer inejefis, final Integer inejefismes, final Timestamp inuserDateTime, final Timestamp inservDateTime) {
        this.id = inid;
        this.iduser = iniduser;
        this.mnuClass = inmnuClass;
        this.op = inop;
        this.obs = inobs;
        this.ejefis = inejefis;
        this.ejefismes = inejefismes;
        this.userDateTime = inuserDateTime;
        this.servDateTime = inservDateTime;
    }

    public UserTrackModel(final ResultSet inrs) throws Exception {
        this(inrs.getLong("id"), inrs.getInt("iduser"), inrs.getString("class"), inrs.getString("op"), inrs.getString("obs"), inrs.getInt("ejefis"), inrs.getInt("ejefismes"), inrs.getTimestamp("userdatetime"), inrs.getTimestamp("servdatetime"));
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the iduser
     */
    public Integer getIdUser() {
        return iduser;
    }

    /**
     * @return the idmenu
     */
    public String getMnuClass() {
        return mnuClass;
    }

    /**
     * @return the op
     */
    public String getOp() {
        return op;
    }

    /**
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

    /**
     * @return the ejefis
     */
    public Integer getEjeFis() {
        return ejefis;
    }

    /**
     * @return the ejefismes
     */
    public Integer getEjeFisMes() {
        return ejefismes;
    }

    /**
     * @return the userDateTime
     */
    public Timestamp getUserDateTime() {
        return userDateTime;
    }

    /**
     * @return the servDateTime
     */
    public Timestamp getServDateTime() {
        return servDateTime;
    }

    private static final Logger logger = LogManager.getLogger(UserTrackModel.class);
}
