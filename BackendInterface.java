import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
public interface BackendInterface {
   /* Constructor
    public BackendClass(GraphADT graph) {
        this.graph = graph;
    }
    */

   /**
    * Read graph data from a file and initialize the graph.
    *
    * @param filename the name of the file containing graph data.
    */
   void readDataFromFile(String filename) throws FileNotFoundException;


   /**
    * Find the shortest path between a start building and a destination building.
    *
    * @param start   the name of the starting building.
    * @param end     the name of the destination building.
    * @return an object representing the shortest path.
    */
    ShortestPathInterface findShortestPath(String start, String end) throws NoSuchElementException;


   /**
    * Get statistics about the graph dataset.
    *
    * @return a string with information such as the number of nodes (buildings),
    * the number of edges, and the total walking time (sum of weights) for all edges in the graph.
    */
   String getDatasetStatistics();
}
