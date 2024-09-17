/*
 * SimulationGame.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the board in the simulation
*/

import java.util.LinkedList;

public class SimulationGame
{
    //Declare a reference to the other Managers
    private MenuManager menuManager;

    static final int X = 1; //Declare player letter
    static final int O = -1; //Declare AI letter
    int EMPTY = 0; //Declare Blank space

    SimulationGameplay lastMove; //Declare the variable that has the last move made
    int lastLetterPlayed; //Declare the variable that has the last letter played
    int winner; //Declare the variable that has the winner
    int[][] gameBoard; //Declare the 2D array

    //Declare the constructor
    public SimulationGame(MenuManager menuManager)
    {
        this.menuManager = menuManager;

        lastMove = new SimulationGameplay();

        if(menuManager.getStartingPlayer() == 0)
        {
            lastLetterPlayed = O;
        }
        else
        {
            lastLetterPlayed = X;
        }

        winner = 0;
        gameBoard = new int[6][7];

        for(int r = 0; r < 6; r++)
        {
            for(int c = 0; c < 7; c++)
            {
                gameBoard[r][c] = EMPTY;
            }
        }
    }

    //Setters
	
    /*
     * This method sets the value of the variable "winner"
     * @param winner
     * @return Nothing.
    */
    public void setWinner(int winner)
    {
        this.winner = winner;
    }

    //Helpers

    /*
     * This method makes a movement based on a column and a player
     * @param col, letter
     * @return Nothing.
    */
    public void makeMove(int col, int letter)
    {
        lastMove = lastMove.moveDone(getRowPosition(col), col);
        gameBoard[getRowPosition(col)][col] = letter;
        lastLetterPlayed = letter;
    }
	
    /*
     * This method checks if a move is valid
     * @param col
     * @return boolean
    */
    public boolean isValidMove(int col)
    {
        int row = getRowPosition(col);

        if (row == -1 || col == -1 || row > 5 || col > 6)
        {
            return false;
        }

        if(gameBoard[row][col] != EMPTY)
        {
            return false;
        }

        return true;
    }
	
    /*
     * This method checks if it can move
     * @param row, col
     * @return boolean
    */
    public boolean canMove(int row, int col)
    {
        if (row <= -1 || col <= -1 || row > 5 || col > 6)
        {
            return false;
        }

        return true;
    }
	
    /*
     * This method checks if the column is full
     * @param col
     * @return boolean
    */
    public boolean checkFullColumn(int col)
    {
        if (gameBoard[0][col] == EMPTY)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
	
    /*
     * This method searches for a blank space to put the token
     * @param col
     * @return rowPosition
    */
    public int getRowPosition(int col)
    {
        int rowPosition = -1;

        for (int row=0; row<6; row++)
        {
            if (gameBoard[row][col] == EMPTY)
            {
                rowPosition = row;
            }
        }

        return rowPosition;
    }
    
    /*
     * This method expands the board
     * @param board
     * @return expansion
    */
    public SimulationGame boardWithExpansion(SimulationGame board)
    {
        SimulationGame expansion = new SimulationGame(menuManager);
        expansion.lastMove = board.lastMove;
        expansion.lastLetterPlayed = board.lastLetterPlayed;
        expansion.winner = board.winner;
        expansion.gameBoard = new int[6][7];

        for(int r = 0; r < 6; r++)
        {
            for(int c = 0; c < 7; c++)
            {
                expansion.gameBoard[r][c] = board.gameBoard[r][c];
            }
        }

        return expansion;
    }
	
    /*
     * This method generates the children of the state
     * @param letter
     * @return children
    */
    public LinkedList<SimulationGame> getChildren(int letter)
    {
        LinkedList<SimulationGame> children = new LinkedList<SimulationGame>();

        for(int c = 0; c < 7; c++)
        {
            if(isValidMove(c))
            {
                SimulationGame child = boardWithExpansion(this);
                child.makeMove(c, letter);
                children.add(child);
            }
        }

        return children;
    }
	
    /*
     * This method assigns the score depending on the movement
     * @param Nothing.
     * @return int
    */
    public int utilityFunction()
    {
        int Xlines = 0;
        int Olines = 0;

        if (checkWinState())
        {
            if(winner == X)
            {
                Xlines = Xlines + 90;
            }
            else
            {
                Olines = Olines + 90;
            }
        }	
        Xlines = Xlines + check3In(X) * 10 + check2In(X) * 4;
        Olines = Olines + check3In(O) * 5 + check2In(O);

	    return Olines - Xlines;
    }
	
    /*
     * This method checks for a winning move
     * @param Nothing.
     * @return boolean
    */
    public boolean checkWinState()
    {
        //4 in row
        for (int i = 5; i >= 0; i--)
        {
            for (int j = 0; j < 4; j++)
            {
                if (gameBoard[i][j] == gameBoard[i][j+1] && gameBoard[i][j] == gameBoard[i][j+2] && gameBoard[i][j] == gameBoard[i][j+3] && gameBoard[i][j] != EMPTY)
                {
                    setWinner(gameBoard[i][j]);
                    return true;
                }
            }
        }
		
        //4 in column
        for (int i = 5; i >= 3; i--)
        {
            for (int j = 0; j < 7; j++)
            {
                if (gameBoard[i][j] == gameBoard[i-1][j] && gameBoard[i][j] == gameBoard[i-2][j] && gameBoard[i][j] == gameBoard[i-3][j] && gameBoard[i][j] != EMPTY)
                {
                    setWinner(gameBoard[i][j]);
                    return true;
                }
            }
        }
		
        //Ascendent 4 in diagonal
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (gameBoard[i][j] == gameBoard[i+1][j+1] && gameBoard[i][j] == gameBoard[i+2][j+2] && gameBoard[i][j] == gameBoard[i+3][j+3] && gameBoard[i][j] != EMPTY)
                {
                    setWinner(gameBoard[i][j]);
                    return true;
                }
            }
        }
		
        //Descendent 4 in diagonal
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (canMove(i - 3, j + 3))
                {
                    if (gameBoard[i][j] == gameBoard[i-1][j+1] && gameBoard[i][j] == gameBoard[i-2][j+2] && gameBoard[i][j] == gameBoard[i-3][j+3]  && gameBoard[i][j] != EMPTY)
                    {
                        setWinner(gameBoard[i][j]);
                        return true;
                    }
                }
            }
        }

        setWinner(0); //No winner yet
        return false;
    }
	
    /*
     * This method checks if there are 3 tokens of a same player
     * @param player
     * @return int
    */
    public int check3In(int player)
    {	
        int times = 0;

        //In row
        for (int i = 5; i >= 0; i--)
        {
            for (int j = 0; j < 7; j++)
            {
                if (canMove(i, j + 2))
                {
                    if (gameBoard[i][j] == gameBoard[i][j + 1] && gameBoard[i][j] == gameBoard[i][j + 2] && gameBoard[i][j] == player)
                    {
                        times++;
                    }
                }
            }
        }

        //In column
        for (int i = 5; i >= 0; i--)
        {
            for (int j = 0; j < 7; j++)
            {
                if (canMove(i - 2, j))
                {
                    if (gameBoard[i][j] == gameBoard[i - 1][j] && gameBoard[i][j] == gameBoard[i - 2][j] && gameBoard[i][j] == player)
                    {
                        times++;
                    }
                }
            }
        }

        //In diagonal ascendent
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (canMove(i + 2, j + 2))
                {
                    if (gameBoard[i][j] == gameBoard[i + 1][j + 1] && gameBoard[i][j] == gameBoard[i + 2][j + 2] && gameBoard[i][j] == player)
                    {
                        times++;
                    }
                }
            }
        }

        //In diagonal descendent
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (canMove(i - 2, j + 2))
                {
                    if (gameBoard[i][j] == gameBoard[i - 1][j + 1] && gameBoard[i][j] == gameBoard[i - 2][j + 2] && gameBoard[i][j] == player)
                    {
                        times++;
                    }
                }
            }
        }

        return times;				
    }

    /*
     * This method checks if there are 2 tokens of a same player
     * @param player
     * @return int
    */
    public int check2In(int player)
    {
        int times = 0;

        //In row
        for (int i = 5; i >= 0; i--)
        {
            for (int j = 0; j < 7; j++)
            {
                if (canMove(i, j + 1))
                {
                    if (gameBoard[i][j] == gameBoard[i][j + 1] && gameBoard[i][j] == player)
                    {
                        times++;
                    }
                }
            }
        }

        //In column
        for (int i = 5; i >= 0; i--)
        {
            for (int j = 0; j < 7; j++)
            {
                if (canMove(i - 1, j))
                {
                    if (gameBoard[i][j] == gameBoard[i - 1][j] && gameBoard[i][j] == player)
                    {
                        times++;
                    }
                }
            }
        }

        //In diagonal ascendent
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (canMove(i + 1, j + 1))
                {
                    if (gameBoard[i][j] == gameBoard[i + 1][j + 1] && gameBoard[i][j] == player)
                    {
                        times++;
                    }
                }
            }
        }

        //In diagonal descendent
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (canMove(i - 1, j + 1))
                {
                    if (gameBoard[i][j] == gameBoard[i - 1][j + 1] && gameBoard[i][j] == player)
                    {
                        times++;
                    }
                }
            }
        }

        return times;
    }
    
    /*
     * This method checks for game over
     * @param Nothing.
     * @return boolean
    */
    public boolean checkGameOver()
    {
        if (checkWinState())
            return true;

        for(int r = 0; r < 6; r++)
        {
            for(int c = 0; c < 7; c++)
            {
                if(gameBoard[r][c] == EMPTY)
                {
                    return false;
                }
            }
        }

        return true;
    }
}