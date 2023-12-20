package com.app;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Programa compilado y probado con JDK 17.
 *
 * Punto de entrada del programa, primero se procede a buscar el nombre del archivo de datos,
 * en el directorio de ejecucion con nombre "disVecRou.txt" por omision, si no existe, se le pregunta al
 * usuario. Luego se procede a ejecutar el algoritmo Bellman-Ford, en modo multitarea e hilos (multitasking and threads).
 *
 * From Wikipedia, the free encyclopedia
 * Bellman–Ford algorithm
 * The Bellman–Ford algorithm is an algorithm that computes shortest paths from a single source vertex to all of
 * the other vertices in a weighted digraph.[1] It is slower than Dijkstra's algorithm for the same problem,
 * but more versatile, as it is capable of handling graphs in which some of the edge weights are negative numbers.
 *
 * Finding negative cycles
 * When the algorithm is used to find shortest paths, the existence of negative cycles is a problem,
 * preventing the algorithm from finding a correct answer. However, since it terminates upon finding a negative cycle,
 * the Bellman–Ford algorithm can be used for applications in which this is the target to be sought – for example in
 * cycle-cancelling techniques in network flow analysis.
 *
 * @param
 */
public final class Main extends JFrame {

    BidirectionalMap<Integer, Integer> nickNames;

    BellmanFordDialog bfGraph;

    private File file;

    private final JFrame jframe = this;

    private static final Logger logger = LogManager.getLogger(BellmanFordMultiThread.class);

    private LinkRepo listLink;

    public Main() {

        // Inicializacion
        listLink = new LinkRepo();
        nickNames = new BidirectionalMap<Integer, Integer>();

        file = null;
        bfGraph = null;

        // Crear una barra de menú
        JMenuBar menuBar = new JMenuBar();

        Font originalFont = menuBar.getFont();
        Font newFont = originalFont.deriveFont(originalFont.getSize() * 1.5f);

        // Crear un menú principal
        JMenu mainMenu = new JMenu("Archivo");
        mainMenu.setFont(newFont);
        menuBar.add(mainMenu);

        // Crear un elemento de menú en el menú principal
        JMenuItem itemAlgoritmo = new JMenuItem("Algoritmo");
        itemAlgoritmo.setFont(newFont);
        mainMenu.add(itemAlgoritmo);
        // Aqui le indicamos la accion que hacer para el item del menu
        itemAlgoritmo.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                bfGraph = new BellmanFordDialog(jframe);
                bfGraph.setVisible(true);
            });
        });

        // Agregar una línea de separación
        mainMenu.add(new JSeparator());

        JMenuItem itemOpen = new JMenuItem("Abrir");
        itemOpen.setFont(newFont);
        mainMenu.add(itemOpen);
        // Agregar acción al elemento para seleccionar el archivo
        itemOpen.addActionListener(e -> fileChooser());

        // Agregar una línea de separación
        mainMenu.add(new JSeparator());

        // Crear un elemento de menú "Salir"
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.setFont(newFont);
        mainMenu.add(itemSalir);
        // Agregar acción al elemento "Acerca de..."
        itemSalir.addActionListener(e -> salir());

        // Crear un menú "Ayuda"
        JMenu ayudaMenu = new JMenu("Ayuda");
        ayudaMenu.setFont(newFont);
        menuBar.add(ayudaMenu);

        // Crear un submenú "Acerca de..."
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de...");
        itemAcercaDe.setFont(newFont);
        ayudaMenu.add(itemAcercaDe);
        itemAcercaDe.addActionListener(e -> mostrarAcercaDe());

        // Configurar la barra de menú para esta ventana
        setJMenuBar(menuBar);

        // Configurar otros aspectos de la ventana
        setTitle("***   Distance Vector Routing   ***");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Si no tenemos un archivo de datos, finalizar la aplicacion
        if (buscarArchivo() == null) {
            JOptionPane.showMessageDialog(null, "Archivo no encontrado, se finalizara la aplicacion", "ERROR", JOptionPane.ERROR_MESSAGE);
            salir();
        }
    }

    /**
     * Buscar el archivo por defecto "disVecRou.txt", o le pregunta al usuario sobre el nombre del archivo.
     * El archivo de datos es buscado recursivamente, a partir del directorio de ejecuacion
     *
     * @return
     */
    private File buscarArchivo() {

        // buscamos el archivo de datos por "default"
        file = buscarArchivoRecursivamente("disVecRou.txt", new File("."));
        if (file == null) {
            JOptionPane.showMessageDialog(null, "Advertencia. No se encontró el archivo 'disVecRou.txt'\nen el directorio actual ni en sus subdirectorios.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);

            String resp = JOptionPane.showInputDialog("Indique el nombre de archivo de datos");
            if (resp != null) {
                String nombreArchivo = resp.trim();
                file = buscarArchivoRecursivamente(nombreArchivo, new File("."));
            }
        }

        return file;
    }

    /**
     * Retorna el idNode original del archivo, dado el nickName asignado por el programa
     *
     * @param inNickName
     * @return el IdNode original del nodo leido del archivo
     */
    public Integer getIdNode(Integer inNickName) {
        return nickNames.getIdNode(inNickName);
    }

    public boolean leerArchivoCrearLinks() {

        // En caso de no existir el archivo de datos, retornar
        if (file == null) {
            return false;
        }

        // Eliminar toda la informacion anterior        
        // Inicializar
        listLink = new LinkRepo();
        nickNames = new BidirectionalMap<Integer, Integer>();

        int contador = 0;
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                System.out.println("Leer el archivo: " + file.getName());

                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    String[] arrNumInt = line.split(" ");
                    if (arrNumInt.length == 3) {

                        // El siguiente bloque se hace para asignar un "nickName" al identificador del nodo en el archivo
                        // y la razon de esto es que la ejecucion del Algoritmo, requiere de que los idNode comiencen en 0,
                        // y que su numeracion sea consecutiva hasta n < numNodes, por ejemplo si existen los nodos
                        // 0, 1, 3, 4 si funciona, si existe 1, 2, 3, 4 no funciona, si existe, 0, 5, 33, 45, no funciona,
                        // por eso internamenten los idNode del archivo se mapean con un NickName, y siempre internamente
                        // se trabajaran los nodos como 0, 1, 2, ... n < NumNodes, al presentarlos en la GUI, se
                        // realiza la operacion inversa
                        final int idSourceNode = Integer.parseInt(arrNumInt[0]);

                        final int sourceNode;
                        if (nickNames.containsIdNode(idSourceNode)) {
                            sourceNode = nickNames.getNickName(idSourceNode);
                        } else {
                            sourceNode = contador;
                            nickNames.put(idSourceNode, sourceNode);
                            contador++;
                        }

                        int idDestNode = Integer.parseInt(arrNumInt[1]);
                        final int destNode;
                        if (nickNames.containsIdNode(idDestNode)) {
                            destNode = nickNames.getNickName(idDestNode);
                        } else {
                            destNode = contador;
                            nickNames.put(idDestNode, destNode);
                            contador++;
                        }

                        System.out.println("idnode: " + idSourceNode + ", nickSource= " + sourceNode + ", idnodeDest: " + idDestNode + ". NickDest= " + destNode);

                        int weight = Integer.parseInt(arrNumInt[2]);

                        // Crea y agrega el link correspondiente, de forma bidireccional
                        addLink(sourceNode, destNode, weight);
                    }
                }
            }

            return true;
        } catch (IOException inex) {
            logger.error(inex);
            JOptionPane.showMessageDialog(this, "Error leyendo el archivo. Final del programa", "Error", HEIGHT);
            System.exit(1);
        }

        // Hubo alguna condicion de archivo no encontrado, o error
        return false;
    }

    public LinkRepo getLinkRepo() {
        if (leerArchivoCrearLinks()) {
            return listLink;
        }

        return null;
    }

    /**
     * Metodo para buscar un archivo en el la carpeta actual y subcarpetas, retorna una instancia de
     * la clase File si lo encuentra, o null en caso de no encontrarlo
     *
     * @param inNombreArchivo
     * @param inCarpetaActual
     * @return
     */
    private static File buscarArchivoRecursivamente(String inNombreArchivo, File inCarpetaActual) {

        // Validar los parametros
        if ((inNombreArchivo == null) || inNombreArchivo.isBlank() || (inCarpetaActual == null) || !inCarpetaActual.isDirectory()) {
            return null;
        }

        // Obtener los archivos de la carpeta actual
        File[] listFiles = inCarpetaActual.listFiles();
        if (listFiles != null) {

            // Recorremos todos los archivo y carpetas
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    File refFile = buscarArchivoRecursivamente(inNombreArchivo, file);

                    // Retornar si el archivo fue encontrado 
                    if (refFile != null) {
                        return refFile;
                    }
                } else if (file.getName().equals(inNombreArchivo)) {
                    return file;
                }
            }
        }

        // El archivo no fue encontrado en la carpetal actual o subcarpetas
        return null;
    }

    private void addLink(int inSource, int inDestination, int inWeight) {
        listLink.addRel(inSource, inDestination, inWeight);
    }

    private void salir() {
        System.out.println("\nFin del programa.");
        exit(0);
    }

    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this, "Mi Aplicación v1.0\nAutor: Tú Nombre", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });

    }

    private void fileChooser() {
        JFileChooser fileChooser = new JFileChooser();

        // Configura el directorio inicial para que sea el directorio de trabajo del usuario
        String userDir = System.getProperty("user.dir");
        fileChooser.setCurrentDirectory(new File(userDir));

        // Crear un filtro para mostrar solo archivos .txt
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        // Usa las preferencias para recordar la última ubicación
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String lastPath = prefs.get("lastPath", userDir);
        fileChooser.setCurrentDirectory(new File(lastPath));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File auxFile;
            try {
                auxFile = fileChooser.getSelectedFile();

                if (auxFile != null) {
                    file = auxFile;
                    setTitle(file.getName());
                    // Guarda la última ubicación
                    prefs.put("lastPath", file.getParent());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception inex) {
                JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
