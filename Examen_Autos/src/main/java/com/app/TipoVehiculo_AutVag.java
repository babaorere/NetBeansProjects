package com.app;


public class TipoVehiculo_AutVag extends TipoVehiculo {
    
    private int cantPas;
    
    // default constructor
    public TipoVehiculo_AutVag() {
        super();
        this.cantPas = 0;
    }

    public TipoVehiculo_AutVag(int inCantPas, String inDescripción) {
        super(inDescripción);
        this.cantPas = inCantPas;
    }

    public int getCantPas() {
        return cantPas;
    }

    public void setCantPas(int cantPas) {
        this.cantPas = cantPas;
    }
    
}
