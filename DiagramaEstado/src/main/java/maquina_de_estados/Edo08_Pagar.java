package maquina_de_estados;

import java.util.Scanner;

/**
 *
 */
public class Edo08_Pagar implements IEstado {

    @Override
    public IEstado getProximoEstado(String codTrans) {
        switch (codTrans) {
            case "08->08":
                return this; // permanecer en el mismo estado
            case "08->09":
                return new Edo09_Pedido();
            default:
                return this; // nos quedamos en el mismo estado
        }
    }

    @Override
    public String mostrarEstado() {
        int opcion = -1;

        Scanner sc = new Scanner(System.in);

        while (opcion == -1) {

            System.out.println("\nEstado: 08 Pagar");
            System.out.println("Mostrar listado de productos del carro de compra");
            System.out.println("1.- Pagar");
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
                    if (ProcesarPago()) {
                        return "08->09"; // pago realizado, pasar al pedido
                    } else {
                        return "08->08"; // quedar en el mismo estado                                                
                    }
                case 2:
                    return "08->07"; // volvemos al Carro de Compra

                default:
                    return "08->08"; // nos quedamos en el mismo estado
            }

        }

        return "08->08"; // nos quedamos en el mismo estado
    }

    private boolean ProcesarPago() {
        Scanner sc = new Scanner(System.in);

        // entrada de una cadena
        System.out.println("Monto a Pagar= ? ");
        String linea = sc.nextLine();

        float montoPagar;
        try {
            montoPagar = Float.parseFloat(linea);
        } catch (NumberFormatException numberFormatException) {
            montoPagar = 0; // vamos al siguiente estado
            return false;
        }

        System.out.println("Monto pagado= " + montoPagar);

        return true;
    }
}
