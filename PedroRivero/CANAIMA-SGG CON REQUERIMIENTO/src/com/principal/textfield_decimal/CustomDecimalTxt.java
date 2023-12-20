package com.principal.textfield_decimal;

import com.principal.pagos.PagoDirectoNormal;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CustomDecimalTxt {

    private static final Logger logger = LogManager.getLogger(PagoDirectoNormal.class);

    public static JFormattedTextField getTxt() {

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

            final Object keyMap = (String) (formattedTextField.getInputMap().get(KeyStroke.getKeyStroke("ENTER")));
            final Action oldAction = formattedTextField.getActionMap().get(keyMap);
            formattedTextField.getActionMap().remove(keyMap);
            formattedTextField.getActionMap().put(keyMap, new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    oldAction.actionPerformed(actionEvent);
                }
            });
        } catch (Exception inex) {
            logger.error(inex);
            formattedTextField = new JFormattedTextField();
        }

        return formattedTextField;
    }
}
