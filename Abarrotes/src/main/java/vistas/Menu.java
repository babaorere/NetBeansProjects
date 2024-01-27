package vistas;

import java.awt.Desktop;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import vistas.AcercaDe.AcercaDe;

public class Menu extends JFrame
    implements ActionListener {

    JMenuBar jmbMenuBar;
    JMenu jmInicio, jmUsuarios, jmAyuda, jmVentas, jmInventario, jmCompras;

    JMenu jmInicioDespliegue, jmRevisarInventario, jmProductos;

    JMenuItem jmiSaludo, jmiSalir, jmiAcercaDe, jmiAgregarProductos, jmiBD,
        jmiCoc, jmiPep, jmiBar, jmiSab, jmiBimb, jmiAbarrotes, jmimanualUsuario, jmiManualUsuario, jmiFrameInto;
    JLabel background;

    public Menu() {
        super("Menu");
        //Cerrar ventana y proceso
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Método para centrar la ventana en pantalla
        this.setLocationRelativeTo(null);
        //Método para tamaño de ventana
        this.setSize(480, 345);
        //Quitar modo de redimensión de ventana
        this.setResizable(false);
        //Cambiar tipo de ordenamiento de compomnentes
        this.setLayout(null);
        this.initLabels();
        this.initJMenuItem();
        this.initJMenu();
        this.initJMenuBar();
        this.initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initJMenuBar() {
        jmbMenuBar = new JMenuBar();
        jmbMenuBar.add(jmInicio);
        jmbMenuBar.add(jmVentas);
        jmbMenuBar.add(jmCompras);
        jmbMenuBar.add(jmInventario);
        jmbMenuBar.add(jmAyuda);
        jmbMenuBar.add(jmUsuarios);

    }

    private void initJMenu() {
        jmInicio = new JMenu("Inicio");
        jmInicio.add(jmiSaludo);
        jmInicio.add(jmInicioDespliegue);
        jmInicio.add(jmiBD);

        jmUsuarios = new JMenu("Usuarios");
        jmInicio.add(jmiSaludo);
        jmInicio.add(jmInicioDespliegue);
        jmInicio.add(jmiBD);

        jmVentas = new JMenu("Ventas");

        jmCompras = new JMenu("Compras");
        jmCompras.add(jmiAgregarProductos);

        jmInventario = new JMenu("Inventario");
        jmInventario.add(jmProductos);

        jmAyuda = new JMenu("Ayuda");
        jmAyuda.add(jmimanualUsuario);
        jmAyuda.add(jmiAcercaDe);

    }

    private void initJMenuItem() {
        jmiAcercaDe = new JMenuItem("Acerca de");
        jmiAcercaDe.addActionListener(this);
        jmInicioDespliegue = new JMenu("Despliegue");

        jmiSaludo = new JMenuItem("Saludo");
        jmiSaludo.addActionListener(this);

        jmiSalir = new JMenuItem("Salir");
        jmiSalir.addActionListener(this);
        jmInicioDespliegue.add(jmiSalir);

        jmiBD = new JMenuItem("Usuarios 'Abarrotes Piolin' ");
        jmiBD.addActionListener(this);
        jmiAgregarProductos = new JMenuItem("Agregar Productos");
        jmiAgregarProductos.addActionListener(this);
        jmRevisarInventario = new JMenu("Revisar Inventario");
        jmRevisarInventario.addActionListener(this);
        jmiAcercaDe = new JMenuItem("Acerca de");
        jmiAcercaDe.addActionListener(this);
        jmInicioDespliegue = new JMenu("Despliegue");
        jmiSaludo = new JMenuItem("Saludo");
        jmiSaludo.addActionListener(this);
        jmiSalir = new JMenuItem("Salir");
        jmiSalir.addActionListener(this);
        jmInicioDespliegue.add(jmiSalir);
        jmProductos = new JMenu("Productos");
        jmProductos.addActionListener(this);
        jmiCoc = new JMenuItem("Coca-Cola Femsa");
        jmiCoc.addActionListener(this);
        jmProductos.add(jmiCoc);
        jmiPep = new JMenuItem("Pepsi");
        jmiPep.addActionListener(this);
        jmProductos.add(jmiPep);
        jmiBar = new JMenuItem("Barcel");
        jmiBar.addActionListener(this);
        jmProductos.add(jmiBar);
        jmiSab = new JMenuItem("Sabritas");
        jmiSab.addActionListener(this);
        jmProductos.add(jmiSab);
        jmiBimb = new JMenuItem("Bimbo");
        jmiBimb.addActionListener(this);
        jmProductos.add(jmiBimb);
        jmiAbarrotes = new JMenuItem("Otros");
        jmiAbarrotes.addActionListener(this);
        jmProductos.add(jmiAbarrotes);
        jmiAgregarProductos = new JMenuItem("Agregar Productos");
        jmiAgregarProductos.addActionListener(this);
        jmRevisarInventario = new JMenu("Revisar Inventario");
        jmRevisarInventario.addActionListener(this);
        jmiAcercaDe = new JMenu("Acerca de");
        jmiManualUsuario = new JMenuItem("Manual de Usuario");
        jmiManualUsuario.addActionListener(this);
        jmiAcercaDe.add(jmiManualUsuario);
        jmiFrameInto = new JMenuItem("Frame (Into)");
        jmiFrameInto.addActionListener(this);
        jmiAcercaDe.add(jmiFrameInto);
        jmimanualUsuario = new JMenuItem("Manual de Usuario");
        jmimanualUsuario.addActionListener(this);

    }

    private void initLabels() {
        ImageIcon path = new ImageIcon("C:\\Users\\yaely\\Downloads\\pro\\ProyecAbar\\ProyecAbar\\ImaGJACEMY\\Piolin2.jpeg");
        background = new JLabel(path);
        background.setBounds(0, 0, 480, 283);
        background.setLayout(null);

    }

    private void initComponents() {
        this.setJMenuBar(jmbMenuBar);
        getContentPane().add(background);
    }

    public void AbrirArchivo(String pdf) {
        try {
            Desktop.getDesktop().open(new File(pdf));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir Guia de Usuario" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jmiSaludo) {
            JOptionPane.showMessageDialog(null,
                "¡Bienvenidos al programa de abarrotes Piolin!",
                "Alerta", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == jmiSalir) {
            System.exit(0);
        }
        if (e.getSource() == jmiAcercaDe) {
            new AcercaDe().setVisible(true);
        }
        if (e.getSource() == jmiBD) {
            new MenuCU().setVisible(true);
        }
        if (e.getSource() == jmiAgregarProductos) {
            new MenuComp().setVisible(true);
        }
        if (e.getSource() == jmimanualUsuario || e.getSource() == jmiManualUsuario) {
            AbrirArchivo("MANUALDEUSUARIO.pdf");
        }

        if (e.getSource() == jmiFrameInto) {
            new AcercaDe().setVisible(true);
        }

        if (e.getSource() == jmiCoc) {
            AbrirArchivo("Coca-Cola.pdf");
        }
        if (e.getSource() == jmiPep) {
            AbrirArchivo("Pepsi.pdf");
        }
        if (e.getSource() == jmiBar) {
            AbrirArchivo("Barcel.pdf");
        }
        if (e.getSource() == jmiBimb) {
            AbrirArchivo("Bimbo.pdf");
        }
        if (e.getSource() == jmiSab) {
            AbrirArchivo("Sabritas.pdf");
        }
        if (e.getSource() == jmiAbarrotes) {
            AbrirArchivo("Abarrotes.pdf");
        }
    }
}
