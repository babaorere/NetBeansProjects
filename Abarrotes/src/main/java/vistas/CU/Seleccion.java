package vistas.CU;

import EntityClass.Usuarios;
import EntityClassController.UsuariosJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;


public class Seleccion extends JDialog implements ActionListener {

    JLabel lblId, lblNombre, lblEdad, lblSexo, lblpasswd;
    JTextField txtId, txtNombre, txtEdad, txtSexo, txtpasswd;
    JButton btnSeleccion;
    EntityManagerFactory emf;
    UsuariosJpaController UJPACtrl;

    public Seleccion() {
        this.initFrame();
        this.initPersistence("BD_Persistance");
        this.initLabels();
        this.initTextFields();
        this.initButtons();
        this.initComponents();
    }

    private void initFrame() {
        this.setTitle("Selección de un registro");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
    }
    
    private void initPersistence(String persUnitName) {
        emf = Persistence.createEntityManagerFactory(persUnitName);
        UJPACtrl =new UsuariosJpaController(emf);
    }

    private void initLabels() {
        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 120, 25);
        lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(20, 50, 120, 25);
        lblSexo = new JLabel("Sexo:");
        lblSexo.setBounds(20, 80, 120, 25);
        lblpasswd = new JLabel("Passwd: ");
        lblpasswd.setBounds(20, 110, 120, 25);
        lblId = new JLabel("Ingrese Id:");
        lblId.setBounds(20, 170, 140, 25);
    }

    private void initTextFields() {
        txtNombre = new JTextField();
        txtNombre.setBounds(140, 20, 150, 25);
        txtNombre.setEditable(false);
        txtEdad = new JTextField();
        txtEdad.setBounds(140, 50, 150, 25);
        txtEdad.setEditable(false);
        txtSexo = new JTextField();
        txtSexo.setBounds(140, 80, 150, 25);
        txtSexo.setEditable(false);
        txtpasswd = new JTextField();
        txtpasswd.setBounds(140, 110, 150, 25);
        txtpasswd.setEditable(false);
        txtId = new JTextField();
        txtId.setBounds(140, 170, 150, 25);
    }

    private void initButtons() {
        btnSeleccion = new JButton("Mostrar");
        btnSeleccion.setBounds(300, 170, 150, 25);
        btnSeleccion.addActionListener(this);
    }

    private void initComponents() {
        getContentPane().add(lblNombre);
        getContentPane().add(lblEdad);
        getContentPane().add(lblSexo);
        getContentPane().add(lblpasswd);
        getContentPane().add(lblId);
        getContentPane().add(txtNombre);
        getContentPane().add(txtEdad);
        getContentPane().add(txtSexo);
        getContentPane().add(txtpasswd);
        getContentPane().add(txtId);
        getContentPane().add(btnSeleccion);
    }

    private void cleanInfo() {
        txtNombre.setText("");
        txtEdad.setText("");
        txtSexo.setText("");
        txtpasswd.setText("");
    }

    private void addInfo(Usuarios u) {
        txtNombre.setText(u.getUser());
        txtEdad.setText(u.getEdad().toString());
        txtSexo.setText(u.getSexo().toString());
        txtpasswd.setText(u.getPasswd());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSeleccion) {
           Usuarios u = UJPACtrl.findUsuarios(Integer.parseInt(txtId.getText()));
            if (u != null) {
                addInfo(u);
                JOptionPane.showMessageDialog(this, "El registro fue encontrado",
                        "Selección exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                cleanInfo();
                JOptionPane.showMessageDialog(this, "El registro no fue encontrado",
                        "Selección fallida", JOptionPane.PLAIN_MESSAGE);
            }

        }
    }
}
