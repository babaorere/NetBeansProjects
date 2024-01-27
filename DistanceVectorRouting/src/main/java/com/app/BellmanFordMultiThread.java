package com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Esta clase se prodria decir que es el "Thread Manager", ya que encarga de la tarea de crear y
 * manipular los Threads, a cada Nodo, para aplicarle el algoritmo de Bellman-Ford, basicamente
 * buscar los Nodos que han sido mapeados en el atributo "mapNode", y uno a uno les va creando
 * su hilo/thread, una vez listos los ejecuta, y espera hasta que todos los hilos hallan terminado
 * usando "latch.await()", dentro de los hilos se ejecuta el algoritmo, y al moemto de realizar alguna
 * actualizacion a la DV Table, se asegura con un "lock", esto asegura la sincronizacion del acceso
 * a la DV Table, ademas se declaran atributo con el modificado "volatile" para asegurar que las actualizaciones
 * se hagan en un solo proceso, sin interrupciones.
 *
 * El atributo "hayCambio" es tiene el modificador volatile, y en caso de que existe una actualizacion a la DV table,
 * este se hace "true", para indicar que es necesario repetir el proceso
 *
 * Hay que mencionar el uso de clase como ConcurrentHashMap, ya que estas estan diseñadas para
 * ambientes multihilos, e internamente realiza la sincronizacioon de las lectura y escritura,
 * y permite simplificar la manipulacion de los objetos, y la multiplicidad de la informacion,
 * "solo existe uno" es la frase de un Map, y eso nos asegura evitar posibles errores en los datos
 * o en la misma programacion.
 *
 */
public class BellmanFordMultiThread {

    // para realizar el track en consola y archivo, de los posibles errores
    private static final Logger logger = LogManager.getLogger(BellmanFordMultiThread.class);

    // Se utiliza como flag, para saber si hay o no cambios en la corrida de un ciclo
    private static volatile boolean huboCambio;

    // Mantiene los nodos que han sido indicados en el archivo de datos, se utiliza como repositorio
    private final ConcurrentHashMap<Integer, Node> mapNode;

    // Se utiliza por simplicacion, y mantiene el numero de nodos creados
    private final int numNodes;

    // Se utiliza para realizar bloqueo en el codigo de arctualizacion, y evitar el acceso multiple
    // en ese bloque de codigo
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Constructo, actualizamos los atributo, se puede ver que el atributo "numNodes" se obtiene del "mapode"
     * pero dado su repetitivo uso, y para legilibilidad del codigo, se prefirio tenerla declarada
     *
     * @param inMapNode
     * @param inLinkRepo
     */
    public BellmanFordMultiThread(final ConcurrentHashMap<Integer, Node> inMapNode) {
        this.numNodes = inMapNode.size();

        this.mapNode = inMapNode;
    }

    /**
     * Contiene el codigo que crea los hilos, y estos a su vez ejecutal el algoritmo Bellman-Ford
     */
    public int bellmanFord() {

        // Validacion
        if (numNodes <= 1) {
            JOptionPane.showMessageDialog(null, "No hay suficientes datos para ejecutar el algoritmo. Cantidad de nodos: " + numNodes,
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        // De ayuda en caso de ciclos infinitos
        int watchDog = 0;

        do {
            // creacion y manipulacionde los hilos
            doMagic();

            // incrementamos para verificar los ciclos
            if (huboCambio) {
                watchDog++;
            }

            // El ciclo se repite, hasta que no hay mas cambios
            // el watchDog, se utiliza en caso de ciclos infinitos, rompe el ciclo
            // el watchDog es una seguridad adicional, en caso de algun fallo
        } while (huboCambio && (watchDog < 1000));

        if (watchDog >= 1000) {
            JOptionPane.showMessageDialog(null, "Cuidado, posible ciclo infinito", "Cuidado", JOptionPane.WARNING_MESSAGE);
        }

        return watchDog;
    }

    /**
     * Contiene el codigo que crea los hilos, y estos a su vez ejecutal el algoritmo Bellman-Ford pero paso a paso
     */
    public boolean bellmanFordStep() {

        // creacion y manipulacionde los hilos
        doMagic();

        return huboCambio;
    }

    private void doMagic() {
        // Creacion de los hilos
        List<Thread> threads = new ArrayList<>();

        // Es utilizado mas adelante, como cuenta regresiva, y esperar que todos los hilos terminen
        // como se ve es comun a todos los hilos. El Hilo es creado con cada Nodo de la red,
        // que sera tomado como el "Source" del algoritmo, en el ejemplo de la presentario podria
        // referirce al nodo "B"
        CountDownLatch latch = new CountDownLatch(numNodes);
        for (ConcurrentHashMap.Entry<Integer, Node> entry : mapNode.entrySet()) {
            final Node value = entry.getValue();
            threads.add(new Thread(new NodeProcessor(value, latch)));
        }

        // Inicializamos la variable flag
        huboCambio = false;

        // Iniciar los hilos, se debe hacer asi, ya que es necesario que el 100% de los otros hilos
        // esten para intercambiarse las "DV Tables"
        for (int i = 0; i < numNodes; i++) {
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            try {
                latch.await(); // Espera que todos los hilos terminen
            } catch (InterruptedException inex) {
                logger.error(inex);
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Esta es la clase donde se define realmente el Hilo
     *
     */
    private class NodeProcessor implements Runnable {

        // El nodo fuente, o principal, sobre el cual "rotan las "DV Tables" de los otros nodos
        private final Node sourceNode;

        // Contador flag multihilo, que se decrementa al final de la ejecucion, y sirve de indicador de fin
        private final CountDownLatch latch;

        /**
         * Constructor para iniciar los atributos
         *
         * @param inSourceNode
         * @param inLatch
         */
        public NodeProcessor(Node inSourceNode, CountDownLatch inLatch) {
            this.sourceNode = inSourceNode;
            this.latch = inLatch;
        }

        @Override
        /**
         * De verdad verdad, aqui esta la magia, realmente es super complicado porque "de donde viene", "hacia donde va",
         * "quien la tiene", etc.,
         * Este es el codigo que se ejecuta en "paralelo" o al menos en forma asincrona, segun el nodo fuente "Source" que
         * se este ejecutando, teniendo cuidado de actualizar cosas dentro de un ambiente multihilo, y recursos compartidos
         */
        //
        // "here's the magic."        
        public void run() {

            // Aqui gurdamos la nueva "DV Table" del nodo fuente "Source", que al final sera actualizada
            ConcurrentHashMap<Integer, Integer> newDV = new ConcurrentHashMap<>();

            // este for es necesario para poder actualizar la respectiva DV table del nodo fuente, 
            // en el caso de la presentacion en clase, si tomamoss com DV, la DV Table del nodo fuente
            // que pudiera ser el nodo B, con el "for" vamos actualizando los valores de la DV(X),
            // recorriendo todas la X, pero a nivel de "DV Table"
            for (ConcurrentHashMap.Entry<Integer, Node> entryVecino : mapNode.entrySet()) {
                final Integer keyVecino = entryVecino.getKey();
                final Node valueVecino = entryVecino.getValue();

                // Rechazar porque no requerimos el nodo  fuente para el algoritmo
                if (keyVecino == sourceNode.getId()) {
                    // Aqui actualizamos en la nueva DV Table
                    newDV.put(keyVecino, sourceNode.getDv().get(keyVecino));
                    continue; // saltamos el codigo
                }

                // Recorremos todos los links del nodo a analizar, para hallar su costo
                // Para efectos del programa, tomamos el valor de "Integer.MAX_VALUE" como infinito
                int distMin = Integer.MAX_VALUE;

                // Esto es para comprobacio y debug
                //                                     B                              A                         Cba
                System.out.println("(B) Source: " + sourceNode.getId() + ", (A) idxDV: " + keyVecino + ", (Cab) dist: " + sourceNode.getDv().get(keyVecino));

                // Recorremos las "DV Tables", esto vendria siendo los sumandos que aparecen en 
                // la ecuacion en la presentacion de clase
                for (ConcurrentHashMap.Entry<Integer, Integer> entry : sourceNode.getDv().entrySet()) {
                    final Integer key = entry.getKey();
                    final Integer value = entry.getValue();

                    // Rechazar porque no requerimos la DV Table de la fuente
                    if (key == sourceNode.getId()) {
                        continue;
                    }

                    // Tomar el costo del enlace, vendria siendo el "C"
                    int distSourceToNeighbor = value;

                    // Aqui el DV(x)
                    System.out.println("key -> " + key);
                    Node nodeNeighbor = mapNode.get(key);
                    System.out.println("idvecino: " + keyVecino);
                    int distDVVecinoToIdxDVSource = nodeNeighbor.getDv().get(keyVecino);

                    // Verificamos los "infinitos"
                    int distAux;
                    if (distSourceToNeighbor >= Integer.MAX_VALUE || distDVVecinoToIdxDVSource >= Integer.MAX_VALUE) {
                        distAux = Integer.MAX_VALUE;
                    } else {
                        distAux = distSourceToNeighbor + distDVVecinoToIdxDVSource;
                    }

                    // A fines de verificacion y debug
                    System.out.println("(A) Source id= " + sourceNode.getId() + " -> neighbor: " + keyVecino + ", D(" + key + ", " + keyVecino + ") , dist (" + sourceNode.getId() + ", " + key + ")= " + distSourceToNeighbor + ", distAux: " + distAux);

                    // Aqui guardamos la distancia minima
                    // de forma similar a la presentacion, el minimo de los sumandos
                    if (distAux < distMin) {
                        distMin = distAux;
                    }

                }

                // Para fines de verificacion y debug
                System.out.println("idVecino:" + keyVecino + ", Min= " + distMin);

                // Aqui actualizamos la nueva DV Table
                newDV.put(keyVecino, distMin);
            }

            // Este codigo sirve para verificar si hubo, o no cambios
            boolean aux = false;
            // Verificar si hubo cambios
            if (sourceNode.getDv().size() != newDV.size()) {
                aux = true;
            } else {
                for (Map.Entry<Integer, Integer> entry : sourceNode.getDv().entrySet()) {
                    Integer key = entry.getKey();
                    Integer value1 = entry.getValue();
                    Integer value2 = newDV.get(key);

                    if (value2 == null || !value2.equals(value1)) {
                        aux = true;
                        break;
                    }
                }
            }

            try {
                lock.lock(); // Adquiere el bloqueo por seguridad compartiendo recursos
                huboCambio = aux;
                sourceNode.setDVTable(newDV);
            } finally {
                lock.unlock(); // Libera el bloqueo
            }

            // Marca la tarea como completada, y a continuacion termina el Hilo
            latch.countDown();
        }

    }

}
