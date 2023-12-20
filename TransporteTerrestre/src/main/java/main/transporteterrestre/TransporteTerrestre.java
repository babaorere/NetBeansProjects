/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main.transporteterrestre;

/**
 *
 * @author manager
 */
public class TransporteTerrestre {

    public static void main(String[] args) {

        Vehiculo v100 = new Camion();

        Camion vc1 = new Camion("F750", "555-ABC", "Blanco", 20000000, 9000000);
        Camion vc2 = new Camion("F750", "666-DEF", "Azul", 15000000, 10000000);

        TipoCamion vt1 = new TipoCamion("Plataforma", "F1000", "999-XYZ", "Rojo", 10000000, 5000000);
        TipoCamion vt2 = new TipoCamion("Cava", "F1000", "876-QWE", "Negro", 5000000, 20000000);

        // Asi se usa el polimorfismo
        System.out.println("\nAsi se usa el polimorfismo, usando 'public String toString()'\n");

        Vehiculo v = vc1;

        System.out.println("\nCaracteristicas Vehiculo 1: " + v.mostrarAtributos());
        v.AjustarMostrarPrecio();

        v = vc2;
        System.out.println("\nCaracteristicas Vehiculo 2: " + v);
        v.AjustarMostrarPrecio();

        v = vt1;
        vt1.MetodoX();
        System.out.println("\nCaracteristicas Vehiculo 3: " + v);
        v.AjustarMostrarPrecio();

        v = vt2;
        System.out.println("\nCaracteristicas Vehiculo 4: " + v);
        v.AjustarMostrarPrecio();

        // Esta es otra forma, usando 'public String mostrarAtributos()'
        System.out.println("\nEsta es otra forma, usando 'public String mostrarAtributos()'");

        v = vc1;
        System.out.println("\nCaracteristicas Vehiculo 1: " + v.mostrarAtributos());
        v.AjustarMostrarPrecio();

        v = vc2;
        System.out.println("\nCaracteristicas Vehiculo 1: " + v.mostrarAtributos());
        v.AjustarMostrarPrecio();

        v = vt1;
        System.out.println("\nCaracteristicas Vehiculo 1: " + v.mostrarAtributos());
        v.AjustarMostrarPrecio();

        v = vt2;
        System.out.println("\nCaracteristicas Vehiculo 1: " + v.mostrarAtributos());
        v.AjustarMostrarPrecio();

    }
}
