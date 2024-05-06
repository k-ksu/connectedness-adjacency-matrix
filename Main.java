//import java.util.*;
//
//class Graph<T> {
//    private Map<T, List<T>> adjacencyList;
//
//    public Graph() {
//        adjacencyList = new HashMap<>();
//    }
//
//    public void addVertex(T vertex) {
//        adjacencyList.put(vertex, new ArrayList<>());
//    }
//
//    public void addEdge(T source, T destination) {
//        adjacencyList.get(source).add(destination);
//    }
//
//    public boolean isReachable(T source, T destination) {
//        Set<T> visited = new HashSet<>();
//        dfs(source, visited);
//        return visited.contains(destination);
//    }
//
//    private void dfs(T vertex, Set<T> visited) {
//        visited.add(vertex);
//        for (T neighbor : adjacencyList.getOrDefault(vertex, Collections.emptyList())) {
//            if (!visited.contains(neighbor)) {
//                dfs(neighbor, visited);
//            }
//        }
//    }
//}
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int[][] adjacencyMatrix = new int[n][n];
//
//        // Reading adjacency matrix
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                adjacencyMatrix[i][j] = scanner.nextInt();
//            }
//        }
//
//        Graph<Integer> graph = new Graph<>();
//
//        // Creating vertices
//        for (int i = 0; i < n; i++) {
//            graph.addVertex(i);
//        }
//
//        // Creating edges based on adjacency matrix
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (adjacencyMatrix[i][j] == 1) {
//                    graph.addEdge(i, j);
//                }
//            }
//        }
//
//        // Check if every city is reachable from every other city
//        boolean isReachable = true;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (!graph.isReachable(i, j)) {
//                    isReachable = false;
//                    break;
//                }
//            }
//            if (!isReachable) {
//                break;
//            }
//        }
//
//        // Output the result
//        System.out.println(isReachable ? "YES" : "NO");
//    }
//}


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static List<List<Character>> allTopologicalSorts(char[][] graph) {
        List<List<Character>> allSorts = new ArrayList<>();
        List<Character> sort = new ArrayList<>();
        boolean[] visited = new boolean[graph.length];
        Map<Character, List<Character>> adjacencyList = new HashMap<>();

        // Constructing the adjacency list
        for (char[] chars : graph) {
            char vertex = chars[0];
            List<Character> neighbors = new ArrayList<>();
            for (int i = 1; i < chars.length; i++) {
                neighbors.add(chars[i]);
            }
            adjacencyList.put(vertex, neighbors);
            visited[vertex - 'A'] = false;
        }

        allTopologicalSortsUtil(adjacencyList, visited, sort, allSorts);
        return allSorts;
    }

    private static void allTopologicalSortsUtil(Map<Character, List<Character>> adjacencyList,
                                                boolean[] visited, List<Character> sort,
                                                List<List<Character>> allSorts) {
        boolean flag = false;
        for (Map.Entry<Character, List<Character>> entry : adjacencyList.entrySet()) {
            char vertex = entry.getKey();
            List<Character> neighbors = entry.getValue();
            if (!visited[vertex - 'A'] && neighbors.isEmpty()) {
                visited[vertex - 'A'] = true;
                sort.add(vertex);
                for (Map.Entry<Character, List<Character>> e : adjacencyList.entrySet()) {
                    char neighborVertex = e.getKey();
                    List<Character> neighborList = e.getValue();
                    if (neighborList.contains(vertex)) {
                        neighborList.remove(Character.valueOf(vertex));
                    }
                }
                allTopologicalSortsUtil(adjacencyList, visited, sort, allSorts);
                visited[vertex - 'A'] = false;
                sort.remove(sort.size() - 1);
                for (Map.Entry<Character, List<Character>> e : adjacencyList.entrySet()) {
                    char neighborVertex = e.getKey();
                    List<Character> neighborList = e.getValue();
                    if (!neighborList.contains(vertex)) {
                        neighborList.add(vertex);
                    }
                }
                flag = true;
            }
        }
        if (!flag) {
            allSorts.add(new ArrayList<>(sort));
        }
    }

    public static void main(String[] args) {
        // Example graph represented as an adjacency list
        char[][] graph = {
                {'A'},     // A -> B, A -> C
                {'B', 'D', 'E', 'A', 'C'},           // B -> D
                {'C', 'D'},           // C -> D
                {'D', 'H'},           // D -> E
                {'E'},           // E -> F
                {'F', 'D', 'E'},           // F -> G
                {'G', 'H', 'F', 'E'},           // G -> H
                {'H'}                 // H (no outgoing edges)
        };

        List<List<Character>> allSorts = allTopologicalSorts(graph);
        for (List<Character> sort : allSorts) {
            System.out.println(sort);
        }
    }
}
