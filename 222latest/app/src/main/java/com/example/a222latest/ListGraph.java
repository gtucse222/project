package com.example.a222latest;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListGraph extends AbstractGraph
{
    private List<Edge>[] edges;

    @SuppressWarnings("unchecked")
    public ListGraph(int numV, boolean isDirected) {
        super(numV, isDirected);
        edges = new List[numV];

        for (int i = 0; i < numV; ++i) edges[i] = new LinkedList<Edge>();
    }

    @Override
    public Iterator<Edge> edgeIterator(int source) {
        return edges[source].iterator();
    }

    @Override
    public Edge getEdge(int source, int dest) {

        Edge target = new Edge(source, dest, Double.POSITIVE_INFINITY);

        for (Edge edge : edges[source])
            if (edge.equals(target)) return edge;

        return null;
    }

    @Override
    public void insert(Edge e) {
        edges[e.getSource()].add(e);

        if (!isDirected())
            edges[e.getDest()].add(new Edge(e.getDest(), e.getSource(), e.getWeight()));
    }

    @Override
    public boolean isEdge(int source, int dest) {
        return edges[source].contains(new Edge(source, dest));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (List<Edge> edge : edges)
            for (Edge value : edge) sb.append(value.toString());

        return sb.toString();
    }
}