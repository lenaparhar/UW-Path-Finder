// --== CS400 File Header Information ==--                                                                                                                             
// Name: Lena Parhar                                                                                                                                                   
// Email: lparhar@wisc.edu                                                                                                                                             
// Group and Team: <your group name: A32                                                                                                                               
// Group TA: Nicholas Russell                                                                                                                                          
// Lecturer: Gary Dahl                                                                                                                                                 
// Notes to Grader:                                                                                                                                                    

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**                                                                                                                                                                    
 * This class extends the BaseGraph data structure with additional methods for                                                                                         
 * computing the total cost and list of node data along the shortest path                                                                                              
 * connecting a provided starting to ending nodes. This class makes use of                                                                                             
 * Dijkstra's shortest path algorithm.                                                                                                                                 
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {
  /**                                                                                                                                                                
     * While searching for the shortest path between two nodes, a SearchNode                                                                                           
     * contains data about one specific path between the start node and another                                                                                        
     * node in the graph. The final node in this path is stored in its node                                                                                            
     * field. The total cost of this path is stored in its cost field. And the                                                                                         
     * predecessor SearchNode within this path is referened by the predecessor                                                                                         
     * field (this field is null within the SearchNode containing the starting                                                                                         
     * node in its node field).                                                                                                                                        
     *                                                                                                                                                                 
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost                                                                                       
     * SearchNode has the highest priority within a java.util.PriorityQueue.                                                                                           
     */
   protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
   public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }
   /**                                                                                                                                                                
     * Constructor that sets the map that the graph uses.                                                                                                              
     *                                                                                                                                                                 
     * @param map the map that the graph uses to map a data object to the node                                                                                         
     *            object it is stored in                                                                                                                               
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }
   /**                                                                                                                                                                
     * This helper method creates a network of SearchNodes while computing the                                                                                         
     * shortest path between the provided start and end locations. The                                                                                                 
     * SearchNode that is returned by this method is represents the end of the                                                                                         
     * shortest path that is found: it's cost is the cost of that shortest path,                                                                                       
     * and the nodes linked together through predecessor references represent                                                                                          
     * all of the nodes along that shortest path (ordered from end to start).                                                                                          
     *                                                                                                                                                                 
     * @param start the data item in the starting node for the path                                                                                                    
     * @param end   the data item in the destination node for the path                                                                                                 
     * @return SearchNode for the final end node within the shortest path                                                                                              
     * @throws NoSuchElementException when no path from start to end is found                                                                                          
     *                                or when either start or end data do not                                                                                          
     *                                correspond to a graph node                                                                                                       
     */
 protected SearchNode computeShortestPath(NodeType start, NodeType end){
        Node startingNode = this.nodes.get(start);
        Node endingNode = this.nodes.get(end);
        PlaceholderMap<Node, Double> costs = new PlaceholderMap<>();
        PriorityQueue<SearchNode> pq = new PriorityQueue<>();

        SearchNode startNode = new SearchNode(startingNode, 0.0, null);
        costs.put(startingNode, 0.0);
        pq.offer(startNode);

        while (!pq.isEmpty()) {
            SearchNode curr = pq.poll();
            double currPathCost = curr.cost;
   // Determaine whether the edges leaving the current node leads to an unreachable node or creates a cheaper path to a destination.                                       
            for (Edge edge : curr.node.edgesLeaving){
                double edgeCost = edge.data.doubleValue();
                Node destination = edge.successor;
           // if we don't have a path to that node (ei, successor isn't key in map) or its > than what it would be                                                             
                if(!costs.containsKey(destination) || costs.get(destination) > currPathCost + edgeCost) {
                SearchNode newNode = new SearchNode(destination, edgeCost, curr);
                double newCost = currPathCost + (edge.data).doubleValue();

                // avoid IllegalArgumentException when adding duplicate keys into the map.                                                                                          
                if(costs.containsKey(destination)){
                    costs.remove(destination);
                    costs.put(destination, currPathCost + edgeCost);
                } else{
                    costs.put(destination, currPathCost + edgeCost);
                }

                // create the new path from the current node to the destination, and add it to the priority queue.                                                                  
                SearchNode successor = new SearchNode(destination, newCost, curr);
                pq.add(successor);
            }
      }

   // if the current node is the ending node, return it.                                                                                                                   
        if(curr.node.data.equals(endingNode.data)) {
            return curr;
        }
            }
        // if the end node was never reached, there is no path found.                                                                                                               
        throw new NoSuchElementException("No path found.");
    }


    /**                                                                                                                                                                             
     * Returns the list of data values from nodes along the shortest path                                                                                                           
     * from the node with the provided start value through the node with the                                                                                                        
     * provided end value. This list of data values starts with the start                                                                                                           
     * value, ends with the end value, and contains intermediary values in the                                                                                                      
     * order they are encountered while traversing this shorteset path. This                                                                                                        
     * method uses Dijkstra's shortest path algorithm to find this solution.                                                                                                        
     *                                                                                                                                                                              
     * @param start the data item in the starting node for the path                                                                                                                 
     * @param end   the data item in the destination node for the path                                                                                                              
     * @return list of data item from node along this shortest path                                                                                                                 
     */
public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    SearchNode endNode = computeShortestPath(start, end);
    List<NodeType> shortestPath = new ArrayList<>();
    SearchNode current = endNode;
    while (current != null) {
       shortestPath.add(0, current.node.data);
       current = current.predecessor;
    }
    return shortestPath;
}

  /**                                                                                                                                                                         
     * Returns the cost of the path (sum over edge weights) of the shortest                                                                                                         
     * path freom the node containing the start data to the node containing the                                                                                                     
     * end data. This method uses Dijkstra's shortest path algorithm to find                                                                                                        
     * this solution.                                                                                                                                                               
     *                                                                                                                                                                              
     * @param start the data item in the starting node for the path                                                                                                                 
     * @param end   the data item in the destination node for the path                                                                                                              
     * @return the cost of the shortest path between these nodes                                                                                                                    
     */
    public double shortestPathCost(NodeType start, NodeType end) {
    SearchNode endNode = computeShortestPath(start, end);
        return endNode.cost;
    }

}
