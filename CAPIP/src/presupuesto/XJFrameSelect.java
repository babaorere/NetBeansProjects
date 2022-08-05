/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import java.awt.AWTEvent;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JTable;

/**
 * XJFrameJTable es una extension de la clase XJFrame, en donde la clase hija debe tener una JTable y un panel de detalles del registro seleccionado en la JTable. La clase hija obligatoriamente debe llamar en su constructor a los metodos para Configurar e Inicializar componentes, y asi aprovechar todas las funcionalidades, y añadir o redefinir metodos para extender la configuracion y control.
 *
 * La clase hija debe tener JTable, panel de detalles, y paneles actualizables, boton de selecciona y de cancelar.
 *
 *
 * La clase hija debe tener tiene un boton jbRetornar, estandar para este tipo de ventanas.
 *
 * La clase hija debe:
 *
 * 1.- LLamar en su constructor, a los siguientes metodos o sus equivalentes, en estricto orden: ConfigComponents(); // Con un Map, donde este jbReturn, jcbFilter, jtCriterio, jbReset, jtable, colObj, jbSelect, jbCancel InitConditions(); UpdatePanels();
 *
 * 2.- Redefinir los siguientes metodos, con la salvedad de que la primera linea sea un super al metodo equivalente en el Padre void ConfigComponents() {super.ConfigComponents();} void InitConditions() {super.InitConditions();) void UpdatePanels() {super.UpdatePanels();}
 *
 * Metodos a ser Implementados en clases hijas
 *
 * void UpdatePanelsNormal(); void UpdatePanelsAdd(); void UpdatePanelsModify(); void UpdatePanelsDelete(); void formWindowActivated(java.awt.event.WindowEvent evt);
 *
 * void UpdateJTable();
 *
 * void UpdatePanelDetailsNormal(); void UpdatePanelDetailsAdd(); void UpdatePanelDetailsModify(); void UpdatePanelDetailsDelete(); void ClearDetails();
 *
 * @author Capip Sistemas C.A.
 */
public abstract class XJFrameSelect extends XJFrameJTable {

    private static final long serialVersionUID = 1L;

    /**
     * Utilizado para intercambio de datos con otras ventanas
     *
     */
    protected static int idSelIO;

    static {
        idSelIO = 0;
    }

    /**
     * @return the id selecionado
     */
    public static int getIdSelIO() {
        final int aux = idSelIO;
        idSelIO = 0;
        return aux;
    }

    /*
     *
     */
    public XJFrameSelect(final java.awt.Window _parent, int _idSelIO) {
        super(_parent);
        idSelIO = _idSelIO;
    }

    /**
     *
     * @param _param
     */
    @Override
    protected void ConfigComponents(final Map<String, Object> _param) {
        super.ConfigComponents(_param);

        JButton jbSelect = (JButton) xjparam.get(JBSELECT);
        if (jbSelect != null) {
            jbSelect.addActionListener(this::jbSeleccionarActionPerformed);
        }

        JButton jbCancel = (JButton) xjparam.get(JBCANCEL);

        if (jbCancel != null) {
            jbCancel.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jbCancelarActionPerformed();
                }
            });
        }
    }

    /**
     *
     * @param _e
     */
    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent _e) {
        JTable jtable = (JTable) xjparam.get(JTABLE);
        Integer colReg = (Integer) xjparam.get(COLOBJ);

        if (jtable != null) {
            int selRow = jtable.getSelectedRow();
            if (selRow >= 0) {
                int col = colReg == null ? -1 : colReg;
                if (col >= 0) {
                    idSelIO = ((ID) jtable.getValueAt(selRow, col)).getId();
                    Salir();
                }
            }
        }
    }

    /**
     *
     */
    protected void jbCancelarActionPerformed() {
        idSelIO = 0;
        Salir();
    }

}
