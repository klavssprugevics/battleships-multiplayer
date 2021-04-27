package client;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class GameGrid extends JPanel{
	
	private GameCell[][] allCells;
    private boolean shooting = false;
    private GameManager gameManager;
    
	GameGrid(int[][] ships)
	{
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        
        // Initialize cells 
        allCells = new GameCell[10][10];
        
        for (int row = 0; row < allCells.length; row++) {
            for (int col = 0; col < allCells[row].length; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                allCells[row][col] = new GameCell(this, row, col);
                allCells[row][col].setStatus(ships[row][col]);
                add(allCells[row][col], gbc);
            }
        }
	}
	
	public void revealShips()
	{
        for (int row = 0; row < allCells.length; row++) {
            for (int col = 0; col < allCells[row].length; col++) {
            	if(allCells[row][col].getStatus() == 0)
            		allCells[row][col].setBackground(new Color(158, 216, 240));
            	else
            		allCells[row][col].setBackground(Color.gray);
            }
        }
	}
	
	public void setColor(int row, int col, Color color, boolean locked)
	{
		allCells[row][col].setBackground(color);
		if(locked)
			allCells[row][col].setLocked(true);
	}
	
	public Color getColor(int row, int col)
	{
		return allCells[row][col].getBackground();
	}
	
	public void allowShoot()
	{
		setShooting(true);
	}
	
	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	// Reset 
	public void refreshGrid()
	{
        for (int row = 0; row < allCells.length; row++) {
            for (int col = 0; col < allCells[row].length; col++) {
            	if(allCells[row][col].getStatus() == 0)
            		allCells[row][col].setBackground(allCells[row][col].getBackground());
            }
        }
	}
	
	public int[][] returnField() {
		
		int field[][] = new int[10][10];
		
		for (int i = 0; i < allCells.length; i++) {
			for (int j = 0; j < allCells[i].length; j++) {
				field[i][j] = allCells[i][j].getStatus();
			}
		}
		
		return field;
	}
	
}
class GameCell extends JPanel{
	
	private int status;
	private boolean locked = false;
		
	public GameCell(GameGrid grid, int row, int col)
	{
		this.status = 0;
		
		setBackground(Color.white);            
    	setBorder(new MatteBorder(1, 1, 1, 1, new Color(79, 183, 227)));
        setPreferredSize(new Dimension(50, 50));
        
        
		addMouseListener(new MouseAdapter() {
	          @Override
	          public void mouseEntered(MouseEvent e) {
	        	  // Check if valid shooting
	        	  if(grid.isShooting() && !locked)
	        	  {
	        		  setBackground(Color.yellow);
	        	  }
	          }

	          @Override
	          public void mouseExited(MouseEvent e) {
	        	  if(grid.isShooting() && !locked)
	        	  {
	        		  setBackground(Color.white);
	        	  }
	        	  
	          }
	          
	          @Override
	          public void mousePressed(MouseEvent e)
	          {
                if (e.getButton() == MouseEvent.BUTTON1){
                	if(grid.isShooting() && !locked)
                	{
                		grid.getGameManager().shoot(row, col);
          
                	}
                }
                else if (e.getButton() == MouseEvent.BUTTON3)
                {

                    
                }
	          }
		});
		
	}
	public void setLocked(boolean locked)
	{
		this.locked = locked;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
