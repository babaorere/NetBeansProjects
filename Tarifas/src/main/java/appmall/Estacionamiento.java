package appmall;

/**
 * Abstract class Estacionamiento - write a description of the class here
 *
 * @author: Date:
 */
public abstract class Estacionamiento {

    // Atributos
    private String sigla; // Corresponde al código que tiene cada estacionamiento
    private String ubicacion; // Posee las opciones de sector 1, sector 2 y sector 3

    //Representa el cálculo de la tarifa del estacionamiento que se debe pagar    
    public abstract double valorAPagar();

    // Despliega una cadena de texto indicando los datos del objeto,
    // y el valor a pagar de acuerdo con los datos ingresados.
    public abstract String mostrarPago();

    // contructor por defecto
    public Estacionamiento() {
        this(null, null); // Ejecutamos el constructor con parametros
    }

    // constructor con parametros
    public Estacionamiento(String sigla, String ubicacion) {
        this.sigla = sigla;
        this.ubicacion = ubicacion;
    }

    // Sobre-escribimos el metodo, para presentar los atributos adecuadamente
    public String toString() {
        return "{"
                + "    \"sigla\": \"" + sigla + "\"\n"
                + "    \"ubicacion\": \"" + ubicacion + "\"}";
    }

    public String getSigla() {
        return sigla;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setSigla(String sigla) {
        if ((sigla != null) && (sigla.length() > 0)) {
            this.sigla = sigla;
        }
    }

    public void setUbicacion(String ubicacion) {
        if ((ubicacion != null) && (ubicacion.length() > 0)) {
            this.ubicacion = ubicacion;
        }
    }

}
