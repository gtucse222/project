package com.example.a222latest;

import java.util.Iterator;

/**
 * Matrix Graph implementation
 */
public class MatrixGraph extends AbstractGraph
{
    /**
     * keep edges with 2D array
     */
    private double[][] edges;

    /**
     * constructor
     * @param numV, vertex number
     * @param isDirected, directed or indirected info
     */
    public MatrixGraph(int numV, boolean isDirected) {
        super(numV, isDirected);

        edges = new double[numV][numV];

        for(int i = 0 ; i < numV ; ++i)
            for(int j = 0 ; j < numV ; ++j)
                edges[i][j] = 0.0;
    }

    /**
     * getter method for Edge object
     * @param source, source vertex
     * @param dest, destination vertex
     * @return Edge source to destination
     */
    @Override
    public Edge getEdge(int source, int dest) {
        if(edges[source][dest] != 0.0)
            return new Edge(source, dest, edges[source][dest]);

        return null;
    }

    /**
     * insert method
     * @param e, to be insert edge
     */
    @Override
    public void insert(Edge e) {
        if(edges[e.getSource()][e.getDest()] == 0.0)
            edges[e.getSource()][e.getDest()] = e.getWeight();

        if(!isDirected()) edges[e.getDest()][e.getSource()] = e.getWeight();
    }

    /**
     * is Edge or not
     * @param source, source vertex
     * @param dest, destination vertex
     * @return if the edges[source][destination] is edge return true otherwise false
     */
    @Override
    public boolean isEdge(int source, int dest) { return edges[source][dest] != 0.0; }

    /**
     * edge iterator method
     * @param source, iterator of source
     * @return return iterator of source vertex
     */
    @Override
    public Iterator<Edge> edgeIterator(int source) {
        return null;
    }

    /**
     * Private inner iterator class
     */
    private class MatrixGraphIterator implements Iterator<Edge>
    {
        /**
         * source
         */
        private int source;

        /**
         * destination
         */
        private int dest;

        /**
         * weighted
         */
        private double w;


        /**
         * contructor
         * @param source source
         */
        public MatrixGraphIterator(int source) {
            this.source = source;
            this.dest = 0;
            this.w = edges[source][dest];
        }

        /**
         * @param source source
         * @return source th iterator
         */
        public MatrixGraphIterator iterator(int source) {

            if((source+1) == edges.length)
                return null;

            return new MatrixGraphIterator(source);
        }

        /**
         * has next method
         * @return if iterator has next true, otherwise return false
         */
        @Override
        public boolean hasNext() {

            int i = dest;
            while(!(edges[source].length <= i+1)) {
                ++i;
                if(isEdge(source, i))
                    return true;
            }

            return false;
        }

        /**
         * next method
         * @return next object of iterator
         */
        @Override
        public Edge next() {

            if(!hasNext())
                return null;

            w = edges[source][++dest];

            while(!isEdge(source, dest)) {

                if(!hasNext())
                    return null;

                ++dest;
                w = edges[source][dest];
            }

            return new Edge(source, dest, w);
        }
    }


    /**
     * override to string method
     * @return string of object
     */
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
