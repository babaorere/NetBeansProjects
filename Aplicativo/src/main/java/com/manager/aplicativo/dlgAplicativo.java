package com.manager.aplicativo;

import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manager
 */
public final class dlgAplicativo extends javax.swing.JDialog {

    private Nodo ic;
    private Nodo nodoActual;
    private int cantNodos;

    private final DefaultTableModel modelo;

    // Clase contenedora
    public class Nodo {

        int codigo;
        String nombres;
        String apellidoPaterno;
        String apellidoMaterno;
        String sexo;
        String estadoCivil;
        int numeroHijos;
        float sueldoBase;
        float ventasRealizadas;

        double sueldoNeto;

        Nodo enlaceSig; // tal cual, enlace al nodo siguiente
        Nodo enlaceAnt; // tal cual, enlace al nodo anterior

        public Nodo(int codigo, String nombres, String apellidoPaterno, String apellidoMaterno, String sexo, String estadoCivil,
                int numeroHijos, float sueldoBase, float ventasRealizadas, double sueldoNeto) {
            this.codigo = codigo;
            this.nombres = nombres;
            this.apellidoPaterno = apellidoPaterno;
            this.apellidoMaterno = apellidoMaterno;
            this.sexo = sexo;
            this.estadoCivil = estadoCivil;
            this.numeroHijos = numeroHijos;
            this.sueldoBase = sueldoBase;
            this.ventasRealizadas = ventasRealizadas;
            this.sueldoNeto = sueldoNeto;
            this.enlaceSig = this;
            this.enlaceAnt = this;
        }
    }

    /**
     * Creates new form dlgRegUser
     *
     * @param parent
     */
    public dlgAplicativo(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        actionSalir();
                    }
                });
            }
        });

        ic = null;
        cantNodos = 0;

        modelo = (DefaultTableModel) tblDatos.getModel();
        LimpiarEntradas();
    }

    /**
     *
     * @param ic
     * @param codigo
     * @param nombres
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param sexo
     * @param estadoCivil
     * @param numeroHijos
     * @param sueldoBase
     * @param ventasRealizadas
     * @param sueldoNeto
     * @return
     */
    public Nodo InsertarInicio(Nodo ic, int codigo, String nombres, String apellidoPaterno, String apellidoMaterno,
            String sexo, String estadoCivil, int numeroHijos, float sueldoBase, float ventasRealizadas, double sueldoNeto) {

        Nodo nuevo = new Nodo(codigo, nombres, apellidoPaterno, apellidoMaterno, sexo, estadoCivil, numeroHijos,
                sueldoBase, ventasRealizadas, sueldoNeto);

        return InsertarInicio(ic, nuevo);
    }

    public Nodo InsertarInicio(Nodo ic, Nodo nuevo) {

        if (ic != null) {
            ic.enlaceSig.enlaceAnt = nuevo;
            nuevo.enlaceAnt = ic;

            nuevo.enlaceSig = ic.enlaceSig;
            ic.enlaceSig = nuevo;

            cantNodos++;

        } else {
            ic = InsertarFinal(ic, nuevo);
        }
        nodoActual = nuevo;
        return ic;
    }

    /**
     *
     * @param ic
     * @param codigo
     * @param nombres
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param sexo
     * @param estadoCivil
     * @param numeroHijos
     * @param sueldoBase
     * @param ventasRealizadas
     * @param sueldoNeto
     * @return
     */
    public Nodo InsertarFinal(Nodo ic, int codigo, String nombres, String apellidoPaterno, String apellidoMaterno,
            String sexo, String estadoCivil, int numeroHijos, float sueldoBase, float ventasRealizadas, double sueldoNeto) {

        Nodo nuevo = new Nodo(codigo, nombres, apellidoPaterno, apellidoMaterno, sexo, estadoCivil, numeroHijos,
                sueldoBase, ventasRealizadas, sueldoNeto);

        return InsertarFinal(ic, nuevo);
    }

    public Nodo InsertarFinal(Nodo ic, Nodo nodoNuevo) {

        if (ic != null) {

            ic.enlaceAnt.enlaceSig = nodoNuevo;
            nodoNuevo.enlaceAnt = ic.enlaceAnt;

            nodoNuevo.enlaceSig = ic;
            ic.enlaceAnt = nodoNuevo;
        }

        cantNodos++;
        ic = nodoNuevo;
        nodoActual = nodoNuevo;
        return ic;
    }

    /**
     *
     */
    void LimpiarEntradas() {
        txtCodigo.setText(String.valueOf(cantNodos + 1));
        txtNombres.setText("");
        txtApePaterno.setText("");
        txtApeMaterno.setText("");
        cbxSexo.setSelectedIndex(-1);
        cbxEdoCivil.setSelectedIndex(-1);
        txtNumHijos.setText("");
        txtSueldoBase.setText("");
        txtVentasRealizadas.setText("");
        txtSueldoNeto.setText("");
        txtNombres.requestFocusInWindow();
    }

    /**
     *
     */
    void VerDatos() {
        int codigo;
        String nombres;
        String apellidoPaterno;
        float sueldoBase;
        double sueldoNeto;

        float s;

        DecimalFormat df2 = new DecimalFormat("#.##");

        vaciarTabla();

        Nodo p;
        int contador = 0;
        if (ic != null) {
            cantNodos = 0;
            p = ic.enlaceSig;
            do {
                codigo = p.codigo;
                nombres = p.nombres;
                apellidoPaterno = p.apellidoPaterno;
                sueldoBase = p.sueldoBase;
                sueldoNeto = p.sueldoNeto;
                contador++;
                Object[] fila = {contador, codigo, nombres, apellidoPaterno, df2.format(p.sueldoBase), df2.format(p.sueldoNeto)};
                modelo.addRow(fila);
                p = p.enlaceSig;
            } while (p != ic.enlaceSig);
        }
    }

//    void Resumen() {
//        DecimalFormat df2 = new DecimalFormat("#.##");
//        double tsueldos = 0.00;
//        double tcomisionVentas = 0.00;
//        double tdescuentoImp = 0.00;
//        double tdescuentoSeg = 0.00;
//        Nodo p;
//
//        txtTSueldos.setText(df2.format(tsueldos));
//        txtTComisionVenta.setText(df2.format(tcomisionVentas));
//        txtTDescuentoImp.setText(df2.format(tdescuentoImp));
//        txtTDescuentoSeg.setText(df2.format(tdescuentoSeg));
//    }
    void Resumen() {
        DecimalFormat df2 = new DecimalFormat("#.##");
        double tsueldos = 0.00;
        double tcomisionVentas = 0.00;
        double tdescuentoImp = 0.00;
        double tdescuentoSeg = 0.00;
        Nodo p;

        if (ic != null) {
            p = ic.enlaceSig;
            do {
                tsueldos += p.sueldoBase;

                double comisionVentas = 0.05 * p.ventasRealizadas;
                tcomisionVentas += comisionVentas;

                double descSeguro;
                switch (p.estadoCivil) {
                    case "SOLTERO":
                        descSeguro = 100;
                        break;
                    case "CASADO":
                        if (p.numeroHijos <= 0) {
                            descSeguro = 120;
                        } else {
                            descSeguro = 50 + 70 * p.numeroHijos;
                        }
                        break;
                    default:
                        descSeguro = 0;
                        break;
                }

                tdescuentoSeg += descSeguro;

                double ta = p.sueldoBase + comisionVentas;

                double descDI;
                if (ta <= 1500) {
                    descDI = 0.00;
                } else if (ta <= 2300) {
                    descDI = 0.03 * ta;
                } else if (ta <= 3000) {
                    descDI = 0.04 * ta;
                } else {
                    descDI = 0.06 * ta;
                }

                tdescuentoImp += descDI;

                p = p.enlaceSig;
            } while (p != ic.enlaceSig);

            txtTSueldos.setText(df2.format(tsueldos));
            txtTComisionVenta.setText(df2.format(tcomisionVentas));
            txtTDescuentoImp.setText(df2.format(tdescuentoImp));
            txtTDescuentoSeg.setText(df2.format(tdescuentoSeg));
        }
    }

    void vaciarTabla() {
        int filas = tblDatos.getRowCount();
        for (int i = 0; i < filas; i++) {
            modelo.removeRow(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pnlDatos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtApeMaterno = new javax.swing.JTextField();
        txtSueldoNeto = new javax.swing.JTextField();
        cbxEdoCivil = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        cbxSexo = new javax.swing.JComboBox<>();
        txtNumHijos = new javax.swing.JTextField();
        txtVentasRealizadas = new javax.swing.JTextField();
        txtSueldoBase = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtApePaterno = new javax.swing.JTextField();
        pnlComandos = new javax.swing.JPanel();
        btnGuardarIni = new javax.swing.JButton();
        btnAdeAtr = new javax.swing.JButton();
        mnuAtrAde = new javax.swing.JButton();
        btnRestaurar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnGuardarFin = new javax.swing.JButton();
        pnlTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        pnlResumen = new javax.swing.JPanel();
        pnlTitulos = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtTSueldos = new javax.swing.JTextField();
        txtTComisionVenta = new javax.swing.JTextField();
        txtTDescuentoImp = new javax.swing.JTextField();
        txtTDescuentoSeg = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("APLICATIVO VENDEDORES");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        pnlTitulo.setPreferredSize(new java.awt.Dimension(800, 40));
        pnlTitulo.setLayout(new javax.swing.BoxLayout(pnlTitulo, javax.swing.BoxLayout.PAGE_AXIS));

        lblTitulo.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("REGISTRO DE VENDEDORES");
        lblTitulo.setAlignmentX(0.5F);
        lblTitulo.setMaximumSize(new java.awt.Dimension(800, 40));
        lblTitulo.setMinimumSize(new java.awt.Dimension(800, 40));
        lblTitulo.setPreferredSize(new java.awt.Dimension(800, 40));
        pnlTitulo.add(lblTitulo);

        getContentPane().add(pnlTitulo);

        jPanel1.setMaximumSize(new java.awt.Dimension(800, 550));
        jPanel1.setMinimumSize(new java.awt.Dimension(700, 380));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 380));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        pnlDatos.setMaximumSize(new java.awt.Dimension(400, 2147483647));
        pnlDatos.setMinimumSize(new java.awt.Dimension(400, 180));
        pnlDatos.setPreferredSize(new java.awt.Dimension(400, 180));
        pnlDatos.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("CODIGO");
        jLabel2.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel2.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("NOMBRE");
        jLabel3.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel3.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel3.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("SUELDO BASE");
        jLabel4.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel4.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel4.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("APE. MATERNO");
        jLabel5.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel5.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel5.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("SEXO");
        jLabel6.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel6.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel6.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel6, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("SUELDO NETO");
        jLabel9.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel9.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel9.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("VENT. REALIZ.");
        jLabel10.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel10.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel10.setPreferredSize(new java.awt.Dimension(120, 20));
        jLabel10.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel10, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("ESTADO CIVIL");
        jLabel11.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel11.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel11.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel11, gridBagConstraints);

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtCodigo.setMaximumSize(new java.awt.Dimension(250, 30));
        txtCodigo.setMinimumSize(new java.awt.Dimension(250, 30));
        txtCodigo.setPreferredSize(new java.awt.Dimension(250, 30));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(txtCodigo, gridBagConstraints);

        txtApeMaterno.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtApeMaterno.setMaximumSize(new java.awt.Dimension(250, 30));
        txtApeMaterno.setMinimumSize(new java.awt.Dimension(250, 30));
        txtApeMaterno.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(txtApeMaterno, gridBagConstraints);

        txtSueldoNeto.setEditable(false);
        txtSueldoNeto.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtSueldoNeto.setMaximumSize(new java.awt.Dimension(250, 30));
        txtSueldoNeto.setMinimumSize(new java.awt.Dimension(250, 30));
        txtSueldoNeto.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(txtSueldoNeto, gridBagConstraints);

        cbxEdoCivil.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cbxEdoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SOLTERO", "CASADO", "DIVORCIADO", "VIUDO" }));
        cbxEdoCivil.setSelectedIndex(-1);
        cbxEdoCivil.setMaximumSize(new java.awt.Dimension(250, 30));
        cbxEdoCivil.setMinimumSize(new java.awt.Dimension(250, 30));
        cbxEdoCivil.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(cbxEdoCivil, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("NUM. HIJOS");
        jLabel12.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel12.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel12, gridBagConstraints);

        txtNombres.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtNombres.setMaximumSize(new java.awt.Dimension(250, 30));
        txtNombres.setMinimumSize(new java.awt.Dimension(250, 30));
        txtNombres.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(txtNombres, gridBagConstraints);

        cbxSexo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FEMENINO", "MASCULINO" }));
        cbxSexo.setSelectedIndex(-1);
        cbxSexo.setMaximumSize(new java.awt.Dimension(250, 30));
        cbxSexo.setMinimumSize(new java.awt.Dimension(250, 30));
        cbxSexo.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(cbxSexo, gridBagConstraints);

        txtNumHijos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtNumHijos.setMaximumSize(new java.awt.Dimension(250, 30));
        txtNumHijos.setMinimumSize(new java.awt.Dimension(250, 30));
        txtNumHijos.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(txtNumHijos, gridBagConstraints);

        txtVentasRealizadas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtVentasRealizadas.setMaximumSize(new java.awt.Dimension(250, 30));
        txtVentasRealizadas.setMinimumSize(new java.awt.Dimension(250, 30));
        txtVentasRealizadas.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(txtVentasRealizadas, gridBagConstraints);

        txtSueldoBase.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtSueldoBase.setMaximumSize(new java.awt.Dimension(250, 30));
        txtSueldoBase.setMinimumSize(new java.awt.Dimension(250, 30));
        txtSueldoBase.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(txtSueldoBase, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("APE. PATERNO");
        jLabel13.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel13.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel13.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlDatos.add(jLabel13, gridBagConstraints);

        txtApePaterno.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtApePaterno.setMaximumSize(new java.awt.Dimension(250, 30));
        txtApePaterno.setMinimumSize(new java.awt.Dimension(250, 30));
        txtApePaterno.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pnlDatos.add(txtApePaterno, gridBagConstraints);

        jPanel2.add(pnlDatos);

        pnlComandos.setMaximumSize(new java.awt.Dimension(360, 2147483647));
        pnlComandos.setMinimumSize(new java.awt.Dimension(300, 180));
        pnlComandos.setPreferredSize(new java.awt.Dimension(300, 180));
        pnlComandos.setLayout(new java.awt.GridBagLayout());

        btnGuardarIni.setBackground(new java.awt.Color(0, 102, 102));
        btnGuardarIni.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnGuardarIni.setText("Guardar Ini.");
        btnGuardarIni.setMaximumSize(new java.awt.Dimension(150, 30));
        btnGuardarIni.setMinimumSize(new java.awt.Dimension(150, 30));
        btnGuardarIni.setPreferredSize(new java.awt.Dimension(150, 30));
        btnGuardarIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarIniActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlComandos.add(btnGuardarIni, gridBagConstraints);

        btnAdeAtr.setBackground(new java.awt.Color(102, 102, 0));
        btnAdeAtr.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAdeAtr.setText("Adelante - Atrás");
        btnAdeAtr.setMaximumSize(new java.awt.Dimension(150, 30));
        btnAdeAtr.setMinimumSize(new java.awt.Dimension(150, 30));
        btnAdeAtr.setPreferredSize(new java.awt.Dimension(150, 30));
        btnAdeAtr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdeAtrActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlComandos.add(btnAdeAtr, gridBagConstraints);

        mnuAtrAde.setBackground(new java.awt.Color(102, 102, 0));
        mnuAtrAde.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        mnuAtrAde.setText("Atrás - Adelante");
        mnuAtrAde.setMaximumSize(new java.awt.Dimension(150, 30));
        mnuAtrAde.setMinimumSize(new java.awt.Dimension(150, 30));
        mnuAtrAde.setPreferredSize(new java.awt.Dimension(150, 30));
        mnuAtrAde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAtrAdeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlComandos.add(mnuAtrAde, gridBagConstraints);

        btnRestaurar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnRestaurar.setText("Restaurar");
        btnRestaurar.setEnabled(false);
        btnRestaurar.setMaximumSize(new java.awt.Dimension(150, 30));
        btnRestaurar.setMinimumSize(new java.awt.Dimension(150, 30));
        btnRestaurar.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlComandos.add(btnRestaurar, gridBagConstraints);

        btnConsultar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnConsultar.setText("Consultar");
        btnConsultar.setEnabled(false);
        btnConsultar.setMaximumSize(new java.awt.Dimension(150, 30));
        btnConsultar.setMinimumSize(new java.awt.Dimension(150, 30));
        btnConsultar.setPreferredSize(new java.awt.Dimension(150, 30));
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        pnlComandos.add(btnConsultar, gridBagConstraints);

        btnActualizar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setEnabled(false);
        btnActualizar.setMaximumSize(new java.awt.Dimension(150, 30));
        btnActualizar.setMinimumSize(new java.awt.Dimension(150, 30));
        btnActualizar.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        pnlComandos.add(btnActualizar, gridBagConstraints);

        btnEliminar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setMaximumSize(new java.awt.Dimension(150, 30));
        btnEliminar.setMinimumSize(new java.awt.Dimension(150, 30));
        btnEliminar.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        pnlComandos.add(btnEliminar, gridBagConstraints);

        btnSalir.setBackground(new java.awt.Color(153, 0, 51));
        btnSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setMaximumSize(new java.awt.Dimension(150, 30));
        btnSalir.setMinimumSize(new java.awt.Dimension(150, 30));
        btnSalir.setPreferredSize(new java.awt.Dimension(150, 30));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        pnlComandos.add(btnSalir, gridBagConstraints);

        btnGuardarFin.setBackground(new java.awt.Color(0, 102, 102));
        btnGuardarFin.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnGuardarFin.setText("Guardar Fin.");
        btnGuardarFin.setMaximumSize(new java.awt.Dimension(150, 30));
        btnGuardarFin.setMinimumSize(new java.awt.Dimension(150, 30));
        btnGuardarFin.setPreferredSize(new java.awt.Dimension(150, 30));
        btnGuardarFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarFinActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        pnlComandos.add(btnGuardarFin, gridBagConstraints);

        jPanel2.add(pnlComandos);

        jPanel1.add(jPanel2);

        pnlTabla.setMaximumSize(new java.awt.Dimension(32767, 150));
        pnlTabla.setMinimumSize(new java.awt.Dimension(22, 150));
        pnlTabla.setPreferredSize(new java.awt.Dimension(453, 150));
        pnlTabla.setLayout(new javax.swing.BoxLayout(pnlTabla, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 120));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(453, 120));

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "N\"", "Codigo", "Nombre", "Ape. Paterno", "S. Base", "S. Neto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDatos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblDatos);

        pnlTabla.add(jScrollPane1);

        jPanel1.add(pnlTabla);

        getContentPane().add(jPanel1);

        pnlResumen.setBackground(new java.awt.Color(0, 51, 255));
        pnlResumen.setMaximumSize(new java.awt.Dimension(800, 75));
        pnlResumen.setMinimumSize(new java.awt.Dimension(800, 75));
        pnlResumen.setPreferredSize(new java.awt.Dimension(800, 50));
        pnlResumen.setLayout(new javax.swing.BoxLayout(pnlResumen, javax.swing.BoxLayout.PAGE_AXIS));

        pnlTitulos.setMinimumSize(new java.awt.Dimension(10, 30));
        pnlTitulos.setPreferredSize(new java.awt.Dimension(100, 30));
        pnlTitulos.setLayout(new javax.swing.BoxLayout(pnlTitulos, javax.swing.BoxLayout.LINE_AXIS));

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("T. SUELDOS");
        jLabel14.setMaximumSize(new java.awt.Dimension(200, 25));
        jLabel14.setMinimumSize(new java.awt.Dimension(200, 25));
        jLabel14.setPreferredSize(new java.awt.Dimension(200, 25));
        pnlTitulos.add(jLabel14);

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("T. COMISIONES VENTA");
        jLabel8.setMaximumSize(new java.awt.Dimension(200, 25));
        jLabel8.setMinimumSize(new java.awt.Dimension(200, 25));
        jLabel8.setPreferredSize(new java.awt.Dimension(200, 25));
        pnlTitulos.add(jLabel8);

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("T. DESCUENTO IMP.");
        jLabel7.setMaximumSize(new java.awt.Dimension(200, 25));
        jLabel7.setMinimumSize(new java.awt.Dimension(200, 25));
        jLabel7.setPreferredSize(new java.awt.Dimension(200, 25));
        pnlTitulos.add(jLabel7);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("T. DESCUENTO SEG.");
        jLabel1.setMaximumSize(new java.awt.Dimension(200, 25));
        jLabel1.setMinimumSize(new java.awt.Dimension(200, 25));
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 25));
        pnlTitulos.add(jLabel1);

        pnlResumen.add(pnlTitulos);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        txtTSueldos.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        txtTSueldos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTSueldos.setMinimumSize(new java.awt.Dimension(200, 30));
        txtTSueldos.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel3.add(txtTSueldos);

        txtTComisionVenta.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        txtTComisionVenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTComisionVenta.setMinimumSize(new java.awt.Dimension(200, 30));
        txtTComisionVenta.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel3.add(txtTComisionVenta);

        txtTDescuentoImp.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        txtTDescuentoImp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTDescuentoImp.setMinimumSize(new java.awt.Dimension(200, 30));
        txtTDescuentoImp.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel3.add(txtTDescuentoImp);

        txtTDescuentoSeg.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        txtTDescuentoSeg.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTDescuentoSeg.setMinimumSize(new java.awt.Dimension(200, 30));
        txtTDescuentoSeg.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel3.add(txtTDescuentoSeg);

        pnlResumen.add(jPanel3);

        getContentPane().add(pnlResumen);

        setSize(new java.awt.Dimension(820, 700));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

//    private Nodo Validar() {
//        String strCodigo = txtCodigo.getText().trim().toUpperCase();
//        if (strCodigo.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Indique un Codigo");
//            txtCodigo.requestFocusInWindow();
//            return null;
//        }
//
//        int codigo;
//        try {
//            codigo = Integer.parseInt(strCodigo);
//        } catch (NumberFormatException numberFormatException) {
//            JOptionPane.showMessageDialog(this, "Codigo invalido");
//            txtCodigo.requestFocusInWindow();
//            return null;
//        }
//
//        String nombres = txtNombres.getText().trim().toUpperCase();
//        if (nombres.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Indique un Nombre");
//            txtNombres.requestFocusInWindow();
//            return null;
//        }
//
//        String apePaterno = txtApePaterno.getText().trim().toUpperCase();
//        if (apePaterno.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Indique un Apellido Paterno");
//            txtApePaterno.requestFocusInWindow();
//            return null;
//        }
//
//        String apeMaterno = txtApeMaterno.getText().trim().toUpperCase();
//        if (apeMaterno.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Indique un Apellido Materno");
//            txtApeMaterno.requestFocusInWindow();
//            return null;
//        }
//
//        String edoCivil = cbxEdoCivil.getSelectedItem().toString();
//        if (cbxEdoCivil.getSelectedIndex() < 0) {
//            JOptionPane.showMessageDialog(this, "Indique un Estado Civil");
//            cbxEdoCivil.requestFocusInWindow();
//            return null;
//        }
//
//        String sex = cbxSexo.getSelectedItem().toString();
//        if (cbxSexo.getSelectedIndex() < 0) {
//            JOptionPane.showMessageDialog(this, "Indique un Sexo");
//            cbxSexo.requestFocusInWindow();
//            return null;
//        }
//
//        String strNumHijos = txtNumHijos.getText().trim();
//        if (strNumHijos.isEmpty()) {
//            strNumHijos = "0";
//            txtNumHijos.setText(strNumHijos);
//        }
//
//        int numHijos;
//        try {
//            numHijos = Integer.parseInt(strNumHijos);
//        } catch (NumberFormatException numberFormatException) {
//            JOptionPane.showMessageDialog(this, "Número de Hijos invalido");
//            txtNumHijos.requestFocusInWindow();
//            return null;
//        }
//
//        if (numHijos < 0 || numHijos > 20) {
//            JOptionPane.showMessageDialog(this, "Número de Hijos invalido");
//            txtNumHijos.requestFocusInWindow();
//            return null;
//        }
//
//        String strSueldoBase = txtSueldoBase.getText().trim();
//        if (strSueldoBase.isEmpty()) {
//            strSueldoBase = "0";
//            txtSueldoBase.setText(strSueldoBase);
//        }
//
//        float sueldoBase;
//        try {
//            sueldoBase = Float.parseFloat(strSueldoBase);
//        } catch (NumberFormatException numberFormatException) {
//            JOptionPane.showMessageDialog(this, "Sueldo Base invalido");
//            txtSueldoNeto.requestFocusInWindow();
//            return null;
//        }
//
//        if (sueldoBase <= 0 || sueldoBase > 1_000_000) {
//            JOptionPane.showMessageDialog(this, "Sueldo Base Invalido");
//            txtSueldoBase.requestFocusInWindow();
//            return null;
//        }
//
//        String strVentasRealizadas = txtVentasRealizadas.getText().trim();
//        if (strVentasRealizadas.isEmpty()) {
//            strVentasRealizadas = "0";
//            txtVentasRealizadas.setText(strVentasRealizadas);
//        }
//
//        float ventasRealizadas;
//        try {
//            ventasRealizadas = Float.parseFloat(strVentasRealizadas);
//        } catch (NumberFormatException numberFormatException) {
//            JOptionPane.showMessageDialog(this, "Ventas realizadas invalido");
//            txtVentasRealizadas.requestFocusInWindow();
//            return null;
//        }
//
//        if (ventasRealizadas < 0 || ventasRealizadas > 100_000_000) {
//            JOptionPane.showMessageDialog(this, "Ventas realizadas Invalido");
//            txtVentasRealizadas.requestFocusInWindow();
//            return null;
//        }
//
//        return new Nodo(codigo, nombres, apePaterno, apeMaterno, sex, edoCivil, numHijos, sueldoBase, ventasRealizadas, 0);
//    }
    private Nodo Validar() {
        String strCodigo = txtCodigo.getText().trim().toUpperCase();
        if (strCodigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique un Codigo");
            txtCodigo.requestFocusInWindow();
            return null;
        }

        int codigo;
        try {
            codigo = Integer.parseInt(strCodigo);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "Codigo invalido");
            txtCodigo.requestFocusInWindow();
            return null;
        }

        String nombres = txtNombres.getText().trim().toUpperCase();
        if (nombres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique un Nombre");
            txtNombres.requestFocusInWindow();
            return null;
        }

        String apePaterno = txtApePaterno.getText().trim().toUpperCase();
        if (apePaterno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique un Apellido Paterno");
            txtApePaterno.requestFocusInWindow();
            return null;
        }

        String apeMaterno = txtApeMaterno.getText().trim().toUpperCase();
        if (apeMaterno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique un Apellido Materno");
            txtApeMaterno.requestFocusInWindow();
            return null;
        }

        String edoCivil = cbxEdoCivil.getSelectedItem().toString();
        if (cbxEdoCivil.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Indique un Estado Civil");
            cbxEdoCivil.requestFocusInWindow();
            return null;
        }

        String sex = cbxSexo.getSelectedItem().toString();
        if (cbxSexo.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Indique un Sexo");
            cbxSexo.requestFocusInWindow();
            return null;
        }

        String strNumHijos = txtNumHijos.getText().trim();
        if (strNumHijos.isEmpty()) {
            strNumHijos = "0";
            txtNumHijos.setText(strNumHijos);
        }

        int numHijos;
        try {
            numHijos = Integer.parseInt(strNumHijos);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "Número de Hijos invalido");
            txtNumHijos.requestFocusInWindow();
            return null;
        }

        if (numHijos < 0 || numHijos > 20) {
            JOptionPane.showMessageDialog(this, "Número de Hijos invalido");
            txtNumHijos.requestFocusInWindow();
            return null;
        }

        String strSueldoBase = txtSueldoBase.getText().trim();
        if (strSueldoBase.isEmpty()) {
            strSueldoBase = "0";
            txtSueldoBase.setText(strSueldoBase);
        }

        float sueldoBase;
        try {
            sueldoBase = Float.parseFloat(strSueldoBase);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "Sueldo Base invalido");
            txtSueldoNeto.requestFocusInWindow();
            return null;
        }

        if (sueldoBase <= 0 || sueldoBase > 1_000_000) {
            JOptionPane.showMessageDialog(this, "Sueldo Base Invalido");
            txtSueldoBase.requestFocusInWindow();
            return null;
        }

        String strVentasRealizadas = txtVentasRealizadas.getText().trim();
        if (strVentasRealizadas.isEmpty()) {
            strVentasRealizadas = "0";
            txtVentasRealizadas.setText(strVentasRealizadas);
        }

        float ventasRealizadas;
        try {
            ventasRealizadas = Float.parseFloat(strVentasRealizadas);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "Ventas realizadas invalido");
            txtVentasRealizadas.requestFocusInWindow();
            return null;
        }

        if (ventasRealizadas < 0 || ventasRealizadas > 100_000_000) {
            JOptionPane.showMessageDialog(this, "Ventas realizadas Invalido");
            txtVentasRealizadas.requestFocusInWindow();
            return null;
        }

        double comisionVentas = 0.05 * ventasRealizadas;

        double descSeguro;
        switch (edoCivil) {
            case "SOLTERO":
                descSeguro = 100;
                break;
            case "CASADO":
                if (numHijos <= 0) {
                    descSeguro = 120;
                } else {
                    descSeguro = 50 + 70 * numHijos;
                }
                break;
            default:
                descSeguro = 0;
                break;
        }

        double ta = sueldoBase + comisionVentas;

        double descDI;
        if (ta <= 1500) {
            descDI = 0.00;
        } else if (ta <= 2300) {
            descDI = 0.03 * ta;
        } else if (ta <= 3000) {
            descDI = 0.04 * ta;
        } else {
            descDI = 0.06 * ta;
        }

        double sueldoNeto = sueldoBase + comisionVentas - descDI - descSeguro;

        txtSueldoNeto.setText(String.valueOf(sueldoNeto));

        return new Nodo(codigo, nombres, apePaterno, apeMaterno, sex, edoCivil, numHijos, sueldoBase, ventasRealizadas, sueldoNeto);
    }

    private void btnGuardarIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarIniActionPerformed

        Nodo nodo = Validar();

        if (nodo == null) {
            return;
        }

        ic = InsertarInicio(ic, nodo);

        LimpiarEntradas();
        VerDatos();
        Resumen();
    }//GEN-LAST:event_btnGuardarIniActionPerformed

    public void IrNodoSig() {

        if (ic == null || nodoActual == null) {
            return;
        }

        nodoActual = nodoActual.enlaceSig;
    }

    public void IrNodoAnt() {

        if (ic == null || nodoActual == null) {
            return;
        }

        nodoActual = nodoActual.enlaceAnt;
    }

    public void VerNodoActual() {
        txtCodigo.setText(String.valueOf(nodoActual.codigo));
        txtNombres.setText(nodoActual.nombres);
        txtApePaterno.setText(nodoActual.apellidoPaterno);
        txtApeMaterno.setText(nodoActual.apellidoMaterno);
        cbxSexo.setSelectedItem(nodoActual.sexo);
        cbxEdoCivil.setSelectedItem(nodoActual.estadoCivil);
        txtNumHijos.setText(String.valueOf(nodoActual.numeroHijos));
        txtSueldoBase.setText(String.valueOf(nodoActual.sueldoBase));
        txtVentasRealizadas.setText(String.valueOf(nodoActual.ventasRealizadas));
        txtSueldoNeto.setText(String.valueOf(nodoActual.sueldoNeto));
    }

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void mnuAtrAdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAtrAdeActionPerformed
        IrNodoSig();
        VerNodoActual();
    }//GEN-LAST:event_mnuAtrAdeActionPerformed

    private void btnAdeAtrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdeAtrActionPerformed
        IrNodoSig();
        VerNodoActual();
    }//GEN-LAST:event_btnAdeAtrActionPerformed

    private void btnGuardarFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarFinActionPerformed
        Nodo nodo = Validar();

        if (nodo == null) {
            return;
        }

        ic = InsertarFinal(ic, nodo);

        LimpiarEntradas();
        VerDatos();
        Resumen();
    }//GEN-LAST:event_btnGuardarFinActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        actionSalir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void actionSalir() {
        JFrame parent = (JFrame) this.getOwner();

        this.setVisible(false);
        parent.setVisible(false);
        this.dispose();
        parent.dispose();
    }

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dlgAplicativo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                dlgAplicativo dialog = new dlgAplicativo(new JFrame());
                dialog.setVisible(true);
            }
        });

        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAdeAtr;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarFin;
    private javax.swing.JButton btnGuardarIni;
    private javax.swing.JButton btnRestaurar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbxEdoCivil;
    private javax.swing.JComboBox<String> cbxSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JButton mnuAtrAde;
    private javax.swing.JPanel pnlComandos;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlResumen;
    private javax.swing.JPanel pnlTabla;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JPanel pnlTitulos;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtApeMaterno;
    private javax.swing.JTextField txtApePaterno;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtNumHijos;
    private javax.swing.JTextField txtSueldoBase;
    private javax.swing.JTextField txtSueldoNeto;
    private javax.swing.JTextField txtTComisionVenta;
    private javax.swing.JTextField txtTDescuentoImp;
    private javax.swing.JTextField txtTDescuentoSeg;
    private javax.swing.JTextField txtTSueldos;
    private javax.swing.JTextField txtVentasRealizadas;
    // End of variables declaration//GEN-END:variables
}
