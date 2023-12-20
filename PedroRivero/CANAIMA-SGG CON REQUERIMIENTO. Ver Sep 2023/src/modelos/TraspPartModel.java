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

    public TraspPartModel(Integer inid, Integer inidori, String intori, Integer iniddest, Date infechaGenerar, String inreferencia, Date infecha, BigDecimal inmonto, String inconcepto, Integer inejefis, Integer inejefismes, Date infchSession, Integer inidSession, Integer inidUser) {
        this.id = inid;
        this.idOri = inidori;
        this.tOri = intori;
        this.idDest = iniddest;
        this.fechaGenerar = infechaGenerar;
        this.referencia = inreferencia;
        this.fecha = infecha;
        this.monto = inmonto;
        this.concepto = inconcepto;
        this.ejefis = inejefis;
        this.ejeFisMes = inejefismes;
        this.fchSession = infchSession;
        this.idSession = inidSession;
        this.idUser = inidUser;
    }

    public TraspPartModel(final ResultSet inrs) throws Exception {
        this.id = inrs.getInt("id");
        this.idOri = inrs.getInt("idori");
        this.tOri = inrs.getString("tori");
        this.idDest = inrs.getInt("iddest");
        this.fechaGenerar = inrs.getDate("fechagenerar");
        this.referencia = inrs.getString("referencia");
        this.fecha = inrs.getDate("fecha");
        this.monto = inrs.getBigDecimal("monto");
        this.concepto = inrs.getString("concepto");
        this.ejefis = inrs.getInt("ejefis");
        this.ejeFisMes = inrs.getInt("ejefismes");
        this.fchSession = inrs.getDate("fchsession");
        this.idSession = inrs.getInt("idsession");
        this.idUser = inrs.getInt("iduser");
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
