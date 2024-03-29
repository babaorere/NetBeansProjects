package serviciobarberia.vista;

import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import java.awt.Window;
import java.util.Optional;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import serviciobarberia.modelo.Bitacora;
import serviciobarberia.modelo.BitacoraCRUD;
import serviciobarberia.modelo.Cliente;
import serviciobarberia.modelo.ClienteCRUD;
import serviciobarberia.modelo.Servicio;
import serviciobarberia.modelo.ServicioCRUD;

/**
 *
 */
public final class BitacoraModificar extends javax.swing.JDialog {

    Optional<Bitacora> obitacora;
    private boolean modificar;
    private boolean leerIdbitacora;

    public BitacoraModificar(java.awt.Window parent, Integer inIdBitacora, boolean inModificar) {
        super(parent, DEFAULT_MODALITY_TYPE);

        BitacoraCRUD bCrud = new BitacoraCRUD();
        obitacora = bCrud.read(inIdBitacora);
        modificar = inModificar;
        leerIdbitacora = false;

        initComponents();
        myInitComponents();
    }

    @Override
    public void setVisible(boolean b) {

        if (!leerIdbitacora) {
            leerIdbitacora = true;

            if (obitacora.isPresent()) {

                Bitacora bitacora = obitacora.get();

                // buscamos en la base de datos el cliente asociado a la bitacora
                ClienteCRUD cCrud = new ClienteCRUD();
                Optional<Cliente> oc = cCrud.read(bitacora.getIdcliente());
                if (oc.isPresent()) {
                    cmbCliente.setSelectedItem(oc.get());
                } else {
                    cmbCliente.setSelectedIndex(-1);
                }

                // buscamos en la base de datos el servicio asociado a la bitacora
                ServicioCRUD sCrud = new ServicioCRUD();
                Optional<Servicio> os = sCrud.read(bitacora.getIdservicio());
                if (os.isPresent()) {
                    cmbServicio.setSelectedItem(os.get());
                } else {
                    cmbServicio.setSelectedIndex(-1);
                }

                txtTexto.setText(obitacora.get().getTexto());

            } else {
                cmbCliente.setSelectedIndex(-1);
                cmbServicio.setSelectedIndex(-1);
                txtTexto.setText("");
            }

        }

        super.setVisible(b);
    }

    public void myInitComponents() {

        // Esto se utiliza mas adelante, para pasar esta Win como parametro en el "windowClosing"
        final java.awt.Window me = this;

        // Creación del listener, qie sera ejecutado una vez que se quiera cerrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    ActionExit(me);
                });
            }
        });

        cmbCliente.setEnabled(modificar);
        cmbServicio.setEnabled(modificar);
        txtTexto.setEnabled(modificar);

        btnGuardar.setEnabled(modificar);

        ClienteCRUD cCrud = new ClienteCRUD();
        cmbCliente.setModel(new DefaultComboBoxModel(cCrud.findAll().toArray()));

        ServicioCRUD sCrud = new ServicioCRUD();
        cmbServicio.setModel(new DefaultComboBoxModel(sCrud.findAll().toArray()));

    }

    private void ActionExit(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        } else {
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbCliente = new javax.swing.JComboBox<>();
        cmbServicio = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTexto = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Servicio");

        jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jLabel1.setText("Servicio");

        btnGuardar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jLabel6.setText("Cliente");

        jLabel2.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jLabel2.setText("Servicio");

        cmbCliente.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        cmbCliente.setMaximumSize(new java.awt.Dimension(500, 32767));
        cmbCliente.setPreferredSize(new java.awt.Dimension(500, 32));

        cmbServicio.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        cmbServicio.setMaximumSize(new java.awt.Dimension(500, 32767));
        cmbServicio.setPreferredSize(new java.awt.Dimension(500, 32));

        jLabel3.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jLabel3.setText("Bitacora");

        txtTexto.setColumns(20);
        txtTexto.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        txtTexto.setRows(5);
        txtTexto.setMaximumSize(new java.awt.Dimension(500, 2147483647));
        txtTexto.setPreferredSize(new java.awt.Dimension(500, 134));
        jScrollPane1.setViewportView(txtTexto);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jLabel2)))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(cmbServicio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbCliente, 0, 514, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(38, 38, 38)
                .addComponent(btnCancelar)
                .addGap(191, 191, 191))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(321, 321, 321))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addGap(25, 25, 25))
        );

        setSize(new java.awt.Dimension(741, 515));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        ActionExit(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if (!obitacora.isPresent()) {
            btnGuardar.setEnabled(false);
            return;
        }

        if (cmbCliente.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Indique el cliente");
            cmbCliente.requestFocus();
            return;
        }

        Cliente c = (Cliente) cmbCliente.getSelectedItem();

        if (cmbServicio.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Indique el servicio");
            cmbServicio.requestFocus();
            return;
        }

        Servicio s = (Servicio) cmbServicio.getSelectedItem();

        String texto = txtTexto.getText().trim().toUpperCase();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Indique la bitacora");
            txtTexto.requestFocus();
            return;
        }

        BitacoraCRUD bCrud = new BitacoraCRUD();

        Bitacora b = new Bitacora(obitacora.get().getIdbitacora(), c.getIdcliente(), s.getIdServicio(), texto);
        Optional<Bitacora> ob = bCrud.update(b);

        if (!ob.isPresent()) {
            JOptionPane.showMessageDialog(null, "Bitacora no actualizada");
            cmbCliente.requestFocus();
            return;
        }

        JOptionPane.showMessageDialog(this, "Bitacora actualizada con éxito");

        ActionExit(this);
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new BitacoraModificar(null, 1, false).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbCliente;
    private javax.swing.JComboBox<String> cmbServicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtTexto;
    // End of variables declaration//GEN-END:variables
}
