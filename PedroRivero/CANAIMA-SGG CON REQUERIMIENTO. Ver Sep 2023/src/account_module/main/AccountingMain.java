package account_module.main;

import accounting_module.Conexion.ConnAccounting;
import accounting_module.Conexion.reportes;
import accounting_module.accounting.comprobantes;
import accounting_module.accounting.editaComprobante;
import accounting_module.accounting.libros;
import accounting_module.accounting.planCuenta;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.swing.*;

public final class AccountingMain extends javax.swing.JFrame {

    private static java.awt.Window parent = null;

    JFileChooser RealizarBackupMySQL = new JFileChooser();

    public AccountingMain(final java.awt.Window inParent) {

        if (inParent != null) {
            parent = inParent;
        }

        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/accounting_module/img/lander.png")).getImage());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.visibleFalse();
        this.mes.setVisible(false);
        this.etiyear.setVisible(false);
        this.fechaSQL.setVisible(false);
        Fecha();

        // Establecer acción al cerrar ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                btnSalir.doClick();
            }
        });
    }

    //     Asigno la Fecha Actualizada a una Etiqueta con el metodo Fecha
    public void Fecha() {
        Calendar cal = Calendar.getInstance();
        String fecha = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        String sqlFecha = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)) + "-" + cal.get(Calendar.DATE);
        this.etiFecha.setText(fecha);
        this.fechaSQL.setText(sqlFecha);
    }

    public void visibleFalse() {
        this.btnPlanCta.setVisible(false);
        this.btnVolver.setVisible(false);
        this.btnListarPlan.setVisible(false);
        this.btnComprobantes.setVisible(false);
        this.btnEditaComprb.setVisible(false);
        this.btnReportes.setVisible(false);
        this.btnRenovar.setVisible(false);
        this.menuPlanCta.setVisible(false);
        this.menuListarPlan.setVisible(false);
        this.menuMovimientos.setVisible(false);
        this.menuVolver.setVisible(false);
        this.panelContabilidad.setVisible(false);
    }

    public void visibleTrue() {
        this.btnPlanCta.setVisible(true);
        this.btnListarPlan.setVisible(true);
        this.btnComprobantes.setVisible(true);
        this.btnEditaComprb.setVisible(true);
        this.btnReportes.setVisible(true);
        this.btnRenovar.setVisible(true);
        this.menuPlanCta.setVisible(true);
        this.menuListarPlan.setVisible(true);
        this.menuMovimientos.setVisible(true);
        this.menuVolver.setVisible(true);
        this.panelContabilidad.setVisible(true);
        this.btnVolver.setVisible(true);
    }

    public void enableFalse() {
        btnRegistrar.setEnabled(false);
        btnListar.setEnabled(false);
        btnElegir.setEnabled(false);
        menuEmpresas.setEnabled(false);
        menuListar.setEnabled(false);
        menuElegir.setEnabled(false);
    }

    public void enableTrue() {
        btnRegistrar.setEnabled(true);
        btnListar.setEnabled(true);
        btnElegir.setEnabled(true);
        menuEmpresas.setEnabled(true);
        menuListar.setEnabled(true);
        menuElegir.setEnabled(true);
    }

    public void empresas() {
        JDialog dialog = new JDialog(new javax.swing.JFrame(), "Registro de Empresas", true);
        dialog.setSize(420, 380);
        dialog.setLocationRelativeTo(null);
        empresas e = new empresas();
        e.etiFecha.setText(this.etiFecha.getText());
        dialog.getContentPane().add(e.getContentPane());
        dialog.setTitle("Registro de Empresas");
        dialog.setVisible(true);
    }

    public void planCuentas() {
        JDialog dialog = new JDialog(new javax.swing.JFrame(), "Plan de Cuentas", true);
        dialog.setSize(650, 455);
        dialog.setLocationRelativeTo(null);
        planCuenta e = new planCuenta();
        e.empresa.setText(this.etiqCodigo.getText());
        e.fechaSql.setText(this.etiInicio.getText());
        dialog.getContentPane().add(e.getContentPane());
        dialog.setTitle("Plan de Cuentas");
        dialog.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    public void comprobantes() {

        JDialog dialog = new JDialog(new javax.swing.JFrame(), "Comprobantes", true);
        dialog.setSize(940, 545);
        dialog.setLocationRelativeTo(null);
        comprobantes c = new comprobantes();
        c.empresa.setText(this.etiqCodigo.getText());
        c.etiYear.setText(this.etiyear.getText());

        try {
            String sql = "SELECT * FROM `mh_concepto` WHERE `emp_id`='" + etiqCodigo.getText() + "' ORDER BY `conc_descripcion` ASC";
            ConnAccounting bd = ConnAccounting.getInstance();
            ResultSet result = bd.buscar(sql);
            while (result.next()) {
                c.cmb_concepto.addItem(result.getObject(3));
                c.conId.setText(result.getString(2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        try {
            String sql = "SELECT DISTINCT `mh_auxiliar`.`cta_id` FROM mh_auxiliar Where emp_id='" + etiqCodigo.getText() + "' "
                    + "AND aux_tipoCta='De Movimiento'";/*OR aux_tipoCta='De Totales'*/

            ConnAccounting bd = ConnAccounting.getInstance();
            ResultSet result = bd.buscar(sql);

            while (result.next()) {
                c.cmboCuenta.addItem(result.getObject("cta_id"));
                System.out.println(c.cmboCuenta.getSelectedItem());
            }

            c.cmboCuenta.setVisible(true);
            c.update(this.getGraphics());
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, ee);
            System.out.println("Error: " + ee);
        }
        dialog.getContentPane().add(c.getContentPane());
        dialog.setTitle("Comprobantes");
        dialog.setVisible(true);
    }

    public void editaComprobante() {

        String a = "Los cambios que realice en esta seccion afectaran\n  los asientos registrados"
                + "\n Desea continuar?";
        String b = "Confirmar";
        if (JOptionPane.showOptionDialog(this, a, b, JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE, null, new Object[]{"Si", "No"}, "NO") == 0) {
            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Actualizar Comprobantes", true);
            dialog.setSize(810, 500);
            dialog.setLocationRelativeTo(null);
            editaComprobante e = new editaComprobante();
            e.yyear.setText(this.etiyear.getText());
            e.etiCodigo.setText(this.etiqCodigo.getText());
            dialog.getContentPane().add(e.getContentPane());
            dialog.setTitle("Actualizar Comprobantes");
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }

    public void libros() {
        JDialog dialog = new JDialog(new javax.swing.JFrame(), "Libros Contables", true);
        dialog.setSize(498, 318);
        dialog.setLocationRelativeTo(null);
        libros l = new libros();
        l.empresa.setText(this.etiqCodigo.getText());
        l.yyear.setText(this.etiyear.getText());
        l.mes.setText(this.mes.getText());
        dialog.getContentPane().add(l.getContentPane());
        dialog.setTitle("Libros Contables");
        dialog.setVisible(true);
    }

    public void renovar() {
        if (JOptionPane.showOptionDialog(this, "Renovar el Periodo Fiscal implica \n "
                + "Que las cuentas quedaran en 0\n ¿Desea Renovar el Periodo Fiscal?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, new Object[]{"Si", "NO"}, "No") == 0) {
            String sql;
            sql = "UPDATE `mh_empresas` "
                    + "SET `mh_empresas`.`emp_inicioFiscal`=DATE_ADD(`mh_empresas`.`emp_inicioFiscal`,INTERVAL 1 YEAR),"
                    + "`mh_empresas`.`emp_finFiscal`=DATE_ADD(`mh_empresas`.`emp_finFiscal`,INTERVAL 1 YEAR)"
                    + " WHERE `mh_empresas`.`emp_id`='" + etiqCodigo.getText() + "'";
            JOptionPane.showMessageDialog(null, "Datos Modificados");
            System.out.println(sql); //muestra si la instuccion sql esta bien contruida
            ConnAccounting bd = ConnAccounting.getInstance();
            bd.ejecutar(sql);
            this.setVisible(false);
            AccountingMain m = new AccountingMain(parent);
            m.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }

    public void volver() {
        this.enableTrue();
        this.visibleFalse();
    }

    public void GenerarBackupMySQL() {
        int resp;
        String fecha = etiFecha.getText();
        resp = RealizarBackupMySQL.showSaveDialog(this);//JFileChooser de nombre RealizarBackupMySQL
        if (resp == JFileChooser.APPROVE_OPTION) {//Si el usuario presiona aceptar; se genera el Backup
            try {
                Runtime runtime = Runtime.getRuntime();
                File backupFile = new File(String.valueOf(RealizarBackupMySQL.getSelectedFile().toString())
                        + "_" + fecha + ".sql");
                FileWriter fw = new FileWriter(backupFile);
                //Generar BackUp desde Wampp
//                Process child = runtime.exec("C:\\wamp\\bin\\mysql\\mysql5.5.24\\bin\\mysqldump  --password= --user=root "
//                        + "--databases sistemacontable -R");
                //Generar backUp desde Xampp
                Process child = runtime.exec("C:\\xampp\\mysql\\bin\\mysqldump  --password= --user=root "
                        + "--databases sistemacontable -R");
                InputStreamReader irs = new InputStreamReader(child.getInputStream());
                BufferedReader br = new BufferedReader(irs);

                String line;
                while ((line = br.readLine()) != null) {
                    fw.write(line + "\n");
                }
                fw.close();
                irs.close();
                br.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error no se genero el archivo por el siguiente motivo:" + e.getMessage(), "Verificar", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "La base de datos ha sido \nrespaldada satisfactoriamente", "Verificar", JOptionPane.INFORMATION_MESSAGE);
        } else if (resp == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "Ha sido cancelada la generacion del Backup");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        etiFecha = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnListar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnElegir = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnPlanCta = new javax.swing.JButton();
        btnListarPlan = new javax.swing.JButton();
        btnComprobantes = new javax.swing.JButton();
        btnEditaComprb = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnRenovar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        panelContabilidad = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        mes = new javax.swing.JLabel();
        etiyear = new javax.swing.JLabel();
        etiqCodigo = new javax.swing.JLabel();
        etiqNombre = new javax.swing.JLabel();
        etiqRif = new javax.swing.JLabel();
        etiInicio1 = new javax.swing.JLabel();
        etiInicio = new javax.swing.JLabel();
        etiInicio2 = new javax.swing.JLabel();
        etiFin = new javax.swing.JLabel();
        fechaSQL = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuEmpresas = new javax.swing.JMenuItem();
        menuListar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuVolver = new javax.swing.JMenuItem();
        menuSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuElegir = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuPlanCta = new javax.swing.JMenuItem();
        menuListarPlan = new javax.swing.JMenuItem();
        menuMovimientos = new javax.swing.JMenu();
        menuComprobantes = new javax.swing.JMenuItem();
        menuEditaComprobante = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuElegir5 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuElegir6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema Contable");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1054, 542));

        jLabel6.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("de Registros Contables");
        jLabel6.setAutoscrolls(true);

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Sistema Informático");
        jLabel3.setAutoscrolls(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("FECHA:");

        etiFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiFecha.setText("00/00/0000");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnListar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnListar.setText("Listar");
        btnListar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnElegir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnElegir.setText("Contabilidad");
        btnElegir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnElegir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElegirActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/accounting_module/img/salir22.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnPlanCta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnPlanCta.setText("Plan de Cuentas");
        btnPlanCta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPlanCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlanCtaActionPerformed(evt);
            }
        });

        btnListarPlan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnListarPlan.setText("Listar Plan de Cuentas");
        btnListarPlan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnListarPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarPlanActionPerformed(evt);
            }
        });

        btnComprobantes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnComprobantes.setText("Comprobantes");
        btnComprobantes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobantesActionPerformed(evt);
            }
        });

        btnEditaComprb.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEditaComprb.setText("Editar Comprobantes");
        btnEditaComprb.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditaComprb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditaComprbActionPerformed(evt);
            }
        });

        btnReportes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        btnRenovar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnRenovar.setText("Renovar");
        btnRenovar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRenovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenovarActionPerformed(evt);
            }
        });

        btnVolver.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnVolver.setText("<< Volver");
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPlanCta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnListarPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 167, Short.MAX_VALUE)
            .addComponent(btnComprobantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEditaComprb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnReportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRenovar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPlanCta)
                .addGap(7, 7, 7)
                .addComponent(btnListarPlan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnComprobantes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditaComprb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReportes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRenovar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVolver)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnElegir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegistrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnListar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnElegir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelContabilidad.setBackground(new java.awt.Color(204, 204, 204));
        panelContabilidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(102, 102, 102), null, null));

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Contabilidad General");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
        );

        mes.setText("jLabel4");

        etiyear.setText("jLabel4");

        etiqCodigo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        etiqCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqCodigo.setText("            Codigo");

        etiqNombre.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        etiqNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqNombre.setText("            Nombre");

        etiqRif.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        etiqRif.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqRif.setText("            Rif");

        etiInicio1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        etiInicio1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiInicio1.setText("Inicio Fiscal: ");

        etiInicio.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        etiInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiInicio.setText("Inicio");

        etiInicio2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        etiInicio2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiInicio2.setText("Cierre Fiscal: ");

        etiFin.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        etiFin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiFin.setText("Fin");

        javax.swing.GroupLayout panelContabilidadLayout = new javax.swing.GroupLayout(panelContabilidad);
        panelContabilidad.setLayout(panelContabilidadLayout);
        panelContabilidadLayout.setHorizontalGroup(
            panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelContabilidadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiyear)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelContabilidadLayout.createSequentialGroup()
                .addGroup(panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(etiqRif, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelContabilidadLayout.createSequentialGroup()
                            .addComponent(etiqCodigo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(etiqNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelContabilidadLayout.createSequentialGroup()
                        .addGroup(panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(etiInicio2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                            .addComponent(etiInicio1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(etiInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addComponent(etiFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelContabilidadLayout.setVerticalGroup(
            panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContabilidadLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mes)
                    .addComponent(etiyear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiqCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiqNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiqRif, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiInicio1)
                    .addComponent(etiInicio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiInicio2)
                    .addComponent(etiFin))
                .addGap(0, 100, Short.MAX_VALUE))
        );

        fechaSQL.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelContabilidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1001, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(fechaSQL)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(etiFecha)
                            .addGap(22, 22, 22)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaSQL))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelContabilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText("Archivo");
        jMenu1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        menuEmpresas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuEmpresas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuEmpresas.setText("Registrar Empresas");
        menuEmpresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEmpresasActionPerformed(evt);
            }
        });
        jMenu1.add(menuEmpresas);

        menuListar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuListar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuListar.setText("Empresas Registradas");
        menuListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuListarActionPerformed(evt);
            }
        });
        jMenu1.add(menuListar);
        jMenu1.add(jSeparator1);

        menuVolver.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_BACK_SPACE, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuVolver.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuVolver.setText("Volver");
        menuVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVolverActionPerformed(evt);
            }
        });
        jMenu1.add(menuVolver);

        menuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuSalir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuSalir.setText("Salir");
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        jMenu1.add(menuSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Contabilidad");
        jMenu2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        menuElegir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuElegir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuElegir.setText("Elegir Empresas");
        menuElegir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuElegirActionPerformed(evt);
            }
        });
        jMenu2.add(menuElegir);
        jMenu2.add(jSeparator2);

        menuPlanCta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuPlanCta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuPlanCta.setText("Plan de Cuentas");
        menuPlanCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPlanCtaActionPerformed(evt);
            }
        });
        jMenu2.add(menuPlanCta);

        menuListarPlan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuListarPlan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuListarPlan.setText("Listar Plan de Cuentas");
        menuListarPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuListarPlanActionPerformed(evt);
            }
        });
        jMenu2.add(menuListarPlan);

        jMenuBar1.add(jMenu2);

        menuMovimientos.setText("Movimientos");
        menuMovimientos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        menuComprobantes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuComprobantes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuComprobantes.setText("Comprobantes");
        menuComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuComprobantesActionPerformed(evt);
            }
        });
        menuMovimientos.add(menuComprobantes);

        menuEditaComprobante.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menuEditaComprobante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuEditaComprobante.setText("Editar Comprobantes");
        menuEditaComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditaComprobanteActionPerformed(evt);
            }
        });
        menuMovimientos.add(menuEditaComprobante);
        menuMovimientos.add(jSeparator3);

        menuElegir5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuElegir5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuElegir5.setText("Reportes");
        menuElegir5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuElegir5ActionPerformed(evt);
            }
        });
        menuMovimientos.add(menuElegir5);
        menuMovimientos.add(jSeparator4);

        menuElegir6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuElegir6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        menuElegir6.setText("Renovar");
        menuElegir6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuElegir6ActionPerformed(evt);
            }
        });
        menuMovimientos.add(menuElegir6);

        jMenuBar1.add(menuMovimientos);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1198, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuEmpresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEmpresasActionPerformed
        // TODO add your handling code here:
        this.empresas();
    }//GEN-LAST:event_menuEmpresasActionPerformed

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        if (JOptionPane.showOptionDialog(this, "Se recomienda respaldar la base de datos \n "
                + "\n ¿Desea Respaldar?\n 'Si' para respaldar o 'No para Salir'", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, new Object[]{"Si", "No"}, "No") == 0) {
            GenerarBackupMySQL();

            if (this.parent == null) {
                System.exit(0);
            } else {
                this.setVisible(false);
                parent.setVisible(true);
                dispose();
            }
        } else {
            if (this.parent == null) {
                System.exit(0);
            } else {
                this.setVisible(false);
                parent.setVisible(true);
                dispose();
            }
        }
    }//GEN-LAST:event_menuSalirActionPerformed

    private void menuListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuListarActionPerformed
        // TODO add your handling code here:
        reportes r = new reportes();
        r.listarEmpresas();
    }//GEN-LAST:event_menuListarActionPerformed

    private void menuElegirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuElegirActionPerformed
        elegir e = new elegir(this, true);
        e.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuElegirActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        reportes r = new reportes();
        r.listarEmpresas();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        this.empresas();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        if (JOptionPane.showOptionDialog(this, "Se recomienda respaldar la base de datos \n "
                + "\n ¿Desea Respaldar?\n 'Si' para respaldar o 'No para Salir'", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, new Object[]{"Si", "No"}, "No") == 0) {
            GenerarBackupMySQL();

            if (this.parent == null) {
                System.exit(0);
            } else {
                this.setVisible(false);
                parent.setVisible(true);
                dispose();
            }
        } else {
            if (this.parent == null) {
                System.exit(0);
            } else {
                this.setVisible(false);
                parent.setVisible(true);
                dispose();
            }
        }

    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnElegirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElegirActionPerformed
        elegir e = new elegir(this, true);
        e.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnElegirActionPerformed

    private void menuPlanCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPlanCtaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuPlanCtaActionPerformed

    private void menuListarPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuListarPlanActionPerformed
        String codigo = this.etiqCodigo.getText();
        reportes r = new reportes();
        r.listarPlanCuenta(codigo);
    }//GEN-LAST:event_menuListarPlanActionPerformed

    private void menuComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuComprobantesActionPerformed
        comprobantes();
    }//GEN-LAST:event_menuComprobantesActionPerformed

    private void menuEditaComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditaComprobanteActionPerformed
        this.editaComprobante();
    }//GEN-LAST:event_menuEditaComprobanteActionPerformed

    private void menuElegir5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuElegir5ActionPerformed
        libros();
    }//GEN-LAST:event_menuElegir5ActionPerformed

    private void menuElegir6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuElegir6ActionPerformed
        this.renovar();
    }//GEN-LAST:event_menuElegir6ActionPerformed

    private void menuVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVolverActionPerformed
        this.volver();
    }//GEN-LAST:event_menuVolverActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.volver();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnPlanCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlanCtaActionPerformed
        planCuentas();
    }//GEN-LAST:event_btnPlanCtaActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        planCuentas();
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void btnListarPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarPlanActionPerformed
        String codigo = this.etiqCodigo.getText();
        reportes r = new reportes();
        r.listarPlanCuenta(codigo);
    }//GEN-LAST:event_btnListarPlanActionPerformed

    private void btnComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobantesActionPerformed
        comprobantes();
    }//GEN-LAST:event_btnComprobantesActionPerformed

    private void btnEditaComprbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditaComprbActionPerformed
        this.editaComprobante();
    }//GEN-LAST:event_btnEditaComprbActionPerformed

    private void btnRenovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenovarActionPerformed

        this.renovar();
    }//GEN-LAST:event_btnRenovarActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        libros();
    }//GEN-LAST:event_btnReportesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AccountingMain(null).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnComprobantes;
    public javax.swing.JButton btnEditaComprb;
    public javax.swing.JButton btnElegir;
    public javax.swing.JButton btnListar;
    public javax.swing.JButton btnListarPlan;
    public javax.swing.JButton btnPlanCta;
    public javax.swing.JButton btnRegistrar;
    public javax.swing.JButton btnRenovar;
    public javax.swing.JButton btnReportes;
    private javax.swing.JButton btnSalir;
    public javax.swing.JButton btnVolver;
    public javax.swing.JLabel etiFecha;
    public javax.swing.JLabel etiFin;
    public javax.swing.JLabel etiInicio;
    public javax.swing.JLabel etiInicio1;
    public javax.swing.JLabel etiInicio2;
    public javax.swing.JLabel etiqCodigo;
    public javax.swing.JLabel etiqNombre;
    public javax.swing.JLabel etiqRif;
    public javax.swing.JLabel etiyear;
    public javax.swing.JLabel fechaSQL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    public javax.swing.JMenu jMenu1;
    public javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    public javax.swing.JMenuItem menuComprobantes;
    public javax.swing.JMenuItem menuEditaComprobante;
    public javax.swing.JMenuItem menuElegir;
    public javax.swing.JMenuItem menuElegir5;
    public javax.swing.JMenuItem menuElegir6;
    public javax.swing.JMenuItem menuEmpresas;
    public javax.swing.JMenuItem menuListar;
    public javax.swing.JMenuItem menuListarPlan;
    public javax.swing.JMenu menuMovimientos;
    public javax.swing.JMenuItem menuPlanCta;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JMenuItem menuVolver;
    public javax.swing.JLabel mes;
    public javax.swing.JPanel panelContabilidad;
    // End of variables declaration//GEN-END:variables
}
