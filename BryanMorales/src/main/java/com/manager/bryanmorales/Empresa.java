package com.manager.bryanmorales;

import Datos.Escribir;
import Datos.Leer;

/**
 *
 * @author halop
 */
public class Empresa {

    private String nombre;
    private String rfc;
    private String carrera;
    private short anios;
    String[] mes;
    double[][] ventaMes;
    double proCal, proGen, sumPro;

    public Empresa(int noMat, int noCalf) {
        mes = new String[noMat];
        ventaMes = new double[noMat][noCalf];
    }

    public Empresa(String nombre, String apPat, String apMat, String rfc, String carrera, short anios,
            int noMat, int noCalf) {
        this.nombre = nombre;
        this.rfc = rfc;
        this.carrera = carrera;
        this.anios = anios;
        mes = new String[noMat];
        ventaMes = new double[noMat][noCalf];
    }

    public void LlenadoDatos() {
        setNombre(Escribir.LlenadoString("Ingrese nombre de la empresa:\t"));
        do {
            setRfc(Escribir.LlenadoString("Ingrese rfc:\t"));
        } while (getRfc().length() < 8 || getRfc().length() > 9);
        do {
            setAnios(Escribir.LlenadoShort("Ingrese años de actividad:\t"));
        } while (getAnios() <= 0 || getAnios() > 800);
        LlenadoMes();
        LlenadoventaMes();

    }

    // llenado del dato mes //
    private void LlenadoMes() {
        System.out.println("Llenado Mes");
        for (int i = 0; i < mes.length; i++) {
            System.out.print("Ingrese mes #" + (i + 1) + ":\t");
            mes[i] = Leer.DatoString();
        }
    }

    // Llenado de dato calificaciones
    private void LlenadoventaMes() {
        for (int i = 0; i < ventaMes.length; i++) {
            System.out.println(mes[i]);

            for (int j = 0; j < ventaMes[i].length; j++) {
                System.out.print("Venta #" + (j + 1) + ":\t");
                ventaMes[i][j] = Leer.DatoDouble();
            }

        }

    }

    private void ImpVentas() {
        proGen = 0;
        for (int i = 0; i < ventaMes.length; i++) {
            System.out.println("Ventas del mes: " + mes[i]);
            sumPro = 0;
            for (int j = 0; j < ventaMes[i].length; j++) {
                System.out.println("Venta #" + (j + 1) + ":\t" + ventaMes[i][j]);
                sumPro += ventaMes[i][j];
            }
            proCal = sumPro / ventaMes[i].length;
            System.out.println("Promedio:\t" + proCal);
            proGen += proCal;
        }
        System.out.println("Promedio de venta:\t " + proGen / mes.length);
    }

    public void ImpDatos(int n) {
        System.out.println("Datos de la empresa #" + (n + 1));
        System.out.println("Nombre:\t" + getNombre());
        System.out.println("RFC:\t" + getRfc());
        System.out.println("Años de actividad:\t" + getAnios());
        ImpVentas();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public short getAnios() {
        return anios;
    }

    public void setAnios(short anios) {
        this.anios = anios;
    }

}
