package com.app;

/**
 *
 */
public class CarroCompra {

    // Aqui guardaremos la lista de categorias
    private final ProductoCarroCompra list[];
    private int proCant = 0;

    public CarroCompra() {
        list = new ProductoCarroCompra[100];
        proCant = 0;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        // Agregar todos los productos
        for (int i = 0; i < proCant; i++) {
            sb.append(list[i].toString()).append("\n");
        }

        return sb.toString();
    }

    /**
     * Listado de descripciones cortas de productos
     *
     * @return
     */
    public String toShortStringAll(Boolean estado) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < proCant; i++) {
            ProductoCarroCompra proCarro = list[i];
            if (!estado || estado && proCarro.getProducto().getEstado()) {
                sb.append(list[i].toShortString()).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * @return the list
     */
    public ProductoCarroCompra[] getList() {
        return list;
    }

    /**
     * @return the proCant
     */
    public int getProCant() {
        return proCant;
    }

    public void add(ProductoCarroCompra proCar) {
        list[proCant] = proCar;
        proCant++;
    }

}
