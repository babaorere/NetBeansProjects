package control_empleados;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/*
47) Una empresa de comida desea llevar el control y registro de sus empleados. Debe 
  implementar un sistema que permita agregar, modificar y eliminar empleados y para 
  cada uno de ellos debe almacenar los siguientes campos:

    a. Nombre
    b. DPI
    e. Salario base por Hora

  Adicional, se requiere realizar el cálculo de los salarios a pagar por empleado por mes. 
  Para ello debe solicitar la cantidad de horas trabajadas por empleado, y el salario se 
  calcula en base a las siguientes reglas:

           1. De O a 40 horas trabajadas, Salario  <= (horas trabajadas) X (salario 
            Base)
           2. Para cada hora arriba de 40, aplica un salarlo extra calculado de la 
            siguiente manera: (salario base) X 1.5 X (horas extra trabajadas). 
            Estas horas extra se deben sumar a las horas "estándar" descritas 
            en el inciso anterior.
           3. El salado base por hora no puede ser menos de $8.
           4. Un trabajador no puede reportar mas de 60 horas laboradas a la 
            semana.

  Nota: Utilice la estructura Linked List para el almacenamiento de los objetos empleado.
 */
/**
 *
 * @author manager
 */
public class Propuesta_47_ControlEmpleados {

    private static int GetTotalSalarios(LinkedLisk _list) {

        return _list.GetTotalSalario();
    }

    /**
     * Clase para guardar los datos del empleado
     */
    public final static class TEmpleado {

        private String nombre;
        private String dpi;
        private int salario_x_hora;
        private int horas_trabajadas;

        public TEmpleado(String _nombre, String _dpi, int _salario_x_hora, int _horas_trabajadas) {
            setNombre(_nombre);
            setDpi(_dpi);
            setSalario_x_hora(_salario_x_hora);
            setHoras_trabajadas(_horas_trabajadas);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 17 * hash + Objects.hashCode(this.getDpi());
            hash = 17 * hash + Objects.hashCode(this.nombre);
            return hash;
        }

        /**
         * Compara dos objetos
         *
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (!Objects.equals(getClass(), obj.getClass())) {
                return false;
            }

            final TEmpleado other = (TEmpleado) obj;

            if (!Objects.equals(this.dpi, other.dpi)) {
                return false;
            }

            return Objects.equals(this.nombre, other.nombre);
        }

        @Override
        public String toString() {
            return nombre + " : " + getDpi() + " :SxH= " + salario_x_hora + " :HT= " + horas_trabajadas + " : Mes Salario= " + getSalario();
        }

        /**
         * @return the nombre
         */
        public String getNombre() {
            return nombre;
        }

        /**
         * @param nombre the nombre to set
         */
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        /**
         * @return the dpi
         */
        public String getDPI() {
            return getDpi();
        }

        /**
         * @param dpi the dpi to set
         */
        public void setDpi(String dpi) {
            this.dpi = dpi;
        }

        /**
         * @return the salario_x_hora
         */
        public int getSalario_x_hora() {
            return salario_x_hora;
        }

        /**
         * @param _salario_x_hora
         */
        public final void setSalario_x_hora(int _salario_x_hora) {
            if (salario_x_hora < 8) {
                this.salario_x_hora = 8;
            } else {
                this.salario_x_hora = _salario_x_hora;
            }
        }

        /**
         * @return the dpi
         */
        public String getDpi() {
            return dpi;
        }

        /**
         * @return the horas_trabajadas
         */
        public int getHoras_trabajadas() {
            return horas_trabajadas;
        }

        /**
         * @param _horas_trabajadas
         */
        public final void setHoras_trabajadas(int _horas_trabajadas) {

            // Validaciones
            if (_horas_trabajadas > 60) {
                this.horas_trabajadas = 60;
            } else {
                this.horas_trabajadas = _horas_trabajadas;
            }
        }

        /**
         *
         * 1. De O a 40 horas trabajadas, Salario <= (horas trabajadas) X (salario Base) <br> 2. Para cada hora arriba de 40, aplica un salarlo extra calculado de la siguiente manera: (salario base) X 1.5 X (horas extra trabajadas). <br>
         * Estas horas extra se deben sumar a las horas "estándar" descritas en el inciso anterior. 3. El salado base por hora no puede ser menos de $8. <br>
         * 4. Un trabajador no puede reportar mas de 60 horas laboradas a la semana. <br>
         *
         * @return
         */
        public int getSalario() {
            int salario;

            if ((horas_trabajadas > 0) || (horas_trabajadas <= 40)) {
                salario = horas_trabajadas * salario_x_hora;
            } else {
                salario = 40 * salario_x_hora;
                salario += (horas_trabajadas - 40) * 1.5 * salario_x_hora;
            }

            return salario * 4; // salario mensual
        }

    }

    // *********************************************************************************************
    /**
     *
     */
    public static class TNodo {

        TEmpleado dato;
        TNodo ant;
        TNodo sig;

        public TNodo(TNodo _ant, TEmpleado _dato, TNodo _sig) {
            this.ant = _ant;
            this.dato = _dato;
            this.sig = _sig;
        }

    }

    // *********************************************************************************************
    public static class LinkedLisk {

        private TNodo inicio;
        private TNodo fin;
        private int tam;

        public LinkedLisk() {
            inicio = null;
            fin = null;
            tam = 0;
        }

        /**
         *
         * @return
         */
        public int GetTotalSalario() {

            int total = 0;
            TNodo aux = inicio;
            while (aux != null) {
                total += aux.dato.getSalario();
                aux = aux.sig;
            }

            return total;
        }

        public boolean vacia() {
            return (inicio == null);
        }

        public int getTam() {
            return tam;
        }

        /**
         *
         * @return
         */
        public String recorrerSig() {
            StringBuilder sb = new StringBuilder();

            final String strSeparador = " -> ";
            TNodo aux = inicio;
            while (aux != null) {
                sb.append(aux.toString()).append(strSeparador);
                aux = aux.sig;
            }

            sb.delete(sb.lastIndexOf(strSeparador), sb.length());

            return sb.toString();
        }

        /**
         *
         * @return
         */
        public String recorrerAnt() {
            StringBuilder sb = new StringBuilder();

            final String strSeparador = " -> ";
            TNodo aux = fin;
            while (aux != null) {
                sb.append(aux.toString()).append(strSeparador);
                aux = aux.ant;
            }

            sb.delete(sb.lastIndexOf(strSeparador), sb.length());

            return sb.toString();
        }

        /**
         *
         * @param elemento
         */
        public void insertarInicio(TEmpleado elemento) {
            inicio = new TNodo(null, elemento, inicio);

            if (tam <= 0) {
                fin = inicio;
            } else {
                inicio.sig.ant = inicio;
            }

            tam++;
        }

        /**
         *
         * @param elemento
         * @return
         */
        public boolean insertarFinal(TEmpleado elemento) {
            if (vacia()) {
                insertarInicio(elemento);
                return true;
            }

            fin.sig = new TNodo(fin, elemento, null);
            fin = fin.sig;

            tam++;

            return true;
        }

        /**
         *
         * @return
         */
        public TEmpleado eliminarInicio() {
            if (vacia()) {
                return null;
            }

            TEmpleado r = inicio.dato;

            inicio = inicio.sig;
            if (inicio == null) {
                fin = null;
            } else {
                inicio.ant = null;
            }

            tam--;

            return r;
        }

        /**
         *
         * @return
         */
        public TEmpleado eliminarFinal() {
            if (vacia()) {
                return null;
            }

            TEmpleado r = fin.dato;

            fin = fin.ant;
            if (fin == null) {
                inicio = null;
            } else {
                fin.sig = null;
            }

            tam--;

            return r;
        }

        /**
         *
         * @param elemento
         * @return
         */
        public boolean Eliminar(TEmpleado elemento) {
            TNodo aux = buscarNodo(elemento);

            if (aux == null) {
                return false;
            }

            if (aux.ant == null) {
                eliminarInicio();
                return true;
            }

            if (aux.sig == null) {
                eliminarFinal();
                return true;
            }

            aux.sig.ant = aux.ant;
            aux.ant.sig = aux.sig;
            tam--;

            return true;
        }

        /**
         *
         * @param elemento
         * @return
         */
        public boolean buscar(TEmpleado elemento) {
            if (vacia()) {
                return false;
            }

            TNodo aux = inicio;
            while (aux != null) {
                if (aux.dato.equals(elemento)) {
                    return true;
                }

                aux = aux.sig;
            }

            return false;
        }

        /**
         * Retorna el Nodo buscado y su anterior, null si NO lo encuentra
         *
         * @param elemento
         * @return
         */
        private TNodo buscarNodo(TEmpleado elemento) {
            if (vacia()) {
                return null;
            }

            TNodo aux = inicio;
            while (aux != null) {
                if (aux.dato.equals(elemento)) {
                    return aux;
                }
                aux = aux.sig;
            }

            return null;
        }

        /**
         * Retorna el Nodo buscado y su anterior, null si NO lo encuentra
         *
         * @param elemento
         * @return
         */
        private TEmpleado buscarEmpleado(int idx) {
            if (vacia()) {
                return null;
            }

            TNodo aux = inicio;
            int i = 0;

            while (aux != null) {
                if (i == idx) {
                    return aux.dato;
                }

                aux = aux.sig;
                i++;
            }

            return null;
        }

        /**
         * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
         *
         * @return the datos
         */
        public ArrayList<String> getStrDatosSig() {

            if (vacia()) {
                return null;
            }

            ArrayList<String> aux = new ArrayList<>(tam);

            TNodo p = inicio;
            for (int i = 0; (i < tam) && (p != null); i++, p = p.sig) {
                aux.add(p.dato.toString());
            }

            return aux;
        }

        /**
         * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
         *
         * @return the datos
         */
        public ArrayList<String> getStrDatosAnt() {

            if (vacia()) {
                return null;
            }

            ArrayList<String> aux = new ArrayList<>(tam);

            TNodo p = fin;
            for (int i = 0; (i < tam) && (p != null); i++, p = p.ant) {
                aux.add(p.dato.toString());
            }

            return aux;
        }

    }
    // Final de la LinkedList
    // **********************************************************************************************

    // Usando Scanner para obtener información del usuario, es creada static por eficiencia
    private static final Scanner scanner = new Scanner(System.in);

    /**
     *
     * @param _list
     */
    private static void Salir(LinkedLisk _list) {
    }

    // **********************************************************************************************
    // **********************************************************************************************
    /**
     * Presentacion del Menu Principal, retorta la Opcion elegida
     *
     * @param _list
     * @return
     */
    private static String Menu(LinkedLisk _list) {

        // Se queda en el ciclo hasta que se presiona una opcion valida
        String opcion = "";
        while (opcion.equals("")) {
            System.out.println("\n\n");
            System.out.println("*************************************************************************");
            System.out.println("*************************************************************************");
            System.out.println("**********   Menu Principal   **********");

            System.out.println("1.- Crear Empleado");
            System.out.println("2.- Modificar Empleado");
            System.out.println("3.- Eliminar Empleado");
            System.out.println("4.- Buscar Empleado");
            System.out.println("5.- Mostrar Empleados");
            System.out.println("6.- Mostrar Resumen Salarios");
            System.out.println("0.- Salir");
            System.out.println("\nOpcion= ? ");
            opcion = scanner.nextLine().trim().toUpperCase();

            // si lo leido tiene una longitud de 1 caracter, y esta contenido entonces
            if ((opcion.length() == 1) && ("0123456".contains(opcion))) {
                return opcion; // salir
            }

            // Inicializa para repetir el ciclo
            opcion = "";
        }

        return opcion;
    }

    private static void AgregarEmpleado(LinkedLisk _list) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Crear y Agregar Empleado   ***");
        System.out.println();

        if (_list == null) {
            System.out.println("\n***********************************************");
            System.out.println("Error. Lista no inicializada");
            System.out.println("\nPresione ENTER para continuar");
            scanner.nextLine();
            return;
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String ne;
        while (true) {
            System.out.println("Nombre del Empleado= ? ");
            ne = scanner.nextLine().trim().toUpperCase();
            if (ne.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String dpi;
        while (true) {
            System.out.println("DPI= ? ");
            dpi = scanner.nextLine().trim().toUpperCase();
            if (dpi.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int salario_x_hora;
        while (true) {
            System.out.println("Salario x Hora= ? ");
            salario_x_hora = scanner.nextInt();
            scanner.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if (salario_x_hora < 8) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int horas_trabajadas;
        while (true) {
            System.out.println("Horas Trabajadas= ? ");
            horas_trabajadas = scanner.nextInt();
            scanner.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if ((horas_trabajadas <= 0) || (horas_trabajadas > 60)) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Instaciar el Empleado
        TEmpleado emp = new TEmpleado(ne, dpi, salario_x_hora, horas_trabajadas);

        if (_list.buscar(emp)) {
            System.out.println("***   Valor Invalido   ");
            System.out.println("Ya existe un empleado registrado con el mismo nombre y dpi");
            return;
        }

        if (_list.insertarFinal(emp)) {
            System.out.println("***   Empleado agregado con Exito   ***");
            System.out.println(emp.toString());
        } else {
            System.out.println("***   No fue posible añadir el Empleado   ***");
        }

        System.out.println("\nPresione ENTER para continuar");
        scanner.nextLine();
    }

    private static void ModificarEmpleado(LinkedLisk _list) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Modificar/Sustituir Empleado   ***");
        System.out.println();

        // verificar que existe o no empleados registrados
        if ((_list == null) || _list.vacia()) {
            System.out.println("No Hay empleados Registrados");
            System.out.println("\nPresione ENTER para continuar");
            scanner.nextLine();
            return; // salir
        }

        // Mostrar todos lo empleados registrados
        ArrayList<String> aux = _list.getStrDatosSig();

        for (int i = 0; i < aux.size(); i++) {
            System.out.println("Idx= " + i + ", Empleado= " + aux.get(i));
        }

        System.out.println("\nSegun la informacion anterior, seleccione un indice");

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int idx;
        while (true) {
            System.out.println("Indice= ? ");
            idx = scanner.nextInt();
            scanner.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if ((idx < 0) || (idx >= _list.getTam())) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Recuperar una copia del empleado situado en el indice idx
        TEmpleado oldEmp = _list.buscarEmpleado(idx);

        if (oldEmp == null) {
            System.out.println("Error al buscar el empleado");
            System.out.println("\nPresione ENTER para continuar");
            scanner.nextLine();
            return; // salir            
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String ne;
        while (true) {
            System.out.println("\nAnterior Nombre del Empleado= " + oldEmp.getNombre());
            System.out.println("Nuevo Nombre del Empleado= ? ");
            ne = scanner.nextLine().trim().toUpperCase();
            if (ne.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String dpi;
        while (true) {
            System.out.println("Anterior DPI= " + oldEmp.getDPI());
            System.out.println("Nuevo DPI= ? ");
            dpi = scanner.nextLine().trim().toUpperCase();
            if (dpi.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int salario_x_hora;
        while (true) {
            System.out.println("Anterior Salario x Hora= " + oldEmp.getSalario_x_hora());
            System.out.println("Nuevo Salario x Hora= ? ");
            salario_x_hora = scanner.nextInt();
            scanner.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if (salario_x_hora <= 0) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int horas_trabajadas;
        while (true) {
            System.out.println("Anterior Hora Trabajadas= " + oldEmp.getHoras_trabajadas());
            System.out.println("Nueva Horas Trabajadas= ? ");
            horas_trabajadas = scanner.nextInt();
            scanner.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if ((horas_trabajadas <= 0) || (horas_trabajadas > 60)) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Instaciar el nuevo Empleado
        TEmpleado newEq = new TEmpleado(ne, dpi, salario_x_hora, horas_trabajadas);

        // Eliminar el viejo empleado y agregar el nuevo        
        if (_list.Eliminar(oldEmp) && _list.insertarFinal(newEq)) {
            System.out.println("Viejo Eq= " + oldEmp.toString());
            System.out.println("***   Empleado modificado con Exito   ***");
            System.out.println("Nuevo Eq= " + newEq.toString());
        } else {
            System.out.println("***   No fue posible agregar el Empleado   ***");
        }

        System.out.println("\nPresione ENTER para continuar");
        scanner.nextLine();

    }

    private static void EliminarEmpleado(LinkedLisk _list) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Eliminar Empleado   ***");
        System.out.println();

        // verificar que existe o no empleados registrados
        if ((_list == null) || (_list.getTam() <= 0)) {
            System.out.println("No Hay empleados Registrados");
            System.out.println("\nPresione ENTER para continuar");
            scanner.nextLine();
            return; // salir
        }

        // Mostrar todos lo empleados registrados
        ArrayList<String> aux = _list.getStrDatosSig();

        for (int i = 0; i < aux.size(); i++) {
            System.out.println("Idx= " + i + ", Empleado= " + aux.get(i));
        }

        System.out.println("\nSegun la informacion anterior, seleccione un indice");

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        int idx;
        while (true) {
            System.out.println("Indice= ? ");
            idx = scanner.nextInt();
            scanner.nextLine(); // no quitar, hay que leer el lo que queda en el buffer de teclado un \n
            if ((idx < 0) || (idx >= _list.getTam())) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        // Recuperar una copia del empleado situado en el indice idx
        TEmpleado oldEmp = _list.buscarEmpleado(idx);

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String respSN;
        while (true) {
            System.out.println("Nombre del Empleado= " + oldEmp.getNombre());
            System.out.println("Esta seguro de eliminar este Empleado [S/N]= ? ");
            respSN = scanner.nextLine().trim().toUpperCase();
            if (!respSN.equals("S")) {
                return; // salir/abortar
            } else {
                break; // salir del ciclo, continuar con la eliminacion
            }
        }

        if (_list.Eliminar(oldEmp)) {
            System.out.println("***   Empleado eliminado con Exito   ***");
        } else {
            System.out.println("***   No fue posible Eliminar el Empleado   ***");
        }

        System.out.println("\nPresione ENTER para continuar");
        scanner.nextLine();
    }

    /**
     * Buescar un empleado por su Nombre
     *
     * @param _list
     */
    private static void BuscarEmpleado(LinkedLisk _list) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Buscar Empleado   ***");
        System.out.println();

        // verificar que existe o no empleados registrados
        if ((_list == null) || (_list.getTam() <= 0)) {
            System.out.println("No Hay empleados Registrados");
            System.out.println("\nPresione ENTER para continuar");
            scanner.nextLine();
            return; // salir
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String ne;
        while (true) {
            System.out.println("Empleado ha buscar Nombre= ? ");
            ne = scanner.nextLine().trim().toUpperCase();
            if (ne.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break;
            }
        }

        // Leer dato y validar, sale del ciclo cuando el valor del dato sea correcto
        String dpi;
        while (true) {
            System.out.println("Empleado ha buscar DPI= ? ");
            dpi = scanner.nextLine().trim().toUpperCase();
            if (dpi.isEmpty()) {
                System.out.println("***   Valor Invalido   ");
            } else {
                break; // salir del ciclo
            }
        }

        TEmpleado emp = new TEmpleado(ne, dpi, 0, 0);

        TEmpleado aux = _list.buscarNodo(emp).dato;

        if (aux != null) {
            System.out.println("***   Empleado encontrado   ***");
            System.out.println("Nombre del Empleado= " + aux.getNombre());
            System.out.println("DPI= " + aux.getDPI());
            System.out.println("Salario x Horan= " + aux.getSalario_x_hora());
        } else {
            System.out.println("***   Empleado NO encontrado   ***");
        }

        System.out.println("\nPresione ENTER para continuar");
        scanner.nextLine();
    }

    private static void MostrarEmpleados(LinkedLisk _list) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Mostrar Empleados   ***");
        System.out.println();

        // verificar que existe o no empleados registrados
        if ((_list == null) || _list.vacia()) {
            System.out.println("No Hay empleados Registrados");
            System.out.println("\nPresione ENTER para continuar");
            scanner.nextLine();
            return; // salir
        }

        // Mostrar todos lo empleados registrados
        ArrayList<String> aux = _list.getStrDatosSig();

        for (int i = 0; i < aux.size(); i++) {
            System.out.println("Idx= " + i + ", Empleado= " + aux.get(i));
        }

        System.out.println("\nPresione ENTER para continuar");
        scanner.nextLine();
    }

    private static void MostrarResumen(LinkedLisk _list) {
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("***   Resumen Salarios x Mes   ***");
        System.out.println();

        // verificar que existe o no empleados registrados
        if ((_list == null) || _list.vacia()) {
            System.out.println("No Hay empleados Registrados");
            System.out.println("\nPresione ENTER para continuar");
            scanner.nextLine();
            return; // salir
        }

        // Mostrar todos lo empleados registrados
        ArrayList<String> aux = _list.getStrDatosSig();

        int suma = 0;
        for (int i = 0; i < aux.size(); i++) {
            System.out.println("Idx= " + i + ", Empleado= " + aux.get(i));
        }

        System.out.println("\n\nTotal Salarios pagados= " + GetTotalSalarios(_list));

        System.out.println("\nPresione ENTER para continuar");
        scanner.nextLine();
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
        System.out.println("***   Programa para la Gestion de Empleado   ***");

        LinkedLisk list = new LinkedLisk();

        // Mantener un ciclo sobre el Menu y sus opciones
        String opcion = "";
        while (!opcion.equals("0")) {

            opcion = Menu(list);

            switch (opcion) {

                case "0":
                    Salir(list);
                    break;

                case "1":
                    AgregarEmpleado(list);
                    break;

                case "2":
                    ModificarEmpleado(list);
                    break;

                case "3":
                    EliminarEmpleado(list);
                    break;

                case "4":
                    BuscarEmpleado(list);
                    break;

                case "5":
                    MostrarEmpleados(list);
                    break;

                case "6":
                    MostrarResumen(list);
                    break;

                default:
                    System.out.println("***   Opcion Invalida   ***");

            }

            // Ejecutar el Menu, y recibir la opcion seleccionada por el Usuario
        }
    }

}
