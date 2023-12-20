package main.transporteterrestre;

/**
 * Crear una clase Vehículo con los siguientes atributos:
 * .- color,
 * .- precio,
 * .- kilómetros recorridos.
 * Los atributos NO pueden ser accedidos desde otras clases.
 * Crear un constructor con parámetros y otro sin parámetros,
 * Agregar métodos get y set para cada uno de los atributos.
 * Crear un método que permita mostrar los atributos de la clase.
 * Crear un método calculo kilometraje que en función al número de kilómetros recorrido aplique lo siguiente:
 * .- Si los kilómetros recorridos en menor o igual a 2000000 se incrementa el 1% del precio del vehículo.
 * .- Si la cantidad de kilómetros va entre 2000001 y 9000000 debe incrementar el 4 del costo del vehículo.
 * .- Si es mayor a 9000001 debe aplicarle el 1% del costo del vehículo.
 * Debe mostrar el costo del vehículo. Este método debe ser utilizado en todas las clases.
 */
public class Vehiculo {

    // Estos atributos se declaran privados, para que NO puedan ser accedidos por otras classes,
    // solamente podran ser accedidos dentro de la clase Vehiculo, esto es el concepto de encapsulamiento de la POO, 
    // y se realiza para evitar el acceso directo de otras clases, y es una medida para contrarrestar problemas
    // derivados de una manipulacion externa invalida
    private String color;
    private int precio;
    private int kmRecorridos;

    // Este es un atributo adicional, para poder controlar el ajuste del precio, 
    // y evitar que sea ajustado varias veces
    float ajuste;

    /**
     * Constructor por defecto, sin parametros. Estos valores son utilizados para inicializar los valores de los atributos
     * al momento de la creacion del objeto con el operador 'new'
     */
    public Vehiculo() {
        // Le damos valores iniciales a los atributos
        this.color = null; // sin valor
        this.precio = 0;
        this.kmRecorridos = 0;

        this.ajuste = 0;
    }

    /**
     * Contructor con parametros. Estos parametros son utilizados para inicializar los valores de los atributos
     * al momento de la creacion del objeto con el operador 'new'
     *
     */
    public Vehiculo(String color, int precio, int kmRecorridos) {
        this.color = color;
        this.precio = precio;
        this.kmRecorridos = kmRecorridos;

        this.ajuste = 0;
    }

    /**
     * Este metodo es automaticamente heredado de la clase Object, de la cual nacen todas las clases en Java,
     * y es llamado cuan aparece en un metodo 'print', o cualdo aparece en una concatenacion de String.
     *
     * Lo vamos a usar para mostrar de forma adecuada los atributos del objeto
     *
     * @return
     */
    @Override
    public String toString() {
        return "Vehiculo -> Color= " + getColor() + ", Precio= " + getPrecio() + ", Kilometros= " + getKmRecorridos();
    }

    /**
     * Metodo para mostrar los atributos del objeto, vamos aprovechar el metodo 'toString'
     *
     */
    public String mostrarAtributos() {
        return toString(); // retornamos la representacion del objeto, en sus atributos
    }

    /**
     * Crear un método calculo kilometraje que en función al número de kilómetros recorrido aplique lo siguiente:
     * .- Si los kilómetros recorridos en menor o igual a 2000000 se incrementa el 1% del precio del vehículo.
     * .- Si la cantidad de kilómetros va entre 2000001 y 9000000 debe incrementar el 4 del costo del vehículo.
     * .- Si es mayor a 9000001 debe aplicarle el 1% del costo del vehículo.
     */
    public int AjustarMostrarPrecio() {

        if (ajuste == 0) {
            if (getKmRecorridos() < 2000000) {
                ajuste = 1; // agregamos el 1%            
            } else if (getKmRecorridos() < 9000000) {
                ajuste = 4; // agregamos el 4%
            } else {
                ajuste = -1; // descontamos el 1%
            }

            precio = (int) Math.round(getPrecio() * (100 + ajuste) / 100.00);
        } else {
            // No hacemos algo, ya que como el ajuste ya se establecio, no deberiamos reajustar el precio
        }

        System.out.println("El precio del vehiculo es: " + precio);

        return precio;
    }

    /**
     */
    public String getColor() {
        return color;
    }

    /**
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     */
    public int getPrecio() {
        return precio;
    }

    /**
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     */
    public int getKmRecorridos() {
        return kmRecorridos;
    }

    /**
     */
    public void setKmRecorridos(int kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public void MetodoX() {
        System.out.println("MetodoX Vehiculo");
    }

}
