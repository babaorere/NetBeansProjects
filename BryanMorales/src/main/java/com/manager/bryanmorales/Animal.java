package com.manager.bryanmorales;

import Datos.Escribir;

/**
 *
 * @author halop
 */
public class prbempresa {

    public static void main(String[] args) {
        int n, m;
        do {
            n = Escribir.LlenadoInt("Ingrese años a registrar:\t");
        } while (n <= 0);
        Empresa[] ani = new Empresa[n];
        for (int i = 0; i < ani.length; i++) {
            System.out.println("Año #" + (i + 1));
            do {
                n = Escribir.LlenadoInt("Ingrese número de meses:\t");
            } while (n <= 0 || n > 12);
            do {
                m = Escribir.LlenadoInt("Ingrese número de ventas por mes:\t");
            } while (m <= 0);
            ani[i] = new Empresa(n, m);
            ani[i].LlenadoDatos();
        }
        for (int i = 0; i < ani.length; i++) {
            ani[i].ImpDatos(i);
        }
    }
}
