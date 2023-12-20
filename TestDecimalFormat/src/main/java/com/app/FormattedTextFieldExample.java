package com.app;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class FormattedTextFieldExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FormattedTextField Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Configurar el JFrame
            frame.setSize(200, 200);
            frame.setLocationRelativeTo(null); // Centrar en pantalla

            DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance();
            format.setGroupingUsed(true);
            format.setGroupingSize(3);
            format.setMaximumFractionDigits(2);
            format.setMinimumFractionDigits(2);
            format.setDecimalSeparatorAlwaysShown(true);

            JFormattedTextField formattedTextField = new JFormattedTextField(format);
            formattedTextField.setValue(BigDecimal.ZERO);
            formattedTextField.setPreferredSize(new Dimension(100, 30)); // Tamaño y altura personalizados
            formattedTextField.setFont(new Font("SansSerif", Font.PLAIN, 18)); // Tipo de letra y tamaño personalizados

            // Configurar el JLabel
            JLabel resultLabel = new JLabel("Valor: ");
            resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

            // Agregar KeyListener para comitedit
            formattedTextField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            formattedTextField.commitEdit();
                        } catch (ParseException ex) {
                            Logger.getLogger(FormattedTextFieldExample.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        BigDecimal value = (BigDecimal) formattedTextField.getValue();
                        resultLabel.setText("Valor: " + value);
                    }
                }
            });

            formattedTextField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            formattedTextField.commitEdit();
                        } catch (ParseException ex) {
                            Logger.getLogger(FormattedTextFieldExample.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Object value = formattedTextField.getValue();
                        if (value instanceof Number) {
                            Number numberValue = (Number) value;
                            resultLabel.setText("Valor: " + numberValue);
                        }
                    }
                }
            });

            JPanel panel = new JPanel();
            panel.add(resultLabel);
            panel.add(formattedTextField);

            frame.add(panel);
//            frame.pack();
            frame.setVisible(true);
        });
    }
}
