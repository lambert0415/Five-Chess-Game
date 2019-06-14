package game.test;

import game.frame.ChessFrame;
import game.frame.FiveChessFrame;

import javax.swing.JOptionPane;

import java.awt.Toolkit;

public class Test {
    public static void main(String[] args){
        /* Game input option 
        ChessFrame cf = new ChessFrame();

        int result = JOptionPane.showConfirmDialog(cf,"Start the new game?");
        //yes
        if(result == 0){
            JOptionPane.showConfirmDialog(cf,"Game Started!");
        //no
        }else if (result == 1){
            JOptionPane.showConfirmDialog(cf,"Game Over!");
        }else {
            JOptionPane.showConfirmDialog(cf,"Select again!");
        }

        String username = JOptionPane.showInputDialog("Please enter your name: ");
        if (username != null) {
            System.out.print(username );
            JOptionPane.showMessageDialog(cf, "Your name is: " + username);
        } else {
            JOptionPane.showMessageDialog(cf, "Please enter your name again!: ");
        }
        */
        FiveChessFrame ff = new FiveChessFrame();
    }
}
