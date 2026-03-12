import java.util.*;

public class DijkstraAlgorithm {

    public void run(Graph graph, Node start) {

        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));

        start.distance = 0;
        pq.add(start);

        while (!pq.isEmpty()) {

            Node current = pq.poll();

            if (current.visited)
                continue;

            current.visited = true;

            for (Edge edge : graph.getNeighbors(current)) {

                Node neighbor = edge.target;
                int newDist = current.distance + edge.weight;

                if (newDist < neighbor.distance) {
                    neighbor.distance = newDist;
                    pq.add(neighbor);
                }
            }
        }
    }
}