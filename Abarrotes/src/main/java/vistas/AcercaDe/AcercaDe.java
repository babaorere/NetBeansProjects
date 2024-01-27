package vistas.AcercaDe;
//@author ammg1
import java.util.Date;
import javax.swing.*;
import javax.swing.ImageIcon;

public class AcercaDe extends JDialog {
    JLabel lblAcercaDe,background;
    public AcercaDe(){
        //Método para centrar la ventana en pantalla
        this.setLocationRelativeTo(null);
        //Método para tamaño de ventana
        this.setSize(1080, 817);
        //Quitar modo de redimensión de ventana
        this.setResizable(false);
        this.setTitle("Acerca De");
        this.setModal(true);
        //Cambiar tipo de ordenamiento de compomnentes
        this.setLayout(null);
        this.initLables();
        this.initComponents();
        this.setLocationRelativeTo(null);
        /*this.initButtons();
        this.initLabels();
        this.initTextFields();
        this.initPasswordFields();
        this.initComponents();*/
    }
    
    private void initLables(){
         ImageIcon path = new ImageIcon("C:\\Users\\yaely\\Downloads\\pro\\ProyecAbar\\ProyecAbar\\ImaGJACEMY\\brm2.jpg");
        background = new JLabel (path);
        background.setBounds (-55,-215,1180,1050);
        background.setLayout(null);
        String info;
        info = "<html>Elaborado por: Jacemy, fue creado para el punto de venta Abarrotes Piolin.</br></br><\t";
        info += "\tFecha " +(new Date().toString()+ "</br>.");
        info += " \tFue creado para el dueño del ya mencionado punto de venta ";
        lblAcercaDe = new JLabel(info);
        lblAcercaDe.setBounds(20, -15, 400, 200);
    }
    
    private void initComponents(){
        this.getContentPane().add(lblAcercaDe);
        getContentPane().add(background);
    }
}