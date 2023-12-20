package com.app;

import java.util.ArrayList;
import java.util.List;

public class BellmanFord {

    private final int numVertices;
    private final List<MyEdge> edges;
    private final int[] distance;
    private final int[] parent;

    public BellmanFord(int numVertices, List<MyEdge> edges) {
        this.numVertices = numVertices;
        this.edges = edges;
        this.distance = new int[numVertices];
        this.parent = new int[numVertices];
    }

    public void bellmanFord(int source) {
        for (int i = 0; i < numVertices; i++) {
            distance[i] = Integer.MAX_VALUE;
            parent[i] = -1; // Inicializa los padres como -1 (sin predecesores).
        }
        distance[source] = 0;

        for (int i = 1; i < numVertices; i++) {
            for (MyEdge edge : edges) {
                int u = edge.getSource();
                int v = edge.getDestination();
                int weight = edge.getWeight();
                if (distance[u] != Integer.MAX_VALUE && distance[u] + weight < distance[v]) {
                    distance[v] = distance[u] + weight;
                    parent[v] = u; // Actualiza el predecesor de v en la ruta más corta.
                }
            }
        }

        for (MyEdge edge : edges) {
            int u = edge.getSource();
            int v = edge.getDestination();
            int weight = edge.getWeight();
            if (distance[u] != Integer.MAX_VALUE && distance[u] + weight < distance[v]) {
                System.out.println("El grafo contiene ciclos de peso negativo.");
                return;
            }
        }

        System.out.println("\n***   Resultados   ***");
        // Imprime las distancias más cortas y las rutas.
        for (int i = 0; i < numVertices; i++) {
            System.out.println("Distancia desde el nodo " + source + " al nodo " + i + ": " + distance[i]);
            printPath(source, i);
        }
    }

    private void printPath(int source, int destination) {
        if (destination == -1) {
            System.out.println("No hay ruta desde " + source + " al nodo " + destination);
            return;
        }

        System.out.print("Ruta desde " + source + " a " + destination + ": ");
        for (int i = destination; i != -1; i = parent[i]) {
            System.out.print(i);
            if (i != source) {
                System.out.print(" -> ");
            }
        }

        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        int numVertices = 4;
        List<MyEdge> edges = new ArrayList<>();
        edges.add(new MyEdge(0, 1, 1));
        edges.add(new MyEdge(0, 2, 3));
        edges.add(new MyEdge(1, 3, 2));
        edges.add(new MyEdge(2, 3, 4));

        BellmanFord bellmanFord = new BellmanFord(numVertices, edges);
        bellmanFord.bellmanFord(0);
    }
}
