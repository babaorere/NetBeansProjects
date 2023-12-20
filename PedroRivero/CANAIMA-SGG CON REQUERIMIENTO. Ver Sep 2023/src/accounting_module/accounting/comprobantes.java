package accounting_module.accounting;

import accounting_module.Conexion.ConnAccounting;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class comprobantes extends javax.swing.JFrame {

    DefaultTableModel dtm;//Se Crea un Modelo para la Tabla
    ConnAccounting bd = ConnAccounting.getInstance();
    private final String accion = "nuevo";
    private int id = 0;

    public comprobantes() {
        initComponents();
        this.visibleFalse();
        inicializarTabla();
        montoFocus();
    }

    public void visibleFalse() {
        this.conId.setVisible(false);
        this.empresa.setVisible(false);
        this.si.setVisible(false);
        this.etiCuenta.setVisible(false);
        this.etiAux.setVisible(false);
        this.btnCalcular.setVisible(false);
    }

    private void inicializarTabla() {
        //array de String's con los títulos de las columnas
        String[] columnNames = {"Cuenta",
            "Auxiliar",
            "Concepto",
            "Debe",
            "Haber"};
        //creamos el Modelo de la tabla con als columnas
        dtm = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        this.tabla.setModel(dtm);
        //le damos tamaño
        this.tabla.setPreferredScrollableViewportSize(new Dimension(500, 200));
        this.tabla.setRowHeight(30);
        this.btnRegistrar.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    public void LlenarCmboAux() {
        try {
            String cta = cmboCuenta.getSelectedItem().toString();
            String sql = "SELECT * FROM mh_auxiliar Where emp_id='" + empresa.getText() + "' AND cta_id = '" + cta + "'";
            ResultSet resul = bd.buscar(sql);
            while (resul.next()) {
                cmboAuxiliar.addItem(resul.getObject("aux_id"));
                etiCuenta.setText(resul.getString("aux_nombreCta"));
                si.setText(resul.getString("aux_condicion"));
                etiCuenta.setVisible(true);
            }
        } catch (Exception ee) {
            System.out.println(ee);
        }
    }

    //    Metodo que convierte el Nombre del mes en elegido en un Entero
    public int NroMes() {
        int mes = 0;
        if (cBoxCompMes.getSelectedItem().equals("Enero")) {
            mes = 1;
        } else if (cBoxCompMes.getSelectedItem().equals("Febrero")) {
            mes = 2;
        } else if (cBoxCompMes.getSelectedItem().equals("Marzo")) {
            mes = 3;
        } else if (cBoxCompMes.getSelectedItem().equals("Abril")) {
            mes = 4;
        } else if (cBoxCompMes.getSelectedItem().equals("Mayo")) {
            mes = 5;
        } else if (cBoxCompMes.getSelectedItem().equals("Junio")) {
            mes = 6;
        } else if (cBoxCompMes.getSelectedItem().equals("Julio")) {
            mes = 7;
        } else if (cBoxCompMes.getSelectedItem().equals("Agosto")) {
            mes = 8;
        } else if (cBoxCompMes.getSelectedItem().equals("Septiembre")) {
            mes = 9;
        } else if (cBoxCompMes.getSelectedItem().equals("Octubre")) {
            mes = 10;
        } else if (cBoxCompMes.getSelectedItem().equals("Noviembre")) {
            mes = 11;
        } else if (cBoxCompMes.getSelectedItem().equals("Diciembre")) {
            mes = 12;
        }
        return mes;
    }

    public void ComprNro() {
        int mes;
        String years;
        mes = NroMes();
        years = etiYear.getText();
        String sql = "SELECT IFNULL(MAX(`mh_comprobante`.`comp_id`),0) AS vv FROM `mh_comprobante` "
                + "WHERE `mh_comprobante`.`emp_id`='" + empresa.getText() + "' "
                + "AND `mh_comprobante`.`comp_fecha` BETWEEN '" + years + "/" + mes + "/01' AND '" + years + "/" + mes + "/31'";
        ResultSet rs = bd.buscar(sql);
        try {
            if (rs.next()) {
                txtNumCompr.setText(rs.getString("vv"));
                int num = Integer.parseInt(txtNumCompr.getText());
                txtNumCompr.setText("" + (num + 1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(comprobantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean esBigDecimal(String num) {
        boolean band = false;
        try {
//            double floatnum = Double.parseDouble(num );
            BigDecimal floatnum = new BigDecimal(num);
            band = true;
        } catch (NumberFormatException e) {
        }
        return band;
    }

    private boolean agregarFila(Object[] datos) {
        /* Object[] data = {"Mary", "Campione","Esquiar", new Integer(5), new Boolean(false)};*/
        boolean band = false;
        try {
            dtm.addRow(datos);
            band = true;
        } catch (Exception e) {
        }
        return band;
    }

    private void AgregarMonto() {
        BigDecimal debe = BigDecimal.ZERO;
        BigDecimal haber = BigDecimal.ZERO;
        String cuenta = null;
        String auxiliar = null;
        String descrip = null;
        cuenta = (String) this.cmboCuenta.getSelectedItem();
        auxiliar = (String) this.cmboAuxiliar.getSelectedItem();

        if (this.radioDebe.isSelected()) {
            debe = new BigDecimal(this.txtMonto.getText());
        } else if (this.radioHaber.isSelected()) {
            haber = new BigDecimal(this.txtMonto.getText());
        }
        descrip = this.txtConcepto.getText();
        try {
            if (accion.equals("nuevo")) {
                //Creo un objeto con los datos que tendra la fila
                Object[] data = {cuenta, auxiliar, descrip, debe, haber};
                //Intento agregar fila y muestro los mensajes correspondientes
                if (this.agregarFila(data)) {
                    JOptionPane.showMessageDialog(null, "Datos agregados correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("debe: " + debe + "\n" + "haber: " + haber);
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema, intentelo en otro momento", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                //Actualizo los datos
                dtm.setValueAt(cuenta, this.id, 0);
                dtm.setValueAt(auxiliar, this.id, 1);
                dtm.setValueAt(descrip, this.id, 2);
                dtm.setValueAt(debe, this.id, 3);
                dtm.setValueAt(haber, this.id, 4);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private String validarForm() {
        String mensaje = "";
        if (txtNumCompr.getText().equals("0")) {
            mensaje += "Debe Elegir el Mes Para Ingresar el Numero de Comprobante\n";
        } else if (this.cmboCuenta.getSelectedItem().toString().equals("")) {
            mensaje += "Debe Seleccionar una Cuenta";
        } else if (si.getText().equals("Si") && cmboAuxiliar.getSelectedItem().equals("")) {
            mensaje += "Debe elegir un Auxiliar";
        } else if (this.txtConcepto.getText().equals("") || this.txtConcepto.getText().length() > 100) {
            mensaje += "- Debe agregar un concepto que no sobrepase los 100 caracteres\n";
        } else if (!this.esBigDecimal(this.txtMonto.getText())) {
            mensaje += "- Debe ingresar Monto \nSepare los decimales con el caracter punto(.)\n";
        } else if (debeHaber.isSelected(null)) {
            mensaje += "Indique el Debe o el Haber";
        }
        return mensaje;
    }

    private void LimpiarForm() {
        this.txtConcepto.setText("");
        this.txtMonto.setText("");
        this.cmboAuxiliar.setSelectedItem(null);
        this.cmboCuenta.setSelectedItem(null);
        this.cmb_concepto.setSelectedItem(null);
    }

    //Metodo para Sumar el contenido de las Filas por Columnas
    private BigDecimal totalColumna(int Columna) {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            total = new BigDecimal(dtm.getValueAt(i, Columna).toString()).add(total);
            System.out.println("totalColumna: " + i + "..." + total);
        }
        return total;
    }

    private boolean esEntero(String num) {
        boolean band = false;
        try {
            Integer Intnum = Integer.parseInt(num);
            band = true;
        } catch (NumberFormatException e) {
        }
        return band;
    }
//Metodo para Validar el Formulario del Comprobante

    private String ValidarComprobante() {
        String msj = "";
        BigDecimal tDebe = BigDecimal.ZERO;
        BigDecimal tHaber = BigDecimal.ZERO;
        BigDecimal resul;
        resul = tDebe.subtract(tHaber);
        if (txtNumCompr.getText().equals("0")) {
            msj += "Debe Elegir el Mes e Ingresar el Numero de Comprobante\n";
        } else if (!this.esEntero(txtNumCompr.getText())) {
            msj += "El Numero del Comprobante debe ser de tipo Entero\n";
        } else if (dtm.getRowCount() == 0) {
            msj += "Por favor ingrese algun asiento.\n";
        } else if (resul != BigDecimal.ZERO) {
            msj += "Los totales no pueden ser distinto a cero(0), Corrija por favor.\n";
        }
        return msj;
    }

    //Metodo para llenar el VALUE de la sentencia INSERT
    private String CrearSentencia(DefaultTableModel modelo) {
        String dia = "", years;
        int mes;
        mes = NroMes();
        if (mes == 2) {
            dia = "28";
        } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            dia = "30";
        } else {
            dia = "31";
        }
        years = etiYear.getText();
        String sql = "";
        String cuenta, auxiliar, concepto;
        String codigo, fechaComp;
        int comprb;
        BigDecimal debe;
        BigDecimal haber;
        //Asigno los datos de la tabla a las variables correspondientes
        for (int i = 0; i < modelo.getRowCount(); i++) {
            cuenta = (String) modelo.getValueAt(i, 0);
            auxiliar = (String) modelo.getValueAt(i, 1);
            concepto = (String) modelo.getValueAt(i, 2);
            debe = new BigDecimal(dtm.getValueAt(i, 3).toString());
            haber = new BigDecimal(dtm.getValueAt(i, 4).toString());
            codigo = empresa.getText();
            comprb = Integer.parseInt(txtNumCompr.getText());
            fechaComp = dia + "/" + mes + "/" + years;
            if (i == (modelo.getRowCount() - 1)) {
                sql += " ('" + codigo + "', '" + cuenta + "','" + auxiliar + "','" + comprb + "',"
                        + "STR_TO_DATE('" + fechaComp + "','%d/%m/%Y'),'" + concepto + "','" + debe + "','" + haber + "',NULL) ";
            } else {
                sql += " ('" + codigo + "','" + cuenta + "','" + auxiliar + "','" + comprb + "',"
                        + "STR_TO_DATE('" + fechaComp + "','%d/%m/%Y'),'" + concepto + "','" + debe + "','" + haber + "',NULL), ";
            }
        }
        System.out.println("Crear Sentencia: " + sql);
        return sql;
    }

    private void RegistrarComprobante() {
        String sql = "";
        sql += "INSERT INTO `mh_comprobante` (`mh_comprobante`.`emp_id`,`mh_comprobante`.`cta_id`,`mh_comprobante`.`aux_id`,"
                + "`mh_comprobante`.`comp_id`, `mh_comprobante`.`comp_fecha`,`mh_comprobante`.`comp_concepto`,"
                + "`mh_comprobante`.`comp_debe`,`mh_comprobante`.`comp_haber`,`mh_comprobante`.`comp_linea`) "
                + "VALUES" + this.CrearSentencia(dtm);
        try {
            bd = ConnAccounting.getInstance();
            bd.ejecutar(sql);
            System.out.print("Registrar Comprobante: " + sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println("Error: " + e);
        }
    }

    public void montoFocus() {
        if (this.radioDebe.isSelected()) {
            this.txtMonto.requestFocus();
        } else if (this.radioHaber.isSelected()) {
            this.txtMonto.requestFocus();
        }
    }

    public void vaciarTabla(DefaultTableModel d) {
        for (int i = d.getRowCount() - 1; i >= 0; i--) {
            d.removeRow(i);
        }
    }

    //Metodo para Mostrar el Total de la Suma de las Columnas Debe y Haber
    private String mostrarTotal() {
        String msj = "";
        BigDecimal tDebe;
        BigDecimal tHaber;
        BigDecimal resul;
        tDebe = this.totalColumna(3);
        tHaber = this.totalColumna(4);
        resul = tDebe.subtract(tHaber);
        msj += "Existe una Diferencia de:\n" + (resul);
        return msj;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        debeHaber = new javax.swing.ButtonGroup();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cBoxCompMes = new javax.swing.JComboBox();
        etiYear = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNumCompr = new javax.swing.JLabel();
        etiCuenta = new javax.swing.JLabel();
        etiAux = new javax.swing.JLabel();
        etiqCuenta = new javax.swing.JLabel();
        cmboCuenta = new javax.swing.JComboBox();
        etiqCuenta1 = new javax.swing.JLabel();
        cmboAuxiliar = new javax.swing.JComboBox();
        cmb_concepto = new javax.swing.JComboBox();
        etiqCuenta2 = new javax.swing.JLabel();
        txtConcepto = new javax.swing.JTextField();
        btn_concepto = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        etiqCuenta3 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        radioDebe = new javax.swing.JRadioButton();
        radioHaber = new javax.swing.JRadioButton();
        btnAceptar = new javax.swing.JButton();
        conId = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnCalcular = new javax.swing.JButton();
        empresa = new javax.swing.JLabel();
        si = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();

        jMenuItem1.setText("Eliminar Asiento");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 51, 51), null, null));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registro de Comprobantes");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cargar Asiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Fecha: ");

        cBoxCompMes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cBoxCompMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cBoxCompMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cBoxCompMesActionPerformed(evt);
            }
        });

        etiYear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiYear.setText("AnnoFiscal");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Comprobante Nro.");

        txtNumCompr.setBackground(new java.awt.Color(255, 255, 255));
        txtNumCompr.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtNumCompr.setText("0");

        etiCuenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiCuenta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiCuenta.setText("NombreCuenta");

        etiAux.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiAux.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiAux.setText("NombreAux");

        etiqCuenta.setBackground(java.awt.Color.black);
        etiqCuenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiqCuenta.setText("Cuenta");

        cmboCuenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmboCuenta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        cmboCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmboCuentaActionPerformed(evt);
            }
        });

        etiqCuenta1.setBackground(java.awt.Color.black);
        etiqCuenta1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiqCuenta1.setText("Auxiliar");

        cmboAuxiliar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmboAuxiliar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        cmboAuxiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmboAuxiliarActionPerformed(evt);
            }
        });

        cmb_concepto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_concepto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        cmb_concepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_conceptoActionPerformed(evt);
            }
        });

        etiqCuenta2.setBackground(java.awt.Color.black);
        etiqCuenta2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiqCuenta2.setText("Concepto");

        txtConcepto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConceptoKeyTyped(evt);
            }
        });

        btn_concepto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_concepto.setText("√");
        btn_concepto.setToolTipText("Guardar Concepto");
        btn_concepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_conceptoActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("X");
        jButton1.setToolTipText("Guardar Concepto");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(etiCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiAux, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(179, 179, 179))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cBoxCompMes, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(etiYear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(etiqCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmboCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(etiqCuenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmboAuxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumCompr, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_concepto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(etiqCuenta2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtConcepto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_concepto, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cBoxCompMes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiYear, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumCompr)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiCuenta)
                    .addComponent(etiAux))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmboCuenta, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmboAuxiliar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmb_concepto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(etiqCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(etiqCuenta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiqCuenta2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_concepto))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingresar Monto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        etiqCuenta3.setBackground(java.awt.Color.black);
        etiqCuenta3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiqCuenta3.setText("Monto");

        txtMonto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMonto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoKeyTyped(evt);
            }
        });

        radioDebe.setBackground(new java.awt.Color(255, 255, 255));
        debeHaber.add(radioDebe);
        radioDebe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        radioDebe.setText("Debe");

        radioHaber.setBackground(new java.awt.Color(255, 255, 255));
        debeHaber.add(radioHaber);
        radioHaber.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        radioHaber.setText("Haber");

        btnAceptar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        conId.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        conId.setText("conID");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiqCuenta3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(conId, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMonto, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(radioDebe)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(radioHaber)))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioDebe)
                    .addComponent(radioHaber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiqCuenta3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(conId))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comprobante", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tabla.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla.setComponentPopupMenu(jPopupMenu2);
        tabla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabla.setInheritsPopupMenu(true);
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );

        btnCalcular.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCalcular.setText("Calcular");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        empresa.setText("jLabel6");

        si.setText("si");

        btnRegistrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrar.setMnemonic('R');
        btnRegistrar.setText("Registrar Comprobante");
        btnRegistrar.setToolTipText("ALT+R");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(empresa)
                        .addGap(18, 18, 18)
                        .addComponent(si)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistrar)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(empresa)
                        .addComponent(si)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmboCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboCuentaActionPerformed
        cmboAuxiliar.removeAllItems();
        this.LlenarCmboAux();
    }//GEN-LAST:event_cmboCuentaActionPerformed

    private void cmboAuxiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboAuxiliarActionPerformed
        String sql = "SELECT * FROM mh_auxiliar Where emp_id='" + empresa.getText() + "' AND cta_id = '" + cmboCuenta.getSelectedItem() + "' "
                + "AND aux_id='" + cmboAuxiliar.getSelectedItem() + "'";
        ConnAccounting bd = ConnAccounting.getInstance();
        ResultSet rs = bd.buscar(sql);
        try {
            if (rs.next()) {
                etiAux.setText(rs.getString("aux_nombreAux"));
                etiAux.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(comprobantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmboAuxiliarActionPerformed

    private void cmb_conceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_conceptoActionPerformed
        // TODO add your handling code here:
        Object a = cmb_concepto.getSelectedItem();
        txtConcepto.setText("" + a);
    }//GEN-LAST:event_cmb_conceptoActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        //Se Valida el Formulario:
        String mensaje = this.validarForm();
        if (mensaje.equals("")) {
            this.AgregarMonto();//Agrego el Monto a la Tabla
            this.LimpiarForm();//Limpio los campos del Formulario
            this.debeHaber.clearSelection();
            this.cmboCuenta.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, mensaje, "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        BigDecimal a = this.totalColumna(3);
        BigDecimal b = this.totalColumna(4);
        System.out.println("Total Columna debe: " + a + " Total Columna haber: " + b);
        double resta = (a.doubleValue()) - (b.doubleValue());
        System.out.println("Total Registro: " + resta);
        if (resta == 0) {
            String msj = this.ValidarComprobante();
            if (msj.equals("")) {
                this.RegistrarComprobante();
                this.vaciarTabla(dtm);
                JOptionPane.showMessageDialog(null, "Comprobante registrado satisfactoriamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                this.update(this.getGraphics());
                this.ComprNro();
                this.cmboCuenta.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, msj, "Error de Registro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, this.mostrarTotal(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            if (resta == 0) {
                this.btnRegistrar.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCalcularActionPerformed

    @SuppressWarnings("unchecked")
    private void btn_conceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_conceptoActionPerformed
        if (JOptionPane.showOptionDialog(this, "Desea Guardar este Concepto?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"Si", "NO"}, "No") == 0) {
            String sql = "INSERT INTO `mh_concepto`(`emp_id`, `conc_id`, `conc_descripcion`)VALUES(";
            sql = sql + "'" + empresa.getText() + "',NULL,'" + txtConcepto.getText() + "')";
            System.out.println(sql);
            bd.ejecutar(sql);
            JOptionPane.showMessageDialog(null, "CONCEPTO GUARDADO");
            cmb_concepto.removeAllItems();
            this.update(this.getGraphics());

            try {
                String sql1 = "SELECT * FROM `mh_concepto` WHERE `emp_id`='" + empresa.getText() + "' ORDER BY `conc_descripcion` ASC";
                ResultSet result1 = bd.buscar(sql1);
                while (result1.next()) {
                    cmb_concepto.addItem(result1.getObject(3));
                    conId.setText(result1.getString(2));
                }
            } catch (Exception e) {
            }
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }//GEN-LAST:event_btn_conceptoActionPerformed

    @SuppressWarnings("unchecked")
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String a = "¿Desea Eliminar estos Datos? Esta Opcion No es Reversible!";
        if (JOptionPane.showOptionDialog(this, a, "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"Si", "NO"}, "No") == 0) {
            String sql = "DELETE FROM `mh_concepto` WHERE `emp_id`='" + empresa.getText() + "' AND `conc_descripcion`='" + txtConcepto.getText() + "'";
            bd.eliminar(sql);
            JOptionPane.showMessageDialog(this, "Concepto Eliminado");
            cmb_concepto.removeAllItems();
            update(getGraphics());
            System.out.println(sql);

            try {
                String sql1 = "SELECT * FROM `mh_concepto` WHERE `emp_id`='" + empresa.getText() + "' ORDER BY `conc_descripcion` ASC";
                ResultSet result1 = bd.buscar(sql1);
                while (result1.next()) {
                    cmb_concepto.addItem(result1.getObject(3));
                    conId.setText(result1.getString(2));
                }
            } catch (Exception e) {
            }
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cBoxCompMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cBoxCompMesActionPerformed
        this.ComprNro();
    }//GEN-LAST:event_cBoxCompMesActionPerformed

    private void txtConceptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConceptoKeyTyped
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[0-9, ,A-Z]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtConceptoKeyTyped

    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[.0-9]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtMontoKeyTyped

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        String mensaje = this.validarForm();
        if (mensaje.equals("")) {
            this.AgregarMonto();//Agrego el Monto a la Tabla
            this.LimpiarForm();//Limpio los campos del Formulario
            this.debeHaber.clearSelection();
            this.cmboCuenta.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, mensaje, "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtMontoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int fila;
        DefaultTableModel modelo;
        try {
            //Obtengo la fila
            fila = this.tabla.getSelectedRow();
            if (fila == -1) {
                javax.swing.JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila");
            } else {
                //Obtengo los datos de la fila
                modelo = (DefaultTableModel) this.tabla.getModel();
                //Confirmo la eliminacion del registro
                int resp = javax.swing.JOptionPane.showConfirmDialog(rootPane, "¿Esta seguro de eliminar este asiento "
                        + (String) modelo.getValueAt(fila, 2) + " ?", "Indique Acción",
                        javax.swing.JOptionPane.YES_NO_OPTION);
                //Elimino
                if (resp == 0) {
                    this.dtm.removeRow(fila);
                }
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(comprobantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(comprobantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(comprobantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(comprobantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new comprobantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btn_concepto;
    private javax.swing.JComboBox cBoxCompMes;
    public javax.swing.JComboBox cmb_concepto;
    public javax.swing.JComboBox cmboAuxiliar;
    public javax.swing.JComboBox cmboCuenta;
    public javax.swing.JLabel conId;
    private javax.swing.ButtonGroup debeHaber;
    public javax.swing.JLabel empresa;
    public javax.swing.JLabel etiAux;
    public javax.swing.JLabel etiCuenta;
    public javax.swing.JLabel etiYear;
    public javax.swing.JLabel etiqCuenta;
    public javax.swing.JLabel etiqCuenta1;
    public javax.swing.JLabel etiqCuenta2;
    public javax.swing.JLabel etiqCuenta3;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton radioDebe;
    private javax.swing.JRadioButton radioHaber;
    private javax.swing.JLabel si;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtConcepto;
    private javax.swing.JTextField txtMonto;
    public javax.swing.JLabel txtNumCompr;
    // End of variables declaration//GEN-END:variables
}
