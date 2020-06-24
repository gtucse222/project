package com.example.a222latest;

import java.util.Iterator;

public interface Graph
{
    /**
     * @return vertex number
     */
    int getNumV();

    /**
     * @return info of directed or not
     */
    boolean isDirected();

    /**
     * insert method
     * @param edge to be insert
     */
    void insert(Edge edge);

    /**
     * @param source source of edge
     * @param dest dest of edge
     * @return is edge or not
     */
    boolean isEdge(int source, int dest);

    /**
     * @param source source of edge
     * @param dest dest of edge
     * @return source -> dest edge
     */
    Edge getEdge(int source, int dest);

    /**
     * @param source source of list
     * @return iterator of source of list
     */
    Iterator<Edge> edgeIterator(int source);
}
