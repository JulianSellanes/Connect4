/*
 * GameManager.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the game in general
*/

import java.util.*;

public class GameManager
{
    //Declare a reference to the other Managers
    private MenuManager menuManager;
    private TimerManager timerManager;
    private MusicManager musicManager;

    private Board mainBoard; //Declare the variable that has a reference to the main board
    private ArrayList<Player> players = new ArrayList<Player>(); //Declare a list with the players
    private int turn; //Declare the variable that has the current turn
    private boolean playing; //Declare the variable that has if the players can play

    SimulationGame simulatedBoard; //Declare the variable that has a reference to the simulation game for the minimax
    MiniMax miniMaxAI; //Declare the variable that has a reference to the minimax
    
    //Declare the constructor
    public GameManager(MenuManager menuManager)
    {
        this.menuManager = menuManager;

        Board mainBoard = new Board(6, 7);
        this.mainBoard = mainBoard;

        resetGameManager(); //Restart Manager
    }

    //Getters

    /*
     * This method returns the value of the variable "mainBoard"
     * @param Nothing.
     * @return mainBoard
    */
    public Board getMainBoard()
    {
        return mainBoard;
    }

    /*
     * This method returns the value of the variable "players"
     * @param Nothing.
     * @return players
    */
    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    /*
     * This method returns the value of the variable "turn"
     * @param Nothing.
     * @return turn
    */
    public int getTurn()
    {
        return turn;
    }

    /*
     * This method returns the value of the variable "playing"
     * @param Nothing.
     * @return playing
    */
    public boolean getPlaying()
    {
        return playing;
    }

    /*
     * This method returns the value of the variable "timerManager"
     * @param Nothing.
     * @return timerManager
    */
    public TimerManager getTimerManager()
    {
        return timerManager;
    }

    /*
     * This method returns the value of the variable "musicManager"
     * @param Nothing.
     * @return musicManager
    */
    public MusicManager getMusicManager()
    {
        return musicManager;
    }

    //Setters

    /*
     * This method sets the value of the variable "turn"
     * @param turn
     * @return Nothing.
    */
    public void setTurn(int turn)
    {
        this.turn = turn;
    }

    /*
     * This method sets the value of the variable "playing"
     * @param playing
     * @return Nothing.
    */
    public void setPlaying(boolean playing)
    {
        this.playing = playing;
    }

    /*
     * This method sets the value of the variable "timerManager"
     * @param timerManager
     * @return Nothing.
    */
    public void setTimerManager(TimerManager timerManager)
    {
        this.timerManager = timerManager;
    }

    /*
     * This method sets the value of the variable "musicManager"
     * @param musicManager
     * @return Nothing.
    */
    public void setMusicManager(MusicManager musicManager)
    {
        this.musicManager = musicManager;
    }

    //Helpers

    /*
     * This method resets the GameManager variables
     * @param Nothing.
     * @return Nothing.
    */
    public void resetGameManager()
    {
        getPlayers().clear();
        setTurn(-1);
        setPlaying(false);
        getMainBoard().resetBoard();
    }

    /*
     * This method creates a new player and adds it to the list
     * @param name, age, color, isAI
     * @return Nothing.
    */
    public void createPlayer(String name, int age, String color, boolean isAI)
    {
        Player player = new Player(name, age, color, isAI, menuManager, this);
        players.add(player);
    }

    /*
     * This method starts the game
     * @param fromLoad
     * @return Nothing.
    */
    public void startGame(boolean fromLoad)
    {
        setPlaying(false);
        getMainBoard().generateBoard(); //Generate a new board
        setTurn(menuManager.getStartingPlayer()); //Assign the turn to the person who was going to start

        //Check if I'm loading a game

        if(!fromLoad) //If not, start the game
        {
            nextTurn();
            playing = true;
        }
        else //If I do, create fake players which are needed when loading a game
        {
            createPlayer("A", 0, "None", false);
            createPlayer("B", 0, "None", false);
            resetSimulationBoard(); //Restart the simulated board
        }
    }

    /*
     * This method assigns which player will start
     * @param picked
     * @return Nothing.
    */
    public void pickWhoStarts(int picked)
    {
        //According to the original instructions of the game, the youngest player must start, that's why the option is given

        if(picked == 2 || picked == 3)
        {
            int random = (int)Math.floor(Math.random() * (1 - 0 + 1) + 0);

            if(picked == 3) //If the option is 3, compare who is the youngest
            {
                if(getPlayers().get(0).getAge() < getPlayers().get(1).getAge())
                {
                    menuManager.setStartingPlayer(0);
                }
                else if(getPlayers().get(0).getAge() > getPlayers().get(1).getAge())
                {
                    menuManager.setStartingPlayer(1);
                }
                else //If they are the same age, choose randomly
                {
                    menuManager.setStartingPlayer(random);
                }
            }
            else if(picked == 2) //If the option is 2, choose randomly
            {
                menuManager.setStartingPlayer(random);
            }
        }
        else
        {
            menuManager.setStartingPlayer(picked);
        }

        resetSimulationBoard(); //Restart the simulated board
    }

    /*
     * This method restarts the simulated board
     * @param Nothing.
     * @return Nothing.
    */
    public void resetSimulationBoard()
    {
        miniMaxAI = new MiniMax(SimulationGame.O, menuManager); //Declare new minimax
        simulatedBoard = new SimulationGame(menuManager); //Declare new simulation game
    }

    /*
     * This method finds how far down a token can fall, and also changes the color of the hole in the board
     * @param posX, color
     * @return y
    */
    public int searchMaxPosY(int posX, String color)
    {
        int y = 0;
        for (int r = getMainBoard().getBoard().length - 1; r >= 0; r--) //Check from the bottom of the column
        {
            if(getMainBoard().getBoard()[r][posX].getColor().equals("None"))
            {
                getMainBoard().getBoard()[r][posX].setColor(color);

                if(r > 0 && r < 7)
                {
                    y = 110 + (70 * r);
                }
                else if(r == 0)
                {
                    y = 110;
                }
                else
                {
                    y = 0;
                }

                break;
            }
        }

        return y;
    }

    /*
     * This method passes the turn
     * @param Nothing.
     * @return Nothing.
    */
    public void nextTurn()
    {
        getMainBoard().checkIfBoardIsFull(); //Check that the board is not full
        
        if(playing == true)
        {
            if(turn == 0)
            {
                turn = 1;
            }
            else
            {
                turn = 0;
            }
        }

        //Let the player in turn be the only one who can move
        getPlayers().get(0).setCanMove(false);
        getPlayers().get(1).setCanMove(false);
        getPlayers().get(turn).setCanMove(true);

        if(getPlayers().get(turn).getImAI() == true) //If the player in turn is an AI, make a decision
        {
            getPlayers().get(turn).makeDecision();
        }
    }
    
    /*
     * This method returns if there is a winner
     * @param Nothing.
     * @return boolean
    */
    public boolean checkWinner()
    {
        ArrayList<String> allPossiblePatterns = new ArrayList<String>(); //Declare list of possible patterns

        searchPatterns(allPossiblePatterns); //Search possible patterns

        for(int i = 0; i < allPossiblePatterns.size(); i++)
        {
            //Check that there are 4 identical colors together
            if(allPossiblePatterns.get(i).contains("RedRedRedRed") || allPossiblePatterns.get(i).contains("YellowYellowYellowYellow") || allPossiblePatterns.get(i).contains("BlueBlueBlueBlue"))
            {
                return true;
            }
        }

        return false;
    }

    /*
     * This method loads the passed ArrayList with all possible patterns
     * @param allPossiblePatterns
     * @return Nothing.
    */
    public void searchPatterns(ArrayList<String> allPossiblePatterns)
    {
        //Horizontal
        
        for(int r = 0; r < getMainBoard().getROW(); r++)
        {
            String text = "";
            
            for (int c = 0; c < getMainBoard().getCOL(); c++)
            {
                text += getMainBoard().getBoard()[r][c].getColor();
            }
            
            allPossiblePatterns.add(text);
        }
        
        //Vertical
        
        for(int c = 0; c < getMainBoard().getCOL(); c++)
        {
            String text = "";
            
            for (int r = 0; r < getMainBoard().getROW(); r++)
            {
                text += getMainBoard().getBoard()[r][c].getColor();
            }
            
            allPossiblePatterns.add(text);
        }
        
        //Diagonal Top Left to Bottom Right
        
        int diagonalLines = (getMainBoard().getROW() + getMainBoard().getCOL()) - 1; 

        for (int i = 0; i < diagonalLines; i++)
        {
            String text = "";
            
            for(int k = 0; k < getMainBoard().getROW(); k++)
            {
                if(i - k < 0)
                {
                    break;
                }

                if(i - k <= getMainBoard().getROW())
                {
                    text += getMainBoard().getBoard()[k][i - k].getColor();
                }
            }
        
            allPossiblePatterns.add(text);
        }
        
        //Diagonal Bottom Left to Top Right
        
        for (int i = 0; i < diagonalLines; i++)
        {
            String text = "";
            
            for(int k = getMainBoard().getROW() - 1; k >= 0; k--)
            {
                if(i - ((getMainBoard().getROW() - 1) - k) < 0)
                {
                    break;
                }
                
                if(i - ((getMainBoard().getROW() - 1) - k) <= getMainBoard().getROW())
                {
                    text += getMainBoard().getBoard()[k][i - ((getMainBoard().getROW() - 1) - k)].getColor();
                }
            }

            allPossiblePatterns.add(text);
        }
    }
}