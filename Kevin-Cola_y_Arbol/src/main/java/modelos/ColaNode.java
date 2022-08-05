package modelos;

/**
 *
 * @author manager
 * @param <T>
 */
public class ColaNode<T> {

    private final T dato;
    private ColaNode ant;
    private ColaNode sig;

    public ColaNode(ColaNode inAnt, T inDato, ColaNode inSig) {
        this.ant = inAnt;
        this.dato = inDato;
        this.sig = inSig;
    }

    /**
     * @return the dato
     */
    public T getDato() {
        return dato;
    }

    /**
     * @return the ant
     */
    public ColaNode getAnt() {
        return ant;
    }

    /**
     * @param ant the ant to set
     */
    public void setAnt(ColaNode ant) {
        this.ant = ant;
    }

    /**
     * @return the sig
     */
    public ColaNode getSig() {
        return sig;
    }

    /**
     * @param sig the sig to set
     */
    public void setSig(ColaNode sig) {
        this.sig = sig;
    }

}
