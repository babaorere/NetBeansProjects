package com.app;

public class TipoVehiculo_Camioneta extends TipoVehiculo {
    
    private int capCarga;
    private int cantEjes;
    private int cantRodadas;

    public TipoVehiculo_Camioneta() {
        super();
        this.capCarga = 0;
        this.cantEjes = 0;
        this.cantRodadas = 0;        
    }

    public TipoVehiculo_Camioneta(int inCapCarga, int inCantEjes, int inCantRodadas, String inDescripción) {
        super(inDescripción);
        this.capCarga = inCapCarga;
        this.cantEjes = inCantEjes;
        this.cantRodadas = inCantRodadas;
    }

    public int getCapCarga() {
        return capCarga;
    }

    public void setCapCarga(int capCarga) {
        this.capCarga = capCarga;
    }

    public int getCantEjes() {
        return cantEjes;
    }

    public void setCantEjes(int cantEjes) {
        this.cantEjes = cantEjes;
    }

    public int getCantRodadas() {
        return cantRodadas;
    }

    public void setCantRodadas(int cantRodadas) {
        this.cantRodadas = cantRodadas;
    }
    
    
}
