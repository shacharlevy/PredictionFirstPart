package ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ui.menu.Menu;
import ui.menu.MenuItem;
import ui.menu.MenuOptions;
import facade.Facade;

public class PredictionConsuleUI {

    private final Menu mainMenu;
    private String currentLoadedPathString;
    private Facade facade = new Facade();
    public PredictionConsuleUI(){
        this.currentLoadedPathString = "no path loaded";
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem ("Load System info from XML","Please enter the full path to the XML file:", null));
        menuItems.add(new MenuItem ("Show Simulation info", null, null));
        menuItems.add(new MenuItem ("Activate Simulation", null, null));
        menuItems.add(new MenuItem ("Show Full Past Simulation info", null, null));
        menuItems.add(new MenuItem ("Exit", null, null));
        mainMenu = new Menu(menuItems);
    }

    public void Run(){
        Scanner scanner = new Scanner(System.in);
        MenuOptions userChoice = null;
        while(userChoice != MenuOptions.EXIT){
            System.out.println(mainMenu.toString());
            userChoice = MenuOptions.getChoice(scanner.nextInt());
            switch(userChoice){
                case LOAD_WORLD_XML:
                    System.out.println(this.mainMenu.getMenuItemInstructions(userChoice.ordinal()));
                    this.currentLoadedPathString = scanner.next();
                    try {
                        this.facade.loadXML(this.currentLoadedPathString);
                        System.out.println("XML file loaded successfully\n");
                    } catch (FileNotFoundException e) {
                        System.out.println("Error loading XML file: " + e.getMessage() + "\n");
                    } catch (Exception e) {
                        System.out.println("Error loading XML file: " + e.getMessage() + "\n");
                    }
                    break;
                case SHOW_SIMULATION_DETAILS:
                    String simulationDetails = this.facade.getSimulationDetails();
                    System.out.println(simulationDetails);
                    break;
                case ACTIVATE_SIMULATION:
                    System.out.println("Simulation activated");
                    this.facade.activateSimulation();
                    break;
                case FULL_PAST_SIMULATION_DETAILS:
                    System.out.println("Current loaded path: " + this.currentLoadedPathString);
                    break;
                case EXIT:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }


}