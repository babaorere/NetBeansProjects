package lexer;

import compilerTools.CodeBlock;
import compilerTools.Directory;
import compilerTools.ErrorLSSL;
import compilerTools.Functions;
import compilerTools.Grammar;
import compilerTools.Production;
import compilerTools.TextColor;
import compilerTools.Token;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yisus
 */
public class Compilador extends javax.swing.JFrame {

    private final String INIT_TITTLE = "JavaScript";

    private String title;
    private Directory directorio;
    private ArrayList<Token> tokens;
    private ArrayList<ErrorLSSL> errors;
    private ArrayList<TextColor> textsColor;
    private Timer timerKeyReleased;
    private ArrayList<Production> identProd;
    private HashMap<String, String> identificadores;
    private boolean codeHasBeenCompiled = false;

    // para recordar el ultimo directorio utilizado
    private Preferences prefs = Preferences.userNodeForPackage(this.getClass());

    // archivo seleccionado
    private File selectedFile;

    /**
     * Creates new form Compilador
     */
    public Compilador() {
        initComponents();
        init();
    }

    private void init() {
        selectedFile = null;
        setLocationRelativeTo(null);
        setTitle(INIT_TITTLE);
        directorio = new Directory(this, txtPanel, title, ".js");
        addWindowListener(new WindowAdapter() {// Cuando presiona la "X" de la esquina superior derecha
            @Override
            public void windowClosing(WindowEvent e) {
                directorio.Exit();
                System.exit(0);
            }
        });
        Functions.setLineNumberOnJTextComponent(txtPanel);
        timerKeyReleased = new Timer((int) (1000 * 0.3), (ActionEvent e) -> {
            timerKeyReleased.stop();
        });
        Functions.insertAsteriskInName(this, txtPanel, () -> {
            timerKeyReleased.restart();
        });
        tokens = new ArrayList<>();
        errors = new ArrayList<>();
        textsColor = new ArrayList<>();
        identProd = new ArrayList<>();
        identificadores = new HashMap<>();
        Functions.setAutocompleterJTextComponent(new String[]{}, txtPanel, () -> {
            timerKeyReleased.restart();
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new javax.swing.JPanel();
        buttonsFilePanel = new javax.swing.JPanel();
        btnAbrir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnGuardarC = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPanel = new javax.swing.JTextPane();
        panelButtonCompilerExecute = new javax.swing.JPanel();
        btnCompilar = new javax.swing.JButton();
        btnEjecutar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaOutputConsole = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTokens = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        btnAbrir.setText("Abrir");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnGuardarC.setText("Guardar como");
        btnGuardarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsFilePanelLayout = new javax.swing.GroupLayout(buttonsFilePanel);
        buttonsFilePanel.setLayout(buttonsFilePanelLayout);
        buttonsFilePanelLayout.setHorizontalGroup(
            buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAbrir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonsFilePanelLayout.setVerticalGroup(
            buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbrir)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnGuardarC))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        txtPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPanelKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtPanel);

        btnCompilar.setText("Compilar");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.setEnabled(false);
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonCompilerExecuteLayout = new javax.swing.GroupLayout(panelButtonCompilerExecute);
        panelButtonCompilerExecute.setLayout(panelButtonCompilerExecuteLayout);
        panelButtonCompilerExecuteLayout.setHorizontalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCompilar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEjecutar)
                .addContainerGap())
        );
        panelButtonCompilerExecuteLayout.setVerticalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCompilar)
                    .addComponent(btnEjecutar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtaOutputConsole.setEditable(false);
        jtaOutputConsole.setColumns(20);
        jtaOutputConsole.setRows(5);
        jScrollPane2.setViewportView(jtaOutputConsole);

        tblTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Componente léxico", "Lexema", "[Línea, Columna]"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTokens.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblTokens);

        javax.swing.GroupLayout rootPanelLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rootPanelLayout.createSequentialGroup()
                        .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
        rootPanelLayout.setVerticalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        getContentPane().add(rootPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        selectedFile = null;
        setTitle(INIT_TITTLE);

        clearFields();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        Abrir();
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnGuardarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCActionPerformed
        if (GuardarComo()) {
            clearFields();
        }
    }//GEN-LAST:event_btnGuardarCActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        if (getTitle().contains("*")) {
            if (Guardar()) {
                AnalisisLexer();
            }
        } else {
            AnalisisLexer();
        }
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        btnCompilar.doClick();
        if (codeHasBeenCompiled) {
            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede ejecutar el código ya que se encontró uno o más errores",
                        "Error en la compilación", JOptionPane.ERROR_MESSAGE);
            } else {
                CodeBlock codeBlock = Functions.splitCodeInCodeBlocks(tokens, "{", "}", ";");
                System.out.println(codeBlock);
                ArrayList<String> blocksOfCode = codeBlock.getBlocksOfCodeInOrderOfExec();
                System.out.println(blocksOfCode);

            }
        }
    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void txtPanelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPanelKeyTyped
        if (selectedFile != null) {
            setTitle(INIT_TITTLE + ": *" + selectedFile.getName() + "*");
        } else {
            if ((selectedFile == null) || txtPanel.getText().isEmpty()) {
                setTitle(INIT_TITTLE + ": ");
            } else {
                setTitle(INIT_TITTLE + ": *" + selectedFile.getName() + "*");
            }

        }
    }//GEN-LAST:event_txtPanelKeyTyped

    private void AnalisisLexer() {
        // Borrar los datos
        errors.clear();
        jtaOutputConsole.setText("");

        lexicalAnalysis();
        fillTableTokens();
        syntacticAnalysis();
//        semanticAnalysis();
        printConsole();
//        codeHasBeenCompiled = true;
    }

    private void lexicalAnalysis() {
        // Extraer tokens
        Lexer lexer;
        try {
            tokens.clear();
            File codigo = new File("code.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = txtPanel.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexer = new Lexer(entrada);
            while (true) {
                Token token = lexer.yylex();
                if (token == null) {
                    break;
                }
                tokens.add(token);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "El archivo no pudo ser encontrado... \n" + ex.toString(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al escribir en el archivo... \n" + ex.toString(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void syntacticAnalysis() {
        try {
            // Guarda la referencia a la salida estándar actual
            PrintStream originalOut = System.out;

            // Crea un flujo de salida para el archivo "output.txt"
            FileOutputStream fileOut = new FileOutputStream("sintactico.txt");
            PrintStream customOut = new PrintStream(fileOut);

            // Redirige la salida estándar a la instancia personalizada
            System.setOut(customOut);

            Grammar gramatica = new Grammar(tokens, errors);

            /*Eliminacion de errores*/
            gramatica.delete(new String[]{"ERROR", "ERROR_1", "ERROR_2"}, 1);

            gramatica.group("SENTENCIA_IF", "IF PAREN_A IDENTIFICADOR OPLOG NUMERO PAREN_C LLAVE_A LLAVE_C", true);
            gramatica.group("SENTENCIA_IF", "IF PAREN_A IDENTIFICADOR OPLOG NUMERO PAREN_C LLAVE_A SENTENCIA LLAVE_C", true);

            gramatica.group("VAR_ASIGNACION", "IDENTIFICADOR ASIGNACION NUMERO", true);
            gramatica.group("VAR_ASIGNACION", "IDENTIFICADOR ASIGNACION IDENTIFICADOR", true);
            gramatica.group("EXPRESION_ARIT", "NUMERO", true);
            gramatica.group("EXPRESION_ARIT", "EXPRESION_ARIT SUMA EXPRESION_ARIT", true);
            gramatica.group("EXPRESION_ARIT", "EXPRESION_ARIT MENOS EXPRESION_ARIT", true);
            gramatica.group("EXPRESION_ARIT", "EXPRESION_ARIT MULTI EXPRESION_ARIT", true);
            gramatica.group("EXPRESION_ARIT", "EXPRESION_ARIT DIV EXPRESION_ARIT", true);
            gramatica.group("EXPRESION_ARIT", "IDENTIFICADOR INCREMENTO", true);
            gramatica.group("EXPRESION_ARIT", "IDENTIFICADOR DECREMENTO", true);
            gramatica.group("EXPRESION_ARIT", "PARE_A EXPRESION_ARIT PAREN_C", true);
            gramatica.group("VAR_ASIGNACION", "IDENTIFICADOR ASIGNACION EXPRESION_ARIT", true);

            gramatica.group("EXPRESION_LOG", "TRUE", true);
            gramatica.group("EXPRESION_LOG", "FALSE", true);
            gramatica.group("EXPRESION_LOG", "NUMERO OPLOG NUMERO", true);
            gramatica.group("EXPRESION_LOG", "NUMERO OPLOG IDENTIFICADOR", true);
//            gramatica.group("EXPRESION_LOG", "IDENTIFICADOR OPLOG NUMERO", true);
//            gramatica.group("EXPRESION_LOG", "IDENTIFICADOR OPLOG EXPRESION_ARIT", true);
            gramatica.group("EXPRESION_LOG", "IDENTIFICADOR OPLOG IDENTIFICADOR", true);
            gramatica.group("EXPRESION_LOG", "IDENTIFICADOR OPLOG TRUE", true);
            gramatica.group("EXPRESION_LOG", "TRUE OPLOG IDENTIFICADOR", true);
            gramatica.group("EXPRESION_LOG", "IDENTIFICADOR OPLOG FALSE", true);
            gramatica.group("EXPRESION_LOG", "FALSE OPLOG IDENTIFICADOR", true);

            gramatica.group("DECLARACION", "LET IDENTIFICADOR PUNTO_COMA", true);
            gramatica.group("SENTENCIA", "LET IDENTIFICADOR ", true, 2, "Error sintáctico {}: falta \";\" [#, %]");

            gramatica.group("DECLARACION", "VAR IDENTIFICADOR PUNTO_COMA", true);
            gramatica.group("SENTENCIA", "VAR IDENTIFICADOR", true, 2, "Error sintáctico {}: falta \";\" [#, %]");

            gramatica.group("DECLARACION", "VAR VAR_ASIGNACION PUNTO_COMA", true);
            gramatica.group("SENTENCIA", "VAR VAR_ASIGNACION", true, 2, "Error sintáctico {}: falta \";\" [#, %]");

            gramatica.group("DECLARACION", "LET VAR_ASIGNACION PUNTO_COMA", true);
            gramatica.group("SENTENCIA", "LET VAR_ASIGNACION", true, 2, "Error sintáctico {}: falta \";\" [#, %]");

//        gramatica.group("SENTENCIA_IF", "IF PAREN_A IDENTIFICADOR OPLOG EXPRESION_ARI PAREN_C LLAVE_A SENTENCIA LLAVE_C", true);
//
//        gramatica.group("SENTENCIA_IF", "IF PAREN_A EXPRESION_LOG PAREN_C LLAVE_A SENTENCIA_LIST LLAVE_C ELSE LLAVE_A SENTENCIA_LIST LLAVE_C", true);
//        gramatica.group("SENTENCIA_IF", "IF PAREN_A EXPRESION_LOG PAREN_C LLAVE_A SENTENCIA_LIST LLAVE_C", true);
            gramatica.group("SENTENCIA_WHILE", "WHILE PAREN_A EXPRESION_LOG PAREN_C LLAVE_A SENTENCIA_LIST LLAVE_C", true);
            gramatica.group("SENTENCIA_FOR", "FOR PAREN_A SENTENCIA PUNTO_COMA EXPRESION_LOG PUNTO_COMA EXPRESION PAREN_C LLAVE_A SENTENCIA_LIST LLAVE_C", true);

            gramatica.group("SENTENCIA", "VAR_ASIGNACION PUNTO_COMA", true);
            gramatica.group("SENTENCIA", "VAR_ASIGNACION", true, 2, "Error sintáctico {}: falta \";\" [#, %]");

            gramatica.group("SENTENCIA", "IDENTIFICADOR PUNTO IDENTIFICADOR PAREN_A CADENA PAREN_C PUNTO_COMA", true);
            gramatica.group("SENTENCIA", "IDENTIFICADOR PUNTO IDENTIFICADOR PAREN_A CADENA PAREN_C", true, 2, "Error sintáctico {}: falta \";\" [#, %]");

            gramatica.group("SENTENCIA", "DECLARACION", true);
            gramatica.group("SENTENCIA", "SENTENCIA_IF", true);
            gramatica.group("SENTENCIA", "SENTENCIA_WHILE", true);
            gramatica.group("SENTENCIA", "SENTENCIA_FOR", true);

            gramatica.group("SENTENCIA_LIST", "SENTENCIA SENTENCIA", true);
            gramatica.group("SENTENCIA_LIST", "SENTENCIA_LIST SENTENCIA+", true);

            /* Agrupacion de valores  */
            // var x = 5;
            // let x = 5;
//        gramatica.group("SENTENCIA", "VAR IDENTIFICADOR ASIGNACION NUMERO PUNTO_COMA", true);
//        gramatica.group("SENTENCIA", "LET IDENTIFICADOR ASIGNACION NUMERO PUNTO_COMA", true);
//
////        gramatica.group("VARIABLE", "VAR IDENTIFICADOR ASIGNACION NUMERO PUNTO_COMA", true);
////        gramatica.group("VARIABLE", "VAR IDENTIFICADOR ASIGNACION CADENA PUNTO_COMA", true);
////        gramatica.group("VARIABLE", "VAR IDENTIFICADOR ASIGNACION VAL_LOG PUNTO_COMA", true);
//        gramatica.group("EXP_LOGICA", "VAL_LOG", true);
//        gramatica.group("EXP_LOGICA", "IDENTIFICADOR OPLOG NUMERO", true);
//        gramatica.group("IF", "PAREN_ABRIR EXP_LOGICA PAREN_CERRAR", true);
//        gramatica.group("WHILE", "WHILE PAREN_ABRIR EXP_LOGICA PAREN_CERRAR", true);
//
//        /*Agrupacion de identificadores y definicion de parametros*/
//        gramatica.group("VALOR", "IDENTIFICADOR", true);
//        gramatica.group("PARAMETROS", "VALOR (COMA VALOR)+");
//
//        // console.log("El valor de x es positivo");
//        gramatica.group("SENTENCIA", "IDENTIFICADOR PAREN_ABRIR CADENA PAREN_CERRAR PUNTO_COMA", true);
//
//        // console.log(10);
//        gramatica.group("SENTENCIA", "IDENTIFICADOR PAREN_ABRIR VALOR PAREN_CERRAR PUNTO_COMA", true);
//
//        // contador++;
//        gramatica.group("SENTENCIA", "IDENTIFICADOR INCREMENTO PUNTO_COMA", true);
//
//        // contador--;
//        gramatica.group("SENTENCIA", "IDENTIFICADOR DECREMENTO PUNTO_COMA", true);
//
//        //
//        gramatica.group("VALOR_LOGICO", "IDENTIFICADOR OPLOG IDENTIFICADOR", true);
//
//        // for (var i = 0; i < numeros.length; i++)
//        gramatica.group("SENTENCIA", "FOR PAREN_ABRIR VAR IDENTIFICADOR IGUAL VALOR PUNTO_COMA VALOR_LOGICO PUNTO_COMA IDENTIFICADOR INCREMENTO PAREN_CIERRA", true);
//        gramatica.group("SENTENCIA", "FOR PAREN_ABRIR VAR IDENTIFICADOR IGUAL VALOR PUNTO_COMA VALOR_LOGICO PUNTO_COMA IDENTIFICADOR DECREMENTO PAREN_CIERRA", true);
//
//        // for (;;)
//        gramatica.group("SENTENCIA", "FOR PAREN_ABRIR PUNTO_COMA PUNTO_COMA PAREN_CIERRA", true);
            gramatica.show();

            // Restaura la salida estándar original
            System.setOut(originalOut);

            // No olvides cerrar los flujos cuando ya no los necesites
            customOut.close();
            fileOut.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.toString(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void semanticAnalysis() {
    }

    private void fillTableTokens() {
        DefaultTableModel model = (DefaultTableModel) tblTokens.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
            Functions.addRowDataInTable(tblTokens, data);
        });
    }

    private void printConsole() {
        int sizeErrors = errors.size();
        if (sizeErrors > 0) {
            Functions.sortErrorsByLineAndColumn(errors);
            String strErrors = "\n";
            for (ErrorLSSL error : errors) {
                String strError = String.valueOf(error);
                strErrors += strError + "\n";
            }
            jtaOutputConsole.setText("Compilación terminada...\n" + strErrors + "\nLa compilación terminó con errores...");
        } else {
            jtaOutputConsole.setText("Compilación terminada...");
        }
        jtaOutputConsole.setCaretPosition(0);

    }

    private void clearFields() {
        Functions.clearDataInTable(tblTokens);
        txtPanel.setText("");
        jtaOutputConsole.setText("");
        tokens.clear();
        errors.clear();
        identProd.clear();
        identificadores.clear();
        codeHasBeenCompiled = false;

        DefaultTableModel model = (DefaultTableModel) tblTokens.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Compilador().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarC;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel buttonsFilePanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jtaOutputConsole;
    private javax.swing.JPanel panelButtonCompilerExecute;
    private javax.swing.JPanel rootPanel;
    private javax.swing.JTable tblTokens;
    private javax.swing.JTextPane txtPanel;
    // End of variables declaration//GEN-END:variables

    private boolean Abrir() {

        JFileChooser fileChooser = new JFileChooser();

        // Ubicar en el ultimo directorio visitado
        String lastDir = prefs.get("lastDir", null);
        if (lastDir != null) {
            fileChooser.setCurrentDirectory(new File(lastDir));
        }
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            clearFields();
            selectedFile = fileChooser.getSelectedFile();
            setTitle(INIT_TITTLE + ": " + selectedFile.getName());
            prefs.put("lastDir", selectedFile.getParent());
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String txtMsg = "";
                String line;
                int cont = 0;
                while ((line = reader.readLine()) != null) {
                    txtMsg += line + "\n";
                    cont++; // cuanta las lineas del archivo
                }
                txtPanel.setText(txtMsg);

                reader.close();
                return cont > 0; // retorna true si el contador es mayor a 0
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERROR: " + ex.toString(), "Error", ERROR_MESSAGE);
            }
        }

        JOptionPane.showMessageDialog(this, "Debe seleccionar un archivo", "Mensaje", INFORMATION_MESSAGE);
        return false;
    }

    private boolean Guardar() {
        if (selectedFile != null) {
            prefs.put("lastDir", selectedFile.getParent());
            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(txtPanel.getText());
                writer.close();
                JOptionPane.showMessageDialog(this, "Archivo guardado", "Mensaje", INFORMATION_MESSAGE);
                setTitle(INIT_TITTLE + ": " + selectedFile.getName());
                return true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERROR: " + ex.toString(), "Error", ERROR_MESSAGE);
            }
        } else {
            return GuardarComo();
        }

        JOptionPane.showMessageDialog(this, "Archivo NO guardado", "Mensaje", INFORMATION_MESSAGE);
        return false;
    }

    private boolean GuardarComo() {
        JFileChooser fileChooser = new JFileChooser();

        // Ubicar en el ultimo directorio visitado
        String lastDir = prefs.get("lastDir", null);
        if (lastDir != null) {
            fileChooser.setCurrentDirectory(new File(lastDir));
        }
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            setTitle(INIT_TITTLE + ": " + selectedFile.getName());
            prefs.put("lastDir", selectedFile.getParent());
            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(txtPanel.getText());
                writer.close();
                JOptionPane.showMessageDialog(this, "Archivo guardado", "Mensaje", INFORMATION_MESSAGE);
                return true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERROR: " + ex.toString(), "Error", ERROR_MESSAGE);
            }
        }

        JOptionPane.showMessageDialog(this, "Archivo NO guardado", "Mensaje", INFORMATION_MESSAGE);

        return false;
    }
}
