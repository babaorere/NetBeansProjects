package maquina_de_estados;

import java.util.Scanner;

/**
 *
 */
public class Edo01_Vacio implements IEstado {

    // Aqui realizamos las acciones
    @Override
    public IEstado getProximoEstado(String codTrans) {

        switch (codTrans) {

            case "01->01":
                return this; // nos quedamos en el mismo estado

            case "01->02":
                return new Edo02_Agregar(); // pasamos al estado "2 Agregar"

            case "01->10":
                return new Edo10_Final(); // vamos al estado final "10 Final"

            default:
                return this; // nos quedamos en el mismo estado
        }

    }

    /**
     * Acciones a realizar en este estado, retorna un nuevo codigo de transicion
     *
     * @return
     */
    @Override
    public String mostrarEstado() {

        int opcion = -1;

        Scanner sc = new Scanner(System.in);

        while (opcion == -1) {

            System.out.println("\nEstado 01 Vacio");
            System.out.println("1.- Permanecer mismo estado");
            System.out.println("2.- Agregar Producto");
            System.out.println("0 Salir");
            System.out.println("\nOpcion= ? ");

            // entrada de una cadena
            String linea = sc.nextLine();

            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException numberFormatException) {
                opcion = 0; // si hay un error solo salimos
            }

            switch (opcion) {

                case 0:
                    return "01->10"; // ir al estado final "10 Final"
                case 1:
                    return "01->01"; // permanecer en el mismo estado
                case 2:
                    return "01->02"; // ir al estado "02 Agregar"
                default:
                    return "01->01"; // nos quedamos en el mismo estado
            }

        }

        return "01->01"; // nos quedamos en el mismo estado
    }
}
