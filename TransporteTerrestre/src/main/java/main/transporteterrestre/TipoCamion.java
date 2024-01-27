package main.transporteterrestre;

/**
 * Crear una clase Tipo de Camión que herede los atributos de la Clase Camión,
 * además debe tener el atributo tipo de camión (plataforma, cerrado, cava, entre otros),
 * Crear un método que muestre los datos de la clase camión mas tipo de camión,
 * utilice polimorfismo paramétrico.
 * Crear constructor, método get y set.
 *
 */
public class TipoCamion extends Camion {

    // Estos atributos se declaran privados, para que NO puedan ser accedidos por otras classes,
    // solamente podran ser accedidos dentro de la clase TipoCamion, esto es el concepto de encapsulamiento de la POO, 
    // y se realiza para evitar el acceso directo de otras clases, y es una medida para contrarrestar problemas
    // derivados de una manipulacion externa invalida    
    private String tipo;

    /**
     * Constructor por defecto, sin parametros. Estos valores son utilizados para inicializar los valores de los atributos
     * al momento de la creacion del objeto con el operador 'new', en este caso primero se llama al constructor de
     * la clase Vehiculo usando 'super()'
     */
    public TipoCamion() {
        super(); // Aqui llamamo al constructor de la clase Camion 

        // Inicializamos los atributos propios, ya que los atributos heredados fueron inicializados
        // al llamar al contructor de Camion, con 'super()', que a su vez llama a el constructor de la clase Vehiculo
        this.tipo = null; // sin valor

    }

    /**
     * Contructor con parametros. Estos parametros son utilizados para inicializar los valores de los atributos
     * al momento de la creacion del objeto con el operador 'new'. Hacemos notar que los atributos que
     * corresponden a la clase Camion, son inicializados llamando al constructor 'super(modelo, numPlaca, color, precio, kmRecorridos)'
     *
     */
    public TipoCamion(String tipo, String modelo, String numPlaca, String color, int precio, int kmRecorridos) {
        super(modelo, numPlaca, color, precio, kmRecorridos);
        this.tipo = tipo;
    }

    /**
     * Sobreescribimos la clase heredada 'toString', para incluir los nuevos atributos,
     * sin embargo vamos a reutilizar la clase toString heredada, para reducir codigo
     * Al hacer 'super.toString()' llamamos al toString de Camion, y se muestran los atributos
     * referenciados solo por la clase Camion, y que a su vez llama a toString de Vehiculo,
     * y ya, al final se muestran todos los atributos propios y heredados
     *
     * @return
     */
    @Override
    public String toString() {
        return "TipoCamion -> Tipo= " + tipo + "\t" + super.toString();
    }

    public void MetodoX() {
        System.out.println("MetodoX Tipo Camion");

    }

}
