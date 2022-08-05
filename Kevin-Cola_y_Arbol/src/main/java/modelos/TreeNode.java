package modelos;


/**
 *
 * @author manager
 * @param <T>
 */
public class TreeNode<T extends Comparable> {

    private T dato;
    private TreeNode left;
    private TreeNode right;


    public TreeNode(TreeNode inLeft, T dato, TreeNode inRight) {
        this.dato = dato;
        this.left = inLeft;
        this.right = inRight;
    }


    public T getDato() {
        return dato;
    }


    public void setDato(T dato) {
        this.dato = dato;
    }


    public TreeNode getLeft() {
        return left;
    }


    public void setLeft(TreeNode inLeft) {
        this.left = inLeft;
    }


    public TreeNode getRight() {
        return right;
    }


    public void setRight(TreeNode inRight) {
        this.right = inRight;
    }


    @Override
    public String toString() {
        return dato.toString();
    }

}
