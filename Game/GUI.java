/*
 * GUI.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the GUI
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class GUI
{
    //Declare a reference to the other Managers
    private MenuManager menuManager;
    private GameManager gameManager;
    private FileManager fileManager;

    private JFrame frame; //Declare the variable that has a reference to the main frame

    private JPanel gamePanel; //Declare the variable that has a reference to the game panel
    private JLabel timerTxt = new JLabel (""); //Declare the variable that has a reference to the timer text
    private KeyListener keyListener; //Declare the variable that has a reference to the KeyListener
    private ArrayList<JLabel> tokensInScene = new ArrayList<JLabel>(); //Declare a list of tokens in scene

    //Declare the constructor
    public GUI(MenuManager menuManager, GameManager gameManager, FileManager fileManager)
    {
        this.menuManager = menuManager;
        this.gameManager = gameManager;
        this.fileManager = fileManager;

        //Setup the frame
        frame = new JFrame ("Connect4, By: Julián Sellanes");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
    }

    /*
     * This method changes the current panel being displayed to a new one
     * @param newPanel
     * @return Nothing.
    */
    public void SwitchActivePanel(JPanel newPanel)
    {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(newPanel);

        frame.pack();
        frame.setVisible (true);
    }

    /*
     * This method displays the main menu
     * @param Nothing.
     * @return Nothing.
    */
    public void MainMenu()
    {
        //Setup new panel
        JPanel menuPanel = new JPanel();
        menuPanel.setPreferredSize (new Dimension (900, 550));
        menuPanel.setLayout (null);
        menuPanel.setBackground(Color.black);

        //Declare new components for the panel
        JLabel titleTxt = new JLabel ("Connect4");
        JLabel infoTxt = new JLabel ("<html>Student: Julián Sellanes<br/>Teacher: Jey Anandarajan<br/>ICS4U1-03<br/>June/7/2022</html>");
        final JButton PvPBttn = new JButton ("Player vs Player");
        final JButton PvAIBttn = new JButton ("Player vs AI");
        final JButton AIvAIBttn = new JButton ("AI vs AI");
        final JButton LoadGameBttn = new JButton ("Load Game");

        //Assign the sizes and positions of the components
        titleTxt.setBounds (360, 55, 180, 30);
        infoTxt.setBounds (10, 475, 210, 65);
        PvPBttn.setBounds (365, 150, 170, 60);
        PvAIBttn.setBounds (365, 245, 170, 60);
        AIvAIBttn.setBounds (365, 340, 170, 60);
        LoadGameBttn.setBounds (365, 435, 170, 60);

        //Decorate the components
        titleTxt.setFont(new Font("Arial", Font.BOLD, 40));
        titleTxt.setForeground(Color.white);
        
        infoTxt.setFont(new Font("Arial", Font.BOLD, 15));
        infoTxt.setForeground(Color.white);
        
        PvPBttn.setBackground(Color.white);
        PvPBttn.setFont(new Font("Arial", Font.BOLD, 15));
        PvPBttn.setForeground(Color.black);
        
        PvAIBttn.setBackground(Color.white);
        PvAIBttn.setFont(new Font("Arial", Font.BOLD, 15));
        PvAIBttn.setForeground(Color.black);
        
        AIvAIBttn.setBackground(Color.white);
        AIvAIBttn.setFont(new Font("Arial", Font.BOLD, 15));
        AIvAIBttn.setForeground(Color.black);
        
        LoadGameBttn.setBackground(Color.white);
        LoadGameBttn.setFont(new Font("Arial", Font.BOLD, 15));
        LoadGameBttn.setForeground(Color.black);

        //Declare buttons actions, and assign them
        ActionListener actionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                if(event.getSource() == PvPBttn)
                {
                    menuManager.setGameMode(1);
                    SetupPlayersMenu();
                }
                else if(event.getSource() == PvAIBttn)
                {
                    menuManager.setGameMode(2);
                    DifficultyMenu();
                }
                else if(event.getSource() == AIvAIBttn)
                {
                    menuManager.setGameMode(3);
                    DifficultyMenu();
                }
                else if(event.getSource() == LoadGameBttn)
                {
                    boolean checkLastMatch = fileManager.checkIfLastMatchExists(); //Check if there is a match that has not finished yet

                    if(checkLastMatch == true)
                    {
                        gameManager.setTimerManager(new TimerManager(timerTxt)); //Declare a new TimerManager
                        gameManager.setMusicManager(new MusicManager()); //Declare a new MusicManager
                        gameManager.startGame(true); //Start game
                        fileManager.loadGame(); //Load game

                        //Create tokens that were already in the scene
                        for(int r = 0; r < gameManager.getMainBoard().getBoard().length; r++)
                        {
                            for(int c = 0; c < gameManager.getMainBoard().getBoard()[r].length; c++)
                            {
                                if(!gameManager.getMainBoard().getBoard()[r][c].getColor().equals("None"))
                                {
                                    JLabel previousToken = new JLabel();
                                    int posX = 212 + (70 * c);
                                    int posY = 110 + (70 * r);
                                    previousToken.setBounds (posX, posY, 56, 56);
                                    previousToken.setIcon(new ImageIcon(getClass().getResource("./Game/pictures/" + gameManager.getMainBoard().getBoard()[r][c].getColor() + "Token.png")));
                                    tokensInScene.add(previousToken);
                                }
                            }
                        }

                        gameManager.getTimerManager().Control("Start"); //Start the timer
                        gameManager.getMusicManager().Control("Start", "Music"); //Start the music

                        GameMenu(); //Display game menu
                    }
                }
            }
        };
        PvPBttn.addActionListener(actionListener);
        PvAIBttn.addActionListener(actionListener);
        AIvAIBttn.addActionListener(actionListener);
        LoadGameBttn.addActionListener(actionListener);

        //Add components to the panel
        menuPanel.add (titleTxt);
        menuPanel.add (infoTxt);
        menuPanel.add (PvPBttn);
        menuPanel.add (PvAIBttn);
        menuPanel.add (AIvAIBttn);
        menuPanel.add (LoadGameBttn);

        if(tokensInScene.size() > 0) //Reset the list of tokens in scene
        {
            tokensInScene.clear();
        }

        //Reset Managers
        menuManager.resetMenuManager();
        gameManager.resetGameManager();

        //Switch panel
        SwitchActivePanel(menuPanel);
    }

    /*
     * This method displays the setup players menu
     * @param Nothing.
     * @return Nothing.
    */
    public void SetupPlayersMenu()
    {
        //Setup new panel
        JPanel setupPlayersPanel = new JPanel();
        setupPlayersPanel.setPreferredSize (new Dimension (900, 550));
        setupPlayersPanel.setLayout (null);
        setupPlayersPanel.setBackground(Color.black);

        //Declare new components for the panel
        JLabel titleTxt = new JLabel ("Setup Players");
        
        JLabel player1Txt = new JLabel("");
        if(menuManager.getGameMode() == 1 || menuManager.getGameMode() == 2)
        {
            player1Txt.setText("Player 1");
        }
        else if(menuManager.getGameMode() == 3)
        {
            player1Txt.setText("Player 1 (AI)");
        }

        JLabel player1NameTxt = new JLabel ("Name:");
        final JTextField player1NameField = new JTextField ();
        JLabel player1AgeTxt = new JLabel ("Age:");
        final JTextField player1AgeField = new JTextField ();
        JLabel player1ColorTxt = new JLabel ("Color:");
        String[] player1ColorBoxItems = {"Red", "Yellow", "Blue"};
        final JComboBox<String> player1ColorBox = new JComboBox<String> (player1ColorBoxItems);

        JLabel player2Txt = new JLabel("");
        if(menuManager.getGameMode() == 1)
        {
            player2Txt.setText("Player 2");
        }
        else if(menuManager.getGameMode() == 2 || menuManager.getGameMode() == 3)
        {
            player2Txt.setText("Player 2 (AI)");
        }

        JLabel player2NameTxt = new JLabel ("Name:");
        final JTextField player2NameField = new JTextField ();
        JLabel player2AgeTxt = new JLabel ("Age:");
        final JTextField player2AgeField = new JTextField ();
        JLabel player2ColorTxt = new JLabel ("Color:");
        String[] player2ColorBoxItems = {"Red", "Yellow", "Blue"};
        final JComboBox<String> player2ColorBox = new JComboBox<String> (player2ColorBoxItems);

        final JButton nextBttn = new JButton ("Next");

        //Assign the sizes and positions of the components
        titleTxt.setBounds (350, 20, 200, 35);

        player1Txt.setBounds (195, 100, 110, 20);
        player1NameTxt.setBounds (70, 167, 60, 15);
        player1NameField.setBounds (150, 162, 220, 25);
        player1AgeTxt.setBounds (85, 264, 60, 20);
        player1AgeField.setBounds (150, 262, 220, 25);
        player1ColorTxt.setBounds (72, 367, 60, 15);
        player1ColorBox.setBounds (150, 362, 220, 25);
        player2Txt.setBounds (625, 100, 110, 20);
        player2NameTxt.setBounds (530, 167, 60, 15);
        player2NameField.setBounds (610, 162, 220, 25);
        player2AgeTxt.setBounds (545, 264, 60, 20);
        player2AgeField.setBounds (610, 262, 220, 25);
        player2ColorTxt.setBounds (532, 367, 60, 15);
        player2ColorBox.setBounds (610, 362, 220, 25);

        nextBttn.setBounds (370, 465, 160, 60);

        //Decorate the components
        titleTxt.setFont(new Font("Arial", Font.BOLD, 30));
        titleTxt.setForeground(Color.white);

        player1Txt.setFont(new Font("Arial", Font.BOLD, 18));
        player1Txt.setForeground(Color.white);

        player1NameTxt.setFont(new Font("Arial", Font.BOLD, 18));
        player1NameTxt.setForeground(Color.white);

        player1AgeTxt.setFont(new Font("Arial", Font.BOLD, 18));
        player1AgeTxt.setForeground(Color.white);

        player1ColorTxt.setFont(new Font("Arial", Font.BOLD, 18));
        player1ColorTxt.setForeground(Color.white);
        
        player2Txt.setFont(new Font("Arial", Font.BOLD, 18));
        player2Txt.setForeground(Color.white);

        player2NameTxt.setFont(new Font("Arial", Font.BOLD, 18));
        player2NameTxt.setForeground(Color.white);

        player2AgeTxt.setFont(new Font("Arial", Font.BOLD, 18));
        player2AgeTxt.setForeground(Color.white);

        player2ColorTxt.setFont(new Font("Arial", Font.BOLD, 18));
        player2ColorTxt.setForeground(Color.white);

        nextBttn.setBackground(Color.white);
        nextBttn.setFont(new Font("Arial", Font.BOLD, 18));
        nextBttn.setForeground(Color.black);

        //Declare buttons actions, and assign them
        ActionListener actionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                if(event.getSource() == nextBttn)
                {
                    //If no field is empty, and the names are not the same, and the ages are just numbers, and the colors are not the same
                    if(!player1NameField.getText().equals("") &&
                       !player2NameField.getText().equals("") &&
                       !player1AgeField.getText().equals("") &&
                       player1AgeField.getText().matches("[0-9]+") &&
                       !player2AgeField.getText().equals("") &&
                       player2AgeField.getText().matches("[0-9]+") &&
                       !player1NameField.getText().equals(player2NameField.getText()) &&
                        player1ColorBox.getSelectedItem() != player2ColorBox.getSelectedItem()) {
                        
                        //Create players depending on the game mode
                        boolean firstAIExists = false;
                        boolean secondAIExists = false;

                        if(menuManager.getGameMode() == 1)
                        {
                            firstAIExists = false;
                            secondAIExists = false;
                        }
                        else if(menuManager.getGameMode() == 2)
                        {
                            firstAIExists = false;
                            secondAIExists = true;
                        }
                        else if(menuManager.getGameMode() == 3)
                        {
                            firstAIExists = true;
                            secondAIExists = true;
                        }

                        gameManager.createPlayer(player1NameField.getText(), Integer.parseInt(player1AgeField.getText()), player1ColorBox.getSelectedItem().toString(), firstAIExists);
                        gameManager.createPlayer(player2NameField.getText(), Integer.parseInt(player2AgeField.getText()), player2ColorBox.getSelectedItem().toString(), secondAIExists);

                        if(gameManager.getPlayers().size() > 0) //Make sure the players were created
                        {
                            PickPlayersMenu();
                        }
                    }
                }
            }
        };
        nextBttn.addActionListener(actionListener);

        //Add components to the panel
        setupPlayersPanel.add (titleTxt);
        setupPlayersPanel.add (player1Txt);
        setupPlayersPanel.add (player1NameTxt);
        setupPlayersPanel.add (player1NameField);
        setupPlayersPanel.add (player1AgeTxt);
        setupPlayersPanel.add (player1AgeField);
        setupPlayersPanel.add (player1ColorTxt);
        setupPlayersPanel.add (player1ColorBox);
        setupPlayersPanel.add (player2Txt);
        setupPlayersPanel.add (player2NameTxt);
        setupPlayersPanel.add (player2NameField);
        setupPlayersPanel.add (player2AgeTxt);
        setupPlayersPanel.add (player2AgeField);
        setupPlayersPanel.add (player2ColorTxt);
        setupPlayersPanel.add (player2ColorBox);
        setupPlayersPanel.add (nextBttn);

        //Switch panel
        SwitchActivePanel(setupPlayersPanel);
    }

    /*
     * This method displays the difficulty menu
     * @param Nothing.
     * @return Nothing.
    */
    public void DifficultyMenu()
    {
        //Setup new panel
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setPreferredSize (new Dimension (900, 550));
        difficultyPanel.setLayout (null);
        difficultyPanel.setBackground(Color.black);

        //Declare new components for the panel
        JLabel titleTxt = new JLabel ("Choose the difficulty");
        final JRadioButton easyRBttn = new JRadioButton ("Easy");
        final JRadioButton normalRBttn = new JRadioButton ("Normal");
        final JRadioButton hardRBttn = new JRadioButton ("Hard");
        final JButton nextBttn = new JButton ("Next");

        //Assign the sizes and positions of the components
        titleTxt.setBounds (300, 40, 300, 35);
        easyRBttn.setBounds (395, 145, 130, 60);
        normalRBttn.setBounds (382, 245, 155, 60);
        hardRBttn.setBounds (395, 345, 130, 60);
        nextBttn.setBounds (370, 465, 160, 60);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(easyRBttn);
        buttonGroup.add(normalRBttn);
        buttonGroup.add(hardRBttn);

        //Decorate the components
        titleTxt.setFont(new Font("Arial", Font.BOLD, 30));
        titleTxt.setForeground(Color.white);
        
        easyRBttn.setOpaque(false);
        easyRBttn.setFont(new Font("Arial", Font.BOLD, 30));
        easyRBttn.setForeground(Color.white);
        
        normalRBttn.setOpaque(false);
        normalRBttn.setFont(new Font("Arial", Font.BOLD, 30));
        normalRBttn.setForeground(Color.white);
        
        hardRBttn.setOpaque(false);
        hardRBttn.setFont(new Font("Arial", Font.BOLD, 30));
        hardRBttn.setForeground(Color.white);
        
        nextBttn.setBackground(Color.white);
        nextBttn.setFont(new Font("Arial", Font.BOLD, 18));
        nextBttn.setForeground(Color.black);

        //Declare buttons actions, and assign them
        ActionListener actionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                if(event.getSource() == easyRBttn)
                {
                    menuManager.setDifficulty(1);
                }
                else if(event.getSource() == normalRBttn)
                {
                    menuManager.setDifficulty(2);
                }
                else if(event.getSource() == hardRBttn)
                {
                    menuManager.setDifficulty(3);
                }
                else if(event.getSource() == nextBttn)
                {
                    if(menuManager.getDifficulty() > -1)
                    {
                        SetupPlayersMenu();
                    }
                }
            }
        };
        easyRBttn.addActionListener(actionListener);
        normalRBttn.addActionListener(actionListener);
        hardRBttn.addActionListener(actionListener);
        nextBttn.addActionListener(actionListener);

        //Add components to the panel
        difficultyPanel.add (titleTxt);
        difficultyPanel.add (easyRBttn);
        difficultyPanel.add (normalRBttn);
        difficultyPanel.add (hardRBttn);
        difficultyPanel.add (nextBttn);

        //Switch panel
        SwitchActivePanel(difficultyPanel);
    }

    /*
     * This method displays the pick players menu
     * @param Nothing.
     * @return Nothing.
    */
    public void PickPlayersMenu()
    {
        //Setup new panel
        JPanel pickPlayersPanel = new JPanel();
        pickPlayersPanel.setPreferredSize (new Dimension (900, 550));
        pickPlayersPanel.setLayout (null);
        pickPlayersPanel.setBackground(Color.black);

        //Declare new components for the panel
        JLabel titleTxt = new JLabel ("Which player starts?");
        final JRadioButton player1RBttn = new JRadioButton ("Player 1");
        final JRadioButton player2RBttn = new JRadioButton ("Player 2");
        final JRadioButton randomRBttn = new JRadioButton ("Random");
        final JRadioButton youngestRBttn = new JRadioButton ("Youngest");
        final JButton startBttn = new JButton ("Start");

        //Assign the sizes and positions of the components
        titleTxt.setBounds (300, 40, 300, 35);
        player1RBttn.setBounds (377, 125, 165, 40);
        player2RBttn.setBounds (377, 205, 165, 40);
        randomRBttn.setBounds (377, 285, 165, 40);
        youngestRBttn.setBounds (373, 365, 180, 40);
        startBttn.setBounds (370, 465, 160, 60);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(player1RBttn);
        buttonGroup.add(player2RBttn);
        buttonGroup.add(randomRBttn);
        buttonGroup.add(youngestRBttn);

        //Decorate the components
        titleTxt.setFont(new Font("Arial", Font.BOLD, 30));
        titleTxt.setForeground(Color.white);

        player1RBttn.setOpaque(false);
        player1RBttn.setFont(new Font("Arial", Font.BOLD, 30));
        player1RBttn.setForeground(Color.white);
        
        player2RBttn.setOpaque(false);
        player2RBttn.setFont(new Font("Arial", Font.BOLD, 30));
        player2RBttn.setForeground(Color.white);
        
        randomRBttn.setOpaque(false);
        randomRBttn.setFont(new Font("Arial", Font.BOLD, 30));
        randomRBttn.setForeground(Color.white);

        youngestRBttn.setOpaque(false);
        youngestRBttn.setFont(new Font("Arial", Font.BOLD, 30));
        youngestRBttn.setForeground(Color.white);

        startBttn.setBackground(Color.white);
        startBttn.setFont(new Font("Arial", Font.BOLD, 18));
        startBttn.setForeground(Color.black);

        //Declare buttons actions, and assign them
        ActionListener actionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                if(event.getSource() == player1RBttn)
                {
                    gameManager.pickWhoStarts(0);
                }
                else if(event.getSource() == player2RBttn)
                {
                    gameManager.pickWhoStarts(1);
                }
                else if(event.getSource() == randomRBttn)
                {
                    gameManager.pickWhoStarts(2);
                }
                else if(event.getSource() == youngestRBttn)
                {
                    gameManager.pickWhoStarts(3);
                }
                else if(event.getSource() == startBttn)
                {
                    if(menuManager.getStartingPlayer() > -1)
                    {
                        gameManager.setTimerManager(new TimerManager(timerTxt)); //Declare a new TimerManager
                        gameManager.getTimerManager().Control("Start"); //Start the timer
                        gameManager.setMusicManager(new MusicManager()); //Declare a new MusicManager
                        gameManager.getMusicManager().Control("Start", "Music"); //Start the music

                        gameManager.startGame(false); //Start game
                        GameMenu(); //Display game menu

                        fileManager.createMatchFile(); //Create new file
                    }
                }
            }
        };
        player1RBttn.addActionListener(actionListener);
        player2RBttn.addActionListener(actionListener);
        randomRBttn.addActionListener(actionListener);
        youngestRBttn.addActionListener(actionListener);
        startBttn.addActionListener(actionListener);

        //Add components to the panel
        pickPlayersPanel.add (titleTxt);
        pickPlayersPanel.add (player1RBttn);
        pickPlayersPanel.add (player2RBttn);
        pickPlayersPanel.add (randomRBttn);
        pickPlayersPanel.add (youngestRBttn);
        pickPlayersPanel.add (startBttn);

        //Switch panel
        SwitchActivePanel(pickPlayersPanel);
    }

    /*
     * This method displays the game menu
     * @param Nothing.
     * @return Nothing.
    */
    public void GameMenu()
    {
        //Setup new panel
        gamePanel = new JPanel();
        gamePanel.setPreferredSize (new Dimension (900, 550));
        gamePanel.setLayout (null);
        gamePanel.setBackground(Color.black);

        //Declare new components for the panel
        JLabel board = new JLabel ("");
        final JLabel token = new JLabel ("");
        JLabel playerTurnTxt = new JLabel ("Player Turn:");
        JLabel playerTurnNameTxt = new JLabel ("", SwingConstants.CENTER);
        JLabel playerTurnImg = new JLabel ("");

        //Assign the sizes and positions of the components
        board.setBounds (204, 100, 491, 423);
        token.setBounds (212, 15, 56, 56);
        timerTxt.setBounds (80, 175, 60, 20);
        playerTurnTxt.setBounds (42, 275, 120, 25);
        playerTurnNameTxt.setBounds (-22, 325, 250, 30);
        playerTurnImg.setBounds (75, 380, 56, 56);

        //Decorate the components
        timerTxt.setFont(new Font("Arial", Font.BOLD, 20));
        timerTxt.setForeground(Color.white);
        playerTurnTxt.setFont(new Font("Arial", Font.BOLD, 20));
        playerTurnTxt.setForeground(Color.white);
        playerTurnNameTxt.setFont(new Font("Arial", Font.BOLD, 20));
        playerTurnNameTxt.setForeground(Color.white);

        board.setIcon(new ImageIcon(getClass().getResource("./pictures/Board.png")));
        token.setIcon(new ImageIcon(getClass().getResource("./pictures/" + gameManager.getPlayers().get(gameManager.getTurn()).getColor() + "Token.png")));
        playerTurnNameTxt.setText(gameManager.getPlayers().get(gameManager.getTurn()).getName());
        playerTurnImg.setIcon(new ImageIcon(getClass().getResource("./pictures/" + gameManager.getPlayers().get(gameManager.getTurn()).getColor() + "Token.png")));

        //Add components to the panel
        gamePanel.add (board);
        gamePanel.add (token);
        gamePanel.add (playerTurnTxt);
        gamePanel.add (playerTurnNameTxt);
        gamePanel.add (playerTurnImg);
        gamePanel.add (timerTxt);

        if(tokensInScene.size() > 0) //Create the tokens that were already in the scene
        {
            for(int i = 0; i < tokensInScene.size(); i++)
            {
                JLabel previousToken = tokensInScene.get(i);
                gamePanel.add(previousToken);
            }
        }

        final int startX = token.getX(); //Declare the variable that has the initial position at x of the token

        if(menuManager.getGameMode() == 3 || menuManager.getGameMode() == 2 && gameManager.getTurn() == 1) //Control AI movement
        {
            final int posX = 212 + (70 * gameManager.getPlayers().get(gameManager.getTurn()).getColumnToPut()); //Declare the variable with the column that the AI wants to use
            int posY = token.getY(); //Declare the variable that has the initial position at y of the token

            if(gameManager.getPlayers().get(gameManager.getTurn()).getCanMove()) //If the AI can move, continue
            {
                gameManager.getPlayers().get(gameManager.getTurn()).setCanMove(false); //Set "canMove" of the AI to false
                gameManager.getMusicManager().Control("Start", "Token"); //Start token falling sound
                token.setLocation(posX, posY); //Put the token in the new position

                Runnable run = new Runnable() //Declare a new Runnable
                {
                    @Override
                    public void run()
                    {
                        int newPosY = token.getY(); //Declare the variable that has the current position at y of the token while falling
                        boolean isFalling = true; //Declare the variable that has if the token is falling
                        
                        String color = "None";
                        color = gameManager.getPlayers().get(gameManager.getTurn()).getColor(); //Get AI color

                        int maxPosY = gameManager.searchMaxPosY(gameManager.getPlayers().get(gameManager.getTurn()).getColumnToPut(), color); //Declare the variable that has the final position at y of the token
                        if(maxPosY != 0)
                        {
                            while(isFalling)
                            {
                                //Increment the position at y every second and update the token position
                                token.setLocation(posX, newPosY);
                                newPosY += 1;
                                if(newPosY > maxPosY) //When it reaches the limit
                                {
                                    isFalling = false; //Stop falling
                                    tokensInScene.add(token); //Add the token to the list
                                    Winner(); //Check winner
                                }
                                
                                try
                                {
                                    Thread.sleep(1);
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                };
                
                Thread animation = new Thread(run); //Declare a new Thread
                animation.start(); //Start Thread
            }
        }
        else if(menuManager.getGameMode() == 1 || menuManager.getGameMode() == 2 && gameManager.getTurn() == 0) //Control player movement
        {
            if(frame.getKeyListeners().length > 0) //Remove previous keyListeners to avoid errors
            {
                frame.removeKeyListener(keyListener);
            }

            keyListener = new KeyListener() //Declare a new KeyListener
            {
                int column = 0;
                
                @Override
                public void keyTyped(KeyEvent event) {}
                
                @Override
                public void keyReleased(KeyEvent event) {}
                
                @Override
                public void keyPressed(KeyEvent event) //When a key is pressed
                {
                    if(gameManager.getPlayers().get(gameManager.getTurn()).getCanMove()) //If the player can move, continue
                    {
                        int posX = token.getX(); //Declare the variable that has the initial position at x of the token
                        int posY = token.getY(); //Declare the variable that has the initial position at y of the token
                        boolean imMoving = true; //Declare the variable that has if the player is moving

                        int code = event.getKeyCode();

                        if(gameManager.getTurn() == 0) //If it's player 1
                        {
                            if(code == KeyEvent.VK_LEFT) //If the player presses the left arrow
                            {
                                moveLeft(posX, posY, startX, imMoving); //Move token left
                            }
                            else if(code == KeyEvent.VK_RIGHT) //If the player presses the right arrow
                            {
                                moveRight(posX, posY, startX, imMoving); //Move token right
                            }
                            else if(code == KeyEvent.VK_DOWN) //If the player presses the down arrow
                            {
                                if(!gameManager.getMainBoard().getBoardIsFull() && !gameManager.getMainBoard().checkIfColIsFull(column)) //If the board is not full, and the column the player wants is not full
                                {
                                    gameManager.getPlayers().get(gameManager.getTurn()).setCanMove(false); //Set "canMove" of the player to false
                                    imMoving = false; //Stop moving
                                    gameManager.getMusicManager().Control("Start", "Token"); //Start token falling sound
                                    fallDown(); //Fall down
                                }
                            }
                        }
                        else if(gameManager.getTurn() == 1 && menuManager.getGameMode() == 1) //If it's player 2
                        {
                            if(code == KeyEvent.VK_A) //If the player presses the A
                            {
                                moveLeft(posX, posY, startX, imMoving); //Move token left
                            }
                            else if(code == KeyEvent.VK_D) //If the player presses the D
                            {
                                moveRight(posX, posY, startX, imMoving); //Move token right
                            }
                            else if(code == KeyEvent.VK_S) //If the player presses the S
                            {
                                if(!gameManager.getMainBoard().getBoardIsFull() && !gameManager.getMainBoard().checkIfColIsFull(column)) //If the board is not full, and the column the player wants is not full
                                {
                                    gameManager.getPlayers().get(gameManager.getTurn()).setCanMove(false); //Set "canMove" of the player to false
                                    imMoving = false; //Stop moving
                                    gameManager.getMusicManager().Control("Start", "Token"); //Start token falling sound
                                    fallDown(); //Fall down
                                }
                            }
                        }
                    }
                }

                /*
                 * This method updates the position of the token while it is within the limit
                 * @param posX, posY, startX, imMoving
                 * @return Nothing.
                */
                public void moveLeft(int posX, int posY, int startX, boolean imMoving)
                {
                    if(imMoving && posX > startX)
                    {
                        token.setLocation(posX - 70, posY);

                        if(column > 0)
                        {
                            column--;
                        }
                    }
                }

                /*
                 * This method updates the position of the token while it is within the limit
                 * @param posX, posY, startX, imMoving
                 * @return Nothing.
                */
                public void moveRight(int posX, int posY, int startX, boolean imMoving)
                {
                    if(imMoving && posX < startX + 420)
                    {
                        token.setLocation(posX + 70, posY);

                        if(column < 6)
                        {
                            column++;
                        }
                    }
                }

                /*
                 * This method makes the animation of the token falling
                 * @param Nothing.
                 * @return Nothing.
                */
                public void fallDown()
                {
                    Runnable run = new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            int posX = token.getX(); //Declare the variable that has the initial position at x of the token
                            int newPosY = token.getY(); //Declare the variable that has the current position at y of the token while falling
                            boolean isFalling = true; //Declare the variable that has if the token is falling
                            
                            String color = "None";
                            color = gameManager.getPlayers().get(gameManager.getTurn()).getColor(); //Get player color

                            int maxPosY = gameManager.searchMaxPosY(column, color); //Declare the variable that has the final position at y of the token
                            if(maxPosY != 0)
                            {
                                while(isFalling)
                                {
                                    //Increment the position at y every second and update the token position
                                    token.setLocation(posX, newPosY);
                                    newPosY += 1;

                                    if(newPosY > maxPosY) //When it reaches the limit
                                    {
                                        isFalling = false; //Stop falling
                                        tokensInScene.add(token); //Add the token to the list

                                        if(menuManager.getGameMode() == 2 && menuManager.getDifficulty() != 1) //If I'm playing against an AI, move inside the simulated board
                                        {
                                            gameManager.simulatedBoard.makeMove(column, SimulationGame.X);
                                        }

                                        Winner(); //Add the token to the list
                                    }
                                    
                                    try
                                    {
                                        Thread.sleep(1);
                                    }
                                    catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    };
                    
                    Thread animation = new Thread(run); //Declare a new Thread
                    animation.start(); //Start Thread
                }
            };
            frame.addKeyListener(keyListener);
        }

        //Switch panel
        SwitchActivePanel(gamePanel);
    }

    /*
     * This method checks if there is a winner, and controls if the game ends or continues
     * @param Nothing.
     * @return Nothing.
    */
    public void Winner()
    {  
        final boolean winner = gameManager.checkWinner(); //Check if there is a winner
        gameManager.getMainBoard().checkIfBoardIsFull(); //Check if the board is full
        fileManager.updateMatchFile(); //Update the file

        if(!gameManager.checkWinner() && !gameManager.getMainBoard().getBoardIsFull()) //If there is no winner and the board is not full, change turns and continue the game
        {
            gameManager.nextTurn(); //Change turn
            gamePanel = null;
            GameMenu(); //Display game menu
            fileManager.updateMatchFile(); //Update the file
        }
        else //If there is a winner or the board is full, end the game
        {
            gameManager.setPlaying(false);
            gameManager.getTimerManager().Control("Stop"); //Stop the timer
            gameManager.getMusicManager().Control("Stop", "Music"); //Stop the background music
            gameManager.getMusicManager().Control("Start", "Victory"); //Start the victory music
            fileManager.finishGame();

            Runnable run = new Runnable() //Declare a new Runnable
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(4000);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    //Display victory menu after 4 seconds
                    VictoryMenu(winner, gameManager.getPlayers().get(gameManager.getTurn()).getName());
                }
            };
            
            Thread time = new Thread(run); //Declare a new Thread
            time.start(); //Start Thread
        }
    }

    /*
     * This method displays the victory menu
     * @param winner, winnerName
     * @return Nothing.
    */
    public void VictoryMenu(boolean winner, String winnerName)
    {
        //Setup new panel
        JPanel victoryPanel = new JPanel();
        victoryPanel.setPreferredSize (new Dimension (900, 550));
        victoryPanel.setLayout (null);
        victoryPanel.setBackground(Color.black);

        //Declare new components for the panel
        JLabel titleTxt = new JLabel ("", SwingConstants.CENTER);

        if(winner)
        {
            titleTxt.setText(winnerName + " WON!");
        }
        else
        {
            titleTxt.setText("TIE!");
        }

        JLabel finalTimerTxt = new JLabel ("Time: " + timerTxt.getText(), SwingConstants.CENTER);
        JLabel usedTokensTxt = new JLabel ("Used tokens: ", SwingConstants.CENTER);
        JLabel fireworksGif = new JLabel ("");
        JLabel dancingGif = new JLabel ("");
        final JButton mainBttn = new JButton ("Main Menu");
        final JButton restartBttn = new JButton ("Restart");
        final JButton exitBttn = new JButton ("Exit");

        //Assign the sizes and positions of the components
        titleTxt.setBounds (140, 60, 600, 25);
        finalTimerTxt.setBounds (345, 175, 200, 20);
        usedTokensTxt.setBounds (325, 275, 250, 20);
        fireworksGif.setBounds (-120, 20, 480, 320);
        dancingGif.setBounds (600, 70, 222, 200);
        mainBttn.setBounds (105, 400, 170, 60);
        restartBttn.setBounds (365, 400, 170, 60);
        exitBttn.setBounds (625, 400, 170, 60);

        int amountOfTokens = 0;
        if(tokensInScene.size() > 0)
        {
            for(int i = 0; i < tokensInScene.size(); i++)
            {
               amountOfTokens++;
            }
        }

        //Decorate the components
        titleTxt.setFont(new Font("Arial", Font.BOLD, 30));
        titleTxt.setForeground(Color.white);

        finalTimerTxt.setFont(new Font("Arial", Font.BOLD, 25));
        finalTimerTxt.setForeground(Color.white);

        usedTokensTxt.setText("Used tokens: " + amountOfTokens);
        usedTokensTxt.setFont(new Font("Arial", Font.BOLD, 25));
        usedTokensTxt.setForeground(Color.white);

        fireworksGif.setIcon(new ImageIcon(getClass().getResource("./pictures/Fireworks.gif")));
        dancingGif.setIcon(new ImageIcon(getClass().getResource("./pictures/Dancing.gif")));

        mainBttn.setBackground(Color.white);
        mainBttn.setFont(new Font("Arial", Font.BOLD, 15));
        mainBttn.setForeground(Color.black);

        restartBttn.setBackground(Color.white);
        restartBttn.setFont(new Font("Arial", Font.BOLD, 15));
        restartBttn.setForeground(Color.black);

        exitBttn.setBackground(Color.white);
        exitBttn.setFont(new Font("Arial", Font.BOLD, 15));
        exitBttn.setForeground(Color.black);

        //Declare buttons actions, and assign them
        ActionListener actionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                if(event.getSource() == mainBttn)
                {
                    MainMenu();
                }
                else if(event.getSource() == restartBttn)
                {
                    Restart();
                }
                else if(event.getSource() == exitBttn)
                {
                    Exit();
                }
            }
        };
        mainBttn.addActionListener(actionListener);
        restartBttn.addActionListener(actionListener);
        exitBttn.addActionListener(actionListener);

        //Add components to the panel
        victoryPanel.add (titleTxt);
        victoryPanel.add (finalTimerTxt);
        victoryPanel.add (usedTokensTxt);
        victoryPanel.add (fireworksGif);
        victoryPanel.add (dancingGif);
        victoryPanel.add (mainBttn);
        victoryPanel.add (restartBttn);
        victoryPanel.add (exitBttn);

        //Switch panel
        SwitchActivePanel(victoryPanel);
    }

    /*
     * This method restarts the game
     * @param Nothing.
     * @return Nothing.
    */
    public void Restart()
    {
        if(tokensInScene.size() > 0) //Reset the list of tokens in scene
        {
            tokensInScene.clear();
        }

        gameManager.resetSimulationBoard(); //Restart the simulated board

        gameManager.setTimerManager(new TimerManager(timerTxt)); //Declare a new TimerManager
        gameManager.getTimerManager().Control("Start"); //Start the timer
        gameManager.setMusicManager(new MusicManager()); //Declare a new MusicManager
        gameManager.getMusicManager().Control("Start", "Music"); //Start the music
        
        gameManager.startGame(false); //Start game
        gamePanel = null; //Set gamePanel to null
        GameMenu(); //Display game menu

        fileManager.createMatchFile(); //Create new file
    }
    
    /*
     * This method exits the game
     * @param Nothing.
     * @return Nothing.
    */
    public void Exit()
    {
        System.exit(0);
    }
}