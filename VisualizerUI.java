import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class VisualizerUI extends Application {

    Pane root = new Pane();

    List<Circle> nodes = new ArrayList<>();
    Map<Circle, List<Edge>> graph = new HashMap<>();

    Circle selectedNode = null;

    class Edge {
        Circle target;
        int weight;
        Line line;

        Edge(Circle t, int w, Line l) {
            target = t;
            weight = w;
            line = l;
        }
    }

    @Override
    public void start(Stage stage) {

        Button runBtn = new Button("Run Dijkstra");
        runBtn.setLayoutX(20);
        runBtn.setLayoutY(20);

        runBtn.setOnAction(e -> runDijkstra());

        root.getChildren().add(runBtn);

        // Click to create node
        root.setOnMouseClicked(e -> {

            if (e.getTarget() instanceof Circle)
                return;

            Circle node = new Circle(e.getX(), e.getY(), 15, Color.BLUE);

            nodes.add(node);
            graph.put(node, new ArrayList<>());

            enableNodeInteraction(node);

            root.getChildren().add(node);

        });

        Scene scene = new Scene(root, 700, 500);

        stage.setTitle("Dijkstra Visualizer");
        stage.setScene(scene);
        stage.show();
    }

    void enableNodeInteraction(Circle node) {

        node.setOnMouseClicked(e -> {

            if (selectedNode == null) {
                selectedNode = node;
                node.setFill(Color.YELLOW);
            }

            else {

                Circle target = node;

                TextInputDialog dialog = new TextInputDialog();
                dialog.setHeaderText("Enter Edge Weight");

                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {

                    int weight = Integer.parseInt(result.get());

                    Line line = new Line(
                            selectedNode.getCenterX(),
                            selectedNode.getCenterY(),
                            target.getCenterX(),
                            target.getCenterY());

                    Text w = new Text(
                            (selectedNode.getCenterX() + target.getCenterX()) / 2,
                            (selectedNode.getCenterY() + target.getCenterY()) / 2,
                            String.valueOf(weight));

                    root.getChildren().addAll(line, w);

                    graph.get(selectedNode).add(new Edge(target, weight, line));
                    graph.get(target).add(new Edge(selectedNode, weight, line));
                }

                selectedNode.setFill(Color.BLUE);
                selectedNode = null;
            }

            e.consume();
        });
    }

    void runDijkstra() {

        if (nodes.isEmpty())
            return;

        Circle start = nodes.get(0);

        Map<Circle, Integer> dist = new HashMap<>();
        Map<Circle, Circle> prev = new HashMap<>();

        for (Circle n : nodes)
            dist.put(n, Integer.MAX_VALUE);

        dist.put(start, 0);

        PriorityQueue<Circle> pq = new PriorityQueue<>(
                Comparator.comparingInt(dist::get));

        pq.add(start);

        while (!pq.isEmpty()) {

            Circle current = pq.poll();

            current.setFill(Color.ORANGE);

            for (Edge edge : graph.get(current)) {

                Circle neighbor = edge.target;

                int newDist = dist.get(current) + edge.weight;

                if (newDist < dist.get(neighbor)) {

                    dist.put(neighbor, newDist);
                    prev.put(neighbor, current);

                    pq.add(neighbor);
                }
            }
        }

        // highlight shortest path to last node
        for (Circle node : prev.keySet()) {

            Circle parent = prev.get(node);

            for (Edge e : graph.get(parent)) {

                if (e.target == node) {

                    e.line.setStroke(Color.RED);
                    e.line.setStrokeWidth(4);

                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}