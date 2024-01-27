package com.app;

/**
 *
 * @author manager
 */
public enum PlanTextoTipo {

    PREMIUM(0.45d), SILVER(0.30d), GOLD(0.25d);

    private final double tarifa;

    private PlanTextoTipo(double tarifa) {
        this.tarifa = tarifa;
    }

    /**
     */
    public double getTarifa() {
        return tarifa;
    }

}
