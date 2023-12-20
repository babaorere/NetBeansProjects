/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohotel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban Quesada
 */
public class ModificarHabitacion extends javax.swing.JFrame {

    Habitaciones menu;

    /**
     * Creates new form consultaImpresion
     */
    public ModificarHabitacion(Habitaciones menu, Habitacion habitacion) {
        initComponents();
        this.menu = menu;

        this.codigoHabitacion.setText(habitacion.codigoHabitacion);

        for (int i = 0; i < this.tipoComboBox.getItemCount(); i++) {
            String valorActualComboBox = this.tipoComboBox.getItemAt(i).toString();
            if (habitacion.tipoHabitacion.equalsIgnoreCase(valorActualComboBox)) {
                this.tipoComboBox.setSelectedIndex(i);
            }
        }

        for (int i = 0; i < this.estadoComboBox.getItemCount(); i++) {
            String valorActualComboBox = this.estadoComboBox.getItemAt(i).toString();
            if (habitacion.estado.equalsIgnoreCase(valorActualComboBox)) {
                this.estadoComboBox.setSelectedIndex(i);
            }
        }

        this.costo.setText(habitacion.costo);

    }

    public ModificarHabitacion() {
        initComponents();
        this.menu = new Habitaciones();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        regresar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        codigoHabitacion = new javax.swing.JTextField();
        costo = new javax.swing.JTextField();
        guardar = new javax.swing.JButton();
        tipoComboBox = new javax.swing.JComboBox<>();
        estadoComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Modificar Habitacion");

        regresar.setText("Regresar");
        regresar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarActionPerformed(evt);
            }
        });

        jLabel2.setText("Codigo de Habitacion");

        jLabel3.setText("Tipo de Habitacion");

        jLabel4.setText("Costo");

        jLabel5.setText("Estado");

        codigoHabitacion.setEditable(false);
        codigoHabitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoHabitacionActionPerformed(evt);
            }
        });

        guardar.setText("Guardar");
        guardar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        tipoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Simple", "Doble", "Suite" }));

        estadoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Libre", "Ocupada" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(codigoHabitacion)
                            .addComponent(costo)
                            .addComponent(tipoComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(estadoComboBox, 0, 85, Short.MAX_VALUE))))
                .addContainerGap(126, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(regresar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(codigoHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tipoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(estadoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regresar)
                    .addComponent(guardar))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regresarActionPerformed
        menu.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_regresarActionPerformed

    private void codigoHabitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoHabitacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoHabitacionActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        modificarHabitacion();
        menu.cargarHabitaciones();
        menu.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_guardarActionPerformed

    private void modificarHabitacion() {
        File archivoLeer = null;
        FileReader fr = null;
        BufferedReader br = null;

        File archivoEscribir = null;
        FileWriter fichero = null;
        PrintWriter pw = null;

        try {
            //Apertura del fichero y creacion de BufferedReader

            archivoLeer = new File("archivos/habitaciones.txt");
            fr = new FileReader(archivoLeer);
            br = new BufferedReader(fr);

            archivoEscribir = new File("archivos/habitacionesTemporal.txt");
            fichero = new FileWriter(archivoEscribir, true);
            pw = new PrintWriter(fichero);

            //Lectura del fichero
            String linea = "";
            // Se hace lectura por linea
            while ((linea = br.readLine()) != null) {
                //El tokenizer lo que hace es agarrar la linea y partirla en pedazos segun el caracter..En este casi la coma ( ,).
                //Guarda esos pedazos internamente en un arreglo y se va recorriendo al usar nextToken.
                StringTokenizer defaultTokenizer = new StringTokenizer(linea, "*");
                // Se entiende que son 3 token lo que se graba en el archivo por linea
                // Si no son 3 token se ignora esa linea porque posiblemente estaria corrupta. 
                if (defaultTokenizer.countTokens() == 4) {
                    String codigo = defaultTokenizer.nextToken();
                    String tipo = defaultTokenizer.nextToken();
                    String costo = defaultTokenizer.nextToken();
                    String estado = defaultTokenizer.nextToken();
                    if (!this.codigoHabitacion.getText().equalsIgnoreCase(codigo)) {
                        pw.println(codigo + "*" + tipo + "*" + costo + "*" + estado);
                    } else {
                        pw.println(this.codigoHabitacion.getText() + "*" + this.tipoComboBox.getSelectedItem().toString() + "*" + this.costo.getText() + "*" + this.estadoComboBox.getSelectedItem().toString());
                    }
                }

            }

            JOptionPane.showMessageDialog(null, "Habitacion modificada con exito");

        } catch (Exception exx) {
            //exx.printStackTrace();
            JOptionPane.showMessageDialog(null, "No existente");
        } finally {

            if (null != fr) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    // Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (null != fichero) {
                try {
                    fichero.close();
                } catch (IOException ex) {
                    // Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            archivoLeer.delete();
            archivoEscribir.renameTo(archivoLeer);

        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codigoHabitacion;
    private javax.swing.JTextField costo;
    private javax.swing.JComboBox<String> estadoComboBox;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton regresar;
    private javax.swing.JComboBox<String> tipoComboBox;
    // End of variables declaration//GEN-END:variables
}