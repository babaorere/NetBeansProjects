/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.utils;

import com.principal.capipsistema.Globales;
import static java.lang.System.exit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP Sistemas C.A.
 */
public final class Format {

    private static final Logger logger = LogManager.getLogger(Format.class);

    private static final DecimalFormat decFormat;

    private static final DecimalFormat intFormat;

    private static final SimpleDateFormat dateFormat;

    private static final SimpleDateFormat timeStampFormat;

    static {
        DecimalFormat auxDecFormat = null;
        DecimalFormat auxIntFormat = null;
        SimpleDateFormat auxDateFormat = null;
        SimpleDateFormat auxTimeStampFormat = null;
        try {
            auxDecFormat = new DecimalFormat("#,##0.00");
            auxDecFormat.setParseBigDecimal(true);
            auxDecFormat.setGroupingUsed(true);
            auxDecFormat.setMaximumFractionDigits(2);
            auxDecFormat.setMinimumFractionDigits(2);
            auxDecFormat.setRoundingMode(RoundingMode.HALF_UP);
            auxIntFormat = new DecimalFormat("#,##0");
            auxIntFormat.setParseBigDecimal(true);
            auxIntFormat.setGroupingUsed(true);
            auxIntFormat.setMaximumFractionDigits(0);
            auxIntFormat.setMinimumFractionDigits(0);
            auxIntFormat.setRoundingMode(RoundingMode.HALF_UP);
            auxDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            auxTimeStampFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            exit(1);
        }
        decFormat = auxDecFormat;
        intFormat = auxIntFormat;
        dateFormat = auxDateFormat;
        timeStampFormat = auxTimeStampFormat;
    }

    private Format() {
        super();
    }

    ///////////////////////////
    // toLong
    /**
     * @param inin
     * @return
     */
    public static long toLong(final double inin) {
        return (long) inin;
    }

    /**
     * @param inin
     * @return
     */
    public static long toLong(final BigDecimal inin) {
        return inin.longValue();
    }

    /**
     * @param inin
     * @return
     * @throws Exception
     */
    public static long toLong(final String inin) throws Exception {
        return ((BigDecimal) intFormat.parse(inin.trim())).longValue();
    }

    /**
     * @param inin
     * @return
     */
    public static long toLong(final java.sql.Date inin) {
        return inin.getTime();
    }

    ///////////////////////////
    // toDouble
    /**
     * @param inin
     * @return
     */
    public static double toDouble(final long inin) {
        return (double) inin;
    }

    /**
     * @param indbl
     * @return
     */
    public static double toDouble(final double indbl) {
        return BigDecimal.valueOf(indbl).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * @param inbigDec
     * @return
     */
    public static double toDouble(final BigDecimal inbigDec) {
        return inbigDec.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * @param instr
     * @return
     * @throws Exception
     */
    public static double toDouble(final String instr) throws Exception {
        return ((BigDecimal) decFormat.parse(instr.trim())).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    ///////////////////////////
    // toBigDec
    /**
     * @param inin
     * @return
     */
    public static BigDecimal toBigDec(final long inin) {
        return BigDecimal.valueOf(inin).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param inin
     * @return
     */
    public static BigDecimal toBigDec(final double inin) {
        return BigDecimal.valueOf(inin).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param inin
     * @return
     */
    public static BigDecimal toBigDec(final BigDecimal inin) {
        return inin.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param inin
     * @return
     * @throws Exception
     */
    public static BigDecimal toBigDec(final String inin) throws Exception {
        return ((BigDecimal) decFormat.parse(inin.trim())).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param inin
     * @return
     * @throws Exception
     */
    public static BigDecimal toBigDec_LocSPN(final String inin) throws Exception {
        return ((BigDecimal) decFormat.parse(inin.trim().replace(".", "").replace(",", "."))).setScale(2, RoundingMode.HALF_UP);
    }

    ///////////////////////////
    // toStr
    /**
     * @param inin
     * @return
     */
    public static String toStr(final long inin) {
        return intFormat.format(inin);
    }

    /**
     * @param inin
     * @return
     */
    public static String toStr(final double inin) {
        return decFormat.format(inin);
    }

    /**
     * @param inin
     * @return
     */
    public static String toStr(final BigDecimal inin) {
        return decFormat.format(inin.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Translada un valor String que representa algun numero, a su correspondiente representacion formateada.
     *
     * @param inin
     * @return
     * @throws Exception
     */
    public static String toStr(final String inin) throws Exception {
        return ((BigDecimal) decFormat.parse(inin.trim())).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * @param inin
     * @return
     */
    public static String toStr(final java.sql.Date inin) {
        return dateFormat.format(inin);
    }

    /**
     * @param inin
     * @return
     */
    public static String toStr(final java.util.Date inin) {
        return dateFormat.format(inin);
    }

    /**
     * @param inin
     * @return
     */
    public static String toStr(final Timestamp inin) {
        return timeStampFormat.format(inin);
    }

    /**
     * @param inin
     * @return
     */
    public static String toStr(Calendar inin) {
        return toStr(inin.getTime());
    }

    ///////////////////////////////////////////////////////////
    // toSqlDate
    /**
     * @param inin
     * @return
     * @throws Exception
     */
    public static java.sql.Date toDateSql(final long inin) throws Exception {
        return new java.sql.Date(inin);
    }

    /**
     * @param inin
     * @return
     * @throws Exception
     */
    public static java.sql.Date toDateSql(final String inin) throws Exception {
        return new java.sql.Date(dateFormat.parse(inin.trim()).getTime());
    }

    /**
     * @param inin
     * @return
     */
    public static java.sql.Date toDateSql(final java.util.Date inin) {
        return new java.sql.Date(inin.getTime());
    }

    /////////////////////////////////////////////////////////////////////
    // Con Java 8, LocalDate y Local dateTime
    private static final DateTimeFormatter formatterUtil = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static final DateTimeFormatter formatterUtilDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final DateTimeFormatter formatterSqlDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    ///////////////
    // de LocalDate =>
    public static java.util.Date toUtilDate(final java.time.LocalDate localDate) {
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static java.sql.Date toSqlDate(final java.time.LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    public static String toUtilStr(final java.time.LocalDate localDate) {
        return localDate.format(formatterUtil);
    }

    public static String toSqlStr(final java.time.LocalDate localDate) {
        return localDate.format(formatterSql);
    }

    ///////////////
    // de LocalDateTime =>
    public static java.util.Date toUtilDate(final java.time.LocalDateTime localDateTime) {
        return java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static java.sql.Date toSqlDate(final java.time.LocalDateTime localDateTime) {
        return java.sql.Date.valueOf(localDateTime.toLocalDate());
    }

    public static String toUtilStr(final java.time.LocalDateTime inin) {
        return inin.format(formatterUtilDateTime);
    }

    public static String toSqlStr(final java.time.LocalDateTime inin) {
        return inin.format(formatterSqlDateTime);
    }

    ///////////////////////
    // de java.util.Date =>
    public static java.time.LocalDate toLocalDate(final java.util.Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static java.time.LocalDateTime toLocalDateTime(final java.util.Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    //////////////////////
    // de java.sql.Date =>
    public static java.time.LocalDate toLocalDate(final java.sql.Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static java.time.LocalDateTime toLocalDateTime(final java.sql.Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    //////////////////////
    // de String =>
    public static java.time.LocalDate toLocalDate(final String inin) {
        return LocalDate.parse(inin, formatterUtil);
    }

    public static java.time.LocalDateTime toLocalDateTime(final String inin) {
        return LocalDateTime.parse(inin, formatterUtilDateTime);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        try {
            System.out.println(toBigDec(1000.1).toPlainString());
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
        try {
            System.out.println();
            System.out.println(toBigDec(1000.105).toPlainString());
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
        try {
            System.out.println(toDouble("1.000,101"));
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
        try {
            System.out.println();
            System.out.println(toDouble(BigDecimal.valueOf(1000.10501)));
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
        try {
            System.out.println();
            System.out.println(toDouble(BigDecimal.valueOf(0.0101)));
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
        try {
            System.out.println();
            System.out.println(toStr(1000.10501));
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
        try {
            System.out.println();
            System.out.println(toStr(BigDecimal.valueOf(0.0101)));
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
        System.out.println(Globales.getServerTimeStamp());
        System.out.println(Format.toDateSql(Globales.getServerTimeStamp()));
        try {
            System.out.println();
            System.out.println(toStr(Format.toDateSql(Globales.getServerTimeStamp())));
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
        try {
            final Timestamp aux = new Timestamp(Globales.getServerTimeStamp().getTime());
            System.out.println();
            System.out.println(aux);
            System.out.println(toStr(aux));
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
        try {
            final java.util.Date utilDate = new java.util.Date();
            final java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            final LocalDate localDate = LocalDate.now();
            final LocalDateTime localDateTime = LocalDateTime.now();
            System.out.println();
            System.out.println("Fechas Java 8");
            System.out.println(utilDate);
            System.out.println(sqlDate);
            System.out.println(localDate);
            System.out.println(localDateTime);
            System.out.println();
            System.out.println(toUtilDate(localDate));
            System.out.println(toSqlDate(localDate));
            System.out.println(toUtilStr(localDate));
            System.out.println(toSqlStr(localDate));
            System.out.println();
            System.out.println(toUtilDate(localDateTime));
            System.out.println(toSqlDate(localDateTime));
            System.out.println(toUtilStr(localDateTime));
            System.out.println(toSqlStr(localDateTime));
            System.out.println();
            System.out.println(toLocalDate(utilDate));
            System.out.println(toLocalDate(sqlDate));
            System.out.println();
            System.out.println(toLocalDateTime(utilDate));
            System.out.println(toLocalDateTime(sqlDate));
            System.out.println();
            System.out.println(toLocalDate("01/01/2018"));
            System.out.println(toLocalDateTime("01/01/2018 00:00"));
        } catch (final Exception inex) {
            System.out.println(inex);
            logger.error(inex);
        }
    }

}
