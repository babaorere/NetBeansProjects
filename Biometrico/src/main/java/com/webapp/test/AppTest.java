package com.webapp.test;

import com.webapp.biometrico.Biometrico;
import com.webapp.biometrico.DigitalPersona;
import com.webapp.biometrico.Microsoft;

/**
 *
 */
public class AppTest {

    public static void main(String[] args) {

        boolean continuar = true;
        int contador = 0;
        while (continuar) {
            Biometrico ms = new Microsoft();

            System.out.println("\nAtributo inicial de ms: " + (++contador) + "\n" + ms.toString());
            System.out.println("Ejecucion de metodos:");
            try {
                System.out.println("ms.onHuella(): " + ms.onHuella());
                System.out.println("ms.onMuestra(): " + ms.onMuestra());
                System.out.println("ms.outHuella(): " + ms.outHuella());
            } catch (Exception ex) {
                System.out.println("ms. Error: " + ex.toString());
                continuar = false;
            }
            System.out.println("\nAtributo final de ms: " + (++contador) + "\n" + ms.toString());
        }

        continuar = true;
        contador = 0;
        while (continuar) {
            Biometrico dp = new DigitalPersona();

            System.out.println("\nAtributo inicial de dp: " + (++contador) + "\n" + dp.toString());
            System.out.println("Ejecucion de metodos:");
            try {
                System.out.println("dp.onHuella(): " + dp.onHuella());
                System.out.println("dp.onMuestra(): " + dp.onMuestra());
                System.out.println("dp.outHuella(): " + dp.outHuella());
            } catch (Exception ex) {
                System.out.println("dp.outHuella(). Error: " + ex.toString());
                continuar = false;
            }
            System.out.println("\nAtributo final de dp: " + (++contador) + "\n" + dp.toString());

        }

    }

}
