import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.Random;
/**
* A game of Mancala
* @author Divine Ndaya Badibanga
*/

public class Board extends JFrame implements ActionListener{

    private int x, y;  //dimensions of the grid
    private JPanel panel;
    private Cell [][] cells; //array containing all the cells

    public Board(){
        //initiate instance variables
        x = 2;
        y = 8;
        //create the window
        panel = new JPanel();
        JFrame aFrame = new JFrame();
        panel.setLayout(new GridLayout(x,x,1,1));
        panel.setPreferredSize(new Dimension(y*y*10,y*y*10));
        
        //build the grid with cells on it
        genesis();
    }

    /**
     * Genesis creates the grid of cells
     *
     */
    public void genesis()
    {
        //create a x by y grid and add cells
        cells = new Cell [x][y];
        for (int i=0; i<x; i++)
        {
            for (int j=0; j<y; j++)
            {
                cells [i][j] = new Cell(i,j);
                cells [i][j].setSize(200,200);
                cells [i][j].setOpaque(true);
                cells [i][j].setBorderPainted(true);
                cells [i][j].setText(String.valueOf(cells [i][j].getScore()));
                
                cells [i][j].addActionListener((ActionListener) this);
                
                panel.add(cells [i][j]);
            }
        }
        
        //set the display
        getContentPane().add(panel);
        panel.setVisible(true);
        
        pack();
        
        panel.setLayout(new GridLayout(x,x,1,1));
        
        //housekeeping
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /*
     *  handles actions performed in the gui
     *  this method must be present to correctly implement the ActionListener interface
     */
    public void actionPerformed (ActionEvent aevt)
    {
        // get the object that was selected in the gui
        Object selected = aevt.getSource();
        
        /*
         * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
         * with just one piece of code.
         * In a real system you'll probably have one piece of action code per selectable item.
         * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
         */
        
        // if a gridsquare is selected then transfer seeds
        if ( selected instanceof Cell){
            var score = ((Cell)selected).getScore();
            System.out.println("this cell's seed bank is " + score);
            ((Cell)selected).prendre();
            System.out.println("now it's " + ((Cell)selected).getScore());
            var x = ((Cell)selected).getx();
            var y = ((Cell)selected).gety();
            while (score > 0){
                if (x == 0){
                    y -= 1; 
                    cells [x][y].donner();
                    score -= 1;
                    
                    System.out.println(x + ", " + y + " cell's seed bank is " + cells [x][y].getScore());
                    if (y == 0){
                        x = 1;
                        y = 0;
                    }
                }
                if (x == 1){
                    y += 1;
                    cells [x][y].donner();
                    score -= 1;
                    System.out.println(x + ", " + y + " cell's seed bank is " + cells [x][y].getScore());
                    if (y == 7){
                        x = 0;
                        y = 7;
                    }
                }   
            }
        }
        update();
    }

    public void update(){
        for (int i=0; i<x; i++)
        {
            for (int j=0; j<y; j++)
            {
                panel.add(cells [i][j]);}}
        //set the display
        getContentPane().add(panel);
        panel.setVisible(true);
        
        //housekeeping
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }
}