/*
 * FileManager.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the save and load game
*/

import java.io.*;
import java.util.*;

public class FileManager
{
    //Declare a reference to the other Managers
    private MenuManager menuManager;
    private GameManager gameManager;

    private final String savePath = "./Game/saves/"; //Declare the path of the folder where the games are saved
    private int match; //Declare the variable that has the number of the match
    private File currentFile; //Declare the variable that has the file we are using

    //Declare the constructor
    public FileManager(MenuManager menuManager, GameManager gameManager)
    {
        this.menuManager = menuManager;
        this.gameManager = gameManager;
    }

    //Getters

    /*
     * This method returns the value of the variable "match"
     * @param Nothing.
     * @return match
    */
    public int getMatch()
    {
        return match;
    }

    /*
     * This method returns the value of the variable "currentFile"
     * @param Nothing.
     * @return currentFile
    */
    public File getCurrentFile()
    {
        return currentFile;
    }

    //Setters

    /*
     * This method sets the value of the variable "match"
     * @param match
     * @return Nothing.
    */
    public void setMatch(int match)
    {
        this.match = match;
    }

    /*
     * This method sets the value of the variable "currentFile"
     * @param currentFile
     * @return Nothing.
    */
    public void setCurrentFile(File currentFile)
    {
        this.currentFile = currentFile;
    }

    //Helpers

    /*
     * This method returns if there is a previous save match that has not finished yet
     * @param Nothing.
     * @return boolean
    */
    public boolean checkIfLastMatchExists()
    {
        File file = new File(savePath + "/LastMatch.txt"); //Get file reference

        if(file.exists()) //Check if it exists
            return true;

        return false;
    }

    /*
     * This method returns the number of the last match we played, and also renames the previous file
     * @param Nothing.
     * @return int
    */
    public int getMatchCount()
    {
        File file = new File(savePath + "/LastMatchFINISHED.txt"); //Get reference to last match if it finished

        if(!file.exists()) //Check if it doesn't exist
        {
            file = new File(savePath + "/LastMatch.txt"); //Get reference to last match if it not finished yet

            if(!file.exists()) //Check if it doesn't exist
                return 0;
        }

        try
        {
            Scanner sc = new Scanner(file); //Declare scanner
        
            //Read the first line and get the value of the variable
            String[] str = sc.nextLine().split("\\s+");
            int numberToPass = Integer.parseInt(str[2]);
            sc.close(); //Close the scanner

            //Here, before passing the match number, we will try to rename the file of the last played game

            File newFile = new File(savePath + "/Match" + numberToPass + ".txt"); //Get reference to the new file

            if(newFile.exists()) //Check if a similar file already exists
                newFile.delete(); //Delete the copy to avoid possible duplicates and errors

            file.renameTo(newFile); //Rename the current file to the new one

            return numberToPass; //Return the number of the previous match
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    /*
     * This method creates a file for a new game
     * @param Nothing.
     * @return Nothing.
    */
    public void createMatchFile()
    {
        match = getMatchCount(); //Get the number of the previous match
        match++; //Increase the value

        File newFile = new File(savePath + "/LastMatch.txt"); //Get reference to the new file

        if(!newFile.exists()) //Check if it doesn't exist
        {
            try
            {
                newFile.createNewFile(); //Create the new file
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        setCurrentFile(newFile); //Assigns the new file to the variable "currentFile"
        updateMatchFile(); //Update the file
    }

    /*
     * This method updates the current game file
     * @param Nothing.
     * @return Nothing.
    */
    public void updateMatchFile()
    {
        if(getCurrentFile() == null) //Check if there is a file being used
            return;
        
        if(!getCurrentFile().exists()) //Check if it doesn't exist
        {
            createMatchFile(); //For security, create a new one
        }

        try
        {
            FileWriter fileWriter = new FileWriter(getCurrentFile()); //Declare fileWriter

            //Write variables with their new data to the text file

            fileWriter.write("MATCH = " + match + "\n\n");

            fileWriter.write("gameMode = " + menuManager.getGameMode() + "\n");
            fileWriter.write("difficulty = " + menuManager.getDifficulty() + "\n");
            fileWriter.write("startingPlayer = " + menuManager.getStartingPlayer() + "\n\n");

            fileWriter.write(gameManager.getMainBoard().getBoard()[0][0].getColor() + "|" + gameManager.getMainBoard().getBoard()[0][1].getColor() + "|" + gameManager.getMainBoard().getBoard()[0][2].getColor() + "|" + gameManager.getMainBoard().getBoard()[0][3].getColor() + "|" + gameManager.getMainBoard().getBoard()[0][4].getColor() + "|" + gameManager.getMainBoard().getBoard()[0][5].getColor() + "|" + gameManager.getMainBoard().getBoard()[0][6].getColor() + "\n");
            fileWriter.write(gameManager.getMainBoard().getBoard()[1][0].getColor() + "|" + gameManager.getMainBoard().getBoard()[1][1].getColor() + "|" + gameManager.getMainBoard().getBoard()[1][2].getColor() + "|" + gameManager.getMainBoard().getBoard()[1][3].getColor() + "|" + gameManager.getMainBoard().getBoard()[1][4].getColor() + "|" + gameManager.getMainBoard().getBoard()[1][5].getColor() + "|" + gameManager.getMainBoard().getBoard()[1][6].getColor() + "\n");
            fileWriter.write(gameManager.getMainBoard().getBoard()[2][0].getColor() + "|" + gameManager.getMainBoard().getBoard()[2][1].getColor() + "|" + gameManager.getMainBoard().getBoard()[2][2].getColor() + "|" + gameManager.getMainBoard().getBoard()[2][3].getColor() + "|" + gameManager.getMainBoard().getBoard()[2][4].getColor() + "|" + gameManager.getMainBoard().getBoard()[2][5].getColor() + "|" + gameManager.getMainBoard().getBoard()[2][6].getColor() + "\n");
            fileWriter.write(gameManager.getMainBoard().getBoard()[3][0].getColor() + "|" + gameManager.getMainBoard().getBoard()[3][1].getColor() + "|" + gameManager.getMainBoard().getBoard()[3][2].getColor() + "|" + gameManager.getMainBoard().getBoard()[3][3].getColor() + "|" + gameManager.getMainBoard().getBoard()[3][4].getColor() + "|" + gameManager.getMainBoard().getBoard()[3][5].getColor() + "|" + gameManager.getMainBoard().getBoard()[3][6].getColor() + "\n");
            fileWriter.write(gameManager.getMainBoard().getBoard()[4][0].getColor() + "|" + gameManager.getMainBoard().getBoard()[4][1].getColor() + "|" + gameManager.getMainBoard().getBoard()[4][2].getColor() + "|" + gameManager.getMainBoard().getBoard()[4][3].getColor() + "|" + gameManager.getMainBoard().getBoard()[4][4].getColor() + "|" + gameManager.getMainBoard().getBoard()[4][5].getColor() + "|" + gameManager.getMainBoard().getBoard()[4][6].getColor() + "\n");
            fileWriter.write(gameManager.getMainBoard().getBoard()[5][0].getColor() + "|" + gameManager.getMainBoard().getBoard()[5][1].getColor() + "|" + gameManager.getMainBoard().getBoard()[5][2].getColor() + "|" + gameManager.getMainBoard().getBoard()[5][3].getColor() + "|" + gameManager.getMainBoard().getBoard()[5][4].getColor() + "|" + gameManager.getMainBoard().getBoard()[5][5].getColor() + "|" + gameManager.getMainBoard().getBoard()[5][6].getColor() + "\n\n");
            
            fileWriter.write("player1Name = " + gameManager.getPlayers().get(0).getName() + "\n");
            fileWriter.write("player1Age = " + gameManager.getPlayers().get(0).getAge() + "\n");
            fileWriter.write("player1Color = " + gameManager.getPlayers().get(0).getColor() + "\n");
            fileWriter.write("player1ImAI = " + gameManager.getPlayers().get(0).getImAI() + "\n");
            fileWriter.write("player1CanMove = " + gameManager.getPlayers().get(0).getCanMove() + "\n");
            fileWriter.write("player1ColumnToPut = " + gameManager.getPlayers().get(0).getColumnToPut() + "\n\n");

            fileWriter.write("player2Name = " + gameManager.getPlayers().get(1).getName() + "\n");
            fileWriter.write("player2Age = " + gameManager.getPlayers().get(1).getAge() + "\n");
            fileWriter.write("player2Color = " + gameManager.getPlayers().get(1).getColor() + "\n");
            fileWriter.write("player2ImAI = " + gameManager.getPlayers().get(1).getImAI() + "\n");
            fileWriter.write("player2CanMove = " + gameManager.getPlayers().get(1).getCanMove() + "\n");
            fileWriter.write("player2ColumnToPut = " + gameManager.getPlayers().get(1).getColumnToPut() + "\n\n");

            fileWriter.write("turn = " + gameManager.getTurn() + "\n");
            fileWriter.write("playing = " + gameManager.getPlaying() + "\n");
            fileWriter.write("timerSecond = " + gameManager.getTimerManager().getSecond() + "\n");
            fileWriter.write("timerMinute = " + gameManager.getTimerManager().getMinute() + "\n\n");

            fileWriter.write("maxDepth = " + gameManager.miniMaxAI.getMaxDepth() + "\n");
            fileWriter.write("AILetter = " + gameManager.miniMaxAI.getAILetter() + "\n\n");

            fileWriter.write(gameManager.simulatedBoard.gameBoard[0][0] + "|" + gameManager.simulatedBoard.gameBoard[0][1] + "|" + gameManager.simulatedBoard.gameBoard[0][2] + "|" + gameManager.simulatedBoard.gameBoard[0][3] + "|" + gameManager.simulatedBoard.gameBoard[0][4] + "|" + gameManager.simulatedBoard.gameBoard[0][5] + "|" + gameManager.simulatedBoard.gameBoard[0][6] + "\n");
            fileWriter.write(gameManager.simulatedBoard.gameBoard[1][0] + "|" + gameManager.simulatedBoard.gameBoard[1][1] + "|" + gameManager.simulatedBoard.gameBoard[1][2] + "|" + gameManager.simulatedBoard.gameBoard[1][3] + "|" + gameManager.simulatedBoard.gameBoard[1][4] + "|" + gameManager.simulatedBoard.gameBoard[1][5] + "|" + gameManager.simulatedBoard.gameBoard[1][6] + "\n");
            fileWriter.write(gameManager.simulatedBoard.gameBoard[2][0] + "|" + gameManager.simulatedBoard.gameBoard[2][1] + "|" + gameManager.simulatedBoard.gameBoard[2][2] + "|" + gameManager.simulatedBoard.gameBoard[2][3] + "|" + gameManager.simulatedBoard.gameBoard[2][4] + "|" + gameManager.simulatedBoard.gameBoard[2][5] + "|" + gameManager.simulatedBoard.gameBoard[2][6] + "\n");
            fileWriter.write(gameManager.simulatedBoard.gameBoard[3][0] + "|" + gameManager.simulatedBoard.gameBoard[3][1] + "|" + gameManager.simulatedBoard.gameBoard[3][2] + "|" + gameManager.simulatedBoard.gameBoard[3][3] + "|" + gameManager.simulatedBoard.gameBoard[3][4] + "|" + gameManager.simulatedBoard.gameBoard[3][5] + "|" + gameManager.simulatedBoard.gameBoard[3][6] + "\n");
            fileWriter.write(gameManager.simulatedBoard.gameBoard[4][0] + "|" + gameManager.simulatedBoard.gameBoard[4][1] + "|" + gameManager.simulatedBoard.gameBoard[4][2] + "|" + gameManager.simulatedBoard.gameBoard[4][3] + "|" + gameManager.simulatedBoard.gameBoard[4][4] + "|" + gameManager.simulatedBoard.gameBoard[4][5] + "|" + gameManager.simulatedBoard.gameBoard[4][6] + "\n");
            fileWriter.write(gameManager.simulatedBoard.gameBoard[5][0] + "|" + gameManager.simulatedBoard.gameBoard[5][1] + "|" + gameManager.simulatedBoard.gameBoard[5][2] + "|" + gameManager.simulatedBoard.gameBoard[5][3] + "|" + gameManager.simulatedBoard.gameBoard[5][4] + "|" + gameManager.simulatedBoard.gameBoard[5][5] + "|" + gameManager.simulatedBoard.gameBoard[5][6] + "\n\n");
            
            fileWriter.write("winner = " + gameManager.simulatedBoard.winner + "\n");
            fileWriter.write("lastLetterPlayed = " + gameManager.simulatedBoard.lastLetterPlayed + "\n");
            fileWriter.write("lastMoveROW = " + gameManager.simulatedBoard.lastMove.getRow() + "\n");
            fileWriter.write("lastMoveCOL = " + gameManager.simulatedBoard.lastMove.getCol() + "\n");
            fileWriter.write("lastMoveVALUE = " + gameManager.simulatedBoard.lastMove.getValue());

            fileWriter.close(); //Close fileWriter
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the file to that of a finished game
     * @param Nothing.
     * @return Nothing.
    */
    public void finishGame()
    {
        if(!getCurrentFile().exists()) //Check if there is a file being used
            return;

        File newFile = new File(savePath + "/LastMatchFINISHED.txt"); //Get reference to the new file
        getCurrentFile().renameTo(newFile); //Rename current file
    }

    /*
     * This method loads a saved game
     * @param Nothing.
     * @return Nothing.
    */
    public void loadGame()
    {
        if(getCurrentFile() != null) //Check if there is no file being used
            return;

        File file = new File(savePath + "/LastMatch.txt"); //Get file reference

        if(!file.exists()) //Check if it doesn't exist
            return;

        setCurrentFile(file); //Assigns the file to the variable "currentFile"

        try
        {
            Scanner sc = new Scanner(file); //Declare scanner
            String[] str; //Declare string that will separate the lines

            //Get the value of each variable and update the data of the current game

            str = sc.nextLine().split("\\s+");
            match = Integer.parseInt(str[2]);

            str = sc.nextLine().split("");

            str = sc.nextLine().split("\\s+");
            menuManager.setGameMode(Integer.parseInt(str[2]));
            str = sc.nextLine().split("\\s+");
            menuManager.setDifficulty(Integer.parseInt(str[2]));
            str = sc.nextLine().split("\\s+");
            menuManager.setStartingPlayer(Integer.parseInt(str[2]));

            str = sc.nextLine().split("");

            str = sc.nextLine().split("\\|+");
            gameManager.getMainBoard().getBoard()[0][0].setColor(str[0]);
            gameManager.getMainBoard().getBoard()[0][1].setColor(str[1]);
            gameManager.getMainBoard().getBoard()[0][2].setColor(str[2]);
            gameManager.getMainBoard().getBoard()[0][3].setColor(str[3]);
            gameManager.getMainBoard().getBoard()[0][4].setColor(str[4]);
            gameManager.getMainBoard().getBoard()[0][5].setColor(str[5]);
            gameManager.getMainBoard().getBoard()[0][6].setColor(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.getMainBoard().getBoard()[1][0].setColor(str[0]);
            gameManager.getMainBoard().getBoard()[1][1].setColor(str[1]);
            gameManager.getMainBoard().getBoard()[1][2].setColor(str[2]);
            gameManager.getMainBoard().getBoard()[1][3].setColor(str[3]);
            gameManager.getMainBoard().getBoard()[1][4].setColor(str[4]);
            gameManager.getMainBoard().getBoard()[1][5].setColor(str[5]);
            gameManager.getMainBoard().getBoard()[1][6].setColor(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.getMainBoard().getBoard()[2][0].setColor(str[0]);
            gameManager.getMainBoard().getBoard()[2][1].setColor(str[1]);
            gameManager.getMainBoard().getBoard()[2][2].setColor(str[2]);
            gameManager.getMainBoard().getBoard()[2][3].setColor(str[3]);
            gameManager.getMainBoard().getBoard()[2][4].setColor(str[4]);
            gameManager.getMainBoard().getBoard()[2][5].setColor(str[5]);
            gameManager.getMainBoard().getBoard()[2][6].setColor(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.getMainBoard().getBoard()[3][0].setColor(str[0]);
            gameManager.getMainBoard().getBoard()[3][1].setColor(str[1]);
            gameManager.getMainBoard().getBoard()[3][2].setColor(str[2]);
            gameManager.getMainBoard().getBoard()[3][3].setColor(str[3]);
            gameManager.getMainBoard().getBoard()[3][4].setColor(str[4]);
            gameManager.getMainBoard().getBoard()[3][5].setColor(str[5]);
            gameManager.getMainBoard().getBoard()[3][6].setColor(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.getMainBoard().getBoard()[4][0].setColor(str[0]);
            gameManager.getMainBoard().getBoard()[4][1].setColor(str[1]);
            gameManager.getMainBoard().getBoard()[4][2].setColor(str[2]);
            gameManager.getMainBoard().getBoard()[4][3].setColor(str[3]);
            gameManager.getMainBoard().getBoard()[4][4].setColor(str[4]);
            gameManager.getMainBoard().getBoard()[4][5].setColor(str[5]);
            gameManager.getMainBoard().getBoard()[4][6].setColor(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.getMainBoard().getBoard()[5][0].setColor(str[0]);
            gameManager.getMainBoard().getBoard()[5][1].setColor(str[1]);
            gameManager.getMainBoard().getBoard()[5][2].setColor(str[2]);
            gameManager.getMainBoard().getBoard()[5][3].setColor(str[3]);
            gameManager.getMainBoard().getBoard()[5][4].setColor(str[4]);
            gameManager.getMainBoard().getBoard()[5][5].setColor(str[5]);
            gameManager.getMainBoard().getBoard()[5][6].setColor(str[6]);

            str = sc.nextLine().split("");

            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(0).setName(str[2]);
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(0).setAge(Integer.parseInt(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(0).setColor(str[2]);
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(0).setImAI(Boolean.parseBoolean(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(0).setCanMove(Boolean.parseBoolean(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(0).setColumnToPut(Integer.parseInt(str[2]));

            str = sc.nextLine().split("");

            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(1).setName(str[2]);
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(1).setAge(Integer.parseInt(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(1).setColor(str[2]);
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(1).setImAI(Boolean.parseBoolean(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(1).setCanMove(Boolean.parseBoolean(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.getPlayers().get(1).setColumnToPut(Integer.parseInt(str[2]));

            str = sc.nextLine().split("");

            str = sc.nextLine().split("\\s+");
            gameManager.setTurn(Integer.parseInt(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.setPlaying(Boolean.parseBoolean(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.getTimerManager().setSecond(Integer.parseInt(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.getTimerManager().setMinute(Integer.parseInt(str[2]));

            str = sc.nextLine().split("");

            str = sc.nextLine().split("\\s+");
            gameManager.miniMaxAI.setMaxDepth(Integer.parseInt(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.miniMaxAI.setAILetter(Integer.parseInt(str[2]));

            str = sc.nextLine().split("");

            str = sc.nextLine().split("\\|+");
            gameManager.simulatedBoard.gameBoard[0][0] = Integer.parseInt(str[0]);
            gameManager.simulatedBoard.gameBoard[0][1] = Integer.parseInt(str[1]);
            gameManager.simulatedBoard.gameBoard[0][2] = Integer.parseInt(str[2]);
            gameManager.simulatedBoard.gameBoard[0][3] = Integer.parseInt(str[3]);
            gameManager.simulatedBoard.gameBoard[0][4] = Integer.parseInt(str[4]);
            gameManager.simulatedBoard.gameBoard[0][5] = Integer.parseInt(str[5]);
            gameManager.simulatedBoard.gameBoard[0][6] = Integer.parseInt(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.simulatedBoard.gameBoard[1][0] = Integer.parseInt(str[0]);
            gameManager.simulatedBoard.gameBoard[1][1] = Integer.parseInt(str[1]);
            gameManager.simulatedBoard.gameBoard[1][2] = Integer.parseInt(str[2]);
            gameManager.simulatedBoard.gameBoard[1][3] = Integer.parseInt(str[3]);
            gameManager.simulatedBoard.gameBoard[1][4] = Integer.parseInt(str[4]);
            gameManager.simulatedBoard.gameBoard[1][5] = Integer.parseInt(str[5]);
            gameManager.simulatedBoard.gameBoard[1][6] = Integer.parseInt(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.simulatedBoard.gameBoard[2][0] = Integer.parseInt(str[0]);
            gameManager.simulatedBoard.gameBoard[2][1] = Integer.parseInt(str[1]);
            gameManager.simulatedBoard.gameBoard[2][2] = Integer.parseInt(str[2]);
            gameManager.simulatedBoard.gameBoard[2][3] = Integer.parseInt(str[3]);
            gameManager.simulatedBoard.gameBoard[2][4] = Integer.parseInt(str[4]);
            gameManager.simulatedBoard.gameBoard[2][5] = Integer.parseInt(str[5]);
            gameManager.simulatedBoard.gameBoard[2][6] = Integer.parseInt(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.simulatedBoard.gameBoard[3][0] = Integer.parseInt(str[0]);
            gameManager.simulatedBoard.gameBoard[3][1] = Integer.parseInt(str[1]);
            gameManager.simulatedBoard.gameBoard[3][2] = Integer.parseInt(str[2]);
            gameManager.simulatedBoard.gameBoard[3][3] = Integer.parseInt(str[3]);
            gameManager.simulatedBoard.gameBoard[3][4] = Integer.parseInt(str[4]);
            gameManager.simulatedBoard.gameBoard[3][5] = Integer.parseInt(str[5]);
            gameManager.simulatedBoard.gameBoard[3][6] = Integer.parseInt(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.simulatedBoard.gameBoard[4][0] = Integer.parseInt(str[0]);
            gameManager.simulatedBoard.gameBoard[4][1] = Integer.parseInt(str[1]);
            gameManager.simulatedBoard.gameBoard[4][2] = Integer.parseInt(str[2]);
            gameManager.simulatedBoard.gameBoard[4][3] = Integer.parseInt(str[3]);
            gameManager.simulatedBoard.gameBoard[4][4] = Integer.parseInt(str[4]);
            gameManager.simulatedBoard.gameBoard[4][5] = Integer.parseInt(str[5]);
            gameManager.simulatedBoard.gameBoard[4][6] = Integer.parseInt(str[6]);
            str = sc.nextLine().split("\\|+");
            gameManager.simulatedBoard.gameBoard[5][0] = Integer.parseInt(str[0]);
            gameManager.simulatedBoard.gameBoard[5][1] = Integer.parseInt(str[1]);
            gameManager.simulatedBoard.gameBoard[5][2] = Integer.parseInt(str[2]);
            gameManager.simulatedBoard.gameBoard[5][3] = Integer.parseInt(str[3]);
            gameManager.simulatedBoard.gameBoard[5][4] = Integer.parseInt(str[4]);
            gameManager.simulatedBoard.gameBoard[5][5] = Integer.parseInt(str[5]);
            gameManager.simulatedBoard.gameBoard[5][6] = Integer.parseInt(str[6]);

            str = sc.nextLine().split("");

            str = sc.nextLine().split("\\s+");
            gameManager.simulatedBoard.winner = Integer.parseInt(str[2]);
            str = sc.nextLine().split("\\s+");
            gameManager.simulatedBoard.lastLetterPlayed = Integer.parseInt(str[2]);
            str = sc.nextLine().split("\\s+");
            gameManager.simulatedBoard.lastMove.setRow(Integer.parseInt(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.simulatedBoard.lastMove.setCol(Integer.parseInt(str[2]));
            str = sc.nextLine().split("\\s+");
            gameManager.simulatedBoard.lastMove.setValue(Integer.parseInt(str[2]));

            sc.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        gameManager.setPlaying(false);
        gameManager.nextTurn(); //Pass the turn
        gameManager.setPlaying(true);
    }
}