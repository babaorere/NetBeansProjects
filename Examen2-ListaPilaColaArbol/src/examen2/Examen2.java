package examen2;

import examen2.arbol.ArbolBinarioImpl;
import examen2.automovil.Automovil;
import examen2.automovil.AutomovilImpl;
import examen2.automovil.Catalitico;
import examen2.cola.Cola;
import examen2.lista.Lista;
import examen2.lista.ListaImpl;
import examen2.pila.Pila;

/**
 *
 * @author Felipe Belloy
 */
public class Examen2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Lista listaGeneral = new ListaImpl();

        listaGeneral.addEnd(new AutomovilImpl("jjcz-81", "Nissan", 2015, Catalitico.SI));
        listaGeneral.addEnd(new AutomovilImpl("jjcz-81", "AUDI", 2015, Catalitico.SI));
        listaGeneral.addEnd(new AutomovilImpl("jjcz-99", "kia", 2017, Catalitico.SI));
        listaGeneral.addEnd(new AutomovilImpl("jjcz-82", "Nissan", 2007, Catalitico.SI));
        listaGeneral.addEnd(new AutomovilImpl("jjcz-82", "Nissan", 1997, Catalitico.NO));
        Automovil itemAdd = new AutomovilImpl("jjcz-82", "AUDI", 2022, Catalitico.SI);
        listaGeneral.addEnd(itemAdd);
        listaGeneral.addEnd(new AutomovilImpl("jjcz-82", "chevrolet", 2017, Catalitico.SI));
        listaGeneral.addEnd(new AutomovilImpl("jjcz-82", "CHEVY", 2001, Catalitico.NO));
        listaGeneral.addEnd(new AutomovilImpl("jjcz-82", "Nissan", 2010, Catalitico.NO));
        listaGeneral.addEnd(new AutomovilImpl("jjcz-82", "CHEVY", 2012, Catalitico.SI));

        System.out.println("\nListado General de Automoviles");
        String[] strLista = listaGeneral.toArrStr();
        for (String str : strLista) {
            System.out.println(str);
        }

        Lista listaAnt2010 = listaGeneral.getListaAnt2010();
        listaAnt2010.ordInsercionById();

        System.out.println("\nAutomoviles anteriores a 2010, ordenados");
        String[] strListaAnt2010 = listaAnt2010.toArrStr();
        for (String str : strListaAnt2010) {
            System.out.println(str);
        }

        System.out.println("\nCola de Automoviles marca CHEVY");
        Cola colaChevy = listaGeneral.getColaMarca("CHEVY");
        String[] strColaChevy = colaChevy.toArrStr();
        for (String str : strColaChevy) {
            System.out.println(str);
        }

        System.out.println("Usando el pull");
        Automovil item = colaChevy.pull();
        while (item != null) {
            System.out.println(item);
            item = colaChevy.pull();
        }

        System.out.println("\nPila de Automoviles marca AUDI");
        Pila pilaAudi = listaGeneral.getPilaMarca("AUDI");
        String[] strPilaAudi = pilaAudi.toArrStr();
        for (String str : strPilaAudi) {
            System.out.println(str);
        }

        System.out.println("Usando el pop");
        item = pilaAudi.pop();
        while (item != null) {
            System.out.println(item);
            item = pilaAudi.pop();
        }

        System.out.println("\nArbol binario, ordenado por año");
        ArbolBinarioImpl arbol = listaGeneral.getArbol();
        arbol.preOrden();

        System.out.println("\nBusqueda Binaria, identificado=" + itemAdd.getId());
        Catalitico resp = listaGeneral.busquedaBinaria(itemAdd.getId());

        if (resp == null) {
            System.out.println("Auto NO encontrado, identificado=" + itemAdd.getId());
        } else {
            System.out.println("Auto SI encontrado: " + itemAdd);
            System.out.println("Es catalitico " + resp);
        }

        strLista = listaGeneral.toArrStr();
        for (String str : strLista) {
            System.out.println(str);
        }

        // Ordenar por el atributo Catalitico
        System.out.println("\nAutomoviles ordenados por atributo Catalítico");
        listaGeneral.ordInsercionByCatalitico();
        strLista = listaGeneral.toArrStr();
        for (String str : strLista) {
            System.out.println(str);
        }

        System.out.println("\nFin del programa");

    }

}
