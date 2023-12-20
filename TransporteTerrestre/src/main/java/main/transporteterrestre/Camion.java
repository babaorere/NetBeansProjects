package main.transporteterrestre;

/**
 *
 * Crear una clase llamada Camión que herede los atributos de la clase vehículo,
 * además contenga olos atributos:
 * .- modelo
 * .- número de placa.
 * Debe tener un método para mostrar datos de la clase Camión agregando los parámetros modelo y número de placa en la clase principal.
 * Debe tener constructor, método get y set
 */
public class Camion extends Vehiculo {

    // Estos atributos se declaran privados, para que NO puedan ser accedidos por otras classes,
    // solamente podran ser accedidos dentro de la clase Camion, esto es el concepto de encapsulamiento de la POO, 
    // y se realiza para evitar el acceso directo de otras clases, y es una medida para contrarrestar problemas
    // derivados de una manipulacion externa invalida
    private String modelo;
    private String numPlaca;

    /**
     * Constructor por defecto, sin parametros. Estos valores son utilizados para inicializar los valores de los atributos
     * al momento de la creacion del objeto con el operador 'new', en este caso primero se llama al constructor de
     * la clase Vehiculo usando 'super()'
     */
    public Camion() {
        super(); // Aqui llamamo al constructor de la clase Vehiculo 

        // Inicializamos los atributos propios, ya que los atributos heredados fueron inicializados
        // al llamar al contructor de Vehiculo, con 'super()'
        this.modelo = null; // sin valor
        this.numPlaca = null; // sin valor

    }

    /**
     * Contructor con parametros. Estos parametros son utilizados para inicializar los valores de los atributos
     * al momento de la creacion del objeto con el operador 'new'. Hacemos notar que los atributos que
     * corresponden a la clase Vehiculo, son inicializados llamando al constructor 'super(color, precio, kmRecorridos)'
     *
     */
    public Camion(String modelo, String numPlaca, String color, int precio, int kmRecorridos) {
        super(color, precio, kmRecorridos);
        this.modelo = modelo;
        this.numPlaca = numPlaca;
    }

    /**
     * Sobreescribimos la clase heredada 'toString', para incluir los nuevos atributos,
     * sin embargo vamos a reutilizar la clase toString heredada, para reducir codigo
     * Al hacer 'super.toString()' llamamos al toString de Vehiculo, y se muestran los atributos
     * refernciados solo por la clase Vehiculo
     *
     * @return
     */
    @Override
    public String toString() {
        return "Camion -> Modelo= " + modelo + ", Placa= " + numPlaca + "\t" + super.toString();
    }

    /**
     */
    public String getModelo() {
        return modelo;
    }

    /**
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     */
    public String getNumPlaca() {
        return numPlaca;
    }

    /**
     */
    public void setNumPlaca(String numPlaca) {
        this.numPlaca = numPlaca;
    }

}
