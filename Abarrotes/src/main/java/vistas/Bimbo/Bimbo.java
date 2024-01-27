/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas.Bimbo;

import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author yaely
 */
public class Bimbo extends  JDialog  {
    JLabel lblBimb;
    
    public Bimbo(){
      //Método para centrar la ventana en pantalla
        this.setLocationRelativeTo(null);
        //Método para tamaño de ventana
        this.setSize(480, 320);
        //Quitar modo de redimensión de ventana
        this.setResizable(false);
        this.setTitle("Coca");
        this.setModal(true);
        //Cambiar tipo de ordenamiento de compomnentes
        this.setLayout(null);
        this.initLables();
        this.initComponents();
        /*this.initButtons();
        this.initLabels();
        this.initTextFields();
        this.initPasswordFields();
        this.initComponents();*/
    }
     private void initLables(){
        String info;
        info = "<html> Creado por: JACEMY </br></br> ";
        info += "Fecha "+(new Date().toString()+ "</br> ");
        info += "Fue creado para el punto de venta "
                + "Abarrotes Piolin </html> ";
        lblBimb = new JLabel(info);
        lblBimb.setBounds(10, 10, 400, 200);
    }
    
    private void initComponents(){
        this.getContentPane().add(lblBimb);
    }
}
