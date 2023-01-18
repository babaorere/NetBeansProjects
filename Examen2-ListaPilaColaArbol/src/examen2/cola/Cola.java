package examen2.cola;

import examen2.automovil.Automovil;

/**
 * Interface para manejar una cola generica
 *
 * @author Felipe Belloy
 */
public interface Cola {

    /**
     * Retorna true si la cola esta vacia
     */
    public boolean isEmpty();

    /**
     * Retorna el tamaño de la cola
     */
    public int getSize();

    /**
     * Agrega un automavil, al final de la cola
     */
    public void push(Automovil item);

    /**
     * Elimina el Nodo inicio, retorna el Automovil, o null si la cola esta vacia
     */
    public Automovil pull();

    public String[] toArrStr();

}
