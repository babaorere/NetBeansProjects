/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.utils;

import com.principal.modelos.CuentaModel;

/**
 * @author Baba
 */
public interface CuentaUpdatable {

    public void setCuenta(final CuentaModel incuenta);

    public CuentaModel getCuenta();
}
