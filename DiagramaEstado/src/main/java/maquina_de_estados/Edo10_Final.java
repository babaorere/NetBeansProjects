package maquina_de_estados;

import static java.lang.System.exit;
import java.util.Scanner;

/**
 *
 */
public class Edo10_Final implements IEstado {

    @Override
    public IEstado getProximoEstado(String codTrans) {
        // Independientemente del codTrans, nos quedamos en el mismo estado 
        return this;
    }

    @Override
    public String mostrarEstado() {
        int opcion = -1;

        Scanner sc = new Scanner(System.in);

        while (opcion == -1) {

            System.out.println("\nEstado: 10 Final");
            System.out.println("\n0 Terminar el programa");

            // entrada de una cadena
            String linea = sc.nextLine();

            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException numberFormatException) {
                opcion = 0; // si hay un error solo salimos
            }

            switch (opcion) {

                case 0:
                    System.out.println("\nFin del programa");
                    exit(0); // Finaliza el programa
                    break;

                default:
                    return "10->10"; // nos quedamos en el mismo estado
            }

        }

        return "10->10"; // nos quedamos en el mismo estado
    }
}
