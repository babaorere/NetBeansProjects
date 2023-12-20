/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.textfield_decimal;

import com.principal.capip_configurador.PropLocalPass;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.NumberFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP
 */
public enum DecimalFormatter {

    INSTANCE;

    private static final DefaultFormatter formatter;

    static {
        formatter = getDisplayFormatter();
    }

    DecimalFormatter() {
    }

    public static DefaultFormatter getDisplayFormatter() {
        NumberFormat numberFormat;
        NumberFormatter numberFormatter;
        try {
            numberFormat = DecimalFormat.getInstance();
            numberFormat.setGroupingUsed(true);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            numberFormat.setMinimumIntegerDigits(1);
            numberFormat.setMinimumFractionDigits(2);
            numberFormat.setMaximumFractionDigits(2);
            if (numberFormat instanceof DecimalFormat) {
                ((DecimalFormat) numberFormat).setParseBigDecimal(true);
            }
            numberFormatter = new NumberFormatter(numberFormat);
        } catch (final Exception inex) {
            numberFormatter = null;
            logger.error(inex);
        }
        return numberFormatter;
    }

    public static DefaultFormatter getEditFormatter() {
        NumberFormat numberFormat;
        NumberFormatter numberFormatter;
        try {
            numberFormat = DecimalFormat.getInstance(Locale.ENGLISH);
            numberFormat.setGroupingUsed(false);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            numberFormat.setMinimumIntegerDigits(1);
            numberFormat.setMinimumFractionDigits(2);
            numberFormat.setMaximumFractionDigits(2);
            if (numberFormat instanceof DecimalFormat) {
                ((DecimalFormat) numberFormat).setParseBigDecimal(true);
            }
            numberFormatter = new NumberFormatter(numberFormat);
            numberFormatter.setOverwriteMode(true);
            numberFormatter.setAllowsInvalid(true);
            numberFormatter.setCommitsOnValidEdit(true);
        } catch (final Exception inex) {
            numberFormatter = null;
            logger.error(inex);
        }
        return numberFormatter;
    }

    public BigDecimal stringToValue(String invalue) throws Exception {
        return (BigDecimal) formatter.stringToValue(invalue);
    }

    public String valueToString(Object invalue) throws Exception {
        return formatter.valueToString(invalue);
    }

    private static final Logger logger = LogManager.getLogger(PropLocalPass.class);

}
