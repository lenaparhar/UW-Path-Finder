import java.util.ArrayList;
import java.util.List;

public class ShortestPath implements ShortestPathInterface, Comparable<ShortestPath> {
    private String start; // path starting point
    private String end; // path ending point
    private List<String> buildings;
    private List<Double> walkingTimes;
    private double totalPathCost;

 
    /**
     * Constructor for a shortestPath.
     * @param start the building the path starts at
     * @param end the buildign the path ends at
     * @param totalPathCost the cost from the start node to the end node
     * 
     */
    public ShortestPath(String start, String end, double totalPathCost) {
        this.start = start;
        this.end = end;
        walkingTimes = new ArrayList<Double>();
        buildings = new ArrayList<String>();
        buildings.add(start);
        walkingTimes.add(totalPathCost);
        this.totalPathCost = totalPathCost;
    }
    
    /**
     * Getter method for the list of buildings along the path
     */
    @Override
    public List<String> getBuildings(){
        return buildings;
    }

    /**
     * Getter method for a list of the walking times of the path segments (the time it takes to walk from one building to the next), and

     */
    @Override
    public List<Double> getWalkingTimes() {
        return walkingTimes;
    }

    /**
     * Getter method for the total path cost as the estimated time it takes to walk from the start to the destination building.
     */
    @Override
    public double getTotalPathCost() {
        return totalPathCost;
    }


    @Override
    public int compareTo(ShortestPath o) {
        return Double.compare(this.totalPathCost, o.totalPathCost);
    }



}
