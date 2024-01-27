package com.principal.account_module.main;

import com.principal.accounting_module.Conexion.ConnAccounting;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class empresas extends javax.swing.JFrame {

    ConnAccounting bd = ConnAccounting.getInstance();

    public empresas() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.etiFecha.setVisible(false);
        this.mesIni.setVisible(false);
        this.nombreMes.setVisible(false);
        this.rrr.setVisible(false);
        Fecha();
    }

    public void Fecha() {
        Calendar cal = Calendar.getInstance();
        String fech = cal.get(cal.DATE) + "/" + (cal.get(cal.MONTH) + 1) + "/" + cal.get(cal.YEAR);
        //        String hora = cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE) + ":" + cal.get(cal.SECOND);
        this.rrr.setText(fech);
    }

    public int NroMes() {
        int mes = 0;
        if (cBoxCierre.getSelectedItem().equals("Enero")) {
            mes = 1;
        } else if (cBoxCierre.getSelectedItem().equals("Febrero")) {
            mes = 2;
        } else if (cBoxCierre.getSelectedItem().equals("Marzo")) {
            mes = 3;
        } else if (cBoxCierre.getSelectedItem().equals("Abril")) {
            mes = 4;
        } else if (cBoxCierre.getSelectedItem().equals("Mayo")) {
            mes = 5;
        } else if (cBoxCierre.getSelectedItem().equals("Junio")) {
            mes = 6;
        } else if (cBoxCierre.getSelectedItem().equals("Julio")) {
            mes = 7;
        } else if (cBoxCierre.getSelectedItem().equals("Agosto")) {
            mes = 8;
        } else if (cBoxCierre.getSelectedItem().equals("Septiembre")) {
            mes = 9;
        } else if (cBoxCierre.getSelectedItem().equals("Octubre")) {
            mes = 10;
        } else if (cBoxCierre.getSelectedItem().equals("Noviembre")) {
            mes = 11;
        } else if (cBoxCierre.getSelectedItem().equals("Diciembre")) {
            mes = 12;
        }
        return mes;
    }

    public int NroMesFin() {
        int mes = 0;
        if (cBoxInicio.getSelectedItem().equals("Enero")) {
            mes = 1;
        } else if (cBoxInicio.getSelectedItem().equals("Febrero")) {
            mes = 2;
        } else if (cBoxInicio.getSelectedItem().equals("Marzo")) {
            mes = 3;
        } else if (cBoxInicio.getSelectedItem().equals("Abril")) {
            mes = 4;
        } else if (cBoxInicio.getSelectedItem().equals("Mayo")) {
            mes = 5;
        } else if (cBoxInicio.getSelectedItem().equals("Junio")) {
            mes = 6;
        } else if (cBoxInicio.getSelectedItem().equals("Julio")) {
            mes = 7;
        } else if (cBoxInicio.getSelectedItem().equals("Agosto")) {
            mes = 8;
        } else if (cBoxInicio.getSelectedItem().equals("Septiembre")) {
            mes = 9;
        } else if (cBoxInicio.getSelectedItem().equals("Octubre")) {
            mes = 10;
        } else if (cBoxInicio.getSelectedItem().equals("Noviembre")) {
            mes = 11;
        } else if (cBoxInicio.getSelectedItem().equals("Diciembre")) {
            mes = 12;
        }
        return mes;
    }

    //    Metodo para llamar el nombre del mes en que termina el periodo fiscal en el combobox
    public void NombreMes(int nro) {
        if (nro == 1) {
            cBoxCierre.setSelectedItem("Enero");
        } else if (nro == 2) {
            cBoxCierre.setSelectedItem("Febrero");
        } else if (nro == 3) {
            cBoxCierre.setSelectedItem("Marzo");
        } else if (nro == 4) {
            cBoxCierre.setSelectedItem("Abril");
        } else if (nro == 5) {
            cBoxCierre.setSelectedItem("Mayo");
        } else if (nro == 6) {
            cBoxCierre.setSelectedItem("Junio");
        } else if (nro == 7) {
            cBoxCierre.setSelectedItem("Julio");
        } else if (nro == 8) {
            cBoxCierre.setSelectedItem("Agosto");
        } else if (nro == 9) {
            cBoxCierre.setSelectedItem("Septiembre");
        } else if (nro == 10) {
            cBoxCierre.setSelectedItem("Octubre");
        } else if (nro == 11) {
            cBoxCierre.setSelectedItem("Noviembre");
        } else if (nro == 12) {
            cBoxCierre.setSelectedItem("Diciembre");
        }
    }

    //    Metodo para llamar el nombre del mes en que Inicia el periodo fiscal en el combobox
    public void NombreMesIni(int nroIni) {
        if (nroIni == 1) {
            cBoxInicio.setSelectedItem("Enero");
        } else if (nroIni == 2) {
            cBoxInicio.setSelectedItem("Febrero");
        } else if (nroIni == 3) {
            cBoxInicio.setSelectedItem("Marzo");
        } else if (nroIni == 4) {
            cBoxInicio.setSelectedItem("Abril");
        } else if (nroIni == 5) {
            cBoxInicio.setSelectedItem("Mayo");
        } else if (nroIni == 6) {
            cBoxInicio.setSelectedItem("Junio");
        } else if (nroIni == 7) {
            cBoxInicio.setSelectedItem("Julio");
        } else if (nroIni == 8) {
            cBoxInicio.setSelectedItem("Agosto");
        } else if (nroIni == 9) {
            cBoxInicio.setSelectedItem("Septiembre");
        } else if (nroIni == 10) {
            cBoxInicio.setSelectedItem("Octubre");
        } else if (nroIni == 11) {
            cBoxInicio.setSelectedItem("Noviembre");
        } else if (nroIni == 12) {
            cBoxInicio.setSelectedItem("Diciembre");
        }
    }

    public void limpiar() {
        txtNombre.setText("");
        txtRif.setText("");
        txtYear.setText("");
        cBoxInicio.setSelectedItem(null);
        cBoxCierre.setSelectedItem(null);
    }

    private String Validar() {
        String msj = "";
        if (this.txtCodigo.getText().equals("")) {
            msj += "Debe Ingresar el Codigo de la Empresa";
        } else if (this.bd.existeDato("mh_empresas", "emp_id", txtCodigo.getText(), 1)) {
            msj += "El Codigo de la Empresa ya esta Registrado\n";
        } else if (this.txtNombre.getText().equals("") || this.txtNombre.getText().length() > 100) {
            msj += "Debe Ingresar un Nombre para la Empresa que No sobrepase los 100 Caracteres";
        } else if (this.txtRif.getText().equals("") || this.txtRif.getText().length() > 100) {
            msj += "Ingrese el RIF de la Empresa";
        } else if (this.cBoxInicio.getSelectedItem().equals("")) {
            msj += "Seleccione el Mes en que Inicia el Ejercicio Fiscal de la Empresa";
        } else if (this.cBoxCierre.getSelectedItem().equals("")) {
            msj += "Defina la Cantidad de Meses que tendra el Ejercicio fiscal de la Empresa";
        } else if (this.txtYear.getText().equals("") || this.txtYear.getText().length() > 4) {
            msj += "Debe Indicar el Año de Inicio Fiscal";
        }
        return msj;
    }

    public void Ingresar() {
        int mes = NroMesFin();
        int month = NroMes();
        String dia = "01";
        String diaF;
        if (month == 2) {
            diaF = "28";
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            diaF = "30";
        } else {
            diaF = "31";
        }
        //        String inicio = dia + "/" + mes + "/" + year.getYear();
        String inicio = txtYear.getText() + "/" + mes + "/" + dia;
        String fin = txtYearEnd.getText() + "/" + month + "/" + diaF;
        String sql;
        sql = "INSERT INTO `mh_empresas` (`emp_id`, `emp_nombre`, `emp_rif`, " + "`emp_inicioFiscal`, `emp_finFiscal`, `emp_fecha`)VALUES (";
        sql = sql + "'" + txtCodigo.getText() + "','" + txtNombre.getText() + "','" + txtRif.getText() + "',";
        sql = sql + "'" + inicio + "','" + fin + "'," + "STR_TO_DATE('" + rrr.getText() + "','%d/%m/%Y'));";
        System.out.println(sql + "\n Fecha Cierre: " + fin);
        bd.ejecutar(sql);
        JOptionPane.showMessageDialog(null, "INSERCION REALIZADA");
    }

    public void buscar() {
        int nro;
        int nroIni;
        String cod = txtCodigo.getText();
        String sql = "SELECT emp_id,emp_nombre,emp_rif," + "MONTH(emp_inicioFiscal),YEAR(emp_inicioFiscal),MONTH(emp_finFiscal)," + "YEAR(emp_finFiscal)" + " FROM mh_empresas WHERE emp_id='" + cod + "'";
        ResultSet resul = bd.buscar(sql);
        try {
            if (resul.next()) {
                txtNombre.setText(resul.getString(2));
                txtRif.setText(resul.getString(3));
                mesIni.setText(resul.getString(4));
                nroIni = Integer.parseInt(mesIni.getText());
                NombreMesIni(nroIni);
                txtYear.setText(resul.getString(5));
                nombreMes.setText(resul.getString(6));
                txtYearEnd.setText(resul.getString(7));
                nro = Integer.parseInt(nombreMes.getText());
                NombreMes(nro);
                System.out.println("Mes Inicio: " + mesIni.getText());
                System.out.println("Mes Cierre: " + nombreMes.getText());
            } else {
                limpiar();
                JOptionPane.showOptionDialog(this, "No esta Registrado\n Por Favor Ingrese los Datos", "Error", JOptionPane.ERROR_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Aceptar" }, "Aceptar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
            logger.error(e);
        }
    }

    //    metodo para modificar los datos en la base de datos
    public void Modificar() {
        int month = NroMes();
        String day = "";
        if (month == 2) {
            day = "28";
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = "30";
        } else {
            day = "31";
        }
        String dia = "01";
        String ano = txtYear.getText();
        String endYear = txtYearEnd.getText();
        int mes = NroMesFin();
        String inicio = ano + "/" + mes + "/" + dia;
        String fin = endYear + "/" + month + "/" + day;
        if (JOptionPane.showOptionDialog(this, "Desea Modificar estos Datos?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Si", "NO" }, "No") == 0) {
            String sql;
            sql = "UPDATE mh_empresas SET emp_id='" + txtCodigo.getText() + "',";
            sql += "emp_nombre='" + txtNombre.getText() + "',";
            sql += "emp_rif='" + txtRif.getText() + "',";
            sql += "emp_finFiscal='" + fin + "',";
            //La ultima linea no lleva la coma +" '";
            sql += "emp_inicioFiscal='" + inicio + "'";
            sql += "WHERE emp_id='" + txtCodigo.getText() + "';";
            JOptionPane.showMessageDialog(null, "Datos Modificados");
            //muestra si la instuccion sql esta bien contruida
            System.out.println(sql);
            bd.ejecutar(sql);
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }

    public void Eliminar() {
        if (JOptionPane.showOptionDialog(this, "Desea Eliminar estos Datos?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] { "Si", "NO" }, "No") == 0) {
            String sql;
            sql = "DELETE FROM `mh_empresas` WHERE emp_id='" + txtCodigo.getText() + "';";
            bd.eliminar(sql);
            System.out.println(sql);
            System.out.println("" + txtCodigo.getText());
            JOptionPane.showMessageDialog(this, "Datos Eliminados");
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
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
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtRif = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cBoxInicio = new javax.swing.JComboBox();
        cBoxCierre = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        btnIngresar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        etiFecha = new javax.swing.JLabel();
        mesIni = new javax.swing.JLabel();
        nombreMes = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        rrr = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtYearEnd = new javax.swing.JTextField();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registro de Empresas");
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE));
        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos de la empresa"));
        // NOI18N
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel2.setText("CODIGO: ");
        // NOI18N
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel3.setText("NOMBRE:");
        // NOI18N
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel4.setText("MES INICIO FISCAL: ");
        // NOI18N
        txtCodigo.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        // NOI18N
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        // NOI18N
        txtRif.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtRif.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRif.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRifKeyTyped(evt);
            }
        });
        // NOI18N
        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        // NOI18N
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel5.setText("R.I.F.:");
        // NOI18N
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel6.setText("MES CIERRE FISCAL: ");
        // NOI18N
        cBoxInicio.setFont(new java.awt.Font("Tahoma", 1, 14));
        cBoxInicio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        // NOI18N
        cBoxCierre.setFont(new java.awt.Font("Tahoma", 1, 14));
        cBoxCierre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        // NOI18N
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel7.setText("AÑO INICIO FISCAL: ");
        // NOI18N
        txtYear.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtYear.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtYear.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtYearActionPerformed(evt);
            }
        });
        txtYear.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtYearKeyTyped(evt);
            }
        });
        // NOI18N
        btnIngresar.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnIngresar.setText("INGRESAR");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        // NOI18N
        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnEditar.setText("EDITAR");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        // NOI18N
        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        etiFecha.setText("jLabel8");
        mesIni.setText("jLabel8");
        nombreMes.setText("jLabel8");
        // NOI18N
        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        rrr.setText("jLabel8");
        // NOI18N
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel8.setText("AÑO CIERRE FISCAL: ");
        // NOI18N
        txtYearEnd.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtYearEnd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtYearEnd.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtYearEndActionPerformed(evt);
            }
        });
        txtYearEnd.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtYearEndKeyTyped(evt);
            }
        });
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel6).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cBoxCierre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addComponent(jLabel4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE).addComponent(cBoxInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addComponent(btnEditar).addGap(48, 48, 48).addComponent(btnNuevo).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnEliminar)).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(txtCodigo).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(txtRif)).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel8).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(txtYearEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel7).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(etiFecha).addGap(18, 18, 18).addComponent(mesIni).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(nombreMes).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(rrr)).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(0, 0, Short.MAX_VALUE)))));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(etiFecha).addComponent(mesIni).addComponent(nombreMes).addComponent(rrr)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(btnBuscar)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(txtRif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cBoxInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cBoxCierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtYearEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(btnIngresar)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnEditar).addComponent(btnEliminar).addComponent(btnNuevo))));
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        pack();
    }

    // </editor-fold>//GEN-END:initComponents
    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnIngresarActionPerformed
        // TODO add your handling code here:
        String msj = this.Validar();
        if (msj.equals("")) {
            this.Ingresar();
            this.limpiar();
        } else {
            JOptionPane.showMessageDialog(null, msj, "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    //GEN-LAST:event_btnIngresarActionPerformed
    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtCodigoKeyTyped
        // TODO add your handling code here:
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[0-9]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }

    //GEN-LAST:event_txtCodigoKeyTyped
    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[0-9, ,A-Z,.]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }

    //GEN-LAST:event_txtNombreKeyTyped
    private void txtRifKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtRifKeyTyped
        // TODO add your handling code here:
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[J,0-9]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }

    //GEN-LAST:event_txtRifKeyTyped
    private void txtYearKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtYearKeyTyped
        // TODO add your handling code here:
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[0-9]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }

    //GEN-LAST:event_txtYearKeyTyped
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.buscar();
    }

    //GEN-LAST:event_btnBuscarActionPerformed
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        this.Modificar();
    }

    //GEN-LAST:event_btnEditarActionPerformed
    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
        this.buscar();
    }

    //GEN-LAST:event_txtCodigoActionPerformed
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        this.Eliminar();
        this.limpiar();
    }

    //GEN-LAST:event_btnEliminarActionPerformed
    private void txtYearActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_txtYearActionPerformed
        // TODO add your handling code here:
        String msj = this.Validar();
        if (msj.equals("")) {
            this.Ingresar();
            this.limpiar();
        } else {
            JOptionPane.showMessageDialog(null, msj, "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    //GEN-LAST:event_txtYearActionPerformed
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        this.limpiar();
        txtCodigo.setText("");
    }

    //GEN-LAST:event_btnNuevoActionPerformed
    private void txtYearEndActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_txtYearEndActionPerformed
        // TODO add your handling code here:
    }

    //GEN-LAST:event_txtYearEndActionPerformed
    private void txtYearEndKeyTyped(java.awt.event.KeyEvent evt) {
        //GEN-FIRST:event_txtYearEndKeyTyped
        // TODO add your handling code here:
    }

    //GEN-LAST:event_txtYearEndKeyTyped
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new empresas().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;

    private javax.swing.JButton btnEditar;

    private javax.swing.JButton btnEliminar;

    private javax.swing.JButton btnIngresar;

    private javax.swing.JButton btnNuevo;

    public javax.swing.JComboBox cBoxCierre;

    private javax.swing.JComboBox cBoxInicio;

    public javax.swing.JLabel etiFecha;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel2;

    private javax.swing.JLabel jLabel3;

    private javax.swing.JLabel jLabel4;

    private javax.swing.JLabel jLabel5;

    private javax.swing.JLabel jLabel6;

    private javax.swing.JLabel jLabel7;

    private javax.swing.JLabel jLabel8;

    private javax.swing.JPanel jPanel1;

    private javax.swing.JPanel jPanel2;

    private javax.swing.JPanel jPanel3;

    private javax.swing.JLabel mesIni;

    private javax.swing.JLabel nombreMes;

    private javax.swing.JLabel rrr;

    private javax.swing.JTextField txtCodigo;

    private javax.swing.JTextField txtNombre;

    private javax.swing.JTextField txtRif;

    private javax.swing.JTextField txtYear;

    private javax.swing.JTextField txtYearEnd;

    // End of variables declaration//GEN-END:variables
    private static final Logger logger = LogManager.getLogger(empresas.class);
}
