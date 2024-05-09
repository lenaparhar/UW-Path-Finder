import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class drives an interactive loop of prompting the user to select a
 * command, then requests
 * any required details about that command from the user, and then displays the
 * results of the
 * command.
 * 
 * @author Apryl Tran
 *
 */
public class Frontend implements FrontendInterface {
  private Backend backend;
  private Scanner scanner;
  private boolean running = true;

  /**
   * Constructor accepts a reference to the backend and a java.util.Scanner
   * instance to read user
   * input
   * 
   * @param backend placeholder input
   * @param scanner reads user input
   */
  public Frontend(Backend backend, Scanner scanner) {
    this.backend = backend;
    this.scanner = scanner;
  }

  /**
   * This method prompts user to select a command.
   */
  @Override
  public void displayMainMenu() {
    System.out.println("Select a command:");
    System.out.println("1. Load data file");
    System.out.println("2. Enter start and end destination");
    System.out.println("3. List statistics about data");
    System.out.println("4. Exit");

  }

  /**
   * This method starts the main command loop for the user and allows for user
   * input.
   */
  @Override
  public void startCommandLoop() {
    while (running) {
      displayMainMenu();
      String command = scanner.nextLine();
      switch (command) {
        case "1":
          System.out.println("Enter the path to the data file:");
          File file = new File(" ");
          loadFile(file);
          break;
        case "2":
          System.out.println("Enter start destination building:");
          String start = scanner.nextLine();
          System.out.println("Enter end destination building:");
          String end = scanner.nextLine();
          String listBuildings = promptForShortestPath(start, end);
          System.out.println("Buildings in the shortest path: " + listBuildings);

          break;
        case "3":
          System.out.println(showData());
          break;
        case "4":
          exitApp();
          running = false;
          break;
        default:
          System.out.println("Invalid command. Please enter a value 1 through 4.");
      }
    }

  }

  /**
   * This method loads a data file.
   */
  @Override
  public void loadFile(File file) {
    String fileName = scanner.nextLine();
    try {
      backend.readDataFromFile(fileName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method prompts the user for specific search criteria (e.g., start and
   * end destination
   * building)
   */
  @Override
  public String promptForShortestPath(String start, String end) {
    String temp = "";
    ShortestPathInterface shortestPath = backend.findShortestPath(start, end);
    for (int i = 0; i < shortestPath.getBuildings().size(); i++) {
      String s = shortestPath.getBuildings().get(i);
      temp = temp.concat(s);

      // Check if it's not the last element
      if (i < shortestPath.getBuildings().size() - 1) {
        temp = temp.concat(", ");
      }
    }

    temp = temp.concat(".\nTime to walk: " + shortestPath.getTotalPathCost() + " seconds.");

    return temp;

  }

  /**
   * This method show statistics about dataset.
   */
  @Override
  public String showData() {
    return backend.getDatasetStatistics();
  }

  /**
   * This method exits the application.
   */
  @Override
  public void exitApp() {
    System.out.println("Exiting the application. Goodbye!");

  }

}
