package com.app;

public class AppMain {

    public static void main(String[] args) {

        String opcion;
        Categoria categoria = null;
        Producto producto = null;

        do {
            opcion = Menu.menuPrincipal();

            String opcion2;
            switch (opcion) {
                case "1":
                    do {
                        opcion2 = Menu.subMenuCategorias(categoria);
                        switch (opcion2) {
                            case "1":
                                Menu.catListar();
                                break;
                            case "2":
                                Menu.catCrear();
                                break;
                            case "3":
                                categoria = Menu.catSeleccionar();
                                break;
                            case "4":
                                Menu.catEditarNombre(categoria);
                                break;
                            case "5":
                                Menu.catCaracteristicas(categoria);
                                break;
                            case "6":
                                Menu.catCambiarEstado(categoria);
                                break;
                        }
                    } while (!opcion2.equals("0"));
                    break;

                case "2":
                    do {
                        opcion2 = Menu.subMenuProductos(producto);
                        switch (opcion2) {
                            case "1":
                                Menu.proListar();
                                break;
                            case "2":
                                Menu.proCrear();
                                break;
                            case "3":
                                producto = Menu.proSeleccionar();
                                break;
                            case "4":
                                Menu.proEditarDescripcion(producto);
                                break;
                            case "5":
                                Menu.proEditarCategoria(producto);
                                break;
                            case "6":
                                Menu.proCambiarEstado(producto);
                                break;
                        }
                    } while (!opcion2.equals("0"));
                    break;

                case "0":
                    break;

            }

        } while (!opcion.equals("0"));

        System.out.println("\n\n\nGracias por utilizar Categorias y Productos\n\n");

    }
}
