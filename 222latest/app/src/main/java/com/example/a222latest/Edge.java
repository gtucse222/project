package com.example.a222latest;

import java.util.Iterator;

public class Edge {
    /**
     * destination of edge
     */
    private int dest;

    /**
     * source of edge
     */
    private int source;

    /**
     * weight of edge
     */
    private double weight;

    /**
     * constructor
     *
     * @param source source
     * @param dest   destination
     */
    public Edge(int source, int dest) {
        this.source = source;
        this.dest = dest;
        this.weight = 1.0;
    }

    /**
     * @param source source
     * @param dest   destination
     * @param w      weight
     */
    public Edge(int source, int dest, double w) {
        this.source = source;
        this.dest = dest;
        this.weight = w;
    }

    /**
     * @return destination of edge
     */
    public int getDest() {
        return dest;
    }

    /**
     * @return source of edge
     */
    public int getSource() {
        return source;
    }

    /**
     * @return weight of edge
     */
    public double getWeight() {
        return weight;
    }

    /**
     * setter method
     *
     * @param source, set new source
     */
    public void setSource(int source) {
        if (source < 0)
            throw new IllegalArgumentException();

        this.source = source;
    }

    /**
     * setter method
     *
     * @param dest, set new destination
     */
    public void setDest(int dest) {
        if (dest < 0)
            throw new IllegalArgumentException();

        this.dest = dest;
    }

    /**
     * setter method
     *
     * @param weight, set new weight
     */
    public void setWeight(double weight) {
        if (weight < 0.0)
            throw new IllegalArgumentException();

        this.weight = weight;
    }

    /**
     * override equals method
     *
     * @param obj object
     * @return if the edges are same true otherwise false
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Edge))
            return false;

        Edge temp = (Edge) obj;
        return (source == temp.getSource()) && (dest == temp.getDest());
    }

    /**
     * override hashcode method
     *
     * @return hascode of edge
     */
    @Override
    public int hashCode() {
        return new Edge(this.source, this.dest).hashCode();
    }

    /**
     * @return string of edge
     */
    @Override
    public String toString() {
        return "Source: " + source + ", Dest: " + dest + ", W: " + weight + "\n";
    }
}

