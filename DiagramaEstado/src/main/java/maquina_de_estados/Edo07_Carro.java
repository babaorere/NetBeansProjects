package maquina_de_estados;

import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class Edo07_Carro implements IEstado {

    @Override
    public IEstado getProximoEstado(String codTrans) {
        switch (codTrans) {
            case "07->07":
                return this; // permanecer en el mismo estado
            case "07->08":
                return new Edo08_Pagar();
            case "07->01":
                return new Edo01_Vacio();
            case "07->03":
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

            System.out.println("\nEstado: 07 Carro de Compra");
            System.out.println("Mostrar listado de productos del carro de compra");
            List<Producto> list = MainApp.getListCarro();
            for (int i = 0; i < list.size(); i++) {
                System.out.println("IDX= " + (i + 1) + ", Nombre= " + list.get(i).getNombre());
            }

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
                    if (MainApp.getListCarro().isEmpty()) {
                        return "07->07"; // no hay productos en el carro, quedamos en el mismo estado                        
                    } else {
                        return "07->08"; // pasamos a estado "08 Pagar"                                                
                    }
                case 2:
                    if (MainApp.getListProd().isEmpty()) {
                        return "07->01"; // pasar al estado "01 Vacio"
                    } else {
                        return "07->03"; // pasar al estado "03 No Vacio"
                    }

                default:
                    return "07->07"; // nos quedamos en el mismo estado
            }

        }

        return "07->07"; // nos quedamos en el mismo estado
    }
}
