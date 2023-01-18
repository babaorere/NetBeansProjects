package com.app;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Inventario {

    private final List<InventarioItem> list;

    public Inventario() {
        list = new ArrayList<>();
    }

    public InventarioItem add(InventarioItem item) {
        int idx = list.indexOf(item);

        if (idx < 0) { // el item no existe, no se agregan item repetidos
            list.add(item);
        }

        return item;
    }

    public int cantTotalTelefonos() {
        return list.size();
    }

    public int cantTotalPrePago() {
        int contador = 0;
        for (InventarioItem item : list) {
            if (item.getPlanLlamada() == PlanLlamada.PREPAGO) {
                contador++;
            }
        }
        return contador;
    }

    public int cantTotalPostPago() {
        int contador = 0;
        for (InventarioItem item : list) {
            if (item.getPlanLlamada() == PlanLlamada.POSTPAGO) {
                contador++;
            }
        }
        return contador;
    }

    public int cantTotalTextoPremium() {
        int contador = 0;
        for (InventarioItem item : list) {
            if (item.getPlanTexto() == PlanTextoTipo.PREMIUM) {
                contador++;
            }
        }
        return contador;
    }

    public int cantTotalTextoSilver() {
        int contador = 0;
        for (InventarioItem item : list) {
            if (item.getPlanTexto() == PlanTextoTipo.SILVER) {
                contador++;
            }
        }
        return contador;
    }

    public int cantTotalTextoGold() {
        int contador = 0;
        for (InventarioItem item : list) {
            if (item.getPlanTexto() == PlanTextoTipo.GOLD) {
                contador++;
            }
        }
        return contador;
    }

    public int cantTotalDatosPremium() {
        int contador = 0;
        for (InventarioItem item : list) {
            if (item.getPlanDatos() == PlanDatosTipo.PREMIUM) {
                contador++;
            }
        }
        return contador;
    }

    public int cantTotalDatosSilver() {
        int contador = 0;
        for (InventarioItem item : list) {
            if (item.getPlanDatos() == PlanDatosTipo.SILVER) {
                contador++;
            }
        }
        return contador;
    }

    public int cantTotalDatosGold() {
        int contador = 0;
        for (InventarioItem item : list) {
            if (item.getPlanDatos() == PlanDatosTipo.GOLD) {
                contador++;
            }
        }
        return contador;
    }

    public double consumoTotal() {
        // son generados datos aleatorios, para carcular el consumo
        double suma = 0;
        for (InventarioItem item : list) {
            long minLlamada = Math.round(Math.rint(100)); // numero de minutos aleatorio, maximo valor 99
            long cantTexto = Math.round(Math.rint(200)); // cantidad de mensajes de texto aleatorio, maximo valor 199
            long cantGigas = Math.round(Math.rint(100)); // numero de gigas aleatorio, maximo valor 99

            suma += item.consumoTotal(minLlamada, cantTexto, cantGigas);
        }

        return suma;
    }

    public double consumoPrePago() {
        // se utilizan los dato aleatorios generados
        double suma = 0;
        for (InventarioItem item : list) {
            if (item.getPlanLlamada() == PlanLlamada.PREPAGO) {
                suma += item.consumoTotal();
            }
        }

        return suma;
    }

    public double consumoPostPago() {
        // se utilizan los dato aleatorios generados
        double suma = 0;
        for (InventarioItem item : list) {
            if (item.getPlanLlamada() == PlanLlamada.POSTPAGO) {
                suma += item.consumoTotal();
            }
        }

        return suma;
    }

}
