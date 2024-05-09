import java.util.List;


public interface ShortestPathInterface {
   /**
    * Get the list of buildings along the path.
    *
    * @return List of building names.
    */
   List<String> getBuildings();


   /**
    * Get the list of walking times between buildings.
    *
    * @return List of walking times.
    */
   List<Double> getWalkingTimes();


   /**
    * Get the total estimated time to walk the path.
    *
    * @return Total path cost.
    */
   double getTotalPathCost();
}
