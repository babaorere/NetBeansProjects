/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.bancos;

import com.principal.capipsistema.FrmPrincipal;
import com.principal.capipsistema.Globales;
import com.principal.capipsistema.Propiedades;
import static com.principal.capipsistema.Propiedades.getDocumentFilter;
import static com.principal.capipsistema.Propiedades.setColStrLen;
import com.principal.capipsistema.UserPassIn;
import com.principal.capipsistema.UserTrack;
import com.principal.connection.ConnCapip;
import com.principal.modelos.AvanceEfectivoModel;
import com.principal.modelos.BancosChAnuModel;
import com.principal.modelos.PagAvaInterface;
import com.principal.modelos.ParamRptPagAva;
import com.principal.modelos.UserModel;
import com.principal.pagos.FondoAvanceSinImp;
import com.principal.utils.Num2Let;
import java.awt.Toolkit;
import java.io.InputStream;
import static java.lang.Integer.min;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la selección de reportes
 *
 * @author Capip Sistemas C.A.
 */
public final class BancosChequeAvanceCambiar extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Mantiene el número de orden
     */
    private final long numOrden;

    private final java.awt.Window parent;

    /**
     * Formulario para la selección de reportes
     *
     * @param inparent
     * @param innumOrden
     */
    public BancosChequeAvanceCambiar(final java.awt.Window inparent, final long innumOrden) {
        super();
        initComponents();
        setTitle(getTitle() + " - " + Propiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        parent = inparent;
        numOrden = innumOrden;
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                btnSalir.doClick();
            }
        });
        try {
            UpdateFields();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Rev /10/2016
     */
    private void setCompBehavior() {
    }

    /**
     * Rev /10/2016
     */
    private void setOwnBehavior() {
        final int MAXLEN_AE_NVO = Propiedades.getColLen("avance_efectivo", "cheque");
        final int MAXLEN_BO_NVO = Propiedades.getColLen("bancos_operaciones", "soporte_o_cheque");
        final int MAXLEN_BCA_MVO = Propiedades.getColLen("bancos_ch_anu", "motivo_anu");
        int auxLen = min(MAXLEN_AE_NVO, MAXLEN_BO_NVO);
        txtNumAnu.setColumns(auxLen);
        txtNumNvo.setColumns(auxLen);
        // Crear un DocumentFilter para limitar la longitud del texto
        AbstractDocument documentAnu = (AbstractDocument) txtNumAnu.getDocument();
        documentAnu.setDocumentFilter(getDocumentFilter(auxLen));
        AbstractDocument documentNvo = (AbstractDocument) txtNumNvo.getDocument();
        documentNvo.setDocumentFilter(getDocumentFilter(auxLen));
        AbstractDocument documentMvo = (AbstractDocument) txtMotivo.getDocument();
        documentMvo.setDocumentFilter(getDocumentFilter(MAXLEN_BCA_MVO));
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
    }

    /**
     * Para Reubicar la ventana al ser visualizada Rev 25/09/2016
     *
     * @param inb
     */
    @Override
    public void setVisible(boolean inb) {
        // Para mostrar la ventana en el tope de la pantalla
        if (inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }
        super.setVisible(inb);
    }

    /**
     * Busca el número de orden en la base de datos, y llena los campos
     *
     * @throws Exception
     */
    private void UpdateFields() throws Exception {
        if (numOrden <= 0) {
            return;
        }
        final String sql;
        final ResultSet rs;
        rs = ConnCapip.getInstance().executeQuery("SELECT * FROM avance_efectivo WHERE id=" + numOrden);
        if (rs.next()) {
            AvanceEfectivoModel reg = new AvanceEfectivoModel(rs);
            lblNumOrden.setText(String.valueOf(reg.getId()));
            txtNumAnu.setText(reg.getCheque());
            txtNumNvo.requestFocusInWindow();
        } else {
            JOptionPane.showMessageDialog(null, "No existe ese número de orden: " + numOrden);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNumOrden = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNumAnu = new javax.swing.JTextField();
        txtNumNvo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMotivo = new javax.swing.JTextArea();
        pnlCommand = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("CAMBIO DE NÚMERO DE CHEQUE");
        setMinimumSize(new java.awt.Dimension(500, 200));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText(" NÚMERO DE ORDEN ");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setMaximumSize(new java.awt.Dimension(185, 20));
        jLabel1.setMinimumSize(new java.awt.Dimension(185, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(185, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jLabel1, gridBagConstraints);
        // NOI18N
        lblNumOrden.setFont(new java.awt.Font("Arial", 1, 14));
        lblNumOrden.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumOrden.setMaximumSize(new java.awt.Dimension(100, 20));
        lblNumOrden.setMinimumSize(new java.awt.Dimension(100, 20));
        lblNumOrden.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(lblNumOrden, gridBagConstraints);
        jPanel1.setMaximumSize(new java.awt.Dimension(450, 250));
        jPanel1.setMinimumSize(new java.awt.Dimension(450, 250));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(450, 250));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        // NOI18N
        jLabel3.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText(" Núm. Cheque Anterior ");
        jLabel3.setMaximumSize(new java.awt.Dimension(190, 25));
        jLabel3.setMinimumSize(new java.awt.Dimension(190, 25));
        jLabel3.setPreferredSize(new java.awt.Dimension(190, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);
        // NOI18N
        jLabel4.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText(" Núm. Cheque Nuevo ");
        jLabel4.setMaximumSize(new java.awt.Dimension(190, 25));
        jLabel4.setMinimumSize(new java.awt.Dimension(190, 25));
        jLabel4.setPreferredSize(new java.awt.Dimension(190, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);
        // NOI18N
        jLabel5.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText(" Motivo del Cambio ");
        jLabel5.setMaximumSize(new java.awt.Dimension(190, 25));
        jLabel5.setMinimumSize(new java.awt.Dimension(190, 25));
        jLabel5.setPreferredSize(new java.awt.Dimension(190, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel5, gridBagConstraints);
        // NOI18N
        txtNumAnu.setFont(new java.awt.Font("Arial", 0, 14));
        txtNumAnu.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumAnu.setEnabled(false);
        txtNumAnu.setMaximumSize(new java.awt.Dimension(150, 25));
        txtNumAnu.setMinimumSize(new java.awt.Dimension(150, 25));
        txtNumAnu.setPreferredSize(new java.awt.Dimension(150, 25));
        txtNumAnu.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumAnuKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(txtNumAnu, gridBagConstraints);
        // NOI18N
        txtNumNvo.setFont(new java.awt.Font("Arial", 3, 14));
        txtNumNvo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumNvo.setMaximumSize(new java.awt.Dimension(150, 25));
        txtNumNvo.setMinimumSize(new java.awt.Dimension(150, 25));
        txtNumNvo.setPreferredSize(new java.awt.Dimension(150, 25));
        txtNumNvo.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumNvoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(txtNumNvo, gridBagConstraints);
        // NOI18N
        jScrollPane1.setFont(new java.awt.Font("Arial", 0, 14));
        jScrollPane1.setMaximumSize(new java.awt.Dimension(250, 100));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(250, 100));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(250, 100));
        txtMotivo.setColumns(20);
        // NOI18N
        txtMotivo.setFont(new java.awt.Font("Arial", 3, 14));
        txtMotivo.setRows(5);
        txtMotivo.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMotivoKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtMotivo);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel2.add(jPanel1, gridBagConstraints);
        pnlCommand.setOpaque(false);
        pnlCommand.setLayout(new java.awt.GridBagLayout());
        btnGuardar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnGuardar.setFont(new java.awt.Font("Arial", 2, 14));
        btnGuardar.setText(" GUARDAR ");
        btnGuardar.setAlignmentX(0.5F);
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.setMaximumSize(new java.awt.Dimension(100, 40));
        btnGuardar.setMinimumSize(new java.awt.Dimension(100, 40));
        btnGuardar.setPreferredSize(new java.awt.Dimension(100, 40));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        pnlCommand.add(btnGuardar, gridBagConstraints);
        btnSalir.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnSalir.setFont(new java.awt.Font("Arial", 2, 14));
        btnSalir.setText("SALIR");
        btnSalir.setAlignmentX(0.5F);
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSalir.setMaximumSize(new java.awt.Dimension(100, 40));
        btnSalir.setMinimumSize(new java.awt.Dimension(100, 40));
        btnSalir.setPreferredSize(new java.awt.Dimension(100, 40));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        pnlCommand.add(btnSalir, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        jPanel2.add(pnlCommand, gridBagConstraints);
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 490, 360));
        // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png")));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 0, 530, 390));
        pack();
        setLocationRelativeTo(null);
    }

    // </editor-fold>//GEN-END:initComponents
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnSalirActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (parent != null) {
                    parent.setVisible(true);
                } else {
                    FrmPrincipal.getInstance().setVisible(true);
                }
            }
        });
        setVisible(false);
        dispose();
    }

    //GEN-LAST:event_btnSalirActionPerformed
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnGuardarActionPerformed
        if (numOrden <= 0) {
            return;
        }
        final String sNumAnt = txtNumAnu.getText().trim().toUpperCase();
        if (sNumAnt.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Número de cheque anterior inválido");
            return;
        }
        final String sNumNuevo = txtNumNvo.getText().trim().toUpperCase();
        if (sNumNuevo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Número de cheque nuevo inválido");
            txtNumNvo.requestFocusInWindow();
            return;
        }
        final String sMotivo = txtMotivo.getText().trim().toUpperCase();
        if (sMotivo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Motivo inválido");
            txtMotivo.requestFocusInWindow();
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "¿ Esta absolutamente seguro(a) de actualizar el número de cheque ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            ConnCapip.getInstance().BeginTransaction();
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM avance_efectivo WHERE id=" + numOrden + " FOR UPDATE");
            final AvanceEfectivoModel regAva;
            if (rs.next()) {
                regAva = new AvanceEfectivoModel(rs);
            } else {
                throw new Exception("No existe ese número de orden: " + numOrden);
            }
            final PreparedStatement psOrdPag = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE avance_efectivo set cheque= ? WHERE id= ?");
            psOrdPag.setString(1, setColStrLen("avance_efectivo", "cheque", sNumNuevo));
            psOrdPag.setLong(2, numOrden);
            if (psOrdPag.executeUpdate() != 1) {
                throw new Exception("Orden de pago, no actualizada");
            }
            final PreparedStatement psBanDet = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE bancos_operaciones set soporte_o_cheque= ? WHERE tipo_operacion= 'FA' AND num_pag_ava= ? AND anulado_sn= 'N'");
            psBanDet.setString(1, setColStrLen("bancos_operaciones", "soporte_o_cheque", sNumNuevo));
            psBanDet.setLong(2, numOrden);
            if (psBanDet.executeUpdate() != 1) {
                throw new Exception("Registro no actualizado");
            }
            final Calendar fechaOp = Calendar.getInstance();
            final java.sql.Date fchAnulacion = new java.sql.Date(fechaOp.getTimeInMillis());
            final long ejeFis = fechaOp.get(Calendar.YEAR);
            final long ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;
            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO bancos_ch_anu(banco,cuenta,cheque_anu,cheque_nvo,monto,pag_ava,num_pag_ava,motivo_anu,fecha_anu,ejefis,ejefismes,iduser) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, setColStrLen("bancos_ch_anu", "banco,cuenta", regAva.getBanco()));
            pst.setString(2, setColStrLen("bancos_ch_anu", "cuenta", regAva.getCuenta()));
            pst.setString(3, setColStrLen("bancos_ch_anu", "cheque_anu", sNumAnt));
            pst.setString(4, setColStrLen("bancos_ch_anu", "cheque_nvo", sNumNuevo));
            pst.setString(5, setColStrLen("bancos_ch_anu", "monto", regAva.getA_pagar_bs().toPlainString()));
            pst.setString(6, "A");
            pst.setLong(7, regAva.getId());
            pst.setString(8, setColStrLen("bancos_ch_anu", "motivo_anu", sMotivo));
            pst.setDate(9, fchAnulacion);
            pst.setLong(10, ejeFis);
            pst.setLong(11, ejeFisMes);
            pst.setLong(12, UserPassIn.getIdUser());
            pst.executeUpdate();
            final Integer idChAnu;
            final ResultSet rsId = ConnCapip.getInstance().getConnection().prepareStatement("SELECT last_insert_id() AS last_id FROM bancos_ch_anu").executeQuery();
            if (rsId.next()) {
                idChAnu = rsId.getInt("last_id");
            } else {
                idChAnu = 0;
            }
            final BancosChAnuModel regChAnu;
            final ResultSet rsRegAnu = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM bancos_ch_anu WHERE id_ch_anu=" + idChAnu).executeQuery();
            if (rsRegAnu.next()) {
                regChAnu = new BancosChAnuModel(rsRegAnu);
            } else {
                regChAnu = null;
            }
            rptPlanillaAnuAva(regAva, regChAnu);
            ParamRptPagAva genParamRptAva = FondoAvanceSinImp.genParamRptAva(numOrden);
            FondoAvanceSinImp.rptOrdAva(FondoAvanceSinImp.genParamRptAva(numOrden), true);
            FondoAvanceSinImp.rptChAva(regAva.getId(), genParamRptAva);
            ConnCapip.getInstance().EndTransaction();
            JOptionPane.showMessageDialog(null, "Cambio realizado");
            btnSalir.doClick();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            // En caso de cualquier error, se devuelve la operacion
            try {
                ConnCapip.getInstance().RollBack();
            } catch (final Exception inex1) {
                JOptionPane.showMessageDialog(null, inex1);
                logger.error(inex1);
            }
            logger.error(inex);
        }
    }

    //GEN-LAST:event_btnGuardarActionPerformed
    @SuppressWarnings({ "unchecked" })
    private void rptPlanillaAnuAva(final PagAvaInterface inregPA, final BancosChAnuModel inregAnu) throws Exception {
        final Map<String, Object> param = new HashMap<>(101);
        param.put("id_ch_anu", inregAnu.getIdChAnu());
        final String NumLiteral = Num2Let.convert(new BigDecimal(inregAnu.getMonto().replace(".", "").replace(",", ".")).setScale(2, RoundingMode.HALF_UP));
        final String NumLiteral_1;
        final String NumLiteral_2;
        if (NumLiteral.length() < 65) {
            NumLiteral_1 = NumLiteral;
            NumLiteral_2 = "";
        } else {
            int pos = NumLiteral.lastIndexOf(" CON ");
            final String s1 = NumLiteral.substring(0, pos);
            if (s1.length() > 65) {
                pos = s1.lastIndexOf(" ") + 1;
            }
            NumLiteral_1 = NumLiteral.substring(0, pos).trim();
            NumLiteral_2 = NumLiteral.substring(pos).trim();
        }
        param.put("NumLiteral_1", NumLiteral_1);
        param.put("NumLiteral_2", NumLiteral_2);
        param.put("fecha", inregAnu.getFechaAnu());
        param.put("razonsocial", inregPA.getBenef_razonsocial());
        param.put("rif", inregPA.getBenef_rif_ci());
        param.put("idsession", UserPassIn.getIdSession());
        param.put("ejefis", Globales.getEjeFisYear());
        param.put("iduser", UserPassIn.getIdUser());
        param.put("user", UserPassIn.getIdUser() <= 0 ? "DEBUG" : UserModel.getUser(UserPassIn.getIdUser()));
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        param.put("motivo_anu", inregAnu.getMotivoAnu());
        param.put("monto", inregAnu.getMonto());
        param.put("pag_ava", inregAnu.getPagAva().equals("P") ? "Pago" : "Avance");
        param.put("aux_1", Propiedades.CAPIP_AUX_1);
        param.put("aux_2", Propiedades.CAPIP_AUX_2);
        param.put("linea_1", Propiedades.CAPIP_LINEA_1);
        param.put("linea_2", Propiedades.CAPIP_LINEA_2);
        param.put("linea_3", Propiedades.CAPIP_LINEA_3);
        param.put("linea_4", Propiedades.CAPIP_LINEA_4);
        param.put("desc_1", Propiedades.CAPIP_DESC_1);
        param.put("desc_2", Propiedades.CAPIP_DESC_2);
        param.put("desc_3", Propiedades.CAPIP_DESC_3);
        param.put("desc_4", Propiedades.CAPIP_DESC_4);
        param.put("desc_5", Propiedades.CAPIP_DESC_5);
        param.put("desc_6", Propiedades.CAPIP_DESC_6);
        param.put("desc_7", Propiedades.CAPIP_DESC_7);
        param.put("desc_8", Propiedades.CAPIP_DESC_8);
        param.put("func_1", Propiedades.CAPIP_FUNC_1);
        param.put("func_2", Propiedades.CAPIP_FUNC_2);
        param.put("func_3", Propiedades.CAPIP_FUNC_3);
        param.put("func_4", Propiedades.CAPIP_FUNC_4);
        param.put("func_5", Propiedades.CAPIP_FUNC_5);
        param.put("func_6", Propiedades.CAPIP_FUNC_6);
        param.put("func_7", Propiedades.CAPIP_FUNC_7);
        param.put("func_8", Propiedades.CAPIP_FUNC_8);
        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));
        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/bancos_ch_anu.jasper");
        if (pathRpt != null) {
            new Thread() {

                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport(pathRpt, new HashMap<>(param), cn), false);
                    } catch (final Exception inex) {
                        JOptionPane.showMessageDialog(null, inex);
                        logger.error(inex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Planilla no encontrada");
        }
    }

    /**
     * Asegurarse que no se sobrepase la longitud del campo en la base de datos
     *
     * @param evt
     */
    private void txtNumNvoKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtNumNvoKeyTyped
        if (txtNumNvo.getText().length() >= 20) {
            evt.consume();
        }
    }

    //GEN-LAST:event_txtNumNvoKeyTyped
    private void txtNumAnuKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtNumAnuKeyTyped
        if (txtNumAnu.getText().length() >= 20) {
            evt.consume();
        }
    }

    //GEN-LAST:event_txtNumAnuKeyTyped
    private void txtMotivoKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtMotivoKeyTyped
        if (txtMotivo.getText().length() >= 128) {
            evt.consume();
        }
    }

    //GEN-LAST:event_txtMotivoKeyTyped
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new BancosChequeAvanceCambiar(null, 1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;

    private javax.swing.JButton btnSalir;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel2;

    private javax.swing.JLabel jLabel3;

    private javax.swing.JLabel jLabel4;

    private javax.swing.JLabel jLabel5;

    private javax.swing.JPanel jPanel1;

    private javax.swing.JPanel jPanel2;

    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JLabel lblNumOrden;

    private javax.swing.JPanel pnlCommand;

    private javax.swing.JTextArea txtMotivo;

    private javax.swing.JTextField txtNumAnu;

    private javax.swing.JTextField txtNumNvo;

    // End of variables declaration//GEN-END:variables
    private final Connection cn = ConnCapip.getInstance().getConnection();

    private static final Logger logger = LogManager.getLogger(BancosChequeAvanceCambiar.class);
}
