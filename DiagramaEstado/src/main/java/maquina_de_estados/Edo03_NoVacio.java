package maquina_de_estados;

import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class Edo03_NoVacio implements IEstado {

    public IEstado getProximoEstado(String codTrans) {

        switch (codTrans) {
            case "03->03":
                return this; // permanecer en el mismo estado
            case "03->02":
                return new Edo02_Agregar();
            case "03->04":
                return new Edo04_Seleccionado();
            default:
                return this; // nos quedamos en el mismo estado

        }

    }

    @Override
    public String mostrarEstado() {
        String opcion = null;

        Scanner sc = new Scanner(System.in);

        while (opcion == null) {

            System.out.println("\nEstado: 03 No Vacio");

            System.out.println("Lista de Productos");
            List<Producto> list = MainApp.getListProd();
            for (int i = 0; i < list.size(); i++) {
                System.out.println("IDX= " + (i + 1) + ", Nombre= " + list.get(i).getNombre());
            }

            System.out.println();
            System.out.println("A.- AgregarProducto");
            System.out.println("B.- Seleccionar Producto");
            System.out.println("C.- Siguiente transicion");
            System.out.println("Opcion= ? ");

            // entrada de una cadena
            opcion = sc.nextLine().trim().toUpperCase();

            switch (opcion) {

                case "A":
                    return "03->02"; // pasar al estado
                case "B":
                    if (MainApp.getListProd().isEmpty()) {
                        return "03->02"; // si NO hay productos en la lista, vamos al estado agregar
                    } else {
                        MainApp.setProdSel(null);;
                        if (SeleccionarProducto() >= 0) {
                            return "03->04"; // si hay un producto seleccionado, pasamos de estado
                        } else {
                            return "03->03"; // si NO hay producto seleccionado, quedamos en el mismo estado
                        }
                    }
                default:
                    return "03->03"; // nos quedamos en el mismo estado
            }

        }

        return "03->03"; // nos quedamos en el mismo estado
    }

    private int SeleccionarProducto() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\nSeleccionar el Producto");
        System.out.println("Lista de Productos");
        List<Producto> list = MainApp.getListProd();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("IDX= " + (i + 1) + ", Nombre= " + list.get(i).getNombre());
        }

        System.out.println("IDX= ? ");
        String linea = sc.nextLine();

        int idx;
        try {
            idx = Integer.parseInt(linea) - 1;
        } catch (NumberFormatException numberFormatException) {
            idx = -1;
        }

        // Mostar los productos
        // Seleccionar el indice del producto
        System.out.println("Producto Seleccionado");

        // marcamos cual es el producto seleccionado
        if (idx >= 0) {
            MainApp.setProdSel(list.get(idx));
        } else {
            MainApp.setProdSel(null);
        }

        return idx; // deberia retornar el indice del producto seleccionado
    }
}
