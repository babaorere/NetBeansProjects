package com.app;

/**
 *
 */
public enum Recarga {

    A(5), B(25), C(100);

    private double monto;

    private Recarga(double inMonto) {
        this.monto = inMonto;
    }

    /**
     * @return the monto
     */
    public double getMonto() {
        return monto;
    }

}
