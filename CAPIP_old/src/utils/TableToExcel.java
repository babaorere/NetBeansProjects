/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package utils;

import capipsistema.CapipPropiedades;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class TableToExcel {

    /**
     * Rev 27//11/2016
     *
     * @param _table
     * @param _fileName
     * @throws Exception
     */
    public static void exportExcel(final JTable _table, final String _fileName) throws Exception {
        if ((_table == null) || (_table.getRowCount() <= 0)) {
            return;
        }

        if (!new File(CapipPropiedades.CAPIP_DIR_LOCAL).exists()) {
            JOptionPane.showMessageDialog(null, "Para continuar debe crear el directorio: " + CapipPropiedades.CAPIP_DIR_LOCAL);
            return;
        }

        File file = new File(CapipPropiedades.CAPIP_DIR_LOCAL + "/" + _fileName);

        HSSFWorkbook fWorkbook = new HSSFWorkbook();
        HSSFSheet fSheet = fWorkbook.createSheet("Reporte");

        HSSFRow headerRow = fSheet.createRow(0); //Create row at line 0
        for (int headings = 0; headings < _table.getColumnCount(); headings++) { //For each column
            final String auxColName = _table.getColumnName(headings);
            if (auxColName != null) {
                headerRow.createCell(headings).setCellValue(auxColName);//Write column name
            }
        }

        for (int fil = 0; fil < _table.getRowCount(); fil++) {
            HSSFRow fRow = fSheet.createRow(fil + 1);
            for (int col = 0; col < _table.getColumnCount(); col++) {
                final Object valueCell = _table.getValueAt(fil, col);
                if (valueCell != null) {
                    HSSFCell cell = fRow.createCell(col);
                    cell.setCellValue(valueCell.toString());
                }
            }
        }

        FileOutputStream fileOutputStream;
        fileOutputStream = new FileOutputStream(file);
        try (BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream)) {
            fWorkbook.write(bos);
        }
        fileOutputStream.close();

    }

    public static void exportTSV(JTable _table, final String _fileName) throws IOException {
        if ((_table == null) || (_table.getRowCount() <= 0)) {
            return;
        }

        if (!new File(CapipPropiedades.CAPIP_DIR_LOCAL).exists()) {
            JOptionPane.showMessageDialog(null, "Para continuar debe crear el directorio: " + CapipPropiedades.CAPIP_DIR_LOCAL);
            return;
        }

        FileWriter out = new FileWriter(new File(CapipPropiedades.CAPIP_DIR_LOCAL + "/" + _fileName));
        try (BufferedWriter bw = new BufferedWriter(out)) {
            for (int i = 0; i < _table.getColumnCount(); i++) {
                final String auxColName = _table.getColumnName(i);
                if (auxColName != null) {
                    bw.write(auxColName + "\t");
                }
            }

            bw.write(System.getProperty("line.separator"));

            for (int i = 0; i < _table.getRowCount(); i++) {
                for (int j = 0; j < _table.getColumnCount(); j++) {
                    final Object valueCell = _table.getValueAt(i, j);
                    if (valueCell != null) {
                        bw.write(_table.getValueAt(i, j).toString() + "\t");
                    }
                }

                bw.write(System.getProperty("line.separator"));
            }
        }
    }
}
