package modelos;

import java.sql.ResultSet;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class ModelBancosOperaciones {

    private final Integer id;
    private final Integer cuentaNumOp;
    private final String cuenta;
    private final String banco;
    private final String desc;
    private final String fecha;
    private final String tipo;
    private final String soporte_o_cheque;
    private final String conciliado;
    private final Double debe;
    private final Double haber;
    private final Integer id_trans_ori;

    public ModelBancosOperaciones() {
        this(null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public ModelBancosOperaciones(Integer inid, Integer incuentaNumOp, String incuenta, String inbanco, String indesc, String infecha, String intipo,
        String insoporte_o_cheque, String inconciliado, Double indebe, Double inhaber, Integer inid_trans_ori) {
        this.id = inid;
        this.cuentaNumOp = incuentaNumOp;
        this.cuenta = incuenta;
        this.banco = inbanco;
        this.desc = indesc;
        this.fecha = infecha;
        this.tipo = intipo;
        this.soporte_o_cheque = insoporte_o_cheque;
        this.conciliado = inconciliado;
        this.debe = indebe;
        this.haber = inhaber;
        this.id_trans_ori = inid_trans_ori;
    }

    public ModelBancosOperaciones(final ResultSet inrs) throws Exception {
        this( inrs.getInt("id"), inrs.getInt("cuenta_numop"), inrs.getString("cuenta"), inrs.getString("banco"), inrs.getString("descripcion"),
            inrs.getString("fecha"), inrs.getString("tipo_operacion"), inrs.getString("soporte_o_cheque"), inrs.getString("conciliado"),
            inrs.getDouble("debe"), inrs.getDouble("haber"), inrs.getInt("id_trans_ori"));
    }

    @Override
    public String toString() {
        return getId().toString();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the soporte
     */
    public String getSoporte_o_Cheque() {
        return soporte_o_cheque;
    }

    /**
     * @return the conciliado
     */
    public String getConciliado() {
        return conciliado;
    }

    /**
     * @return the debe
     */
    public Double getDebe() {
        return debe;
    }

    /**
     * @return the haber
     */
    public Double getHaber() {
        return haber;
    }

    /**
     * @return the id_trans_ori
     */
    public Integer getId_trans_ori() {
        return id_trans_ori;
    }

    /**
     * @return the cuentaNumOp
     */
    public Integer getCuentaNumOp() {
        return cuentaNumOp;
    }

}
