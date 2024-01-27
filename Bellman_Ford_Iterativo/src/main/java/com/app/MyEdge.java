package com.app;

class MyEdge {

    private final int source;
    private final int destination;
    private final int weight;

    public MyEdge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * @return the source
     */
    public int getSource() {
        return source;
    }

    /**
     * @return the destination
     */
    public int getDestination() {
        return destination;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

}
