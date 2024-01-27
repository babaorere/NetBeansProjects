package com.app;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Edge (Link or Edge): An edge is a connection between two vertices in a graph. Edges represent relationships or
 * connections between the objects or elements that the vertices represent. In this case, the edges are bidirectional,
 * meaning that the relationship works in both directions.
 *
 */
public final class LinkRepo {

    // Mapa para el primer número y sus segundos números asociados con costo
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> mapLink = new ConcurrentHashMap<>();

    // Método para agregar una relación con costo entre un primer y un segundo número
    public void addRel(int inId1, int inId2, int inCost) {

        // Verifica si ya existe una relación con los mismos Ids
        if (mapLink.containsKey(inId1) && mapLink.get(inId1).containsKey(inId2)) {
            // Como ya existe una relacion, se procedo ha actualizar su costo
            mapLink.get(inId1).put(inId2, inCost);
        } else {
            // Si no existe una relación, crea una nueva entrada
            mapLink.computeIfAbsent(inId1, key -> new ConcurrentHashMap<>()).put(inId2, inCost);
        }

        // Verifica si ya existe una relación con los mismos Ids
        if (mapLink.containsKey(inId2) && mapLink.get(inId2).containsKey(inId1)) {
            // Como ya existe una relacion, se procedo ha actualizar su costo
            mapLink.get(inId2).put(inId1, inCost);
        } else {
            // Si no existe una relación, crea una nueva entrada
            mapLink.computeIfAbsent(inId2, key -> new ConcurrentHashMap<>()).put(inId1, inCost);
        }

    }

    // Método para obtener las relaciones con un Id
    public ConcurrentHashMap<Integer, Integer> getMapRel(int inId) {
        // Retorna el map de links solicitados, o un map vacio
        return mapLink.getOrDefault(inId, new ConcurrentHashMap<>());
    }

    // Método para obtener el costo dado un primer y segundo nodo
    public Integer getCost(int inIdNode1, int inIdNode2) {
        ConcurrentHashMap<Integer, Integer> mapRel = mapLink.get(inIdNode1);

        if (mapRel != null) {
            for (ConcurrentHashMap.Entry<Integer, Integer> entry : mapRel.entrySet()) {
                if (entry.getKey() == inIdNode2) {
                    return entry.getValue();
                }
            }
        }

        // Retornar null, porque no se encontro alguna relacion
        return null;
    }

    public LinkRepo() {
    }

    public boolean isEmpty() {
        return mapLink.isEmpty();
    }

    public ConcurrentHashMap<Integer, Node> getListNodes() {
        Set<Integer> uniqueNodes = new HashSet<>();

        // Aqui procedemos a "limpiar" los nodos, para solo identifica y tener un unico nodo, el MAP nos ayuda a 
        // realizar esta tarea, ya que solo permite una unica "key"
        for (ConcurrentHashMap.Entry<Integer, ConcurrentHashMap<Integer, Integer>> entry : mapLink.entrySet()) {
            uniqueNodes.add(entry.getKey());
            for (ConcurrentHashMap.Entry<Integer, Integer> entry2 : entry.getValue().entrySet()) {
                uniqueNodes.add(entry2.getKey());
            }
        }

        // Ya tenemos los IDs de los nodos, sin repeticion, vamos a crear la "DVTable" para cada nodo
        for (Integer id1 : uniqueNodes) {
            for (Integer id2 : uniqueNodes) {
                if (mapLink.get(id1).containsKey(id2)) {
                    if (Objects.equals(id1, id2)) {
                        mapLink.get(id1).put(id2, 0);
                    }
                } else {
                    if (Objects.equals(id1, id2)) {
                        mapLink.get(id1).put(id2, 0);
                    } else {
                        mapLink.get(id1).put(id2, Integer.MAX_VALUE);
                    }
                }
            }
        }

        ConcurrentHashMap<Integer, Node> arrNode = new ConcurrentHashMap<>();
        for (Integer id : uniqueNodes) {
            arrNode.put(id, new Node(id, uniqueNodes.size(), mapLink.get(id)));
        }

        return arrNode;
    }

    void clear() {
        mapLink.clear();
    }
}
