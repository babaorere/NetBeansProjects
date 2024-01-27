/**
 * https://coderanch.com/t/306966/databases/Execute-sql-file-java
 *
 * This class can be used to read sql files into an array of Strings, each
 * representing a single query terminated by ";"
 * Comments are filtered out.
 */
package utils;

import capipsistema.Conn;
import capipsistema.FrmPrincipal;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/*
 * ATTENTION: SQL file must not contain column names, etc. including comment signs (#, --, /* etc.)
 * like e.g. a.'#rows' etc. because every characters after # or -- in a line are filtered
 * out of the query string the same is true for every characters surrounded by /* and */
/* */
public class SQLReader {

    private static ArrayList<String> listOfQueries = null;

    /*
     * @param path Path to the SQL file
     * @return List of query strings
     */
    public static ArrayList<String> createQueries(final InputStream _inSt) {
        String queryLine;
        final StringBuilder sBuffer = new StringBuilder();
        listOfQueries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(_inSt))) {

            //read the SQL file line by line
            while ((queryLine = br.readLine()) != null) {
                // ignore comments beginning with #
                int indexOfCommentSign = queryLine.indexOf('#');
                if (indexOfCommentSign != -1) {
                    if (queryLine.startsWith("#")) {
                        queryLine = "";
                    } else {
                        queryLine = queryLine.substring(0, indexOfCommentSign - 1);
                    }
                }
                // ignore comments beginning with --
                indexOfCommentSign = queryLine.indexOf("--");
                if (indexOfCommentSign != -1) {
                    if (queryLine.startsWith("--")) {
                        queryLine = "";
                    } else {
                        queryLine = queryLine.substring(0, indexOfCommentSign - 1);
                    }
                }
                // ignore comments surrounded by /* */
                indexOfCommentSign = queryLine.indexOf("/*");
                if (indexOfCommentSign != -1) {
                    if (queryLine.startsWith("#")) {
                        queryLine = "";
                    } else {
                        queryLine = queryLine.substring(0, indexOfCommentSign - 1);
                    }

                    sBuffer.append(queryLine).append(" ");
                    // ignore all characters within the comment
                    do {
                        queryLine = br.readLine();
                    } while (queryLine != null && !queryLine.contains("*/"));

                    if (queryLine != null) {
                        indexOfCommentSign = queryLine.indexOf("*/");
                        if (indexOfCommentSign != -1) {
                            if (queryLine.endsWith("*/")) {
                                queryLine = "";
                            } else {
                                queryLine = queryLine.substring(indexOfCommentSign + 2, queryLine.length() - 1);
                            }
                        }
                    }
                }

                //  the + " " is necessary, because otherwise the content before and after a line break are concatenated
                // like e.g. a.xyz FROM becomes a.xyzFROM otherwise and can not be executed 
                if (queryLine != null) {
                    sBuffer.append(queryLine).append(" ");
                }
            }
            br.close();

            // here is our splitter ! We use ";" as a delimiter for each request 
            String[] splittedQueries = sBuffer.toString().split(";");

            // filter out empty statements
            for (String splittedQuerie : splittedQueries) {
                if (!splittedQuerie.trim().equals("") && !splittedQuerie.trim().equals("\t")) {
                    listOfQueries.add(splittedQuerie);
                }
            }
        } catch (final Exception e) {
            System.out.println("*** Error : " + e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            System.out.println("################################################");
            System.out.println(sBuffer.toString());
        }
        return listOfQueries;
    }

    public static void main(String[] args) {
        ArrayList<String> queries_01;
        final String pathname;

        //... get queries from sql file .................
        pathname = "/sql/prueba.sql";
        final InputStream streamFile = FrmPrincipal.getInstance().getClass().getResourceAsStream(pathname);

        if (streamFile == null) {
            JOptionPane.showMessageDialog(null, "Archivo no encontrado: " + pathname);
            return;
        }

        try {
            queries_01 = createQueries(streamFile);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de abrir el archivo " + pathname + System.getProperty("line.separator") + _ex);
            return;
        }

        ArrayList<ResultSet> RSList_01 = new ArrayList<>();

        try {
            for (String query : queries_01) {
                RSList_01.add(Conn.executeQuery(query));
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al trar de ejecutar el comando sql" + System.getProperty("line.separator") + _ex);
        }

        try {
            ResultSet tmpRS = RSList_01.get(0);
            if (!tmpRS.isClosed()) {
                if (tmpRS.next()) {
                    System.out.println("Query 1: id = " + tmpRS.getInt("id"));
                }
            }

        } catch (final Exception _ex) {

        }
    }

}
