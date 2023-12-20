package com.app.relacionesnumeros;

import java.util.*;

public class RelacionesNumeros {

    // Estructura de datos personalizada para representar una relación con costo
    private static class RelacionConCosto {

        private int numeroAsociado;
        private double costo;

        public RelacionConCosto(int numeroAsociado, double costo) {
            this.numeroAsociado = numeroAsociado;
            this.costo = costo;
        }

        public int getNumeroAsociado() {
            return numeroAsociado;
        }

        public double getCosto() {
            return costo;
        }
    }

    // Mapa para el primer número y sus segundos números asociados con costo
    private Map<Integer, List<RelacionConCosto>> primerANumeros = new HashMap<>();

    // Mapa para el segundo número y sus primeros números asociados con costo
    private Map<Integer, List<RelacionConCosto>> segundoANumeros = new HashMap<>();

    // Método para agregar una relación con costo entre un primer y un segundo número
    public void agregarRelacionConCosto(int primer, int segundo, double costo) {
        // Agregar al mapa primerANumeros
        primerANumeros.computeIfAbsent(primer, key -> new ArrayList<>())
                .add(new RelacionConCosto(segundo, costo));

        // Agregar al mapa segundoANumeros
        segundoANumeros.computeIfAbsent(segundo, key -> new ArrayList<>())
                .add(new RelacionConCosto(primer, costo));
    }

    // Método para obtener las relaciones con costo (segundos números y costos) asociados a un primer número
    public List<RelacionConCosto> obtenerRelacionesConCosto(int primer) {
        return primerANumeros.getOrDefault(primer, Collections.emptyList());
    }

    // Método para obtener las relaciones con costo (primeros números y costos) asociados a un segundo número
    public List<RelacionConCosto> obtenerRelacionesConCosto2(int segundo) {
        return segundoANumeros.getOrDefault(segundo, Collections.emptyList());
    }

    public static void main(String[] args) {
        RelacionesNumeros relaciones = new RelacionesNumeros();

        // Agregar relaciones con costo
        relaciones.agregarRelacionConCosto(1, 10, 5.0);
        relaciones.agregarRelacionConCosto(1, 20, 3.0);
        relaciones.agregarRelacionConCosto(2, 10, 2.0);

        // Consultar las relaciones con costo
        List<RelacionConCosto> relacionesConCosto = relaciones.obtenerRelacionesConCosto(1);
        for (RelacionConCosto relacion : relacionesConCosto) {
            System.out.println("Número asociado: " + relacion.getNumeroAsociado());
            System.out.println("Costo: " + relacion.getCosto());
        }
    }
}
