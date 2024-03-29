package com.app;

import com.app.Modelos.Turno;
import com.app.Modelos.TurnoA;
import com.app.Modelos.TurnoB;
import com.app.Modelos.TurnoC;
import com.app.Modelos.TurnoD;
import controlador.Controlador;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 */
public class DlgDespachador extends javax.swing.JDialog {

    private PriorityQueue<Turno> colaPrioridad;
    private Queue<Turno> colaAtendidos;
    private Turno caja_1;
    private Turno caja_2;
    private Turno caja_3;

    private int cont_1;
    private int cont_2;
    private int cont_3;

    /**
     * Creates new form DlgDespachador
     *
     * @param parent
     */
    public DlgDespachador(java.awt.Window parent) {
        super(parent, ModalityType.APPLICATION_MODAL);
        initComponents();
        MyInitComponents();
    }

    private void MyInitComponents() {

        final java.awt.Window me = this;

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    Controlador.actionSalir(me);
                });
            }
        });

        // Genera la cola de prioridad con una capacidad inicial de 103 items
        colaPrioridad = new PriorityQueue<>(103);
        colaAtendidos = new LinkedList<>();

        caja_1 = null;
        caja_2 = null;
        caja_3 = null;

        cont_1 = 0;
        cont_2 = 0;
        cont_3 = 0;

        ActGUI();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstTurnos = new javax.swing.JList<>();
        pnlComp = new javax.swing.JPanel();
        pnlInfo = new javax.swing.JPanel();
        pnlInfoIzq = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblCaja_1 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblCaja_3 = new javax.swing.JLabel();
        pnlInfoDer = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblCaja_2 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInfo = new javax.swing.JTable();
        pnlBtn = new javax.swing.JPanel();
        btnGenTA = new javax.swing.JButton();
        btnLibC1 = new javax.swing.JButton();
        btnGenTB = new javax.swing.JButton();
        btnLibC2 = new javax.swing.JButton();
        btnGenTC = new javax.swing.JButton();
        btnLibC3 = new javax.swing.JButton();
        btnGenTD = new javax.swing.JButton();
        btnCerrarTurnos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("BANCO PATITO S.A. de C.V.");
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        pnlList.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        pnlList.setPreferredSize(new java.awt.Dimension(80, 400));
        pnlList.setLayout(new javax.swing.BoxLayout(pnlList, javax.swing.BoxLayout.LINE_AXIS));

        lstTurnos.setBackground(new java.awt.Color(0, 102, 204));
        jScrollPane1.setViewportView(lstTurnos);

        pnlList.add(jScrollPane1);

        getContentPane().add(pnlList);

        pnlComp.setLayout(new javax.swing.BoxLayout(pnlComp, javax.swing.BoxLayout.PAGE_AXIS));

        pnlInfo.setMinimumSize(new java.awt.Dimension(0, 250));
        pnlInfo.setPreferredSize(new java.awt.Dimension(519, 250));
        pnlInfo.setLayout(new javax.swing.BoxLayout(pnlInfo, javax.swing.BoxLayout.LINE_AXIS));

        pnlInfoIzq.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        pnlInfoIzq.setMinimumSize(new java.awt.Dimension(150, 175));
        pnlInfoIzq.setPreferredSize(new java.awt.Dimension(150, 175));
        pnlInfoIzq.setLayout(new javax.swing.BoxLayout(pnlInfoIzq, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel11.setBackground(new java.awt.Color(255, 102, 102));
        jPanel11.setMinimumSize(new java.awt.Dimension(260, 125));
        jPanel11.setPreferredSize(new java.awt.Dimension(260, 125));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Caja 1");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setPreferredSize(new java.awt.Dimension(80, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel11.add(jLabel1, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setMaximumSize(new java.awt.Dimension(80, 80));
        jPanel3.setMinimumSize(new java.awt.Dimension(80, 80));
        jPanel3.setPreferredSize(new java.awt.Dimension(80, 80));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblCaja_1.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblCaja_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCaja_1.setText("jLabel2");
        jPanel3.add(lblCaja_1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel11.add(jPanel3, gridBagConstraints);

        pnlInfoIzq.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(153, 153, 153));
        jPanel12.setMinimumSize(new java.awt.Dimension(260, 125));
        jPanel12.setPreferredSize(new java.awt.Dimension(260, 125));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Caja 3");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setMaximumSize(new java.awt.Dimension(80, 20));
        jLabel3.setMinimumSize(new java.awt.Dimension(80, 20));
        jLabel3.setPreferredSize(new java.awt.Dimension(80, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel12.add(jLabel3, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setMaximumSize(new java.awt.Dimension(80, 80));
        jPanel4.setMinimumSize(new java.awt.Dimension(80, 80));
        jPanel4.setPreferredSize(new java.awt.Dimension(80, 80));
        jPanel4.setLayout(new java.awt.BorderLayout());

        lblCaja_3.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblCaja_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCaja_3.setText("jLabel7");
        jPanel4.add(lblCaja_3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel12.add(jPanel4, gridBagConstraints);

        pnlInfoIzq.add(jPanel12);

        pnlInfo.add(pnlInfoIzq);

        pnlInfoDer.setMinimumSize(new java.awt.Dimension(150, 175));
        pnlInfoDer.setPreferredSize(new java.awt.Dimension(150, 175));
        pnlInfoDer.setLayout(new javax.swing.BoxLayout(pnlInfoDer, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel13.setBackground(new java.awt.Color(153, 153, 153));
        jPanel13.setMinimumSize(new java.awt.Dimension(260, 125));
        jPanel13.setPreferredSize(new java.awt.Dimension(260, 125));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Caja 2");
        jLabel5.setAlignmentX(0.5F);
        jLabel5.setMaximumSize(new java.awt.Dimension(80, 20));
        jLabel5.setMinimumSize(new java.awt.Dimension(80, 20));
        jLabel5.setPreferredSize(new java.awt.Dimension(80, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel13.add(jLabel5, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setMaximumSize(new java.awt.Dimension(80, 80));
        jPanel6.setMinimumSize(new java.awt.Dimension(80, 80));
        jPanel6.setPreferredSize(new java.awt.Dimension(80, 80));
        jPanel6.setLayout(new java.awt.BorderLayout());

        lblCaja_2.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblCaja_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCaja_2.setText("jLabel4");
        jPanel6.add(lblCaja_2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel13.add(jPanel6, gridBagConstraints);

        pnlInfoDer.add(jPanel13);

        jPanel14.setBackground(new java.awt.Color(102, 102, 102));
        jPanel14.setMinimumSize(new java.awt.Dimension(260, 125));
        jPanel14.setPreferredSize(new java.awt.Dimension(260, 125));
        jPanel14.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Turnos Atendidos");
        jLabel6.setAlignmentX(0.5F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel14.add(jLabel6, gridBagConstraints);

        jPanel15.setMaximumSize(new java.awt.Dimension(230, 80));
        jPanel15.setMinimumSize(new java.awt.Dimension(230, 80));
        jPanel15.setPreferredSize(new java.awt.Dimension(230, 80));
        jPanel15.setLayout(new java.awt.BorderLayout());

        tblInfo.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        tblInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "Caja"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblInfo);

        jPanel15.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel14.add(jPanel15, gridBagConstraints);

        pnlInfoDer.add(jPanel14);

        pnlInfo.add(pnlInfoDer);

        pnlComp.add(pnlInfo);

        pnlBtn.setBackground(new java.awt.Color(0, 51, 153));
        pnlBtn.setMinimumSize(new java.awt.Dimension(0, 150));
        pnlBtn.setPreferredSize(new java.awt.Dimension(522, 150));
        pnlBtn.setLayout(new java.awt.GridBagLayout());

        btnGenTA.setBackground(new java.awt.Color(0, 102, 204));
        btnGenTA.setText("<html>Genera<br>Turno A</html>");
        btnGenTA.setMinimumSize(new java.awt.Dimension(100, 50));
        btnGenTA.setPreferredSize(new java.awt.Dimension(100, 50));
        btnGenTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenTAActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        pnlBtn.add(btnGenTA, gridBagConstraints);

        btnLibC1.setBackground(new java.awt.Color(0, 102, 204));
        btnLibC1.setText("<html>Libera<br>Caja 1</html>");
        btnLibC1.setMinimumSize(new java.awt.Dimension(100, 50));
        btnLibC1.setPreferredSize(new java.awt.Dimension(100, 50));
        btnLibC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLibC1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 20, 20);
        pnlBtn.add(btnLibC1, gridBagConstraints);

        btnGenTB.setBackground(new java.awt.Color(0, 102, 204));
        btnGenTB.setText("<html>Genera<br>Turno B</html>");
        btnGenTB.setToolTipText("");
        btnGenTB.setMinimumSize(new java.awt.Dimension(100, 50));
        btnGenTB.setPreferredSize(new java.awt.Dimension(100, 50));
        btnGenTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenTBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 20);
        pnlBtn.add(btnGenTB, gridBagConstraints);

        btnLibC2.setBackground(new java.awt.Color(0, 102, 204));
        btnLibC2.setText("<html>Libera<br>Caja 2</html>");
        btnLibC2.setMinimumSize(new java.awt.Dimension(100, 50));
        btnLibC2.setPreferredSize(new java.awt.Dimension(100, 50));
        btnLibC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLibC2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 20);
        pnlBtn.add(btnLibC2, gridBagConstraints);

        btnGenTC.setBackground(new java.awt.Color(0, 102, 204));
        btnGenTC.setText("<html>Genera<br>Turno C</html>");
        btnGenTC.setMinimumSize(new java.awt.Dimension(100, 50));
        btnGenTC.setPreferredSize(new java.awt.Dimension(100, 50));
        btnGenTC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenTCActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 20);
        pnlBtn.add(btnGenTC, gridBagConstraints);

        btnLibC3.setBackground(new java.awt.Color(0, 102, 204));
        btnLibC3.setText("<html>Libera<br>Caja 3</html>");
        btnLibC3.setMinimumSize(new java.awt.Dimension(100, 50));
        btnLibC3.setPreferredSize(new java.awt.Dimension(100, 50));
        btnLibC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLibC3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 20);
        pnlBtn.add(btnLibC3, gridBagConstraints);

        btnGenTD.setBackground(new java.awt.Color(0, 102, 204));
        btnGenTD.setText("<html>Genera<br>Turno D</html>");
        btnGenTD.setMinimumSize(new java.awt.Dimension(100, 50));
        btnGenTD.setPreferredSize(new java.awt.Dimension(100, 50));
        btnGenTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenTDActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 20);
        pnlBtn.add(btnGenTD, gridBagConstraints);

        btnCerrarTurnos.setBackground(new java.awt.Color(0, 102, 204));
        btnCerrarTurnos.setText("<html>Cerrar<br>Turnos</html>");
        btnCerrarTurnos.setMinimumSize(new java.awt.Dimension(100, 50));
        btnCerrarTurnos.setPreferredSize(new java.awt.Dimension(100, 50));
        btnCerrarTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarTurnosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 20);
        pnlBtn.add(btnCerrarTurnos, gridBagConstraints);

        pnlComp.add(pnlBtn);

        getContentPane().add(pnlComp);

        setSize(new java.awt.Dimension(610, 430));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenTAActionPerformed
        actionGenTurno_A();
    }//GEN-LAST:event_btnGenTAActionPerformed

    private void btnGenTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenTBActionPerformed
        actionGenTurno_B();
    }//GEN-LAST:event_btnGenTBActionPerformed

    private void btnGenTCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenTCActionPerformed
        actionGenTurno_C();
    }//GEN-LAST:event_btnGenTCActionPerformed

    private void btnGenTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenTDActionPerformed
        actionGenTurno_D();
    }//GEN-LAST:event_btnGenTDActionPerformed

    private void btnLibC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLibC1ActionPerformed
        actionLibCaja_1();
    }//GEN-LAST:event_btnLibC1ActionPerformed

    private void btnLibC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLibC2ActionPerformed
        actionLibCaja_2();
    }//GEN-LAST:event_btnLibC2ActionPerformed

    private void btnLibC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLibC3ActionPerformed
        actionLibCaja_3();
    }//GEN-LAST:event_btnLibC3ActionPerformed

    private void btnCerrarTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarTurnosActionPerformed
        actionCerrarTurnos();
    }//GEN-LAST:event_btnCerrarTurnosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            DlgDespachador dialog = new DlgDespachador(new javax.swing.JFrame());
            dialog.setVisible(true);
            System.exit(0);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarTurnos;
    private javax.swing.JButton btnGenTA;
    private javax.swing.JButton btnGenTB;
    private javax.swing.JButton btnGenTC;
    private javax.swing.JButton btnGenTD;
    private javax.swing.JButton btnLibC1;
    private javax.swing.JButton btnLibC2;
    private javax.swing.JButton btnLibC3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCaja_1;
    private javax.swing.JLabel lblCaja_2;
    private javax.swing.JLabel lblCaja_3;
    private javax.swing.JList<Turno> lstTurnos;
    private javax.swing.JPanel pnlBtn;
    private javax.swing.JPanel pnlComp;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlInfoDer;
    private javax.swing.JPanel pnlInfoIzq;
    private javax.swing.JPanel pnlList;
    private javax.swing.JTable tblInfo;
    // End of variables declaration//GEN-END:variables

    private void LimpiarComp() {

        lblCaja_1.setText("");
        lblCaja_2.setText("");
        lblCaja_3.setText("");

        DefaultListModel<Turno> model = new DefaultListModel<>();
        model.clear();
        lstTurnos.setModel(model);

        DefaultTableModel tblModel = (DefaultTableModel) tblInfo.getModel();
        tblModel.setRowCount(0);
    }

    private void actionGenTurno_A() {
        colaPrioridad.add(new TurnoA());
        ActGUI();
    }

    private void actionGenTurno_B() {
        colaPrioridad.add(new TurnoB());
        ActGUI();
    }

    private void actionGenTurno_C() {
        colaPrioridad.add(new TurnoC());
        ActGUI();
    }

    private void actionGenTurno_D() {
        colaPrioridad.add(new TurnoD());
        ActGUI();
    }

    private void actionLibCaja_1() {

        // Guardar el turno actual en la Caja
        if (caja_1 != null) {
            colaAtendidos.add(caja_1);
            cont_1++;
        }

        caja_1 = colaPrioridad.poll();
        if (caja_1 != null) {
            caja_1.setCaja(1);
        }

        ActGUI();
    }

    private void actionLibCaja_2() {

        // Guardar el turno actual en la Caja
        if (caja_2 != null) {
            colaAtendidos.add(caja_2);
            cont_2++;
        }

        caja_2 = colaPrioridad.poll();
        if (caja_2 != null) {
            caja_2.setCaja(2);
        }

        ActGUI();
    }

    private void actionLibCaja_3() {
        // Guardar el turno actual en la Caja
        if (caja_3 != null) {
            colaAtendidos.add(caja_3);
            cont_3++;
        }

        caja_3 = colaPrioridad.poll();
        if (caja_3 != null) {
            caja_3.setCaja(3);
        }

        ActGUI();
    }

    private void actionCerrarTurnos() {

        // Guardar el turno actual en la Caja
        if (caja_1 != null) {
            colaAtendidos.add(caja_1);
            caja_1 = null;
        }

        // Guardar el turno actual en la Caja
        if (caja_2 != null) {
            colaAtendidos.add(caja_2);
            caja_2 = null;
        }

        // Guardar el turno actual en la Caja
        if (caja_3 != null) {
            colaAtendidos.add(caja_3);
            caja_3 = null;
        }

        ActGUI();
    }

    private void ActGUI() {
        LimpiarComp();

        Object[] aux = colaPrioridad.toArray();

        Arrays.sort(aux);

        final DefaultListModel model = (DefaultListModel) lstTurnos.getModel();
        for (Object item : aux) {
            model.addElement((Turno) item);
        }

        if (caja_1 != null) {
            lblCaja_1.setText(caja_1.toString());
        } else {
            lblCaja_1.setText("Libre");
        }

        if (caja_2 != null) {
            lblCaja_2.setText(caja_2.toString());
        } else {
            lblCaja_2.setText("Libre");
        }

        if (caja_3 != null) {
            lblCaja_3.setText(caja_3.toString());
        } else {
            lblCaja_3.setText("Libre");
        }

        DefaultTableModel tblModel = (DefaultTableModel) tblInfo.getModel();

        if (caja_3 != null) {
            tblModel.addRow(new Object[]{caja_3.toString(), 3});
        } else {
            tblModel.addRow(new Object[]{"libre", 3});
        }

        if (caja_2 != null) {
            tblModel.addRow(new Object[]{caja_2.toString(), 2});
        } else {
            tblModel.addRow(new Object[]{"libre", 2});
        }

        if (caja_1 != null) {
            tblModel.addRow(new Object[]{caja_1.toString(), 1});
        } else {
            tblModel.addRow(new Object[]{"libre", 1});
        }

    }
}
