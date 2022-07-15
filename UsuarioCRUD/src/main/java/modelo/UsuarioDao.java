package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 */
public class UsuarioDao {

    public static boolean create(UsuarioModelo inUsuario) {

        final String nameFileOut = UsuarioModelo.getFileName();

        // Manejador para acceder al archivo de entrada
        File fileOut = new File(nameFileOut);
        FileWriter fileWriter = null;
        PrintWriter pw = null;

        try {
            // Si el archivo no existe, se crea
            if (!fileOut.exists()) {
                fileOut.createNewFile();
            }

            fileWriter = new FileWriter(fileOut, true);
            if (fileWriter == null) {
                System.out.println("\n\nError al tratar de abrir el archivo: " + nameFileOut);
                System.exit(1);
                return false;
            }

            pw = new PrintWriter(fileWriter);
            if (pw == null) {
                System.out.println("\n\nError al tratar de abrir el PrintWriter del archivo: " + nameFileOut);
                System.exit(1);
                return false;
            }

            // creacion de la linea de texto, para agregar al archivo
            String linea = inUsuario.getNombre() + "," + inUsuario.getApellido() + "," + inUsuario.getEdad() + "," + inUsuario.getTipodeSangre() + "\n";

            // Escribir la linea al archivo, si la longitud es valida
            if (linea.length() > 4) {
                pw.print(linea);
            }

        } catch (Exception ex) {
            System.out.println("\n\nError archivo: " + nameFileOut + "\n" + ex.getMessage());
            System.exit(1);
            return false;
        } finally {
            try {

                // Cerrar archivo de salida
                if (pw != null) {
                    pw.close();
                }

            } catch (Exception ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return false;
            }

            try {

                // Cerrar archivo de salida
                if (fileWriter != null) {
                    fileWriter.close();
                }

            } catch (IOException ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return false;
            }

        }

        return true;
    }

    public static UsuarioModelo read(String inNombre, String inApellido) {

        final String nameFileIn = UsuarioModelo.getFileName();

        // Manejador para acceder al archivo de entrada
        UsuarioModelo usuario = null;
        FileReader fileIn = null;
        BufferedReader br = null;

        try {
            File archivo = new File(nameFileIn);

            fileIn = new FileReader(archivo);
            if (fileIn == null) {
                System.out.println("\n\nError al tratar de abrir el archivo: " + nameFileIn);
                System.exit(1);
                return null;
            }

            br = new BufferedReader(fileIn);
            if (br == null) {
                System.out.println("\n\nError al tratar de abrir el BufferedReader del archivo: " + nameFileIn);
                System.exit(1);
                return null;
            }

            // Lectura del fichero
            String lineaIn;

            while ((lineaIn = br.readLine()) != null) {

                // Separar la lineas en campos separados por comas
                // Split es un metodo de la clase String de java
                // y se utiliza una expresion regular, para indicar el caracter separador
                String[] vecStr = lineaIn.split("[\\,]");

                // Se encontro la posicion buscada
                if ((vecStr.length == 4)
                    && Objects.equals(inNombre, vecStr[0]) // comparar el nombre
                    && Objects.equals(inApellido, vecStr[1])) { // comparar el apellido
                    usuario = new UsuarioModelo(vecStr[0], vecStr[1], vecStr[2], vecStr[3]);
                    break; // salir del ciclo
                }

            }

        } catch (Exception ex) {
            System.out.println("\n\nError archivo: " + nameFileIn + "\n" + ex.getMessage());
            System.exit(1);
            return null;
        } finally {
            try {

                // Cerrar archivo de salida
                if (br != null) {
                    br.close();
                }

            } catch (Exception ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return null;
            }

            try {

                // Cerrar archivo de salida
                if (fileIn != null) {
                    fileIn.close();
                }

            } catch (Exception ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return null;
            }

        }

        return usuario;
    }

    public static boolean update(UsuarioModelo OldUsuario, UsuarioModelo newUsuario) {
        return updateAction(OldUsuario, newUsuario, false);
    }

    public static boolean delete(UsuarioModelo inUsuario) {
        return updateAction(inUsuario, inUsuario, true);
    }

    private static boolean updateAction(UsuarioModelo oldUsuario, UsuarioModelo newUsuario, boolean borrar) {

        if ((oldUsuario == null) || !borrar && (newUsuario == null)) {
            return false;
        }

        final String nameFileIn = UsuarioModelo.getFileName();
        final String nameFileOut = "temporal.txt";

        // Manejador para acceder al archivo de entrada
        FileReader fileIn = null;
        BufferedReader br = null;

        FileWriter fileOut = null;
        PrintWriter pw = null;

        try {
            File archivo = new File(nameFileIn);

            fileIn = new FileReader(archivo);
            if (fileIn == null) {
                System.out.println("\n\nError al tratar de abrir el archivo: " + nameFileIn);
                System.exit(1);
                return false;
            }

            br = new BufferedReader(fileIn);
            if (br == null) {
                System.out.println("\n\nError al tratar de abrir el BufferedReader del archivo: " + nameFileIn);
                System.exit(1);
                return false;
            }

            fileOut = new FileWriter(nameFileOut);
            if (fileOut == null) {
                System.out.println("\n\nError al tratar de abrir el archivo: " + nameFileOut);
                System.exit(1);
                return false;
            }

            pw = new PrintWriter(fileOut);
            if (pw == null) {
                System.out.println("\n\nError al tratar de abrir el PrintWriter del archivo: " + nameFileOut);
                System.exit(1);
                return false;
            }

            // Lectura del fichero
            String lineaIn;

            while ((lineaIn = br.readLine()) != null) {

                // Separar la lineas en campos separados por comas
                // Split es un metodo de la clase String de java
                // y se utiliza una expresion regular, para indicar el caracter separador
                String[] vecStr = lineaIn.split("[\\,]");

                // Se encontro la posicion buscada
                String linea;
                if ((vecStr.length == 4)
                    && Objects.equals(oldUsuario.getNombre(), vecStr[0]) // comparar el nombre
                    && Objects.equals(oldUsuario.getApellido(), vecStr[1])) { // comparar el apellido

                    if (!borrar) {

                        // creacion de la linea de texto, para agregar al archivo
                        linea = newUsuario.getNombre() + "," + newUsuario.getApellido() + "," + newUsuario.getEdad() + "," + newUsuario.getTipodeSangre();

                        // Escribir la linea al archivo, si la longitud es valida
                        if (linea.length() > 4) {
                            pw.print(linea + "\n");
                        } else {
                            pw.print(lineaIn + "\n");
                        }
                    }
                } else {
                    pw.print(lineaIn + "\n");
                }
            }

        } catch (Exception ex) {
            System.out.println("\n\nError archivo: " + nameFileOut + "\n" + ex.getMessage());
            System.exit(1);
            return false;
        } finally {
            try {

                // Cerrar archivo de salida
                if (pw != null) {
                    pw.close();
                }

            } catch (Exception ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return false;
            }

            try {

                // Cerrar archivo de salida
                if (fileOut != null) {
                    fileOut.close();
                }

            } catch (Exception ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return false;
            }

            try {

                // Cerrar archivo de salida
                if (br != null) {
                    br.close();
                }

            } catch (Exception ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return false;
            }

            try {

                // Cerrar archivo de salida
                if (fileIn != null) {
                    fileIn.close();
                }

            } catch (Exception ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return false;
            }

        }

        // Borrar el archivo viejo, y renombrarlo como el archivo de trabajo
        File oldfile = new File(UsuarioModelo.getFileName());
        File newfile = new File(nameFileOut);

        oldfile.delete();

        if (newfile.renameTo(new File(UsuarioModelo.getFileName()))) {
            System.out.println("archivo renombrado");
        } else {
            System.out.println("error");
        }

        return true;
    }

    public static ArrayList<UsuarioModelo> findAll() {

        ArrayList<UsuarioModelo> list = new ArrayList<>();

        final String nameFileIn = UsuarioModelo.getFileName();

        // Manejador para acceder al archivo de entrada
        UsuarioModelo usuario = null;
        FileReader fileIn = null;
        BufferedReader br = null;

        try {
            File archivo = new File(nameFileIn);

            fileIn = new FileReader(archivo);
            if (fileIn == null) {
                System.out.println("\n\nError al tratar de abrir el archivo: " + nameFileIn);
                System.exit(1);
                return null;
            }

            br = new BufferedReader(fileIn);
            if (br == null) {
                System.out.println("\n\nError al tratar de abrir el BufferedReader del archivo: " + nameFileIn);
                System.exit(1);
                return null;
            }

            // Lectura del fichero
            String lineaIn;

            while ((lineaIn = br.readLine()) != null) {

                // Separar la lineas en campos separados por comas
                // Split es un metodo de la clase String de java
                // y se utiliza una expresion regular, para indicar el caracter separador
                String[] vecStr = lineaIn.split("[\\,]");

                if ((vecStr.length == 4)) { // comparar el apellido
                    list.add(new UsuarioModelo(vecStr[0], vecStr[1], vecStr[2], vecStr[3]));
                }

            }

        } catch (Exception ex) {
            System.out.println("\n\nError archivo: " + nameFileIn + "\n" + ex.getMessage());
            System.exit(1);
            return null;
        } finally {
            try {

                // Cerrar archivo de salida
                if (br != null) {
                    br.close();
                }

            } catch (Exception ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return null;
            }

            try {

                // Cerrar archivo de salida
                if (fileIn != null) {
                    fileIn.close();
                }

            } catch (Exception ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo");
                System.exit(1);
                return null;
            }

        }

        return list;
    }

}
