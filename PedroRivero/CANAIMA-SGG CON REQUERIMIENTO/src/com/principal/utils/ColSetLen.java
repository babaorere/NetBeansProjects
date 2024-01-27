package com.principal.utils;

import com.principal.connection.ConnCapip;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * En este código, reemplaza your_database_name, your_username, y your_password con
 * tus propias credenciales de la base de datos. Este programa recorre todas las tablas y
 * columnas en la base de datos, obtiene las máximas longitudes permitidas y
 * las almacena en un archivo de propiedades llamado "column_lengths.properties".
 *
 * Puedes ajustar el nombre del archivo según tus necesidades.
 *
 * Una vez que se haya generado el archivo de propiedades,
 * puedes utilizarlo en tu aplicación para validar la longitud de los campos. Para hacerlo,
 * simplemente lee el archivo de propiedades y consulta las máximas longitudes permitidas
 * para las columnas relevantes según sea necesario.
 *
 * @author
 */
public final class ColSetLen {

    public static final String FILENAME = "column_lengths.properties";

    public static void main(String[] args) {
        try (Connection connection = ConnCapip.getInstance().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tablesResultSet = metaData.getTables(null, null, null, new String[] { "TABLE" });
            TreeMap<String, String> columnMaxLengths = new TreeMap<>((a, b) -> {
                return a.compareTo(b);
            });
            while (tablesResultSet.next()) {
                String tableName = tablesResultSet.getString("TABLE_NAME");
                ResultSet columnsResultSet = metaData.getColumns(null, null, tableName, null);
                while (columnsResultSet.next()) {
                    String columnName = columnsResultSet.getString("COLUMN_NAME");
                    String dataType = columnsResultSet.getString("TYPE_NAME");
                    int maxLength = columnsResultSet.getInt("COLUMN_SIZE");
                    // Combine table name and column name as the key
                    String key = tableName + "->" + columnName;
                    columnMaxLengths.put(key, String.valueOf(maxLength));
                }
            }
            // Print the sorted and deduplicated information
            for (Map.Entry<String, String> entry : columnMaxLengths.entrySet()) {
                System.out.println(entry);
            }
            // Create and save the properties file
            savePropertiesToFile(columnMaxLengths, FILENAME);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    private static void savePropertiesToFile(TreeMap<String, String> treeMap, String fileName) throws IOException {
        Properties properties = new Properties();
        properties.putAll(treeMap);
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            properties.store(outputStream, "Maximum Lengths for Database Columns");
        }
    }

    private static final Logger logger = LogManager.getLogger(ColSetLen.class);
}
