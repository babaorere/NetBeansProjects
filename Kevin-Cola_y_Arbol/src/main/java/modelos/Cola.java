package modelos;


/**
 *
 * @author manager
 */
public class Cola<T> implements ICola<T> {

    private ColaNode inicio;
    private ColaNode fin;
    private int tam;


    public Cola() {
        inicio = null;
        fin = null;
        tam = 0;
    }


    public int getTam() {
        return tam;
    }


    @Override
    public boolean vacia() {
        return (inicio == null);
    }


    @Override
    public void insertar(T elem) {
        if (vacia()) {
            insertarInicio(elem);
            return;
        }

        fin.setSig(new ColaNode(fin, elem, null));
        fin = fin.getSig();

        tam++;
    }


    private void insertarInicio(T elem) {
        inicio = new ColaNode(null, elem, inicio);

        if (tam <= 0) {
            fin = inicio;
        } else {
            inicio.getSig().setAnt(inicio);
        }

        tam++;
    }


    @Override
    public T eliminar() {
        if (vacia()) {
            return null;
        }

        T r = (T) inicio.getDato();

        inicio = inicio.getSig();
        if (inicio == null) {
            fin = null;
        } else {
            inicio.setAnt(null);
        }

        tam--;

        return r;

    }


    @Override
    public T getInicio() {
        if (vacia()) {
            return null;
        }

        return (T) (inicio.getDato());
    }


    @Override
    public T getFinal() {
        if (vacia()) {
            return null;
        }

        return (T) (fin.getDato());
    }

}
