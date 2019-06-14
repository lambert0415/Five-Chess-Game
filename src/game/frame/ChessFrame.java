package game.frame;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ChessFrame extends JFrame implements MouseListener{

    public ChessFrame(){

        this.setTitle("Five Chess Game");
        //default size
        this.setSize(1024,768);


        //able to change the window size
        this.setResizable(true);

        //terminate program when close window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //get current screen size
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        //System.out.println("Screen size is" + width + " * " + height);

        //set window at center of the screen
        this.setLocation((width-1024)/2,(height-768)/2);
        this.addMouseListener(this);

        this.setVisible(true);
    }

    public void paint(Graphics g){
        /*
         g.drawString("Five Chess Game",20,40);
        g.drawOval(20,40,40,40);
        */
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("/Users/lambert/Desktop/FiveChessProject/background.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(image,0,0,this);


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
