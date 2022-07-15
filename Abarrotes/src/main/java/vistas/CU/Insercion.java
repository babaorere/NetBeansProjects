
package vistas.CU;
import EntityClass.Usuarios;
import EntityClassController.UsuariosJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import vistas.*;
import javax.swing.*;

public class Insercion extends JDialog implements ActionListener{
    
    JLabel lbluser, lbledad, lblsexo,lblpasswd;
    JTextField txtuser,txtpasswd;
    JSpinner jsedad;
    JComboBox jcbsexo;
    JButton btnGuardar;
    EntityManagerFactory emf;
    UsuariosJpaController UJPACtrl;
    
    public Insercion(){
        this.initFrame();
        this.initPersistence("BD_Persistance");
        this.initLabels();
        this.initTextFields();
        this.initTextFieldsuno();
        this.initJSpinners();
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
    UJPACtrl =new UsuariosJpaController(emf);
    }
    
    public void initLabels(){
        lbluser = new JLabel("Usuario: ");
        lbluser.setBounds(20, 20, 120, 25);
        lbledad = new JLabel("Edad: ");
        lbledad.setBounds(20, 50, 120, 25);
        lblsexo = new JLabel("Sexo: ");
        lblsexo.setBounds(20, 80, 120, 25);
        lblpasswd = new JLabel("Passwd: ");
        lblpasswd.setBounds(20, 110, 120, 25);
    }
    
    private void initTextFields(){
        txtuser = new JTextField();
        txtuser.setBounds(140, 20, 150, 25);
    }
    
    private void initJSpinners(){
        jsedad = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        jsedad.setBounds(140, 50, 150, 25);
        setJSpinnerTextEditor(false);
    }
    
    private void initComboBoxes(){
        jcbsexo = new JComboBox (new String []{"Seleccione...", "Masculino", "Femenino"});
        jcbsexo.setBounds(140, 80, 150, 25);
    }
      private void initTextFieldsuno(){
        txtpasswd = new JTextField();
        txtpasswd.setBounds(140, 110, 150, 25);
    }
    
    private void initButtons(){
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 170, 150, 25);
        btnGuardar.addActionListener(this);
    }
    
    private void initComponents(){
        getContentPane().add(lbluser);
        getContentPane().add(lbledad);
        getContentPane().add(lblsexo);
        getContentPane().add(lblpasswd);
        getContentPane().add(txtuser);
        getContentPane().add(jsedad);
        getContentPane().add(jcbsexo);
        getContentPane().add(txtpasswd);
        getContentPane().add(btnGuardar);
    }
    
    private void setJSpinnerTextEditor(boolean val){
        ((JSpinner.DefaultEditor) jsedad.getEditor()).getTextField().setEditable(val);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){{
        if (e.getSource() == btnGuardar){
            if (!(this.txtuser.getText().equals("") || this.jcbsexo.getSelectedItem().toString().equals("Seleccione...")));
            Usuarios p = new Usuarios();
            p.setUser(txtuser.getText());
            p.setEdad(Integer.parseInt(jsedad.getValue().toString()));
            p.setSexo(jcbsexo.getSelectedItem().toString());
            p.setPasswd(txtpasswd.getText());
            UJPACtrl.create(p);
            JOptionPane.showMessageDialog(this, "El registro ha queadado guardado", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Hay campos vacios o sin seleccionados", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
        }
    }
    
}
