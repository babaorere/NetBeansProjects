package com.manager.tda_arreglo;

import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manager
 */
public class dlgAppMain extends javax.swing.JDialog {

    private void mostrarDatos() {
        if (myArray == null) {
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblTable.getModel();
        model.setRowCount(0); // Borrar la tabla

        int[] aux = myArray.getDatos();

        for (int i = 0; i < aux.length; i++) {
            Object[] fila = {aux[i]};
            model.addRow(fila);
        }
    }

    private void limpiarEntradas() {
        txtTam.setText("");
        txtElemento.setText("");
    }

    private static class TDA_Arreglo {

        private int[] datos;
        private int tam;
        private boolean is_orden;

        /**
         * Constructor por defecto. Se le asigna un valor inicial de 1000 elementos
         *
         */
        public TDA_Arreglo() throws Exception {
            this.datos = new int[1000];
            this.tam = this.datos.length;
            this.is_orden = false;

            setValorDatos(0);
        }

        /**
         * Constructor por definiendo el tamaño
         *
         * @param tam
         * @throws Exception
         */
        public TDA_Arreglo(int tam) throws ArrayIndexOutOfBoundsException, Exception {

            if ((tam <= 0) || (tam > 10_000)) {
                throw new ArrayIndexOutOfBoundsException("Tamaño fuera de rango");
            }

            this.datos = new int[tam];
            this.tam = tam;
            this.is_orden = false;

            setValorDatos(0);
        }

        /**
         * Constructorr copy
         */
        public TDA_Arreglo(int[] indatos) throws ArrayIndexOutOfBoundsException {

            if ((indatos == null) || (indatos.length <= 0)) {
                throw new ArrayIndexOutOfBoundsException("Parametro Nulo");
            }

            this.datos = new int[indatos.length];
            this.tam = indatos.length;

            for (int i = 0; i < this.tam; i++) {
                this.datos[i] = indatos[i];
            }

            this.is_orden = false;
        }

        /**
         * Para establecer un valor para todo el array
         *
         * @param elemento
         * @throws Exception
         */
        public void setValorDatos(int elemento) throws Exception {
            if ((elemento < 0) || (elemento > 1_000_000)) {
                throw new Exception("Elemento fuera de rango, 0 <= elemeto <= 1_000_000");
            }

            for (int i = 0; i < this.getTam(); i++) {
                this.datos[i] = elemento;
            }
        }

        /**
         * Llena el Array de valores aleatorios
         *
         */
        public void fillRandom() {
            Random r = new Random();

            for (int i = 0; i < this.getTam(); i++) {
                datos[i] = r.nextInt(this.getTam() * 10);
            }
        }

        /**
         * utilizado para mostrar el contenido del Array
         *
         * @return
         */
        @Override
        public String toString() {
            StringBuilder aux = new StringBuilder();

            for (int i = 0; i < this.getTam(); i++) {
                aux.append(String.valueOf(this.datos[i])).append(", ");
            }

            return aux.toString();
        }

        /**
         * Retorna una copia del Array
         *
         * @return the datos
         */
        public int[] getDatos() {

            int[] aux = new int[tam];

            for (int i = 0; i < this.tam; i++) {
                aux[i] = this.datos[i];
            }

            return aux;
        }

        /**
         * @param datos the datos to set
         */
        public void setDatos(int[] indatos) throws ArrayIndexOutOfBoundsException {
            if ((indatos == null) || (indatos.length <= 0)) {
                throw new ArrayIndexOutOfBoundsException("Parametro Nulo");
            }

            this.datos = new int[indatos.length];
            this.tam = indatos.length;

            for (int i = 0; i < this.tam; i++) {
                this.datos[i] = indatos[i];
            }

            this.is_orden = false;
        }

        /**
         * @return the tam
         */
        public int getTam() {
            return tam;
        }

        /**
         * @param tam the tam to set
         */
        public void setTam(int tam) throws ArrayIndexOutOfBoundsException {
            if ((tam <= 0) || (tam > 10_000)) {
                throw new ArrayIndexOutOfBoundsException("Tamaño fuera de rango");
            }

            this.tam = tam;

            this.datos = new int[this.tam];

            for (int i = 0; i < this.tam; i++) {
                this.datos[i] = 0;
            }

            this.is_orden = false;
        }

        /**
         * @return the is_orden
         */
        public boolean isIs_orden() {
            return is_orden;
        }

        public void shell() {

            int salto;
            int aux;
            int i;
            boolean cent;

            for (salto = datos.length / 2; salto != 0; salto /= 2) {
                cent = true;
                while (cent) {
                    cent = false;
                    for (i = salto; i < datos.length; i++) {
                        if (datos[i - salto] > datos[i]) {
                            aux = datos[i];
                            datos[i] = datos[i - salto];
                            datos[i - salto] = aux;
                            cent = true;
                        }
                    }
                }
            }

            this.is_orden = true;
        }

        public int BusqBin(int item) {

            int lowIndex = 0;
            int highIndex = this.datos.length - 1;

            int pos = -1;

            while (lowIndex <= highIndex) {
                int midIndex = (lowIndex + highIndex) / 2;
                if (item == this.datos[midIndex]) {
                    pos = midIndex;
                    break;
                } else if (item < this.datos[midIndex]) {
                    highIndex = midIndex - 1;
                } else if (item > this.datos[midIndex]) {
                    lowIndex = midIndex + 1;
                }
            }
            return pos;
        }

    }

    private java.awt.Frame owner;

    private TDA_Arreglo myArray;

    /**
     * Creates new form dlgAppMain
     *
     * @param parent
     */
    public dlgAppMain(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        this.owner = parent;
        myArray = null;
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
        txtElemento = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnCrear = new javax.swing.JButton();
        btnAleatorio = new javax.swing.JButton();
        btnOrd = new javax.swing.JButton();
        btnBusq = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 400));
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
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 10, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        txtTam.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtTam.setMinimumSize(new java.awt.Dimension(100, 30));
        txtTam.setPreferredSize(new java.awt.Dimension(120, 30));
        txtTam.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 10, 10);
        jPanel1.add(txtTam, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Item Busq.");
        jLabel2.setMinimumSize(new java.awt.Dimension(80, 30));
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 10, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        txtElemento.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtElemento.setMinimumSize(new java.awt.Dimension(100, 30));
        txtElemento.setPreferredSize(new java.awt.Dimension(120, 30));
        txtElemento.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 10, 10);
        jPanel1.add(txtElemento, gridBagConstraints);

        getContentPane().add(jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnCrear.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnCrear.setText("Crear");
        btnCrear.setMinimumSize(new java.awt.Dimension(160, 35));
        btnCrear.setPreferredSize(new java.awt.Dimension(160, 35));
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 20);
        jPanel2.add(btnCrear, gridBagConstraints);

        btnAleatorio.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnAleatorio.setText("Aleatorio");
        btnAleatorio.setMinimumSize(new java.awt.Dimension(160, 35));
        btnAleatorio.setPreferredSize(new java.awt.Dimension(160, 35));
        btnAleatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAleatorioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 20);
        jPanel2.add(btnAleatorio, gridBagConstraints);

        btnOrd.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnOrd.setText("Ord. Shell");
        btnOrd.setMinimumSize(new java.awt.Dimension(160, 35));
        btnOrd.setPreferredSize(new java.awt.Dimension(160, 35));
        btnOrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 20);
        jPanel2.add(btnOrd, gridBagConstraints);

        btnBusq.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnBusq.setText("Busq. Binaria");
        btnBusq.setMinimumSize(new java.awt.Dimension(160, 35));
        btnBusq.setPreferredSize(new java.awt.Dimension(160, 35));
        btnBusq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusqActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 20);
        jPanel2.add(btnBusq, gridBagConstraints);

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
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 20);
        jPanel2.add(btnSalir, gridBagConstraints);

        getContentPane().add(jPanel2);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(40, 26));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(120, 375));

        tblTable.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        tblTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Array"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTable.setEnabled(false);
        jScrollPane1.setViewportView(tblTable);
        if (tblTable.getColumnModel().getColumnCount() > 0) {
            tblTable.getColumnModel().getColumn(0).setResizable(false);
            tblTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        }

        getContentPane().add(jScrollPane1);

        setSize(new java.awt.Dimension(610, 430));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        int tam;
        String strTam = txtTam.getText().trim().toUpperCase();

        try {
            tam = Integer.parseInt(strTam);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO ***");
            txtTam.requestFocus();
            return;
        }

        if ((tam <= 0) || (tam > 10_000)) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO *** 0 < tam <= 10_000)");
            txtTam.requestFocus();
            return;
        }

        try {
            myArray = new TDA_Arreglo(tam);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Error al crear el TDA");
            txtTam.requestFocus();
            myArray = null;
        }

        mostrarDatos();
        limpiarEntradas();
        txtTam.requestFocus();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnAleatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAleatorioActionPerformed
        if (myArray == null) {
            JOptionPane.showMessageDialog(null, "Debe crear primero el Array");
            txtTam.requestFocus();
            return;
        }

        myArray.fillRandom();
        mostrarDatos();
        limpiarEntradas();
        txtTam.requestFocus();
    }//GEN-LAST:event_btnAleatorioActionPerformed

    private void btnOrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdActionPerformed
        if (myArray == null) {
            JOptionPane.showMessageDialog(null, "Debe crear primero el Array");
            txtTam.requestFocus();
            return;
        }

        myArray.shell();
        mostrarDatos();
        limpiarEntradas();
        txtTam.requestFocus();
    }//GEN-LAST:event_btnOrdActionPerformed

    private void btnBusqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusqActionPerformed
        if (myArray == null) {
            JOptionPane.showMessageDialog(null, "Debe crear primero el Array");
            txtTam.requestFocus();
            return;
        }

        int item;
        String strItem = txtElemento.getText().trim().toUpperCase();

        try {
            item = Integer.parseInt(strItem);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO ***");
            txtElemento.requestFocus();
            return;
        }

        if ((item <= 0) || (item > 1_000_000)) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO *** 0 < elemento <= 1_000_000)");
            txtElemento.requestFocus();
            return;
        }

        if (myArray.isIs_orden()) {
            int pos = myArray.BusqBin(item);
            if (pos > 0) {
                JOptionPane.showMessageDialog(this, "Elemento encontrado posicion= " + pos);
            } else {
                JOptionPane.showMessageDialog(this, "Elemento NO encontrado");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El array debe estar ordenado");
        }

        mostrarDatos();
        limpiarEntradas();
        txtElemento.requestFocus();

    }//GEN-LAST:event_btnBusqActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        Salir();
    }//GEN-LAST:event_btnSalirActionPerformed

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
    private javax.swing.JButton btnBusq;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnOrd;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTable;
    private javax.swing.JTextField txtElemento;
    private javax.swing.JTextField txtTam;
    // End of variables declaration//GEN-END:variables
}
