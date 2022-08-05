package com.manager.andrespalacios;

import java.util.ArrayList;

/**
 * a) Se debe de construir la clase padre persona con los atributos de: Nombre, Apellido, Fecha de Nacimiento, genero, estatura, peso.
 *
 * b) A partir de la clase creada aplicar el concepto de herencia donde se cree un nuevo objeto llamado Profesor, incluir los atributos y niveles de accesibilidad para trabajar el concepto de herencia.
 *
 * c) Al crear los objetos tanto padre como hijo incluís las sentencias this y super en los Métodos constructores.
 *
 * d) A partir del objeto profesor crear dentro de la clase un TDA un arreglo que permita almacenar 50 registros.
 *
 * @author manager
 */
public class AppMain {

    /**
     * definicion de una interface, para ser utilizada/implementada por la clase Persona
     */
    public interface iPersona {

        public String getNombre();

        public void setNombre(String nombre);

        public String getApellido();

        public void setApellido(String apellido);

        public String getFechaNacimiento();

        public void setFechaNacimiento(String fechaNacimiento);

        public String getGenero();

        public void setGenero(String genero);

        public float getEstatura();

        public void setEstatura(float estatura);

        public float getPeso();

        public void setPeso(float peso);

        @Override
        public String toString();

    }

    /**
     * Definicion de la clase Persona
     */
    public class Persona implements iPersona {

        private String nombre;
        private String apellido;
        private String fechaNacimiento;
        private String genero;
        private float estatura;
        private float peso;

        /**
         * Constructor con parametros, para inicializar los atributos
         *
         * @param Nombre
         * @param apellido
         * @param fechaNacimiento
         * @param genero
         * @param estatura
         * @param peso
         */
        public Persona(String Nombre, String apellido, String fechaNacimiento, String genero, float estatura, float peso) {
            this.nombre = Nombre;
            this.apellido = apellido;
            this.fechaNacimiento = fechaNacimiento;
            this.genero = genero;
            this.estatura = estatura;
            this.peso = peso;
        }

        /**
         * @return the Nombre
         */
        @Override
        public String getNombre() {
            return nombre;
        }

        /**
         * @param nombre the Nombre to set
         */
        @Override
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        /**
         * @return the apellido
         */
        @Override
        public String getApellido() {
            return apellido;
        }

        /**
         * @param apellido the apellido to set
         */
        @Override
        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        /**
         * @return the fechaNacimiento
         */
        @Override
        public String getFechaNacimiento() {
            return fechaNacimiento;
        }

        /**
         * @param fechaNacimiento the fechaNacimiento to set
         */
        @Override
        public void setFechaNacimiento(String fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        /**
         * @return the genero
         */
        @Override
        public String getGenero() {
            return genero;
        }

        /**
         * @param genero the genero to set
         */
        @Override
        public void setGenero(String genero) {
            this.genero = genero;
        }

        /**
         * @return the estatura
         */
        @Override
        public float getEstatura() {
            return estatura;
        }

        /**
         * @param estatura the estatura to set
         */
        @Override
        public void setEstatura(float estatura) {
            this.estatura = estatura;
        }

        /**
         * @return the peso
         */
        @Override
        public float getPeso() {
            return peso;
        }

        /**
         * @param peso the peso to set
         */
        @Override
        public void setPeso(float peso) {
            this.peso = peso;
        }

        /**
         * All utilizar cualquier print, se activa este metodo
         *
         * @return
         */
        @Override
        public String toString() {
            return "Yo Soy Persona: " + getNombre() + " " + getApellido();
        }

    }

    interface iProfesor {

        public String getEmail();

        public void setEmail(String email);

        public String getTelefono();

        public void setTelefono(String telefono);

        public String getTitulo();

        public void setTitulo(String titulo);

        public String getGradaduadoEn();

        public void setGradaduadoEn(String gradaduadoEn);
    }

    /**
     * Aqui se aplica la herencia, de Persona hacia el Profesor, la clase padre es: Persona, la clase hija es: Profesor
     *
     */
    public class Profesor extends Persona implements iProfesor {

        /**
         * Atributos propios de profesor
         */
        private String email;
        private String telefono;
        private String titulo;
        private String gradaduadoEn;

        /**
         * Constructor propios de la clase, que recibe parametros y a su vez los pasa al contrustor de la clase padre Persona, llamando a su constructor con la la sentencia "super"
         *
         * @param nombre
         * @param apellido
         * @param fechaNacimiento
         * @param genero
         * @param estatura
         * @param peso
         * @param email
         * @param telefono
         * @param titulo
         * @param graduadoEn
         */
        public Profesor(String nombre, String apellido, String fechaNacimiento, String genero, float estatura, float peso,
                String email, String telefono, String titulo, String graduadoEn) {
            super(nombre, apellido, fechaNacimiento, genero, estatura, peso);

            this.email = email;
            this.telefono = telefono;
            this.titulo = titulo;
            this.gradaduadoEn = graduadoEn;
        }

        /**
         * @return the email
         */
        @Override
        public String getEmail() {
            return email;
        }

        /**
         * @param email the email to set
         */
        @Override
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return the telefono
         */
        @Override
        public String getTelefono() {
            return telefono;
        }

        /**
         * @param telefono the telefono to set
         */
        @Override
        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        /**
         * @return the titulo
         */
        @Override
        public String getTitulo() {
            return titulo;
        }

        /**
         * @param titulo the titulo to set
         */
        @Override
        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        /**
         * @return the gradaduadoEn
         */
        @Override
        public String getGradaduadoEn() {
            return gradaduadoEn;
        }

        /**
         * @param gradaduadoEn the gradaduadoEn to set
         */
        @Override
        public void setGradaduadoEn(String gradaduadoEn) {
            this.gradaduadoEn = gradaduadoEn;
        }

        /**
         * Metodo que es activado en cualquier print
         *
         * @return
         */
        @Override
        public String toString() {
            return "Yo Soy Profesor: " + getNombre() + " " + getApellido() + ". Titulado en: " + getTitulo();
        }

    }

    /**
     * Interface, para acceder a los metodos que seran implementados
     */
    private interface TDA {

        public Profesor addProf(Profesor prof);

        public Profesor ProxProf();

        public Profesor delProf(Profesor prof);

        public Profesor delProf(int idx);
    }

    /**
     * Implementacion de una TDA
     *
     */
    private class TDA_Prof implements TDA {

        private final ArrayList<Profesor> list;

        /**
         * Maxima cantidad de profesores
         */
        final int maxCantProf;

        public TDA_Prof(int maxProf) {
            list = new ArrayList<>();

            // Validaciones
            if (maxProf > 1000) { // maxima cantidad de profesores que acepta el TDA
                this.maxCantProf = maxProf;

            } else if (maxProf <= 0) {
                this.maxCantProf = 1;
            } else {
                this.maxCantProf = maxProf;
            }
        }

        /**
         * Si hay capacidad, se procede a agregar el Profesor al final de la lista
         *
         * @param prof
         * @return
         */
        @Override
        public Profesor addProf(Profesor prof) {
            if (list.size() < maxCantProf) {
                list.add(prof);
                return prof;
            }
            return null; // no fue posible añadir el profesor, se supera la capidad
        }

        /**
         * Si hay Profesores en la lista, saca el siguiente profesor disponible es la lista, es decir el primero de la lista
         *
         * @param prof
         * @return
         */
        @Override
        public Profesor ProxProf() {
            if (list.size() <= 0) {
                return null; // no hay profesor disponible
            }

            return list.remove(0); // retorna el primer profesor de la lista
        }

        /**
         * Elimina un profesor de la lista, por comparacion
         *
         * @param idx
         * @return
         */
        @Override
        public Profesor delProf(Profesor prof) {

            // Buscar el Profesor
            for (int i = 0; i < list.size(); i++) {
                iPersona ip = list.get(i);
                if (ip.getNombre().equals(prof.getNombre()) && ip.getApellido().equals(prof.getApellido())) {
                    return list.remove(i);
                }
            }

            // no encontrado
            return null;
        }

        /**
         * Elimina un profesor de la lista, por indice/posicion
         *
         * @param idx
         * @return
         */
        @Override
        public Profesor delProf(int idx) {
            if (idx >= 0 && idx < list.size()) {
                return list.remove(idx);
            }

            return null;
        }

    }

    public void Test() {

        Persona pers = new Persona("Andres", "Palacios", "01-01-200", "Masculino", 1.70f, 80.00f);

        System.out.println("\nLa Persona es: " + pers);

        // Aqui se prueba la herencia
        Profesor prof1 = new Profesor("Manuel", "Estuardo", "01-02-1930", "Masculino", 1.65f, 65.00f,
                "manuel.estuardo@gmail.com", "56123456789", "PSicopedagogo", "Universidad");

        System.out.println("\nEl Profesor es: " + prof1);
        System.out.println("Graduado en: " + prof1.getGradaduadoEn());

        // Aqui se pueba el Polimorfirmo
        Profesor prof2 = new Profesor("Ines", "Narea", "01-02-1990", "Femenino", 1.67f, 55.00f,
                "manuel.estuardo@gmail.com", "56123456789", "Geriatria", "Universidad");

        iPersona pm = prof2;
        System.out.println("\nNombre es: " + pm.getNombre());
        System.out.println("Apellido es: " + pm.getApellido());

        // Prueba del TDA, con un maximo de 50 profesores
        TDA_Prof tda = new TDA_Prof(50);
        tda.addProf(prof1);
        tda.addProf(prof2);
        tda.addProf(new Profesor("Jose", "Gomez", "01-01-1980", "Masculino", 160f, 70.00f, "profe@gmail.com",
                "56321654987", "Fisica", "Universidad"));

        iProfesor ip = tda.ProxProf();
        if (ip != null) {
            System.out.println("\nEl Proximo Profesor es: " + ip);
        } else {
            System.out.println("\nNo hay Profesor disponible");
        }

        ip = tda.delProf(prof2);
        if (ip != null) {
            System.out.println("Eliminado de la lista, el Profesor es: " + ip);
        } else {
            System.out.println("Eliminacion abortada. No fue encontrado el Profesor es: " + prof2);
        }

    }

    /**
     * Punto de entrada de la aplicacion
     *
     * @param args
     */
    public static void main(String[] args) {

        // Ejecutar las pruebasPruebas
        (new AppMain()).Test();

    }

}
