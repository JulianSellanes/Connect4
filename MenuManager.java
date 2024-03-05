/*
 * MenuManager.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the menus
*/

public class MenuManager
{
    private int gameMode; //Declare the variable that has the reference to the gamemode
    private int difficulty; //Declare the variable that has the reference to the difficulty
    private int startingPlayer; //Declare the variable that has the reference to the starting player
    
    //Declare the constructor
    public MenuManager()
    {
        resetMenuManager(); //Restart Manager
    }

    //Getters

    /*
     * This method returns the value of the variable "gameMode"
     * @param Nothing.
     * @return gameMode
    */
    public int getGameMode()
    {
        return gameMode;
    }

    /*
     * This method returns the value of the variable "difficulty"
     * @param Nothing.
     * @return difficulty
    */
    public int getDifficulty()
    {
        return difficulty;
    }

    /*
     * This method returns the value of the variable "startingPlayer"
     * @param Nothing.
     * @return startingPlayer
    */
    public int getStartingPlayer()
    {
        return startingPlayer;
    }

    //Setters

    /*
     * This method sets the value of the variable "gameMode"
     * @param gameMode
     * @return Nothing.
    */
    public void setGameMode(int gameMode)
    {
        this.gameMode = gameMode;
    }

    /*
     * This method sets the value of the variable "difficulty"
     * @param difficulty
     * @return Nothing.
    */
    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }

    /*
     * This method sets the value of the variable "startingPlayer"
     * @param startingPlayer
     * @return Nothing.
    */
    public void setStartingPlayer(int startingPlayer)
    {
        this.startingPlayer = startingPlayer;
    }

    //Helpers

    /*
     * This method resets the MenuManager variables
     * @param Nothing.
     * @return Nothing.
    */
    public void resetMenuManager()
    {
        setGameMode(-1);
        setDifficulty(-1);
        setStartingPlayer(-1);
    }
}