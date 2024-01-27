package com.principal.modelos;

import com.principal.presupuesto.ConnPpto;
import com.principal.presupuesto.ID;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP Sistemas C.A.
 */
public class PptoModel implements ID<PptoModel> {

    public static PptoModel getReg_x_Id(String intabla, long inid) throws Exception {
        PptoModel aux;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + intabla + " WHERE id= '" + inid + "'");
        if (rs.next()) {
            aux = new PptoModel(rs);
        } else {
            aux = null;
        }
        return aux;
    }

    public static PptoModel getReg_x_Cod(String intabla, final String incodigo) throws Exception {
        PptoModel aux;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + intabla + " WHERE codigo= '" + incodigo + "'");
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

    public PptoModel(int inid, String incodigo, String inpartida, BigDecimal inmonto_ini, BigDecimal inmonto, final long inejefis, final String inanulado_sn, final String incod_contable) {
        id = inid;
        codigo = incodigo == null ? "" : incodigo;
        partida = inpartida;
        monto_ini = inmonto_ini;
        monto = inmonto;
        ejefis = inejefis;
        anulado_sn = inanulado_sn;
        cod_contable = incod_contable == null ? "" : incod_contable;
    }

    public PptoModel(final ResultSet inrs) throws Exception {
        this(inrs.getInt("id"), inrs.getString("codigo"), inrs.getString("partida"), inrs.getBigDecimal("monto_ini"), inrs.getBigDecimal("monto"), inrs.getLong("ejefis"), inrs.getString("anulado_sn"), inrs.getString("cod_contable"));
    }

    @Override
    public String toString() {
        return codigo;
    }

    @Override
    public int compareTo(PptoModel inotro) {
        return toString().compareTo(inotro.toString());
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

    private static final Logger logger = LogManager.getLogger(PptoModel.class);
}
