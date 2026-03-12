import java.util.*;

public class Graph {

    Map<Node, List<Edge>> adjList = new HashMap<>();

    public void addNode(Node node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node source, Node target, int weight) {
        adjList.get(source).add(new Edge(target, weight));
    }

    public List<Edge> getNeighbors(Node node) {
        return adjList.get(node);
    }
}