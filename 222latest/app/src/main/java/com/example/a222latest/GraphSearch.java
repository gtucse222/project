package com.example.a222latest;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GraphSearch
{
    public static int[] BreadthFirstSearch(Graph graph, int start) {

        Queue<Integer> queue = new LinkedList<>();

        int[] parent = new int[graph.getNumV()];

        for(int i = 0 ; i < graph.getNumV() ; ++i) parent[i] = -1;

        boolean[] identified = new boolean[graph.getNumV()];

        identified[start] = true;
        queue.offer(start);

        while(!queue.isEmpty()) {

            int current = queue.remove();
            Iterator<Edge> itr = graph.edgeIterator(current);

            while(itr.hasNext()) {

                int neighbor = itr.next().getDest();

                if(!identified[neighbor]) {
                    identified[neighbor] = true;
                    queue.offer(neighbor);
                    parent[neighbor] = current;
                }
            }
        }

        return parent;
    }

    public static String dijkstrasAlgorithm(Graph graph, int start, int[] pred, double[] dist) {

        int numv = graph.getNumV();
        HashSet<Integer> vMinusS = new HashSet<>(numv);

        for(int i = 0 ; i < numv ; ++i)
            if(i != start) vMinusS.add(i);

        for(int v : vMinusS) {
            pred[v] = start;
            dist[v] = graph.getEdge(start, v).getWeight();
        }

        while(vMinusS.size() != 0) {

            double minDist = Double.POSITIVE_INFINITY;
            int u = -1;

            for(int v : vMinusS) {
                if(dist[v] < minDist) {
                    minDist = dist[v];
                    u = v;
                }
            }

            vMinusS.remove(u);

            for(int v : vMinusS) {
                if(graph.isEdge(u, v)) {

                    double weight = graph.getEdge(u, v).getWeight();
                    if(dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                        pred[v] = u;
                    }
                }
            }
        }

        return pathToString(pred, start);
    }

    private static String pathToString(int[] path, int start) {

        StringBuilder sb = new StringBuilder();



        return sb.toString();
    }
}
