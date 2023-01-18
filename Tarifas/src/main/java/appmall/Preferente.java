package appmall;

/**
 * Write a description of class Preferente here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Preferente extends Estacionamiento {

    // Representa el valor que paga por vez al ocupar el estacionamiento
    private int tarifaUnica;

    // Representa el tipo de descuento que tiene en el 
    // estacionamiento (funcionario 20% sobre la tarifaUnica, 
    // PrePago 10% sobre la tarificaUnica)
    private int tipoDescuento;

    // Constructor por defecto
    public Preferente() {
        // ejecutar al contructor con parametros propio, lo inicializamos con valores ilogicos para
        // forzar un error, en caso de no establecer correctamente el atributo, y asi capturar un bug
        this(-1, -1, null, null);
    }

    // Constructor con parametros parciales, correspondiente a los atributos de la clase base    
    public Preferente(String sigla, String ubicacion) {
        // ejecutar al contructor con parametros propio, lo inicializamos con valores ilogicos para
        // forzar un error, en caso de no establecer correctamente el atributo, y asi capturar un bug
        this(-1, -1, sigla, ubicacion);
    }

    // Constructor con todos los parametros
    public Preferente(int tarifaUnica, int tipoDescuento, String sigla, String ubicacion) {
        super(sigla, ubicacion);
        this.tarifaUnica = tarifaUnica;
        this.tipoDescuento = tipoDescuento;
    }

    // Representa la tarifa del estacionamiento a pagar, el cual consta de la 
    // tarifaUnica con su descuento aplicado.
    public double valorAPagar() {
        int descuento;

        // Solo se permiten dos valores, por eso no se vuelve preguntr en el else
        if (tipoDescuento == 1) {
            descuento = tarifaUnica * 20 / 100;
        } else {
            descuento = tarifaUnica * 10 / 100;
        }

        return tarifaUnica - descuento;
    }

    // Despliega una cadena de texto indicando los datos del objeto y el
    // valor a pagar de acuerdo con los datos ingresados.
    public String mostrarPago() {
        int descuento;
        // Solo se permiten dos valores, por eso no se vuelve preguntr en el else
        if (tipoDescuento == 1) {
            descuento = tarifaUnica * 20 / 100;
        } else {
            descuento = tarifaUnica * 10 / 100;
        }

        return "Preferente : " + getSigla() + " - " + getUbicacion() + " $ " + valorAPagar()
                + " Descuento: " + descuento;
    }

    // Permite asignar un valor a la tarifa única del objeto en cuestión.
    public void definirTarifaUnica(int pTarifa) {
        if (pTarifa >= 0) {
            this.tarifaUnica = pTarifa;
        }
    }

    // Asigna un tipo de descuento a la instancia correspondiente. 
    // Son solo dos valores posibles, 1 para funcionario y 2 para PrePago; 
    // cualquier otro valor ingresado impide modificar el atributo.
    // Funcionario 20% sobre la tarifaUnica, PrePago 10% sobre la tarificaUnica
    public void definirTipoDescuento(int pTipoDescuento) {
        if ((pTipoDescuento == 1) || (pTipoDescuento == 2)) {
            this.tipoDescuento = pTipoDescuento;
        }
    }

    public int getTarifaUnica() {
        return tarifaUnica;
    }

    public void setTarifaUnica(int pTarifaUnica) {
        definirTarifaUnica(pTarifaUnica);
    }

    public int getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(int pTipoDescuento) {
        definirTipoDescuento(pTipoDescuento);
    }

}
