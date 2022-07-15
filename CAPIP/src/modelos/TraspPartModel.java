package modelos;

import capipsistema.Globales;
import static capipsistema.Globales.TBL_PPTO_EGRESO;
import static capipsistema.Globales.TBL_PPTO_INGRESO;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import presupuesto.ConnPpto;

/**
 *
 * @author Caipip Sistemas
 */
public class TraspPartModel {

    private final Integer id;
    private final Integer idOri;
    private final String tOri;
    private final Integer idDest;
    private final java.sql.Date fechaGenerar;
    private final String referencia;
    private final java.sql.Date fecha;
    private final BigDecimal monto;
    private final String concepto;
    private final Integer ejefis;
    private final Integer ejeFisMes;
    private final java.sql.Date fchSession;
    private final Integer idSession;
    private final Integer idUser;

    public TraspPartModel() {
        this.id = null;
        this.idOri = null;
        this.tOri = null;
        this.idDest = null;
        this.fechaGenerar = null;
        this.referencia = null;
        this.fecha = null;
        this.monto = null;
        this.concepto = null;
        this.ejefis = null;
        this.ejeFisMes = null;
        this.fchSession = null;
        this.idSession = null;
        this.idUser = null;
    }

    public TraspPartModel(Integer _id, Integer _idori, String _tori, Integer _iddest, Date _fechaGenerar, String _referencia, Date _fecha, BigDecimal _monto, String _concepto, Integer _ejefis, Integer _ejefismes, Date _fchSession, Integer _idSession, Integer _idUser) {
        this.id = _id;
        this.idOri = _idori;
        this.tOri = _tori;
        this.idDest = _iddest;
        this.fechaGenerar = _fechaGenerar;
        this.referencia = _referencia;
        this.fecha = _fecha;
        this.monto = _monto;
        this.concepto = _concepto;
        this.ejefis = _ejefis;
        this.ejeFisMes = _ejefismes;
        this.fchSession = _fchSession;
        this.idSession = _idSession;
        this.idUser = _idUser;
    }

    public TraspPartModel(final ResultSet _rs) throws Exception {
        this.id = _rs.getInt("id");
        this.idOri = _rs.getInt("idori");
        this.tOri = _rs.getString("tori");
        this.idDest = _rs.getInt("iddest");
        this.fechaGenerar = _rs.getDate("fechagenerar");
        this.referencia = _rs.getString("referencia");
        this.fecha = _rs.getDate("fecha");
        this.monto = _rs.getBigDecimal("monto");
        this.concepto = _rs.getString("concepto");
        this.ejefis = _rs.getInt("ejefis");
        this.ejeFisMes = _rs.getInt("ejefismes");
        this.fchSession = _rs.getDate("fchsession");
        this.idSession = _rs.getInt("idsession");
        this.idUser = _rs.getInt("iduser");
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    public static String getPartidaEg(Integer id) throws Exception {
        final String partida;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + TBL_PPTO_EGRESO + " WHERE id=" + id + " AND ejefis=" + Globales.getEjeFisYear());
        if (rs.next()) {
            partida = rs.getString("partida");
        } else {
            throw new Exception("Partida no encontrada");
        }

        return partida;
    }

    public static String getPartidaIn(Integer id) throws Exception {
        final String partida;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + TBL_PPTO_INGRESO + " WHERE id=" + id + " AND ejefis=" + Globales.getEjeFisYear());
        if (rs.next()) {
            partida = rs.getString("partida");
        } else {
            throw new Exception("Partida no encontrada");
        }

        return partida;
    }

    public static String getCodPartEg(Integer id) throws Exception {
        final String partida;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + TBL_PPTO_EGRESO + " WHERE id=" + id + " AND ejefis=" + Globales.getEjeFisYear());
        if (rs.next()) {
            partida = rs.getString("codigo");
        } else {
            throw new Exception("Partida no encontrada");
        }

        return partida;
    }

    public static String getCodPartIn(Integer id) throws Exception {
        final String partida;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + TBL_PPTO_INGRESO + " WHERE id=" + id + " AND ejefis=" + Globales.getEjeFisYear());
        if (rs.next()) {
            partida = rs.getString("codigo");
        } else {
            throw new Exception("Partida no encontrada");
        }

        return partida;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the idori
     */
    public Integer getIdOri() {
        return idOri;
    }

    /**
     * @return the tori
     */
    public String getTori() {
        return tOri;
    }

    /**
     * @return the iddest
     */
    public Integer getIdDest() {
        return idDest;
    }

    /**
     * @return the fechaGenerar
     */
    public java.sql.Date getFechaGenerar() {
        return fechaGenerar;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @return the fecha
     */
    public java.sql.Date getFecha() {
        return fecha;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
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
        return ejeFisMes;
    }

    /**
     * @return the fchSession
     */
    public java.sql.Date getFchSession() {
        return fchSession;
    }

    /**
     * @return the idSession
     */
    public Integer getIdSession() {
        return idSession;
    }

    /**
     * @return the idUser
     */
    public Integer getIdUser() {
        return idUser;
    }

}
