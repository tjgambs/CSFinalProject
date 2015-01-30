package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public final class CreateGame extends JPanel implements ActionListener, KeyListener
{
    int flag = 0;
    
    int numOfBalls;
    int velocity;
    boolean gameOver = false;

    int count = 0;
    
    int highScoreHour;
    int highScoreMinute;
    int highScoreSecond;
    int highScoreMillisecond;
    
    private final int VELOCITY_OF_PLAYER = 5;
    private final int RADIUS_OF_BALL = 20;
    private final int WIDTH = 900-RADIUS_OF_BALL; //depends on the image
    private final int HEIGHT = 722-RADIUS_OF_BALL; //depends on the image
    private final int SPACING = 50+RADIUS_OF_BALL; //depends on the image
    
    double xOfPlayer = 450;
    double yOfPlayer = 375;
    double xVOfPlayer = 0;
    double yVOfPlayer = 0;
    double SIDE_OF_PLAYER = 20;
    
    double[] xValues;
    double[] yValues;
    double[] xVelocities;
    double[] yVelocities;
    
    double[] xVelocitiesTemp;
    double[] yVelocitiesTemp;
    double xVOfPlayerTemp = 0;
    double yVOfPlayerTemp = 0;
    
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    int milliseconds = 0;
    
    Timer timer = new Timer(25, this);
    JPanel t;
    JButton pause;
    JPanel highScore;
    
    ClockPanel time;
    JLabel h;
    public CreateGame(int numBalls, int velocityInput)
    {
        h = new JLabel("High Score: 00:00:00:00");
        t  = new JPanel();
        pause = new JButton();
        highScore = new JPanel();
        highScoreSet();
        pauseButton();
        timerSet();
        setFocusable(true);
        velocity = velocityInput;
        numOfBalls = numBalls;
        xValues = new double[numOfBalls];
        yValues = new double[numOfBalls];
        xVelocities = new double[numOfBalls];
        yVelocities = new double[numOfBalls]; 
        xVelocitiesTemp = new double[numOfBalls];
        yVelocitiesTemp = new double[numOfBalls]; 
        addKeyListener(this);
        setFocusTraversalKeysEnabled(false);
        add(highScore);
        add(t);
        add(pause); 
    }
    public void highScoreSet()
    {
        h.setFont(new java.awt.Font("Tahoma", 0, 24));
        highScore.setBounds(600,8,300,60);
        highScore.setBackground(new Color(192, 192, 192));
        highScore.setFocusable(false);
        highScore.add(h);
    }
    public void timerSet()
    {
        t.setBounds(300,8,300,60);
        t.setBackground(Color.WHITE);
        t.setFocusable(false);
    }
    public void pauseButton()
    {
        pause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Play.png")));
        pause.setBounds(20,670,25,30);
        pause.setFocusable(false);
        pause.setContentAreaFilled(false);
        pause.setBorderPainted(false);
        pause.setBackground(Color.white);
        pause.setFont(new java.awt.Font("Tahoma", 0, 24));
        ifPauseButtonClick();
    }
    public void ifPauseButtonClick()
    {
        pause.addActionListener((ActionEvent e) -> {
            checkIfCollision();
            if(gameOver == false) //if the game is still running/ so it will act as a pause
            {
                if(count%2 == 0 && count == 0) //start game
                {
                    restartGame();
                    time = new ClockPanel();
                    pause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Pause.png")));
                    t.add(time);
                    time.count = 0;
                }
                else if(count%2 == 0) //play
                {
                    playGame();
                    pause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Pause.png")));
                    time.count = 0;
                }
                else if(count%2 == 1) //pause
                {
                    pauseGame();
                    pause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Play.png")));
                    time.count = 1;
                }
                count++;
            }
            if(gameOver == true) //if the game is over, restart the game
            {
                time.count = 0;
                time.hours = 0;
                time.minutes = 0;
                time.seconds = 0;
                time.milliseconds = 0;
                pause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Pause.png")));
                restartGame();
            }
        });
    }
    public void pauseGame()
    {      
        xVOfPlayerTemp = xVOfPlayer;
        xVOfPlayer = 0;
        yVOfPlayerTemp = yVOfPlayer;
        yVOfPlayer = 0;
        for(int i = 0; i<xVelocities.length; i++)
        {
            xVelocitiesTemp[i] = xVelocities[i];
            xVelocities[i] = 0;
            yVelocitiesTemp[i] = yVelocities[i];
            yVelocities[i] = 0;
        }
    }
    public void playGame()
    {
        xVOfPlayer = xVOfPlayerTemp;
        xVOfPlayerTemp = 0;
        yVOfPlayer = yVOfPlayerTemp;
        yVOfPlayerTemp = 0;
        for(int i = 0; i<xVelocitiesTemp.length; i++)
        {
            xVelocities[i] = xVelocitiesTemp[i];
            xVelocitiesTemp[i] = 0;
            yVelocities[i] = yVelocitiesTemp[i];
            yVelocitiesTemp[i] = 0;
        }
    }
    public void restartGame()
    {
        gameOver = false;
        fillValues();
        xOfPlayer = 450;
        yOfPlayer = 375;
        xVOfPlayer = 0;
        yVOfPlayer = 0;
        
    }
    public void decideDirection(int i)
    {
        int random = (int)(Math.random()*4); //decides which way the ball will go
        int xPosition = (int)(Math.random()*WIDTH) + SPACING;
        int yPosition = (int)(Math.random()*HEIGHT) + SPACING;
        if(random == 0) //ball goes down
        {
            xValues[i] = xPosition;
            yValues[i] = SPACING;
            xVelocities[i] = 0;
            yVelocities[i] = velocity;
        }
        if(random == 1) // ball goes up
        {
            xValues[i] = xPosition;
            yValues[i] = HEIGHT;
            xVelocities[i] = 0;
            yVelocities[i] = -velocity;
        }
        if(random == 2) //ball goes right
        {
            xValues[i] = SPACING;
            yValues[i] = yPosition;  
            xVelocities[i] = velocity;
            yVelocities[i] = 0;
        }
        if(random == 3) // ball goes left
        {
            xValues[i] = WIDTH;
            yValues[i] = yPosition;
            xVelocities[i] = -velocity;
            yVelocities[i] = 0;
        }
    }
    public void fillValues()
    {
        for(int i = 0; i<numOfBalls; i++)
        {
            decideDirection(i);
        }
    }
    @Override
    public void paintComponent(Graphics g)
    {
        Color contrast = new Color(192, 192, 192);
        Color background = new Color(160, 160, 160);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(background);
        g2.fill(new Rectangle2D.Double(0,0,900,744)); //makes background
        g2.setColor(Color.BLACK);
        
        for(int i = 0; i<numOfBalls; i++)
        {
            g2.fill(new Ellipse2D.Double(xValues[i],yValues[i],RADIUS_OF_BALL,RADIUS_OF_BALL));
            timer.start();
        }
        g2.fill(new Rectangle2D.Double(xOfPlayer, yOfPlayer, SIDE_OF_PLAYER, SIDE_OF_PLAYER)); //makes the player
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle2D.Double(0,0,70,744)); //makes left background
        g2.setColor(contrast);
        g2.fill(new Rectangle2D.Double(0,0,900,70)); //makes top contrast with white
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle2D.Double(300,0,300,70)); //makes top white box
        g2.setColor(Color.black);
        g2.fill(new Rectangle2D.Double(70,70,3,674)); //makes the borders
        g2.fill(new Rectangle2D.Double(897,70,3,674)); //makes the borders
        g2.fill(new Rectangle2D.Double(0,70,900,3)); //makes the borders
        g2.fill(new Rectangle2D.Double(0,719,900,3)); //makes the borders
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        for(int i = 0; i<numOfBalls; i++)
        {
            if(yValues[i]>HEIGHT || yValues[i]<SPACING)
            {
                if(xVelocities[i]>0 || xVelocities[i]<0) decideDirection(i);
                if(yVelocities[i]>0 || yVelocities[i]<0) decideDirection(i);
            }
            if(xValues[i]>WIDTH || xValues[i]<SPACING)
            {
                if(xVelocities[i]>0 || xVelocities[i]<0) decideDirection(i);
                if(yVelocities[i]>0 || yVelocities[i]<0) decideDirection(i);
            }
            xValues[i]+=xVelocities[i];
            yValues[i]+=yVelocities[i];
        }
        if(yOfPlayer>=HEIGHT || yOfPlayer<=SPACING)
        {
            if(xVOfPlayer>0 || xVOfPlayer<0) xVOfPlayer = -xVOfPlayer; //turn the player around
            if(yVOfPlayer>0 || yVOfPlayer<0) yVOfPlayer = -yVOfPlayer; //turn the player around
        }
        if(xOfPlayer>=WIDTH || xOfPlayer<=SPACING)
        {
            if(xVOfPlayer>0 || xVOfPlayer<0) xVOfPlayer = -xVOfPlayer; //turn the player around
            if(yVOfPlayer>0 || yVOfPlayer<0) yVOfPlayer = -yVOfPlayer; //turn the player around
        }
        xOfPlayer+=xVOfPlayer;
        yOfPlayer+=yVOfPlayer;
        checkIfCollision();
        repaint();
    }
    public void checkIfCollision() //end the game
    {
        for(int i = 0; i<numOfBalls; i++)
        {
            if(xValues[i]>xOfPlayer-RADIUS_OF_BALL && xValues[i]<xOfPlayer+RADIUS_OF_BALL &&
                    yValues[i]>yOfPlayer-RADIUS_OF_BALL && yValues[i]<yOfPlayer+RADIUS_OF_BALL)
            {
                gameOver = true;
                pause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Play.png")));
                time.count = 1;
                gameOver();
                break;
            }
        }
    }
    public void fillHighScore()
    {
        highScoreHour = time.hours;
        highScoreMinute = time.minutes;
        highScoreSecond = time.seconds;
        highScoreMillisecond = time.milliseconds;
    }
    public void gameOver()
    {
        xVOfPlayer = 0; 
        yVOfPlayer = 0;
        for(int i = 0; i<numOfBalls; i++)
        {
            xVelocities[i] = 0;
            yVelocities[i] = 0;
        }
    }
    public void goUp()
    {
        if(time.count == 0)
        {
            xVOfPlayer = 0;
            yVOfPlayer = -VELOCITY_OF_PLAYER;
        }
    }
    public void goDown()
    {
        if(time.count == 0)
        {
            xVOfPlayer = 0;
            yVOfPlayer = VELOCITY_OF_PLAYER;
        } 
    }
    public void goLeft()
    {
        if(time.count == 0)
        {
            xVOfPlayer = -VELOCITY_OF_PLAYER;
            yVOfPlayer = 0;
        }
    }
    public void goRight()
    {
        if(time.count == 0)
        {
            xVOfPlayer = VELOCITY_OF_PLAYER;
            yVOfPlayer = 0;
        }
    }
    public void stop()
    {
        if(time.count == 0)
        {
            xVOfPlayer = 0;
            yVOfPlayer = 0;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver == false)
        {
            int input = e.getKeyCode();
            if(input == KeyEvent.VK_UP) goUp();
            if(input == KeyEvent.VK_DOWN) goDown();
            if(input == KeyEvent.VK_RIGHT) goRight();
            if(input == KeyEvent.VK_LEFT) goLeft();
            if(input == KeyEvent.VK_SPACE) ifPauseButtonClick();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(gameOver == false)
        {
            int input = e.getKeyCode();
            if(input == KeyEvent.VK_UP) goUp();
            if(input == KeyEvent.VK_DOWN) goDown();
            if(input == KeyEvent.VK_RIGHT) goRight();
            if(input == KeyEvent.VK_LEFT) goLeft();
        }
    }  
}