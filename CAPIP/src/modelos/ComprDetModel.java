/*
 * Todos los derechos reservados por CAPIP C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package modelos;

import capipsistema.Conn;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.TipoCompr;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class ComprDetModel {

    private final Long id_compr_det;
    private final Long id_compr;
    private final TipoCompr tipo_compr;
    private final BigDecimal cantpro;
    private final String descpro;
    private final BigDecimal punitario;
    private final String codpresu;
    private final String partida;

    /**
     * Rev 24/09/2016
     *
     * @param _rs
     * @throws Exception
     */
    public ComprDetModel(final ResultSet _rs) throws Exception {
        id_compr_det = _rs.getLong("id_compr_det");
        id_compr = _rs.getLong("id_compr");
        tipo_compr = TipoCompr.valueOf(_rs.getString("tipo_compr"));
        cantpro = _rs.getBigDecimal("cantpro");
        descpro = _rs.getString("descpro");
        punitario = _rs.getBigDecimal("punitario");
        codpresu = _rs.getString("codpresu");
        partida = _rs.getString("partida");
    }

    /**
     * Rev 24/09/2016
     *
     * @param _id
     * @return
     * @throws Exception
     */
    public static ComprDetModel getxID(final long _id) throws Exception {
        final ResultSet rs = Conn.executeQuery("SELECT * FROM compr_det WHERE id_compr_det=" + _id);
        if (rs.next()) {
            return new ComprDetModel(rs);
        }

        return null;
    }

    /**
     * Rev 24/09/2016
     *
     * @param _id_compr
     * @param _tipoCompr
     * @return
     * @throws Exception
     */
    public static ArrayList<ComprDetModel> getxIdCompr(final long _id_compr, final TipoCompr _tipoCompr) throws Exception {
        ArrayList<ComprDetModel> list = new ArrayList<>();

        final ResultSet rs = Conn.executeQuery("SELECT * FROM compr_det WHERE id_compr=" + _id_compr + " AND tipo_compr= '" + _tipoCompr.name() + "'");
        while (rs.next()) {
            list.add(new ComprDetModel(rs));
        }

        return list;
    }

    /**
     * Rev 15/09/2016
     *
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(id_compr_det);
    }

    /**
     * @return the id_compr_det
     */
    public Long getId_compr_det() {
        return id_compr_det;
    }

    /**
     * @return the id_compr
     */
    public Long getId_compr() {
        return id_compr;
    }

    /**
     * @return the tipo_compr
     */
    public TipoCompr getTipoCompr() {
        return tipo_compr;
    }

    /**
     * @return the cantpro
     */
    public BigDecimal getCantPro() {
        return cantpro;
    }

    /**
     * @return the descpro
     */
    public String getDescPro() {
        return descpro;
    }

    /**
     * @return the punitario
     */
    public BigDecimal getPUnitario() {
        return punitario;
    }

    /**
     * @return the codpresu
     */
    public String getCodPresu() {
        return codpresu;
    }

    /**
     * @return the partida
     */
    public String getPartida() {
        return partida;
    }

}
