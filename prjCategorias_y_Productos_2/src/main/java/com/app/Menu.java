package com.app;

import javax.swing.JOptionPane;

public class Menu {

    public Menu() {
    }

    public static String menuPrincipal() {

        String opcion;
        boolean marca = true;

        do {
            String msj = new StringBuilder("\n1.- Categorías").append("\n2.- Productos").append("\n3.- Carro Compras").append("\n0.- Salir").toString();

            opcion = JOptionPane.showInputDialog(null, msj, "Menú Principal", JOptionPane.QUESTION_MESSAGE);

            if (opcion == null) {
                opcion = "0";
            } else {
                opcion = opcion.trim();
            }

            if ((opcion.length() == 1) && "0123".contains(opcion)) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        return opcion;
    }

    public static String subMenuCategorias(Categoria categoria) {

        String opcion;
        boolean marca = true;

        do {
            StringBuilder sb = new StringBuilder("\n1.- Listar Categorías")
                    .append("\n2.- Crear Categoría")
                    .append("\n3.- Seleccionar");

            if (categoria != null) {
                sb.append(categoria);
            }

            sb.append("\n4.- Editar nombre")
                    .append("\n5.- Editar caracteristicas")
                    .append("\n6.- Cambiar Estado")
                    .append("\n0.- Salir");

            opcion = JOptionPane.showInputDialog(null, sb.toString(), "Menú Categorías", JOptionPane.QUESTION_MESSAGE);

            if (opcion == null) {
                opcion = "0";
            } else {
                opcion = opcion.trim();
            }

            if ((opcion.length() == 1) && "0123456".contains(opcion)) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        return opcion;
    }

    public static String subMenuProductos(Producto producto) {

        String opcion;
        boolean marca = true;

        do {
            StringBuilder sb = new StringBuilder("\n1.- Listar Productos")
                    .append("\n2.- Crear Producto")
                    .append("\n3.- Seleccionar");

            if (producto != null) {
                sb.append(producto);
            }

            sb.append("\n4.- Editar descripción")
                    .append("\n5.- Editar categoría")
                    .append("\n6.- Editar precio")
                    .append("\n7.- Cambiar Estado")
                    .append("\n0.- Salir");

            opcion = JOptionPane.showInputDialog(null, sb.toString(), "Menú Productos", JOptionPane.QUESTION_MESSAGE);

            if (opcion == null) {
                opcion = "0";
            } else {
                opcion = opcion.trim();
            }

            if ((opcion.length() == 1) && "01234567".contains(opcion)) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        return opcion;
    }

    public static String subMenuCarroCampras() {

        String opcion;
        boolean marca = true;

        do {
            String sb = "\n1.- Listar Productos\n2.- Agregar Producto\n3.- Pagar\n0.- Salir";

            opcion = JOptionPane.showInputDialog(null, sb, "Carro de Compras", JOptionPane.QUESTION_MESSAGE);

            if (opcion == null) {
                opcion = "0";
            } else {
                opcion = opcion.trim();
            }

            if ((opcion.length() == 1) && "0123".contains(opcion)) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        return opcion;
    }

    public static void catListar() {

        if (Categoria.getCatCant() > 0) {
            JOptionPane.showMessageDialog(null, Categoria.toStringAll(false), "Listar Categorías", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No hay Categorías que mostrar", "Listar Categorías", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void proListar() {
        if (Producto.getProCant() > 0) {
            JOptionPane.showMessageDialog(null, Producto.toStringAll(false), "Listar Productos", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No hay Productos que mostrar", "Listar Productos", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static Categoria catCrear() {

        boolean marca = true;

        String nombre;
        do {
            nombre = JOptionPane.showInputDialog(null, "Nombre", "Crear Categoría", JOptionPane.QUESTION_MESSAGE).trim();

            if ((nombre != null) && nombre.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showInputDialog(null, "\nOpción invalida, intente de nuevo", "Crear Categoría", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        String caracteristicas;
        do {
            caracteristicas = JOptionPane.showInputDialog(null, "Caracteristicas", "Crear Categoría", JOptionPane.QUESTION_MESSAGE).trim();

            if ((caracteristicas != null) && caracteristicas.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showInputDialog(null, "\nOpción invalida, intente de nuevo", "Crear Categoría", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        Categoria categoria = new Categoria(nombre, caracteristicas, true);
        Categoria.add(categoria);

        JOptionPane.showMessageDialog(null, "Se ha crado la categoría: " + categoria.toString(), "Crear Categoría", JOptionPane.INFORMATION_MESSAGE);

        return categoria;
    }

    public static Producto proCrear() {

        boolean marca = true;
        Categoria list[] = Categoria.getList();

        String descripcion;
        do {
            descripcion = JOptionPane.showInputDialog(null, "Descripción", "Crear Producto", JOptionPane.QUESTION_MESSAGE).trim();

            if ((descripcion != null) && (descripcion.length() > 0)) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showInputDialog(null, "Opción invalida, intente de nuevo", "Crear Producto", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        int idx = -1;
        Categoria categoria;
        Integer precio = 0;

        do {
            String strCategoriaId = JOptionPane.showInputDialog(null, Categoria.toStringAll(true) + "\nID de la categoría", "Crear Producto", JOptionPane.QUESTION_MESSAGE).trim();

            if ((strCategoriaId == null) || (strCategoriaId.length() <= 0)) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Crear Producto", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                precio = Integer.parseInt(strCategoriaId);
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showInputDialog(null, "Opción invalida, intente de nuevo", "Crear Producto", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            idx = -1;
            for (int i = 0; i < Categoria.getCatCant(); i++) {
                if (list[i].getEstado() && list[i].getId().equals(precio)) { // solo se toman las categorias activas
                    idx = i;
                    break; // fin del ciclo
                }
            }

            if (idx >= 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showInputDialog(null, "Opción invalida, intente de nuevo", "Crear Producto", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        do {
            String strPrecio = JOptionPane.showInputDialog(null, "Precio", "Crear Producto", JOptionPane.QUESTION_MESSAGE).trim();

            if ((strPrecio == null) || (strPrecio.length() <= 0)) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Crear Producto", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                precio = Integer.parseInt(strPrecio);
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showInputDialog(null, "Opción invalida, intente de nuevo", "Crear Producto", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (precio > 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showInputDialog(null, "Opción invalida, intente de nuevo", "Crear Producto", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        Producto producto;
        if (idx >= 0) {

            producto = new Producto(descripcion, list[idx], precio, true);
            Producto.add(producto);

            JOptionPane.showMessageDialog(null, "Se ha creado el producto: " + producto.toString(), "Crear Producto", JOptionPane.INFORMATION_MESSAGE);

        } else {
            producto = null;
            JOptionPane.showMessageDialog(null, "No se ha podido agregar el producto", "Crear Producto", JOptionPane.INFORMATION_MESSAGE);
        }

        return producto;
    }

    public static Categoria catSeleccionar() {

        int idx = -1;
        Integer id;
        boolean marca = true;
        Categoria list[] = Categoria.getList();

        do {

            String strId = JOptionPane.showInputDialog(null, "Categoría id", "Seleccionar Categoría", JOptionPane.QUESTION_MESSAGE).trim();

            if ((strId == null) || (strId.length() <= 0)) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Seleccionar Categoría", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                id = Integer.valueOf(strId);
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Seleccionar Categoría", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            idx = -1;
            for (int i = 0; i < Categoria.getCatCant(); i++) {
                if (list[i].getEstado() && list[i].getId().equals(id)) { // solo se toman las categorias inactivas
                    idx = i;
                    break; // fin del ciclo
                }
            }

            if (idx >= 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Seleccionar Categoría", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        if (idx < 0) {
            return null;
        }

        return list[idx];

    }

    public static Producto proSeleccionar() {

        int idx = -1;
        Integer id;
        boolean marca = true;
        Producto list[] = Producto.getList();

        do {
            String strId = JOptionPane.showInputDialog(null, Categoria.toStringAll(true) + "Producto id", "Seleccionar Producto", JOptionPane.QUESTION_MESSAGE).trim();

            if ((strId == null) || (strId.length() <= 0)) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Seleccionar Producto", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                id = Integer.valueOf(strId);
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Seleccionar Producto", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            idx = -1;
            for (int i = 0; i < Producto.getProCant(); i++) {
                if (list[i].getEstado() && list[i].getId().equals(id)) { // solo se toman los productos inactivas
                    idx = i;
                    break; // fin del ciclo
                }
            }

            if (idx >= 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Seleccionar Producto", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        if (idx < 0) {
            return null;
        }

        return list[idx];
    }

    public static void catEditarNombre(Categoria categoria) {

        boolean marca = true;

        String nombre;
        do {
            nombre = JOptionPane.showInputDialog(null, "Nombre anterior= " + categoria.getNombre() + "\nNombre", "Categoría editar nombre", JOptionPane.QUESTION_MESSAGE).trim();

            if ((nombre != null) && nombre.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Categoría editar nombre", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        categoria.setNombre(nombre);

        JOptionPane.showMessageDialog(null, "Se ha modificado el nombre: " + categoria.toString(), "Categoría editar nombre", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void catEditarCaracteristicas(Categoria categoria) {

        boolean marca = true;

        String caracteristicas;
        do {
            caracteristicas = JOptionPane.showInputDialog(null, "Caracteristicas anterior= " + categoria.getCaracteristicas() + "\nNombre", "Categoría editar Caracteristicas", JOptionPane.QUESTION_MESSAGE).trim();

            if ((caracteristicas != null) && (caracteristicas.length() > 0)) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "\nOpción invalida, intente de nuevo", "Categoría editar Caracteristicas", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        categoria.setCaracteristicas(caracteristicas);

        JOptionPane.showMessageDialog(null, "Se ha modificado las caracteristicas: " + categoria.toString(), "Categoría editar caracteristicas", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void catCambiarEstado(Categoria categoria) {

        categoria.setEstado(!categoria.getEstado());

        JOptionPane.showMessageDialog(null, "Se ha modificado el estado: " + categoria.toString(), "Categoría modificar estado", JOptionPane.INFORMATION_MESSAGE);
    }

    public static Producto proEditarDescripcion(Producto producto) {

        boolean marca = true;

        String descripcion;
        do {
            descripcion = JOptionPane.showInputDialog(null, "Descripción anterior: " + producto.getDescripcion() + "\nDescripción", "Producto editar descripción", JOptionPane.QUESTION_MESSAGE).trim();

            if ((descripcion != null) && descripcion.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "\nOpción invalida, intente de nuevo", "Producto editar descripción", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        producto.setDescripcion(descripcion);

        JOptionPane.showMessageDialog(null, "Se ha modificado la descripción: " + producto.toString(), "Producto editar descripción", JOptionPane.INFORMATION_MESSAGE);

        return producto;
    }

    public static Producto proEditarCategoria(Producto producto) {

        int idx = -1;
        Integer categoriaId;
        boolean marca = true;
        Categoria list[] = Categoria.getList();

        do {
            String strCategoriaId = JOptionPane.showInputDialog(null, Categoria.toStringAll(true) + "\nID de la categoría anterior: " + producto.getCategoria().getId(), "Producto modificar Categoría", JOptionPane.QUESTION_MESSAGE).trim();

            if ((strCategoriaId == null) || (strCategoriaId.length() <= 0)) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Producto modificar categoría", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                categoriaId = Integer.parseInt(strCategoriaId);
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Producto modificar categoría", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            idx = -1;
            for (int i = 0; i < Categoria.getCatCant(); i++) {
                if (list[i].getEstado() && list[i].getId().equals(categoriaId)) { // solo se toman las categorias activas
                    idx = i;
                    break; // fin del ciclo
                }
            }

            if (idx >= 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Producto modificar categoría", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        if (idx < 0) {
            return null;
        }

        producto.setCategoria(list[idx]);

        JOptionPane.showMessageDialog(null, "Se ha modificado el producto: " + producto.toString(), "Producto modificar categoría", JOptionPane.INFORMATION_MESSAGE);

        return producto;
    }

    public static Producto proCambiarEstado(Producto producto) {

        producto.setEstado(!producto.getEstado());

        JOptionPane.showMessageDialog(null, "Se ha modificado el estado: " + producto.toString(), "Producto modificar estado", JOptionPane.INFORMATION_MESSAGE);

        return producto;
    }

    public static Producto proCambiarPrecio(Producto producto) {

        Integer precio = 0;
        boolean marca = true;

        do {
            String strPrecio = JOptionPane.showInputDialog(null, "\nPrecio anterior: " + producto.getPrecio(), "Producto modificar precio", JOptionPane.QUESTION_MESSAGE).trim();

            if ((strPrecio == null) || (strPrecio.length() <= 0)) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Producto modificar precio", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                precio = Integer.parseInt(strPrecio);
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Producto modificar precio", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (precio >= 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Producto modificar precio", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        producto.setPrecio(precio);

        JOptionPane.showMessageDialog(null, "Se ha modificado el precio: " + producto.toString(), "Producto modificar precio", JOptionPane.INFORMATION_MESSAGE);

        return producto;
    }

    /**
     * Lista los productos del carro de compra
     *
     * @param carCom
     */
    static void ccListarProductos(CarroCompra carCom) {
        if (carCom.getProCant() > 0) {
            JOptionPane.showMessageDialog(null, carCom.toShortStringAll(true), "Listar Productos", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No hay Productos que mostrar", "Listar Productos", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    static void ccAgregarProducto(CarroCompra carCom) {

        Integer cantidad = 0;
        Integer categoriaId;
        boolean marca = true;
        Producto list[] = Producto.getList();

        Integer idx = -1;
        do {
            String strProductoId = JOptionPane.showInputDialog(null, Producto.toShortStringAll(true) + "\nID del producto", "Agregar Producto al Carro de Compra", JOptionPane.QUESTION_MESSAGE).trim();

            if ((strProductoId == null) || (strProductoId.length() <= 0)) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Agregar Producto al Carro de Compra", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                categoriaId = Integer.parseInt(strProductoId);
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showInputDialog(null, "Opción invalida, intente de nuevo", "Agregar Producto al Carro de Compra", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            idx = -1;
            for (int i = 0; i < Producto.getProCant(); i++) {
                if (list[i].getEstado() && list[i].getId().equals(categoriaId)) { // solo se toman los productos activos
                    idx = i;
                    break; // fin del ciclo
                }
            }

            if (idx >= 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showInputDialog(null, "Opción invalida, intente de nuevo", "Agregar Producto al Carro de Compra", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        do {
            String strCantidad = JOptionPane.showInputDialog(null, "Cantidad", "Agregar Producto al Carro de Compra", JOptionPane.QUESTION_MESSAGE).trim();

            if ((strCantidad == null) || (strCantidad.length() <= 0)) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Agregar Producto al Carro de Compra", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                cantidad = Integer.parseInt(strCantidad);
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Agregar Producto al Carro de Compra", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (cantidad > 0) {
                marca = false; // salir del "do while"
            } else {
                JOptionPane.showMessageDialog(null, "Opción invalida, intente de nuevo", "Producto modificar precio", JOptionPane.ERROR_MESSAGE);
            }

        } while (marca);

        if (idx >= 0) {
            ProductoCarroCompra proCar = new ProductoCarroCompra(list[idx], cantidad);
            carCom.add(proCar);
            JOptionPane.showMessageDialog(null, "Se ha agregado el producto: " + proCar.toString(), "Agregar Producto al Carro de Compra", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showInputDialog(null, "No fue agregado algun producto", "Agregar Producto al Carro de Compra", JOptionPane.ERROR_MESSAGE);
        }

    }

    static void ccPagar(CarroCompra carCom) {

        int total = 0;
        ProductoCarroCompra list[] = carCom.getList();

        for (int i = 0; i < carCom.getProCant(); i++) {
            total += list[i].getProducto().getPrecio() * list[i].getCantidad();
        }

        JOptionPane.showMessageDialog(null, "El total de la factura es: " + total, "Pagar Carro de Compra", JOptionPane.INFORMATION_MESSAGE);

    }

}
