/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package utils;

import capipsistema.Globales;
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

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class Format {

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
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
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
     *
     * @param _in
     * @return
     */
    public static long toLong(final double _in) {
        return (long) _in;
    }

    /**
     *
     * @param _in
     * @return
     */
    public static long toLong(final BigDecimal _in) {
        return _in.longValue();
    }

    /**
     *
     * @param _in
     * @return
     * @throws Exception
     */
    public static long toLong(final String _in) throws Exception {
        return ((BigDecimal) intFormat.parse(_in.trim())).longValue();
    }

    /**
     *
     * @param _in
     * @return
     */
    public static long toLong(final java.sql.Date _in) {
        return _in.getTime();
    }

    ///////////////////////////
    // toDouble
    /**
     *
     * @param _in
     * @return
     */
    public static double toDouble(final long _in) {
        return (double) _in;
    }

    /**
     *
     * @param _dbl
     * @return
     */
    public static double toDouble(final double _dbl) {
        return BigDecimal.valueOf(_dbl).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     *
     * @param _bigDec
     * @return
     */
    public static double toDouble(final BigDecimal _bigDec) {
        return _bigDec.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     *
     * @param _str
     * @return
     * @throws Exception
     */
    public static double toDouble(final String _str) throws Exception {
        return ((BigDecimal) decFormat.parse(_str.trim())).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    ///////////////////////////
    // toBigDec
    /**
     *
     * @param _in
     * @return
     */
    public static BigDecimal toBigDec(final long _in) {
        return BigDecimal.valueOf(_in).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     *
     * @param _in
     * @return
     */
    public static BigDecimal toBigDec(final double _in) {
        return BigDecimal.valueOf(_in).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     *
     * @param _in
     * @return
     */
    public static BigDecimal toBigDec(final BigDecimal _in) {
        return _in.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     *
     * @param _in
     * @return
     * @throws Exception
     */
    public static BigDecimal toBigDec(final String _in) throws Exception {
        return ((BigDecimal) decFormat.parse(_in.trim())).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     *
     * @param _in
     * @return
     * @throws Exception
     */
    public static BigDecimal toBigDec_LocSPN(final String _in) throws Exception {
        return ((BigDecimal) decFormat.parse(_in.trim().replace(".", "").replace(",", "."))).setScale(2, RoundingMode.HALF_UP);
    }

    ///////////////////////////
    // toStr
    /**
     *
     * @param _in
     * @return
     */
    public static String toStr(final long _in) {
        return intFormat.format(_in);
    }

    /**
     *
     * @param _in
     * @return
     */
    public static String toStr(final double _in) {
        return decFormat.format(_in);
    }

    /**
     *
     * @param _in
     * @return
     */
    public static String toStr(final BigDecimal _in) {
        return decFormat.format(_in.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Translada un valor String que representa algun numero, a su correspondiente representacion formateada.
     *
     * @param _in
     * @return
     * @throws Exception
     */
    public static String toStr(final String _in) throws Exception {
        return ((BigDecimal) decFormat.parse(_in.trim())).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     *
     * @param _in
     * @return
     */
    public static String toStr(final java.sql.Date _in) {
        return dateFormat.format(_in);
    }

    /**
     *
     * @param _in
     * @return
     */
    public static String toStr(final java.util.Date _in) {
        return dateFormat.format(_in);
    }

    /**
     *
     * @param _in
     * @return
     */
    public static String toStr(final Timestamp _in) {
        return timeStampFormat.format(_in);
    }

    /**
     *
     * @param _in
     * @return
     */
    public static String toStr(Calendar _in) {
        return toStr(_in.getTime());
    }

    ///////////////////////////////////////////////////////////
    // toSqlDate
    /**
     *
     * @param _in
     * @return
     * @throws Exception
     */
    public static java.sql.Date toDateSql(final long _in) throws Exception {
        return new java.sql.Date(_in);
    }

    /**
     *
     * @param _in
     * @return
     * @throws Exception
     */
    public static java.sql.Date toDateSql(final String _in) throws Exception {
        return new java.sql.Date(dateFormat.parse(_in.trim()).getTime());
    }

    /**
     *
     * @param _in
     * @return
     */
    public static java.sql.Date toDateSql(final java.util.Date _in) {
        return new java.sql.Date(_in.getTime());
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

    public static String toUtilStr(final java.time.LocalDateTime _in) {
        return _in.format(formatterUtilDateTime);
    }

    public static String toSqlStr(final java.time.LocalDateTime _in) {
        return _in.format(formatterSqlDateTime);
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
    public static java.time.LocalDate toLocalDate(final String _in) {
        return LocalDate.parse(_in, formatterUtil);
    }

    public static java.time.LocalDateTime toLocalDateTime(final String _in) {
        return LocalDateTime.parse(_in, formatterUtilDateTime);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        try {
            System.out.println(toBigDec(1000.1).toPlainString());
        } catch (final Exception _ex) {
            System.out.println(_ex);
        }

        try {
            System.out.println();
            System.out.println(toBigDec(1000.105).toPlainString());
        } catch (final Exception _ex) {
            System.out.println(_ex);
        }

        try {
            System.out.println(toDouble("1.000,101"));
        } catch (final Exception _ex) {
            System.out.println(_ex);
        }

        try {
            System.out.println();
            System.out.println(toDouble(BigDecimal.valueOf(1000.10501)));
        } catch (final Exception _ex) {
            System.out.println(_ex);
        }

        try {
            System.out.println();
            System.out.println(toDouble(BigDecimal.valueOf(0.0101)));
        } catch (final Exception _ex) {
            System.out.println(_ex);
        }

        try {
            System.out.println();
            System.out.println(toStr(1000.10501));
        } catch (final Exception _ex) {
            System.out.println(_ex);
        }

        try {
            System.out.println();
            System.out.println(toStr(BigDecimal.valueOf(0.0101)));
        } catch (final Exception _ex) {
            System.out.println(_ex);
        }

        System.out.println(Globales.getServerTimeStamp());
        System.out.println(Format.toDateSql(Globales.getServerTimeStamp()));
        try {
            System.out.println();
            System.out.println(toStr(Format.toDateSql(Globales.getServerTimeStamp())));
        } catch (final Exception _ex) {
            System.out.println(_ex);
        }

        try {
            final Timestamp aux = new Timestamp(Globales.getServerTimeStamp().getTime());
            System.out.println();
            System.out.println(aux);
            System.out.println(toStr(aux));
        } catch (final Exception _ex) {
            System.out.println(_ex);
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
        } catch (final Exception _ex) {
            System.out.println(_ex);
        }

    }

}
