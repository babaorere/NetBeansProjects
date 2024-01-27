/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.textfield_decimal;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP
 */
public enum DecimalTextField {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger(DecimalTextField.class);

    public static JFormattedTextField getTextField() {

        JFormattedTextField formattedTextField;

        try {
            formattedTextField = new JFormattedTextField();
            DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
            customSymbols.setGroupingSeparator('.');
            customSymbols.setDecimalSeparator(',');
            DecimalFormat customFormat = new DecimalFormat("#,##0.00", customSymbols);

            NumberFormatter formatter = new NumberFormatter(customFormat);
            formatter.setValueClass(BigDecimal.class);
            formattedTextField.setFormatterFactory(new DefaultFormatterFactory(formatter));
            formattedTextField.setValue(new BigDecimal("0.00"));
        } catch (Exception inex) {
            logger.error(inex);
            formattedTextField = new JFormattedTextField();
        }

        return formattedTextField;
    }

}
