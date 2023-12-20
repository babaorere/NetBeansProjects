/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.utils;

import java.awt.Component;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP
 */
public class DateCellRenderer extends DefaultTableCellRenderer {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public DateCellRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(jTable, value, isSelected, hasFocus, row, column);
        if (c instanceof JLabel && (value instanceof Timestamp || value instanceof java.sql.Date || value instanceof java.util.Date)) {
            ((JLabel) c).setText(dateFormat.format(value));
        }
        return c;
    }

    private static final Logger logger = LogManager.getLogger(DateCellRenderer.class);
}
