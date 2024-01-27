package com.app;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 */
public class InventarioItem {

    // Para generar automaticamente el id del registro
    private static int contId;

    private final int id; // identificador interno del sistema
    private Telefono telefono;
    private final String sku; // ID del teléfono dentro del inventario
    private int precioVenta;
    private int costo;
    private String caracteristicas;
    private PlanLlamada planLlamada;
    private PlanTextoTipo planTexto;
    private PlanDatosTipo planDatos;

    private long minLlamada;
    private long cantTexto;
    private long cantGigas;
    private ArrayList<Recarga> listRecarga;

    static {
        contId = 0;
    }

    public InventarioItem() {
        this(null, 0, 0, null, null, null, null);
    }

    public InventarioItem(Telefono telefono, int precioVenta, int costo, String caracteristicas, PlanLlamada planLlamada, PlanTextoTipo planTexto, PlanDatosTipo planDatos) {
        this.id = ++contId;
        this.telefono = telefono;
        this.sku = String.valueOf(this.id); // el sku es el mismo id
        this.precioVenta = precioVenta;
        this.costo = costo;
        this.caracteristicas = caracteristicas;
        this.planLlamada = planLlamada;
        this.planTexto = planTexto;
        this.planDatos = planDatos;

        if (this.planLlamada == PlanLlamada.PREPAGO) {
            this.listRecarga = new ArrayList<>();
        } else {
            this.listRecarga = null;
        }

        minLlamada = 0;
        cantTexto = 0;
        cantGigas = 0;

    }

    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        }

        if ((other == null) || (getClass() != other.getClass())) {
            return false;
        }

        InventarioItem item = (InventarioItem) other;
        return (id == item.getId()) && (telefono.getId() == item.getTelefono().getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.telefono.getId());
        return hash;
    }

    public double consumoTotal(long inMinLlamada, long inCantText, long inCantGigas) {

        this.minLlamada = inMinLlamada;
        this.cantTexto = inCantText;
        this.cantGigas = inCantGigas;

        double consumoFinal;

        if (planLlamada == PlanLlamada.PREPAGO) {
            double sumaRecarga = 0;
            for (Recarga r : listRecarga) {
                sumaRecarga += r.getMonto();
            }

            double montoTeorico = minLlamada * planLlamada.getTarifa();

            double montoAux = sumaRecarga - montoTeorico;

            if (montoAux >= 0) {
                consumoFinal = montoAux;
            } else {
                consumoFinal = sumaRecarga;  // no se puede gastar mas de la suma recarga en prepago
            }

        } else {
            consumoFinal = minLlamada * PlanLlamada.POSTPAGO.getTarifa();
        }

        if (planTexto != null) {
            consumoFinal += planTexto.getTarifa() * cantTexto;
        }

        if (planDatos != null) {

            long entera = (long) (cantGigas / planDatos.getGigas());
            long fraccion = cantGigas % (long) (planDatos.getGigas());

            if (fraccion != 0) {
                entera++; // se cobran gigas enteros
            }

            consumoFinal += planDatos.getTarifa() * entera;
        }

        return consumoFinal;
    }

    public double consumoTotal() {

        double consumoFinal;

        if (planLlamada == PlanLlamada.PREPAGO) {
            double sumaRecarga = 0;
            for (Recarga r : listRecarga) {
                sumaRecarga += r.getMonto();
            }

            double montoTeorico = minLlamada * planLlamada.getTarifa();

            double montoAux = sumaRecarga - montoTeorico;

            if (montoAux >= 0) {
                consumoFinal = montoAux;
            } else {
                consumoFinal = sumaRecarga;  // no se puede gastar mas de la suma recarga en prepago
            }

        } else {
            consumoFinal = minLlamada * PlanLlamada.POSTPAGO.getTarifa();
        }

        if (planTexto != null) {
            consumoFinal += planTexto.getTarifa() * cantTexto;
        }

        if (planDatos != null) {

            long entera = (long) (cantGigas / planDatos.getGigas());
            long fraccion = cantGigas % (long) (planDatos.getGigas());

            if (fraccion != 0) {
                entera++; // se cobran gigas enteros
            }

            consumoFinal += planDatos.getTarifa() * entera;
        }

        return consumoFinal;
    }

    public double consumoLLamadas() {

        double consumoFinal;

        if (planLlamada == PlanLlamada.PREPAGO) {
            double sumaRecarga = 0;
            for (Recarga r : listRecarga) {
                sumaRecarga += r.getMonto();
            }

            double montoTeorico = minLlamada * planLlamada.getTarifa();

            if (sumaRecarga >= montoTeorico) {
                consumoFinal = montoTeorico;
            } else {
                consumoFinal = sumaRecarga;  // no se puede gastar mas de la suma recarga en prepago
            }

        } else {
            consumoFinal = minLlamada * PlanLlamada.POSTPAGO.getTarifa();
        }

        return consumoFinal;
    }

    public double addRecarga(Recarga inRecarga) {

        // solo se aceptan las recargas si el plan es prepago
        if (planLlamada == PlanLlamada.PREPAGO) {
            listRecarga.add(inRecarga);
            return 0; // indica que se consumio la recarga
        }

        return inRecarga.getMonto(); // indica recarga rechazada
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the telefono
     */
    public Telefono getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @return the precioVenta
     */
    public int getPrecioVenta() {
        return precioVenta;
    }

    /**
     * @param precioVenta the precioVenta to set
     */
    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    /**
     * @return the costo
     */
    public int getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /**
     * @return the caracteristicas
     */
    public String getCaracteristicas() {
        return caracteristicas;
    }

    /**
     * @param caracteristicas the caracteristicas to set
     */
    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    /**
     * @return the planLlamada
     */
    public PlanLlamada getPlanLlamada() {
        return planLlamada;
    }

    /**
     * @param planLlamada the planLlamada to set
     */
    public void setPlanLlamada(PlanLlamada planLlamada) {
        this.planLlamada = planLlamada;
    }

    /**
     * @return the planTexto
     */
    public PlanTextoTipo getPlanTexto() {
        return planTexto;
    }

    /**
     * @param planTexto the planTexto to set
     */
    public void setPlanTexto(PlanTextoTipo planTexto) {
        this.planTexto = planTexto;
    }

    /**
     * @return the planDatos
     */
    public PlanDatosTipo getPlanDatos() {
        return planDatos;
    }

    /**
     * @param planDatos the planDatos to set
     */
    public void setPlanDatos(PlanDatosTipo planDatos) {
        this.planDatos = planDatos;
    }

}
