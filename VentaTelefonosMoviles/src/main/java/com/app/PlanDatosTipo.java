package com.app;

/**
 *
 * @author manager
 */
public enum PlanDatosTipo {

    PREMIUM(2, 0.35d), SILVER(4, 0.65d), GOLD(8, 0.75d);

    private final double gigas;
    private final double tarifa;

    private PlanDatosTipo(int inGigas, double inTarifa) {
        this.gigas = inGigas;
        this.tarifa = inTarifa;
    }

    /**
     */
    public double getTarifa() {
        return tarifa;
    }

    /**
     * @return the gigas
     */
    public double getGigas() {
        return gigas;
    }

}
