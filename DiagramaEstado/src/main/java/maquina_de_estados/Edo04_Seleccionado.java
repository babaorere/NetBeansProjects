package maquina_de_estados;

import java.util.Scanner;

/**
 *
 */
public class Edo04_Seleccionado implements IEstado {

    @Override
    public IEstado getProximoEstado(String codTrans) {

        switch (codTrans) {
            case "04->04":
                return this; // permanecer en el mismo estado
            case "04->05":
                return new Edo05_Manipulado();
            case "04->03":
                return new Edo03_NoVacio();
            case "04->06":
                return new Edo06_Borrado();
            case "04->07":
                return new Edo07_Carro();
            default:
                return this; // nos quedamos en el mismo estado

        }

    }

    @Override
    public String mostrarEstado() {
        int opcion = -1;

        Scanner sc = new Scanner(System.in);

        while (opcion == -1) {

            System.out.println("\nEstado: 04 Seleccionado");
            System.out.println("Mostrar producto");
            Producto prod = MainApp.getProdSel();
            if (prod != null) {
                System.out.println("Producto seleccionado");
                System.out.println(prod);
            } else {
                System.out.println("Producto NO seleccionado");
            }
            System.out.println("1.- Modificar Producto");
            System.out.println("2.- Ir al Carro");
            System.out.println("3.- Borrar");
            System.out.println("4.- Retornar");

            // entrada de una cadena
            String linea = sc.nextLine();

            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException numberFormatException) {
                opcion = 2; // vamos al siguiente estado
            }

            switch (opcion) {

                case 1:
                    return "04->05"; // pasar al estado para manipular el producto
                case 2:
                    // Agregamos el producto a la lista de compras
                    if (prod != null) {
                        MainApp.getListCarro().add(prod);
                    }

                    return "04->07"; // pasar al estado para agregar al carrito de compra
                case 3:
                    return "04->06"; // pasar al estado para borrar
                case 4:
                    return "04->03"; // retornamos al estado "3 No Vacio"

                default:
                    return "04->04"; // nos quedamos en el mismo estado
            }

        }

        return "04->04"; // nos quedamos en el mismo estado

    }
}
