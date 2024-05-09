import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Backend<NodeType, EdgeType extends Number> implements BackendInterface {

    private GraphADT<String, Double> graph; // graph that passed in by the user
    private double totalWalkingTime; // total walking time across all paths in the graph

    /**
     * main method for the backend class
     */
    public static void main(String[] args) {
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Scanner scnr = new Scanner(System.in);
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend, scnr);
        frontend.startCommandLoop();
    }

    /**
     * Constructor for backendClass objects
     * 
     * @param graph GraphADT object
     */
    public Backend(GraphADT<String, Double> graph) {
        this.graph = graph;
        this.totalWalkingTime = 0.0;
    }

    /**
     * Read graph data from a file and initialize the graph.
     * 
     * @param filename the name of the file containing graph data.
     * @throws FileNotFoundException when the file isn't found
     */
    public void readDataFromFile(String filename) throws FileNotFoundException {
        try {
            FileReader fileReader = new FileReader(filename);
            Scanner scnr = new Scanner(fileReader);

            if (scnr.hasNextLine()) {
                scnr.nextLine(); // skips line at the top that says graph campus{
            }

            Pattern pattern = Pattern.compile("\"(.*?)\" -- \"(.*?)\" \\[seconds=(\\d+\\.?\\d*)\\];");

            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();

                // Check if the line matches the pattern
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) { // if pattern is found on the line add the data to the graph and update the
                                      // total walking time
                    String start = matcher.group(1);
                    String end = matcher.group(2);
                    double time = Double.parseDouble(matcher.group(3));

                    graph.insertNode(start);
                    graph.insertNode(end);

                    // if it does not contain an edge (undirected) between the start/end, update the
                    // total walking time in the graph.
                    // don't add duplicate edges into the graph
                    if (!graph.containsEdge(start, end) || !graph.containsEdge(end, start)) {
                        totalWalkingTime += time;

                        graph.insertEdge(start, end, Double.valueOf(time));
                        // add the oppsite direction since the graph is undirected
                        graph.insertEdge(end, start, Double.valueOf(time));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(filename + " Not found");
        }
    }

    /**
     * Find the shortest path between a start building and a destination building.
     * 
     * @param start the name of the starting building.
     * @param end   the name of the destination building.
     * @return an object representing the shortest path.
     */
    @Override
    public ShortestPathInterface findShortestPath(String start, String end) throws NoSuchElementException {
        List<String> pathData = graph.shortestPathData(start, end);
        ShortestPath shortestPath = new ShortestPath(start, end, graph.shortestPathCost(start, end));

        // Add nodes and walking times to the ShortestPath object
        for (int i = 1; i < pathData.size(); i++) {
            String current = pathData.get(i - 1);
            String next = pathData.get(i);
            double walkingTime = graph.getEdge(current, next).doubleValue();

            shortestPath.getBuildings().add(next);
            shortestPath.getWalkingTimes().add(walkingTime);

        }

        return shortestPath;

    }

    /**
     * Get statistics about the graph dataset.
     * 
     * @return a string with information such as the number of nodes (buildings),
     *         the number of edges, and the total walking time (sum of weights) for
     *         all edges in the graph.
     */
    public String getDatasetStatistics() {
        int numNodes = graph.getNodeCount();
        int numEdges = graph.getEdgeCount() / 2;

        String dataString = "Number of buildings: " + numNodes + " Number of Paths: " + numEdges
                + " Total Walking Time: " + totalWalkingTime + " seconds";

        return dataString;
    }

}
