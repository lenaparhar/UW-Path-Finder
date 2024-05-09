import java.io.File;
import java.util.Scanner;

public interface FrontendInterface<T extends Comparable<T>> {

  // Constructor to accept a reference to the backend and a Scanner for user input
  // public IndividualFrontendInterface(BackendInterface backend, Scanner scanner);
    // Display main menu
    public void displayMainMenu();
    
  // Start the main command loop for the user interface
  public void startCommandLoop();

  // Command to specify and load a data file
  public void loadFile(File file);

  // Command to show statistics about dataset
  public String showData();

  // Prompt the user for specific search criteria (e.g., start and end destination building)
  public String promptForShortestPath(String start, String end);

  // Command to exit the application
  public void exitApp();
}
