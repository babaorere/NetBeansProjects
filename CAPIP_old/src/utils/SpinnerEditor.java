/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package utils;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.text.DecimalFormatSymbols;
import java.util.EventObject;
import java.util.Locale;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

/**
 * Rev 23/10/2016
 *
 * @author CAPIP Sistemas C.A.
 */
public class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {

    final JSpinner spinner = new JSpinner();

    /**
     * Rev 23/10/2016
     *
     */
    public SpinnerEditor() {
        spinner.setModel(new SpinnerNumberModel(50.00, 0.00, Double.MAX_VALUE, 1.00));

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "0.00");
        editor.getFormat().setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        spinner.setEditor(editor);
    }

    /**
     * Rev 23/10/2016
     *
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            spinner.setValue(value);
        } else {
            spinner.setValue(0.00d);
        }
        return spinner;
    }

    @Override
    public boolean isCellEditable(EventObject evt) {
        if (evt instanceof MouseEvent) {
            return ((MouseEvent) evt).getClickCount() >= 2;
        }
        return true;
    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    public JSpinner getSpinner() {
        return spinner;
    }

}
