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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban Quesada
 */
public class Reservacion extends javax.swing.JFrame {

    Menu menu;

    public Reservacion(Menu menu) {
        initComponents();
        this.menu = menu;
        this.llenarCombobox();

    }

    public Reservacion() {
        initComponents();
        this.menu = new Menu("", "");
    }

    public void llenarCombobox() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            //Apertura del fichero y creacion de BufferedReader

            archivo = new File("archivos/clientes.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

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
                    String codigoCliente = defaultTokenizer.nextToken();
                    String nombreCliente = defaultTokenizer.nextToken();
                    String telefonoCliente = defaultTokenizer.nextToken();
                    String direccionCliente = defaultTokenizer.nextToken();

                    clienteComboBox.addItem(nombreCliente);
                }

            }

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
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clienteComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cantidadPersonas = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        buscarHabitacion = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        reservar = new javax.swing.JButton();
        regresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        clienteComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("Seleccionar Cliente");

        jLabel2.setText("Cantidad de Personas");

        buscarHabitacion.setText("Buscar Habitacion");
        buscarHabitacion.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buscarHabitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarHabitacionActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Tipo", "Costo", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tabla);

        reservar.setText("Reservar");
        reservar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        reservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservarActionPerformed(evt);
            }
        });

        regresar.setText("Regresar");
        regresar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clienteComboBox, 0, 67, Short.MAX_VALUE)
                            .addComponent(cantidadPersonas))
                        .addGap(18, 18, 18)
                        .addComponent(buscarHabitacion))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(reservar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(regresar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clienteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cantidadPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarHabitacion))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reservar)
                    .addComponent(regresar))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regresarActionPerformed
        menu.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_regresarActionPerformed

    private void clienteComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteComboBoxActionPerformed

    private void buscarHabitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarHabitacionActionPerformed
        buscarHabitacion();
    }//GEN-LAST:event_buscarHabitacionActionPerformed

    private void buscarHabitacion() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int cantidadPorHabitacion = 0;

        int contador = 0;

        try {

            for (int i = 0; i < 7; i++) {
                tabla.setValueAt("", i, 0);
                tabla.setValueAt("", i, 1);
                tabla.setValueAt("", i, 2);
                tabla.setValueAt("", i, 3);
            }

            archivo = new File("archivos/habitaciones.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

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

                    if (tipo.equals("simple")) {
                        cantidadPorHabitacion = 2;
                    } else if (tipo.equals("doble")) {
                        cantidadPorHabitacion = 2;
                    } else {
                        cantidadPorHabitacion = 4;
                    }
                    if (Integer.parseInt(cantidadPersonas.getText()) <= cantidadPorHabitacion) {

                        if (estado.equalsIgnoreCase("libre")) {
                            tabla.setValueAt(codigo, contador, 0);
                            tabla.setValueAt(tipo, contador, 1);
                            tabla.setValueAt(costo, contador, 2);
                            tabla.setValueAt(estado, contador, 3);
                            contador++;
                        }

                    }
                }

            }

            if (contador == 0) {
                JOptionPane.showMessageDialog(null, "No hay disponibilidad para la cantidad de personas requeridas");
            }

        } catch (Exception exx) {
            //exx.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar habitaciones disponibles");
        } finally {
            if (null != fr) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    // Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    private void reservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservarActionPerformed
        
        if (tabla.getSelectedRow()== -1) {
            JOptionPane.showMessageDialog(null, "No se pudo Reservar, no hay Habitaciones Disponibles");
        }else{
        escribirReservacion();
        buscarHabitacion();
        }
    }//GEN-LAST:event_reservarActionPerformed
    private void escribirReservacion() {
        FileWriter fichero = null;
        PrintWriter pw = null; //Salvar cadena dentro de un txt
        //Instancio porque FileWriter es una clase que captura el txt
        //Las clases estaticas debo instanciarlas, claro ejemplo el FL y PW
        try {

            reservarHabitacion();

            fichero = new FileWriter("archivos/reservaciones.txt", true); // si no pongo el true, al tocar el boton de ingresar, sobreescribira el archivo txt
            pw = new PrintWriter(fichero);
            int selectedRow = tabla.getSelectedRow();
            String userName = menu.userName;
            String nombre = menu.nombreUsuario;
            String nombreCliente = clienteComboBox.getSelectedItem().toString();
            String codigoCliente = buscarCodigoCliente(nombreCliente);
            Date myDate = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String fechaReservacion = formato.format(myDate);
            String codigoHabitacion = tabla.getValueAt(selectedRow, 0).toString();
            String cantidadPersonas = this.cantidadPersonas.getText();
            pw.println(userName + "*" + nombre + "*" + nombreCliente + "*" + codigoCliente + "*" + fechaReservacion + "*" + codigoHabitacion + "*" + cantidadPersonas);

            JOptionPane.showMessageDialog(null, "Su reservación se agregó con éxito");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                //uso el finally para asegurarme de que cierre el fichero
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }

    private void reservarHabitacion() {
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

                    //selecciona el numero de fila que usuario escogio
                    //compara codigo de habitacion del archivo con el que el usuario eligio
                    //sino son iguales el codigo de habitacion que el usuario eligio y 
                    //el que esta en el archivo entonces escribe en el archivo temporal 
                    //los datos de la habitacion del archivo
                    //si es igual ,entonces escribe en el archivo temporal la habitacion,pero 
                    //en el estado de ocupada
                    int numeroFila = tabla.getSelectedRow();
                    String codigoHabitacion = tabla.getValueAt(numeroFila, 0).toString();
                    if (!codigo.equalsIgnoreCase(codigoHabitacion)) {
                        pw.println(codigo + "*" + tipo + "*" + costo + "*" + estado);
                    } else {
                        pw.println(codigo + "*" + tipo + "*" + costo + "*" + "ocupada");
                    }
                }

            }

        } catch (Exception exx) {
            //exx.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al elegir habitación");
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

    public String buscarCodigoCliente(String nombre) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String codigoEncontrado = "";

        try {
            //Apertura del fichero y creacion de BufferedReader

            archivo = new File("archivos/clientes.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

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
                    String codigoCliente = defaultTokenizer.nextToken();
                    String nombreCliente = defaultTokenizer.nextToken();
                    String telefonoCliente = defaultTokenizer.nextToken();
                    String direccionCliente = defaultTokenizer.nextToken();

                    if (nombreCliente.equalsIgnoreCase(nombre)) {
                        codigoEncontrado = codigoCliente;
                    }

                }

            }

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
        }
        return codigoEncontrado;
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
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reservacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscarHabitacion;
    private javax.swing.JTextField cantidadPersonas;
    private javax.swing.JComboBox<String> clienteComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton regresar;
    private javax.swing.JButton reservar;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}