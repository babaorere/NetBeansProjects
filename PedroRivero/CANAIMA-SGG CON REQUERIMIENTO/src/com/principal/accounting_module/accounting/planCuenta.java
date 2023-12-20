package com.principal.accounting_module.accounting;

import com.principal.account_module.main.AccountingMain;
import com.principal.accounting_module.Conexion.ConnAccounting;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Ing. Cadenas Joel
 */
public final class planCuenta extends javax.swing.JFrame {

    AccountingMain p = new AccountingMain(null);

    public planCuenta() {
        initComponents();
        this.empresa.setVisible(false);
        this.fechaSql.setVisible(false);
        this.DeshabilitarAuxiliar();
        this.Habilitar();
    }

    public void DeshabilitarAuxiliar() {
        this.txtCodAux.setEnabled(false);
        this.btnIngresarAux.setEnabled(false);
        this.txtNomAux.setEnabled(false);
        this.btnModificarAux.setEnabled(false);
        this.btnEliminarAux.setEnabled(false);
        this.cmbListaAux.setEnabled(false);
    }

    ////Metodo que Habilita el Formulario Auxiliar
    public void Habilitar() {
        String si = this.cmbCondicion.getSelectedItem().toString();
        if (si.equals("Si")) {
            this.txtCodAux.setEnabled(true);
            this.btnIngresarAux.setEnabled(true);
            this.txtNomAux.setEnabled(true);
            this.btnModificarAux.setEnabled(true);
            this.btnEliminarAux.setEnabled(true);
            this.cmbListaAux.setEnabled(true);
        } else {
            this.DeshabilitarAuxiliar();
        }
    }

    //Metodo que Vacia el Contenido de los campos del Formulario Cuentas
    public void LimpiarCuenta() {
        txtCuenta.setText("");
        txtNombre.setText("");
        cmbTipo.setSelectedItem("Seleccione");
        cmbCondicion.setSelectedItem("Seleccione");
        this.DeshabilitarAuxiliar();
    }

    public void Insert1() {
        ConnAccounting objeto;
        objeto = ConnAccounting.getInstance();
        String cta = txtCuenta.getText();
        //        Primero Validamos si el Codigo de la Cuenta existe
        if (objeto.existeDato("mh_cuentas", "cta_id", cta, 1)) {
            //            JOptionPane.showMessageDialog(null, "El codigo de la Cuenta ya Existe");
        } else {
            String sql;
            sql = "INSERT INTO mh_cuentas (cta_id )VALUES ('" + cta + "')";
            objeto.ejecutar(sql);
        }
    }

    public void Insert2() {
        ConnAccounting bd = ConnAccounting.getInstance();
        String emp = empresa.getText(), ctaID = txtCuenta.getText(), ctaNomb = txtNombre.getText();
        String tipo = cmbTipo.getSelectedItem().toString(), cond = cmbCondicion.getSelectedItem().toString();
        String auxID = txtCodAux.getText(), auxNomb = txtNomAux.getText();
        if (bd.existeDato2("mh_auxiliar", "emp_id", "cta_id", emp, ctaID, 1)) {
            JOptionPane.showMessageDialog(null, "El codigo de la Cuenta ya  esta asociado a esta Empresa\n " + "Por Favor, Ingrese Otro Codigo");
        } else {
            String sql = "INSERT INTO mh_auxiliar (emp_id,cta_id ,aux_nombreCta,aux_tipoCta,aux_condicion,aux_id,aux_nombreAux)" + "VALUES ('" + emp + "','" + ctaID + "','" + ctaNomb + "','" + tipo + "','" + cond + "','" + auxID + "','" + auxNomb + "')";
            bd.ejecutar(sql);
            JOptionPane.showOptionDialog(this, "Cuenta Registrada \n Satisfactoriamente", "Ingreso Exitoso", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "OK" }, "Ok");
        }
    }

    public void InsertAux() {
        ConnAccounting bd = ConnAccounting.getInstance();
        String emp = empresa.getText(), ctaID = txtCuenta.getText(), ctaNomb = txtNombre.getText();
        String tipo = cmbTipo.getSelectedItem().toString(), cond = cmbCondicion.getSelectedItem().toString();
        String auxID = txtCodAux.getText(), auxNomb = txtNomAux.getText();
        if (bd.existeDato3("mh_auxiliar", "emp_id", "cta_id", "aux_id", emp, ctaID, auxID, 1)) {
            JOptionPane.showMessageDialog(null, "El codigo del Auxiliar ya  esta asociado a esta Cuenta\n " + "Por Favor, Ingrese Otro Codigo");
        } else {
            String sql = "INSERT INTO mh_auxiliar (emp_id,cta_id ,aux_nombreCta,aux_tipoCta,aux_condicion,aux_id,aux_nombreAux)" + "VALUES ('" + emp + "','" + ctaID + "','" + ctaNomb + "','" + tipo + "','" + cond + "','" + auxID + "','" + auxNomb + "')";
            bd.ejecutar(sql);
            JOptionPane.showOptionDialog(this, "Auxiliar Registrado \n Satisfactoriamente", "Ingreso Exitoso", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "OK" }, "Ok");
            this.txtCodAux.requestFocus();
        }
    }

    public void inicializarCuentas() {
        ConnAccounting bd = ConnAccounting.getInstance();
        String emp = empresa.getText(), fecha = fechaSql.getText();
        String cuenta = txtCuenta.getText(), auxiliar = txtCodAux.getText();
        int num = 0;
        double cantidad = 0.0;
        String tipo = this.cmbTipo.getSelectedItem().toString();
        String condicion = this.cmbCondicion.getSelectedItem().toString();
        if (condicion.equals("No") && tipo.equals("De Movimiento")) {
            String sql = "";
            sql += "INSERT INTO `mh_comprobante` (`mh_comprobante`.`emp_id`,`mh_comprobante`.`cta_id`,`mh_comprobante`.`aux_id`," + "`mh_comprobante`.`comp_id`, `mh_comprobante`.`comp_fecha`,`mh_comprobante`.`comp_concepto`," + "`mh_comprobante`.`comp_debe`,`mh_comprobante`.`comp_haber`,`mh_comprobante`.`comp_linea`) " + "VALUES('" + emp + "','" + cuenta + "','','" + num + "',STR_TO_DATE('" + fecha + "','%d/%m/%Y'),'Comprobante Inicial'," + "'" + cantidad + "','" + cantidad + "',NULL)";
            bd.ejecutar(sql);
            System.out.print("Inicializando Cuentas: " + sql);
        }
    }

    public void inicializarAuxiliar() {
        ConnAccounting bd = ConnAccounting.getInstance();
        String emp = empresa.getText(), fecha = fechaSql.getText();
        String cuenta = txtCuenta.getText(), auxiliar = txtCodAux.getText();
        int num = 0;
        double cantidad = 0.0;
        String tipo = this.cmbTipo.getSelectedItem().toString();
        String condicion = this.cmbCondicion.getSelectedItem().toString();
        if (condicion.equals("Si") && tipo.equals("De Movimiento")) {
            String sql = "";
            sql += "INSERT INTO `mh_comprobante` (`mh_comprobante`.`emp_id`,`mh_comprobante`.`cta_id`,`mh_comprobante`.`aux_id`," + "`mh_comprobante`.`comp_id`, `mh_comprobante`.`comp_fecha`,`mh_comprobante`.`comp_concepto`," + "`mh_comprobante`.`comp_debe`,`mh_comprobante`.`comp_haber`,`mh_comprobante`.`comp_linea`) " + "VALUES('" + emp + "','" + cuenta + "','" + auxiliar + "','" + num + "',STR_TO_DATE('" + fecha + "','%d/%m/%Y'),'Comprobante Inicial'," + "'" + cantidad + "','" + cantidad + "',NULL)";
            bd.ejecutar(sql);
            System.out.print("Inicializando Auxiliar: " + sql);
        }
    }

    //Metodo para Ingresar los datos del Formulario Cuentas en la ConnAccounting
    private String validarcuenta() {
        String msj = "";
        if (this.txtCuenta.getText().equals("")) {
            msj += " Debe Colocar codigo de la cuenta";
        } else if (this.txtNombre.getText().equals("") || this.txtNombre.getText().length() > 100) {
            msj += "Coloque el Nombre de la empresa que no exceda de los 100 caracteres";
        }
        return msj;
    }

    //Metodo para Consultar los datos de la ConnAccounting para el Formulario Cuentas
    public void BuscarCuenta() {
        this.cmbListaAux.removeAllItems();
        String cta = txtCuenta.getText();
        String cod = empresa.getText();
        String sql = "SELECT  * FROM `mh_auxiliar` WHERE `emp_id` = '" + cod + "' AND `cta_id` = '" + cta + "'";
        ConnAccounting bd = ConnAccounting.getInstance();
        ResultSet resul = bd.buscar(sql);
        try {
            if (resul.next()) {
                txtNombre.setText(resul.getString(3));
                cmbTipo.setSelectedItem(resul.getString(4));
                cmbCondicion.setSelectedItem(resul.getString(5));
            } else {
                JOptionPane.showOptionDialog(this, "Cuenta No Asociada\n Por Favor Ingrese los Datos", "Error", JOptionPane.ERROR_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Aceptar" }, "Aceptar");
                txtNombre.setText("");
                cmbTipo.setSelectedItem("Seleccione");
                cmbCondicion.setSelectedItem("Seleccione");
                this.DeshabilitarAuxiliar();
                System.out.println("EmpresaID: " + cod + " ");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
            logger.error(e);
        }
    }

    public void ModificarCuenta() {
        if (JOptionPane.showOptionDialog(this, "Desea Modificar estos Datos?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] { "Si", "NO" }, "No") == 0) {
            String sql;
            sql = "UPDATE mh_auxiliar SET aux_nombreCta = '" + txtNombre.getText() + "',";
            sql += "cta_id='" + txtCuenta.getText() + "',";
            sql += "aux_tipoCta='" + cmbTipo.getSelectedItem() + "',";
            sql += "aux_condicion='" + cmbCondicion.getSelectedItem() + "'";
            sql += "WHERE cta_id = '" + txtCuenta.getText() + "' ";
            sql += "AND emp_id = '" + empresa.getText() + "'";
            JOptionPane.showMessageDialog(null, "Datos Modificados");
            //muestra si la instruccion sql esta bien contruida
            System.out.println(sql);
            ConnAccounting bd = ConnAccounting.getInstance();
            bd.ejecutar(sql);
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }

    //Metodo para Eliminar una Cuenta de la ConnAccounting
    public void EliminarCuenta() {
        String a = "¿Desea Eliminar estos Datos? Esta Opcion No es Reversible!";
        String b = "Confirmar";
        if (JOptionPane.showOptionDialog(this, a, b, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] { "Si", "No" }, "NO") == 0) {
            String sql;
            sql = "DELETE FROM mh_auxiliar WHERE cta_id='" + txtCuenta.getText() + "' AND emp_id ='" + empresa.getText() + "';";
            ConnAccounting bd = ConnAccounting.getInstance();
            bd.eliminar(sql);
            JOptionPane.showMessageDialog(this, "Datos Eliminados");
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }

    ////////////////Fin de los metodos para las cuentas/////////////////
    private String valdaAux() {
        String msj = "";
        if (this.txtCodAux.getText().equals("")) {
            msj += "Debe Ingresar el Codigo del Auxiliar\n";
        } else if (this.txtNomAux.getText().equals("") || this.txtNomAux.getText().length() > 50) {
            msj += "Ingrese un Nombre al Auxiliar que no Exeda los 50 caracteres";
        }
        return msj;
    }

    public void BuscarAuxiliar() {
        String sql = "SELECT * FROM mh_auxiliar ";
        sql = sql + "WHERE emp_id='" + empresa.getText() + "' ";
        sql = sql + "AND cta_id='" + txtCuenta.getText() + "'";
        sql = sql + "AND aux_id = '" + cmbListaAux.getSelectedItem() + "'";
        ConnAccounting objeto = ConnAccounting.getInstance();
        ResultSet resul = objeto.buscar(sql);
        try {
            if (resul.next()) {
                txtCodAux.setText(cmbListaAux.getSelectedItem().toString());
                txtNomAux.setText(resul.getString(7));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
            logger.error(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void LlenarComboAuxilar() {
        try {
            String sql = "SELECT * FROM mh_auxiliar WHERE emp_id='" + empresa.getText() + "' AND cta_id='" + txtCuenta.getText() + "'";
            ConnAccounting objeto = ConnAccounting.getInstance();
            ResultSet resul = objeto.buscar(sql);
            while (resul.next()) {
                cmbListaAux.addItem(resul.getObject("aux_id"));
            }
            cmbListaAux.setVisible(true);
        } catch (Exception ee) {
            System.out.println(ee);
            logger.error(ee);
        }
    }

    //Metodo para Modificar la Tabla Auxiliares en la ConnAccounting
    public void ModificarAuxiliar() {
        if (JOptionPane.showOptionDialog(this, "Desea Modificar estos Datos?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Si", "NO" }, "No") == 0) {
            String sql = "UPDATE mh_auxiliar SET aux_id = '" + txtCodAux.getText() + "',";
            sql += "aux_nombreAux = '" + txtNomAux.getText() + "'";
            sql += "WHERE emp_id = '" + empresa.getText() + "'";
            sql += "AND cta_id='" + txtCuenta.getText() + "'";
            sql += "AND aux_id='" + txtCodAux.getText() + "'";
            JOptionPane.showMessageDialog(null, "Datos Modificados");
            ConnAccounting bd = ConnAccounting.getInstance();
            bd.ejecutar(sql);
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }

    public void EliminarAuxiliar() {
        String a = "¿Desea Eliminar este Auxiliar? Esta Opcion No es Reversible!";
        String b = "Confirmar";
        String cta = txtCuenta.getText();
        String codigo = empresa.getText();
        String aux = txtCodAux.getText();
        if (JOptionPane.showOptionDialog(this, a, b, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, new Object[] { "Si", "No" }, "NO") == 0) {
            String sql;
            sql = "DELETE FROM mh_auxiliar WHERE emp_id = '" + codigo + "' AND cta_id  = '" + cta + "' AND aux_id = '" + aux + "' ";
            ConnAccounting bd = ConnAccounting.getInstance();
            bd.eliminar(sql);
            JOptionPane.showMessageDialog(this, "Auxiliar Eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }

    public void btnRegistrar() {
        String si = this.cmbCondicion.getSelectedItem().toString();
        if (si.equals("Si")) {
            String msj = this.validarcuenta();
            if (msj.equals("")) {
                this.Insert1();
                this.Insert2();
                this.inicializarCuentas();
                this.Habilitar();
                this.txtCodAux.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, msj, "Mensaje", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            String msj = this.validarcuenta();
            if (msj.equals("")) {
                this.Insert1();
                this.Insert2();
                this.inicializarCuentas();
                this.Habilitar();
                this.LimpiarCuenta();
                this.txtCuenta.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, msj, "Mensaje", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void NuevoAuxiliar() {
        txtCodAux.setText("");
        txtNomAux.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCuenta = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        cmbTipo = new javax.swing.JComboBox();
        cmbCondicion = new javax.swing.JComboBox();
        btnBuscar = new javax.swing.JButton();
        btnIngresarCuenta = new javax.swing.JButton();
        empresa = new javax.swing.JLabel();
        fechaSql = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnEditarCuenta = new javax.swing.JButton();
        btnEliminarCuenta = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbListaAux = new javax.swing.JComboBox();
        txtCodAux = new javax.swing.JTextField();
        txtNomAux = new javax.swing.JTextField();
        btnIngresarAux = new javax.swing.JButton();
        btnNuevoAux = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnModificarAux = new javax.swing.JButton();
        btnEliminarAux = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registro de Cuentas y Auxiliares");
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE).addContainerGap()));
        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        // NOI18N
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Crear cuentas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        // NOI18N
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("Codigo de la Cuenta");
        // NOI18N
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel3.setText("Nombre de la Cuenta");
        // NOI18N
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel4.setText("Tipo de la Cuenta");
        // NOI18N
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel5.setText("Tiene Auxiliar?");
        // NOI18N
        txtCuenta.setFont(new java.awt.Font("Tahoma", 1, 12));
        txtCuenta.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCuentaActionPerformed(evt);
            }
        });
        txtCuenta.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCuentaKeyTyped(evt);
            }
        });
        // NOI18N
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 12));
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        // NOI18N
        cmbTipo.setFont(new java.awt.Font("Tahoma", 1, 12));
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "De Titulo", "De Movimiento", "De Totales" }));
        cmbTipo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });
        // NOI18N
        cmbCondicion.setFont(new java.awt.Font("Tahoma", 1, 12));
        cmbCondicion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Si", "No" }));
        cmbCondicion.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCondicionActionPerformed(evt);
            }
        });
        // NOI18N
        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        // NOI18N
        btnIngresarCuenta.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnIngresarCuenta.setText("Registrar");
        btnIngresarCuenta.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarCuentaActionPerformed(evt);
            }
        });
        empresa.setText("jLabel6");
        fechaSql.setText("jLabel6");
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(cmbCondicion, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(cmbTipo, javax.swing.GroupLayout.Alignment.LEADING, 0, 187, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnIngresarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))).addGroup(jPanel3Layout.createSequentialGroup().addComponent(empresa).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(fechaSql).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(empresa).addComponent(fechaSql)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(cmbTipo, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE).addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGap(16, 16, 16).addComponent(cmbCondicion, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)).addGroup(jPanel3Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(btnIngresarCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))).addContainerGap()));
        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        // NOI18N
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Accion", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        // NOI18N
        btnEditarCuenta.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnEditarCuenta.setText("Editar");
        btnEditarCuenta.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCuentaActionPerformed(evt);
            }
        });
        // NOI18N
        btnEliminarCuenta.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnEliminarCuenta.setText("Eliminar");
        btnEliminarCuenta.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCuentaActionPerformed(evt);
            }
        });
        // NOI18N
        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(btnEliminarCuenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnEditarCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnEditarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnEliminarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        // NOI18N
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Asignar auxiliar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        // NOI18N
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel10.setText("Auxiliares Asociados a la Cuenta");
        // NOI18N
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setText("Codigo");
        // NOI18N
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel9.setText("Nombre ");
        // NOI18N
        cmbListaAux.setFont(new java.awt.Font("Tahoma", 1, 12));
        cmbListaAux.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Auxiliares--" }));
        cmbListaAux.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbListaAuxActionPerformed(evt);
            }
        });
        // NOI18N
        txtCodAux.setFont(new java.awt.Font("Tahoma", 1, 12));
        txtCodAux.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodAuxKeyTyped(evt);
            }
        });
        // NOI18N
        txtNomAux.setFont(new java.awt.Font("Tahoma", 1, 12));
        txtNomAux.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomAuxActionPerformed(evt);
            }
        });
        txtNomAux.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomAuxKeyTyped(evt);
            }
        });
        // NOI18N
        btnIngresarAux.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnIngresarAux.setText("Registrar");
        btnIngresarAux.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarAuxActionPerformed(evt);
            }
        });
        // NOI18N
        btnNuevoAux.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnNuevoAux.setText("Nuevo");
        btnNuevoAux.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoAuxActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel5Layout.createSequentialGroup().addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(btnNuevoAux, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel5Layout.createSequentialGroup().addComponent(txtCodAux, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(10, 10, 10).addComponent(jLabel9))))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtNomAux).addComponent(cmbListaAux, 0, 235, Short.MAX_VALUE))).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(btnIngresarAux, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel5Layout.createSequentialGroup().addComponent(cmbListaAux).addGap(2, 2, 2))).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtCodAux, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtNomAux, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnIngresarAux, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(btnNuevoAux, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))));
        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        // NOI18N
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Accion", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        // NOI18N
        btnModificarAux.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnModificarAux.setText("Editar");
        btnModificarAux.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarAuxActionPerformed(evt);
            }
        });
        // NOI18N
        btnEliminarAux.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnEliminarAux.setText("Eliminar");
        btnEliminarAux.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAuxActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(btnEliminarAux, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE).addComponent(btnModificarAux, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addComponent(btnModificarAux, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(btnEliminarAux, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(10, Short.MAX_VALUE)));
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pack();
    }

    // </editor-fold>//GEN-END:initComponents
    private void txtCuentaActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_txtCuentaActionPerformed
        BuscarCuenta();
        Habilitar();
        this.LlenarComboAuxilar();
    }

    //GEN-LAST:event_txtCuentaActionPerformed
    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_cmbTipoActionPerformed
        // TODO add your handling code here:
    }

    //GEN-LAST:event_cmbTipoActionPerformed
    private void btnEditarCuentaActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnEditarCuentaActionPerformed
        this.ModificarCuenta();
        this.Habilitar();
        this.txtCuenta.requestFocus();
    }

    //GEN-LAST:event_btnEditarCuentaActionPerformed
    private void btnModificarAuxActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnModificarAuxActionPerformed
        this.ModificarAuxiliar();
        this.txtCodAux.requestFocus();
    }

    //GEN-LAST:event_btnModificarAuxActionPerformed
    private void cmbListaAuxActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_cmbListaAuxActionPerformed
        this.BuscarAuxiliar();
    }

    //GEN-LAST:event_cmbListaAuxActionPerformed
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnBuscarActionPerformed
        this.BuscarCuenta();
        this.Habilitar();
        this.LlenarComboAuxilar();
    }

    //GEN-LAST:event_btnBuscarActionPerformed
    private void btnIngresarCuentaActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnIngresarCuentaActionPerformed
        this.btnRegistrar();
    }

    //GEN-LAST:event_btnIngresarCuentaActionPerformed
    private void btnEliminarCuentaActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnEliminarCuentaActionPerformed
        this.EliminarCuenta();
        this.DeshabilitarAuxiliar();
        this.LimpiarCuenta();
        this.txtCuenta.requestFocus();
    }

    //GEN-LAST:event_btnEliminarCuentaActionPerformed
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnNuevoActionPerformed
        this.LimpiarCuenta();
        this.txtCuenta.requestFocus();
    }

    //GEN-LAST:event_btnNuevoActionPerformed
    private void btnIngresarAuxActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnIngresarAuxActionPerformed
        String msj = this.valdaAux();
        if (msj.equals("")) {
            this.InsertAux();
            this.inicializarAuxiliar();
            this.NuevoAuxiliar();
            cmbListaAux.removeAllItems();
            LlenarComboAuxilar();
            this.txtCodAux.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, msj, "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    //GEN-LAST:event_btnIngresarAuxActionPerformed
    private void btnEliminarAuxActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnEliminarAuxActionPerformed
        this.EliminarAuxiliar();
        this.txtCodAux.requestFocus();
        this.LlenarComboAuxilar();
    }

    //GEN-LAST:event_btnEliminarAuxActionPerformed
    private void txtNomAuxActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_txtNomAuxActionPerformed
        String msj = this.valdaAux();
        if (msj.equals("")) {
            this.InsertAux();
            this.inicializarAuxiliar();
            this.NuevoAuxiliar();
            cmbListaAux.removeAllItems();
            LlenarComboAuxilar();
        } else {
            JOptionPane.showMessageDialog(null, msj, "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    //GEN-LAST:event_txtNomAuxActionPerformed
    private void txtCuentaKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtCuentaKeyTyped
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[0-9]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }

    //GEN-LAST:event_txtCuentaKeyTyped
    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtNombreKeyTyped
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[ ,A-Z]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }

    //GEN-LAST:event_txtNombreKeyTyped
    private void txtCodAuxKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtCodAuxKeyTyped
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[0-9]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }

    //GEN-LAST:event_txtCodAuxKeyTyped
    private void txtNomAuxKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtNomAuxKeyTyped
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[ ,A-Z]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }

    //GEN-LAST:event_txtNomAuxKeyTyped
    private void btnNuevoAuxActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnNuevoAuxActionPerformed
        this.NuevoAuxiliar();
        this.txtCodAux.requestFocus();
    }

    //GEN-LAST:event_btnNuevoAuxActionPerformed
    private void cmbCondicionActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_cmbCondicionActionPerformed
        this.Habilitar();
    }

    //GEN-LAST:event_cmbCondicionActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            java.util.logging.Logger.getLogger(planCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            logger.error(ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(planCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            logger.error(ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(planCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            logger.error(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(planCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            logger.error(ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new planCuenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;

    private javax.swing.JButton btnEditarCuenta;

    private javax.swing.JButton btnEliminarAux;

    private javax.swing.JButton btnEliminarCuenta;

    private javax.swing.JButton btnIngresarAux;

    private javax.swing.JButton btnIngresarCuenta;

    private javax.swing.JButton btnModificarAux;

    private javax.swing.JButton btnNuevo;

    private javax.swing.JButton btnNuevoAux;

    private javax.swing.JComboBox cmbCondicion;

    private javax.swing.JComboBox cmbListaAux;

    private javax.swing.JComboBox cmbTipo;

    public javax.swing.JLabel empresa;

    public javax.swing.JLabel fechaSql;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel10;

    private javax.swing.JLabel jLabel2;

    private javax.swing.JLabel jLabel3;

    private javax.swing.JLabel jLabel4;

    private javax.swing.JLabel jLabel5;

    private javax.swing.JLabel jLabel8;

    private javax.swing.JLabel jLabel9;

    private javax.swing.JPanel jPanel1;

    private javax.swing.JPanel jPanel2;

    private javax.swing.JPanel jPanel3;

    private javax.swing.JPanel jPanel4;

    private javax.swing.JPanel jPanel5;

    private javax.swing.JPanel jPanel6;

    private javax.swing.JTextField txtCodAux;

    private javax.swing.JTextField txtCuenta;

    private javax.swing.JTextField txtNomAux;

    private javax.swing.JTextField txtNombre;

    // End of variables declaration//GEN-END:variables
    private static final Logger logger = LogManager.getLogger(planCuenta.class);
}
