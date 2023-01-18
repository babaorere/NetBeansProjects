package com.manager;

import java.util.Scanner;

/*

34) Diseñe un programa que permita administrar una liga de football (ahora en java :p ):

• Registro de equipos participantes: Para cada equipo debe almacenar la siguiente 
  información: Nombre, cantidad de jugadores (mínimo 15), capitán del equipo.

• Para cada equipo: Debe brindar la opción de buscar y editar equipos.

  Generar calendario de partidos: Tomando como base los equipos ingresados, debe 
  generar el calendario de los partidos de la liga (una vuelta). Por ejemplo, si los equipos 
  ingresados son: Barcelona, Bayern Munich, Inter y el Municipal, el calendario podría 
  quedar de la siguiente forma:
      Barcelona - Bayern
      inter - Municipal
      Barcelona - Inter
      Bayern - Municipal
      Barcelona - Municipal
      inter - bBayern

  Su sistema debe tener la capacidad de generar el calendario de partidos para una cantidad 
  de equipos par e impar.
 */
/**
 *
 * @author manager
 */
public class AppMain {

    // Aqui se mantiene la Liga
    LigaFooball liga;

    // Usando Scanner para obtener información del usuario, es creada static por eficiencia
    private static final Scanner readData = new Scanner(System.in);

    private static void Salir(LigaFooball _liga) {
    }

    /**
     * Clase interna, y estatica, no requiere instanciar algun objeto, como si fuera una libreria, Math por ejemplo
     *
     */
    public static final class Equipo {

        private String nombre;
        private int cantJug;
        private String capitan;

        /**
         * Contructor con parametros, notar que el nombre y el capitan se pasan a mayuscula
         *
         * @param _nombre
         * @param _cantJug
         * @param _capitan
         */
        public Equipo(String _nombre, int _cantJug, String _capitan) {
            setNombre(_nombre);
            setCantJug(_cantJug);
            setCapitan(_capitan);
        }

        /**
         * Para mostar la info del equipo en cualquier print
         *
         * @return
         */
        @Override
        public String toString() {
            return nombre + " : " + cantJug + " : " + capitan;
        }

        /**
         * @return the nombre
         */
        public String getNombre() {
            return nombre;
        }

        /**
         * @param _nombre the nombre to set
         */
        public final void setNombre(String _nombre) {
            if (_nombre != null) {
                this.nombre = _nombre.trim().toUpperCase();
            } else {
                this.nombre = "ERROR en Nombre";
            }
        }

        /**
         * @return the cantJug
         */
        public int getCantJug() {
            return cantJug;
        }

        /**
         * Valida y establece la cantidad de Jugadores del equipo
         *
         * @param _cantJug the cantJug to set
         */
        public void setCantJug(int _cantJug) {

            if (_cantJug <= 0) {
                this.cantJug = 15;
            } else {
                this.cantJug = _cantJug; // un valor por defecto
            }
        }

        /**
         * @return the capitan
         */
        public String getCapitan() {
            return capitan;
        }

        /**
         * @param _capitan
         */
        public final void setCapitan(String _capitan) {
            if (_capitan != null) {
                this.capitan = _capitan;
            } else {
                this.capitan = "ERROR en Capitan";
            }
        }

    }

    // **********************************************************************************************
    // **********************************************************************************************
    /**
     * Clase interna, y estatica, no requiere instanciar algu objeto, como si fuera una libreria, Math por ejemplo
     */
    public static class LigaFooball {

        // Guarada el nombre de la Liga
        private final String nombreLiga;

        // Vector para guardar los equipos
        Equipo[] vecEq;

        // entero que guarda la cantidad de equipos, hasta el maximo
        private int cantEq;

        /**
         * Constructor, para inicializar el nombre y tamaño maximo de la liga
         *
         * @param _nombreLiga
         * @param _cantMaxEq
         */
        public LigaFooball(String _nombreLiga, int _cantMaxEq) {

            if (_nombreLiga != null) {
                this.nombreLiga = _nombreLiga.trim().toUpperCase();
            } else {
                nombreLiga = "ERROR en el Nombre de la LIga";
            }

            // Crear el tamaño maximo de equipos para la Liga
            vecEq = new Equipo[_cantMaxEq];
            for (int i = 0; i < vecEq.length; i++) {
                vecEq[i] = null;
            }

            // Inicializar la cantidad de equipos registrados
            cantEq = 0;
        }

        /**
         * Busca el nombre de un equipo en el vector de equipos, y devuelve su posicion
         *
         * @param _nomEq
         * @return
         */
        public int Buscar(String _nomEq) {

            for (int idx = 0; idx < getCantEq(); idx++) {
                if (vecEq[idx].nombre.equalsIgnoreCase(_nomEq)) {
                    // se ha conseguiedo una coincidencia, por lo que se retorna el indice
                    return idx;
                }
            }

            // Equipo no encontrado
            return -1;
        }

        /**
         * De ser posible, se agrega el equipo a la Liga
         *
         * @param _eq
         * @return
         */
        public boolean AgregarEq(Equipo _eq) {

            // retorna false si el  Equipo es null o si el vector esta lleno, y no se puede agregar
            if ((_eq == null) || (getCantEq() >= vecEq.length)) {
                return false;
            }

            // Verificar que el equipo no alla sido agregado, no se permiten nombres duplicados
            if (Buscar(_eq.getNombre()) >= 0) {
                return false;
            }

            vecEq[getCantEq()] = _eq;

            // Incrementar la cantidad de equipos registrados
            cantEq++;

            // retorna true indicando que la operacion fue exitosa
            return true;
        }

        /**
         * Actualiza/Sustituye los valores de un equipo, segun la posicion idx
         *
         * @param _idx
         * @param _eq
         * @return
         */
        public boolean ActualizarEq(int _idx, Equipo _eq) {

            // validar
            if ((_idx < 0) || (_idx >= vecEq.length)) {
                return false;
            }

            // Aqui se hace la actualizacion/sustitucion
            vecEq[_idx] = _eq;

            // retorna true indicando que la operacion fue exitosa
            return true;
        }

        /**
         * @return the nombreLiga
         */
        public String getNombreLiga() {
            return nombreLiga;
        }

        /**
         * @return the cantEq
         */
        public int getCantEq() {
            return cantEq;
        }

        /**
         * @return the cantEq
         */
        public int getMaxtEq() {
            return vecEq.length;
        }

        /**
         * Retorna una copia del equipo, que se encuentra en la posicion idx
         *
         * @param idx
         * @return
         */
        private Equipo getEquipo(int idx) {

            if ((idx < 0) || (idx >= cantEq)) {
                return null;
            }

            return new Equipo(vecEq[idx].getNombre(), vecEq[idx].getCantJug(), vecEq[idx].getCapitan());
        }

        private boolean Eliminar(int idx) {

            if ((idx < 0) || (idx >= cantEq)) {
                return false;
            }

            // correr un espacio los elementos del vector, para eliminar la posicion idx
            for (int i = idx; i < (cantEq - 1); i++) {
                vecEq[i] = vecEq[i + 1];
            }

            // borrar la ultima posicion
            vecEq[cantEq] = null;

            // ajustar la cantidad de equipos
            cantEq--;

            return true;
        }

    }

    /**
     * Presentacion del Menu Principal, retorta la Opcion elegida
     *
     * @param _liga
     * @return
     */
    private static String Menu(LigaFooball _liga) {

        // Se queda en el ciclo hasta que se presiona una opcion valida
        String opcion = "";
        while (opcion.equals("")) {
            System.out.println("\n\n");
            System.out.println("*************************************************************************");
            System.out.println("*************************************************************************");
            System.out.println("**********   Menu Principal   **********");

            System.out.println("1.- Crear Equipo");
            System.out.println("2.- Modificar Equipo");
            System.out.println("3.- Eliminar Equipo");
            System.out.println("4.- Buscar Equipo");
            System.out.println("5.- Mostrar Equipos");
            System.out.println("6.- Generar Calendario");
            System.out.println("0.- Salir");
            System.out.println("\nOpcion= ? ");
            opcion = readData.nextLine().trim().toUpperCase();

            // si lo leido tiene una longitud de 1 caracter, y esta contenido entonces
            if ((opcion.length() == 1) && ("0123456".contains(opcion))) {
                return opcion; // salir
            }

            // Inicializa para repetir el ciclo
            opcion = "";
        }

        return opcion;
    }

    private static void AgregarEquipo(LigaFooball _liga) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Crear y Agregar Equipo   ***");
        System.out.println("            Nombre de la Liga  = " + _liga.getNombreLiga());
        System.out.println("    Cantidad Maxima de Equipos = " + _liga.getMaxtEq());
        System.out.println("Cantidad de Equipos registrados= " + _liga.getCantEq());
        System.out.println();

        if (_liga.getCantEq() >= _liga.getMaxtEq()) {
            System.out.println("\n***********************************************");
            System.out.println("Error. Liga llena, ya no se aceptan mas Equipos");
            System.out.println("\nPresione ENTER para continuar");
            readData.nextLine();
            return;
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String ne;
        while (true) {
            System.out.println("Nombre del Equipo= ? ");
            ne = readData.nextLine().trim().toUpperCase();
            if (ne.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        if (_liga.Buscar(ne) >= 0) {
            System.out.println("***   Valor Invalido   ");
            System.out.println("Ya existe un equipo registrado con el mismo nombre");
            return;
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int cj;
        while (true) {
            System.out.println("Cantidad jugadores (min.15)= ? ");
            cj = readData.nextInt();
            readData.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if (cj < 15) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String cap;
        while (true) {
            System.out.println("Capitan del Equipo= ? ");
            cap = readData.nextLine().trim().toUpperCase();
            if (cap.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Instaciar el Equipo
        Equipo eq = new Equipo(ne, cj, cap);

        if (_liga.AgregarEq(eq)) {
            System.out.println("***   Equipo agregado con Exito   ***");
            System.out.println(eq.toString());
        } else {
            System.out.println("***   No fue posible añadir el Equipo   ***");
        }

        System.out.println("\nPresione ENTER para continuar");
        readData.nextLine();
    }

    private static void ModificarEquipo(LigaFooball _liga) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Modifcar/Sustituir Equipo   ***");
        System.out.println("            Nombre de la Liga  = " + _liga.getNombreLiga());
        System.out.println("    Cantidad Maxima de Equipos = " + _liga.getMaxtEq());
        System.out.println("Cantidad de Equipos registrados= " + _liga.getCantEq());
        System.out.println();

        // verificar que existe o no equipos registrados
        if (_liga.getCantEq() <= 0) {
            System.out.println("No Hay equipos Registrados");
            System.out.println("\nPresione ENTER para continuar");
            readData.nextLine();
            return; // salir
        }

        // Mostrar todos lo equipos registrados
        for (int i = 0; i < _liga.getCantEq(); i++) {
            System.out.println("Idx= " + i + ", Equipo= " + _liga.getEquipo(i));

        }

        System.out.println("\nSegun la informacion anterior, seleccione un indice");

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int idx;
        while (true) {
            System.out.println("Indice= ? ");
            idx = readData.nextInt();
            readData.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if ((idx < 0) || (idx >= _liga.getCantEq())) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Recuperar una copia del equipo situado en el indice idx
        Equipo oldAux = _liga.getEquipo(idx);

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String ne;
        while (true) {
            System.out.println("\nAnterior Nombre del Equipo= " + oldAux.getNombre());
            System.out.println("Nuevo Nombre del Equipo= ? ");
            ne = readData.nextLine().trim().toUpperCase();
            if (ne.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int cj;
        while (true) {
            System.out.println("Anterior Cantidad de jugadores= " + oldAux.getCantJug());
            System.out.println("Nueva Cantidad de jugadores (min.15)= ? ");
            cj = readData.nextInt();
            readData.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if (cj < 15) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String cap;
        while (true) {
            System.out.println("Anterior Capitan= " + oldAux.getCapitan());
            System.out.println("Capitan del Equipo= ? ");
            cap = readData.nextLine().trim().toUpperCase();
            if (cap.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Instaciar el Equipo
        Equipo newEq = new Equipo(ne, cj, cap);

        if (_liga.ActualizarEq(idx, newEq)) {
            System.out.println("Viejo Eq= " + oldAux.toString());
            System.out.println("***   Equipo modificado con Exito   ***");
            System.out.println("Nuevo Eq= " + newEq.toString());
        } else {
            System.out.println("***   No fue posible agregar el Equipo   ***");
        }

        System.out.println("\nPresione ENTER para continuar");
        readData.nextLine();

    }

    private static void EliminarEquipo(LigaFooball _liga) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Eliminar Equipo   ***");
        System.out.println("            Nombre de la Liga  = " + _liga.getNombreLiga());
        System.out.println("    Cantidad Maxima de Equipos = " + _liga.getMaxtEq());
        System.out.println("Cantidad de Equipos registrados= " + _liga.getCantEq());
        System.out.println();

        // verificar que existe o no equipos registrados
        if (_liga.getCantEq() <= 0) {
            System.out.println("No Hay equipos Registrados");
            System.out.println("\nPresione ENTER para continuar");
            readData.nextLine();
            return; // salir
        }

        // Mostrar todos lo equipos registrados
        for (int i = 0; i < _liga.getCantEq(); i++) {
            System.out.println("Idx= " + i + ", Equipo= " + _liga.getEquipo(i));

        }

        System.out.println("\nSegun la informacion anterior, seleccione un indice");

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int idx;
        while (true) {
            System.out.println("Indice del Equipo ha eliminar= ? ");
            idx = readData.nextInt();
            readData.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if ((idx < 0) || (idx >= _liga.getCantEq())) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Recuperar una copia del equipo situado en el indice idx
        Equipo oldAux = _liga.getEquipo(idx);

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String respSN;
        while (true) {
            System.out.println("Nombre del Equipo= " + oldAux.getNombre());
            System.out.println("Esta seguro de eliminar este Equipo [S/N]= ? ");
            respSN = readData.nextLine().trim().toUpperCase();
            if (!respSN.equals("S")) {
                return; // salir/abortar
            } else {
                break; // salir del ciclo, continuar con la eliminacion
            }
        }

        if (_liga.Eliminar(idx)) {
            System.out.println("***   Equipo eliminado con Exito   ***");
        } else {
            System.out.println("***   No fue posible Eliminar el Equipo   ***");
        }

        System.out.println("\nPresione ENTER para continuar");
        readData.nextLine();
    }

    /**
     * Buescar un equipo por su Nombre
     *
     * @param _liga
     */
    private static void BuscarEquipo(LigaFooball _liga) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Buscar Equipo   ***");
        System.out.println("            Nombre de la Liga  = " + _liga.getNombreLiga());
        System.out.println("    Cantidad Maxima de Equipos = " + _liga.getMaxtEq());
        System.out.println("Cantidad de Equipos registrados= " + _liga.getCantEq());
        System.out.println();

        // verificar que existe o no equipos registrados
        if (_liga.getCantEq() <= 0) {
            System.out.println("No Hay equipos Registrados");

            System.out.println("\nPresione ENTER para continuar");
            readData.nextLine();

            return; // salir
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String nomEq;
        while (true) {
            System.out.println("Nombre del Equipo ha buscar= ? ");
            nomEq = readData.nextLine().trim().toUpperCase();
            if (nomEq.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        int pos = _liga.Buscar(nomEq);

        if (pos >= 0) {
            Equipo oldAux = _liga.getEquipo(pos);
            System.out.println("***   Equipo encontrado   ***");
            System.out.println("Nombre del Equipo= " + oldAux.getNombre());
            System.out.println("Cantidad de jugadores= " + oldAux.getCantJug());
            System.out.println("Capitan= " + oldAux.getCapitan());
        } else {
            System.out.println("***   Enquipo NO encontrado   ***");
        }

        System.out.println("\nPresione ENTER para continuar");
        readData.nextLine();
    }

    private static void MostrarEquipos(LigaFooball _liga) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Mostrar Equipos   ***");
        System.out.println("            Nombre de la Liga  = " + _liga.getNombreLiga());
        System.out.println("    Cantidad Maxima de Equipos = " + _liga.getMaxtEq());
        System.out.println("Cantidad de Equipos registrados= " + _liga.getCantEq());
        System.out.println();

        // verificar que existe o no equipos registrados
        if (_liga.getCantEq() <= 0) {
            System.out.println("No Hay equipos Registrados");

            System.out.println("\nPresione ENTER para continuar");
            readData.nextLine();

            return; // salir
        }

        // Mostrar todos lo equipos registrados
        for (int i = 0; i < _liga.getCantEq(); i++) {
            System.out.println("Idx= " + (i + 1) + ", Equipo= " + _liga.getEquipo(i));
        }

        System.out.println("\nPresione ENTER para continuar");
        readData.nextLine();
    }

    private static void GenerarCalendario(LigaFooball _liga) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Generar Calendario   ***");
        System.out.println("            Nombre de la Liga  = " + _liga.getNombreLiga());
        System.out.println("    Cantidad Maxima de Equipos = " + _liga.getMaxtEq());
        System.out.println("Cantidad de Equipos registrados= " + _liga.getCantEq());
        System.out.println();

        // verificar que existe o no equipos registrados
        if (_liga.getCantEq() <= 0) {
            System.out.println("No Hay equipos Registrados");

            System.out.println("\nPresione ENTER para continuar");
            readData.nextLine();

            return; // salir
        }

        int cantJuegos = (_liga.getCantEq() * _liga.getCantEq() - _liga.getCantEq()) / 2;

        // Aqui se guardan lo calendarios
        String[] calendario = new String[cantJuegos];

        int cont = 0;
        for (int i = 0; i < (_liga.getCantEq() - 1); i++) {
            for (int j = i + 1; j < _liga.getCantEq(); j++) {
                calendario[cont] = _liga.getEquipo(i).getNombre() + " Vs " + _liga.getEquipo(j).getNombre();
                cont++;
            }
        }

        System.out.println("\n***   Calendario   ***");
        for (int i = 0; i < cont; i++) {
            System.out.println(calendario[i]);
        }

        System.out.println("\nPresione ENTER para continuar");
        readData.nextLine();

    }

    // **********************************************************************************************
    // **********************************************************************************************
    /**
     * Punto de entrada del Programa
     *
     * @param args
     */
    public static void main(String[] args) {

        // Aqui se crea la Liga
        System.out.println("\n\n\n");
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Programa para la Gestion de Liga de Foolball   ***");
        System.out.println("Nombre de la Liga= ? ");
        String nl = readData.nextLine().trim().toUpperCase();

        System.out.println("Cantidad maxima de equipos= ? ");
        int cm = readData.nextInt();

        LigaFooball liga = new LigaFooball(nl, cm);

        // Mantener un cilo sobre el Menu y sus opciones
        String opcion = "";
        while (!opcion.equals("0")) {

            opcion = Menu(liga);

            switch (opcion) {

                case "0":
                    Salir(liga);
                    break;

                case "1":
                    AgregarEquipo(liga);
                    break;

                case "2":
                    ModificarEquipo(liga);
                    break;

                case "3":
                    EliminarEquipo(liga);
                    break;

                case "4":
                    BuscarEquipo(liga);
                    break;

                case "5":
                    MostrarEquipos(liga);
                    break;

                case "6":
                    GenerarCalendario(liga);
                    break;

                default:
                    System.out.println("***   Opcion Invalida   ***");

            }

            // Ejecutar el Menu, y recibir la opcion seleccionada por el Usuario
        }
    }

}
