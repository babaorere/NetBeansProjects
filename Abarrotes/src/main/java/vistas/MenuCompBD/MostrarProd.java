package vistas.MenuCompBD;
import vistas.CU.*;
import EntityClass.Usuarios;
import EntityClassController.UsuariosJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class MostrarProd extends JDialog {

    DefaultTableModel dTable;
    JTable jTable;
    List<Usuarios> perList;
    EntityManagerFactory emf;
    UsuariosJpaController uJPACtrl;

    public MostrarProd() {
        this.initFrame();
        this.initPersistence("BD_Persistance");
        this.initTables();
        this.initComponents();
    }

    private void initFrame() {
        this.setTitle("Lista de Usuarios");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
    }

    private void initPersistence(String persUnitName) {
        emf = Persistence.createEntityManagerFactory(persUnitName);
        uJPACtrl = new UsuariosJpaController(emf);
    }

    private void initTables() {
        dTable = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable = new JTable(dTable);
        valuesTables();
    }

    private void initComponents() {
        getContentPane().add(jTable.getTableHeader());
        getContentPane().add(jTable);
    }

    private void valuesTables() {
        perList = uJPACtrl.findUsuariosEntities();
        dTable.setColumnIdentifiers(new String[]{"Id"
                , "Usuario", "Edad", "Sexo","passwd"});
        for (Usuarios usu : perList) {
            dTable.addRow(
                    new Object[]{usu.getId(), 
                        usu.getUser(), 
                        usu.getEdad(),
                        usu.getSexo(),
                        usu.getPasswd()
                        });
        }
        jTable.getTableHeader().setBounds(0, 0, this.getWidth() - 15, 20);
        jTable.setBounds(0, 20, this.getWidth() - 15, 16 * perList.size());
    }
    
}
