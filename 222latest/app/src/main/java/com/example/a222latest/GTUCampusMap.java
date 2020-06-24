package com.example.a222latest;

/**
 * keeps GTUCampus map graph information
 */
public class GTUCampusMap
{
    /**
     * Campus's vertex
     */
    private Graph GTUCampus;

    /**
     * constructor
     */
    public GTUCampusMap() {

        GTUCampus = new ListGraph(40, false);
        buildGraph();
    }

    /**
     * building graph
     */
    private void buildGraph() {
        GTUCampus.insert(new Edge(32, 33, 25.0));
        GTUCampus.insert(new Edge(33, 13, 10.0));
        GTUCampus.insert(new Edge(33, 34, 16.0));
        GTUCampus.insert(new Edge(36, 14, 10.0));
        GTUCampus.insert(new Edge(35, 19, 2.0));
        GTUCampus.insert(new Edge(19, 20, 0.5));
        GTUCampus.insert(new Edge(19, 24, 4.5));
        GTUCampus.insert(new Edge(20, 21, 1.0));
        GTUCampus.insert(new Edge(24, 21, 1.0));
        GTUCampus.insert(new Edge(22, 21, 1.0));
        GTUCampus.insert(new Edge(22, 23, 1.0));
        GTUCampus.insert(new Edge(23, 24, 1.0));
        GTUCampus.insert(new Edge(23, 26, 1.0));
        GTUCampus.insert(new Edge(23, 25, 1.0));
        GTUCampus.insert(new Edge(25, 26, 1.0));
        GTUCampus.insert(new Edge(15, 16, 3.0));
        GTUCampus.insert(new Edge(15, 17, 3.0));
        GTUCampus.insert(new Edge(16, 18, 1.5));
        GTUCampus.insert(new Edge(16, 17, 1.5));
        GTUCampus.insert(new Edge(17, 18, 1.5));
        GTUCampus.insert(new Edge(18, 11, 4.5));
        GTUCampus.insert(new Edge(18, 12, 1.5));
        GTUCampus.insert(new Edge(11, 12, 1.5));
        GTUCampus.insert(new Edge(11, 10, 2.0));
        GTUCampus.insert(new Edge(14, 15, 1.5));
        GTUCampus.insert(new Edge(8, 35, 13.0));
        GTUCampus.insert(new Edge(36, 1, 11.5));
        GTUCampus.insert(new Edge(34, 36, 5.0));
        GTUCampus.insert(new Edge(33, 37, 14.0));
        GTUCampus.insert(new Edge(37, 0, 14.0));
        GTUCampus.insert(new Edge(37, 34, 2.0));
        GTUCampus.insert(new Edge(37, 13, 14.0));
        GTUCampus.insert(new Edge(13, 0, 10.0));
        GTUCampus.insert(new Edge(32, 31, 2.0));
        GTUCampus.insert(new Edge(31, 29, 9.0));
        GTUCampus.insert(new Edge(29, 30, 1.0));
        GTUCampus.insert(new Edge(30, 13, 1.0));
        GTUCampus.insert(new Edge(13, 28, 6.0));
        GTUCampus.insert(new Edge(13, 27, 6.0));
        GTUCampus.insert(new Edge(12, 10, 1.5));
        GTUCampus.insert(new Edge(10, 9, 4.5));
        GTUCampus.insert(new Edge(9, 35, 3.5));
        GTUCampus.insert(new Edge(11, 9, 3.5));
        GTUCampus.insert(new Edge(2, 14, 4.0));
        GTUCampus.insert(new Edge(27, 0, 8.5));
        GTUCampus.insert(new Edge(1, 3, 13.0));
        GTUCampus.insert(new Edge(26, 0, 2.5));
        GTUCampus.insert(new Edge(1, 2, 4.5));
        GTUCampus.insert(new Edge(1, 2, 4.5));
        GTUCampus.insert(new Edge(3, 4, 1.0));
        GTUCampus.insert(new Edge(4, 5, 1.0));
        GTUCampus.insert(new Edge(5, 6, 3.0));
        GTUCampus.insert(new Edge(2, 6, 7.0));
        GTUCampus.insert(new Edge(6, 7, 1.5));
        GTUCampus.insert(new Edge(7, 8, 3.5));
        GTUCampus.insert(new Edge(2, 9, 8.0));
        GTUCampus.insert(new Edge(27, 28, 3.0));
        GTUCampus.insert(new Edge(38, 28, 7.0));
        GTUCampus.insert(new Edge(38, 31, 5.0));
        GTUCampus.insert(new Edge(30, 28, 7.0));
        GTUCampus.insert(new Edge(33, 30, 8.0));
    }

    /**
     * find direction with breadth first search algorithm
     * @param to, to location
     * @param from, from location
     * @return get location as string
     */
    public String direction_BFS(int to, int from) {

        StringBuilder sb = new StringBuilder();
        int[] direction_arr = GraphSearch.BreadthFirstSearch(GTUCampus, from);

        sb.append(to);
        sb.append(" ");
        int index = direction_arr[to];

        while(index != from) {
            sb.append(" ");
            sb.append(index);
            index = direction_arr[index];
        }
        sb.append(" ");
        sb.append(from);

        return sb.toString();
    }
}

