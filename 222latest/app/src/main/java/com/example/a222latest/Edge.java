package com.example.a222latest;

public class Edge
{
    private int dest;

    private int source;

    private double weight;

    public Edge(int source, int dest) {
        this.source = source;
        this.dest = dest;
        this.weight = 1.0;
    }

    public Edge(int source, int dest, double w) {
        this.source = source;
        this.dest = dest;
        this.weight = w;
    }

    public int getDest() { return dest; }

    public int getSource() { return source; }

    public double getWeight() { return weight; }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Edge))
            return false;

        Edge temp = (Edge) obj;
        return (source == temp.getSource()) && (dest == temp.getDest());
    }

    @Override
    public int hashCode() { return new Edge(this.source, this.dest).hashCode(); }

    @Override
    public String toString() { return "Source: " + source + ", Dest: " + dest + ", W: " + weight + "\n"; }
}
