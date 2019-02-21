package lab4.gui;

import lab4.data.GameGrid;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GamePanel extends JPanel implements Observer {
    private final int UNIT_SIZE = 20;
    private GameGrid grid;


    public GamePanel(GameGrid grid) {
        this.grid = grid;
        grid.addObserver(this);
        setBackground(Color.RED);
        setSize(grid.getSize()*UNIT_SIZE + 20, grid.getSize()*UNIT_SIZE + 20);
        //setBounds(0, 0, 300, 300);
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    /**
     * Find what tile player clicked on according to each size.
     *
     * @param x The clicked x-coordinate.
     * @param y The clicked y-coordinate.
     * @return Return a pair of integers of what tile has been clicked {col,row}.
     */
    public int[] getGridPosition(int x, int y) {

        return null;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("called!");
        for (int i = 0; i <= grid.getSize(); i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, UNIT_SIZE * grid.getSize());
            g.drawLine(0, i * UNIT_SIZE, UNIT_SIZE * grid.getSize(), i * UNIT_SIZE);
            
            
            
        }
        
        
        
    }
}
