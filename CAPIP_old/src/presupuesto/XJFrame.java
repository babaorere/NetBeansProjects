/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import java.awt.Toolkit;
import java.util.Map;
import javax.swing.JButton;

/**
 * XJFrame es una extension de la clase, para que las clases hijas obligatoriamente llamar en su constructor a los metodos para Configurar e Inicializar componentes, y asi aprovechar todas las funcionalidades, y añadir o redefinir metodos para extender la configuracion y control.
 *
 * La clase hija debe tener paneles que deben ser actualizados, segun el estado de la ventana.
 *
 * La clase hija debe tener tiene un boton jbRetornar, un estandar de la aplicacion para este tipo de ventanas.
 *
 * La clase hija debe:
 *
 * 1.- LLamar en su constructor, a los siguientes metodos o sus equivalentes, en estricto orden: ConfigComponents(component); // Con un Map, donde este el boton retornar InitConditions(); UpdatePanels();
 *
 * 2.- Redefinir los siguientes metodos, con la salvedad de que la primera linea sea un super al metodo equivalente en el Padre void ConfigComponents() {super.ConfigComponents();} void InitConditions() {super.InitConditions();) void UpdatePanels() {super.UpdatePanels();}
 *
 * Metodos a ser Implementados en clases hijas void UpdatePanelsNormal(); void UpdatePanelsAdd(); void UpdatePanelsModify(); void UpdatePanelsDelete(); void formWindowActivated(java.awt.event.WindowEvent evt);
 *
 * @author Capip Sistemas C.A.
 */
public abstract class XJFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    public static final String JBRETURN = "jbReturn";
    public static final String JCBFILTER = "jcbFilter";
    public static final String JTFILTRO = "jtFiltero";
    public static final String JBRESET = "jbReset";
    public static final String JTABLE = "jtable";
    public static final String COLOBJ = "colObj";
    public static final String JBSELECT = "jbSelect";
    public static final String JBCANCEL = "jbCancel";

    /*
     * Para llevar un mapeo de los componentes configurables
     */
    protected Map<String, Object> xjparam;

    /**
     * Para mantener una referencia a la ventana anterior
     */
    protected final java.awt.Window parent;

    /**
     * Para mantener el estado de la ventana
     */
    protected WinState winState;

    /**
     * Creates new form _Padre
     *
     * @param _parent
     */
    public XJFrame(final java.awt.Window _parent) {
        super();
        parent = _parent;
        xjparam = null;
    }

    /**
     * Configuracion de los componentes de la ventana. Generalmente establece los modelos o funcionamiento de los componentes.
     *
     * @param _param
     */
    protected void ConfigComponents(final Map<String, Object> _param) {

        xjparam = _param;

        // Para mostrar la ventana en el tope de la pantalla
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);

        // Activar la tecla ESC
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");

        // Para establecer el cancel de una ventana 
        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Salir();
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        JButton jbReturn = (JButton) _param.get("jbReturn");

        if (jbReturn != null) {
            jbReturn.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jbRetornarActionPerformed();
                }
            });
        }
    }

    /**
     * Condiciones iniciales de la ventana. Generalmente valores, estado, centinelas, enabled
     *
     * Aqui se debe establecer el foco inicial. Mas adelante, solo los action pueden modificar el foco.
     *
     */
    protected void InitConditions() {
        winState = WinState.NORMAL;
    }

    /**
     * Establecer los valores de los componentes actualizables como jlabels, textfields, jtable.
     *
     * Como el Guion de la aplicacion indica que cada jpanel tendra su propio update y clear, en esta seccion se debera colocar el respectivo codigo.
     *
     * El Guion de la aplicacion indica que cada Panel debe ser auto contenido.
     *
     * El Guion indica que dependiendo del estado solo habra un requesfocus
     *
     */
    abstract protected void UpdatePanels();

    /**
     *
     * @param evt
     */
    protected void formWindowActivated(java.awt.event.WindowEvent evt) {

    }

    /**
     *
     * @param evt
     */
    protected void formWindowClosing(java.awt.event.WindowEvent evt) {
        Salir();
    }

    /**
     *
     */
    protected void jbRetornarActionPerformed() {
        Salir();
    }

    /**
     * Rev 25/11/2016
     *
     */
    protected void Salir() {

        if (parent != null) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    parent.setVisible(true);
                }
            });

            dispose();
        } else {
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setTitle("CAPI SISTEMAS");
        setMaximumSize(new java.awt.Dimension(1100, 650));
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
