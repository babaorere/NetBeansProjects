package modelos;

/**
 *
 * @author manager
 */
public interface ICola<T> {

    public boolean vacia();

    public void insertar(T elem);

    public T eliminar();

    public T getInicio();

    public T getFinal();

}
