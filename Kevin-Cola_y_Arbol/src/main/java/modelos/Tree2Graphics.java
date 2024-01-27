package modelos;

/**
 *
 * @author manager
 */
import java.awt.*;
import java.util.*;
import javax.swing.*;


public final class Tree2Graphics<T> extends JPanel {

    private final BinarySearchTree miArbol;

    private HashMap posicionNodos = null;
    private HashMap subtreeSizes = null;
    private boolean dirty = true;
    private final int parent2child = 20;
    private final int child2child = 30;
    private final Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;


    public Tree2Graphics(BinarySearchTree miArbol) {
        super();
        this.miArbol = miArbol;
        setBackground(new java.awt.Color(204, 255, 204));

        posicionNodos = new HashMap();
        subtreeSizes = new HashMap();
        dirty = true;
    }


    private void calcularPosiciones() {
        posicionNodos.clear();
        subtreeSizes.clear();
        TreeNode root = this.miArbol.getRoot();
        if (root != null) {
            calcularTamañoSubarbol(root);
            CalPosition(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }


    private Dimension calcularTamañoSubarbol(TreeNode n) {
        if (n == null) {
            return new Dimension(0, 0);
        }

        Dimension ld = calcularTamañoSubarbol(n.getLeft());
        Dimension rd = calcularTamañoSubarbol(n.getRight());

        int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
        int w = ld.width + child2child + rd.width;

        Dimension d = new Dimension(w, h);
        subtreeSizes.put(n, d);

        return d;
    }


    private void CalPosition(TreeNode n, int left, int right, int top) {
        if (n == null) {
            return;
        }

        Dimension ld = (Dimension) subtreeSizes.get(n.getLeft());
        if (ld == null) {
            ld = empty;
        }

        Dimension rd = (Dimension) subtreeSizes.get(n.getRight());
        if (rd == null) {
            rd = empty;
        }

        int center = 0;

        if (right != Integer.MAX_VALUE) {
            center = right - rd.width - child2child / 2;
        } else if (left != Integer.MAX_VALUE) {
            center = left + ld.width + child2child / 2;
        }

        String strAux;
        T auxT = (T) n.getDato();

        if (auxT instanceof NumComplejo) {
            strAux = ((NumComplejo) auxT).strXY();
        } else {
            strAux = auxT.toString();
        }
        int width = fm.stringWidth(strAux);

        posicionNodos.put(n, new Rectangle(center - width / 2 - 3, top, width + 6, fm.getHeight()));

        CalPosition(n.getLeft(), Integer.MAX_VALUE, center - child2child / 2, top + fm.getHeight() + parent2child);
        CalPosition(n.getRight(), center + child2child / 2, Integer.MAX_VALUE, top + fm.getHeight() + parent2child);
    }


    private void DrawTree(Graphics2D g, TreeNode n, int puntox, int puntoy, int yoffs) {
        if (n == null) {
            return;
        }

        Rectangle r = (Rectangle) posicionNodos.get(n);
        g.draw(r);

        String strAux;
        T auxT = (T) n.getDato();

        if (auxT instanceof NumComplejo) {
            strAux = ((NumComplejo) auxT).strXY();
        } else {
            strAux = auxT.toString();
        }
        int width = fm.stringWidth(strAux);

        g.drawString(strAux, r.x + 3, r.y + yoffs);

        if (puntox != Integer.MAX_VALUE) {
            g.drawLine(puntox, puntoy, (int) (r.x + r.width / 2), r.y);
        }

        DrawTree(g, n.getLeft(), (int) (r.x + r.width / 2), r.y + r.height, yoffs);
        DrawTree(g, n.getRight(), (int) (r.x + r.width / 2), r.y + r.height, yoffs);
    }


    /**
     * Sobreescribe el metodo paint y se encarga de pintar todo el árbol.
     *
     * @param g: Objeto de la clase Graphics.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        fm = g.getFontMetrics();

        if (dirty) {
            calcularPosiciones();
            dirty = false;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        DrawTree(g2d, this.miArbol.getRoot(), Integer.MAX_VALUE, Integer.MAX_VALUE,
            fm.getLeading() + fm.getAscent());
        fm = null;
    }

}
