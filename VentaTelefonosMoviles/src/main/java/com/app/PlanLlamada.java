package com.app;

/**
 *
 */
public enum PlanLlamada {

    PREPAGO(0.05d), POSTPAGO(0.02d);

    private final double tarifa;

    private PlanLlamada(double tarifa) {
        this.tarifa = tarifa;
    }

    /**
     */
    public double getTarifa() {
        return tarifa;
    }

}
