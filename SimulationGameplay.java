/*
 * SimulationGameplay.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the gameplay in the simulation
*/

public class SimulationGameplay
{	
    private int row;
    private int col;
    private int value;
    
    //Declare the constructor
    public SimulationGameplay()
    {
        row = -1;
        col = -1;
        value = 0;
    }

    //Getters

    /*
     * This method returns the value of the variable "row"
     * @param Nothing.
     * @return row
    */
    public int getRow()
    {
        return row;
    }

    /*
     * This method returns the value of the variable "col"
     * @param Nothing.
     * @return col
    */
    public int getCol()
    {
        return col;
    }

    /*
     * This method returns the value of the variable "value"
     * @param Nothing.
     * @return value
    */
    public int getValue()
    {
        return value;
    }

    //Setters

    /*
     * This method sets the value of the variable "row"
     * @param row
     * @return Nothing.
    */
    public void setRow(int row)
    {
        this.row = row;
    }

    /*
     * This method sets the value of the variable "col"
     * @param col
     * @return Nothing.
    */
    public void setCol(int col)
    {
        this.col = col;
    }

    /*
     * This method sets the value of the variable "value"
     * @param value
     * @return Nothing.
    */
    public void setValue(int value)
    {
        this.value = value;
    }

    //Helpers

    /*
     * This method checks for the move made
     * @param row, col
     * @return Nothing.
    */
    public SimulationGameplay moveDone(int row, int col)
    {
        SimulationGameplay moveDone = new SimulationGameplay();
        moveDone.row = row;
        moveDone.col = col;
        moveDone.value = -1;
        return moveDone;
    }
    
    /*
     * This method checks for the move for expansion
     * @param row, col, value
     * @return Nothing.
    */
    public SimulationGameplay possibleMove(int row, int col, int value)
    {
        SimulationGameplay posisibleMove = new SimulationGameplay();
        posisibleMove.row = row;
        posisibleMove.col = col;
        posisibleMove.value = value;
        return posisibleMove;
    }

    /*
     * This method checks for the move used to compare in MiniMax
     * @param value
     * @return Nothing.
    */
    public SimulationGameplay moveToCompare(int value)
    {
        SimulationGameplay moveToCompare = new SimulationGameplay();
        moveToCompare.row = -1;
        moveToCompare.col = -1;
        moveToCompare.value = value;
        return moveToCompare;
    }
}