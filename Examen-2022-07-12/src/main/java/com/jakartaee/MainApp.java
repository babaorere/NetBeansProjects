package com.jakartaee;

/**
 *
 * @author manager
 */
public class MainApp {

    // utilizada para definir el estilo de un vehiculo
    public static class Estilo {

        private String desc;

        // default constructor
        public Estilo() {
            this(null);
        }

        // contructor con parametros
        public Estilo(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        /**
         * @return the desc
         */
        public String getDesc() {
            return desc;
        }

        /**
         * @param desc the desc to set
         */
        public void setDesc(String desc) {
            this.desc = desc;
        }

    }

    public static class Fabricante {

    }

    public static class Vehiculo {

        private String modelo;
        private int anio;
        private int kilometraje;

        private Fabricante fabricante;
        private Estilo estilo;

        // default constructor
        public Vehiculo() {
            this(null, 0, 0, null, null);
        }

        // constructor con parametros
        public Vehiculo(String modelo, int anio, int kilometraje, Fabricante fabricante, Estilo estilo) {
            this.modelo = modelo;
            this.anio = anio;
            this.kilometraje = kilometraje;
            this.fabricante = fabricante;
            this.estilo = estilo;
        }

        @Override
        public String toString() {
            return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
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

        /**
         * @return the anio
         */
        public int getAnio() {
            return anio;
        }

        /**
         * @param anio the anio to set
         */
        public void setAnio(int anio) {
            this.anio = anio;
        }

        /**
         * @return the kilometraje
         */
        public int getKilometraje() {
            return kilometraje;
        }

        /**
         * @param kilometraje the kilometraje to set
         */
        public void setKilometraje(int kilometraje) {
            this.kilometraje = kilometraje;
        }

        /**
         * @return the fabricante
         */
        public Fabricante getFabricante() {
            return fabricante;
        }

        /**
         * @param fabricante the fabricante to set
         */
        public void setFabricante(Fabricante fabricante) {
            this.fabricante = fabricante;
        }

        /**
         * @return the estilo
         */
        public Estilo getEstilo() {
            return estilo;
        }

        /**
         * @param estilo the estilo to set
         */
        public void setEstilo(Estilo estilo) {
            this.estilo = estilo;
        }

    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
