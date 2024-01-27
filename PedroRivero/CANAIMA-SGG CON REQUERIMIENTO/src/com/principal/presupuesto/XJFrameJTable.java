/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.presupuesto;

import java.awt.AWTEvent;
import java.awt.event.ItemEvent;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * XJFrameJTable es una extension de la clase XJFrame, en donde la clase hija debe tener una JTable y un panel de detalles del registro seleccionado en la JTable. La clase hija obligatoriamente debe llamar en su constructor a los metodos para Configurar e Inicializar componentes, y asi aprovechar todas las funcionalidades, y añadir o redefinir metodos para extender la configuracion y control.
 *
 * La clase hija debe tener JTable, panel de detalles, y paneles actualizables, segun el estado de la ventana.
 *
 * La clase hija debe tener tiene un boton jbRetornar, estandar para este tipo de ventanas.
 *
 * La clase hija debe:
 *
 * 1.- LLamar en su constructor, a los siguientes metodos o sus equivalentes, en estricto orden: ConfigComponents(); // Con un Map, donde este jbReturn, jcbFilter, jtCriterio, jbReset, jtable InitConditions(); UpdatePanels();
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
 * void jbSeleccionarActionPerformed();
 *
 * @author Capip Sistemas C.A.
 */
public abstract class XJFrameJTable extends XJFrame {

    private static final long serialVersionUID = 1L;

    /**
     * 29/10/2016
     *
     * @param inparent
     */
    public XJFrameJTable(final java.awt.Window inparent) {
        super(inparent);
    }

    @Override
    protected void ConfigComponents(final Map<String, Object> inparam) {
        super.ConfigComponents(inparam);
        JButton jbReset = (JButton) inparam.get(JBRESET);
        if (jbReset != null) {
            jbReset.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jbResetearActionPerformed();
                }
            });
        }
        @SuppressWarnings("unchecked")
        JComboBox<String> jcbFilter = (JComboBox) xjparam.get(JCBFILTER);
        if (jcbFilter != null) {
            jcbFilter.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(java.awt.event.ItemEvent evt) {
                    if (evt.getStateChange() == ItemEvent.SELECTED) {
                        jcbFilterItemStateChanged(evt);
                    }
                }
            });
        }
        final JTextField jtFiltro = (JTextField) xjparam.get(JTFILTRO);
        if (jtFiltro != null) {
            jtFiltro.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent e) {
                    newFilter();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    newFilter();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    newFilter();
                }
            });
        }
        JTable jtable = (JTable) inparam.get(JTABLE);
        if (jtable != null) {
            jtable.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        jbSeleccionarActionPerformed(evt);
                    }
                }
            });
            // Para actualizar automaticamente los campos de detalles, al seleccionar una fila de la tabla
            // al estilo master/detail
            jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent inlse) {
                    if (inlse.getValueIsAdjusting()) {
                        return;
                    }
                    UpdatePanelDetails();
                }
            });
            jtable.setRowSorter(new TableRowSorter<>(jtable.getModel()));
        }
    }

    /**
     */
    abstract protected void UpdateJTable();

    /**
     */
    @Override
    abstract protected void UpdatePanels();

    /**
     */
    abstract protected void UpdatePanelDetails();

    /**
     */
    protected void UpdateTotal() {
    }

    /**
     */
    abstract protected void ClearDetails();

    /**
     * @param ine
     */
    abstract protected void jbSeleccionarActionPerformed(AWTEvent ine);

    /**
     * @param inevt
     */
    protected void jcbFilterItemStateChanged(ItemEvent inevt) {
        final JTextField jtCriterio = (JTextField) xjparam.get(JTFILTRO);
        if (jtCriterio != null) {
            jtCriterio.setText("");
            jtCriterio.requestFocusInWindow();
        }
    }

    /**
     * Rev 25/11/2016
     */
    @SuppressWarnings("unchecked")
    private void newFilter() {
        final JComboBox jcbFilter = (JComboBox) xjparam.get(JCBFILTER);
        JTable jtable = (JTable) xjparam.get(JTABLE);
        // Retorna si no hay item seleccionado
        if ((jcbFilter.getSelectedIndex() < 0) || (jtable == null)) {
            return;
        }
        final JTextField jtFiltro = (JTextField) xjparam.get(JTFILTRO);
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {

            final String strFilter = jtFiltro.getText().trim().toUpperCase();

            @Override
            public boolean include(RowFilter.Entry entry) {
                return entry.getValue(jcbFilter.getSelectedIndex()).toString().contains(strFilter);
            }
        };
        // Establecer el filtro
        TableRowSorter sorter = (TableRowSorter) jtable.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>(jtable.getModel());
        }
        sorter.setRowFilter(filter);
        UpdateTotal();
        //jtable.setRowSorter(sorter);
    }

    /**
     */
    protected void jbResetearActionPerformed() {
        @SuppressWarnings("unchecked")
        final JComboBox<String> jcbFilter = (JComboBox) xjparam.get(JCBFILTER);
        final JTextField jtCriterio = (JTextField) xjparam.get(JTFILTRO);
        if (jcbFilter != null) {
            jcbFilter.setSelectedIndex(0);
        }
        if (jtCriterio != null) {
            jtCriterio.setText("");
            jtCriterio.requestFocusInWindow();
        }
    }

    private static final Logger logger = LogManager.getLogger(XJFrameJTable.class);
}
