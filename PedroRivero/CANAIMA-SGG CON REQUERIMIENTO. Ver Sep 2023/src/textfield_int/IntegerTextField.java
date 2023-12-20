/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package textfield_int;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultFormatterFactory;

/**
 *
 * @author CAPIP
 */
public enum IntegerTextField {

    INSTANCE;

    private static class IntegerVerifier extends InputVerifier {

        private final BigDecimal min;
        private final BigDecimal max;

        public IntegerVerifier() {
            min = BigDecimal.ZERO;
            max = BigDecimal.valueOf(Integer.MAX_VALUE);
        }

        public IntegerVerifier(int inmin, int inmax) {
            min = BigDecimal.valueOf( inmin);
            max = BigDecimal.valueOf( inmax);
        }

        @Override
        public boolean verify(JComponent ininput) {
            if ( ininput instanceof JFormattedTextField) {
                JFormattedTextField ftf = (JFormattedTextField) ininput;
                AbstractFormatter formatter = ftf.getFormatter();
                if (formatter != null) {
                    String text = ftf.getText();
                    try {
                        Object o = formatter.stringToValue(text);
                        if (o instanceof BigDecimal) {
                            BigDecimal bd = (BigDecimal) o;

                            // Rechazar si es menor que el minimo
                            if ((min != null) && (bd.compareTo(min) < 0)) {
                                JOptionPane.showMessageDialog(null, "Valor menor que el mìnimo");
                                return false;
                            }

                            // Rechazar si es mayor que el maximo
                            if ((max != null) && (bd.compareTo(max) > 0)) {
                                JOptionPane.showMessageDialog(null, "Valor mayor que el màximo");
                                return false;
                            }
                        }

                        return true;
                    } catch (final Exception inex) {
                        JOptionPane.showMessageDialog(null, inex);
                        return false;
                    }
                }
            }
            return true;
        }

//        @Deprecated(since="9")
//        @Override
//        public boolean shouldYieldFocus(JComponent ininput) {
//            return verify( ininput);
//        }
        @Override
        public boolean shouldYieldFocus(JComponent source, JComponent target) {
            return verify(source);
        }
    }

    public static JFormattedTextField getTextField() {
        return getTextField(0);
    }

    public static JFormattedTextField getTextField(Integer invalue) {
        return getTextField( invalue, 0, Integer.MAX_VALUE);
    }

    public static JFormattedTextField getTextField(Integer invalue, int inmin, int inmax) {

        JFormattedTextField textf = new JFormattedTextField(new DefaultFormatterFactory(IntegerFormatter.getDisplayFormatter(), IntegerFormatter.getDisplayFormatter(), IntegerFormatter.getEditFormatter()));
        textf.setHorizontalAlignment(JTextField.RIGHT);
        textf.setValue(BigDecimal.valueOf( invalue));

        // Establecer el verificador
        textf.setInputVerifier(new IntegerVerifier( inmin, inmax));

        final Object keyMap = (String) (textf.getInputMap().get(KeyStroke.getKeyStroke("ENTER")));

        final Action oldAction = textf.getActionMap().get(keyMap);

        textf.getActionMap().remove(keyMap);
        textf.getActionMap().put(keyMap, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                oldAction.actionPerformed(actionEvent);
            }
        });

        return textf;
    }

}
