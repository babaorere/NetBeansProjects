/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas.AgregarProductos;
import java.awt.event.*;
import javax.swing.*;
        
public class AgregarProductos extends  JDialog 
    implements ActionListener {
    private int a;
    JButton btnAgregar;
    JLabel lblMarc,lblCod, lblAgregarProductos,lblPrec;
    JTextField txtAg,txtCod,txtMar;
    JPasswordField txtNumAgreg,txtPre;
    public AgregarProductos(){
        //Método para centrar la ventana en pantalla
        this.setLocationRelativeTo(null);
        //Método para tamaño de ventana
        this.setSize(720, 480);
        //Quitar modo de redimensión de ventana
        this.setResizable(false);
        this.setTitle("Agregar Productos");
        this.setModal(true);
        //Cambiar tipo de ordenamiento de compomnentes
        this.setLayout(null);
        this.initButtons();
        this.initLabels();
        this.initTextFields();
        this.initPasswordFields();
        this.initComponents(); 
        this.setLocationRelativeTo(null);
        /*this.initButtons();
        this.initLabels();
        this.initTextFields();
        this.initPasswordFields();
        this.initComponents();*/
    }
    private void initButtons(){
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(285, 300, 150, 20);
        btnAgregar.addActionListener(this);
    }
    private void initLabels(){
        lblCod = new JLabel("Codigo de producto");
        lblCod.setBounds(430, 60, 200, 15);
        lblAgregarProductos = new JLabel("Numero de productos agregar");
        lblAgregarProductos.setBounds(150, 150, 200, 15);
        lblMarc = new JLabel("Marca");
        lblMarc.setBounds(200, 60, 200, 15);
        lblPrec = new JLabel("Precio ");
        lblPrec.setBounds(475, 150, 200, 15);
    }
    private void initTextFields(){
        txtCod = new JTextField(16);
        txtCod.setBounds(420, 80, 200, 20);
        txtMar = new JTextField(16);
        txtMar.setBounds(150, 80, 150, 20);
    }
    
    private void initPasswordFields(){
        txtNumAgreg = new JPasswordField(16);
        txtNumAgreg.setBounds(160, 180, 150, 20);
        txtPre = new JPasswordField(16);
        txtPre.setBounds(420, 180, 180, 20);
    }
    private void initComponents(){
        this.getContentPane().add(lblAgregarProductos);
        this.getContentPane().add(lblCod);
        this.getContentPane().add(lblMarc);
        this.getContentPane().add(lblPrec);
        this.getContentPane().add(txtCod);
        this.getContentPane().add(txtMar);
        this.getContentPane().add(txtPre);
        this.getContentPane().add(txtNumAgreg);
        this.getContentPane().add(btnAgregar);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
       String agregar = "";
        String NumAgreg = "160300";
        if(e.getSource() == btnAgregar);
        setA(a++);
         if(agregar.equals(txtCod.getText()) &&
               NumAgreg.equals(new String(txtNumAgreg.getPassword()))){
                JOptionPane.showMessageDialog(null,
                        "Producto Agregado Exitosamente", "Alerta", 
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                 if(e.getSource() == btnAgregar);
         if(agregar.equals(txtMar.getText()) &&
               NumAgreg.equals(new String(txtPre.getPassword()))){
                JOptionPane.showMessageDialog(null,
                        "Producto Agregado Exitosamente", "Alerta", 
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
    }
         }
    }

    /**
     * @return the a
     */
    public int getA() {
        return a;
    }

    /**
     * @param a the a to set
     */
    public void setA(int a) {
        this.a = a;
    }
}
