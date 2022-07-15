/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package utils;

import modelos.CuentaModel;

/**
 *
 * @author Baba
 */
public interface CuentaUpdatable {

    public void setCuenta(final CuentaModel _cuenta);

    public CuentaModel getCuenta();

}
