
package vistas;
import vistas.CU.Modificacion;
import vistas.CU.Seleccion;
import vistas.CU.Mostrar;
import vistas.CU.Insercion;
import vistas.CU.Eliminacion;
import java.awt.event.*;
import javax.swing.*;

public class MenuCU extends JDialog implements ActionListener{
    
    JRadioButton rbtn1, rbtn2, rbtn3, rbtn4, rbtn5, rbtn6, rbtn7;
    ButtonGroup btngrp;
    JLabel lblMenuBD;
    JButton btnOK, btnRegresar;
    
    public MenuCU(){
        this.setTitle("Usuarios");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setModal(1 == 1);
        this.initRdBtn();
        this.initBtn();
        this.initComponents();
    }
    
    private void initBtn(){
        btnOK = new JButton("Aceptar");
        btnOK.setBounds(120, 300, 100, 20);
        btnOK.addActionListener(this);
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(120, 340, 100, 20);
        btnRegresar.addActionListener(this);
    }
    
    private void initRdBtn(){
//        rbtn1 = new JRadioButton("Inserción ", true);
//        rbtn1.setBounds(20, 20, 150, 20);
//        rbtn2 = new JRadioButton("Selección ", false);
//        rbtn2.setBounds(20, 50, 150, 20);
//        btngrp.add(rbtn1);
//        btngrp.add(rbtn2);
//        btngrp.add(rbtn3);

rbtn1 = new JRadioButton("Insertar nuevo usuario", true);
        rbtn1.setBounds(120, 20, 220, 20);
        rbtn2 = new JRadioButton("Selección de usuario", false);
        rbtn2.setBounds(120, 60, 220, 20);
        rbtn3 = new JRadioButton("Eliminación de usuario", false);
        rbtn3.setBounds(120, 100, 220, 20);
        rbtn4 = new JRadioButton("Modificación de lista a usuarios", false);
        rbtn4.setBounds(120, 140, 220, 20);
        rbtn5 = new JRadioButton("Mostrar listas de usuarios", false);
        rbtn5.setBounds(120, 180, 220, 20);
        rbtn6 = new JRadioButton("Inserción Registro", false);
        rbtn6.setBounds(120, 220, 220, 20);
        rbtn7 = new JRadioButton("Mostrar Registro", false);
        rbtn7.setBounds(120, 260, 220, 20);
        btngrp = new ButtonGroup();
        btngrp.add(rbtn1);
        btngrp.add(rbtn2);
        btngrp.add(rbtn3);
        btngrp.add(rbtn4);
        btngrp.add(rbtn5);
        btngrp.add(rbtn6);
        btngrp.add(rbtn7);
    }
    
    private void initComponents(){
        this.getContentPane().add(rbtn1);
        this.getContentPane().add(rbtn2);
        this.getContentPane().add(rbtn3);
        this.getContentPane().add(rbtn4);
        this.getContentPane().add(rbtn5);
        this.getContentPane().add(rbtn6);
        this.getContentPane().add(rbtn7);
        this.getContentPane().add(btnOK);
        this.getContentPane().add(btnRegresar);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btnOK);
            if(rbtn1.isSelected()){
                new Insercion().setVisible(true);
            }
            if (rbtn2.isSelected()) {
                new Seleccion().setVisible(true);
            }
            if (rbtn3.isSelected()) {
                new Eliminacion().setVisible(true);
            }
            if (rbtn4.isSelected()) {
                new Modificacion().setVisible(true);
            }
            if (rbtn5.isSelected()) {
                new Mostrar().setVisible(true);
            }
//            if (rbtn6.isSelected()) {
//                new InserciónForánea().setVisible(true);
//            }
//            if (rbtn7.isSelected()) {
//                new MostrarForáneo().setVisible(true);
//            }
//        }
        if (e.getSource() == btnRegresar) {
            this.dispose();
        }
    }  
}

