package com.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import static java.lang.System.exit;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class BellmanFordDialog extends JDialog {

    private void showDVTables(JPanel inBottomSubPanel) {

        // A los fines de sincronizar la visualizacion de componentes, con el sistema de Eventos de Java
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                inBottomSubPanel.removeAll();

                GridBagLayout gridBagLayout = new GridBagLayout();
                inBottomSubPanel.setLayout(gridBagLayout);

                GridBagConstraints constraints = new GridBagConstraints();
                // Establece el alto fijo de las filas
                constraints.fill = GridBagConstraints.BOTH;
                constraints.weightx = 1.0;

                // Recorremos todos los nodos
                for (Map.Entry< Integer, Node> entryNode : mapNodes.entrySet()) {
                    constraints.gridy = entryNode.getKey();
                    constraints.gridwidth = 1;
                    constraints.gridheight = 1;

                    // Establece alturas fijas para las filas (por ejemplo, 30 píxeles)
                    constraints.insets = new Insets(5, 5, 5, 5); // Espaciado
                    constraints.ipady = 30;

                    // recorremos la DV Table del nodo en cuestion
                    for (ConcurrentHashMap.Entry< Integer, Integer> entryDV : entryNode.getValue().getDv().entrySet()) {

                        constraints.gridx = entryDV.getKey();

                        final String strValue;
                        if (entryDV.getValue() >= Integer.MAX_VALUE) {
                            strValue = "-";
                        } else {
                            strValue = String.valueOf(entryDV.getValue());
                        }

                        JLabel lbl = new JLabel("<html><div style='text-align: center;'>[" + entryNode.getKey() + " -> " + entryDV.getKey() + "]<br> " + strValue + "</div></html>");
                        lbl.setHorizontalAlignment(JLabel.CENTER);
                        lbl.setForeground(Color.WHITE);
                        gridBagLayout.setConstraints(lbl, constraints);
                        inBottomSubPanel.add(lbl);
                    }
                }
                // Actualizar la interface grafica
                inBottomSubPanel.revalidate();
                inBottomSubPanel.repaint();
            }

        }
        );
    }

    private final int W = 1000;
    private final int H = 600;

    private final Main main;
    private BellmanFordMultiThread bellmanFord;

    private ConcurrentHashMap<Integer, Node> mapNodes;

    private LinkRepo linkRepo;

    private boolean huboCambio;
    private int contCambios;
    private final NodePanel leftPanel;
    private final JPanel rightPanel;
    private final JPanel bottomSubPanel;
    private final JLabel lblTime;
    private final JButton btnPlay;
    private final JButton btnStep;

    public BellmanFordDialog(Window inParent) {
        super(inParent, "Graphics Presentatien", ModalityType.APPLICATION_MODAL);

        main = (Main) inParent;

        bellmanFord = null;
        huboCambio = true;
        contCambios = 0;
        this.linkRepo = main.getLinkRepo();
        mapNodes = linkRepo.getListNodes();

        setLocationRelativeTo(null);
        setSize(W, H);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        centerDialogOnScreen();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Panel izquierdo para nodos y campos de texto
        leftPanel = new NodePanel(this, mapNodes, linkRepo, main);
        leftPanel.setBounds(0, 0, W / 2, H);
        leftPanel.setLayout(null);
        leftPanel.setBackground(Color.BLACK);

        // Panel derecho con subpaneles
        rightPanel = new JPanel();
        rightPanel.setBounds(W / 2 + 1, 0, W / 2, H);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.RED);

        // Subpanel superior con botones
        JPanel topSubPanel = new JPanel();
        topSubPanel.setBounds(0, 0, W / 2, 50);
        topSubPanel.setLayout(new FlowLayout());

        lblTime = new JLabel("tiempo/ciclo T0");
        lblTime.setToolTipText("Contador de tiempo/ciclos");

        JButton btnIniciar = new JButton("Start BF");
        URL iniciarImgURL = getClass().getResource("/images/record.png");
        if (iniciarImgURL != null) {
            btnIniciar = new JButton(new ImageIcon(iniciarImgURL));
        } else {
            btnIniciar = new JButton("Iniciar");
        }

        btnIniciar.setToolTipText("Cargar el archivo de datos");

        URL playImgURL = getClass().getResource("/images/play.png");
        if (playImgURL != null) {
            btnPlay = new JButton(new ImageIcon(playImgURL));
        } else {
            btnPlay = new JButton("Start");
        }
        btnPlay.setToolTipText("Ejecutar el algoritmo Bellman-Ford");
        btnPlay.setEnabled(true);

        URL pauseImgURL = getClass().getResource("/images/pause.png");
        if (pauseImgURL != null) {
            btnStep = new JButton(new ImageIcon(pauseImgURL));
        } else {
            btnStep = new JButton("xStep");
        }
        btnStep.setToolTipText("Ejecutar paso a paso el algoritmo Bellman-Ford");
        btnStep.setEnabled(true);

        topSubPanel.add(lblTime);
        topSubPanel.add(new JLabel("  "));
        topSubPanel.add(btnIniciar);
        topSubPanel.add(btnPlay);
        topSubPanel.add(btnStep);

        // Subpanel inferior para dibujar las DV Tables
        bottomSubPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(bottomSubPanel);
        showDVTables(bottomSubPanel);

        rightPanel.add(topSubPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        // Establece el color de fondo del JPanel
        bottomSubPanel.setBackground(Color.BLUE);

        // Agregar acción a los botones
        // Vamos a recargar el archivo seleccionado
        btnIniciar.addActionListener(e -> {

            // Reiniciamos los links y los nodos
            contCambios = 0;
            lblTime.setText("tiempo/ciclo T" + contCambios);
            huboCambio = true;
            btnPlay.setEnabled(true);
            btnStep.setEnabled(true);

            // Cargamos la informacion del archivo seleccionado            
            this.linkRepo = main.getLinkRepo();

            // Verificar que se halla leido el archivo
            if (linkRepo == null) {
                JOptionPane.showMessageDialog(null, "No se pudo cargar el archivo. Fin del programa", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Obtenemos los nodos
            mapNodes = linkRepo.getListNodes();

            // Verificamos condiciones
            if ((mapNodes == null) || (mapNodes.size() <= 0)) {
                JOptionPane.showMessageDialog(this, "No hay suficientes datos para ejecutar el algoritmo. Cantidad de nodos: " + mapNodes.size());
                exit(0);
                return;
            }

            // Vemos la "DV Table"
            showDVTables(bottomSubPanel);
        });

        btnPlay.addActionListener(e -> {
            bellmanFord();
            showDVTables(bottomSubPanel);
        });

        btnStep.addActionListener(e -> {

            if (huboCambio) {
                bellmanFordStep();
                showDVTables(bottomSubPanel);
            }

        });

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        add(mainPanel);
    }

    /**
     * Preparacion para ejecutar el algoritmo
     */
    public void bellmanFord() {

        // Verificamos, que no hay de por medio una operacion step x step
        if (bellmanFord == null) {
            bellmanFord = new BellmanFordMultiThread(mapNodes);
        }

        // ejecutamos y guardamos los ciclos ejecutados
        contCambios += bellmanFord.bellmanFord();
        lblTime.setText("tiempo/ciclo T" + contCambios);

        // Se deshabilitan los botones ya que no tiene sentido continuar si se ha terminado
        btnPlay.setEnabled(false);
        btnStep.setEnabled(false);

        // inicializamos
        bellmanFord = null;
        huboCambio = false;
        contCambios = 0;
    }

    /**
     * Preparacion para ejecutar el algoritmo, paso a paso. En este caso se van contabilizando
     * los cambios, el atributo "bellmanFord" que corresponde a la instacia del algoritmo,
     * no se inicializa, para permitir que los valores se mantengan para el siguiente ciclo
     */
    public void bellmanFordStep() {

        // Verificamos, si hay o no, de por medio una operacion step x step
        if (bellmanFord == null) {
            bellmanFord = new BellmanFordMultiThread(mapNodes);
        }

        huboCambio = bellmanFord.bellmanFordStep();

        // Si al ejecutar el algoritmo, hubo un cambio, actualizamos
        if (huboCambio) {
            contCambios++;
            lblTime.setText("tiempo/ciclo T" + contCambios);
        } else {
            // indicamos que ya se termino con esta corrida
            // inicializamos
            bellmanFord = null;
            huboCambio = false;
            contCambios = 0;

            // Se deshabilitan los botones ya que no tiene sentido continuar si se ha terminado
            btnPlay.setEnabled(false);
            btnStep.setEnabled(false);
        }

    }

    @Override
    /**
     * Para ajustar el tamaño final del panel, es necesario tener disponible las dimensiones finales del JDialog,
     * por eso hay que esperar que el JDialog se muestre
     */
    public void setVisible(boolean inB) {
        leftPanel.setCenter(getWidth(), getHeight());
        super.setVisible(inB);
    }

    /**
     * Utiliza la libreria de Java, para centrar la ventana en la pantalla nro. 1 del sistema,
     * tener pantallas multible
     */
    private void centerDialogOnScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        Rectangle bounds = new Rectangle();
        GraphicsDevice screen = screens[0];
        bounds.add(screen.getDefaultConfiguration().getBounds());

        Dimension dialogSize = this.getSize();
        int x = bounds.x + (bounds.width - dialogSize.width) / 2;
        int y = bounds.y + (bounds.height - dialogSize.height) / 2;
        this.setLocation(x, y);
    }

    /**
     * A los fines de pruebas y debug
     *
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            BellmanFordDialog dialog = new BellmanFordDialog(new Main());
            dialog.setVisible(true);
        });
    }
}
