package lab4.gui;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class GomokuGUI implements Observer {
    /**
     * @param gomokoClient
     * @param gameState
     */
    public GomokuGUI(GomokuClient gomokoClient, GomokuGameState gameState) {
        JFrame frame = new JFrame("Gomoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        

        JPanel gameGridPanel = new GamePanel(gameState.getGameGrid());
        gameGridPanel.setLayout(new BoxLayout(gameGridPanel, BoxLayout.PAGE_AXIS));
		
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        
  	    JLabel messageLabel = new JLabel("hejsan");
        JButton connectButton = new JButton("Connect");
        JButton newGameButton = new JButton("New Game");
        JButton disconnectButton = new JButton("Disconnect");
        
        
        p1.add(connectButton);
    	p1.add(newGameButton);
        p1.add(disconnectButton);
        gameGridPanel.add(p1, BorderLayout.CENTER);
        gameGridPanel.add(messageLabel, BorderLayout.LINE_END);
        
        
   	
		frame.setContentPane(gameGridPanel);

        //frame.add(gameGridPanel);
        
        gameGridPanel.addMouseListener((new MouseAdapter(){
        	
        	public void mouseClicked(MouseEvent e) {
        		int x = e.getX();
        		int y = e.getY();
        		System.out.println("x: "+x+", y: "+y);
        		System.out.println("Ruta: "+(x/20+1) + ", " + (y/20+1));
        		
        		if(x > 300 || y > 300 ) { //Ändra från 300 till storleken på spelplanen
        			System.out.println("Utanför spelplanen");
        		}else {
        			gameState.move(x, y);
        		}
        		
        	}
        }));
        
        connectButton.addActionListener((new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		new ConnectionWindow(gomokoClient);
        	}

			
        }));
        
        newGameButton.addActionListener((new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		gameState.newGame();
        	}
        }));
        
        disconnectButton.addActionListener((new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e) {
        		gameState.disconnect();
        	}
        }));
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //frame.setSize(400,400);

        
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
