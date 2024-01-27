
package vistas;

//import vistas.ticket.ticket;
import java.awt.event.*;
import javax.swing.*;
import vistas.MenuCompBD.InsercionProd;
import vistas.AgregarProductos.AgregarProductos;

public class MenuComp extends JDialog implements ActionListener{
    
    JRadioButton rbtn1, rbtn2, rbtn3, rbtn4, rbtn5;
    ButtonGroup btngrp;
    JLabel lblMenucompras;
    JButton btnOK, btnRegresar, btnCompNue;
    
    public MenuComp(){
        this.setTitle("Lista de productos ");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setModal(2 == 2);
        this.initRdBtn();
        this.initBtn();
        this.initComponents();
    }
    
    private void initBtn(){
        btnOK = new JButton("Aceptar");
        btnOK.setBounds(120, 220, 100, 20);
        btnOK.addActionListener(this);
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(120, 260, 100, 20);
        btnRegresar.addActionListener(this);
        btnCompNue = new JButton("Compra nueva");
        btnCompNue.setBounds(120, 300, 100, 20);
        btnCompNue.addActionListener(this);
    }
    
    private void initRdBtn(){
        rbtn1 = new JRadioButton("Agregar nuevo Producto", true);
        rbtn1.setBounds(120, 20, 220, 20);
        rbtn2 = new JRadioButton("Seleccion de producto", false);
        rbtn2.setBounds(120, 60, 220, 20);
        rbtn3 = new JRadioButton("Eliminación de producto", false);
        rbtn3.setBounds(120, 100, 220, 20);
        rbtn4 = new JRadioButton("Modificación de lista de prductos", false);
        rbtn4.setBounds(120, 140, 220, 20);
        rbtn5 = new JRadioButton("Mostrar listas de productos", false);
        rbtn5.setBounds(120, 180, 220, 20);

        btngrp = new ButtonGroup();
        btngrp.add(rbtn1);
        btngrp.add(rbtn2);
        btngrp.add(rbtn3);
        btngrp.add(rbtn4);
        btngrp.add(rbtn5);

    }
    
    private void initComponents(){
        this.getContentPane().add(rbtn1);
        this.getContentPane().add(rbtn2);
        this.getContentPane().add(rbtn3);
        this.getContentPane().add(rbtn4);
        this.getContentPane().add(rbtn5);

        this.getContentPane().add(btnOK);
        this.getContentPane().add(btnCompNue);
        this.getContentPane().add(btnRegresar);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btnOK);
            if(rbtn1.isSelected()){
              new InsercionProd().setVisible(true); 
            }
//            if (rbtn2.isSelected()) {
//                new Seleccion().setVisible(true);
//            }
//            if (rbtn3.isSelected()) {
//                new Eliminacion().setVisible(true);
//            }
//            if (rbtn4.isSelected()) {
//                new Modificacion().setVisible(true);
//            }
//            if (rbtn5.isSelected()) {
//                new Mostrar().setVisible(true);
//            }

        if (e.getSource() == btnRegresar) {
            this.dispose();
        }
//        if (e.getSource() == btnCompNue){
//            int var=0;
//            var=0;
//            if(var<1){
//                ticket tic = new ticket();
//                AgregarProductos aux = new AgregarProductos();
//                tic.setB(aux.getA());
//                var++;
//            }
            
//            new InsercionProd().setVisible(true);
//        }
    }  
}