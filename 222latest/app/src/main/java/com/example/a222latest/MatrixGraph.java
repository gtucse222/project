package com.example.a222latest;

import java.util.Iterator;

public class MatrixGraph extends AbstractGraph
{
    private double[][] edges;

    public MatrixGraph(int numV, boolean isDirected) {
        super(numV, isDirected);

        edges = new double[numV][numV];

        for(int i = 0 ; i < numV ; ++i)
            for(int j = 0 ; j < numV ; ++j)
                edges[i][j] = 0.0;
    }

    @Override
    public Edge getEdge(int source, int dest) {
        if(edges[source][dest] != 0.0)
            return new Edge(source, dest, edges[source][dest]);

        return null;
    }

    @Override
    public void insert(Edge e) {
        if(edges[e.getSource()][e.getDest()] == 0.0)
            edges[e.getSource()][e.getDest()] = e.getWeight();

        if(!isDirected()) edges[e.getDest()][e.getSource()] = e.getWeight();
    }

    @Override
    public boolean isEdge(int source, int dest) { return edges[source][dest] != 0.0; }

    @Override
    public Iterator<Edge> edgeIterator(int source) {
        return null;
    }

    private class MatrixGraphIterator implements Iterator<Edge>
    {
        @Override
        public boolean hasNext() { return false; }

        @Override
        public Edge next() {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (double[] edge : edges) {
            for (double v : edge) {
                sb.append(v);
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
