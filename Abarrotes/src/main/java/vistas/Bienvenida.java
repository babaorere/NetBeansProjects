package vistas;

import EntityClassController.UsuariosJpaController;
import java.awt.event.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Bienvenida extends JFrame 
    implements ActionListener{
    JButton btnAcceder;
    JLabel lblUser, lblPass,background;
    JTextField txtUser;
    JPasswordField txtPass;
    EntityManagerFactory emf;
    UsuariosJpaController UJPACtrl;
    
    public Bienvenida(){
        this.initJFrame();
        this.initPersistence("BD_Persistance");
        this.initButtons();
        this.initLabels();
        this.initTextFields();
        this.initPasswordFields();
        this.initComponents();
    }
    
    private void initJFrame(){
        this.setTitle("Abarrotes ");
        //Cerrar ventana y proceso
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Método para centrar la ventana en pantalla
        this.setLocationRelativeTo(null);
        //Método para tamaño de ventana
        this.setSize(1080, 665);
        //Quitar modo de redimensión de ventana
        this.setResizable(false);
        //Cambiar tipo de ordenamiento de compomnentes
        this.setLayout(null);
        this.setLocationRelativeTo(null);
    }

    
    private void initPersistence(String persUnitName){
    emf = Persistence.createEntityManagerFactory(persUnitName);
    UJPACtrl =new UsuariosJpaController(emf);
    }
    
    private void initButtons(){
        btnAcceder = new JButton("Acceder");
        btnAcceder.setBounds(200, 160, 300, 40);
        btnAcceder.addActionListener(this);
    }
    
    private void initLabels(){
        ImageIcon path = new ImageIcon("C:\\Users\\yaely\\Downloads\\pro\\ProyecAbar\\ProyecAbar\\ImaGJACEMY\\Piolin.jpg");
        background = new JLabel (path);
        background.setBounds (-55,-215,1180,1050);
        background.setLayout(null);
        lblUser = new JLabel("Usuario:");
        lblUser.setBounds(30, 20, 150, 20);
        lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(30, 60, 150, 20);
    }
    
    private void initTextFields(){
        txtUser = new JTextField(16);
        txtUser.setBounds(280, 40, 150, 20);
    }
    
    private void initPasswordFields(){
        txtPass = new JPasswordField(16);
        txtPass.setBounds(280, 80, 150, 20);
    }
    
    private void initComponents(){
        this.getContentPane().add(lblUser);
        this.getContentPane().add(lblPass);
        this.getContentPane().add(txtUser);
        this.getContentPane().add(txtPass);
        this.getContentPane().add(btnAcceder);
        getContentPane().add(background);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAcceder){
            EntityManager em = UJPACtrl.getEntityManager();
            Query q = em.createNamedQuery("Usuarios.findByUserPass");
            q.setParameter("user",txtUser.getText());
            q.setParameter("passwd", new String (txtPass.getPassword()));
            if(Integer.parseInt(q.getSingleResult().toString()) == 1){
                JOptionPane.showMessageDialog(null,
                        "Inicio de sesión exitoso", "Alerta", 
                        JOptionPane.INFORMATION_MESSAGE);
                new Menu().setVisible(true);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null,
                        "Usuario y/o contraseña incorrecto(s)",
                        "Alerta", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        String user = "MVB";
//        String pass = "160300";
//        if(e.getSource() == btnAcceder){
//            if(user.equals(txtUser.getText()) &&
//               pass.equals(new String(txtPass.getPassword()))){
//                JOptionPane.showMessageDialog(null,
//                        "Inicio de sesión exitoso", "Alerta", 
//                        JOptionPane.INFORMATION_MESSAGE);
//                new Menu().setVisible(true);
//                this.dispose();
//            }else{
//                JOptionPane.showMessageDialog(null,
//                        "Usuario y/o contraseña incorrecto(s)",
//                        "Alerta", 
//                        JOptionPane.INFORMATION_MESSAGE);
//            }
//        }
    }
}
