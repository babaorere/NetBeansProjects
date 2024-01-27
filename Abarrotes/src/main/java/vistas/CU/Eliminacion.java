package vistas.CU;

import EntityClass.Usuarios;
import EntityClassController.UsuariosJpaController;
import EntityClassController.exceptions.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.*;

public class Eliminacion extends JDialog implements ActionListener {

    JLabel lblId, lblNombre, lblEdad, lblSexo, lblpasswd;
    JTextField txtId, txtNombre, txtEdad, txtSexo, txtpasswd;
    JButton btnSelección;
    EntityManagerFactory emf;
    UsuariosJpaController UJPACtrl;

    public Eliminacion() {
        this.initFrame();
        this.initPersistence("BD_Persistance");
        this.initLabels();
        this.initTextFields();
        this.initButtons();
        this.initComponents();
    }

    private void initFrame() {
        this.setTitle("Eliminación");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
    }

    private void initPersistence(String persUnitName) {
        emf = Persistence.createEntityManagerFactory(persUnitName);
        UJPACtrl = new UsuariosJpaController(emf);
    }

    private void initLabels() {
        lblNombre = new JLabel("Usuario:");
        lblNombre.setBounds(20, 20, 120, 25);
        lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(20, 50, 120, 25);
        lblSexo = new JLabel("Sexo:");
        lblSexo.setBounds(20, 80, 120, 25);
        lblpasswd = new JLabel("passwd:");
        lblpasswd.setBounds(20, 110, 120, 25);
        lblNombre = new JLabel("Ingrese Nombre:");
        lblNombre.setBounds(20, 170, 120, 25);
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

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 170, 150, 25);
    }

    private void initButtons() {
        btnSelección = new JButton("Mostrar");
        btnSelección.setBounds(300, 170, 150, 25);
        btnSelección.addActionListener(this);
    }

    private void initComponents() {
        getContentPane().add(lblNombre);
        getContentPane().add(lblEdad);
        getContentPane().add(lblSexo);

        getContentPane().add(lblpasswd);
        getContentPane().add(lblNombre);
        getContentPane().add(txtNombre);
        getContentPane().add(txtEdad);
        getContentPane().add(txtSexo);
        getContentPane().add(txtpasswd);
        getContentPane().add(txtNombre);
        getContentPane().add(btnSelección);
    }

    private void enableInfo(Usuarios u) {
        txtNombre.setText(u.getUser());
        txtEdad.setText(u.getEdad().toString());
        txtSexo.setText(u.getSexo());
        txtpasswd.setText(u.getPasswd());
    }

    private void cleanInfo() {
        txtNombre.setText("");
        txtEdad.setText("");
        txtSexo.setText("");
        txtpasswd.setText("");
        txtNombre.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSelección) {
            if (btnSelección.getText().equals("Mostrar")) {
                EntityManager em = this.UJPACtrl.getEntityManager();
                Query q = em.createNamedQuery("Usuarios.findByUser");
                q.setParameter("user", this.txtNombre.getText());
                List<Usuarios> listU = q.getResultList();
                if (listU != null) {
                    for (Usuarios usuarios : listU) {
                        enableInfo(usuarios);
                    }
                    txtNombre.setEditable(false);
                    btnSelección.setText("Eliminar");
                } else {
                    cleanInfo();
                    JOptionPane.showMessageDialog(this, "El registro no fue encontrado",
                            "Selección fallida", JOptionPane.PLAIN_MESSAGE);
                }
            } else if (btnSelección.getText().equals("Eliminar")) {
                if (JOptionPane.showOptionDialog(this, "¿Desea eliminar registro?", "Eliminar",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new String[]{"Sí­", "No"}, "Sí­") == 0) {
                    try {
                        EntityManager em = UJPACtrl.getEntityManager();
                        em.getTransaction().begin();
                        Query q = em.createNamedQuery("Usuarios.deleteUser");
                        q.setParameter("user", this.txtNombre.getText());
                        q.executeUpdate();
                        em.getTransaction().commit();
                    } catch (Exception ex) {
                        Logger.getLogger(Eliminacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                cleanInfo();
                txtNombre.setEditable(true);
                btnSelección.setText("Mostrar");
            }

        }
    }
}
