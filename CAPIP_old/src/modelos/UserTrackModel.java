package modelos;

import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
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

    public UserTrackModel(final Long _id, final Integer _iduser, final String _mnuClass, final String _op, final String _obs, final Integer _ejefis, final Integer _ejefismes, final Timestamp _userDateTime, final Timestamp _servDateTime) {
        this.id = _id;
        this.iduser = _iduser;
        this.mnuClass = _mnuClass;
        this.op = _op;
        this.obs = _obs;
        this.ejefis = _ejefis;
        this.ejefismes = _ejefismes;
        this.userDateTime = _userDateTime;
        this.servDateTime = _servDateTime;
    }

    public UserTrackModel(final ResultSet _rs) throws Exception {
        this(_rs.getLong("id"),
            _rs.getInt("iduser"),
            _rs.getString("class"),
            _rs.getString("op"),
            _rs.getString("obs"),
            _rs.getInt("ejefis"),
            _rs.getInt("ejefismes"),
            _rs.getTimestamp("userdatetime"),
            _rs.getTimestamp("servdatetime"));
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

}
