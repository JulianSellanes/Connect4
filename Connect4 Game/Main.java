/*
 * Main.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class starts the program, and generates the Manager classes that will control the entire program.
*/

public class Main
{
    /*
     * This is the main method that starts the program
     * @param args. Unused
     * @return Nothing.
    */
    public static void main(String[] args)
    {
        //Initialize important Manager objects
        MenuManager menuManager = new MenuManager();
        GameManager gameManager = new GameManager(menuManager);
        FileManager fileManager = new FileManager(menuManager, gameManager);
        GUI gui = new GUI(menuManager, gameManager, fileManager);
        gui.MainMenu(); //Display main menu
    }
}