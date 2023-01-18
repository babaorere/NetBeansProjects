package examen2.arbol;

import examen2.automovil.Automovil;

/**
 * Crea un arbol binario, ordenado por año
 */
public class ArbolBinarioImpl {

    private class Nodo {

        Automovil value;
        Nodo left;
        Nodo right;

        Nodo(Automovil value) {
            this.value = value;
            right = null;
            left = null;
        }
    }
    Nodo root;

    public void add(Automovil value) {
        root = addHelper(root, value);
    }

    private Nodo addHelper(Nodo current, Automovil value) {

        if (current == null) {
            return new Nodo(value);
        }

        if (value.getAnio() < current.value.getAnio()) {
            current.left = addHelper(current.left, value);
        } else if (value.getAnio() > current.value.getAnio()) {
            current.right = addHelper(current.right, value);
        }

        return current;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void preOrden() {
        preOrdenHelper(root);
    }

    private void preOrdenHelper(Nodo node) {
        if (node != null) {
            System.out.println(node.value);
            preOrdenHelper(node.left);
            preOrdenHelper(node.right);
        }
    }

}
