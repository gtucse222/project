package com.example.a222latest;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Search adapter class
 */
public class GraphSearch
{
    /**
     * static function helps for breadt first search algorithm
     * @param graph, graph
     * @param start, start vertex
     * @return result array of BFS
     */
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
}
