package com.app;

import java.util.Scanner;


/**
 * Considere el tradicional juego de la SOPA DE LETRAS, y la siguiente matriz:
 *
 * char matriz[][] = { {'X', 'M', 'A', 'N', 'T', 'I', 'J', 'E'}, <br>
 * {'G', 'U', 'S', 'A', 'S', 'U', 'H', 'O'}, <br>
 * {'M', 'A', 'N', 'Z', 'A', 'N', 'A', 'R'}, <br>
 * {'E', 'N', 'T', 'O', 'N', 'E', 'L', 'M'}, <br>
 * {'L', 'B', 'O', 'A', 'D', 'R', 'V', 'E'}, <br>
 * {'O', 'G', 'F', 'W', 'I', 'G', 'E', 'I'}, <br>
 * {'N', 'T', 'U', 'V', 'A', 'Z', 'R', 'E'}, <br>
 * {'U', 'V', 'E', 'R', 'L', 'O', 'M', 'E'}}; <br>
 *
 * El juego es un tanto distinto del tradicional. La idea es ingresar una palabra, con o sin sentido, <br>
 * y que el programa responda indicando si está o no la palabra, y la posición inicial y final en la que se encuentra. <br>
 * La palabra puede estar en forma horizontal de izquierda a derecha, o vertical, de arriba hacia abajo.  <br>
 *
 */
public class AppMain {

    private static char matriz[][] = {
        {'X', 'M', 'A', 'N', 'T', 'I', 'J', 'E'},
        {'G', 'U', 'S', 'A', 'S', 'U', 'H', 'O'},
        {'M', 'A', 'N', 'Z', 'A', 'N', 'A', 'R'},
        {'E', 'N', 'T', 'O', 'N', 'E', 'L', 'M'},
        {'L', 'B', 'O', 'A', 'D', 'R', 'V', 'E'},
        {'O', 'G', 'F', 'W', 'I', 'G', 'E', 'I'},
        {'N', 'T', 'U', 'V', 'A', 'Z', 'R', 'E'},
        {'U', 'V', 'E', 'R', 'L', 'O', 'M', 'E'}};


    public static void main(String[] args) {

        boolean game_over = false;

        Scanner sc = new Scanner(System.in);

        do {

            System.out.print("\n\n******************************");
            System.out.print("\n******  SOPA DE LETRAS   *****");

            System.out.print("\n\n Tablero");
            for (int i = 0; i < matriz.length; i++) {
                System.out.print("\n");
                for (int j = 0; j < matriz[0].length; j++) {
                    System.out.print(" " + matriz[i][j]);
                }
            }

            System.out.print("\nPor favor, indique la palabra a buscar");
            System.out.print("\nPalabra= ? ");

            // Leer la palabra/linea, sin espacios en blanco al principio y final, y pasarlo a mayuscula
            String palabra = sc.nextLine().trim().toUpperCase();

            // Variables de programa
            int iini = -1;
            int kini = -1;
            int ifin = -1;
            int kfin = -1;
            boolean encontrado = false;
            int palLen = palabra.length();

            // buscar la palabra horizontalmente, de izquierda a derecha            
            for (int i = 0; (i < matriz.length) && !encontrado; i++) { // recorriendo las filas, de arriba hacia abajo
                for (int j = 0; j < matriz[0].length; j++) { // recorriendo el comienzo de la palabra, de izquierda a derecha
                    StringBuilder sb = new StringBuilder();
                    for (int k = j; (k < j + palLen) && (k < matriz[0].length); k++) { // Formando la palabra con las letras del tablero                        
                        sb.append(matriz[i][k]);
                    }

                    // Comparar la palabra leida, con la palabra que se ha formado
                    if (palabra.equals(sb.toString())) {
                        iini = i;
                        kini = j;

                        ifin = i;
                        kfin = j + palLen - 1;
                        encontrado = true;
                        break;
                    }
                }
            }

            // buscar la palabra verticalmente, de arriba hacia abajo            
            if (!encontrado) {
                for (int j = 0; (j < matriz[0].length) && !encontrado; j++) { // recorriendo las columnas, de izquierda a derecha
                    for (int i = 0; i < matriz.length; i++) { // recorriendo las filas, de arriba hacia abajo
                        StringBuilder sb = new StringBuilder();
                        for (int k = i; (k < i + palLen) && (k < matriz.length); k++) { // Formando la palabra con las letras del tablero                        
                            sb.append(matriz[k][j]);
                        }

                        // Comparar la palabra leida, con la palabra que se ha formado
                        if (palabra.equals(sb.toString())) {
                            iini = i;
                            kini = j;

                            ifin = i + palLen - 1;
                            kfin = j;
                            encontrado = true;
                            break;
                        }
                    }
                }
            }

            if (encontrado) {
                System.out.print("\nLa Palabra " + palabra + " existe");
                System.out.print("\nComienza en la posición [" + iini + ", " + kini + "] y termina en la posición [" + ifin + ", " + kfin + "]");
            } else {
                System.out.print("\nLa Palabra " + palabra + " NO existe");
            }

            System.out.print("\nDesea continuar [S/N]");
            System.out.print("\nRespuesta= ? ");

            // Leer la repuesta/linea, sin espacios en blanco al principio y final, y pasarlo a mayuscula
            String respuesta = sc.nextLine().trim().toUpperCase();

            // Finaliza con cualquier letra distinta a "S"gusa
            if ((respuesta.length() > 0) && (respuesta.charAt(0) != 'S')) {
                game_over = true;
            }

        } while (!game_over);

        System.out.print("\n\nThat's all Folk\n\n");
    }

}
