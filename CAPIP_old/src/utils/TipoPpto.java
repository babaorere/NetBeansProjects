/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package utils;

/**
 *
 * @author Baba
 */
public enum TipoPpto {

    PRESUPE("presupe"), PRESUPI("presupi");

    private final String tbl;

    private TipoPpto(final String _tbl) {
        tbl = _tbl;
    }

    /**
     * @return the tbl
     */
    public String getTbl() {
        return tbl;
    }

}
