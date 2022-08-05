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

    public BancosChAnuModel(long _id_ch_anu, String _banco, String _cuenta, String _cheque_anu, String _cheque_nvo, String _monto,
        String _pag_ava, Integer _num_pag_ava, String _motivo_anu, Date _fecha_anu,
        Integer _ejefis, Integer _ejefismes, Integer _iduser) {
        this.id_ch_anu = _id_ch_anu;
        this.banco = _banco;
        this.cuenta = _cuenta;
        this.cheque_anu = _cheque_anu;
        this.cheque_nvo = _cheque_nvo;
        this.monto = _monto;
        this.pag_ava = _pag_ava;
        this.num_pag_ava = _num_pag_ava;
        this.motivo_anu = _motivo_anu;
        this.fecha_anu = _fecha_anu;
        this.ejefis = _ejefis;
        this.ejefismes = _ejefismes;
        this.iduser = _iduser;
    }

    public BancosChAnuModel(ResultSet _rs) throws Exception {
        this(_rs.getLong("id_ch_anu"), _rs.getString("banco"), _rs.getString("cuenta"),
            _rs.getString("cheque_anu"), _rs.getString("cheque_nvo"), _rs.getString("monto"),
            _rs.getString("pag_ava"), _rs.getInt("num_pag_ava"), _rs.getString("motivo_anu"), _rs.getDate("fecha_anu"),
            _rs.getInt("ejefis"), _rs.getInt("ejefismes"), _rs.getInt("iduser"));
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
