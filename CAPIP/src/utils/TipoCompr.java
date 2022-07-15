/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package utils;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public enum TipoCompr {

    OC("compr_compra", "orden de compra"), OS("compr_servicio", "orden de servicio"), CO("compr_otros", "otros compromisos");

    private final String tbl;
    private final String rpt;

    private TipoCompr(final String _tbl, final String _rpt) {
        tbl = _tbl;
        rpt = _rpt;
    }

    public String getTbl() {
        return tbl;
    }

    public String getRpt() {
        return rpt;
    }

}
