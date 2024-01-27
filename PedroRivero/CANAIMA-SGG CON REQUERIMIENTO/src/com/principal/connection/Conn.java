/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018 ... 2023
 */
package com.principal.connection;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * @author manager
 */
public interface Conn {

    public Connection getConnection() throws Exception;

    public void CloseConnection() throws Exception;

    public void BeginTransaction() throws Exception;

    public void EndTransaction() throws Exception;

    public void RollBack() throws Exception;

    public ResultSet executeQuery(final String insql) throws Exception;

    public int executeUpdate(final String insql) throws Exception;

    // ************************************************************************
    // This part corresponds to the accounting module
    public void ejecutar(String sql) throws Exception;

    public void eliminar(String sql) throws Exception;

    public ResultSet buscar(String sql) throws Exception;

    public boolean existeDato(String tabla, String campoVerificacion, String datoVerificar, int limite) throws Exception;

    public boolean existeDato2(String tabla, String campo1, String campo2, String dato1, String dato2, int limite) throws Exception;

    public boolean existeDato3(String tabla, String campo1, String campo2, String campo3, String dato1, String dato2, String dato3, int limite) throws Exception;
}
