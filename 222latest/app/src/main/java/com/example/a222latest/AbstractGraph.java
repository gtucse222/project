package com.example.a222latest;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public abstract class AbstractGraph implements Graph
{
    private boolean directed;

    private int numv;

    public AbstractGraph(int numV, boolean directed) {
        this.numv = numV;
        this.directed = directed;
    }

    public abstract void insert(Edge edge);
    public abstract boolean isEdge(int source, int dest);
    public abstract Edge getEdge(int source, int dest);
    public abstract Iterator<Edge> edgeIterator(int source);

    @Override
    public int getNumV() { return numv; }

    @Override
    public boolean isDirected() { return directed; }

    public void loadEdgesFromFile(Scanner scan) {

        String line = "";
        int source = 0;

        while(line != null) {

            line = scan.nextLine();

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