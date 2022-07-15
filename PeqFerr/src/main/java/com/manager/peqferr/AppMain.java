package com.manager.peqferr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author manager
 */
public class AppMain {

    private static void InsertarProducto() {
        //Ingrese datos usando BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n*********************************");
        System.out.println("*****  Pequeña Ferreteria  *****");
        System.out.println("*****  Insertar Producto   *****");

        String strCodigo;
        try {
            System.out.println("Codigo= ? ");
            strCodigo = reader.readLine().trim().toUpperCase();
        } catch (IOException ex) {
            strCodigo = "";
        }

        if (strCodigo.isEmpty()) {
            System.out.println("Valor invalido");
            return;
        }

        String strNombre;
        try {
            System.out.println("Nombre= ? ");
            strNombre = reader.readLine().trim().toUpperCase();
        } catch (IOException ex) {
            strNombre = "";
        }

        if (strNombre.isEmpty()) {
            System.out.println("Valor invalido");
            return;
        }

        int precio;
        try {
            System.out.println("Precio= ? ");
            precio = Integer.parseInt(reader.readLine().trim().toUpperCase());
        } catch (Exception ex) {
            precio = 0;
        }

        if (precio <= 0) {
            System.out.println("Valor invalido");
            return;
        }

        int stock;
        try {
            System.out.println("Stock= ? ");
            stock = Integer.parseInt(reader.readLine().trim().toUpperCase());
        } catch (Exception ex) {
            stock = 0;
        }

        if (stock <= 0) {
            System.out.println("Valor invalido");
            return;
        }

        // Añadir a la lista de Productos
        TProducto.AddItem(new TProducto(strCodigo, strNombre, precio, stock));

        System.out.println("Producto añadido a la Compra");

    }

    private static void CompraNueva() {
        System.out.println("\n\n*********************************");
        System.out.println("*****  Pequeña Ferreteria  *****");
        System.out.println("*******  Compra Nueva    *****");

        TItemCarrito.ClearList();

        System.out.println("*****  Carrito Nuevo   *****");
    }

    private static void AgregarAlCarrito() {
        //Ingrese datos usando BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n*********************************");
        System.out.println("*****  Pequeña Ferreteria  *****");
        System.out.println("*****  Agregar al Carrito  *****");

        TProducto[] aux = TProducto.getList();

        if (aux.length <= 0) {
            System.out.println("*****  NO HAY PRODUCTOS  *****");
            return;
        }

        System.out.println("Escoja un Producto por el ID");
        System.out.println("ID Producto");
        for (int i = 0; i < aux.length; i++) {
            System.out.println("Id= " + (i + 1) + ", Producto= " + aux[i]);
        }

        int id;
        try {
            System.out.println("ID= ? ");
            id = Integer.parseInt(reader.readLine().trim().toUpperCase());
        } catch (Exception ex) {
            id = 0;
        }

        if ((id < 1) || (id > aux.length)) {
            System.out.println("Valor invalido");
            return;
        }

        if (aux[id - 1].getStock() <= 0) {
            System.out.println("Producto agotado");
            return;
        }

        System.out.println("ID Producto= " + id);
        System.out.println("Nombre Producto= " + aux[id - 1].getNombre());
        System.out.println("Precio= " + aux[id - 1].getPrecio());
        System.out.println("Stock= " + aux[id - 1].getStock());

        int cantidad;
        try {
            System.out.println("Cantidad= ? ");
            cantidad = Integer.parseInt(reader.readLine().trim().toUpperCase());
        } catch (Exception ex) {
            cantidad = 0;
        }

        if ((cantidad < 0) || (cantidad > aux[id - 1].getStock())) {
            System.out.println("Valor invalido");
            return;
        }

        // Añadir al Carrito
        TItemCarrito.AddItem(new TItemCarrito(id - 1, cantidad));

        System.out.println("Producto añadido a la Compra");
    }

    private static void Resumen() {

        int total = 0;

        System.out.println("\n\n*********************************");
        System.out.println("*****  Pequeña Ferreteria  *****");
        System.out.println("**********  Resumen  ***********");

        TItemCarrito[] aux = TItemCarrito.getList();

        for (int i = 0; i < aux.length; i++) {
            int idxprod = aux[i].getIdxProd();
            TProducto prod = TProducto.getList()[idxprod];
            System.out.println("Producto= " + prod.getNombre() + ", Precio = ? " + prod.getPrecio() + ", Cantidad= " + aux[i].getCantidad());
            total += prod.getPrecio() * aux[i].getCantidad();
        }

        System.out.println("Total= " + total);
        System.out.println("Gracias por su Compra");
    }

    //***************************************************************************************************************
    /**
     * Clase para gestionar los productos dentro de una Ferreteria
     *
     */
    public static final class TProducto {

        // Atrubutos estaticos para el manejo de la lista de productos
        private static final int MAX_CANTT_PROD = 1000;
        private static TProducto[] list;
        private static int cantProd;

        // Bloque de inicializacion de variables estaticas
        static {
            ClearList();
        }

        private static void AddItem(TProducto producto) {
            list[cantProd] = producto;
            cantProd++;
        }

        private static void AjustarStock(int idxProd, int cantidad) {
            if (list[idxProd].stock >= cantidad) {
                list[idxProd].stock -= cantidad;
            }
        }

        private String codigo;
        private String Nombre;
        private int precio;
        private int stock;

        public TProducto(String codigo, String Nombre, int precio, int stock) {
            this.codigo = codigo;
            this.Nombre = Nombre;
            this.precio = precio;
            this.stock = stock;
        }

        @Override
        public String toString() {
            return "Código= " + codigo + ": Nombre= " + Nombre + ": Precio= " + String.valueOf(precio);
        }

        /**
         * @return the codigo
         */
        public String getCodigo() {
            return codigo;
        }

        /**
         * @param codigo the codigo to set
         */
        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        /**
         * @return the Nombre
         */
        public String getNombre() {
            return Nombre;
        }

        /**
         * @param Nombre the Nombre to set
         */
        public void setNombre(String Nombre) {
            this.Nombre = Nombre;
        }

        /**
         * @return the precio
         */
        public int getPrecio() {
            return precio;
        }

        /**
         * @param precio the precio to set
         */
        public void setPrecio(int precio) {
            this.precio = precio;
        }

        /**
         * @return the stock
         */
        public int getStock() {
            return stock;
        }

        /**
         * @param stock the stock to set
         */
        public void setStock(int stock) {
            this.stock = stock;
        }

        /**
         * Retorna una copia de los productos
         *
         * @return the list
         */
        public static TProducto[] getList() {

            TProducto[] aux = new TProducto[cantProd];

            for (int i = 0; i < cantProd; i++) {
                aux[i] = list[i];
            }

            return aux;
        }

        /**
         * @return the cantProd
         */
        public static int getCantProd() {
            return cantProd;
        }

        /**
         */
        public static void ClearList() {
            list = new TProducto[MAX_CANTT_PROD];
            cantProd = 0;
        }

    }

    //***************************************************************************************************************    
    /**
     * Clase Inmutable, para guardar la seleccion de la compra del Usuario. Es inmutable, porque una vez creado ya no se puede modificar
     */
    private static class TItemCarrito {

        // Atrubutos estaticos para el manejo de la lista del carrito de compras
        private static final int MAX_CANTT_ITEM = 1000;
        private static TItemCarrito[] list;
        private static int cantItem;

        // Bloque de inicializacion de variables estaticas
        static {
            ClearList();
        }

        // Atrubutos para el manejo propio de la compra
        private final int idxProd;
        private final int cantidad;

        public TItemCarrito(int idxProd, int cantidad) {
            this.idxProd = idxProd;
            this.cantidad = cantidad;
        }

        @Override
        public String toString() {
            return TProducto.getList()[idxProd].toString() + ": Cantidad= " + String.valueOf(cantidad);
        }

        /**
         * @return the producto
         */
        public int getIdxProd() {
            return idxProd;
        }

        /**
         * @return the cantidad
         */
        public int getCantidad() {
            return cantidad;
        }

        /**
         * @return the list
         */
        public static TItemCarrito[] getList() {

            TItemCarrito[] aux = new TItemCarrito[cantItem];

            for (int i = 0; i < cantItem; i++) {
                aux[i] = list[i];
            }

            return aux;
        }

        /**
         * @return the cantProd
         */
        public static int getCantProd() {
            return cantItem;
        }

        /**
         * @return the item
         */
        public static TItemCarrito AddItem(TItemCarrito item) {

            // Ajustar la cantidad en Stock;
            TProducto.AjustarStock(item.getIdxProd(), item.getCantidad());

            list[cantItem++] = item;

            return item;
        }

        /**
         * @return the item
         */
        public static void ClearList() {
            list = new TItemCarrito[MAX_CANTT_ITEM];
            cantItem = 0;
        }

    }

    // ************************************
    private static String PresentarMenu() {

        //Ingrese datos usando BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            System.out.println("\n\n*********************************");
            System.out.println("*****  Pequeña Ferreteria  *****");

            System.out.println("A. ADMINISTRATIVO INSERTAR PRODUCTO.");
            System.out.println("B. COMPRA NUEVA.");
            System.out.println("C. AGREGAR PRODUCTO AL CARRITO.");
            System.out.println("D. RESUMEN DE LA COMPRA.");
            System.out.println("E. SALIR.");

            System.out.println("OPCION= ? ");
            String opcion;
            try {
                opcion = reader.readLine().trim().toUpperCase();
            } catch (IOException ex) {
                opcion = "";
            }

            System.out.println("OPCION= " + opcion);

            if ((opcion.length() == 1) && ("ABCDE".contains(opcion))) {
                return opcion;
            } else {
                System.out.println("\n*** OPCION INVALIDA ***");
            }
        }

    }

    //***************************************************************************************************************
    // Atributos estaticos de la clase aplicacion
    /**
     * Punto entrada de la aplicacion
     *
     * @param args
     */
    public static void main(String[] args) {

        String opcion = "";
        while (!opcion.equals("E")) {

            opcion = PresentarMenu();

            switch (opcion) {
                case "A":
                    InsertarProducto();
                    break;

                case "B":
                    CompraNueva();
                    break;

                case "C":
                    AgregarAlCarrito();
                    break;

                case "D":
                    Resumen();
                    break;

                default:
                    System.out.println("\n\nOpción Invalida");
            }

        }

        System.out.println("\n\nGracias por su Compra");
    }

}
