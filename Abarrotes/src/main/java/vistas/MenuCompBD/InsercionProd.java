
package vistas.MenuCompBD;
import EntityClass.Productos;
import vistas.CU.*;
import EntityClass.Usuarios;
import EntityClassController.ProductosJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import vistas.*;
//import vistas.ticket.*;
import javax.swing.*;

public class InsercionProd extends JDialog implements ActionListener{
    
    JLabel lblnombre, lblmarca, lbldesc,lblconte, lblprecio;
    JTextField txtnombre,txtdesc, txtconte, txtprecio;
    JComboBox jcbmarca;
    JButton btnGuardar,btnTerminar;
    EntityManagerFactory emf;
    ProductosJpaController UJPACtrl;
    
    public InsercionProd(){
        this.initFrame();
        this.initPersistence("BD_Persistance");
        this.initLabels();
        this.initTextFields();
        this.initComboBoxes();
        this.initButtons();
        this.initComponents();
    }
    
    private void initFrame(){
        this.setTitle("Insertar");
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
    }
    
    private void initPersistence(String persUnitName){
    emf = Persistence.createEntityManagerFactory(persUnitName);
    UJPACtrl =new ProductosJpaController(emf);
    }
    
    public void initLabels(){
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
    
    private void initTextFields(){
        txtnombre = new JTextField();
        txtnombre.setBounds(140, 20, 150, 25);
        txtdesc = new JTextField();
        txtdesc.setBounds(140, 50, 120, 25);
        txtconte = new JTextField();
        txtconte.setBounds(140, 110, 120, 25);
        txtprecio = new JTextField();
        txtprecio.setBounds(140, 140, 120, 25);
    }
    
//    private void initJSpinners(){
//        jsecant = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
//        jsecant.setBounds(140, 50, 150, 25);
//        setJSpinnerTextEditor(false);
//    }
    
    private void initComboBoxes(){
        jcbmarca = new JComboBox (new String []{"Seleccione...", "Coca-Cola", "Pepsi", "Barcel","Bimbo","Sabritas","Otros"});
        jcbmarca.setBounds(140, 80, 150, 25);
    }
    
//      private void initTextFieldsuno(){
//        txtpasswd = new JTextField();
//        txtpasswd.setBounds(140, 110, 150, 25);
//    }
    
    private void initButtons(){
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(90, 170, 150, 25);
        btnGuardar.addActionListener(this);
        btnTerminar = new JButton("Terminar");
        btnTerminar.setBounds(210, 170, 150, 25);
        btnTerminar.addActionListener(this);
    }
    
    private void initComponents(){
        getContentPane().add(lblnombre);
        getContentPane().add(lbldesc);
        getContentPane().add(lblmarca);
        getContentPane().add(lblconte);
        getContentPane().add(lblprecio);
        getContentPane().add(txtnombre);
        getContentPane().add(txtdesc);
        getContentPane().add(txtconte);
        getContentPane().add(txtprecio);
        getContentPane().add(jcbmarca);
        getContentPane().add(btnGuardar);
        getContentPane().add(btnTerminar);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){{
        if (e.getSource() == btnGuardar){
            if (!(this.txtnombre.getText().equals("") || this.jcbmarca.getSelectedItem().toString().equals("Seleccione...")));
            Productos p = new Productos();
            p.setNombre(txtnombre.getText());
            p.setContenidodelproducto(txtconte.getText());
            p.setMarca(jcbmarca.getSelectedItem().toString());
            p.setDescripcion(txtdesc.getText());
            p.setPrecio(Float.parseFloat(txtprecio.getText()));
            UJPACtrl.create(p);
            JOptionPane.showMessageDialog(this, "El registro ha queadado guardado", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Hay campos vacios o sin seleccionados", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
//        if (e.getSource() == btnTerminar){
//            this.dispose();
//            new ticket().setVisible(true);
//        }

        }
    }
    
}
