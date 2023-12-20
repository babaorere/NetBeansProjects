package com.principal.test;

import static com.principal.capipsistema.Propiedades.getColLen;
import static com.principal.capipsistema.Propiedades.setColStrLen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author
 */
public class ColLenTest {

    public static void main(String[] args) {
        System.out.println(getColLen("bancos_operaciones", "soporte_o_cheque"));
        System.out.println(setColStrLen("bancos_operaciones", "soporte_o_cheque", "Hola mundo"));
    }

    private static final Logger logger = LogManager.getLogger(ColLenTest.class);
}
