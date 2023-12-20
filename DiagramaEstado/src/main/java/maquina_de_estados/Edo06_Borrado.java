package maquina_de_estados;

import java.util.Scanner;

/**
 *
 */
public class Edo06_Borrado implements IEstado {

    @Override
    public IEstado getProximoEstado(String codTrans) {

        switch (codTrans) {
            case "06->06":
                return this; // permanecer en el mismo estado
            case "06->01":
                return new Edo01_Vacio();
            case "06->03":
                return new Edo03_NoVacio();
            default:
                return this; // nos quedamos en el mismo estado
        }
    }

    @Override
    public String mostrarEstado() {
        int opcion = -1;

        Scanner sc = new Scanner(System.in);

        while (opcion == -1) {

            Producto prod = MainApp.getProdSel();
            if (prod == null) {
                System.out.println("Producto NO seleccionado");
                if (MainApp.getListProd().isEmpty()) {
                    return "06->01"; // pasamos a estado "01 Vacio", porque NO hay productos                                                                    
                } else {
                    return "06->03"; // pasamos a estado "03 No Vacia", ya que hay producto
                }
            }

            System.out.println("\nEstado: 06 Borrado");
            System.out.println("Procedemos a borrar el producto de la lista de productos y");
            System.out.println("de la lista de Carro de Compra (si esta incluido)");
            System.out.println("Producto seleccionado");
            System.out.println(prod);
            System.out.println("1.- Borrar");
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
                    BorrarProducto();
                    if (MainApp.getListProd().isEmpty()) {
                        return "06->01"; // pasamos a estado inicial, ya que no hay productos                        
                    } else {
                        return "06->03"; // pasamos a estado "03 No Vacia"                                                
                    }
                case 2:
                    return "06->04"; // pasar al estado de seleccionado

                default:
                    return "06->06"; // nos quedamos en el mismo estado
            }

        }

        return "06->06"; // nos quedamos en el mismo estado
    }

    private void BorrarProducto() {

        Producto prod = MainApp.getProdSel();

        // Eliminamos el producto de las listas
        MainApp.getListProd().remove(prod);
        MainApp.getListCarro().remove(prod);

        System.out.println("Producto Eliminado");
    }
}
