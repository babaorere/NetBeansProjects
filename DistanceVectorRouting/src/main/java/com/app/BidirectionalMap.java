package com.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta es una clase que hace las veces de un MAP bidireccional, es decir existe una relacion 1 a 1, y solo una, no existe otro par igual,
 * y es utilizada para mapear los IDs de los nodos en el archivo, y convertirlos en un formato IDs 0..n donde se cumple que: n es menor numNodos
 * Esto se debe a que internamente el algoritmo esta diseñado para IDs de nodos que comience en 0, y el ID no supere el numero de nodos
 *
 * Sin esta clase los nodos [1, 2, 3, 4] (no comienza por 0) no podrian ser procesados, o tampoco [0, 5, 33, 22] porque el Id 33 supera el numerode nodos,
 *
 * @author manager
 * @param <K>
 * @param <V>
 */
public class BidirectionalMap<K, V> {

    private final Map<K, V> forwardMap = new HashMap<>();
    private final Map<V, K> reverseMap = new HashMap<>();

    /**
     * Agregar una nueva relacion
     *
     * @param inIdNode
     * @param inNickName
     */
    public void put(K inIdNode, V inNickName) {
        forwardMap.put(inIdNode, inNickName);
        reverseMap.put(inNickName, inIdNode);
    }

    public V getNickName(K inIdNode) {
        return forwardMap.get(inIdNode);
    }

    public K getIdNode(V inNickName) {
        return reverseMap.get(inNickName);
    }

    public boolean containsIdNode(K inIdNode) {
        return forwardMap.containsKey(inIdNode);
    }

    public boolean containsNickName(V inNickName) {
        return reverseMap.containsKey(inNickName);
    }

    public void removeIdNode(K inIdNode) {
        V nickName = forwardMap.get(inIdNode);
        if (nickName != null) {
            forwardMap.remove(inIdNode);
            reverseMap.remove(nickName);
        }
    }

    public void removeNickName(V inNickName) {
        K idNode = reverseMap.get(inNickName);
        if (idNode != null) {
            reverseMap.remove(inNickName);
            forwardMap.remove(idNode);
        }
    }

    public int size() {
        return forwardMap.size();
    }

    public boolean isEmpty() {
        return forwardMap.isEmpty();
    }
}
