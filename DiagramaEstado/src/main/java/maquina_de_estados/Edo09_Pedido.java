package maquina_de_estados;

import java.util.Scanner;

/**
 *
 */
public class Edo09_Pedido implements IEstado {

    @Override
    public IEstado getProximoEstado(String codTrans) {
        switch (codTrans) {
            case "09->09":
                return this; // permanecer en el mismo estado
            case "09->08":
                return new Edo08_Pagar();
            case "09->10":
                return new Edo10_Final();
            default:
                return this; // nos quedamos en el mismo estado
        }
    }

    @Override
    public String mostrarEstado() {
        int opcion = -1;

        Scanner sc = new Scanner(System.in);

        while (opcion == -1) {

            System.out.println("\nEstado: 09 Pedido");
            System.out.println("Mostrar listado de productos del carro de compra");
            System.out.println("1.- Datos del Pedido");
            System.out.println("2.- Retornar");

            // entrada de una cadena
            String linea = sc.nextLine();

            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException numberFormatException) {
                opcion = 2; // vamos al siguiente estado
            }

            switch (opcion) {

                case 1:
                    if (DatosPedido()) {
                        return "09->10"; // datos del pedido listos, proceso terminado
                    } else {
                        return "09->09"; // quedar en el mismo estado                                                
                    }
                case 2:
                    return "09->08"; // volvemos al Carro de Compra

                default:
                    return "09->09"; // nos quedamos en el mismo estado
            }

        }

        return "08->08"; // nos quedamos en el mismo estado
    }

    private boolean DatosPedido() {
        Scanner sc = new Scanner(System.in);

        // entrada de una cadena
        System.out.println("Direccion de envior= ? ");
        String linea = sc.nextLine().trim();

        if (linea.isEmpty()) {
            return false;
        }

        System.out.println("Direccion de envio es: " + linea);

        return true;
    }
}
