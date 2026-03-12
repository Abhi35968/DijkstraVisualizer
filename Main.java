public class Main {

    public static void main(String[] args) {

        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");

        Graph graph = new Graph();

        graph.addNode(A);
        graph.addNode(B);
        graph.addNode(C);

        graph.addEdge(A,B,4);
        graph.addEdge(A,C,2);
        graph.addEdge(C,B,1);

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
        dijkstra.run(graph, A);

        System.out.println("Distance to B: " + B.distance);
        System.out.println("Distance to C: " + C.distance);
    }
}