package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author manager
 */
public class Tools {

    public static void LeerArchivo(String tipo) {
        Scanner sc = new Scanner(System.in);

        tipo = tipo.toUpperCase();

        if (!tipo.equals("DEPARTAMENTO") && !tipo.equals("PUESTO")) {
            System.out.println("\nERROR en tipo");
            return;
        }

        System.out.println("\n----------------------------------------------------------------------------------------------------------------");
        if (tipo.equals("DEPARTAMENTO")) {
            System.out.println("                                   DEPARTAMENTOS DE LA ORGANIZACIÓN");
        } else {
            System.out.println("                                   PUESTOS DE LA ORGANIZACIÓN");
        }

        final String NAME = "Empresa.bcon";
        String linea;

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(NAME)));

            while ((linea = br.readLine()) != null) {

                if (linea.startsWith(tipo)) {
                    System.out.println("\n" + Splip(linea, 80));
                }
            }

        } catch (Exception ex) {
            System.out.println("ERROR al tratar de leer el archivo: " + NAME + "\n" + ex);
        }

        System.out.println("\nPresione ENTER para continuar");
        sc.nextLine();

    }

    /**
     * Divide una cadena larga en secciones mas pequeñas de tamaño "tam"
     *
     * @param cadena
     * @param tam
     * @return
     */
    public static String Splip(String cadena, int tam) {

        StringBuilder sb = new StringBuilder();

        int pos = 0;

        // Separar la cadena en palabras
        String palabra[] = cadena.split(" ");

        for (int idx = 0; idx < palabra.length; idx++) {
            pos += palabra[idx].length();

            if (pos >= tam) {
                sb.append("\n");
                pos = palabra[idx].length();
            }

            sb.append(palabra[idx]).append(" ");
        }

        return sb.toString();
    }

}
