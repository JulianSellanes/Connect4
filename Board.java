/*
 * Board.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the board
*/

public class Board
{
    private int ROW; //Declare the variable that has the number of rows
    private int COL; //Declare the variable that has the number of columns
    private Hole[][] board; //Declare the 2D array
    private boolean boardIsFull; //Declare the variable that has if the board is full

    //Declare the constructor
    public Board(int ROW, int COL)
    {
        this.ROW = ROW;
        this.COL = COL;
    }

    //Getters

    /*
     * This method returns the value of the variable "ROW"
     * @param Nothing.
     * @return ROW
    */
    public int getROW()
    {
        return ROW;
    }

    /*
     * This method returns the value of the variable "COL"
     * @param Nothing.
     * @return COL
    */
    public int getCOL()
    {
        return COL;
    }

    /*
     * This method returns the value of the variable "board"
     * @param Nothing.
     * @return board[][]
    */
    public Hole[][] getBoard()
    {
        return board;
    }

    /*
     * This method returns the value of the variable "boardIsFull"
     * @param Nothing.
     * @return boardIsFull
    */
    public boolean getBoardIsFull()
    {
        return boardIsFull;
    }

    //Setters

    /*
     * This method sets the value of the variable "board"
     * @param board[][]
     * @return Nothing.
    */
    public void setBoard(Hole[][] board)
    {
        this.board = board;
    }

    /*
     * This method sets the value of the variable "boardIsFull"
     * @param boardIsFull
     * @return Nothing.
    */
    public void setBoardIsFull(boolean boardIsFull)
    {
        this.boardIsFull = boardIsFull;
    }

    //Helpers
    
    /*
     * This method restarts the board
     * @param Nothing.
     * @return Nothing.
    */
    public void resetBoard()
    {
        if(getBoard() == null) //Check if the board exists
            return;

        for (int r = 0; r < ROW; r++)
        {
            for(int c = 0; c < COL; c++)
            {
                getBoard()[r][c] = null; //Assign null values
            }
        }
    }

    /*
     * This method generates a new board
     * @param Nothing.
     * @return Nothing.
    */
    public void generateBoard()
    {
        Hole[][] newBoard = new Hole[ROW][COL]; //Declare a new board

        for (int r = 0; r < ROW; r++)
        {
            for(int c = 0; c < COL; c++)
            {
                newBoard[r][c] = new Hole("None"); //Create the Hole objects, and assign them to the board
            }
        }
        
        setBoard(newBoard); //Assigns the new board to the variable "board"
        setBoardIsFull(false); //Assigns a false value to the variable "boardIsFull"
    }

    /*
     * This method sets a new value to the variable "boardIsFull" depending if the board is full or not
     * @param Nothing.
     * @return Nothing.
    */
    public void checkIfBoardIsFull()
    {
        int check = 0;

        for(int c = 0; c < COL; c++)
        {
            if(!getBoard()[0][c].getColor().equals("None"))
            {
                check++;
            }
        }

        if(check >= 7)
        {
            setBoardIsFull(true);
        }
        else
        {
            setBoardIsFull(false);
        }
    }
    
    /*
     * This method returns if certain column is full
     * @param c
     * @return boolean
    */
    public boolean checkIfColIsFull(int c)
    {
        if(!getBoardIsFull() && getBoard()[0][c].getColor().equals("None"))
            return false;
      
        return true;
    }
}