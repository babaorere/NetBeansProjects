package doblelink;

/**
 *
 * @author manager
 */
public interface ILista {

    /*vacía: Regresa true si la lista está vacía
    su no, regresa false*/
    public boolean vacia();

    /* Regresa el tamaño de la lista*/
    public int tamano();

    /*regresa un string con todos los elementos 
    de la lista, separados por comas, recorriendo hacia adelante  */
    public String recorrerSig();

    /*regresa un string con todos los elementos 
    de la lista, separados por comas, recorriendo hacia atras  */
    public String recorrerAnt();

    /*Inserta el elemento al inicio  de la lista*/
    public void insertarInicio(Fraccion elemento);

    /*Inserta el elemento al final de la lista*/
    public void insertarFinal(Fraccion elemento);

    /*Elimina el elemento que se encuentra al inicio de
    la lista y lo regresa, si está vacía regresa null*/
    public Fraccion eliminarInicio();

    /*Elimina el elemento que se encuentra al final de
    la lista y lo regresa, si está vacía regresa null*/
    public Fraccion eliminarFinal();

    /*Busca el ememento y si lo encuentra lo elimina y 
    regresa true, si no lo encuentra sólo regresa false*/
    public boolean eliminar(Fraccion elemento);

    /*Busca el ememento y si lo encuentra regresa true, si no false*/
    public boolean buscar(Fraccion elemento);
}
