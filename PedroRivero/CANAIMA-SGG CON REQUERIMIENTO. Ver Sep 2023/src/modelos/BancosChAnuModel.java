package modelos;

import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class BancosChAnuModel {

    private final long id_ch_anu;
    private final String banco;
    private final String cuenta;
    private final String cheque_anu;
    private final String cheque_nvo;
    private final String monto;
    private final String pag_ava;
    private final long num_pag_ava;
    private final String motivo_anu;
    private final java.sql.Date fecha_anu;
    private final Integer ejefis;
    private final Integer ejefismes;
    private final Integer iduser;

    public BancosChAnuModel() {
        this(0, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public BancosChAnuModel(long inid_ch_anu, String inbanco, String incuenta, String incheque_anu, String incheque_nvo, String inmonto,
        String inpag_ava, Integer innum_pag_ava, String inmotivo_anu, Date infecha_anu,
        Integer inejefis, Integer inejefismes, Integer iniduser) {
        this.id_ch_anu = inid_ch_anu;
        this.banco = inbanco;
        this.cuenta = incuenta;
        this.cheque_anu = incheque_anu;
        this.cheque_nvo = incheque_nvo;
        this.monto = inmonto;
        this.pag_ava = inpag_ava;
        this.num_pag_ava = innum_pag_ava;
        this.motivo_anu = inmotivo_anu;
        this.fecha_anu = infecha_anu;
        this.ejefis = inejefis;
        this.ejefismes = inejefismes;
        this.iduser = iniduser;
    }

    public BancosChAnuModel(ResultSet inrs) throws Exception {
        this( inrs.getLong("id_ch_anu"), inrs.getString("banco"), inrs.getString("cuenta"),
            inrs.getString("cheque_anu"), inrs.getString("cheque_nvo"), inrs.getString("monto"),
            inrs.getString("pag_ava"), inrs.getInt("num_pag_ava"), inrs.getString("motivo_anu"), inrs.getDate("fecha_anu"),
            inrs.getInt("ejefis"), inrs.getInt("ejefismes"), inrs.getInt("iduser"));
    }

    @Override
    public String toString() {
        return String.valueOf(getIdChAnu());
    }

    /**
     * @return the id_ch_anu
     */
    public long getIdChAnu() {
        return id_ch_anu;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @return the cheque_anu
     */
    public String getChequeAnu() {
        return cheque_anu;
    }

    /**
     * @return the cheque_nvo
     */
    public String getChequeNvo() {
        return cheque_nvo;
    }

    /**
     * @return the monto
     */
    public String getMonto() {
        return monto;
    }

    /**
     * @return the pag_ava
     */
    public String getPagAva() {
        return pag_ava;
    }

    /**
     * @return the num_pag_ava
     */
    public long getIdPagAva() {
        return num_pag_ava;
    }

    /**
     * @return the motivo_anu
     */
    public String getMotivoAnu() {
        return motivo_anu;
    }

    /**
     * @return the fecha_anu
     */
    public java.sql.Date getFechaAnu() {
        return fecha_anu;
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
     * @return the iduser
     */
    public Integer getIduser() {
        return iduser;
    }

}
