/*
 * MiniMax.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the MiniMax
*/

import java.util.LinkedList;
import java.util.Random;

public class MiniMax
{
    private int maxDepth; //Declare the variable that has the maximum depth of the minimax
    private int AILetter; //Declare the variable that has the letter of the AI

    //Declare the constructor
    public MiniMax(int AILetter, MenuManager menuManager)
    {
        //Setup depth depending on difficulty
        if(menuManager.getDifficulty() == 2)
        {
            maxDepth = 3;
        }
        else if(menuManager.getDifficulty() == 3)
        {
            maxDepth = 6;
        }

        this.AILetter = AILetter;
    }

    //Getters

    /*
     * This method returns the value of the variable "maxDepth"
     * @param Nothing.
     * @return maxDepth
    */
    public int getMaxDepth()
    {
        return maxDepth;
    }

    /*
     * This method returns the value of the variable "AILetter"
     * @param Nothing.
     * @return AILetter
    */
    public int getAILetter()
    {
        return AILetter;
    }

    //Setters

    /*
     * This method sets the value of the variable "maxDepth"
     * @param maxDepth
     * @return Nothing.
    */
    public void setMaxDepth(int maxDepth)
    {
        this.maxDepth = maxDepth;
    }

    /*
     * This method sets the value of the variable "AILetter"
     * @param AILetter
     * @return Nothing.
    */
    public void setAILetter(int AILetter)
    {
        this.AILetter = AILetter;
    }

    //Helpers

    /*
     * This method returns the best move
     * @param board
     * @return Nothing.
    */
    public SimulationGameplay getNextMove(SimulationGame board)
    {
        return max(board.boardWithExpansion(board), 0); //Take the lowest of its recursive generated values in order to choose the greatest
    }

    /*
     * The max and min methods are called interchangingly, one after another until a max depth is reached
     * @param board, depth
     * @return Nothing.
    */
    public SimulationGameplay min(SimulationGame board, int depth)
    {
        Random r = new Random();

        // If min is called on a state that is terminal or after a maximum depth is reached, then a heuristic is calculated on the state
        if(board.checkGameOver() || depth == maxDepth)
        {
            SimulationGameplay baseMove = new SimulationGameplay();
            baseMove = baseMove.possibleMove(board.lastMove.getRow(), board.lastMove.getCol(), board.utilityFunction());
            return baseMove;
        }
        else
        {
            //The children-moves of the state are calculated
            LinkedList<SimulationGame> children = new LinkedList<SimulationGame>(board.getChildren(SimulationGame.X));
            SimulationGameplay minMove = new SimulationGameplay();
            minMove = minMove.moveToCompare(Integer.MAX_VALUE);

            for (int i = 0; i < children.size(); i++)
            {
                SimulationGame child = children.get(i);
                SimulationGameplay move = max(child, depth + 1);
                if(move.getValue() <= minMove.getValue()) //If the lowest value is selected
                {
                    if (move.getValue() == minMove.getValue()) //If the heuristic has the same value then randomly choose one of the two moves
                    {
                        if (r.nextInt(2) == 0) //If 0, rewrite
                        {
                            minMove.setRow(child.lastMove.getRow());
                            minMove.setCol(child.lastMove.getCol());
                            minMove.setValue(move.getValue());
                        }
                    }
                    else //Is the same
                    {
                        minMove.setRow(child.lastMove.getRow());
                        minMove.setCol(child.lastMove.getCol());
                        minMove.setValue(move.getValue());
                    }
                }
            }
            return minMove;
        }
    }

    /*
     * The max and min methods are called interchangingly, one after another until a max depth is reached
     * @param board, depth
     * @return Nothing.
    */
    public SimulationGameplay max(SimulationGame board, int depth)
    {
        Random r = new Random();

        //If max is called on a state that is terminal or after a maximum depth is reached, then a heuristic is calculated on the state
        if(board.checkGameOver() || depth == maxDepth)
        {
            SimulationGameplay baseMove = new SimulationGameplay();
            baseMove = baseMove.possibleMove(board.lastMove.getRow(), board.lastMove.getCol(), board.utilityFunction());
            return baseMove;
        }
        else
        {
            //The children-moves of the state are calculated
            LinkedList<SimulationGame> children = new LinkedList<SimulationGame>(board.getChildren(AILetter));
            SimulationGameplay maxMove = new SimulationGameplay();
            maxMove = maxMove.moveToCompare(Integer.MIN_VALUE);

            for (int i = 0; i < children.size(); i++)
            {
                SimulationGame child = children.get(i);
                SimulationGameplay move = min(child, depth + 1);
                if(move.getValue() >= maxMove.getValue()) //If the greatest value is selected
                {
                    if (move.getValue() == maxMove.getValue()) //If the heuristic has the same value then randomly choose one of the two moves
                    {
                        if (r.nextInt(2) == 0) //If 0, rewrite
                        {
                            maxMove.setRow(child.lastMove.getRow());
                            maxMove.setCol(child.lastMove.getCol());
                            maxMove.setValue(move.getValue());
                        }
                    }
                    else //Is the same
                    {
                        maxMove.setRow(child.lastMove.getRow());
                        maxMove.setCol(child.lastMove.getCol());
                        maxMove.setValue(move.getValue());
                    }
                }
            }
            return maxMove;
        }
    }
}