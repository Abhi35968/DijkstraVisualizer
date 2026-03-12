public class Node {

    String name;
    int distance;
    boolean visited;

    public Node(String name) {
        this.name = name;
        this.distance = Integer.MAX_VALUE;
        this.visited = false;
    }

    public String getName() {
        return name;
    }
}