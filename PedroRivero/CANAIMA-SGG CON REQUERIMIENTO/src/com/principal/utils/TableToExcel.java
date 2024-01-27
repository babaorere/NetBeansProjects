/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.utils;

import com.principal.capipsistema.Propiedades;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
public class TableToExcel {

    /**
     * Rev 27//11/2016
     *
     * @param intable
     * @param infileName
     * @throws Exception
     */
    public static void exportExcel(final JTable intable, final String infileName) throws Exception {
        if ((intable == null) || (intable.getRowCount() <= 0)) {
            return;
        }
        if (!new File(Propiedades.CAPIP_DIR_LOCAL).exists()) {
            JOptionPane.showMessageDialog(null, "Para continuar debe crear el directorio: " + Propiedades.CAPIP_DIR_LOCAL);
            return;
        }
        File file = new File(Propiedades.CAPIP_DIR_LOCAL + "/" + infileName);
        HSSFWorkbook fWorkbook = new HSSFWorkbook();
        HSSFSheet fSheet = fWorkbook.createSheet("Reporte");
        //Create row at line 0
        HSSFRow headerRow = fSheet.createRow(0);
        for (int headings = 0; headings < intable.getColumnCount(); headings++) {
            //For each column
            final String auxColName = intable.getColumnName(headings);
            if (auxColName != null) {
                //Write column name
                headerRow.createCell(headings).setCellValue(auxColName);
            }
        }
        for (int fil = 0; fil < intable.getRowCount(); fil++) {
            HSSFRow fRow = fSheet.createRow(fil + 1);
            for (int col = 0; col < intable.getColumnCount(); col++) {
                final Object valueCell = intable.getValueAt(fil, col);
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

    public static void exportTSV(JTable intable, final String infileName) throws IOException {
        if ((intable == null) || (intable.getRowCount() <= 0)) {
            return;
        }
        if (!new File(Propiedades.CAPIP_DIR_LOCAL).exists()) {
            JOptionPane.showMessageDialog(null, "Para continuar debe crear el directorio: " + Propiedades.CAPIP_DIR_LOCAL);
            return;
        }
        FileWriter out = new FileWriter(new File(Propiedades.CAPIP_DIR_LOCAL + "/" + infileName));
        try (BufferedWriter bw = new BufferedWriter(out)) {
            for (int i = 0; i < intable.getColumnCount(); i++) {
                final String auxColName = intable.getColumnName(i);
                if (auxColName != null) {
                    bw.write(auxColName + "\t");
                }
            }
            bw.write(System.getProperty("line.separator"));
            for (int i = 0; i < intable.getRowCount(); i++) {
                for (int j = 0; j < intable.getColumnCount(); j++) {
                    final Object valueCell = intable.getValueAt(i, j);
                    if (valueCell != null) {
                        bw.write(intable.getValueAt(i, j).toString() + "\t");
                    }
                }
                bw.write(System.getProperty("line.separator"));
            }
        }
    }

    private static final Logger logger = LogManager.getLogger(TableToExcel.class);
}
