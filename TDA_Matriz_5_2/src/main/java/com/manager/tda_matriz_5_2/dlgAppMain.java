package com.manager.tda_matriz_5_2;

import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manager
 */
public final class dlgAppMain extends javax.swing.JDialog {

    private void GenAleatorio() {

        // maxima cantidad de numeros es de 100
        int nfil = Matriz.TAM;
        int ncol = Matriz.TAM;

        if ((nfil <= 0) || (nfil > 6)) {
            nfil = 6;
        }

        if ((ncol <= 0) || (ncol > 6)) {
            ncol = 6;
        }

        Random r = new Random();

        // Llenar las Matrices
        for (int i = 0; i < nfil; i++) {
            for (int j = 0; j < ncol; j++) {
                this.modelA.setValueAt(r.nextInt(10), i, j);
                this.modelB.setValueAt(r.nextInt(10), i, j);
                this.modelC.setValueAt("", i, j);
            }
        }
    }

    // ***************************************************************************************************************************
    /**
     * Clase que define operaciones sobre Matrices enteras
     */
    public static final class Matriz implements Cloneable {

        public static final int TAM = 6;

        private int[][] myMat;

        // Constructor por defecto
        public Matriz() {
            myMat = new int[TAM][TAM];

            for (int i = 0; i < TAM; i++) {
                for (int j = 0; j < TAM; j++) {
                    myMat[i][j] = 0;
                }
            }

        }

        // copy Constructor
        public Matriz(int[][] _myMat) {

            // Verificar si tienen la misma cantidad de filas y columnas
            if (_myMat.length != TAM || _myMat[0].length != TAM) {
                myMat = new int[TAM][TAM];
                // Llenar la Matriz de ceros
                for (int i = 0; i < TAM; i++) {
                    for (int j = 0; j < TAM; j++) {
                        myMat[i][j] = 0;
                    }
                }

                return;
            }

            // Inicializar la Matriz
            myMat = new int[TAM][TAM];

            // Copiar la Matriz
            for (int i = 0; i < TAM; i++) {
                for (int j = 0; j < TAM; j++) {
                    myMat[i][j] = _myMat[i][j];
                }
            }

        }

        // copy Constructor
        public Matriz(Matriz _other) {

            // Verificar si tienen la misma cantidad de filas y columnas
            if (_other.getNFil() != this.getNFil() || _other.getNRow() != this.getNRow()) {
                myMat = new int[TAM][TAM];
                // Llenar las Matrices
                for (int i = 0; i < TAM; i++) {
                    for (int j = 0; j < TAM; j++) {
                        myMat[i][j] = 0;
                    }
                }

                return;
            }

            myMat = new int[TAM][TAM];

            // Copiar la Matriz
            for (int i = 0; i < TAM; i++) {
                for (int j = 0; j < TAM; j++) {
                    myMat[i][j] = _other.myMat[i][j];
                }
            }

        }

        /**
         * Realiza una Deep copy
         *
         * @return
         * @throws CloneNotSupportedException
         */
        @Override
        protected Object clone() throws CloneNotSupportedException {
            Matriz aux = (Matriz) super.clone();

            for (int i = 0; i < TAM; i++) {
                for (int j = 0; j < TAM; j++) {
                    aux.myMat[i][j] = myMat[i][j];
                }
            }

            return aux;
        }

        public int getNFil() {
            return myMat.length;
        }

        public int getNRow() {
            return myMat[0].length;
        }

        /**
         * Retornar el resultado de sumar las Matrices
         *
         * @param other
         * @return
         */
        public Matriz add(Matriz other) {
            Matriz aux = new Matriz();

            for (int i = 0; i < TAM; i++) {
                for (int j = 0; j < TAM; j++) {
                    aux.myMat[i][j] = this.myMat[i][j] + other.myMat[i][j];
                }
            }

            return aux;
        }

        /**
         * Retornar el resultado de sumar las Matrices
         *
         * @param other
         * @return
         */
        public Matriz sub(Matriz other) {
            Matriz aux = new Matriz();

            for (int i = 0; i < TAM; i++) {
                for (int j = 0; j < TAM; j++) {
                    aux.myMat[i][j] = this.myMat[i][j] - other.myMat[i][j];
                }
            }

            return aux;
        }

        /**
         * Retornar el resultado de sumar las Matrices
         *
         * @param other
         * @return
         */
        public Matriz mult(Matriz other) {
            Matriz aux = new Matriz();

            // Se realiza la Multiplicacion
            for (int a = 0; a < TAM; a++) {
                for (int i = 0; i < TAM; i++) {
                    int suma = 0;
                    for (int j = 0; j < TAM; j++) {
                        suma += this.myMat[i][j] * other.myMat[j][a];
                    }
                    aux.myMat[i][a] = suma;
                }
            }

            return aux;
        }

        /**
         * Retornar el resultado de sumar las Matrices
         *
         * @return
         */
        public double det() {
            return auxDet(myMat);
        }

        private double auxDet(int[][] auxMat) {
            double determinante = 0.0;

            int nFilas = auxMat.length;
            int nColumnas = auxMat[0].length;

            // Si la matriz es 1x1, el determinante es el elemento de la matriz
            if ((nFilas == 1) && (nColumnas == 1)) {
                return auxMat[0][0];
            }

            int signo = 1;

            for (int col = 0; col < nColumnas; col++) {
                int[][] submatriz = getSubmatriz(auxMat, nFilas, nColumnas, col);
                determinante = determinante + signo * auxMat[0][col] * auxDet(submatriz);
                signo *= -1;
            }

            return determinante;
        }

        public int determinante() {
            Matriz a;
            try {
                a = (Matriz) clone();
            } catch (CloneNotSupportedException ex) {
                return 0;
            }

            for (int k = 0; k < TAM - 1; k++) {
                for (int i = k + 1; i < TAM; i++) {
                    for (int j = k + 1; j < TAM; j++) {
                        a.myMat[i][j] -= a.myMat[i][k] * a.myMat[k][j] / a.myMat[k][k];
                    }
                }
            }

            int deter = 1;
            for (int i = 0; i < TAM; i++) {
                deter *= a.myMat[i][i];
            }

            return deter;
        }

        /**
         * Obtiene la matriz que resulta de eliminar la primera fila y la columna que se pasa como parámetro.
         *
         * @param auxMat Matriz original
         * @param nFilas Numero de filas de la matriz original
         * @param nColumnas Numero de columnas de la matriz original
         * @param col Columna que se quiere eliminar, junto con la fila=0
         * @return Una matriz de N-1 x N-1 elementos
         */
        private int[][] getSubmatriz(int[][] auxMat, int nFilas, int nColumnas, int col) {

            int[][] submatriz = new int[nFilas - 1][nColumnas - 1];

            int contador = 0;
            for (int j = 0; j < nColumnas; j++) {
                if (j == col) {
                    continue;
                }

                for (int i = 1; i < nFilas; i++) {
                    submatriz[i - 1][contador] = auxMat[i][j];
                }

                contador++;
            }

            return submatriz;
        }

        public int determinante2() {
            return determinante(myMat);
        }

        private int determinante(int[][] matriz) {

            int det = 0;

            if (matriz.length == 1) {
                return matriz[0][0];

            } else {
                for (int j = 0; j < matriz.length; j++) {
                    det = det + matriz[0][j] * cofactor(matriz, 0, j);
                }
            }

            return det;
        }

        private int cofactor(int[][] matriz, int fila, int columna) {

            int submatriz[][];
            int n = matriz.length - 1;

            submatriz = new int[n][n];
            int x = 0;
            int y = 0;
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz.length; j++) {
                    if (i != fila && j != columna) {
                        submatriz[x][y] = matriz[i][j];
                        y++;
                        if (y >= n) {
                            x++;
                            y = 0;
                        }
                    }
                }
            }

            return (int) Math.pow(-1.0, fila + columna) * determinante(submatriz);
        }

        public Matriz traspuesta() {
            Matriz auxMat = new Matriz();

            for (int i = 0; i < TAM; i++) {
                for (int j = 0; j < TAM; j++) {
                    auxMat.myMat[i][j] = myMat[j][i];
                }
            }
            return auxMat;
        }

        public Matriz inversa2() {
            return inversa(this);
        }

        private Matriz inversa(Matriz _auxMat) {
            int n = TAM;
            Matriz a;
            try {
                a = (Matriz) _auxMat.clone();
            } catch (CloneNotSupportedException ex) {
                return null;
            }

            Matriz b = new Matriz();
            Matriz c = new Matriz();

            //matriz unidad
            for (int i = 0; i < n; i++) {
                b.myMat[i][i] = 1;
            }
            try {

                // transformación de la matriz y de los términos independientes
                for (int k = 0; k < n - 1; k++) {
                    for (int i = k + 1; i < n; i++) {

                        //términos independientes
                        for (int s = 0; s < n; s++) {
                            b.myMat[i][s] -= a.myMat[i][k] * b.myMat[k][s] / a.myMat[k][k];
                        }

                        //elementos de la matriz
                        for (int j = k + 1; j < n; j++) {
                            a.myMat[i][j] -= a.myMat[i][k] * a.myMat[k][j] / a.myMat[k][k];
                        }
                    }
                }

                //cálculo de las incógnitas, elementos de la matriz inversa
                for (int s = 0; s < n; s++) {
                    c.myMat[n - 1][s] = b.myMat[n - 1][s] / a.myMat[n - 1][n - 1];
                    for (int i = n - 2; i >= 0; i--) {
                        c.myMat[i][s] = b.myMat[i][s] / a.myMat[i][i];
                        for (int k = n - 1; k > i; k--) {
                            c.myMat[i][s] -= a.myMat[i][k] * c.myMat[k][s] / a.myMat[i][i];
                        }
                    }
                }
            } catch (Exception e) {
                return null;
            }

            return c;
        }

        public int determinant(int[][] matrix) {
            if (matrix.length != matrix[0].length) {
                throw new IllegalStateException("invalid dimensions");
            }

            if (matrix.length == 2) {
                return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            }

            int det = 0;
            for (int i = 0; i < matrix[0].length; i++) {
                det += Math.pow(-1, i) * matrix[0][i]
                        * determinant(minor(matrix, 0, i));
            }

            return det;
        }

        public int[][] inverse(int[][] matrix) {
            int[][] inverse = new int[matrix.length][matrix.length];

            // minors and cofactors
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    inverse[i][j] = (int) Math.round(Math.pow(-1, i + j)) * determinant(minor(matrix, i, j));
                }
            }

            // adjugate and determinant
            float det = 1f / determinant(matrix);
            for (int i = 0; i < inverse.length; i++) {
                for (int j = 0; j <= i; j++) {
                    float temp = inverse[i][j];
                    inverse[i][j] = Math.round(inverse[j][i] * det);
                    inverse[j][i] = Math.round(temp * det);
                }
            }

            return inverse;
        }

        public int[][] minor(int[][] matrix, int row, int column) {
            int[][] minor = new int[matrix.length - 1][matrix.length - 1];

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; i != row && j < matrix[i].length; j++) {
                    if (j != column) {
                        minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
                    }
                }
            }

            return minor;
        }

    }

    //*****************************************************************************************************************************
    //*****************************************************************************************************************************
    private DefaultTableModel modelA;
    private DefaultTableModel modelB;
    private DefaultTableModel modelC;

    /**
     * Creates new form dlgMulMat
     *
     * @param parent
     */
    public dlgAppMain(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        MyInitComponents();
    }

    private void MyInitComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Salir();
            }
        });

        modelA = (DefaultTableModel) (tblMatA.getModel());
        modelB = (DefaultTableModel) (tblMatB.getModel());
        modelC = (DefaultTableModel) (tblMatC.getModel());

        CleanTbl(tblMatA);
        CleanTbl(tblMatB);
        CleanTbl(tblMatC);

        DefaultTableCellRenderer centerRendererA = new DefaultTableCellRenderer();
        centerRendererA.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < modelA.getColumnCount(); i++) {
            tblMatA.getColumnModel().getColumn(i).setCellRenderer(centerRendererA);
        }

        DefaultTableCellRenderer centerRendererB = new DefaultTableCellRenderer();
        centerRendererB.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < modelB.getColumnCount(); i++) {
            tblMatB.getColumnModel().getColumn(i).setCellRenderer(centerRendererB);
        }

        DefaultTableCellRenderer centerRendererC = new DefaultTableCellRenderer();
        centerRendererC.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < modelC.getColumnCount(); i++) {
            tblMatC.getColumnModel().getColumn(i).setCellRenderer(centerRendererC);
        }

        GenAleatorio();

    }

    /**
     * Limpiar la tabla, llenandola de ceros (0) en su totalidad
     *
     * @param table
     */
    private void CleanTbl(JTable table) {
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.setValueAt(0, i, j);
            }
        }
    }

    private void Salir() {

        final javax.swing.JDialog me = this;
        final javax.swing.JFrame parent = (javax.swing.JFrame) me.getParent();

        me.setVisible(false);
        me.dispose();

        if (parent != null) {
            parent.setVisible(false);
            parent.dispose();
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

        pnlTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlText = new javax.swing.JPanel();
        btnAleatorio = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnAmasB = new javax.swing.JButton();
        btnAmenosB = new javax.swing.JButton();
        btnApuntoB = new javax.swing.JButton();
        btnInvA = new javax.swing.JButton();
        btnAcruzB = new javax.swing.JButton();
        btnDetA = new javax.swing.JButton();
        pnlTables = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMatA = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMatB = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMatC = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        pnlTitulo.setLayout(new javax.swing.BoxLayout(pnlTitulo, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Operaciones sobre Matrices Integer");
        jLabel1.setMinimumSize(new java.awt.Dimension(315, 50));
        jLabel1.setPreferredSize(new java.awt.Dimension(330, 50));
        pnlTitulo.add(jLabel1);

        getContentPane().add(pnlTitulo);

        pnlText.setMaximumSize(new java.awt.Dimension(2147483647, 75));
        pnlText.setMinimumSize(new java.awt.Dimension(700, 75));
        pnlText.setPreferredSize(new java.awt.Dimension(770, 75));
        pnlText.setLayout(new java.awt.GridBagLayout());

        btnAleatorio.setBackground(new java.awt.Color(0, 153, 51));
        btnAleatorio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAleatorio.setForeground(new java.awt.Color(0, 0, 0));
        btnAleatorio.setText("Aleatorio");
        btnAleatorio.setMinimumSize(new java.awt.Dimension(100, 25));
        btnAleatorio.setPreferredSize(new java.awt.Dimension(100, 25));
        btnAleatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAleatorioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlText.add(btnAleatorio, gridBagConstraints);

        btnSalir.setBackground(new java.awt.Color(255, 153, 153));
        btnSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(0, 0, 0));
        btnSalir.setText("Salir");
        btnSalir.setPreferredSize(new java.awt.Dimension(100, 25));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlText.add(btnSalir, gridBagConstraints);

        btnAmasB.setBackground(new java.awt.Color(0, 153, 51));
        btnAmasB.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAmasB.setForeground(new java.awt.Color(0, 0, 0));
        btnAmasB.setText("A + B");
        btnAmasB.setMinimumSize(new java.awt.Dimension(100, 25));
        btnAmasB.setPreferredSize(new java.awt.Dimension(100, 25));
        btnAmasB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmasBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlText.add(btnAmasB, gridBagConstraints);

        btnAmenosB.setBackground(new java.awt.Color(0, 153, 51));
        btnAmenosB.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAmenosB.setForeground(new java.awt.Color(0, 0, 0));
        btnAmenosB.setText("A - B");
        btnAmenosB.setMinimumSize(new java.awt.Dimension(100, 25));
        btnAmenosB.setPreferredSize(new java.awt.Dimension(100, 25));
        btnAmenosB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmenosBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlText.add(btnAmenosB, gridBagConstraints);

        btnApuntoB.setBackground(new java.awt.Color(0, 153, 51));
        btnApuntoB.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnApuntoB.setForeground(new java.awt.Color(0, 0, 0));
        btnApuntoB.setText("Transp. A");
        btnApuntoB.setToolTipText("Calcula la Transpuesta de A");
        btnApuntoB.setMinimumSize(new java.awt.Dimension(100, 25));
        btnApuntoB.setPreferredSize(new java.awt.Dimension(100, 25));
        btnApuntoB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApuntoBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlText.add(btnApuntoB, gridBagConstraints);

        btnInvA.setBackground(new java.awt.Color(0, 153, 51));
        btnInvA.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnInvA.setForeground(new java.awt.Color(0, 0, 0));
        btnInvA.setText("Inv. A");
        btnInvA.setMinimumSize(new java.awt.Dimension(100, 25));
        btnInvA.setPreferredSize(new java.awt.Dimension(100, 25));
        btnInvA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvAActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlText.add(btnInvA, gridBagConstraints);

        btnAcruzB.setBackground(new java.awt.Color(0, 153, 51));
        btnAcruzB.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAcruzB.setForeground(new java.awt.Color(0, 0, 0));
        btnAcruzB.setText("A x B");
        btnAcruzB.setMinimumSize(new java.awt.Dimension(100, 25));
        btnAcruzB.setPreferredSize(new java.awt.Dimension(100, 25));
        btnAcruzB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcruzBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlText.add(btnAcruzB, gridBagConstraints);

        btnDetA.setBackground(new java.awt.Color(0, 153, 51));
        btnDetA.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnDetA.setForeground(new java.awt.Color(0, 0, 0));
        btnDetA.setText("Det A");
        btnDetA.setMinimumSize(new java.awt.Dimension(100, 25));
        btnDetA.setPreferredSize(new java.awt.Dimension(100, 25));
        btnDetA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetAActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlText.add(btnDetA, gridBagConstraints);

        getContentPane().add(pnlText);

        pnlTables.setLayout(new javax.swing.BoxLayout(pnlTables, javax.swing.BoxLayout.LINE_AXIS));

        tblMatA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "A.Col.1", "A.Col.2", "A.Col.3", "A.Col.4", "A.Col.5", "A.Col.6"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblMatA.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblMatA);
        if (tblMatA.getColumnModel().getColumnCount() > 0) {
            tblMatA.getColumnModel().getColumn(0).setResizable(false);
            tblMatA.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(1).setResizable(false);
            tblMatA.getColumnModel().getColumn(1).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(2).setResizable(false);
            tblMatA.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(3).setResizable(false);
            tblMatA.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(4).setResizable(false);
            tblMatA.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(5).setResizable(false);
            tblMatA.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        pnlTables.add(jScrollPane3);

        tblMatB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "B.Col.1", "B.Col.2", "B.Col.3", "B.Col.4", "B.Col.5", "B.Col.6"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblMatB.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblMatB);
        if (tblMatB.getColumnModel().getColumnCount() > 0) {
            tblMatB.getColumnModel().getColumn(0).setResizable(false);
            tblMatB.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(1).setResizable(false);
            tblMatB.getColumnModel().getColumn(1).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(2).setResizable(false);
            tblMatB.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(3).setResizable(false);
            tblMatB.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(4).setResizable(false);
            tblMatB.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(5).setResizable(false);
            tblMatB.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        pnlTables.add(jScrollPane2);

        tblMatC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "C.Col.1", "C.Col.2", "C.Col.3", "C.Col.4", "C.Col.5", "C.Col.6"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMatC.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblMatC);

        pnlTables.add(jScrollPane1);

        getContentPane().add(pnlTables);

        setSize(new java.awt.Dimension(1010, 330));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAleatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAleatorioActionPerformed
        GenAleatorio();
    }//GEN-LAST:event_btnAleatorioActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        Salir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAmasBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmasBActionPerformed

        Matriz matA = new Matriz();
        Matriz matB = new Matriz();
        Matriz matC;

        // Llenar las Matriz A
        for (int i = 0; i < modelA.getRowCount(); i++) {
            for (int j = 0; j < modelA.getColumnCount(); j++) {
                matA.myMat[i][j] = (int) modelA.getValueAt(i, j);
            }
        }

        // Llenar las Matriz B
        for (int i = 0; i < modelB.getRowCount(); i++) {
            for (int j = 0; j < modelB.getColumnCount(); j++) {
                matB.myMat[i][j] = (int) modelB.getValueAt(i, j);
            }
        }

        matC = matA.add(matB);

        // Llenar la tabla Matriz C
        for (int i = 0; i < Matriz.TAM; i++) {
            for (int j = 0; j < Matriz.TAM; j++) {
                modelC.setValueAt(matC.myMat[i][j], i, j);
            }
        }

    }//GEN-LAST:event_btnAmasBActionPerformed

    private void btnAmenosBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmenosBActionPerformed
        Matriz matA = new Matriz();
        Matriz matB = new Matriz();
        Matriz matC;

        // Llenar las Matriz A
        for (int i = 0; i < modelA.getRowCount(); i++) {
            for (int j = 0; j < modelA.getColumnCount(); j++) {
                matA.myMat[i][j] = (int) modelA.getValueAt(i, j);
            }
        }

        // Llenar las Matriz B
        for (int i = 0; i < modelB.getRowCount(); i++) {
            for (int j = 0; j < modelB.getColumnCount(); j++) {
                matB.myMat[i][j] = (int) modelB.getValueAt(i, j);
            }
        }

        matC = matA.sub(matB);

        // Llenar la tabla Matriz C
        for (int i = 0; i < Matriz.TAM; i++) {
            for (int j = 0; j < Matriz.TAM; j++) {
                modelC.setValueAt(matC.myMat[i][j], i, j);
            }
        }

    }//GEN-LAST:event_btnAmenosBActionPerformed

    private void btnApuntoBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApuntoBActionPerformed
        Matriz matA = new Matriz();
        Matriz matC;

        // Llenar las Matriz A
        for (int i = 0; i < modelA.getRowCount(); i++) {
            for (int j = 0; j < modelA.getColumnCount(); j++) {
                matA.myMat[i][j] = (int) modelA.getValueAt(i, j);
            }
        }

        // Llenar las Matriz B
        for (int i = 0; i < Matriz.TAM; i++) {
            for (int j = 0; j < Matriz.TAM; j++) {
                modelB.setValueAt("", i, j);
            }
        }

        matC = matA.traspuesta();

        // Llenar la tabla Matriz C
        for (int i = 0; i < Matriz.TAM; i++) {
            for (int j = 0; j < Matriz.TAM; j++) {
                modelC.setValueAt(matC.myMat[i][j], i, j);
            }
        }

    }//GEN-LAST:event_btnApuntoBActionPerformed

    private void btnInvAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvAActionPerformed
        Matriz matA = new Matriz();
        Matriz matC;

        // Llenar las Matriz A
        for (int i = 0; i < modelA.getRowCount(); i++) {
            for (int j = 0; j < modelA.getColumnCount(); j++) {
                matA.myMat[i][j] = (int) modelA.getValueAt(i, j);
            }
        }

        // Llenar las Matriz B
        for (int i = 0; i < Matriz.TAM; i++) {
            for (int j = 0; j < Matriz.TAM; j++) {
                modelB.setValueAt("", i, j);
            }
        }

        matC = new Matriz(matA.inverse(matA.myMat));

        if (matC == null) {
            JOptionPane.showMessageDialog(null, "Error");
            for (int i = 0; i < Matriz.TAM; i++) {
                for (int j = 0; j < Matriz.TAM; j++) {
                    modelC.setValueAt("", i, j);
                }
            }
            return;
        }

        // Llenar la tabla Matriz C
        for (int i = 0; i < Matriz.TAM; i++) {
            for (int j = 0; j < Matriz.TAM; j++) {
                modelC.setValueAt(matC.myMat[i][j], i, j);
            }
        }
    }//GEN-LAST:event_btnInvAActionPerformed

    private void btnAcruzBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcruzBActionPerformed
        Matriz matA = new Matriz();
        Matriz matB = new Matriz();
        Matriz matC;

        // Llenar las Matriz A
        for (int i = 0; i < modelA.getRowCount(); i++) {
            for (int j = 0; j < modelA.getColumnCount(); j++) {
                matA.myMat[i][j] = (int) modelA.getValueAt(i, j);
            }
        }

        // Llenar las Matriz B
        for (int i = 0; i < modelB.getRowCount(); i++) {
            for (int j = 0; j < modelB.getColumnCount(); j++) {
                matB.myMat[i][j] = (int) modelB.getValueAt(i, j);
            }
        }

        matC = matA.mult(matB);

        // Llenar la tabla Matriz C
        for (int i = 0; i < Matriz.TAM; i++) {
            for (int j = 0; j < Matriz.TAM; j++) {
                modelC.setValueAt(matC.myMat[i][j], i, j);
            }
        }

    }//GEN-LAST:event_btnAcruzBActionPerformed

    private void btnDetAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetAActionPerformed
        Matriz matA = new Matriz();
        Matriz matC;

        // Llenar las Matriz A
        for (int i = 0; i < modelA.getRowCount(); i++) {
            for (int j = 0; j < modelA.getColumnCount(); j++) {
                matA.myMat[i][j] = (int) modelA.getValueAt(i, j);
            }
        }

        // Llenar las Matriz B
        for (int i = 0; i < Matriz.TAM; i++) {
            for (int j = 0; j < Matriz.TAM; j++) {
                modelB.setValueAt("", i, j);
            }
        }

        // Llenar la tabla Matriz C
        for (int i = 0; i < Matriz.TAM; i++) {
            for (int j = 0; j < Matriz.TAM; j++) {
                modelC.setValueAt("", i, j);
            }
        }

        modelC.setValueAt(matA.determinant(matA.myMat), 0, 0);

    }//GEN-LAST:event_btnDetAActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dlgAppMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dlgAppMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dlgAppMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dlgAppMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                javax.swing.JFrame frmApp = new javax.swing.JFrame();
                dlgAppMain dialog = new dlgAppMain(new javax.swing.JFrame());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcruzB;
    private javax.swing.JButton btnAleatorio;
    private javax.swing.JButton btnAmasB;
    private javax.swing.JButton btnAmenosB;
    private javax.swing.JButton btnApuntoB;
    private javax.swing.JButton btnDetA;
    private javax.swing.JButton btnInvA;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel pnlTables;
    private javax.swing.JPanel pnlText;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTable tblMatA;
    private javax.swing.JTable tblMatB;
    private javax.swing.JTable tblMatC;
    // End of variables declaration//GEN-END:variables
}
