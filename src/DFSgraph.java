import java.io.*;
import java.util.*;

public class DFSgraph {

    public static void main(String[] args) {

        String fileName = "edges.txt";

        ArrayList<int[]> edges = new ArrayList<>();
        int maxVertex = -1;

        // Read graph information from the text file
        try {
            Scanner input = new Scanner(new File(fileName));

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] values = line.split("\\s+");

                try {
                    int source = Integer.parseInt(values[0]);
                    int target = Integer.parseInt(values[1]);

                    edges.add(new int[]{source, target});

                    maxVertex = Math.max(maxVertex,
                            Math.max(source, target));

                } catch (NumberFormatException e) {
                    // Skip the header
                }
            }

            input.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return;
        }

        if (maxVertex == -1) {
            System.out.println("No graph data found.");
            return;
        }

        int vertices = maxVertex + 1;

        // Create adjacency list
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }

        // Create adjacency matrix
        int[][] adjMatrix = new int[vertices][vertices];

        // Add edges to both graph representations
        for (int[] edge : edges) {
            int source = edge[0];
            int target = edge[1];

            adjList.get(source).add(target);
            adjMatrix[source][target] = 1;
        }

        // Sort adjacency list
        for (ArrayList<Integer> neighbors : adjList) {
            Collections.sort(neighbors);
        }

        // Perform DFS using adjacency list
        System.out.println("DFS using Adjacency List:");

        boolean[] visitedList = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            if (!visitedList[i]) {
                DFSList(adjList, i, visitedList);
            }
        }

        System.out.println();

        // Perform DFS using adjacency matrix
        System.out.println("DFS using Adjacency Matrix:");

        boolean[] visitedMatrix = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            if (!visitedMatrix[i]) {
                DFSMatrix(adjMatrix, i, visitedMatrix);
            }
        }

        System.out.println();
    }

    // DFS implementation using adjacency list
    public static void DFSList(
            ArrayList<ArrayList<Integer>> graph,
            int vertex,
            boolean[] visited) {

        visited[vertex] = true;

        System.out.print(vertex + " ");

        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                DFSList(graph, neighbor, visited);
            }
        }
    }

    // DFS implementation using adjacency matrix
    public static void DFSMatrix(
            int[][] graph,
            int vertex,
            boolean[] visited) {

        visited[vertex] = true;

        System.out.print(vertex + " ");

        for (int i = 0; i < graph.length; i++) {
            if (graph[vertex][i] == 1 && !visited[i]) {
                DFSMatrix(graph, i, visited);
            }
        }
    }

}
