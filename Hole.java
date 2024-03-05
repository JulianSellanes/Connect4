/*
 * Hole.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class contains the information of the Hole objects, which are used in the board
*/

public class Hole
{
    //Declare variables
    private String color;
  
    //Declare the constructor
    public Hole(String color)
    {
        this.color = color;
    }
    
    //Getters

    /*
     * This method returns the value of the variable "color"
     * @param Nothing.
     * @return color
    */
    public String getColor()
    {
        return color;
    }
    
    //Setters
    
    /*
     * This method sets the value of the variable "color"
     * @param color
     * @return Nothing.
    */
    public void setColor(String color)
    {
        this.color = color;
    }
}