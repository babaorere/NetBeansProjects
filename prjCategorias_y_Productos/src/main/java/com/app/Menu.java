package com.app;

import java.util.List;
import java.util.Scanner;

public class Menu {

    public Menu() {
    }

    public static String menuPrincipal() {

        String opcion;
        boolean marca = true;
        Scanner in = new Scanner(System.in);

        System.out.println("\n\n\n***   Menu de la Aplicacion   ***");

        do {
            System.out.println("\n1.- Categorias");
            System.out.println("\n2.- Productos");
            System.out.println("\n0.- Salir");
            System.out.println("\nOpcion= ? ");

            opcion = in.nextLine().trim();

            if ((opcion.length() == 1) && "012".contains(opcion)) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpcion invalida, intente de nuevo");
            }

        } while (marca);

        return opcion;
    }

    public static String subMenuCategorias(Categoria categoria) {

        String opcion;
        boolean marca = true;
        Scanner in = new Scanner(System.in);

        System.out.println("\n\n\n***   Sub Menu de Categorias   ***");

        do {
            System.out.println("\n1.- Listar Categorias");
            System.out.println("\n2.- Crear Categoria");
            System.out.println("\n3.- Seleccionar");
            if (categoria != null) {
                System.out.println(categoria);
            }

            System.out.println("\n4.- Editar nombre");
            System.out.println("\n5.- Editar caracteristicas");
            System.out.println("\n6.- Cambiar Estado");
            System.out.println("\n0.- Salir");
            System.out.println("\nOpcion= ? ");

            opcion = in.nextLine().trim();

            if ((opcion.length() == 1) && "0123456".contains(opcion)) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpcion invalida, intente de nuevo");
            }

        } while (marca);

        return opcion;
    }

    public static String subMenuProductos(Producto producto) {

        String opcion;
        boolean marca = true;
        Scanner in = new Scanner(System.in);

        System.out.println("\n\n\n***   Sub Menu de Productos   ***");

        do {
            System.out.println("\n1.- Listar Productos");
            System.out.println("\n2.- Crear Producto");
            System.out.println("\n3.- Seleccionar");
            if (producto != null) {
                System.out.println(producto);
            }

            System.out.println("\n4.- Editar descripción");
            System.out.println("\n5.- Editar categoria");
            System.out.println("\n6.- Cambiar Estado");
            System.out.println("\n0.- Salir");
            System.out.println("\nOpcion= ? ");

            opcion = in.nextLine().trim();

            if ((opcion.length() == 1) && "0123456".contains(opcion)) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        return opcion;
    }

    public static void catListar() {
        System.out.println("\n\nLista Categorias\n");
        List<Categoria> list = Categoria.getList();
        if (list.size() > 0) {
            System.out.println(Categoria.toStringAll(false));
        } else {
            System.out.println("No hay Categorias que mostrar");
        }
    }

    public static void proListar() {
        System.out.println("\n\nLista todos los Productos\n");
        List<Producto> list = Producto.getList();
        if (list.size() > 0) {
            System.out.println(Producto.toStringAll(false));
        } else {
            System.out.println("No hay Productos que mostrar");
        }
    }

    public static Categoria catCrear() {

        System.out.println("\n\nCrear Categoria");

        boolean marca = true;
        Scanner in = new Scanner(System.in);

        String nombre;
        do {
            System.out.println("\nNombre= ? ");

            nombre = in.nextLine().trim();

            if (nombre.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        String caracteristicas;
        do {
            System.out.println("\nCaracteristicas= ? ");

            caracteristicas = in.nextLine().trim();

            if (caracteristicas.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        Categoria categoria = new Categoria(nombre, caracteristicas, true);
        Categoria.add(categoria);

        System.out.println("\nSe ha crado la categoria:");
        System.out.println(categoria);
        return categoria;
    }

    public static Categoria catSeleccionar() {

        System.out.println("\n\n\nSeleccionar Categoria\n\n");

        int idx;
        Integer id;
        boolean marca = true;
        List<Categoria> list = Categoria.getList();
        Scanner in = new Scanner(System.in);

        do {
            System.out.println(Categoria.toStringAll(true));

            System.out.println("\nCategoria id= ? ");

            id = in.nextInt();

            idx = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getEstado() && list.get(i).getId().equals(id)) { // solo se toman las categorias inactivas
                    idx = i;
                    break; // fin del ciclo
                }
            }

            if (idx >= 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        return list.get(idx);

    }

    public static Producto proSeleccionar() {

        System.out.println("\n\nMenu Seleccionar Producto\n");

        int idx;
        Integer id;
        boolean marca = true;
        final List<Producto> list = Producto.getList();
        Scanner in = new Scanner(System.in);

        do {
            System.out.println(Producto.toStringAll(true));

            System.out.println("\nProducto id= ? ");

            id = in.nextInt();

            idx = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getEstado() && list.get(i).getId().equals(id)) { // solo se toman los productos inactivas
                    idx = i;
                    break; // fin del ciclo
                }
            }

            if (idx >= 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        return list.get(idx);
    }

    public static void catEditarNombre(Categoria categoria) {

        System.out.println("\n\n\nMenu Categoria editar nombre\n\n");

        boolean marca = true;
        Scanner in = new Scanner(System.in);

        String nombre;
        do {
            System.out.println("\nNombre anterior= " + categoria.getNombre());

            System.out.println("\nNombre= ? ");

            nombre = in.nextLine().trim();

            if (nombre.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        categoria.setNombre(nombre);

        System.out.println("\nSe ha modificado el nombre");
        System.out.println(categoria);

    }

    public static void catCaracteristicas(Categoria categoria) {
        System.out.println("\n\n\n***   Menu Categoria modificar caracteristicas\n\n");

        boolean marca = true;
        Scanner in = new Scanner(System.in);

        String caracteristicas;
        do {
            System.out.println("\nCaracteristicas anterior= " + categoria.getCaracteristicas());
            System.out.println("\nCaracteristicas= ? ");

            caracteristicas = in.nextLine().trim();

            if (caracteristicas.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        categoria.setCaracteristicas(caracteristicas);

        System.out.println("\nSe ha modificado las caracteristicas");
        System.out.println(categoria);

    }

    public static void catCambiarEstado(Categoria categoria) {
        System.out.println("\n\n***   Categoria modificar estado\n");

        categoria.setEstado(!categoria.getEstado());

        System.out.println("\nSe ha modificado el estado");
        System.out.println(categoria);
    }

    public static void proCrear() {
        System.out.println("\n\nCrear Producto\n");

        boolean marca = true;
        Scanner in = new Scanner(System.in);
        List<Categoria> list = Categoria.getList();

        String descripcion;
        do {
            System.out.println("\nDescripcion= ? ");

            descripcion = in.nextLine().trim();

            if (descripcion.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        int idx;
        Integer categoriaId;
        Categoria categoria;

        do {
            System.out.println("\nCategorias:");
            System.out.println(Categoria.toStringAll(true)); // mostrar solo los activos

            System.out.println("\nID de la categoria= ? ");

            categoriaId = in.nextInt();

            idx = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getEstado() && list.get(i).getId().equals(categoriaId)) { // solo se toman las categorias activas
                    idx = i;
                    break; // fin del ciclo
                }
            }

            if (idx >= 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        Producto producto = new Producto(descripcion, list.get(idx), true);
        Producto.add(producto);

        System.out.println("\nSe ha crado el Producto");
        System.out.println(producto);

    }

    public static Producto proEditarDescripcion(Producto producto) {
        System.out.println("\n\n***   Menu Producto editar descripcion\n\n");

        boolean marca = true;
        Scanner in = new Scanner(System.in);

        String descripcion;
        do {
            System.out.println("\nDescripcion anterior= " + producto.getDescripcion());

            System.out.println("\nDescripcion= ? ");

            descripcion = in.nextLine().trim();

            if (descripcion.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        producto.setDescripcion(descripcion);

        System.out.println("\nSe ha modificado la descripcion");
        System.out.println(producto);
        return producto;
    }

    public static Producto proEditarCategoria(Producto producto) {

        System.out.println("\n\n\nProducto editar categoria\n\n");

        Integer categoriaId;
        boolean marca = true;
        List<Categoria> list = Categoria.getList();
        Scanner in = new Scanner(System.in);

        String descripcion;
        int idx;
        do {
            System.out.println("\nCategoria anterior= " + producto.getCategoria().getNombre());

            System.out.println(Categoria.toStringAll(true));

            System.out.println("\nID de la nueva categoria= ? ");

            categoriaId = in.nextInt();

            idx = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getEstado() && list.get(i).getId().equals(categoriaId)) { // solo se toman las categorias activas
                    idx = i;
                    break; // fin del ciclo
                }
            }

            if (idx >= 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpción invalida, intente de nuevo");
            }

        } while (marca);

        producto.setCategoria(list.get(idx));

        System.out.println("\nSe ha modificado la categoria");
        System.out.println(producto);
        return producto;
    }

    public static Producto proCambiarEstado(Producto producto) {
        System.out.println("\n\n\nProducto modificar estado\n\n");

        producto.setEstado(!producto.getEstado());

        System.out.println("\nSe ha modificado el estado");
        System.out.println(producto);
        return producto;
    }
}
