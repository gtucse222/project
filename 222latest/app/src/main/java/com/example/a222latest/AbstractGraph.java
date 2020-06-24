package com.example.a222latest;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public abstract class AbstractGraph implements Graph
{
    /**
     * directed or not information
     */
    private boolean directed;

    /**
     * vertex number
     */
    private int numv;

    /**
     * constructor
     * @param numV vertex number
     * @param directed directed or not
     */
    public AbstractGraph(int numV, boolean directed) {
        this.numv = numV;
        this.directed = directed;
    }

    /**
     * insert method
     * @param edge to be insert
     */
    public abstract void insert(Edge edge);

    /**
     * @param source source of edge
     * @param dest dest of edge
     * @return
     */
    public abstract boolean isEdge(int source, int dest);

    /**
     * @param source source of edge
     * @param dest dest of edge
     * @return edge of source -> dest
     */
    public abstract Edge getEdge(int source, int dest);

    /**
     * @param source source of list
     * @return iterator of source -> dest
     */
    public abstract Iterator<Edge> edgeIterator(int source);

    /**
     * @return vertex number
     */
    @Override
    public int getNumV() { return numv; }

    /**
     * @return directed or not
     */
    @Override
    public boolean isDirected() { return directed; }

    /**
     * load from file
     * @param scan scanner object
     */
    public void loadEdgesFromFile(Scanner scan) {

        String line = "";
        int source = -1;

        while(line != null) {

            line = scan.nextLine();
            ++source;

            if(line != null)
            {
                try {

                    for(int i = 0 ; i < line.length() ; ++i)
                        insert(new Edge(source, Integer.parseInt(String.valueOf(line.charAt(i)))));

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
            }
        }
    }

    /**
     * @param scan scanner object
     * @param isDirected directed or not
     * @param type matrix or list
     * @return graph
     * @throws IOException for file stream
     */
    public static Graph createGraph(Scanner scan, boolean isDirected, String type)
            throws IOException {

        int numV = scan.nextInt();
        scan.nextLine();

        AbstractGraph returnValue = null;

        if(type.equalsIgnoreCase("Matrix")) returnValue = new MatrixGraph(numV, isDirected);
        else if(type.equalsIgnoreCase("List")) returnValue = new ListGraph(numV, isDirected);
        else throw new IllegalArgumentException();

        returnValue.loadEdgesFromFile(scan);

        return (Graph) returnValue;
    }
}
