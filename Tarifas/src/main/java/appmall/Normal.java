package appmall;

/**
 * Write a description of class Normal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Normal extends Estacionamiento {

    // Atributos
    private int horaEntrada;   // Hora en la que entra el vehículo
    private int minutoEntrada; // Minuto en que entra el vehículo
    private int horaSalida;    // Hora en la que sale el vehículo
    private int minutoSalida;  // Minuto en que sale el vehículo
    private int tarifaMin;     // Tarifa por minuto

    // Contructor por defecto
    public Normal() {
        // ejecutar al contructor con parametros propio, lo inicializamos con valores ilogicos para
        // forzar un error, en caso de no establecer correctamente el atributo, y asi capturar un bug
        this(-1, -1, -1, -1, -1, null, null);
    }

    // Constructor con parametros parciales, correspondiente a los atributos de la clase base
    public Normal(String sigla, String ubicacion) {
        // ejecutar al contructor con parametros propio, lo inicializamos con valores ilogicos para
        // forzar un error, en caso de no establecer correctamente el atributo, y asi capturar un bug
        this(-1, -1, -1, -1, -1, sigla, ubicacion);
    }

    // Constructor con todos los parametros
    public Normal(int horaEntrada, int minutoEntrada, int horaSalida, int minutoSalida, int tarifaMin, String sigla, String ubicacion) {
        super(sigla, ubicacion);
        this.horaEntrada = horaEntrada;
        this.minutoEntrada = minutoEntrada;
        this.horaSalida = horaSalida;
        this.minutoSalida = minutoSalida;
        this.tarifaMin = tarifaMin;
    }

    // Representa la tarifa del estacionamiento a pagar el cual
    // consta de la cantidad de minutos en que estuvo el vehículo estacionado
    // multiplicado por la tarifa.
    public double valorAPagar() {
        int minEntrada = horaEntrada * 60 + minutoEntrada;
        int minSalida = horaSalida * 60 + minutoSalida;

        return (minSalida - minEntrada) * tarifaMin;
    }

    // Despliega una cadena de texto indicando los datos del
    // objeto y el valor a pagar de acuerdo con los datos ingresados.
    public String mostrarPago() {
        return "Normal : " + getSigla() + " - " + getUbicacion() + " $ " + valorAPagar();
    }

    // Define valores para los atributos de hora de inicio y minuto de inicio del servicio.
    public void registrarEntrada(int pHora, int pMinuto) {
        if ((pHora >= 0) && (pHora <= 23) && (pMinuto >= 0) && (pMinuto <= 59)) {
            this.horaEntrada = pHora;
            this.minutoEntrada = pMinuto;
        }
    }

    // Define valores para los atributos de hora de inicio y minuto de finalización del servicio.
    public void registrarSalida(int pHora, int pMinuto) {
        // La hora de salida, debe ser obligatoriamente mayor a la hora de entrada
        if ((pHora >= 0) && (pHora <= 23) && (pMinuto >= 0) && (pMinuto <= 59) && (pHora >= this.horaEntrada)) {
            this.horaSalida = pHora;
            this.minutoSalida = pMinuto;
        }
    }

    // Define la tarifa por minuto asociada al servicio.
    public void definirTarifaMinuto(int tarifa) {
        // Aqui pudieramos poner un tope maximo, para minimizar algun error, por error de "dedo"
        if (tarifa >= 0) {
            this.tarifaMin = tarifa;
        }
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int pHoraEntrada) {
        if ((pHoraEntrada >= 0) && (pHoraEntrada <= 23)) {
            this.horaEntrada = pHoraEntrada;
        }
    }

    public int getMinutoEntrada() {
        return minutoEntrada;
    }

    public void setMinutoEntrada(int pMinutoEntrada) {
        if ((pMinutoEntrada >= 0) && (pMinutoEntrada <= 59)) {
            this.minutoEntrada = pMinutoEntrada;
        }
    }

    public int getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(int pHoraSalida) {
        if ((pHoraSalida >= 0) && (pHoraSalida <= 23)) {
            this.horaSalida = pHoraSalida;
        }
    }

    public int getMinutoSalida() {
        return minutoSalida;
    }

    public void setMinutoSalida(int pMinutoSalida) {
        if ((pMinutoSalida >= 0) && (pMinutoSalida <= 59)) {
            this.minutoSalida = pMinutoSalida;
        }
    }

    public int getTarifaMin() {
        return tarifaMin;
    }

    public void setTarifaMin(int pTarifaMin) {
        if (pTarifaMin > 0) {
            this.tarifaMin = pTarifaMin;
        }
    }

}
