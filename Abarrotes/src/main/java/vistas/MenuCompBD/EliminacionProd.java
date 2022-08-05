package vistas.MenuCompBD;

import EntityClass.Productos;
import vistas.CU.*;
import EntityClass.Usuarios;
import EntityClassController.ProductosJpaController;
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

public class EliminacionProd extends JDialog implements ActionListener {

    JLabel lblnombre, lblmarca, lbldesc,lblconte, lblprecio;
    JTextField txtnombre,txtDescripcion, txtMarca, txtprecio,txtContenidodelproducto;
    JButton btnSelección;
    EntityManagerFactory emf;
    ProductosJpaController UJPACtrl;

    public EliminacionProd() {
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
        UJPACtrl = new ProductosJpaController(emf);
    }

    private void initLabels() {
        lblnombre = new JLabel("Nombre: ");
        lblnombre.setBounds(20, 20, 120, 25);
        lbldesc = new JLabel("Descripcion: ");
        lbldesc.setBounds(20, 50, 120, 25);
        lblmarca = new JLabel("Marca: ");
        lblmarca.setBounds(20, 80, 120, 25);
        lblconte = new JLabel("Contenido: ");
        lblconte.setBounds(20, 110, 120, 25);
        lblprecio = new JLabel("Precio: ");
        lblprecio.setBounds(20, 140, 120, 25);
    }

    private void initTextFields() {
        txtnombre = new JTextField();
        txtnombre.setBounds(140, 20, 150, 25);
        txtnombre.setEditable(false);
        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(140, 50, 150, 25);
        txtDescripcion.setEditable(false);
        txtMarca = new JTextField();
        txtMarca.setBounds(140, 80, 150, 25);
        txtMarca.setEditable(false);

        txtContenidodelproducto = new JTextField();
        txtContenidodelproducto.setBounds(140, 110, 150, 25);
        txtContenidodelproducto.setEditable(false);
       txtprecio = new JTextField();
        txtprecio.setBounds(140, 110, 150, 25);
        txtprecio.setEditable(false);

        
    }

    private void initButtons() {
        btnSelección = new JButton("Mostrar");
        btnSelección.setBounds(300, 170, 150, 25);
        btnSelección.addActionListener(this);
    }

    private void initComponents() {
        getContentPane().add(lblnombre);
        getContentPane().add(lbldesc);
        getContentPane().add(lblconte);

        getContentPane().add(lblprecio);
        getContentPane().add(lblnombre);
        getContentPane().add(txtnombre);
        getContentPane().add(txtDescripcion);
        getContentPane().add(txtMarca);
        getContentPane().add(txtContenidodelproducto);
        getContentPane().add(txtprecio);
        getContentPane().add(txtnombre);
        getContentPane().add(btnSelección);
    }

    private void enableInfo(Productos u) {
        txtnombre.setText(u.getNombre());
        txtDescripcion.setText(u.getDescripcion().toString());
        txtMarca.setText(u.getMarca());
        txtContenidodelproducto.setText(u.getContenidodelproducto());
//        txtprecio.setText(u.getrecio());

    }

    private void cleanInfo() {
        txtnombre.setText("");
        txtDescripcion.setText("");
        txtMarca.setText("");
        txtprecio.setText("");
        txtnombre.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSelección) {
            if (btnSelección.getText().equals("Mostrar")) {
                EntityManager em = this.UJPACtrl.getEntityManager();
                Query q = em.createNamedQuery("Productos.findByUser");
                q.setParameter("nombre", this.txtnombre.getText());
                List<Productos> listU = q.getResultList();
                if (listU != null) {
                    for (Productos productos : listU) {
                        enableInfo(productos);
                    }
                    txtnombre.setEditable(false);
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
                        Query q = em.createNamedQuery("Productos.deleteUser");
                        q.setParameter("nombre", this.txtnombre.getText());
                        q.executeUpdate();
                        em.getTransaction().commit();
                    } catch (Exception ex) {
                        Logger.getLogger(EliminacionProd.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                cleanInfo();
                txtnombre.setEditable(true);
                btnSelección.setText("Mostrar");
            }

        }
    }
}
