/*
 * Player.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the players
*/

public class Player
{
    //Declare a reference to the other Managers
    private MenuManager menuManager;
    private GameManager gameManager;

    private String name; //Declare the variable that has the name of the player
    private int age; //Declare the variable that has the age of the player
    private String color; //Declare the variable that has the color of the player
    private boolean imAI; //Declare the variable that has if it is an AI
    private boolean canMove; //Declare the variable that has if the player can move a token
    private int columnToPut = -1; //Declare the variable that has the column that the AI wants to use

    //Declare the constructor
    public Player(String name, int age, String color, boolean imAI, MenuManager menuManager, GameManager gameManager)
    {
        this.name = name;
        this.age = age;
        this.color = color;
        this.imAI = imAI;
        this.menuManager = menuManager;
        this.gameManager = gameManager;
    }

    //Getters
    
    /*
     * This method returns the value of the variable "name"
     * @param Nothing.
     * @return name
    */
    public String getName()
    {
        return name;
    }

    /*
     * This method returns the value of the variable "age"
     * @param Nothing.
     * @return age
    */
    public int getAge()
    {
        return age;
    }

    /*
     * This method returns the value of the variable "color"
     * @param Nothing.
     * @return color
    */
    public String getColor()
    {
        return color;
    }

    /*
     * This method returns the value of the variable "imAI"
     * @param Nothing.
     * @return imAI
    */
    public boolean getImAI()
    {
        return imAI;
    }

    /*
     * This method returns the value of the variable "canMove"
     * @param Nothing.
     * @return canMove
    */
    public boolean getCanMove()
    {
        return canMove;
    }

    /*
     * This method returns the value of the variable "columnToPut"
     * @param Nothing.
     * @return columnToPut
    */
    public int getColumnToPut()
    {
        return columnToPut;
    }

    //Setters

    /*
     * This method sets the value of the variable "name"
     * @param name
     * @return Nothing.
    */
    public void setName(String name)
    {
        this.name = name;
    }

    /*
     * This method sets the value of the variable "age"
     * @param age
     * @return Nothing.
    */
    public void setAge(int age)
    {
        this.age = age;
    }

    /*
     * This method sets the value of the variable "color"
     * @param color
     * @return Nothing.
    */
    public void setColor(String color)
    {
        this.color = color;
    }

    /*
     * This method sets the value of the variable "imAI"
     * @param imAI
     * @return Nothing.
    */
    public void setImAI(boolean imAI)
    {
        this.imAI = imAI;
    }

    /*
     * This method sets the value of the variable "canMove"
     * @param canMove
     * @return Nothing.
    */
    public void setCanMove(boolean canMove)
    {
        this.canMove = canMove;
    }

    /*
     * This method sets the value of the variable "columnToPut"
     * @param columnToPut
     * @return Nothing.
    */
    public void setColumnToPut(int columnToPut)
    {
        this.columnToPut = columnToPut;
    }

    //Helpers

    /*
     * This method generates the AI decision
     * @param Nothing.
     * @return Nothing.
    */
    public void makeDecision()
    {
        if(menuManager.getDifficulty() == 1) //If the difficulty is 1, put a token anywhere
        {
            setColumnToPut(easyRecursion());
        }
        else
        {
            //If the difficulty is not 1, generate a game with MiniMax and see the possible best moves
            SimulationGameplay aiMove = gameManager.miniMaxAI.getNextMove(gameManager.simulatedBoard);
            
            if(menuManager.getGameMode() == 3 && gameManager.getTurn() == 0)
            {
                gameManager.simulatedBoard.makeMove(aiMove.getCol(), SimulationGame.X);
            }
            else
            {
                gameManager.simulatedBoard.makeMove(aiMove.getCol(), SimulationGame.O);
            }

            setColumnToPut(aiMove.getCol());
        }
    }

    /*
     * This method uses recursion to choose a random column that is not full
     * @param Nothing.
     * @return random
    */
    public int easyRecursion()
    {
        int random = (int)Math.floor(Math.random() * (6 - 0 + 1) + 0);
        if(!gameManager.getMainBoard().getBoardIsFull())
        {
            if(gameManager.getMainBoard().checkIfColIsFull(random))
            {
                return easyRecursion();
            }
            
            return random;
        }
        
        return 0;
    }
}