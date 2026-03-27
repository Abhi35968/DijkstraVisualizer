# Dijkstra Visualizer

An interactive JavaFX playground for experimenting with Dijkstra's shortest-path algorithm. The app lets you sketch a weighted graph by clicking in the canvas, assign custom edge weights, and then animate how Dijkstra relaxes edges and recovers the minimum-cost tree.

## Feature Highlights

- **Click-to-build graphs** – Left-click anywhere on the canvas to create a node. Select two nodes in succession to draw an undirected edge; you'll be prompted for the weight via a JavaFX `TextInputDialog`.
- **Live data model** – Each node is stored as a JavaFX `Circle` tracked in memory, while adjacency lists reside in a `Map<Circle, List<Edge>>` (see [VisualizerUI.java](VisualizerUI.java)).
- **One-button simulation** – Hit **Run Dijkstra** to execute the classic algorithm starting from the first node that was created. Nodes change color as they are dequeued, and the final shortest-path tree is highlighted in red.
- **No external build system** – The repo ships with the JavaFX 21 SDK for local experiments. You can compile and run with a single `javac` / `java` invocation.

## Requirements

- JDK 21 (or newer) with JavaFX support
- JavaFX SDK 21.0.10 (already vendored in `javafx-sdk-21.0.10/`)
- Windows/macOS/Linux capable of running JavaFX desktop apps

## Running the Visualizer

1. Open a terminal inside `DijkstraVisualizer/` (the folder containing `VisualizerUI.java`).
2. Compile the JavaFX application:
	```bash
	javac --module-path javafx-sdk-21.0.10/lib --add-modules javafx.controls VisualizerUI.java
	```
3. Launch the simulator:
	```bash
	java --module-path javafx-sdk-21.0.10/lib --add-modules javafx.controls VisualizerUI
	```

> Tip: If you're using an IDE (IntelliJ IDEA, VS Code, Eclipse), add the JavaFX SDK directory as a library/module dependency instead of invoking `javac` manually.

## Using the App

1. **Add nodes** – Click any empty spot to drop a blue node.
2. **Connect nodes** – Click one node (it turns yellow), then click another node to open the weight dialog. Enter an integer weight to draw a bidirectional edge labelled with that weight.
3. **Run the algorithm** – Press **Run Dijkstra**. The first-created node is treated as the source. As the algorithm progresses, dequeued nodes flash orange.
4. **Inspect the result** – Edges that belong to the computed shortest-path tree are recolored red and thickened for clarity.

You can continue adding nodes and edges after a run; simply click existing nodes again to create more connections and rerun the algorithm.

## Implementation Notes

- The entire UI and algorithm logic lives in [VisualizerUI.java](VisualizerUI.java), which subclasses `javafx.application.Application`.
- `Edge` is defined as an inner class that couples the target node, weight, and the `Line` drawn on screen so that styling updates propagate to the canvas instantly.
- Dijkstra's algorithm uses a `PriorityQueue<Circle>` keyed by the `dist` map and records back-pointers in `prev`. After the queue empties, the code walks the `prev` map to recolor the winning edges.
- Paths are computed from the first node added; tweak `runDijkstra()` if you want to let the user choose a source node dynamically.

## Possible Enhancements

1. Allow users to pick a source and destination node, then highlight only the requested path.
2. Display the running distance table or keep a log/animation of relaxations for teaching purposes.
3. Persist graphs (JSON export/import) so complex layouts can be saved.
4. Add directed edges, negative-weight detection, or alternative algorithms (A*, Bellman-Ford).

---

Made with JavaFX and a love for graph theory. Contributions and suggestions are welcome!