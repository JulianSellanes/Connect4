/*
 * TimerManager.java
 * Created by: Julian
 * Created on: June/7/2022
 * This class controls the timer
*/

import java.text.DecimalFormat;
import javax.swing.*;
import java.util.*;
import java.util.Timer;

public class TimerManager
{
    //Declare the timer
    Timer timer = new Timer();
    Thread t;

    //Declare time variables
    int second = 0;
    int minute = 0;
    boolean stop;
    String ddSecond, ddMinute;
    DecimalFormat dFormat = new DecimalFormat("00");

    //Declare the constructor
    public TimerManager(final JLabel timerTxt)
    {
        timerTxt.setText("00:00"); //Reset timer text
        
        TimerTask timerTask = new TimerTask() //Declare the timerTask
        {
            @Override
            public void run()
            {
                //Increment seconds
                second++;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);

                timerTxt.setText(ddMinute + ":" + ddSecond); //Update timer text

                //Increment minutes
                if(second == 60)
                {
                    second = 0;
                    minute++;
                    timerTxt.setText(ddMinute + ":" + ddSecond); //Update timer text
                }
            }
        };

        t = new Thread(new Runnable() //Declare a new Thread, which will increment the seconds every 1000 milliseconds
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        if (stop)
                        {
                            timer.cancel();
                            break;
                        }

                        Thread.sleep(1000);
                    }
                    catch (InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });

        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    //Getters

    /*
     * This method returns the value of the variable "second"
     * @param Nothing.
     * @return second
    */
    public int getSecond()
    {
        return second;
    }

    /*
     * This method returns the value of the variable "minute"
     * @param Nothing.
     * @return minute
    */
    public int getMinute()
    {
        return minute;
    }

    //Setters

    /*
     * This method sets the value of the variable "second"
     * @param second
     * @return Nothing.
    */
    public void setSecond(int second)
    {
        this.second = second;
    }

    /*
     * This method sets the value of the variable "minute"
     * @param minute
     * @return Nothing.
    */
    public void setMinute(int minute)
    {
        this.minute = minute;
    }

    //Helpers

    /*
     * This method controls the timer, starts it or ends it
     * @param action
     * @return Nothing.
    */
    public void Control(String action)
    {
        if(action.equals("Start"))
        {
            stop = false;
            t.start();
        }
        else if(action.equals("Stop"))
        {
            stop = true;
            timer.cancel();
        }
    }
}