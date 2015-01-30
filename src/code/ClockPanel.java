package code;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public final class ClockPanel extends JPanel 
{ 
    JLabel current;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    int milliseconds = 0;
    int count = 0;
    
    String hoursString = "00";
    String minutesString = "00";
    String secondsString = "00";
    String millisecondsString = "00";
    
    
    public ClockPanel() 
    {
            current = new JLabel();
            current.setFont(new java.awt.Font("Tahoma", 0, 24));
            setBackground(Color.white);
            add(current);
            new Timer(10, (ActionEvent event) -> {
                if(count == 0)
                {
                    current.setText(hoursString + ":" + minutesString + ":" + secondsString + ":" + millisecondsString);
                    milliseconds++;
                    formatNumbers();
                }
            }).start();
    }
    public String getTime()
    {
        return hoursString + ":" + minutesString + ":" + secondsString + ":" + millisecondsString;
    }
    public void formatNumbers()
    {
        if(milliseconds == 100)
        {
            milliseconds = 0;
            seconds++;
        }
        if(seconds == 60)
        {
            seconds = 0;
            minutes++;
        }
        if(minutes == 60)
        {
            minutes = 0;
            hours++;
        }
        hoursString = hours+"";
        minutesString = minutes+"";
        secondsString = seconds+"";
        millisecondsString = milliseconds+"";
        if(hours<10)
        {
            hoursString = "0" + hours;
        }
        if(minutes<10)
        {
            minutesString = "0" + minutes;
        }
        if(seconds<10)
        {
            secondsString = "0" + seconds;
        }
        if(milliseconds<10)
        {
            millisecondsString = milliseconds+"0";
        }    
    }
}