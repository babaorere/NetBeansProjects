package vistas.CU;

import EntityClass.Usuarios;
import EntityClassController.UsuariosJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;

public class Modificacion extends JDialog implements ActionListener {

    JLabel lblId, lblNombre, lblEdad, lblSexo,lblpasswd;
    JTextField txtId, txtNombre,txtpasswd;
    JSpinner jsEdad;
    JComboBox jcbSexo;
    JButton btnSeleccion;
    EntityManagerFactory emf;
    UsuariosJpaController uJPACtrl;

    public Modificacion() {
        this.initFrame();
        this.initPersistence("BD_Persistance");
        this.initLabels();
        this.initTextFields();
        this.initJSpinners();
        this.initButtons();
        this.initComboBoxes();
        this.initComponents();
    }

    private void initFrame() {
        this.setTitle("Modificacion");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
    }

    private void initPersistence(String persUnitName) {
        emf = Persistence.createEntityManagerFactory(persUnitName);
        uJPACtrl = new UsuariosJpaController(emf);
    }

    private void initLabels() {
        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 120, 25);
        lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(20, 50, 120, 25);
        lblSexo = new JLabel("Sexo:");
        lblSexo.setBounds(20, 80, 120, 25);
        lblpasswd = new JLabel("Passwd:");
        lblpasswd.setBounds(20, 110, 120, 25);
        lblId = new JLabel("Ingrese Id:");
        lblId.setBounds(20, 170, 120, 25);
    }

    private void initTextFields() {
        txtNombre = new JTextField();
        txtNombre.setBounds(140, 20, 150, 25);
        txtNombre.setEditable(false);
        txtpasswd = new JTextField();
        txtpasswd.setBounds(140, 110, 150, 25);
        txtpasswd.setEditable(false);
        txtId = new JTextField();
        txtId.setBounds(140, 170, 150, 25);
    }

    private void initJSpinners() {
        jsEdad = new JSpinner();
        valuesJSpinner(jsEdad, 0, 0, 0, 0, false);
        jsEdad.setBounds(140, 50, 150, 25);
        jsEdad.setEnabled(false);
    }

    private void initButtons() {
        btnSeleccion = new JButton("Mostrar");
        btnSeleccion.setBounds(300, 170, 150, 25);
        btnSeleccion.addActionListener(this);
    }

    private void initComboBoxes() {
        jcbSexo = new JComboBox();
        jcbSexo.setBounds(140, 80, 150, 25);
        jcbSexo.setEditable(false);
    }

    private void initComponents() {
        getContentPane().add(lblNombre);
        getContentPane().add(lblEdad);
        getContentPane().add(lblSexo);
        getContentPane().add(lblpasswd);
        getContentPane().add(lblId);
        getContentPane().add(txtNombre);
        getContentPane().add(jsEdad);
        getContentPane().add(jcbSexo);
        getContentPane().add(txtpasswd);
        getContentPane().add(txtId);
        getContentPane().add(btnSeleccion);
    }

    private void valuesJSpinner(JSpinner aux, int init, int min, int max, int step, boolean val) {
        aux.setModel(new SpinnerNumberModel(init, min, max, step));
        ((JSpinner.DefaultEditor) aux.getEditor()).getTextField().setEditable(val);
    }

    private void valuesComboBox(JComboBox aux) {
        for (String item : new String[]{"Seleccione...", "Masculino", "Femenino"}) {
            jcbSexo.addItem(item);
        }
    }

    private void enableInfo(Usuarios u) {
        txtNombre.setText(u.getUser());
        txtNombre.setEditable(true);
        
        valuesJSpinner(jsEdad, 1, 1, 99, 1, false);
        valuesComboBox(jcbSexo);
        jsEdad.setValue(u.getEdad());
        jsEdad.setEnabled(true);
        jcbSexo.setSelectedItem(u.getSexo());
        jcbSexo.setEnabled(true);
        
        txtpasswd.setText(u.getPasswd());
        txtpasswd.setEditable(true);
        
        txtId.setEditable(false);
    }

    private void cleanInfo() {
        txtNombre.setText("");
        txtNombre.setEditable(false);
        valuesJSpinner(jsEdad, 0, 0, 0, 0, false);
        jsEdad.setEnabled(false);
        jcbSexo.removeAllItems();
        jcbSexo.setEnabled(false);
        txtpasswd.setText("");
        txtpasswd.setEditable(false);
        txtId.setText("");
        txtId.setEditable(true);
    }

    private void addInfo(Usuarios u) {
        u.setUser(txtNombre.getText());
        u.setEdad(Integer.parseInt(jsEdad.getValue().toString()));
        u.setSexo(jcbSexo.getSelectedItem().toString());
        u.setPasswd(txtpasswd.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSeleccion) {
            if (btnSeleccion.getText().equals("Mostrar")) {
                Usuarios u = uJPACtrl.findUsuarios(Integer.parseInt(this.txtId.getText()));
                if (u != null) {
                    enableInfo(u);
                    btnSeleccion.setText("Modificar");
                } else {
                    cleanInfo();
                    JOptionPane.showMessageDialog(this, "El registro no fue encontrado",
                            "Seleccion fallida", JOptionPane.PLAIN_MESSAGE);
                }
            } else if (btnSeleccion.getText().equals("Modificar")) {
                if (JOptionPane.showOptionDialog(this, "¿esea modificar registro?", "Modificar",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new String[]{"Si", "No"}, "Si") == 0) {
                    try {
                        Usuarios u = new Usuarios(Integer.parseInt(txtId.getText()));
                        addInfo(u);
                        uJPACtrl.edit(u);
                        JOptionPane.showMessageDialog(this, "El registro ha sido modificado",
                                "Modificacion exitosa", JOptionPane.PLAIN_MESSAGE);
                    } catch (Exception ex) {
                        Logger.getLogger(Modificacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                cleanInfo();
                btnSeleccion.setText("Mostrar");
            }

        }
    }
}
