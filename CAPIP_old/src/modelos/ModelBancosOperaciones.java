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

    public ModelBancosOperaciones(Integer _id, Integer _cuentaNumOp, String _cuenta, String _banco, String _desc, String _fecha, String _tipo,
        String _soporte_o_cheque, String _conciliado, Double _debe, Double _haber, Integer _id_trans_ori) {
        this.id = _id;
        this.cuentaNumOp = _cuentaNumOp;
        this.cuenta = _cuenta;
        this.banco = _banco;
        this.desc = _desc;
        this.fecha = _fecha;
        this.tipo = _tipo;
        this.soporte_o_cheque = _soporte_o_cheque;
        this.conciliado = _conciliado;
        this.debe = _debe;
        this.haber = _haber;
        this.id_trans_ori = _id_trans_ori;
    }

    public ModelBancosOperaciones(final ResultSet _rs) throws Exception {
        this(_rs.getInt("id"), _rs.getInt("cuenta_numop"), _rs.getString("cuenta"), _rs.getString("banco"), _rs.getString("descripcion"),
            _rs.getString("fecha"), _rs.getString("tipo_operacion"), _rs.getString("soporte_o_cheque"), _rs.getString("conciliado"),
            _rs.getDouble("debe"), _rs.getDouble("haber"), _rs.getInt("id_trans_ori"));
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
