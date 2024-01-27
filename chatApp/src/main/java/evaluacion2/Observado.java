package evaluacion2;

import java.util.ArrayList;

/**
 *
 */
public abstract class Observado {

    private ArrayList<IObservador> observadores = new ArrayList<IObservador>();

    public Observado() {
    }

    public void agregarObservador(IObservador objObservador) {
        observadores.add(objObservador);
    }

    /**
     * El objeto removido, ya no recibira mas notificaciones, por ejemplo: cuaando alguien salga del chat
     *
     * @param objObservador Objeto a ser removido
     */
    public void eliminarObservador(IObservador objObservador) {
        observadores.remove(objObservador);
    }

    /**
     * realizamos la notificacion a todos los observadores, envaiandoles tambien el nuevo valor
     *
     * @param valor
     */
    public void notificarObservadores(Object valor) {
        // Enviarles la notificación a cada observador a través de su propio método 
        // el ciclo <for> itera sobre todos los observadores en la lista
        for (IObservador obj : observadores) {
            obj.observadoActualizado(this, valor);
        }

    }

}
