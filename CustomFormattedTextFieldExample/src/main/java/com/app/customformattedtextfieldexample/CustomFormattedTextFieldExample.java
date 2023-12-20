package com.app.customformattedtextfieldexample;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.text.*;

public class CustomFormattedTextFieldExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom FormattedTextField Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();

            // Configurar el primer JFormattedTextField
            JFormattedTextField formattedTextField1 = createCustomFormattedTextField();
            panel.add(formattedTextField1);

            // Configurar el segundo JFormattedTextField
            JFormattedTextField formattedTextField2 = createCustomFormattedTextField();
            panel.add(formattedTextField2);

            // Agregar un botón para realizar commitEdit() y pasar el valor al segundo campo
            JButton commitButton = new JButton("Commit");
            panel.add(commitButton);
            commitButton.addActionListener(e -> {
                try {
                    formattedTextField1.commitEdit(); // Intenta hacer commitEdit()
                    Object value = formattedTextField1.getValue();
                    formattedTextField2.setValue(value); // Pasa el valor al segundo campo
                } catch (ParseException ex) {
                    ex.printStackTrace(); // Manejar el error de parseo si ocurre
                }
            });

            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        });
    }

    private static JFormattedTextField createCustomFormattedTextField() {
        JFormattedTextField formattedTextField = new JFormattedTextField();
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        customSymbols.setGroupingSeparator('.');
        customSymbols.setDecimalSeparator(',');
        DecimalFormat customFormat = new DecimalFormat("#,##0.00", customSymbols);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        formattedTextField.setColumns(10); // Ancho de 10 columnas
        formattedTextField.setFont(new Font("SansSerif", Font.PLAIN, 20)); // Tamaño de letra 20

        NumberFormatter formatter = new NumberFormatter(customFormat);
        formatter.setValueClass(Double.class);
        formattedTextField.setFormatterFactory(new DefaultFormatterFactory(formatter));
        formattedTextField.setValue(0.0);

        return formattedTextField;
    }
}
