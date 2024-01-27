package presentacion;

import com.manager.datos.TLibro;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Punto de entrada de la aplicacion
 *
 * @author manager
 */
public class AppMain {

    private static String PresentarMenu() {

        //Ingrese datos usando BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            System.out.println("\n\n*******************************************");
            System.out.println("*****  Biblioteca Ordenacion y LIFO  *****");

            System.out.println("A. INSERTAR UN LIBRO.");
            System.out.println("B. BORRA UN LIBRO.");
            System.out.println("E. SALIR.");

            System.out.println("OPCION= ? ");
            String opcion;
            try {
                opcion = reader.readLine().trim().toUpperCase();
            } catch (IOException ex) {
                opcion = "";
            }

            System.out.println("OPCION= " + opcion);

            if ((opcion.length() == 1) && ("ABE".contains(opcion))) {
                return opcion;
            } else {
                System.out.println("\n*** OPCION INVALIDA ***");
            }
        }

    }

    private static TLibro InsertarLibro(ArrayList<TLibro> list) {

        //Ingrese datos usando BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n***************************************");
        System.out.println("*****  Biblioteca Insertar Libro  *****");

        int id;
        while (true) {
            System.out.println("\nId Libro= ? ");
            String strId;
            try {
                strId = reader.readLine().trim().toUpperCase();
            } catch (IOException ex) {
                strId = "";
            }

            try {
                id = Integer.parseInt(strId);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("*** VALOR INVALIDO ***");
                id = -1;
            }

            if (id > 0) {
                break;
            }

        }

        String strAutor;
        while (true) {
            System.out.println("\nAutor= ? ");
            try {
                strAutor = reader.readLine().trim().toUpperCase();
            } catch (IOException ex) {
                strAutor = "";
            }

            if (strAutor.length() > 0) {
                break;
            } else {
                System.out.println("*** VALOR INVALIDO ***");
            }
        }

        String strTitulo;
        while (true) {
            System.out.println("\nTitulo= ? ");
            try {
                strTitulo = reader.readLine().trim().toUpperCase();
            } catch (IOException ex) {
                strTitulo = "";
            }

            if (strTitulo.length() > 0) {
                break;
            } else {
                System.out.println("*** VALOR INVALIDO ***");
            }
        }

        int nroCopias;
        while (true) {
            System.out.println("\nNro. copias= ? ");
            String strnroCopias;
            try {
                strnroCopias = reader.readLine().trim().toUpperCase();
            } catch (IOException ex) {
                strnroCopias = "";
            }

            try {
                nroCopias = Integer.parseInt(strnroCopias);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("*** VALOR INVALIDO ***");
                nroCopias = -1;
            }

            if (nroCopias > 0) {
                break;
            }
        }

        System.out.println("Libro: id= " + id + ", Autor= " + strAutor + ", Titulo= " + strTitulo + ", Nro. Copias= " + nroCopias);

        TLibro libro = new TLibro(id, strAutor, strTitulo, nroCopias);

        // Verificar si el Libro ya existe
        if (list.contains(libro)) {
            System.out.println("*** EL LIBRO YA FUE INGRESADO ***");
            System.out.println("*** VALOR INVALIDO ***");
            libro = null;
        } else {
            list.add(libro);
        }

        return libro;
    }

    private static TLibro BorrarLibro(ArrayList<TLibro> list) {

        if (list.size() <= 0) {
            System.out.println("***  LISTA VACIA   ***");
            System.out.println("*** VALOR INVALIDO ***");
        }

        //Ingrese datos usando BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n**************************************");
        System.out.println("*****  Biblioteca Borrar Libro  *****");

        int id;
        while (true) {
            System.out.println("\nId Libro= ? ");
            String strId;
            try {
                strId = reader.readLine().trim().toUpperCase();
            } catch (IOException ex) {
                strId = "";
            }

            try {
                id = Integer.parseInt(strId);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("*** VALOR INVALIDO ***");
                id = -1;
            }

            if (id > 0) {
                break;
            }

        }

        System.out.println("Libro: id= " + id);

        TLibro libro = new TLibro(id, "", "", 0);

        // Busca el Libro
        int idx = list.indexOf(libro);
        if (idx >= 0) {
            libro = list.remove(idx);
            System.out.println("*** LIBRO BORRADO ***");
        } else {
            System.out.println("*** LIBRO NO ENCONTRADO ***");
            System.out.println("   *** VALOR INVALIDO ***");
            libro = null;
        }

        return libro;
    }

    public static void main(String[] args) {

        ArrayList<TLibro> list;
        list = new ArrayList<>();

        boolean seguir = true;
        while (seguir) {
            switch (PresentarMenu()) {
                case "A":
                    InsertarLibro(list);
                    break;
                case "B":
                    BorrarLibro(list);
                    break;
                case "E":
                    seguir = false;
                    break;

                default:
                    System.out.println("*** MENU OPCION INVALIDA ***");
            }
        }

    }

}
