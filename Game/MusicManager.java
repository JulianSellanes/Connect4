/*
 * MusicManager.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the music
*/

import java.io.*;
import javax.sound.sampled.*;

public class MusicManager
{
    //Declare the path of the music files
    private String musicPath = "./Game/music/BGMusic.wav";
    private String tokenPath = "./Game/music/TokenSoundEffect.wav";
    private String victoryPath = "./Game/music/VictorySoundEffect.wav";
    //Declare Clips
    Clip clipMusic;
    Clip clipToken;
    Clip clipVictory;

    //Declare the constructor
    public MusicManager()
    {
        try
        {
            //Get reference to the music/sound file, declare audioStreamMusic and assign it to the corresponding clip

            File mFile = new File (musicPath);
		    AudioInputStream audioStreamMusic = AudioSystem.getAudioInputStream(mFile);
		    clipMusic = AudioSystem.getClip();
		    clipMusic.open(audioStreamMusic);

            File tFile = new File (tokenPath);
		    AudioInputStream audioStreamToken = AudioSystem.getAudioInputStream(tFile);
		    clipToken = AudioSystem.getClip();
		    clipToken.open(audioStreamToken);

            File vFile = new File (victoryPath);
		    AudioInputStream audioStreamVictory = AudioSystem.getAudioInputStream(vFile);
		    clipVictory = AudioSystem.getClip();
		    clipVictory.open(audioStreamVictory);

            //Stop music in case of emergency
            Control("Stop", "Music");
            Control("Stop", "Token");
            Control("Stop", "Victory");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //Helpers

    /*
     * This method controls the clips, starts them or ends them
     * @param action, type
     * @return Nothing.
    */
    public void Control(String action, String type)
    {
        if(action.equals("Start"))
        {
            if(type.equals("Music"))
            {
                if(clipMusic.isRunning()) //If the clip was already playing, stop it
                    clipMusic.stop();

                clipMusic.setFramePosition(0); //Restart clip
                clipMusic.loop(Clip.LOOP_CONTINUOUSLY); //Make it a loop
                clipMusic.start(); //Start clip
            }
            else if(type.equals("Token"))
            {
                if(clipToken.isRunning()) //If the clip was already playing, stop it
                    clipToken.stop();

                clipToken.setFramePosition(0); //Restart clip
                clipToken.start(); //Start clip
            }
            else if(type.equals("Victory"))
            {
                if(clipVictory.isRunning()) //If the clip was already playing, stop it
                    clipVictory.stop();

                clipVictory.setFramePosition(0); //Restart clip
                clipVictory.start(); //Start clip
            }
        }
        else if(action.equals("Stop"))
        {
            if(type.equals("Music"))
            {
                if(clipMusic.isRunning()) //If the clip was already playing, stop it
                    clipMusic.stop();
            }
            else if(type.equals("Token"))
            {
                if(clipToken.isRunning()) //If the clip was already playing, stop it
                    clipToken.stop();
            }
            else if(type.equals("Victory"))
            {
                if(clipVictory.isRunning()) //If the clip was already playing, stop it
                    clipVictory.stop();
            }
        }
    }
}