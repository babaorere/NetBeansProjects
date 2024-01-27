/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import capipsistema.CapipPropiedades;
import connection.ConnCapip;
import capipsistema.Globales;
import static capipsistema.Globales.TBL_PPTO_EGRESO;
import static capipsistema.Globales.TBL_PPTO_INGRESO;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.PptoModel;
import utils.Format;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class TraspPartidas extends XJFrame {

    private static final int colCod = 0;
    private static final int colPartida = 1;
    private static final int colMontoIni = 2;
    private static final int colMontoAct = 3;
    private static final int colMontoTrasp = 4;
    private static final int colRegPpto = 5;
    private static final int colRegTraspDet = 6;

    private boolean recoverIdOri;
    private boolean recoverIdDest;

    private String strTblOri;
    private String strTblDest;

    /**
     * Creates new form transferencia
     *
     * @param inparent
     */
    public TraspPartidas(final JFrame inparent) {
        super( inparent);
        initComponents();

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada
     *
     * @param inb
     */
    @Override
    public void setVisible(boolean inb) {
        // Para mostrar la ventana en el tope de la pantalla
        if ( inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }

        super.setVisible( inb);
    }

    /**
     * Establece el comportamiento de la presente Ventana
     * Rev 21/09/2016
     *
     */
    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        setTitle(CapipPropiedades.CAPIP_SISTEMAS + " - " + getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        // Establecer acción al cerrar ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Salir();
            }
        });

        // Para salir con la tecla ESC
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");

        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Salir();
            }
        });

    }

    private void setCompBehavior() {
        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, jbRetornar);
        ConfigComponents(param);
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {

        // Hay que verificar la existencia de datos cargados anteriormente
        final Calendar fechaOp = Calendar.getInstance();

        // Ajustar a la fecha del servidor
        fechaOp.setTimeInMillis(Globales.getServerSqlDate().getTime());

        // se le resta 7 dias (una semana), para borrar toda la data anterior, 
        // y evitar la acumulacion perenne
        fechaOp.add(Calendar.DAY_OF_MONTH, -7);

        // Formatear la fecha
        final String sFchDelete = (new java.sql.Date(fechaOp.getTimeInMillis())).toString();

        try {
            // Borrar de la tabla, todos los datos que tengan id_trasp= 0, es decir, en proceso de carga o creacion, 
            // y que sean demasiado viejos.
            // Esto permite que un usuario pueda recuperar los datos guardadoes en una sesion anterior.
            ConnCapip.getInstance().getConnection().prepareStatement("DELETE FROM trasppartidas_det WHERE id_trasp= 0 AND DATE(date_session) <= '" + sFchDelete + "'").executeUpdate();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, inex);
        }

        // Verificar si hay datos cargados para el usuario en curso
        try {
            final ResultSet rsNext = ConnPpto.executeQuery("SELECT COUNT(*) FROM trasppartidas_det WHERE id_trasp= 0 AND id_user= " + UserPassIn.getIdUser() + "");
            if (rsNext.next() && (rsNext.getInt(1) > 0)) {
                if (JOptionPane.showConfirmDialog(this, "Existen registros cargados en la Base de Datos" + System.getProperty("line.separator")
                    + " ¿ Desea eliminar estos registros ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    ConnPpto.execute("DELETE FROM trasppartidas_det WHERE id_trasp= 0 AND id_user= " + UserPassIn.getIdUser() + "");
                }
            }

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, inex);
        }

        InitConditions();
        UpdatePanels();
        UpdateEnabledAndFocus();
    }

    /**
     * Valores y/o condiciones iniciales de la ventana
     */
    @Override
    public void InitConditions() {
        super.InitConditions();

        strTblOri = TBL_PPTO_EGRESO;
        strTblDest = TBL_PPTO_EGRESO;

        recoverIdOri = false;
        recoverIdDest = false;

        ClearPanels();
    }

    @Override
    protected void ConfigComponents(final Map<String, Object> inparam) {
        super.ConfigComponents( inparam);

        // Configurar el formato de la fecha
        txtFechaTrasp.setFormatterFactory(PptoGlobales.getDateFormatterFactory());

        // Configurar el campo de Monto, para que acepte y maneje BigDecimal
        txtMontoTrasp.setFormatterFactory(PptoGlobales.getCurFormatterFactory());

        // Establecer el boton por defecto
        getRootPane().setDefaultButton(null);
    }

    /**
     * Rev 05-02-2017
     *
     */
    @Override
    protected void UpdatePanels() {

        // Actualizar tabla origen
        // Actualizar monto total de la tabla origen
        BigDecimal totalOri = ZERO;

        // Para evitar multiples llamadas al metodo valueChanged
        tblOrigen.clearSelection();

        // Eliminar los elementos existentes, manteniendo el mismo modelo
        final DefaultTableModel modelOri = (DefaultTableModel) tblOrigen.getModel();
        modelOri.getDataVector().removeAllElements();
        modelOri.fireTableDataChanged();

        final String ppto_egr_ing = btnPptoEgresoOrg.isSelected() ? "E" : "I";

        try {
            final Object[] datos = new Object[7];
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + strTblOri + " WHERE ejefis= " + Globales.getEjeFisYear() + " AND anulado_sn= 'N' ORDER BY codigo ASC");
            while (rs.next()) {
                // Obtener el registro del presupuesto
                PptoModel regPpto = new PptoModel(rs);

                // Verificar si en la tabla de detalle existe datos cargados, 
                // para la respectiva partida de presupuesto
                final ResultSet rsTraspDet = ConnPpto.executeQuery("SELECT * FROM trasppartidas_det WHERE id_trasp= 0 AND id_user= " + UserPassIn.getIdUser() + " AND tipo_ori_dest= 'O' AND ppto_egr_ing= '" + ppto_egr_ing + "' AND codpresu= '" + regPpto.getCodigo() + "'");
                if (rsTraspDet.next()) {
                    TblTraspDet regTraspDet = new TblTraspDet(rsTraspDet);
                    final BigDecimal montoTrasp = regTraspDet.getMonto();

                    datos[colCod] = regPpto.getCodigo();
                    datos[colPartida] = regPpto.getPartida();
                    datos[colMontoIni] = regPpto.getMontoIni().doubleValue();
                    datos[colMontoAct] = regPpto.getMonto().doubleValue();
                    datos[colMontoTrasp] = montoTrasp.doubleValue();
                    datos[colRegPpto] = regPpto;
                    datos[colRegTraspDet] = regTraspDet;
                    modelOri.addRow(datos);

                    totalOri = totalOri.add(montoTrasp);
                }
            }

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de cargar la tabla Origen" + System.getProperty("line.separator") + inex);
        }

        txtMontoTotalOri.setText(Format.toStr(totalOri));
        txtMontoTrasp.setText(Format.toStr(totalOri));

        // Actualizar monto total de la tabla destino
        BigDecimal totalDest = ZERO;

        // Para evitar multiples llamadas al metodo valueChanged
        tblOrigen.clearSelection();

        // Eliminar los elementos existentes, manteniendo el mismo modelo
        final DefaultTableModel modelDest = (DefaultTableModel) tblDestino.getModel();
        modelDest.getDataVector().removeAllElements();
        modelDest.fireTableDataChanged();

        try {
            final Object[] datos = new Object[7];
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + strTblDest + " WHERE ejefis= " + Globales.getEjeFisYear() + " AND anulado_sn= 'N' ORDER BY codigo ASC");
            while (rs.next()) {
                // Obtener el registro del presupuesto
                PptoModel regPpto = new PptoModel(rs);

                // Verificar si en la tabla de detalle existe datos cargados, 
                // para la respectiva partida de presupuesto
                final ResultSet rsTraspDet = ConnPpto.executeQuery("SELECT * FROM trasppartidas_det WHERE id_trasp= 0 AND id_user= " + UserPassIn.getIdUser() + " AND tipo_ori_dest= 'D' AND codpresu= '" + regPpto.getCodigo() + "'");
                if (rsTraspDet.next()) {
                    TblTraspDet regTraspDet = new TblTraspDet(rsTraspDet);
                    final BigDecimal montoTrasp = regTraspDet.getMonto();

                    datos[colCod] = regPpto.getCodigo();
                    datos[colPartida] = regPpto.getPartida();
                    datos[colMontoIni] = regPpto.getMontoIni().doubleValue();
                    datos[colMontoAct] = regPpto.getMonto().doubleValue();
                    datos[colMontoTrasp] = montoTrasp.doubleValue();
                    datos[colRegPpto] = regPpto;
                    datos[colRegTraspDet] = regTraspDet;
                    modelDest.addRow(datos);

                    totalDest = totalDest.add(montoTrasp);
                }
            }

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de cargar la tabla Destino" + System.getProperty("line.separator") + inex);
        }

        txtMontoTotalDest.setText(Format.toStr(totalDest));

        // Actualizar el boton seleccionado
        if (tblOrigen.getRowCount() > 0) {
            //  Tomar el primer registro de la tabla y verificar el tipo del presupuesto
            TblTraspDet reg = (TblTraspDet) tblOrigen.getValueAt(0, colRegTraspDet);
            if (reg.getPpto_egr_ing().equals("E")) {
                btnPptoEgresoOrg.setSelected(true);
            } else {
                btnPptoIngresoOri.setSelected(true);
            }
        } else {
            btnPptoEgresoOrg.setSelected(true);
        }

    }

    private void UpdateEnabledAndFocus() {

        jtReferencia.setEditable(true);
        txtFechaTrasp.setEditable(true);
        txtMontoTrasp.setEditable(true);
        jtConcepto.setEditable(true);

        jbGenerar.setEnabled(true);
        jbConsultar.setEnabled(true);
        jbRetornar.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jbgOri = new javax.swing.ButtonGroup();
        jbgDest = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10));
        jpOrigen = new javax.swing.JPanel();
        jpTipoPpto = new javax.swing.JPanel();
        btnPptoEgresoOrg = new javax.swing.JToggleButton();
        btnPptoIngresoOri = new javax.swing.JToggleButton();
        jLabel5 = new javax.swing.JLabel();
        txtMontoTotalOri = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrigen = new javax.swing.JTable();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        jpDestino = new javax.swing.JPanel();
        jpTipoPpto1 = new javax.swing.JPanel();
        btnPptoGastoDest = new javax.swing.JToggleButton();
        jtbPptoIngresoDest = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        txtMontoTotalDest = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDestino = new javax.swing.JTable();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        jpDetalles = new javax.swing.JPanel();
        jtConcepto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtReferencia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtFechaTrasp = new javax.swing.JFormattedTextField();
        txtMontoTrasp = new javax.swing.JFormattedTextField();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jpCmdFoot = new javax.swing.JPanel();
        jbGenerar = new javax.swing.JButton();
        jbConsultar = new javax.swing.JButton();
        jbRetornar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("TRASPASO DE PARTIDAS");
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        getContentPane().setLayout(null);

        jpFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setOpaque(false);
        jpFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        jpFondo.setLayout(new javax.swing.BoxLayout(jpFondo, javax.swing.BoxLayout.PAGE_AXIS));
        jpFondo.add(filler4);

        jpOrigen.setBorder(javax.swing.BorderFactory.createTitledBorder(" PARTIDA ORIGEN "));
        jpOrigen.setMaximumSize(new java.awt.Dimension(1060, 170));
        jpOrigen.setMinimumSize(new java.awt.Dimension(1060, 170));
        jpOrigen.setOpaque(false);
        jpOrigen.setPreferredSize(new java.awt.Dimension(1060, 170));
        jpOrigen.setLayout(new javax.swing.BoxLayout(jpOrigen, javax.swing.BoxLayout.PAGE_AXIS));

        jpTipoPpto.setMaximumSize(new java.awt.Dimension(1060, 50));
        jpTipoPpto.setMinimumSize(new java.awt.Dimension(1060, 50));
        jpTipoPpto.setOpaque(false);
        jpTipoPpto.setPreferredSize(new java.awt.Dimension(1060, 50));
        jpTipoPpto.setLayout(new java.awt.GridBagLayout());

        btnPptoEgresoOrg.setBackground(java.awt.SystemColor.inactiveCaption);
        jbgOri.add(btnPptoEgresoOrg);
        btnPptoEgresoOrg.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        btnPptoEgresoOrg.setSelected(true);
        btnPptoEgresoOrg.setText("PRESUPUESTO DE GASTO");
        btnPptoEgresoOrg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPptoEgresoOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPptoEgresoOrgActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        jpTipoPpto.add(btnPptoEgresoOrg, gridBagConstraints);

        btnPptoIngresoOri.setBackground(java.awt.SystemColor.inactiveCaption);
        jbgOri.add(btnPptoIngresoOri);
        btnPptoIngresoOri.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        btnPptoIngresoOri.setText("PRESUPUESTO DE INGRESO");
        btnPptoIngresoOri.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPptoIngresoOri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPptoIngresoOriActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 100, 0, 0);
        jpTipoPpto.add(btnPptoIngresoOri, gridBagConstraints);

        jLabel5.setText("Monto Total Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 100, 0, 0);
        jpTipoPpto.add(jLabel5, gridBagConstraints);

        txtMontoTotalOri.setBackground(new java.awt.Color(175, 204, 125));
        txtMontoTotalOri.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtMontoTotalOri.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoTotalOri.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMontoTotalOri.setEnabled(false);
        txtMontoTotalOri.setMaximumSize(new java.awt.Dimension(150, 25));
        txtMontoTotalOri.setMinimumSize(new java.awt.Dimension(150, 25));
        txtMontoTotalOri.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpTipoPpto.add(txtMontoTotalOri, gridBagConstraints);

        jpOrigen.add(jpTipoPpto);

        tblOrigen.setFont(new java.awt.Font("Arial", 3, 15)); // NOI18N
        tblOrigen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Partida", "Monto Ini. Bs.", "Monto Disp Bs.", "Traspaso Bs.", "RegPpto", "REgTraspDet"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrigen.setSelectionBackground(new java.awt.Color(175, 204, 125));
        jScrollPane1.setViewportView(tblOrigen);
        if (tblOrigen.getColumnModel().getColumnCount() > 0) {
            tblOrigen.getColumnModel().getColumn(0).setMinWidth(75);
            tblOrigen.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblOrigen.getColumnModel().getColumn(0).setMaxWidth(300);
            tblOrigen.getColumnModel().getColumn(2).setMinWidth(75);
            tblOrigen.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblOrigen.getColumnModel().getColumn(2).setMaxWidth(300);
            tblOrigen.getColumnModel().getColumn(3).setMinWidth(75);
            tblOrigen.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblOrigen.getColumnModel().getColumn(3).setMaxWidth(300);
            tblOrigen.getColumnModel().getColumn(4).setMinWidth(75);
            tblOrigen.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblOrigen.getColumnModel().getColumn(4).setMaxWidth(300);
            tblOrigen.getColumnModel().getColumn(5).setMinWidth(0);
            tblOrigen.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblOrigen.getColumnModel().getColumn(5).setMaxWidth(0);
            tblOrigen.getColumnModel().getColumn(6).setMinWidth(0);
            tblOrigen.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblOrigen.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        jpOrigen.add(jScrollPane1);

        jpFondo.add(jpOrigen);
        jpFondo.add(filler2);

        jpDestino.setBorder(javax.swing.BorderFactory.createTitledBorder(" PARTIDA DESTINO "));
        jpDestino.setMaximumSize(new java.awt.Dimension(1060, 170));
        jpDestino.setMinimumSize(new java.awt.Dimension(1060, 170));
        jpDestino.setOpaque(false);
        jpDestino.setPreferredSize(new java.awt.Dimension(1060, 170));
        jpDestino.setLayout(new javax.swing.BoxLayout(jpDestino, javax.swing.BoxLayout.PAGE_AXIS));

        jpTipoPpto1.setMaximumSize(new java.awt.Dimension(1060, 50));
        jpTipoPpto1.setMinimumSize(new java.awt.Dimension(1060, 50));
        jpTipoPpto1.setOpaque(false);
        jpTipoPpto1.setPreferredSize(new java.awt.Dimension(1060, 50));
        jpTipoPpto1.setLayout(new java.awt.GridBagLayout());

        btnPptoGastoDest.setBackground(java.awt.SystemColor.inactiveCaption);
        jbgDest.add(btnPptoGastoDest);
        btnPptoGastoDest.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        btnPptoGastoDest.setSelected(true);
        btnPptoGastoDest.setText("PRESUPUESTO DE GASTO");
        btnPptoGastoDest.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPptoGastoDest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPptoGastoDestActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        jpTipoPpto1.add(btnPptoGastoDest, gridBagConstraints);

        jtbPptoIngresoDest.setBackground(java.awt.SystemColor.inactiveCaption);
        jbgDest.add(jtbPptoIngresoDest);
        jtbPptoIngresoDest.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jtbPptoIngresoDest.setText("PRESUPUESTO DE INGRESO");
        jtbPptoIngresoDest.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtbPptoIngresoDest.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 100, 0, 0);
        jpTipoPpto1.add(jtbPptoIngresoDest, gridBagConstraints);

        jLabel1.setText("Monto Total Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 100, 0, 0);
        jpTipoPpto1.add(jLabel1, gridBagConstraints);

        txtMontoTotalDest.setBackground(new java.awt.Color(175, 204, 125));
        txtMontoTotalDest.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtMontoTotalDest.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoTotalDest.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMontoTotalDest.setEnabled(false);
        txtMontoTotalDest.setMaximumSize(new java.awt.Dimension(150, 25));
        txtMontoTotalDest.setMinimumSize(new java.awt.Dimension(150, 25));
        txtMontoTotalDest.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpTipoPpto1.add(txtMontoTotalDest, gridBagConstraints);

        jpDestino.add(jpTipoPpto1);

        tblDestino.setFont(new java.awt.Font("Arial", 3, 15)); // NOI18N
        tblDestino.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Partida", "Monto Ini. Bs.", "Monto Disp. Bs.", "Traspaso Bs.", "RegPpto", "RegTraspDet"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDestino.setSelectionBackground(new java.awt.Color(175, 204, 125));
        jScrollPane2.setViewportView(tblDestino);
        if (tblDestino.getColumnModel().getColumnCount() > 0) {
            tblDestino.getColumnModel().getColumn(0).setMinWidth(75);
            tblDestino.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblDestino.getColumnModel().getColumn(0).setMaxWidth(300);
            tblDestino.getColumnModel().getColumn(2).setMinWidth(75);
            tblDestino.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblDestino.getColumnModel().getColumn(2).setMaxWidth(300);
            tblDestino.getColumnModel().getColumn(3).setMinWidth(75);
            tblDestino.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblDestino.getColumnModel().getColumn(3).setMaxWidth(300);
            tblDestino.getColumnModel().getColumn(4).setMinWidth(75);
            tblDestino.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblDestino.getColumnModel().getColumn(4).setMaxWidth(300);
            tblDestino.getColumnModel().getColumn(5).setMinWidth(0);
            tblDestino.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblDestino.getColumnModel().getColumn(5).setMaxWidth(0);
            tblDestino.getColumnModel().getColumn(6).setMinWidth(0);
            tblDestino.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblDestino.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        jpDestino.add(jScrollPane2);

        jpFondo.add(jpDestino);
        jpFondo.add(filler1);

        jpDetalles.setBorder(javax.swing.BorderFactory.createTitledBorder(" DETALLES DEL TRASPASO "));
        jpDetalles.setMaximumSize(new java.awt.Dimension(1060, 150));
        jpDetalles.setMinimumSize(new java.awt.Dimension(1060, 150));
        jpDetalles.setOpaque(false);
        jpDetalles.setPreferredSize(new java.awt.Dimension(1060, 150));
        jpDetalles.setLayout(new java.awt.GridBagLayout());

        jtConcepto.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        jtConcepto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtConcepto.setMaximumSize(new java.awt.Dimension(600, 21));
        jtConcepto.setMinimumSize(new java.awt.Dimension(600, 21));
        jtConcepto.setPreferredSize(new java.awt.Dimension(600, 21));
        jtConcepto.setSelectionColor(new java.awt.Color(175, 204, 125));
        jtConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtConceptoActionPerformed(evt);
            }
        });
        jtConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtConceptoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpDetalles.add(jtConcepto, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel2.setText("FECHA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpDetalles.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel3.setText("CONCEPTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpDetalles.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel4.setText("MONTO Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpDetalles.add(jLabel4, gridBagConstraints);

        jtReferencia.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        jtReferencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtReferencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtReferencia.setMaximumSize(new java.awt.Dimension(200, 21));
        jtReferencia.setMinimumSize(new java.awt.Dimension(200, 21));
        jtReferencia.setPreferredSize(new java.awt.Dimension(200, 21));
        jtReferencia.setSelectionColor(new java.awt.Color(175, 204, 125));
        jtReferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtReferenciaActionPerformed(evt);
            }
        });
        jtReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtReferenciaKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpDetalles.add(jtReferencia, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("REFERENCIA ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpDetalles.add(jLabel8, gridBagConstraints);

        txtFechaTrasp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFechaTrasp.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtFechaTrasp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaTrasp.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtFechaTrasp.setMaximumSize(new java.awt.Dimension(100, 21));
        txtFechaTrasp.setMinimumSize(new java.awt.Dimension(100, 21));
        txtFechaTrasp.setPreferredSize(new java.awt.Dimension(100, 21));
        txtFechaTrasp.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtFechaTrasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaTraspActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpDetalles.add(txtFechaTrasp, gridBagConstraints);

        txtMontoTrasp.setBackground(new java.awt.Color(175, 204, 125));
        txtMontoTrasp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMontoTrasp.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtMontoTrasp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoTrasp.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtMontoTrasp.setMaximumSize(new java.awt.Dimension(150, 21));
        txtMontoTrasp.setMinimumSize(new java.awt.Dimension(150, 21));
        txtMontoTrasp.setPreferredSize(new java.awt.Dimension(150, 21));
        txtMontoTrasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoTraspActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpDetalles.add(txtMontoTrasp, gridBagConstraints);

        jpFondo.add(jpDetalles);
        jpFondo.add(filler3);

        jpCmdFoot.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpCmdFoot.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpCmdFoot.setOpaque(false);
        jpCmdFoot.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpCmdFoot.setLayout(new java.awt.GridBagLayout());

        jbGenerar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbGenerar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbGenerar.setText("GENERAR");
        jbGenerar.setToolTipText("");
        jbGenerar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbGenerar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGenerarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdFoot.add(jbGenerar, gridBagConstraints);

        jbConsultar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbConsultar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbConsultar.setText("CONSULTAR");
        jbConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbConsultar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConsultarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdFoot.add(jbConsultar, gridBagConstraints);

        jbRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbRetornar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbRetornar.setText("RETORNAR");
        jbRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbRetornar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 600, 0, 0);
        jpCmdFoot.add(jbRetornar, gridBagConstraints);

        jpFondo.add(jpCmdFoot);

        getContentPane().add(jpFondo);
        jpFondo.setBounds(0, 0, 1100, 650);

        jlFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        jlFondo.setAlignmentX(0.5F);
        jlFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        getContentPane().add(jlFondo);
        jlFondo.setBounds(0, 0, 1100, 650);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGenerarActionPerformed

        // Realizar las Validaciones
        final String referencia = getReferencia();
        if (referencia == null) {
            JOptionPane.showMessageDialog(null, "Referencia inválida");
            jtReferencia.requestFocusInWindow();
            return;
        }

        final java.sql.Date fechaTrasp = getFecha();
        if (fechaTrasp == null) {
            JOptionPane.showMessageDialog(null, "Fecha inválida");
            txtFechaTrasp.requestFocusInWindow();
            return;
        }

        final BigDecimal montoTraspaso = getMonto();
        if ((montoTraspaso == null) || (montoTraspaso.compareTo(ZERO) <= 0)) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            txtMontoTrasp.requestFocusInWindow();
            return;
        }

        final String concepto = getConcepto();
        if ((concepto == null) || concepto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Concepto inválido");
            jtConcepto.requestFocusInWindow();
            return;
        }

        final BigDecimal montoPartOri;
        try {
            montoPartOri = Format.toBigDec(txtMontoTotalOri.getText());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al formatear monto origen" + System.getProperty("line.separator") + inex);
            return;
        }

        final BigDecimal montoPartDest;
        try {
            montoPartDest = Format.toBigDec(txtMontoTotalDest.getText());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al formatear monto destino" + System.getProperty("line.separator") + inex);
            return;
        }

        if (montoTraspaso.compareTo(montoPartOri) != 0) {
            JOptionPane.showMessageDialog(null, "Monto origen inválido, no coincide con el monto del traspaso");
            txtMontoTrasp.requestFocusInWindow();
            return;
        }

        if (montoTraspaso.compareTo(montoPartDest) != 0) {
            JOptionPane.showMessageDialog(null, "Monto destino inválido, no coincide con el monto del traspaso");
            txtMontoTrasp.requestFocusInWindow();
            return;
        }

        // Para recuperar el registro recien insertado
        long id_trasp = 0;
        try {
            ConnPpto.BeginTransaction();

            // Incluir el registro de traspaso
            final Calendar fechaOp = Calendar.getInstance();
            fechaOp.setTimeInMillis(Globales.getServerTimeStamp().getTime());
            final java.sql.Date date_session = new java.sql.Date(fechaOp.getTimeInMillis());
            final long ejeFis = fechaOp.get(Calendar.YEAR);
            final long ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;

            final PreparedStatement psTrasp = ConnPpto.conn.prepareStatement("INSERT INTO " + PptoGlobales.TBL_TRASP_PARTIDAS
                + "(id_user, id_session, date_session, " // 1, 2, 3,
                + "ppto_egr_ing, referencia, fecha, " // 4, 5, 6
                + "monto, concepto, ejefis, ejefismes) " // 7, 8, 9, 10
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            psTrasp.setLong(1, UserPassIn.getIdSession());
            psTrasp.setLong(2, UserPassIn.getIdUser());
            psTrasp.setDate(3, date_session);
            psTrasp.setString(4, btnPptoEgresoOrg.isSelected() ? "E" : "I");
            psTrasp.setString(5, referencia);
            psTrasp.setDate(6, new java.sql.Date(fechaTrasp.getTime()));
            psTrasp.setBigDecimal(7, montoTraspaso);
            psTrasp.setString(8, concepto);
            psTrasp.setLong(9, ejeFis);
            psTrasp.setLong(10, ejeFisMes);
            if (psTrasp.executeUpdate() != 1) {
                throw new Exception("Error al tratar de actualizar los datos");
            }

            // recuperar el id del registro recien creado
            ResultSet rsId = psTrasp.getGeneratedKeys();
            if (rsId.next()) {
                id_trasp = rsId.getInt(1);
            } else {
                throw new Exception("Error al tratar de actualizar los datos" + System.getProperty("line.separator") + "id_trasp inválido");
            }

            // actualizar el monto en las partidas del presupuesto origen
            for (int i = 0; i < tblOrigen.getRowCount(); i++) {
                final TblTraspDet regTraspDet = (TblTraspDet) tblOrigen.getValueAt(i, colRegTraspDet);
                final String strPpto = regTraspDet.getPpto_egr_ing().equals("E") ? Globales.TBL_PPTO_EGRESO : Globales.TBL_PPTO_INGRESO;

                // recuperar las partidas del presupuesto afectadas, y verificar que se genere saldo negativo montoAct
                final PreparedStatement pstPpto = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM " + strPpto + " WHERE codigo= ? AND ejefis= ? FOR UPDATE");
                pstPpto.setString(1, regTraspDet.getCodpresu());
                pstPpto.setLong(2, Globales.getEjeFisYear());
                ResultSet rsPpto = pstPpto.executeQuery();

                PptoModel regPpto;
                if (rsPpto.next()) {
                    regPpto = new PptoModel(rsPpto);
                    if (regPpto.getMonto().compareTo(regTraspDet.getMonto()) < 0) {
                        throw new Exception("Error registro en presupuesto, con saldo negativo" + System.getProperty("line.separator")
                            + regPpto.getPartida());
                    }
                } else {
                    throw new Exception("Error registro no encontrado");
                }

                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE " + strPpto + " SET monto= monto - ? WHERE id= ?");
                pst.setBigDecimal(1, regTraspDet.getMonto());
                pst.setLong(2, regPpto.getId());
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error al tratar de actualizar el presupuesto de origen");
                }

                // para todos los registros detalle origen, actualizar su id_trasp
                final PreparedStatement pstTraspDet = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE trasppartidas_det set id_trasp= ?  WHERE id_trasp_det= ?");
                pstTraspDet.setLong(1, id_trasp);
                pstTraspDet.setLong(2, regTraspDet.getId_trasp_det());
                if (pstTraspDet.executeUpdate() != 1) {
                    throw new Exception("Error al tratar de actualizar el registro de traspaso detalle de origen");
                }
            }

            // actualizar el monto en las partidas del presupuesto destino
            for (int i = 0; i < tblDestino.getRowCount(); i++) {
                final TblTraspDet regTraspDet = (TblTraspDet) tblDestino.getValueAt(i, colRegTraspDet);
                final String strPpto = regTraspDet.getPpto_egr_ing().equals("E") ? Globales.TBL_PPTO_EGRESO : Globales.TBL_PPTO_INGRESO;

                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE " + strPpto + " SET monto= monto + ? WHERE codigo= ? AND ejefis= ?");
                pst.setBigDecimal(1, regTraspDet.getMonto());
                pst.setString(2, regTraspDet.getCodpresu());
                pst.setLong(3, Globales.getEjeFisYear());
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error al tratar de actualizar el presupuesto de destino");
                }

                // para todos los registros detalle destino, actualizar su id_trasp
                final PreparedStatement pstTraspDet = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE trasppartidas_det set id_trasp= ?  WHERE id_trasp_det= ?");
                pstTraspDet.setLong(1, id_trasp);
                pstTraspDet.setLong(2, regTraspDet.getId_trasp_det());
                if (pstTraspDet.executeUpdate() != 1) {
                    throw new Exception("Error al tratar de actualizar el registro de traspaso detalle de origen");
                }
            }

            // eliminar los registro en la tabla detalle con id_trasp == 0, para el usuario
            ConnPpto.execute("DELETE FROM trasppartidas_det WHERE id_trasp= 0 AND id_user= " + UserPassIn.getIdUser() + "");

            ConnPpto.EndTransaction();

            JOptionPane.showMessageDialog(null, "Operación realizada");

        } catch (final Exception inex) {
            try {
                ConnPpto.RollBack();
            } catch (final Exception inex1) {
                JOptionPane.showMessageDialog(null, inex1);
            }

            JOptionPane.showMessageDialog(null, inex);

            return;
        }

        if (id_trasp > 0) {
            try {
                TblTrasp reg = TblTrasp.getReg_x_Id(id_trasp);
                if (reg != null) {
                    TraspPartidasSelect.ReportSingle(reg);
                } else {
                    throw new Exception("Regstro no encontrado");
                }
            } catch (final Exception inex) {
                JOptionPane.showMessageDialog(this, "Error al tratar de generar el reporte" + System.getProperty("line.separator") + inex);
            }
        }

        // Se graban las modificaciones
        ClearPanels();
        UpdatePanels();
        UpdateEnabledAndFocus();
    }//GEN-LAST:event_jbGenerarActionPerformed

    @Override
    protected void formWindowActivated(java.awt.event.WindowEvent evt) {
        if (recoverIdOri) {
            recoverIdOri = false;
            UpdatePanels();
            UpdateEnabledAndFocus();
        }

        if (recoverIdDest) {
            recoverIdDest = false;
            UpdatePanels();
            UpdateEnabledAndFocus();
        }
    }

    /**
     * Rev 05-02-2017
     */
    public void setOri() {
        recoverIdOri = true;
    }

    /**
     * Rev 05-02-2017
     */
    public void setDest() {
        recoverIdDest = true;
    }

    private void jtReferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtReferenciaActionPerformed
        jtReferencia.transferFocus();
    }//GEN-LAST:event_jtReferenciaActionPerformed

    private void jtConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtConceptoActionPerformed
        jbGenerar.requestFocusInWindow();
    }//GEN-LAST:event_jtConceptoActionPerformed

    private void btnPptoEgresoOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPptoEgresoOrgActionPerformed
        strTblOri = TBL_PPTO_EGRESO;
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TraspSelOriDest(me, strTblOri, "O").setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnPptoEgresoOrgActionPerformed

    private void btnPptoIngresoOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPptoIngresoOriActionPerformed
        strTblOri = TBL_PPTO_INGRESO;
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TraspSelOriDest(me, strTblOri, "O").setVisible(true);
                me.setVisible(false);
            }
        });

    }//GEN-LAST:event_btnPptoIngresoOriActionPerformed

    private void btnPptoGastoDestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPptoGastoDestActionPerformed
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TraspSelOriDest(me, strTblDest, "D").setVisible(true);
                me.setVisible(false);
            }
        });

    }//GEN-LAST:event_btnPptoGastoDestActionPerformed

    private void jbConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConsultarActionPerformed
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TraspPartidasSelect(me, 0).setVisible(true);
                me.setVisible(false);
            }
        });

    }//GEN-LAST:event_jbConsultarActionPerformed

    private void txtFechaTraspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaTraspActionPerformed
        txtFechaTrasp.transferFocus();
    }//GEN-LAST:event_txtFechaTraspActionPerformed

    private void txtMontoTraspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoTraspActionPerformed
        txtMontoTrasp.transferFocus();
    }//GEN-LAST:event_txtMontoTraspActionPerformed

    private void jtReferenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtReferenciaKeyTyped
        if (jtReferencia.getText().length() >= 128) {
            evt.consume();
        }
    }//GEN-LAST:event_jtReferenciaKeyTyped

    private void jtConceptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtConceptoKeyTyped
        if (jtConcepto.getText().length() >= 512) {
            evt.consume();
        }
    }//GEN-LAST:event_jtConceptoKeyTyped

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if (recoverIdOri) {
            recoverIdOri = false;
            UpdatePanels();
        }

        if (recoverIdDest) {
            recoverIdDest = false;
            UpdatePanels();
        }

    }//GEN-LAST:event_formWindowGainedFocus

    /**
     * Rev 05/02/2017
     *
     */
    private void ClearPanels() {
        jtReferencia.setText("");
        txtFechaTrasp.setText("");
        txtMontoTrasp.setText("0,00");
        jtConcepto.setText("");
    }

    private String getReferencia() {
        String aux = jtReferencia.getText().trim().toUpperCase();
        if (aux.isEmpty()) {
            aux = null;
        }
        return aux;
    }

    /**
     * Rev 06-02-2017
     *
     * @return
     */
    private java.sql.Date getFecha() {

        java.sql.Date aux;
        try {
            aux = Format.toDateSql(txtFechaTrasp.getText().trim());
        } catch (final Exception inex) {
            aux = null;
        }

        return aux;
    }

    private BigDecimal getMonto() {
        String smonto = txtMontoTrasp.getText().trim();
        BigDecimal aux;

        if (smonto.isEmpty()) {
            aux = null;
        } else {
            try {
                aux = (BigDecimal) PptoGlobales.curFormat.stringToValue(smonto);
            } catch (final Exception inex) {
                aux = null;
            }
        }

        return aux;
    }

    private String getConcepto() {
        String aux = jtConcepto.getText().trim().toUpperCase();
        if (aux.isEmpty()) {
            aux = null;
        }
        return aux;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TraspPartidas(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnPptoEgresoOrg;
    private javax.swing.JToggleButton btnPptoGastoDest;
    private javax.swing.JToggleButton btnPptoIngresoOri;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbConsultar;
    private javax.swing.JButton jbGenerar;
    private javax.swing.JButton jbRetornar;
    private javax.swing.ButtonGroup jbgDest;
    private javax.swing.ButtonGroup jbgOri;
    private javax.swing.JLabel jlFondo;
    private javax.swing.JPanel jpCmdFoot;
    private javax.swing.JPanel jpDestino;
    private javax.swing.JPanel jpDetalles;
    private javax.swing.JPanel jpFondo;
    private javax.swing.JPanel jpOrigen;
    private javax.swing.JPanel jpTipoPpto;
    private javax.swing.JPanel jpTipoPpto1;
    private javax.swing.JTextField jtConcepto;
    private javax.swing.JTextField jtReferencia;
    private javax.swing.JToggleButton jtbPptoIngresoDest;
    private javax.swing.JTable tblDestino;
    private javax.swing.JTable tblOrigen;
    private javax.swing.JFormattedTextField txtFechaTrasp;
    private javax.swing.JTextField txtMontoTotalDest;
    private javax.swing.JTextField txtMontoTotalOri;
    private javax.swing.JFormattedTextField txtMontoTrasp;
    // End of variables declaration//GEN-END:variables
}
