package com.tayrona;

import java.time.format.DateTimeFormatter;

/**
 *
 */
public final class ConsultarDialog extends javax.swing.JDialog {

    private Integer idx;

    /**
     * Creates new form RegistroDialog
     *
     * @param inWin
     */
    public ConsultarDialog(final java.awt.Window inWin, Integer inIdx) {
        super(inWin, DEFAULT_MODALITY_TYPE);

        idx = inIdx;

        // Aqui se procede a crear los componentes graficos, varios de la GUI
        initComponents();

        // Aqui se procede a crear los componentes propios de la clase, los funcionales de la aplicación
        myInitComponents();
    }

    public void myInitComponents() {

        // Esto se utiliza mas adelante, para pasar esta Win como parametro en el "windowClosing"
        final java.awt.Window me = this;

        // Creación del listener, quien sera ejecutado una vez que se quiera cerrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    Controller.actionSalir(me);
                });
            }
        });

        // Llenar los Combos
        cmbSexo.addItem(SexoEnum.FEMENINO);
        cmbSexo.addItem(SexoEnum.MASCULINO);
        cmbSexo.setSelectedIndex(-1);

        cmbEdoCivil.addItem(EdoCivilEnum.SOLTERO);
        cmbEdoCivil.addItem(EdoCivilEnum.CASADO);
        cmbEdoCivil.addItem(EdoCivilEnum.DIVORCIADO);
        cmbEdoCivil.addItem(EdoCivilEnum.VIUDO);
        cmbEdoCivil.setSelectedIndex(-1);

        cmbGradoEsc.addItem(GradoEscolaridadEnum.BASICA);
        cmbGradoEsc.addItem(GradoEscolaridadEnum.MEDIA);
        cmbGradoEsc.addItem(GradoEscolaridadEnum.UNIVERSITARIA);
        cmbGradoEsc.addItem(GradoEscolaridadEnum.POSTGRADO);
        cmbGradoEsc.setSelectedIndex(-1);

        cmbEPS.addItem(EpsEnum.CAFESALUD);
        cmbEPS.addItem(EpsEnum.COMPENSAR);
        cmbEPS.addItem(EpsEnum.SALUD_COLMENA);
        cmbEPS.addItem(EpsEnum.SALUD_TOTAL);
        cmbEPS.addItem(EpsEnum.SANITAS);
        cmbEPS.addItem(EpsEnum.UNIMEC);
        cmbEPS.setSelectedIndex(-1);

        cmbCajaComp.addItem(CajaCompEnum.COLSUBSIDIO);
        cmbCajaComp.addItem(CajaCompEnum.COMBARRANQUILLA);
        cmbCajaComp.addItem(CajaCompEnum.COMFABOY);
        cmbCajaComp.addItem(CajaCompEnum.COMFAMA);
        cmbCajaComp.addItem(CajaCompEnum.COMFAMILIAR_CAMACOL);
        cmbCajaComp.setSelectedIndex(-1);

        cmbTipoCont.addItem(TipoContratoEnum.NOMINA);
        cmbTipoCont.addItem(TipoContratoEnum.SERVICIO);
        cmbTipoCont.addItem(TipoContratoEnum.VENTA);
        cmbTipoCont.setSelectedIndex(-1);

        cmbCategoria.addItem(CategoriaEnum.COORDINACION);
        cmbCategoria.addItem(CategoriaEnum.OFICINA);
        cmbCategoria.addItem(CategoriaEnum.SERVICIOS_GENERALES);
        cmbCategoria.addItem(CategoriaEnum.VENTAS);
        cmbCategoria.addItem(CategoriaEnum.GERENCIA);
        cmbCategoria.setSelectedIndex(-1);

        // Aprovechamos el polimorfismo
        InterfazEmpleado registro = AppFrame.getEmpList().get(idx);

        txtIdentificacion.setText(registro.getIdentificacion());
        txtNombre.setText(registro.getNombre());
        txtFechaNac.setText(registro.getFechaNac().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        cmbSexo.setSelectedItem(registro.getSexo());
        cmbEdoCivil.setSelectedItem(registro.getEdoCivil());
        txtNumHijos.setText(String.valueOf(registro.getNumHijos()));
        cmbGradoEsc.setSelectedItem(registro.getGradoEsc());
        cmbEPS.setSelectedItem(registro.getEpsEnum());
        cmbCajaComp.setSelectedItem(registro.getCajaComp());
        txtSalario.setText(String.valueOf(registro.getSalario()));
        txtAhorro.setText(String.valueOf(registro.getAhorro()));
        cmbTipoCont.setSelectedItem(registro.getTipoCont());
        cmbCategoria.setSelectedItem(registro.getCategoria());

        // Verificar el tipo de empleado
        if (registro instanceof EmpVenta) {
            txtMetaVentas.setText(String.valueOf(registro.getMetaVentas()));
            txtPorcBonificacion.setText(String.valueOf(registro.getPorcNonificacion()));
        } else {
            txtMetaVentas.setText("");
            txtPorcBonificacion.setText("");
        }

        int aporteSalud = (int) (registro.getSalario() * 0.04);
        int aportePension = (int) (registro.getSalario() * 0.04);
        int ahorroProgramado = registro.getAhorro();
        int retensionFuente;
        if (registro.getSalario() > 4_800_000) {
            retensionFuente = (int) (registro.getSalario() * 0.04);
        } else {
            retensionFuente = 0;
        }

        int bonificacion;
        bonificacion = (int) (switch (registro.getCategoria()) {
            case VENTAS ->
                registro.getMetaVentas() * 0.02 * registro.getNumHijos();
            case SERVICIOS_GENERALES ->
                registro.getSalario() * 0.06 * registro.getNumHijos();
            case OFICINA ->
                registro.getSalario() * 0.03 * registro.getNumHijos();
            case COORDINACION ->
                registro.getSalario() * 0.02 * registro.getNumHijos();
            case GERENCIA ->
                registro.getSalario() * 0.01 * registro.getNumHijos();
            default ->
                0;
        });

        int pagoMensual = registro.getSalario() + bonificacion - aporteSalud - aportePension - ahorroProgramado - retensionFuente;

        lblAporteSalud.setText(String.valueOf(aporteSalud));
        lblAportePension.setText(String.valueOf(aportePension));
        lblAhorroProgramado.setText(String.valueOf(ahorroProgramado));
        lblRetencionFuente.setText(String.valueOf(retensionFuente));
        lblBonificaciones.setText(String.valueOf(bonificacion));
        lblPagoMensual.setText(String.valueOf(pagoMensual));

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
        pnlDatos = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtIdentificacion = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtFechaNac = new javax.swing.JTextField();
        txtNumHijos = new javax.swing.JTextField();
        cmbSexo = new javax.swing.JComboBox<>();
        cmbGradoEsc = new javax.swing.JComboBox<>();
        cmbEPS = new javax.swing.JComboBox<>();
        cmbEdoCivil = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtAhorro = new javax.swing.JTextField();
        cmbTipoCont = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txtMetaVentas = new javax.swing.JTextField();
        txtPorcBonificacion = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cmbCajaComp = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblAporteSalud = new javax.swing.JLabel();
        lblAportePension = new javax.swing.JLabel();
        lblAhorroProgramado = new javax.swing.JLabel();
        lblRetencionFuente = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        lblBonificaciones = new javax.swing.JLabel();
        pnlComandos = new javax.swing.JPanel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel29 = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        lblPagoMensual = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnCancelar = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Tayrona");
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        pnlTitulo.setMaximumSize(new java.awt.Dimension(800, 50));
        pnlTitulo.setMinimumSize(new java.awt.Dimension(800, 50));
        pnlTitulo.setPreferredSize(new java.awt.Dimension(800, 50));
        pnlTitulo.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CONSULTAR EMPLEADO");
        pnlTitulo.add(jLabel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlTitulo);

        pnlDatos.setLayout(new javax.swing.BoxLayout(pnlDatos, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(400, 32767));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 0));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("IDENTIFICACIÓN:");
        jLabel2.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("NOMBRE:");
        jLabel3.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("FECHA DE NACIMIENTO:");
        jLabel4.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("SEXO:");
        jLabel5.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("ESTADO CIVIL:");
        jLabel6.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("NÚMERO DE HIJOS:");
        jLabel7.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("GRADO DE ESCOLARIDAD:");
        jLabel8.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("EPS:");
        jLabel9.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel1.add(jLabel9, gridBagConstraints);

        txtIdentificacion.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtIdentificacion.setEnabled(false);
        txtIdentificacion.setMaximumSize(new java.awt.Dimension(150, 25));
        txtIdentificacion.setMinimumSize(new java.awt.Dimension(150, 25));
        txtIdentificacion.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(txtIdentificacion, gridBagConstraints);

        txtNombre.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtNombre.setEnabled(false);
        txtNombre.setMaximumSize(new java.awt.Dimension(150, 25));
        txtNombre.setMinimumSize(new java.awt.Dimension(150, 25));
        txtNombre.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(txtNombre, gridBagConstraints);

        txtFechaNac.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtFechaNac.setEnabled(false);
        txtFechaNac.setMaximumSize(new java.awt.Dimension(150, 25));
        txtFechaNac.setMinimumSize(new java.awt.Dimension(150, 25));
        txtFechaNac.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(txtFechaNac, gridBagConstraints);

        txtNumHijos.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtNumHijos.setEnabled(false);
        txtNumHijos.setMaximumSize(new java.awt.Dimension(150, 25));
        txtNumHijos.setMinimumSize(new java.awt.Dimension(150, 25));
        txtNumHijos.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(txtNumHijos, gridBagConstraints);

        cmbSexo.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cmbSexo.setEnabled(false);
        cmbSexo.setMaximumSize(new java.awt.Dimension(150, 25));
        cmbSexo.setMinimumSize(new java.awt.Dimension(150, 25));
        cmbSexo.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(cmbSexo, gridBagConstraints);

        cmbGradoEsc.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cmbGradoEsc.setEnabled(false);
        cmbGradoEsc.setMaximumSize(new java.awt.Dimension(150, 25));
        cmbGradoEsc.setMinimumSize(new java.awt.Dimension(150, 25));
        cmbGradoEsc.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(cmbGradoEsc, gridBagConstraints);

        cmbEPS.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cmbEPS.setEnabled(false);
        cmbEPS.setMaximumSize(new java.awt.Dimension(150, 25));
        cmbEPS.setMinimumSize(new java.awt.Dimension(150, 25));
        cmbEPS.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(cmbEPS, gridBagConstraints);

        cmbEdoCivil.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cmbEdoCivil.setEnabled(false);
        cmbEdoCivil.setMaximumSize(new java.awt.Dimension(150, 25));
        cmbEdoCivil.setMinimumSize(new java.awt.Dimension(150, 25));
        cmbEdoCivil.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(cmbEdoCivil, gridBagConstraints);

        pnlDatos.add(jPanel1);

        jPanel3.setMaximumSize(new java.awt.Dimension(400, 32767));
        jPanel3.setMinimumSize(new java.awt.Dimension(400, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 0));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("CAJA DE COMPENSACIÓN:");
        jLabel10.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel3.add(jLabel10, gridBagConstraints);

        txtSalario.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtSalario.setEnabled(false);
        txtSalario.setMaximumSize(new java.awt.Dimension(150, 25));
        txtSalario.setMinimumSize(new java.awt.Dimension(150, 25));
        txtSalario.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel3.add(txtSalario, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("SALARIO:");
        jLabel11.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel3.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("AHORRO:");
        jLabel12.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel3.add(jLabel12, gridBagConstraints);

        txtAhorro.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtAhorro.setEnabled(false);
        txtAhorro.setMaximumSize(new java.awt.Dimension(150, 25));
        txtAhorro.setMinimumSize(new java.awt.Dimension(150, 25));
        txtAhorro.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel3.add(txtAhorro, gridBagConstraints);

        cmbTipoCont.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cmbTipoCont.setEnabled(false);
        cmbTipoCont.setMaximumSize(new java.awt.Dimension(150, 25));
        cmbTipoCont.setMinimumSize(new java.awt.Dimension(150, 25));
        cmbTipoCont.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel3.add(cmbTipoCont, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("TIPO CONTRATACIÓN:");
        jLabel13.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel3.add(jLabel13, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("CATEGORÍA");
        jLabel14.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel3.add(jLabel14, gridBagConstraints);

        cmbCategoria.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cmbCategoria.setEnabled(false);
        cmbCategoria.setMaximumSize(new java.awt.Dimension(150, 25));
        cmbCategoria.setMinimumSize(new java.awt.Dimension(150, 25));
        cmbCategoria.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel3.add(cmbCategoria, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("META DE VENTAS:");
        jLabel16.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel3.add(jLabel16, gridBagConstraints);

        txtMetaVentas.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtMetaVentas.setEnabled(false);
        txtMetaVentas.setMaximumSize(new java.awt.Dimension(150, 25));
        txtMetaVentas.setMinimumSize(new java.awt.Dimension(150, 25));
        txtMetaVentas.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel3.add(txtMetaVentas, gridBagConstraints);

        txtPorcBonificacion.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtPorcBonificacion.setEnabled(false);
        txtPorcBonificacion.setMaximumSize(new java.awt.Dimension(150, 25));
        txtPorcBonificacion.setMinimumSize(new java.awt.Dimension(150, 25));
        txtPorcBonificacion.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel3.add(txtPorcBonificacion, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("PORC. BONIFICACIÓN:");
        jLabel17.setPreferredSize(new java.awt.Dimension(185, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel3.add(jLabel17, gridBagConstraints);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("solo si el empleado es de Ventas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel3.add(jLabel18, gridBagConstraints);

        cmbCajaComp.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cmbCajaComp.setEnabled(false);
        cmbCajaComp.setMaximumSize(new java.awt.Dimension(150, 25));
        cmbCajaComp.setMinimumSize(new java.awt.Dimension(150, 25));
        cmbCajaComp.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel3.add(cmbCajaComp, gridBagConstraints);

        pnlDatos.add(jPanel3);

        getContentPane().add(pnlDatos);

        jPanel2.setMaximumSize(new java.awt.Dimension(800, 150));
        jPanel2.setMinimumSize(new java.awt.Dimension(800, 150));
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 175));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setMinimumSize(new java.awt.Dimension(400, 0));
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 100));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel15.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Aporte a Salud");
        jLabel15.setMaximumSize(new java.awt.Dimension(180, 20));
        jLabel15.setMinimumSize(new java.awt.Dimension(180, 20));
        jLabel15.setPreferredSize(new java.awt.Dimension(180, 20));
        jLabel15.setRequestFocusEnabled(false);
        jPanel4.add(jLabel15, new java.awt.GridBagConstraints());

        jLabel19.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Aporte a Pensión");
        jLabel19.setMaximumSize(new java.awt.Dimension(180, 20));
        jLabel19.setMinimumSize(new java.awt.Dimension(180, 20));
        jLabel19.setPreferredSize(new java.awt.Dimension(180, 20));
        jLabel19.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel4.add(jLabel19, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Ahorro Programado");
        jLabel20.setMaximumSize(new java.awt.Dimension(180, 20));
        jLabel20.setMinimumSize(new java.awt.Dimension(180, 20));
        jLabel20.setPreferredSize(new java.awt.Dimension(180, 20));
        jLabel20.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel4.add(jLabel20, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Retención en la Fuente");
        jLabel21.setMaximumSize(new java.awt.Dimension(180, 20));
        jLabel21.setMinimumSize(new java.awt.Dimension(180, 20));
        jLabel21.setPreferredSize(new java.awt.Dimension(180, 20));
        jLabel21.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel4.add(jLabel21, gridBagConstraints);

        lblAporteSalud.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblAporteSalud.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAporteSalud.setMaximumSize(new java.awt.Dimension(160, 25));
        lblAporteSalud.setMinimumSize(new java.awt.Dimension(160, 25));
        lblAporteSalud.setPreferredSize(new java.awt.Dimension(160, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel4.add(lblAporteSalud, gridBagConstraints);

        lblAportePension.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblAportePension.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAportePension.setMaximumSize(new java.awt.Dimension(160, 25));
        lblAportePension.setMinimumSize(new java.awt.Dimension(160, 25));
        lblAportePension.setPreferredSize(new java.awt.Dimension(160, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel4.add(lblAportePension, gridBagConstraints);

        lblAhorroProgramado.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblAhorroProgramado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAhorroProgramado.setMaximumSize(new java.awt.Dimension(160, 25));
        lblAhorroProgramado.setMinimumSize(new java.awt.Dimension(160, 25));
        lblAhorroProgramado.setPreferredSize(new java.awt.Dimension(160, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel4.add(lblAhorroProgramado, gridBagConstraints);

        lblRetencionFuente.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblRetencionFuente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRetencionFuente.setMaximumSize(new java.awt.Dimension(160, 25));
        lblRetencionFuente.setMinimumSize(new java.awt.Dimension(160, 25));
        lblRetencionFuente.setPreferredSize(new java.awt.Dimension(160, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel4.add(lblRetencionFuente, gridBagConstraints);

        jPanel2.add(jPanel4);

        jPanel5.setMinimumSize(new java.awt.Dimension(400, 10));
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 100));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel26.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Bonificaciones");
        jLabel26.setMaximumSize(new java.awt.Dimension(180, 20));
        jLabel26.setMinimumSize(new java.awt.Dimension(180, 20));
        jLabel26.setPreferredSize(new java.awt.Dimension(180, 20));
        jPanel5.add(jLabel26, new java.awt.GridBagConstraints());

        lblBonificaciones.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblBonificaciones.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBonificaciones.setMaximumSize(new java.awt.Dimension(160, 20));
        lblBonificaciones.setMinimumSize(new java.awt.Dimension(160, 20));
        lblBonificaciones.setPreferredSize(new java.awt.Dimension(160, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel5.add(lblBonificaciones, gridBagConstraints);

        jPanel2.add(jPanel5);

        getContentPane().add(jPanel2);

        pnlComandos.setMaximumSize(new java.awt.Dimension(800, 50));
        pnlComandos.setMinimumSize(new java.awt.Dimension(800, 50));
        pnlComandos.setPreferredSize(new java.awt.Dimension(800, 50));
        pnlComandos.setLayout(new javax.swing.BoxLayout(pnlComandos, javax.swing.BoxLayout.LINE_AXIS));
        pnlComandos.add(filler5);

        jLabel29.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("PAGO MENSUAL");
        jLabel29.setMaximumSize(new java.awt.Dimension(180, 25));
        jLabel29.setMinimumSize(new java.awt.Dimension(180, 25));
        jLabel29.setPreferredSize(new java.awt.Dimension(180, 25));
        pnlComandos.add(jLabel29);
        pnlComandos.add(filler3);

        lblPagoMensual.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblPagoMensual.setForeground(new java.awt.Color(0, 204, 0));
        lblPagoMensual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPagoMensual.setMaximumSize(new java.awt.Dimension(180, 25));
        lblPagoMensual.setMinimumSize(new java.awt.Dimension(180, 25));
        lblPagoMensual.setPreferredSize(new java.awt.Dimension(180, 25));
        pnlComandos.add(lblPagoMensual);
        pnlComandos.add(filler4);

        btnCancelar.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setMaximumSize(new java.awt.Dimension(125, 30));
        btnCancelar.setMinimumSize(new java.awt.Dimension(125, 30));
        btnCancelar.setPreferredSize(new java.awt.Dimension(125, 30));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        pnlComandos.add(btnCancelar);
        pnlComandos.add(filler1);

        getContentPane().add(pnlComandos);

        setSize(new java.awt.Dimension(810, 630));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Controller.actionSalir(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<CajaCompEnum> cmbCajaComp;
    private javax.swing.JComboBox<com.tayrona.CategoriaEnum> cmbCategoria;
    private javax.swing.JComboBox<EpsEnum> cmbEPS;
    private javax.swing.JComboBox<EdoCivilEnum> cmbEdoCivil;
    private javax.swing.JComboBox<GradoEscolaridadEnum> cmbGradoEsc;
    private javax.swing.JComboBox<SexoEnum> cmbSexo;
    private javax.swing.JComboBox<TipoContratoEnum> cmbTipoCont;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAhorroProgramado;
    private javax.swing.JLabel lblAportePension;
    private javax.swing.JLabel lblAporteSalud;
    private javax.swing.JLabel lblBonificaciones;
    private javax.swing.JLabel lblPagoMensual;
    private javax.swing.JLabel lblRetencionFuente;
    private javax.swing.JPanel pnlComandos;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTextField txtAhorro;
    private javax.swing.JTextField txtFechaNac;
    private javax.swing.JTextField txtIdentificacion;
    private javax.swing.JTextField txtMetaVentas;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumHijos;
    private javax.swing.JTextField txtPorcBonificacion;
    private javax.swing.JTextField txtSalario;
    // End of variables declaration//GEN-END:variables
}
