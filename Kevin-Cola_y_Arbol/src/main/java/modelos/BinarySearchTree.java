package modelos;

import java.util.ArrayList;


/**
 *
 * @author manager
 *
 * Implementacion de un arbol Binario de Busqueda, no acepta repetidos, es similar al TreeSet,
 *
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable> {

    private TreeNode root;
    private int size;
    private int height;


    public BinarySearchTree() {
        root = null;
        size = 0;
    }


    public void add(T dato) {
        Insert(new TreeNode(null, dato, null), root);
    }


    // No se aceptan valores repetidos
    private void Insert(TreeNode nuevo, TreeNode pivote) {
        if (this.root == null) {
            root = nuevo;
            size++;
        } else {
            int rcmp = nuevo.getDato().compareTo(pivote.getDato());
            if (rcmp < 0) {
                if (pivote.getLeft() == null) {
                    pivote.setLeft(nuevo);
                    size++;
                } else {
                    Insert(nuevo, pivote.getLeft());
                }
            } else if (rcmp > 0) {
                if (pivote.getRight() == null) {
                    pivote.setRight(nuevo);
                    size++;
                } else {
                    Insert(nuevo, pivote.getRight());
                }
            } else { // es igual
                // Verificamos si la parte Real e Imaginaria son diferentes, se puede añadir

            }
        }

    }


    public TreeNode getRoot() {
        return root;
    }


    public void setRoot(TreeNode inRoot) {
        this.root = inRoot;
    }


    public ArrayList<T> preOrden() {
        ArrayList<T> list = new ArrayList<>();
        PreOrden(root, list);
        return list;
    }


    public void PreOrden(TreeNode aux, ArrayList<T> inPath) {
        if (aux != null) {
            inPath.add((T) aux.getDato());
            PreOrden(aux.getLeft(), inPath);
            PreOrden(aux.getRight(), inPath);
        }
    }


    public ArrayList inOrden() {
        ArrayList<T> list = new ArrayList<>();
        inorden(root, list);
        return list;
    }


    public void inorden(TreeNode aux, ArrayList<T> path) {
        if (aux != null) {
            inorden(aux.getLeft(), path);
            path.add((T) aux.getDato());
            inorden(aux.getRight(), path);
        }
    }


    public ArrayList postOrden() {
        ArrayList<T> list = new ArrayList<>();
        postorden(root, list);
        return list;
    }


    public void postorden(TreeNode aux, ArrayList<T> path) {
        if (aux != null) {
            postorden(aux.getLeft(), path);
            postorden(aux.getRight(), path);
            path.add((T) aux.getDato());
        }
    }


    public boolean Contains(T dato) {
        TreeNode aux = root;
        while (aux != null) {
            int rcmp = dato.compareTo(aux.getDato());
            if (rcmp == 0) {
                return true;
            } else if (rcmp > 0) {
                aux = aux.getRight();
            } else {
                aux = aux.getLeft();
            }
        }

        return false;
    }


    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }


    /**
     * @return the height
     */
    public int getHeight() {
        CalHeight(root, 1);
        return height;
    }


    private void CalHeight(TreeNode aux, int inLevel) {
        if (aux != null) {
            CalHeight(aux.getLeft(), inLevel + 1);
            height = inLevel;
            CalHeight(aux.getRight(), inLevel + 1);
        }
    }


    public void borrar(T dato) {
        root = borrar(root, dato);

    }


    private TreeNode<T> borrar(TreeNode<T> r, T x) {
        if (r == null) {
            return null;
        }

        int compara = r.getDato().compareTo(x);

        if (compara > 0) {
            r.setLeft((borrar(r.getLeft(), x)));
        } else if (compara < 0) {
            r.setRight((borrar(r.getRight(), x)));
        } else {
            if (r.getLeft() != null && r.getRight() != null) {
                TreeNode cambiar = buscarMin(r.getRight());
                T aux = (T) cambiar.getDato();
                cambiar.setDato(r.getDato());

                r.setDato(aux);

                r.setRight((borrar(r.getRight(), x)));
            } else {
                r = (r.getLeft() != null) ? r.getLeft() : r.getRight();
            }
        }
        return r;
    }


    private TreeNode buscarMin(TreeNode r) {
        while (r.getLeft() != null) {
            r = r.getRight();
        }

        return r;
    }

}
