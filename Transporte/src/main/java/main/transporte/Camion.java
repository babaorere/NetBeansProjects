package main.transporte;

/**
 *
 */
public class Camion extends Vehiculo {

    private String modelo;

    public Camion() {
        super();
        this.modelo = null;
    }

    public Camion(String inModelo, String inColor, int inPrecio, int inKmRecorridos) {
        super(inColor, inPrecio, inKmRecorridos);
        this.modelo = inModelo;
    }

    @Override
    public String mostrar() {
        return "Camion -> Modelo= " + modelo + " " + super.mostrar();
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

}
