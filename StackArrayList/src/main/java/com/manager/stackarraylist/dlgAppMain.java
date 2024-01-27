package com.manager.stackarraylist;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author manager
 */
public final class dlgAppMain extends javax.swing.JDialog {

    // Aqui se guarda la ventana contenedora, el Padre/Madre
    private java.awt.Frame owner;

    /**
     * Clase inmutable, solo tiene getters
     */
    public static final class Rectangular implements Cloneable {

        private static DecimalFormat df = new DecimalFormat("###.##");

        private final double coord_x;
        private final double coord_y;
        private final double modulus;
        private final double angle;

        //  constructor de la Class
        public Rectangular(double _x, double _y) {
            this.coord_x = _x;
            this.coord_y = _y;

            this.modulus = Math.sqrt(coord_x * coord_x + coord_y * coord_y);
            this.angle = Math.atan(coord_y / coord_x) * (180.0 / Math.PI);
        }

        //  copy constructor
        public Rectangular(Rectangular _other) {
            this.coord_x = _other.coord_x;
            this.coord_y = _other.coord_y;

            this.modulus = Math.sqrt(coord_x * coord_x + coord_y * coord_y);
            this.angle = Math.atan(coord_y / coord_x) * (180.0 / Math.PI);
        }

        public String toStrPolar() {
            //poder operar con ellos como lo que sigue:		
            return "( " + Double.toString(getModulus()) + " @ " + Double.toString(getAngle()) + " grados )";
        }

        @Override
        public String toString() {
            return ("( " + getDf().format(getCoord_x()) + ", " + getDf().format(getCoord_y()) + " )");
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        /**
         * @return the df
         */
        public static DecimalFormat getDf() {
            return (DecimalFormat) df.clone();
        }

        /**
         * @return the coord_x
         */
        public double getCoord_x() {
            return coord_x;
        }

        /**
         * @return the coord_y
         */
        public double getCoord_y() {
            return coord_y;
        }

        /**
         * @return the modulus
         */
        public double getModulus() {
            return modulus;
        }

        /**
         * @return the angle
         */
        public double getAngle() {
            return angle;
        }

    }

    public static final class Stack implements Cloneable {

        // Aqui se guardan las instacias de Rectangular, accediendo como un Stack
        private final ArrayList<Rectangular> stack;

        public Stack() {
            stack = new ArrayList<>(128);
        }

        public Stack(Stack other) {
            this.stack = new ArrayList<>(128);

            for (int i = 0; i < other.stack.size(); i++) {
                try {
                    this.stack.set(i, (Rectangular) other.stack.get(i).clone());
                } catch (CloneNotSupportedException ex) {
                    break;
                }
            }
        }

        public boolean isEmpty() {
            return stack.isEmpty();
        }

        public int getSize() {
            return stack.size();
        }

        public Rectangular pop() {
            Rectangular aux;
            aux = stack.remove((stack.size() - 1));
            return aux;
        }

        public void push(Rectangular _aux) {
            stack.add(_aux);
        }

        /**
         * Llena el vector de numeros complejos aleatorios
         *
         * @param intam
         */
        public void fillRandom(int intam) {

            // maxima cantidad de numeros es de 100
            int cant = intam % 101;

            if (cant <= 0) {
                cant = 100;
            }

            // crea el vector de un tamaño predefinido
            stack.clear();

            Random r = new Random();

            for (int i = 0; i < cant; i++) {
                push(new Rectangular(Math.round(r.nextDouble() * 10000) / 100.00, Math.round(r.nextDouble() * 10000) / 100.00));
            }

        }

        /**
         * Retorna una copia del elemento en el tope de la lista
         *
         * @return
         */
        public Rectangular getCopyTop() {
            if (stack.isEmpty()) {
                return null;
            }

            return new Rectangular(stack.get(stack.size() - 1));
        }

        /**
         * Deep copy
         *
         * @return
         * @throws CloneNotSupportedException
         */
        @Override
        protected Object clone() throws CloneNotSupportedException {
            Stack aux = (Stack) super.clone();

            // Copia nuevos elementos internos
            for (int i = 0; i < this.stack.size(); i++) {
                try {
                    aux.stack.set(i, (Rectangular) this.stack.get(i).clone());
                } catch (CloneNotSupportedException ex) {
                    break;
                }
            }

            return aux;
        }

    }

    Stack pila;

// **********************************************************************************************
    /**
     *
     */
    /**
     * Creates new form dlgAppMain
     *
     * @param parent
     */
    public dlgAppMain(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        this.owner = parent;
        MyInitComponents();
    }

    /**
     * Inicializar componentes propios de la clase
     *
     */
    private void MyInitComponents() {

        final javax.swing.JDialog me = this;
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Salir();
            }
        });

        this.pila = new Stack();
        this.pila.fillRandom(17);

        limpiarEntradas();
        mostrarDatos();

        txtTop.requestFocus();
    }

    /**
     * Acciones para cerrar la aplicacion
     *
     */
    private void Salir() {
        this.setVisible(false);
        this.dispose();

        if (owner != null) {
            owner.setVisible(false);
            owner.dispose();
        }

        System.exit(0);
    }

    private void mostrarDatos() {
        if (pila == null) {
            txtTam.setText("");
            return;
        }

        DefaultListModel model = new DefaultListModel();
        lstList.setModel(model);

        Stack aux;
        try {
            aux = (Stack) pila.clone();
        } catch (CloneNotSupportedException ex) {
            return;
        }

        if (aux != null) {
            for (int i = 0; i < aux.getSize(); i++) {
                model.addElement(aux.stack.get(i).toString());
            }
        }

        txtTam.setText(String.valueOf(aux.getSize()));
        Rectangular tmp = pila.getCopyTop();
        if (tmp != null) {
            txtTop.setText(tmp.toString());
        } else {
            txtTop.setText("");
        }

        txtCoordX.requestFocus();
    }

    private void limpiarEntradas() {
        txtTam.setText("");
        txtTop.setText("");
        txtCoordX.setText("");
        txtCoordY.setText("");

        txtCoordX.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTam = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTop = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCoordX = new javax.swing.JTextField();
        txtCoordY = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnPop = new javax.swing.JButton();
        btnTop = new javax.swing.JButton();
        btnPush = new javax.swing.JButton();
        btnAleatorio = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Stack con ArrayList");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setPreferredSize(new java.awt.Dimension(350, 350));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Tamaño");
        jLabel1.setMinimumSize(new java.awt.Dimension(80, 30));
        jLabel1.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        txtTam.setEditable(false);
        txtTam.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtTam.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTam.setMinimumSize(new java.awt.Dimension(150, 30));
        txtTam.setPreferredSize(new java.awt.Dimension(150, 30));
        txtTam.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(txtTam, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Top");
        jLabel2.setMinimumSize(new java.awt.Dimension(80, 30));
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        txtTop.setEditable(false);
        txtTop.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtTop.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTop.setMinimumSize(new java.awt.Dimension(150, 30));
        txtTop.setPreferredSize(new java.awt.Dimension(150, 30));
        txtTop.setRequestFocusEnabled(false);
        txtTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTopMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(txtTop, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Coord. X");
        jLabel3.setMinimumSize(new java.awt.Dimension(80, 30));
        jLabel3.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Coord. Y");
        jLabel4.setMinimumSize(new java.awt.Dimension(80, 30));
        jLabel4.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        txtCoordX.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtCoordX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCoordX.setMinimumSize(new java.awt.Dimension(150, 30));
        txtCoordX.setPreferredSize(new java.awt.Dimension(150, 30));
        txtCoordX.setRequestFocusEnabled(false);
        txtCoordX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCoordXMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(txtCoordX, gridBagConstraints);

        txtCoordY.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtCoordY.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCoordY.setMinimumSize(new java.awt.Dimension(150, 30));
        txtCoordY.setPreferredSize(new java.awt.Dimension(150, 30));
        txtCoordY.setRequestFocusEnabled(false);
        txtCoordY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCoordYMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(txtCoordY, gridBagConstraints);

        getContentPane().add(jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnPop.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnPop.setText("Pop");
        btnPop.setToolTipText("Elimina una Fracción al inicio de la lista");
        btnPop.setMinimumSize(new java.awt.Dimension(160, 35));
        btnPop.setPreferredSize(new java.awt.Dimension(160, 35));
        btnPop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPopActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        jPanel2.add(btnPop, gridBagConstraints);

        btnTop.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnTop.setText("Top");
        btnTop.setToolTipText("Inserta una Fraccion, al inicio de la lista\n");
        btnTop.setMinimumSize(new java.awt.Dimension(160, 35));
        btnTop.setPreferredSize(new java.awt.Dimension(160, 35));
        btnTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTopActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        jPanel2.add(btnTop, gridBagConstraints);

        btnPush.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnPush.setText("Push");
        btnPush.setToolTipText("Inserta una Fracción, al final de una lista");
        btnPush.setMinimumSize(new java.awt.Dimension(160, 35));
        btnPush.setPreferredSize(new java.awt.Dimension(160, 35));
        btnPush.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPushActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        jPanel2.add(btnPush, gridBagConstraints);

        btnAleatorio.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnAleatorio.setText("Aleatorio");
        btnAleatorio.setToolTipText("Toma del campo Item, la cantidad de elementos aleatorios tendra la lista enlazada, y la genera");
        btnAleatorio.setMinimumSize(new java.awt.Dimension(160, 35));
        btnAleatorio.setPreferredSize(new java.awt.Dimension(160, 35));
        btnAleatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAleatorioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        jPanel2.add(btnAleatorio, gridBagConstraints);

        btnSalir.setBackground(new java.awt.Color(255, 102, 102));
        btnSalir.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setMinimumSize(new java.awt.Dimension(160, 35));
        btnSalir.setPreferredSize(new java.awt.Dimension(160, 35));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        jPanel2.add(btnSalir, gridBagConstraints);

        getContentPane().add(jPanel2);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(100, 150));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(150, 150));

        lstList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstList.setEnabled(false);
        jScrollPane2.setViewportView(lstList);

        getContentPane().add(jScrollPane2);

        setSize(new java.awt.Dimension(610, 530));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPopActionPerformed
        // Lista no ha sido creada
        if (pila == null) {
            JOptionPane.showMessageDialog(null, "Debe crear primero la Pila");
            limpiarEntradas();
            txtCoordX.requestFocus();
            return;
        }

        // Lista Vacia
        if (pila.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pila Vacia");
            limpiarEntradas();
            txtCoordX.requestFocus();
            return;
        }

        Rectangular aux = pila.pop();

        mostrarDatos();

        if (pila.isEmpty()) {
            txtCoordX.setText("");
            txtCoordY.setText("");
        } else {
            txtCoordX.setText(String.valueOf(aux.getCoord_x()));
            txtCoordY.setText(String.valueOf(aux.getCoord_y()));
        }

        txtCoordX.requestFocus();
    }//GEN-LAST:event_btnPopActionPerformed

    private void btnAleatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAleatorioActionPerformed
        if (pila == null) {
            JOptionPane.showMessageDialog(null, "Debe crear primero la Pila");
            txtTop.requestFocus();
            return;
        }

        pila.fillRandom(17);

        limpiarEntradas();
        mostrarDatos();

        txtTop.setText(pila.getCopyTop().toString());
        txtCoordX.requestFocus();
        txtCoordX.requestFocus();
    }//GEN-LAST:event_btnAleatorioActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        Salir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnPushActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPushActionPerformed

        // Lista no ha sido creada
        if (pila == null) {
            JOptionPane.showMessageDialog(null, "Debe crear primero la Pila");
            limpiarEntradas();
            txtCoordX.requestFocus();
            return;
        }

        // Lista Vacia
        if (pila.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pila Vacia");
            limpiarEntradas();
            txtCoordX.requestFocus();
            return;
        }

        double x;
        String strX = txtCoordX.getText().trim();

        try {
            x = Double.parseDouble(strX);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO ***");
            txtCoordX.requestFocus();
            return;
        }

        if ((x <= -100) || (x >= 100)) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO *** -100 < elemento < 100)");
            txtCoordX.requestFocus();
            return;
        }

        double y;
        String strY = txtCoordY.getText().trim();

        try {
            y = Double.parseDouble(strY);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO ***");
            txtCoordY.requestFocus();
            return;
        }

        if ((y <= -100) || (y >= 100)) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO *** -100 < elemento < 100)");
            txtCoordY.requestFocus();
            return;
        }

        pila.push(new Rectangular(x, y));

        limpiarEntradas();
        mostrarDatos();
        txtCoordX.requestFocus();
    }//GEN-LAST:event_btnPushActionPerformed

    private void btnTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTopActionPerformed
        // Lista no ha sido creada
        if (pila == null) {
            JOptionPane.showMessageDialog(null, "Debe crear primero la Pila");
            limpiarEntradas();
            txtCoordX.requestFocus();
            return;
        }

        // Lista Vacia
        if (pila.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pila Vacia");
            limpiarEntradas();
            txtCoordX.requestFocus();
            return;
        }

        limpiarEntradas();
        mostrarDatos();

        txtCoordX.requestFocus();
    }//GEN-LAST:event_btnTopActionPerformed

    private void txtTopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTopMouseClicked
        txtTop.requestFocus();
    }//GEN-LAST:event_txtTopMouseClicked

    private void txtCoordXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCoordXMouseClicked
        txtCoordX.requestFocus();
    }//GEN-LAST:event_txtCoordXMouseClicked

    private void txtCoordYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCoordYMouseClicked
        txtCoordY.requestFocus();
    }//GEN-LAST:event_txtCoordYMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new dlgAppMain(new javax.swing.JFrame())).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAleatorio;
    private javax.swing.JButton btnPop;
    private javax.swing.JButton btnPush;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lstList;
    private javax.swing.JTextField txtCoordX;
    private javax.swing.JTextField txtCoordY;
    private javax.swing.JTextField txtTam;
    private javax.swing.JTextField txtTop;
    // End of variables declaration//GEN-END:variables

}
