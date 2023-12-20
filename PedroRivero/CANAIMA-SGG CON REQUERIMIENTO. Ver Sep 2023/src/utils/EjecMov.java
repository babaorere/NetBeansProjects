/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package utils;

/**
 * Rev 29-03-2017
 *
 * @author CAPIP Sistemas C.A.
 */
public enum EjecMov {

    INICIAL(), APROBADO(), CREDITO("CRÉDITO"), TRASPASO(), COMPROMISO(), CAUSADO(), PAGADO(), SALDO();

    final private String str;

    private EjecMov() {
        str = name();
    }

    private EjecMov(final String instr) {
        str = instr;
    }

    @Override
    public String toString() {
        return str;
    }
}
