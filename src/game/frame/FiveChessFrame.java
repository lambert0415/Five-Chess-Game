package game.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FiveChessFrame extends JFrame implements MouseListener, Runnable {

    //get screen resolution(width,height)
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;

    int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    BufferedImage bgImage = null;

    int x = 0;
    int y = 0;

    //save all chess coordinates
    //0: no chess, 1: black chess, 2: white chess
    int[][] allChess = new int[19][19];

    //set black chess go first
    boolean isBlack = true;

    boolean canPlay = true;

    String message = "Black First";

    int maxTime = 0;
    //current window thread control 
    Thread t = new Thread(this);

    int blackTime = 0;
    int whiteTime = 0;

    String blackMessage = "Unlimited";
    String whiteMessage = "Unlimited";

    public FiveChessFrame() {

        this.setTitle("Five Chess Game");

        this.setSize(500, 500);

        this.setLocation((width - 500) / 2, (height - 500) / 2);

        this.setResizable(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addMouseListener(this);

        this.setVisible(true);

        t.start();
        t.suspend();


        this.repaint();
        String imagePath = "" ;
        try {
            imagePath = "/Users/lambert/Desktop/FiveChessProject/wuzi_meitu.jpg" ;
            bgImage = ImageIO.read(new File(imagePath.replaceAll("\\\\", "/")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
    	
    	//prevent from screen flashing
        BufferedImage bi = new BufferedImage(500, 500,
                BufferedImage.TYPE_INT_RGB);
        Graphics g2 = bi.createGraphics();
        g2.setColor(Color.BLACK);

        g2.drawImage(bgImage, 1, 20, this);

        g2.setFont(new Font("TimesNewRoman", Font.BOLD, 20));
        g2.drawString("Game Info: " + message, 130, 60);

        g2.setFont(new Font("TimesNewRoman", 0, 14));
        g2.drawString("Black Player Time:" + blackMessage, 30, 470);
        g2.drawString("White Player Time:" + whiteMessage, 260, 470);


        //draw chess lines
        for (int i = 0; i < 19; i++) {
            g2.drawLine(10, 70 + 20 * i, 370, 70 + 20 * i);
            g2.drawLine(10 + 20 * i, 70, 10 + 20 * i, 430);
        }

        //draw 9 dots on the chess UI
        g2.fillOval(68, 128, 4, 4);
        g2.fillOval(308, 128, 4, 4);
        g2.fillOval(308, 368, 4, 4);
        g2.fillOval(68, 368, 4, 4);
        g2.fillOval(308, 248, 4, 4);
        g2.fillOval(188, 128, 4, 4);
        g2.fillOval(68, 248, 4, 4);
        g2.fillOval(188, 368, 4, 4);
        g2.fillOval(188, 248, 4, 4);

        //draw all chess
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                //black chess
                if (allChess[i][j] == 1) {
                    // the length between 1st column and left border is 10
                    int tempX = i * 20 + 10;
                    int tempY = j * 20 + 70;
                    g2.fillOval(tempX - 7, tempY - 7, 14, 14);
                }
                //white chess
                if (allChess[i][j] == 2) {
                    //
                    int tempX = i * 20 + 10;
                    int tempY = j * 20 + 70;
                    g2.setColor(Color.WHITE);
                    g2.fillOval(tempX - 7, tempY - 7, 14, 14);
                    //give white chess black surrounding
                    g2.setColor(Color.BLACK);
                    g2.drawOval(tempX - 7, tempY - 7, 14, 14);
                }
            }
        }
        g.drawImage(bi, 0, 0, this);
    }

    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        /*
         * System.out.println("X:"+e.getX()); System.out.println("Y:"+e.getY());
         */
        if (canPlay == true) {
            x = e.getX();
            y = e.getY();
            if (x >= 10 && x <= 370 && y >= 70 && y <= 430) {
            	//save the coordinates 
                x = (x - 10) / 20;
                y = (y - 70) / 20;
                //check if there is a chess on the coordinate 
                if (allChess[x][y] == 0) {
                    //check current chess color
                    if (isBlack == true) {
                        allChess[x][y] = 1;
                        isBlack = false;
                        message = "White Player";
                    } else {
                        allChess[x][y] = 2;
                        isBlack = true;
                        message = "Black Player";
                    }
                    //
                    boolean winFlag = this.checkWin();
                    if (winFlag == true) {
                        JOptionPane.showMessageDialog(this, "Game Over!,"
                                + (allChess[x][y] == 1 ? "Black" : "White") + "Wins");
                        canPlay = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Coordinate has been taken, pick another one!");
                }
                this.repaint();
            }
        }
        /* System.out.println(e.getX() + " -- " + e.getY()); */

        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 70
                && e.getY() <= 100) {
            int result = JOptionPane.showConfirmDialog(this, "Start New Game?");
            if (result == 0) {
            	//clear all chess coordinates
                for (int i = 0; i < 19; i++) {
                    for (int j = 0; j < 19; j++) {
                        allChess[i][j] = 0;
                    }
                }
                //allChess = new int[19][19];
                message = "Black First";
                isBlack = true;
                blackTime = maxTime;
                whiteTime = maxTime;
                if (maxTime > 0) {
                    blackMessage = maxTime / 3600 + ":"
                            + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                            + (maxTime - maxTime / 60 * 60);
                    whiteMessage = maxTime / 3600 + ":"
                            + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                            + (maxTime - maxTime / 60 * 60);
                    t.resume();
                } else {
                    blackMessage = "Unlimited";
                    whiteMessage = "Unlimited";
                }
                this.canPlay = true;
                this.repaint();

            }
        }
        
        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 120
                && e.getY() <= 150) {
            String input = JOptionPane
                    .showInputDialog("Game Round Time Setting Minutes (0 for unlimited):");
            try {
                maxTime = Integer.parseInt(input) * 60;
                if (maxTime < 0) {
                    JOptionPane.showMessageDialog(this, "No negative Value!");
                }
                if (maxTime == 0) {
                    int result = JOptionPane.showConfirmDialog(this,
                            "Set Complete,Start New Game?");
                    if (result == 0) {
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j < 19; j++) {
                                allChess[i][j] = 0;
                            }
                        }
                        //  allChess = new int[19][19];
                        message = "Black First";
                        isBlack = true;
                        blackTime = maxTime;
                        whiteTime = maxTime;
                        blackMessage = "??????";
                        whiteMessage = "??????";
                        this.canPlay = true;
                        this.repaint();
                    }
                }
                if (maxTime > 0) {
                    int result = JOptionPane.showConfirmDialog(this,
                            "Set Complete,Start New Game?");
                    if (result == 0) {
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j < 19; j++) {
                                allChess[i][j] = 0;
                            }
                        }
                        // allChess = new int[19][19];
                        message = "Black First";
                        isBlack = true;
                        blackTime = maxTime;
                        whiteTime = maxTime;
                        blackMessage = maxTime / 3600 + ":"
                                + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                                + (maxTime - maxTime / 60 * 60);
                        whiteMessage = maxTime / 3600 + ":"
                                + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                                + (maxTime - maxTime / 60 * 60);
                        t.resume();
                        this.canPlay = true;
                        this.repaint();
                    }
                }
            } catch (NumberFormatException e1) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(this, "Please Input Number !");
            }
        }
        //
        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 170
                && e.getY() <= 200) {
            JOptionPane.showMessageDialog(this,
                    "player got 5 chesses in any direction wins!"); 
        }
        //
        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 270
                && e.getY() <= 300) {
            int result = JOptionPane.showConfirmDialog(this, "Make Sure To Surrender?");
            if (result == 0) {
                if (isBlack) {
                    JOptionPane.showMessageDialog(this, "Black Quit,Game Over!");
                } else {
                    JOptionPane.showMessageDialog(this, "White Quit,Game Over!");
                }
                canPlay = false;
            }
        }

        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 320
                && e.getY() <= 350) {
            JOptionPane.showMessageDialog(this,
                    "GAME MAKER: Su 2019-06-20");
        }

        if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 370
                && e.getY() <= 400) {
            JOptionPane.showMessageDialog(this, "Game End");
            System.exit(0);
        }
    }

    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    private boolean checkWin() {
        boolean flag = false;
        //total same color chess count 
        int count = 1;
        // check 5 chesses at the same line
        int color = allChess[x][y];
        
        //horizontal line check 
        count = this.checkCount(1, 0, color);
        if (count >= 5) {
            flag = true;
        } else {
        	//vertical line check 
            count = this.checkCount(0, 1, color);
            if (count >= 5) {
                flag = true;
            } else {
            	//right diagonal line check 
                count = this.checkCount(1, -1, color);
                if (count >= 5) {
                    flag = true;
                } else {
                	//left diagonal line check 
                    count = this.checkCount(1, 1, color);
                    if (count >= 5) {
                        flag = true;
                    }
                }
            }
        }

        return flag;
    }


    private int checkCount(int xChange, int yChange, int color) {
        int count = 1;
        //default value of change
        int tempX = xChange;
        int tempY = yChange;
        while (x + xChange >= 0 && x + xChange <= 18 && y + yChange >= 0
                && y + yChange <= 18
                && color == allChess[x + xChange][y + yChange]) {
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0) {
                if (yChange > 0)
                    yChange++;
                else {
                	
                    yChange--;
                }
            }
        }
        xChange = tempX;
        yChange = tempY; 
        while (x - xChange >= 0 && x - xChange <= 18 && y - yChange >= 0
                && y - yChange <= 18
                && color == allChess[x - xChange][y - yChange]) {
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0) {
                if (yChange > 0)
                    yChange++;
                else {
                    yChange--;
                }
            }
        }
        return count;
    }

    public void run() {
        // TODO Auto-generated method stub
        // check if there is time limit
        if (maxTime > 0) {
            while (true) {
                if (isBlack) {
                    blackTime--;
                    if (blackTime == 0) {
                        JOptionPane.showMessageDialog(this, "Black Time Out, Game Over!");
                    }
                } else {
                    whiteTime--;
                    if (whiteTime == 0) {
                        JOptionPane.showMessageDialog(this, "White Time Out,Game Over!");
                    }
                }
                blackMessage = blackTime / 3600 + ":"
                        + (blackTime / 60 - blackTime / 3600 * 60) + ":"
                        + (blackTime - blackTime / 60 * 60);
                whiteMessage = whiteTime / 3600 + ":"
                        + (whiteTime / 60 - whiteTime / 3600 * 60) + ":"
                        + (whiteTime - whiteTime / 60 * 60);
                this.repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(blackTime + " -- " + whiteTime);
            }
        }
    }

}
