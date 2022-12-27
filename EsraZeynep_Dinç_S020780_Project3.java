import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Graph {
    private Map<Character, List<Edge>> adj;Graph() {
        adj = new HashMap<>();
    }
    void addEdge(char u, char v, int w) {
        List<Edge> edges = adj.get(u);
        if (edges == null) {
            edges = new ArrayList<>();
            adj.put(u, edges);
        }
        edges.add(new Edge(v, w));
    }List<Edge> getAdj(char u) {
        List<Edge> edges = adj.get(u);
        if (edges == null) {
            return new ArrayList<>();
        }
        return edges;
    }
    static class Edge {
        char v;
        int w;
        Edge(char v, int w) {
            this.v = v;
            this.w = w;
        }
    }
}
class Main {
    private static Map<Character, Graph.Edge> residualCapacity;
    private static Map<Character, Character> parent;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        char[] projects = new char[n];
        int[] outcomes = new int[n];
        for (int i = 0; i < n; i++) {
            projects[i] = scanner.next().charAt(0);
        }
        for (int i = 0; i < n; i++) {
            outcomes[i] = scanner.nextInt();
        }
        Graph graph = new Graph();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.equals("Decide")) {
                break;
            }
            char u = s.charAt(1);
            char v = s.charAt(3);
            graph.addEdge(u, v, 0);
        }
        for (int i = 0; i < n; i++) {
            graph.addEdge(projects[i], 'T', outcomes[i]);
        }
        int maxFlow = maxFlow(graph, 'S', 'T');
        List<Character> subset = new ArrayList<>();
        List<Character> dependencies = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Graph.Edge> adj = graph.getAdj(projects[i]);
            for (Graph.Edge edge : adj) {
                if (edge.v == 'T' && edge.w > 0) {
                    subset.add(projects[i]);
                } else if (edge.w == 0) {
                    dependencies.add(edge.v);
                }
            }
        }
        int profit = 0;
        for (Character c : subset) {
            profit += outcomes[c - 'A'];
        }
        for (Character c : dependencies) {
            profit -= outcomes[c - 'A'];
        }
        System.out.println("Venture projects: " + subset);
        System.out.println("Maximum profit: " + profit);
    }
    public static int maxFlow(Graph graph, char source, char sink) {
        int flow = 0;
        while (bfs(graph, source, sink)) {
            int pathFlow = 0;
            char u = sink;
            while (u != source) {
                Graph.Edge edge = residualCapacity.get(u);
                pathFlow = Math.min(pathFlow, edge.w);
                u = parent.get(u);
            }
            u = sink;
            while (u != source) {
                Graph.Edge edge = residualCapacity.get(u);
                edge.w -= pathFlow;
                Graph.Edge reverseEdge = graph.getAdj(edge.v).get(edge.v);
                reverseEdge.w += pathFlow;
                u = parent.get(u);
            }
            flow += pathFlow;
        }
        return flow;
    }private static boolean bfs(Graph graph, char source, char sink) {
        Map<Character, Boolean> visited = new HashMap<>();
        LinkedList<Character> queue = new LinkedList<>();
        queue.add(source);
        visited.put(source, true);
        parent = new HashMap<>();
        residualCapacity = new HashMap<>();
        while (!queue.isEmpty()) {
            char u = queue.poll();
            List<Graph.Edge> adj = graph.getAdj(u);
            for (Graph.Edge edge : adj) {
                char v = edge.v;
                if (parent.get(v) == null && edge.w > 0) {
                    queue.offer(v);
                    parent.put(v, u);
                    residualCapacity.put(v, edge);
                    if (v == sink) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
