package modelos;

import java.math.BigDecimal;
import java.sql.ResultSet;
import presupuesto.ConnPpto;
import presupuesto.ID;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class PptoModel implements ID<PptoModel> {

    public static PptoModel getReg_x_Id(String _tabla, long _id) throws Exception {
        PptoModel aux;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + _tabla + " WHERE id= '" + _id + "'");

        if (rs.next()) {
            aux = new PptoModel(rs);
        } else {
            aux = null;
        }

        return aux;
    }

    public static PptoModel getReg_x_Cod(String _tabla, final String _codigo) throws Exception {
        PptoModel aux;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + _tabla + " WHERE codigo= '" + _codigo + "'");

        if (rs.next()) {
            aux = new PptoModel(rs);
        } else {
            aux = null;
        }

        return aux;
    }

    private final int id;
    private final String codigo;
    private final String partida;
    private final BigDecimal monto_ini;
    private final BigDecimal monto;
    private final long ejefis;
    private final String anulado_sn;
    private final String cod_contable;

    public PptoModel(int _id, String _codigo, String _partida, BigDecimal _monto_ini, BigDecimal _monto, final long _ejefis, final String _anulado_sn, final String _cod_contable) {
        id = _id;
        codigo = _codigo == null ? "" : _codigo;
        partida = _partida;
        monto_ini = _monto_ini;
        monto = _monto;
        ejefis = _ejefis;
        anulado_sn = _anulado_sn;
        cod_contable = _cod_contable == null ? "" : _cod_contable;
    }

    public PptoModel(final ResultSet _rs) throws Exception {
        this(_rs.getInt("id"), _rs.getString("codigo"), _rs.getString("partida"), _rs.getBigDecimal("monto_ini"), _rs.getBigDecimal("monto"), _rs.getLong("ejefis"), _rs.getString("anulado_sn"), _rs.getString("cod_contable"));
    }

    @Override
    public String toString() {
        return codigo;
    }

    @Override
    public int compareTo(PptoModel _otro) {
        return toString().compareTo(_otro.toString());
    }

    /**
     * @return the id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the partida
     */
    public String getPartida() {
        return partida;
    }

    /**
     * @return the monto_ini
     */
    public BigDecimal getMontoIni() {
        return monto_ini;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @return the ejefis
     */
    public long getEjefis() {
        return ejefis;
    }

    /**
     * @return the anulado_sn
     */
    public String getAnulado_sn() {
        return anulado_sn;
    }

    /**
     * @return the cod_contable
     */
    public String getCod_contable() {
        return cod_contable;
    }

}
