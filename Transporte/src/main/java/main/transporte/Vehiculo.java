package main.transporte;

/**
 * Crear un método calculo kilometraje que en función al número de kilómetros recorrido aplique lo siguiente:
 * .- Si los kilómetros recorridos en menor o igual a 2000000 se incrementa el 1% del precio del vehículo.
 * .- Si la cantidad de kilómetros va entre 2000001 y 9000000 debe incrementar el 4 del costo del vehículo.
 * .- Si es mayor a 9000001 debe aplicarle el 1% del costo del vehículo.
 */
public class Vehiculo {

    private String color;
    private int precio;
    private int kmRecorridos;

    public Vehiculo() {
        this.color = null;
        this.precio = 0;
        this.kmRecorridos = 0;
    }

    public Vehiculo(String inColor, int inPrecio, int inKmRecorridos) {
        this.color = inColor;
        this.precio = inPrecio;
        this.kmRecorridos = inKmRecorridos;
    }

    public String mostrar() {
        return "Vehiculo -> Color= " + color + ", Precio= " + precio + ", KmRec.= " + kmRecorridos;
    }

    public void cambiarPrecio() {

        if (kmRecorridos <= 2_000_000) {
            precio = (int) (precio * 1.01);
        }
        if (kmRecorridos <= 9_000_000) {
            precio = (int) (precio * 1.04);
        } else {
            precio = (int) (precio * 0.99);
        }
    }

    @Override
    public String toString() {
        return mostrar();
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
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
     * @return the kmRecorridos
     */
    public int getKmRecorridos() {
        return kmRecorridos;
    }

    /**
     * @param kmRecorridos the kmRecorridos to set
     */
    public void setKmRecorridos(int kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

}
