package code;

import javax.swing.JFrame;

public class RunnerTest
{
    public static void main(String args[])
    {
        CreateGame game = new CreateGame(10,10);//first is number of balls. second is velocity of balls
        JFrame f = new JFrame();
        f.add(game);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game.setLayout(null);
        game.setSize(900, 744);
        
        f.setResizable(false);
        f.setSize(900,744);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        
    }
}
