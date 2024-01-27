package com.app;

import javax.swing.JOptionPane;

public class AppMain {

    public static void main(String[] args) {

        // Categorias iniciales
        Categoria catTerror = new Categoria("Terror", "Libros del género Terror y similares", true);
        Categoria.add(catTerror);

        Categoria catAmor = new Categoria("Amor", "Libros del género Amor, Amistad, Familia y similares", true);
        Categoria.add(catAmor);

        Categoria catComedia = new Categoria("Comedia", "Libros del género Comedia y similares", true);
        Categoria.add(catComedia);

        Categoria catEducativo = new Categoria("Educativo", "Libros Educativos, Ciencia y Tecnología", true);
        Categoria.add(catEducativo);

        // Productos iniciales
        Producto.add(new Producto("El Resplandor", catTerror, 300, true));
        Producto.add(new Producto("Drácula", catTerror, 350, true));

        Producto.add(new Producto("Orgullo y Prejuicio", catAmor, 400, true));
        Producto.add(new Producto("Cumbres Borrascosas", catAmor, 399, true));
        Producto.add(new Producto("Ana Karenina", catAmor, 500, true));

        Producto.add(new Producto("Maldito Karma ", catComedia, 250, true));
        Producto.add(new Producto("El Proyecto Esposa", catComedia, 299, true));

        Producto.add(new Producto("El Arte de dar Clase", catEducativo, 199, true));

        String opcion;
        Categoria categoria = null;
        Producto producto = null;

        do {
            opcion = Menu.menuPrincipal();

            switch (opcion) {
                case "1":
                    String opcion_1;
                    do {
                        opcion_1 = Menu.subMenuCategorias(categoria);
                        switch (opcion_1) {
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
                                Menu.catEditarCaracteristicas(categoria);
                                break;
                            case "6":
                                Menu.catCambiarEstado(categoria);
                                break;
                        }
                    } while (!opcion_1.equals("0"));
                    break;

                case "2":
                    String opcion_2;
                    do {
                        opcion_2 = Menu.subMenuProductos(producto);
                        switch (opcion_2) {
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
                                Menu.proCambiarPrecio(producto);
                                break;
                            case "7":
                                Menu.proCambiarEstado(producto);
                                break;
                        }
                    } while (!opcion_2.equals("0"));
                    break;

                case "3":
                    String opcion_3;

                    // Al entrar a la opcion crearmos un nuevo carro de compra
                    CarroCompra carCom = new CarroCompra();
                    do {
                        opcion_3 = Menu.subMenuCarroCampras();
                        switch (opcion_3) {
                            case "1":
                                Menu.ccListarProductos(carCom);
                                break;
                            case "2":
                                Menu.ccAgregarProducto(carCom);
                                break;
                            case "3":
                                Menu.ccPagar(carCom);
                                opcion_3 = "0"; // fin del menu
                                break;
                        }
                    } while (!opcion_3.equals("0"));
                    break;

                case "0":
                    break;

            }

        } while (!opcion.equals("0"));

        JOptionPane.showMessageDialog(null, "Gracias por utilizar Categorías y Productos", "Categorías y Productos", JOptionPane.INFORMATION_MESSAGE);

    }

}
