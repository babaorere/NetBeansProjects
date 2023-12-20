package maquina_de_estados;

import java.util.Scanner;

/**
 *
 */
public class Edo05_Manipulado implements IEstado {

    @Override
    public IEstado getProximoEstado(String codTrans) {

        switch (codTrans) {
            case "05->05":
                return this; // permanecer en el mismo estado
            case "05->04":
                return new Edo04_Seleccionado();
            default:
                return this; // nos quedamos en el mismo estado

        }
    }

    @Override
    public String mostrarEstado() {

        Scanner sc = new Scanner(System.in);

        // Obtenemos el producto previamente seleccionado
        Producto prod = MainApp.getProdSel();

        // Validamos que el producto si exista
        if (prod == null) {
            return "05->05"; // nos quedamos en el mismo estado            
        }

        int opcion = -1;
        while (opcion == -1) {

            System.out.println("\nEstado: 05 Manipulado");
            System.out.println("Mostrar cada caracteristica del producto");
            System.out.println(prod);
            System.out.println("Selecciones la propiedad");
            System.out.println("1.- Nombre");
            System.out.println("2.- Ancho");
            System.out.println("3.- Alto");
            System.out.println("4.- Largo");
            System.out.println("5.- Textura");
            System.out.println("6.- Color");
            System.out.println("0.- Retornar");
            System.out.println("\nOpcion= ?");

            // entrada de una cadena
            String linea = sc.nextLine();

            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException numberFormatException) {
                opcion = 0; // vamos al estado de "04 Seleccionado"
            }

            switch (opcion) {

                case 0:
                    return "05->04"; // pasar al estado de seleccionado

                case 1:
                    System.out.println("Nombre= ? ");
                    String nombre = sc.nextLine().trim();

                    if (!nombre.isEmpty()) {
                        prod.setNombre(nombre);
                    }
                    return "05->05"; // nos quedamos en el mismo estado

                case 2:
                    System.out.println("Ancho mm= ? ");
                    linea = sc.nextLine();
                    int ancho;
                    try {
                        ancho = Integer.parseInt(linea);
                    } catch (NumberFormatException numberFormatException) {
                        ancho = 0;
                    }

                    // validamos
                    if (ancho > 0) {
                        prod.setAncho(ancho); // actualizamos el valor
                    }
                    return "05->05"; // nos quedamos en el mismo estado

                case 3:
                    System.out.println("alto mm= ? ");
                    linea = sc.nextLine();
                    int alto;
                    try {
                        alto = Integer.parseInt(linea);
                    } catch (NumberFormatException numberFormatException) {
                        alto = 0;
                    }

                    // validamos
                    if (alto > 0) {
                        prod.setAlto(alto); // actualizamos el valor
                    }
                    return "05->05"; // nos quedamos en el mismo estado

                case 4:
                    System.out.println("Largo mm= ? ");
                    linea = sc.nextLine();
                    int largo;
                    try {
                        largo = Integer.parseInt(linea);
                    } catch (NumberFormatException numberFormatException) {
                        largo = 0;
                    }

                    // validamos
                    if (largo > 0) {
                        prod.setLargo(largo); // actualizamos el valor
                    }
                    return "05->05"; // nos quedamos en el mismo estado

                case 5:
                    System.out.println("Textura= ? ");
                    String textura = sc.nextLine().trim();

                    // Validamos que no este vacio
                    if (!textura.isEmpty()) {
                        prod.setTextura(textura); // actualizamos el valor
                    }
                    return "05->05"; // nos quedamos en el mismo estado

                case 6:
                    // Validamos que no este vacio
                    System.out.println("Color= ? ");
                    String color = sc.nextLine().trim();

                    if (!color.isEmpty()) {
                        prod.setColor(color);
                    }
                    return "05->05"; // nos quedamos en el mismo estado

                default:
                    return "05->05"; // nos quedamos en el mismo estado
            }

        }

        return "05->05"; // nos quedamos en el mismo estado
    }

    private void ManipularCaracteristica(int opcion) {
        System.out.println("Caracteristica del producto modificada");
    }
}
