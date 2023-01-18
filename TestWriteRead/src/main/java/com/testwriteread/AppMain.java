package com.testwriteread;


/**
 *
 * @author manager
 */
public class AppMain {

    public static void main(String[] args) {

        Vehiculo vehiculo1 = new Vehiculo("Zona", 'D');
        Vehiculo vehiculo2 = new Vehiculo("Zona Remona", 'D');
        Vehiculo vehiculo3 = new Vehiculo("Zona Perona", 'D');

        System.out.println("\n1.- **********");
        Vehiculo.list.forEach((v) -> {
            System.out.println("\n" + v.toString());
        });

        Vehiculo.WriteToFile();

        Vehiculo.ReadFromFile();

        System.out.println("\n2.- **********");
        Vehiculo.list.forEach((v) -> {
            System.out.println("\n" + v.toString());
        });

        new Vehiculo("2 Zona", 'D');
        new Vehiculo("2 Zona Remona", 'D');
        new Vehiculo("2 Zona Perona", 'D');

        System.out.println("\n2.- **********");
        Vehiculo.list.forEach((v) -> {
            System.out.println("\n" + v.toString());
        });

    }

}
