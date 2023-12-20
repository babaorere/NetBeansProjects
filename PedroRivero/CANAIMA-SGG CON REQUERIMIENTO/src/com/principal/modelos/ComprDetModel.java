/*
 * Todos los derechos reservados por CAPIP C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package com.principal.modelos;

import com.principal.connection.ConnCapip;
import com.principal.utils.TipoCompr;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
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
     * @param inrs
     * @throws Exception
     */
    public ComprDetModel(final ResultSet inrs) throws Exception {
        id_compr_det = inrs.getLong("id_compr_det");
        id_compr = inrs.getLong("id_compr");
        tipo_compr = TipoCompr.valueOf(inrs.getString("tipo_compr"));
        cantpro = inrs.getBigDecimal("cantpro");
        descpro = inrs.getString("descpro");
        punitario = inrs.getBigDecimal("punitario");
        codpresu = inrs.getString("codpresu");
        partida = inrs.getString("partida");
    }

    /**
     * Rev 24/09/2016
     *
     * @param inid
     * @return
     * @throws Exception
     */
    public static ComprDetModel getxID(final long inid) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM compr_det WHERE id_compr_det=" + inid);
        if (rs.next()) {
            return new ComprDetModel(rs);
        }
        return null;
    }

    /**
     * Rev 24/09/2016
     *
     * @param inid_compr
     * @param intipoCompr
     * @return
     * @throws Exception
     */
    public static ArrayList<ComprDetModel> getxIdCompr(final long inid_compr, final TipoCompr intipoCompr) throws Exception {
        ArrayList<ComprDetModel> list = new ArrayList<>();
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM compr_det WHERE id_compr=" + inid_compr + " AND tipo_compr= '" + intipoCompr.name() + "'");
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

    private static final Logger logger = LogManager.getLogger(ComprDetModel.class);
}
