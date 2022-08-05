package vista;

import control.Tools;
import java.util.Scanner;

/**
 *
 * @author manager
 */
public class AppMain {

    private static String Menu() {

        Scanner sc = new Scanner(System.in);
        String opcion;

        do {
            System.out.println("\n\n");
            System.out.println("********************************************************************************");
            System.out.println("       *********   BASE DE CONOCIMIENTO DE UNA RED PROFESIONAL   *********");
            System.out.println("1 - Mostrar descripciones de Departamentos");
            System.out.println("2 - Mostrar descripciones de Puestos");
            System.out.println("3 - Salir");

            System.out.println("Opcion = ? ");

            opcion = sc.nextLine().trim();

            if ((opcion.length() == 1) && "123".contains(opcion)) {
                break;
            } else {
                System.out.println("Opcion INVALIDA");
            }

        } while (true);

        return opcion;

    }

    public static void main(String[] args) {

        System.out.println("\n\n");
        System.out.println("********************************************************************************");
        System.out.println("***   BIENVENIDOS AL PROGRAMABASE DE CONOCIMIENTO DE UNA RED PROFESIONAL   ******");

        Scanner sc = new Scanner(System.in);

        String opcion;
        do {

            opcion = Menu();

            switch (opcion) {

                case "1":
                    Tools.LeerArchivo("DEPARTAMENTO");
                    break;

                case "2":
                    Tools.LeerArchivo("PUESTO");
                    break;

            }

        } while (!opcion.equals("3"));

    }

}
