///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package vistas.ticket;
//import EntityClass.Ticket;
//import EntityClassController.UsuariosJpaController;
//import java.util.List;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.swing.JDialog;
//import javax.swing.JTable;
//import javax.swing.table.DefaultTableModel;
//import vistas.AgregarProductos.AgregarProductos;
//import vistas.AgregarProductos.AgregarProductos;
///**
// *
// * @author yaely
// */
//public class ticket extends JDialog {
//    private int b;
//
//    DefaultTableModel dTable;
//    JTable jTable;
//    List<Ticket> perList;
//    EntityManagerFactory emf;
//    UsuariosJpaController uJPACtrl;
//
//    public ticket() {
//        this.initFrame();
//        this.initPersistence("BD_Persistance");
//        this.initTables();
//        this.initComponents();
//    }
//
//    private void initFrame() {
//        this.setTitle("Lista de Usuarios");
//        this.setSize(800, 600);
//        this.setLocationRelativeTo(null);
//        this.setLayout(null);
//        this.setResizable(false);
//        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//        this.setModal(true);
//    }
//
//    private void initPersistence(String persUnitName) {
//        emf = Persistence.createEntityManagerFactory(persUnitName);
//        uJPACtrl = new UsuariosJpaController(emf);
//    }
//
//    private void initTables() {
//        dTable = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        jTable = new JTable(dTable);
//        valuesTables();
//    }
//
//    private void initComponents() {
//        getContentPane().add(jTable.getTableHeader());
//        getContentPane().add(jTable);
//    }
//
//    private void valuesTables() {
//        perList = uJPACtrl.findTicketEntities();
//        dTable.setColumnIdentifiers(new String[]{"Id"
//                , "marca", "cantidad", "precio"});
//        for (Ticket tick : perList) {
//            dTable.addRow(
//                    new Object[]{tick.getId(), 
//                        tick.getMarca(), 
//                        tick.getCantidad(),
//                        tick.getPrecio()
//                    });
//        }
//        jTable.getTableHeader().setBounds(0, 0, this.getWidth() - 15, 20);
//        jTable.setBounds(0, 20, this.getWidth() - 15, 16 * perList.size());
//    }
//
//    /**
//     * @return the b
//     */
//    public int getB() {
//        return b;
//    }
//
//    /**
//     * @param b the b to set
//     */
//    public void setB(int b) {
//        this.b = b;
//    }
//    
//}
