package maquina_de_estados;

/**
 * Reepresenta a un objeto/producto 3D
 */
public class Producto {

    private String nombre;
    private int ancho, largo, alto;
    private String textura;
    private String color;

    /**
     * Inicializar los atributos del producto, con valores por defecto
     */
    public Producto() {
        this.nombre = "";
        this.ancho = 0;
        this.largo = 0;
        this.alto = 0;
        this.textura = "";
        this.color = "";

    }

    /**
     * Inicializar los atributos del producto, con valores dados en los parametros
     */
    public Producto(String nombre, int ancho, int largo, int alto, String textura, String color) {
        this.nombre = nombre;
        this.ancho = ancho;
        this.largo = largo;
        this.alto = alto;
        this.textura = textura;
        this.color = color;
    }

    @Override
    public String toString() {

        return "Nombre= " + this.nombre + "\n2 ancho= " + this.ancho + "\n3 largo= " + this.largo
                + "\n4 alto= " + this.alto + "\n5 textura= " + this.textura + "\n6 color= " + this.color;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @param ancho the ancho to set
     */
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    /**
     * @return the largo
     */
    public int getLargo() {
        return largo;
    }

    /**
     * @param largo the largo to set
     */
    public void setLargo(int largo) {
        this.largo = largo;
    }

    /**
     * @return the alto
     */
    public int getAlto() {
        return alto;
    }

    /**
     * @param alto the alto to set
     */
    public void setAlto(int alto) {
        this.alto = alto;
    }

    /**
     * @return the textura
     */
    public String getTextura() {
        return textura;
    }

    /**
     * @param textura the textura to set
     */
    public void setTextura(String textura) {
        this.textura = textura;
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

}
