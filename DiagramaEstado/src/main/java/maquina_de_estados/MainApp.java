package maquina_de_estados;

import java.util.ArrayList;

/**
 * Aqui tenemos el inicio de la aplicacion, donde tenemos un ciclo "while", <br>
 * comenzando por el estado inicial "1 Vacio" y esperando llegar al estado final "10 Final", <br>
 * y terminar el programa
 */
public class MainApp {

    private IEstado estadoActual;

    // Lista de productos del sistema
    private static ArrayList<Producto> listProd = new ArrayList<>();
    private static ArrayList<Producto> listCarro = new ArrayList<>();
    private static Producto prodSel = null; // este es el producto seleccionado

    public MainApp() {

        // Asi se inicia la maquina de estados        
        estadoActual = new Edo01_Vacio();
    }

    public static ArrayList<Producto> getListProd() {
        return listProd;
    }

    public static ArrayList<Producto> getListCarro() {
        return listCarro;
    }

    public static Producto getProdSel() {
        return prodSel;
    }

    public static void setProdSel(Producto prod) {
        prodSel = prod;
    }

    public static void main(String[] args) {
        System.out.println("\n\n\nBienvenido a la Maquina de Estado para Productos");

        MainApp main = new MainApp();

        // el diagrama comienza por el estado inicial "1 Vacio"
        IEstado edo = main.getEstadoActual();

        // el programa se ejecuta mientras no se ha llegado al estado final
        // en el estado final, se realiza la finalizacion del programa, mediante un comando "exit(0)"
        while (true) {

            // Realizar acciones pertinentes al siguiente estado, retorna un codigo de transicion            
            // Obtener el siguiente estado tomando en cuenta la transicion y el estado actual
            edo = edo.getProximoEstado(edo.mostrarEstado());
        }

    }

    /**
     * @return the estadoActual
     */
    public IEstado getEstadoActual() {
        return estadoActual;
    }
}
