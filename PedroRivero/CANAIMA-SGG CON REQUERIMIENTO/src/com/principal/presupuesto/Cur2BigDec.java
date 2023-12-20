/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.presupuesto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utilidad para construir un numero BigDecimal, del tipo Decimal64. y una Escala de 2 digitos.
 *
 * Decimal64 format, 16 digits, and a rounding mode of HALF_UP,
 *
 * @author CAPIP Sistemas C.A.
 */
public final class Cur2BigDec {

    /**
     * Para guardar la ultima operacion de [parse]
     */
    private static BigDecimal lastOp = null;

    /**
     * Convierte un [long], se guarda el resultado en [lastOp]
     *
     * @param numero
     * @return Un BigDecimal cuyo contenido, es la representacion del numero
     */
    public static BigDecimal parse(long numero) {
        try {
            lastOp = new BigDecimal(numero, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
        } catch (final Exception e) {
            lastOp = null;
            logger.error(e);
        }
        return lastOp;
    }

    /**
     * Convierte un [double], se guarda el resultado en [lastOp]
     *
     * @param numero
     * @return Un BigDecimal cuyo contenido, es la representacion del numero
     */
    public static BigDecimal parse(double numero) {
        try {
            lastOp = new BigDecimal(numero, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
        } catch (final Exception e) {
            lastOp = null;
            logger.error(e);
        }
        return lastOp;
    }

    /**
     * Convierte un [String], se guarda el resultado en [lastOp]
     *
     * @param numero
     * @return Un BigDecimal cuyo contenido, es la representacion del numero
     */
    public static BigDecimal parse(String numero) {
        try {
            //String auxNum = numero.replace(".", "").replace(",", ".");
            //lastOp = new BigDecimal(Globales.curFormat.stringToValue(numero).toString(), MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
            lastOp = (BigDecimal) PptoGlobales.curFormat.stringToValue(numero);
        } catch (final Exception e) {
            lastOp = null;
            logger.error(e);
        }
        return lastOp;
    }

    /**
     * Convierte un [Object], utilizando la funcion toString(), se guarda el resultado en [lastOp]
     *
     * @param numero
     * @return Un BigDecimal cuyo contenido, es la representacion del numero
     */
    public static BigDecimal parse(Object numero) {
        try {
            lastOp = new BigDecimal(numero.toString(), MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
        } catch (final Exception e) {
            lastOp = null;
            logger.error(e);
        }
        return lastOp;
    }

    public static String format(BigDecimal numero) {
        try {
            return PptoGlobales.curFormat.valueToString(numero == null ? BigInteger.ZERO : numero);
        } catch (ParseException inex) {
            logger.error(inex);
        }
        return "error";
    }

    /**
     * Retorna el resultado de la ultima operacion de conversion
     *
     * @return lastOp
     */
    public static BigDecimal getLastOp() {
        return lastOp;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BigDecimal a = Cur2BigDec.parse(1233.1415);
        BigDecimal b = Cur2BigDec.parse("3.1415");
        try {
            System.out.println(PptoGlobales.curFormat.valueToString(a));
            System.out.println(PptoGlobales.curFormat.valueToString(b));
        } catch (ParseException inex) {
            logger.error(inex);
        }
    }

    private static final Logger logger = LogManager.getLogger(Cur2BigDec.class);
}
