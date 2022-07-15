/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package textfield_int;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.NumberFormatter;

/**
 *
 *
 *
 * @author CAPIP
 */
public enum IntegerFormatter {

    INSTANCE;

    private static final DefaultFormatter formatter;

    static {
        formatter = getDisplayFormatter();
    }

    IntegerFormatter() {
    }

    public static DefaultFormatter getDisplayFormatter() {

        NumberFormat numberFormat;
        NumberFormatter numberFormatter;

        try {
            numberFormat = NumberFormat.getIntegerInstance();
            numberFormat.setParseIntegerOnly(true);
            numberFormat.setGroupingUsed(true);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            if (numberFormat instanceof DecimalFormat) {
                ((DecimalFormat) numberFormat).setParseBigDecimal(true);
            }

            numberFormatter = new NumberFormatter(numberFormat);
        } catch (final Exception _ex) {
            numberFormatter = null;
        }

        return numberFormatter;
    }

    public static DefaultFormatter getEditFormatter() {

        NumberFormat numberFormat;
        NumberFormatter numberFormatter;

        try {
            numberFormat = DecimalFormat.getIntegerInstance(Locale.ENGLISH);
            numberFormat.setParseIntegerOnly(true);
            numberFormat.setGroupingUsed(false);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            if (numberFormat instanceof DecimalFormat) {
                ((DecimalFormat) numberFormat).setParseBigDecimal(true);
            }

            numberFormatter = new NumberFormatter(numberFormat);
            numberFormatter.setOverwriteMode(true);
            numberFormatter.setAllowsInvalid(false);
            numberFormatter.setCommitsOnValidEdit(true);
        } catch (final Exception _ex) {
            numberFormatter = null;
        }

        return numberFormatter;
    }

    public BigDecimal stringToValue(String _value) throws Exception {
        return (BigDecimal) formatter.stringToValue(_value);
    }

    public String valueToString(Integer _value) throws Exception {
        return formatter.valueToString(_value);
    }

}
