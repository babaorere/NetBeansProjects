package com.app;

/**
 *
 */
public class ProductoCarroCompra {

    private final Producto producto;
    private final int cantidad;

    public ProductoCarroCompra(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     *
     */
    @Override
    public String toString() {
        return producto.toString() + ", \"cantidad\": " + cantidad;
    }

    /**
     * Descripción corta del Producto
     *
     * @return
     */
    public String toShortString() {
        return producto.toShortString() + ", \"cantidad\": " + cantidad;
    }

}
