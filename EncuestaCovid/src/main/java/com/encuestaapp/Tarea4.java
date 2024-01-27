package com.encuestaapp;

import java.util.ArrayList;
import java.util.Scanner;

public class Tarea4 {

    /**
     * Definimos una interface para aprovechar la utilizacion del Polimorfismo
     */
    public static interface Alumno {

        @Override
        public String toString();

        public String getNombre();

        public void setNombre(String nombre);

        public String getMatricula();

        public void setMatricula(String matricula);

        public String getSemestre();

        public void setSemestre(String semestre);

    }

    /**
     * La implementacion final del "Alumno", pudieramos definir otros tipos de "Alumnos", que implementen la interface "Alumno",
     */
    public static class AlumnoImp implements Alumno {

        private String nombre;
        private String matricula;
        private String semestre;

        /**
         * default contructor
         */
        public AlumnoImp() {
            this(null, null, null);
        }

        /**
         * Constructor con parametros
         *
         * @param nombre
         * @param matricula
         * @param semestre
         */
        public AlumnoImp(String nombre, String matricula, String semestre) {
            this.nombre = nombre;
            this.matricula = matricula;
            this.semestre = semestre;
        }

        @Override
        public String toString() {
            return "Nom: " + nombre + ", Mat: " + matricula + ", Sem: " + semestre;
        }

        /**
         * @return the nombre
         */
        @Override
        public String getNombre() {
            return nombre;
        }

        /**
         * @param nombre the nombre to set
         */
        @Override
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        /**
         * @return the matricula
         */
        @Override
        public String getMatricula() {
            return matricula;
        }

        /**
         * @param matricula the matricula to set
         */
        @Override
        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        /**
         * @return the semestre
         */
        @Override
        public String getSemestre() {
            return semestre;
        }

        /**
         * @param semestre the semestre to set
         */
        @Override
        public void setSemestre(String semestre) {
            this.semestre = semestre;
        }

    }

    /**
     * Definimos una interface para aprovechar la utilizacion del Polimorfismo
     */
    public static interface Encuesta {

    }

    /**
     * La implementacion final de la encuesta, <br>
     * pudieramos definir otros tipos de escuestas que implementen la interface Encuesta <br>
     */
    public static class EncuestaImp implements Encuesta {

        private String salon;
        private Alumno alumno;
        private String covid_SN;

        /**
         * Default constructor
         *
         */
        public EncuestaImp() {
            this(null, null, null);
        }

        /**
         * Constructor con parametros
         *
         * @param salon
         * @param alumno
         * @param covid_SN
         */
        public EncuestaImp(String salon, Alumno alumno, String covid_SN) {
            this.salon = salon;
            this.alumno = alumno;
            this.covid_SN = covid_SN;
        }

        /**
         * @return the salon
         */
        public String getSalon() {
            return salon;
        }

        /**
         * @param salon the salon to set
         */
        public void setSalon(String salon) {
            this.salon = salon;
        }

        /**
         * @return the alumno
         */
        public Alumno getAlumno() {
            return alumno;
        }

        /**
         * @param alumno the alumno to set
         */
        public void setAlumno(Alumno alumno) {
            this.alumno = alumno;
        }

        /**
         * @return the covid_SN
         */
        public String getCovid_SN() {
            return covid_SN;
        }

        /**
         * @param covid_SN the covid_SN to set
         */
        public void setCovid_SN(String covid_SN) {
            this.covid_SN = covid_SN;
        }

    }

    static ArrayList<Encuesta> listEncuesta = new ArrayList<>();

    public static void main(String[] args) {

        boolean salirSalon;

        do {
            // se prefiere reinicializar el scanner cada vez
            Scanner sc = new Scanner(System.in);

            System.out.println("\n\nPROGRMA DE CONTROL DE COVID");

            System.out.println("\nSalon nª");
            String salon = sc.nextLine().trim().toUpperCase();

            boolean salirAlumno;

            do {
                System.out.println("\nINGRESE NOMBRE DEL ALUMNO: ? ");
                String nombre = sc.nextLine().trim().toUpperCase();

                System.out.println("\nINGRESE LA MATRICULA: ? ");
                String matricula = sc.nextLine().trim().toUpperCase();

                System.out.println("\nINGRESE EL SEMESTRE EN EL QUE CURSA: ? ");
                String semestre = sc.nextLine().trim().toUpperCase();

                System.out.println("¿Usted a tenido covid 19 durante este ultimo periodo? S/N");
                String convid_SN = sc.nextLine().trim().toUpperCase();

                // Crear el Alumno
                Alumno alumno = new AlumnoImp(nombre, matricula, semestre);

                // Crear la Encuesta
                Encuesta encuesta = new EncuestaImp(salon, alumno, convid_SN);

                // Agregar la encuesta a la lista
                listEncuesta.add(encuesta);

                System.out.println("\nDesea Introducir otro Alumno: S/N ? ");
                String sn = sc.nextLine().trim().toUpperCase();

                // la respuesta es "S", salir se hace true, y en el while se vuelve false, terminando el ciclo
                salirAlumno = !sn.equals("S");

            } while (!salirAlumno);

            System.out.println("\nDesea Introducir otro Salon: S/N ? ");
            String sn = sc.nextLine().trim().toUpperCase();

            // la respuesta es "S", salir se hace true, y en el while se vuelve false, terminando el ciclo
            salirSalon = !sn.equals("S");

        } while (!salirSalon);

    }
}
