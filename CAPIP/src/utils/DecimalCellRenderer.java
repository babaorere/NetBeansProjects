/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package utils;

import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author CAPIP
 */
public class DecimalCellRenderer extends DefaultTableCellRenderer {

    private static final DecimalFormat numberFormat = new DecimalFormat("#,##0.00");

    public DecimalCellRenderer() {
        setHorizontalAlignment(JLabel.RIGHT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        final Component c = super.getTableCellRendererComponent(jTable, value, isSelected, hasFocus, row, column);

        if (c instanceof JLabel && value instanceof Number) {
            ((JLabel) c).setText(numberFormat.format((Number) value));
        }

        return c;
    }
}
