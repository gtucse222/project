package com.example.a222latest;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Graph class that represent vertex with list
 */
public class ListGraph extends AbstractGraph
{
    /**
     * list of array edges
     */
    private List<Edge>[] edges;

    /**
     * consrtructor
     * @param numV, vertex number
     * @param isDirected, directed or not
     */
    @SuppressWarnings("unchecked")
    public ListGraph(int numV, boolean isDirected) {
        super(numV, isDirected);
        edges = new List[numV];

        for (int i = 0; i < numV; ++i) edges[i] = new LinkedList<Edge>();
    }

    /**
     * Iterator class
     * @param source source of list
     * @return iterator of source
     */
    @Override
    public Iterator<Edge> edgeIterator(int source) {
        return edges[source].iterator();
    }

    /**
     * getter method
     * @param source source of edge
     * @param dest dest of edge
     * @return get source to destination edge
     */
    @Override
    public Edge getEdge(int source, int dest) {

        Edge target = new Edge(source, dest, Double.POSITIVE_INFINITY);

        for (Edge edge : edges[source])
            if (edge.equals(target)) return edge;

        return null;
    }

    /**
     * insert method
     * @param e, to be insert
     */
    @Override
    public void insert(Edge e) {
        edges[e.getSource()].add(e);

        if (!isDirected())
            edges[e.getDest()].add(new Edge(e.getDest(), e.getSource(), e.getWeight()));
    }

    /**
     * is edge or not
     * @param source source of edge
     * @param dest dest of edge
     * @return true if the source to destination edge is edge
     */
    @Override
    public boolean isEdge(int source, int dest) {
        return edges[source].contains(new Edge(source, dest));
    }

    /**
     * string of list graph structure
     * @return string of list graph structure
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (List<Edge> edge : edges)
            for (Edge value : edge) sb.append(value.toString());

        return sb.toString();
    }
}
