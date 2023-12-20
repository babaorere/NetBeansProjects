package maquina_de_estados;

import java.util.Scanner;

/**
 *
 */
public class Edo02_Agregar implements IEstado {

    @Override
    public IEstado getProximoEstado(String codTrans) {

        switch (codTrans) {
            case "02->02":
                return this; // permanecer en el mismo estado
            case "02->03":
                if (MainApp.getListProd().size() > 0) {
                    return new Edo03_NoVacio();
                } else {
                    return this; // si NO hay productos en la lista, nos quedamos en el mismo estado                        
                }
            default:
                return this; // nos quedamos en el mismo estado

        }
    }

    @Override
    public String mostrarEstado() {
        int opcion = -1;

        Scanner sc = new Scanner(System.in);

        while (opcion == -1) {

            System.out.println("\nEstado: 02 Agregar");
            System.out.println("Este estado se encarga de agregar nuevos productos al sito,");
            System.out.println("la siguiente transicion, si hay productos en la lista es: \"03 No Vacio\"");
            System.out.println("de lo contrario, permanece en el mismo estado");
            System.out.println("1.- Agregar Producto");
            System.out.println("2.- Siguiente transicion");

            // entrada de una cadena
            String linea = sc.nextLine();

            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException numberFormatException) {
                opcion = 2; // vamos al siguiente estado
            }

            switch (opcion) {

                case 1:
                    AgregarProducto();
                    return "02->02"; // permanecer en el mismo estado
                case 2:
                    if (MainApp.getListProd().isEmpty()) {
                        return "02->02"; // si NO hay productos en la lista, nos quedamos en el mismo estado                        
                    } else {
                        return "02->03"; // si hay productos en la lista vamos al estado "3 No Vacio"
                    }
                default:
                    return "02->02"; // nos quedamos en el mismo estado
            }

        }

        return "02->02"; // nos quedamos en el mismo estado
    }

    private void AgregarProducto() {

        System.out.println("\nAgregar Producto");

        Scanner sc = new Scanner(System.in);

        System.out.println("Nombre= ? ");
        String nombre = sc.nextLine().trim();

        System.out.println("Ancho mm= ? ");
        String linea = sc.nextLine();
        int ancho;
        try {
            ancho = Integer.parseInt(linea);
        } catch (NumberFormatException numberFormatException) {
            ancho = 0;
        }

        System.out.println("alto mm= ? ");
        linea = sc.nextLine();
        int alto;
        try {
            alto = Integer.parseInt(linea);
        } catch (NumberFormatException numberFormatException) {
            alto = 0;
        }

        System.out.println("Largo mm= ? ");
        linea = sc.nextLine();
        int largo;
        try {
            largo = Integer.parseInt(linea);
        } catch (NumberFormatException numberFormatException) {
            largo = 0;
        }

        System.out.println("Textura= ? ");
        String textura = sc.nextLine();

        System.out.println("Color= ? ");
        String color = sc.nextLine();

        // Agregamos el producto, a la lista de productos
        MainApp.getListProd().add(new Producto(nombre, ancho, largo, alto, textura, color));

        System.out.println("Producto Agregado");

    }
}
