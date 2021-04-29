package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import client.ServerConnection;
import model.Ship;
import model.Shot;

@SuppressWarnings("serial")
public class EnemyGrid extends JPanel{
	
	private ServerConnection connection;
	
	private EnemyCell[][] allCells;
	private boolean shooting = false;
    
    public EnemyGrid(ServerConnection server)
	{
    	this.connection = server;
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Initialize cells 
        allCells = new EnemyCell[10][10];
        
        for (int row = 0; row < allCells.length; row++) {
            for (int col = 0; col < allCells[row].length; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                allCells[row][col] = new EnemyCell(this, row, col);
                allCells[row][col].setStatus(0);
                add(allCells[row][col], gbc);
            }
        }
	}
		
	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
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

	public void callShot(int row, int col) {
		
		connection.sendShot(new Shot(col, row));
	}
	
	public void drawBoundingBox(Ship ship)
	{
		Vector<Point> shipCoords = ship.getCoordinates();
		int col = ship.getxPos();
		int row = ship.getyPos();
		int rotation = ship.getRotation();
		int size = ship.getSize();
	
		int startIndexRow;
		int startIndexCol;
		int endIndexRow;
		int endIndexCol;
				
		// Calcualtes bounding box coordinates
		if(rotation == 1)
		{
			startIndexRow = (row - 1 < 0) ? 0 : row - 1;
			startIndexCol = (col - 1 < 0) ? 0 : col - 1;
			endIndexRow = (row + 1 > 9) ? 9 : row + 1;
			endIndexCol = (col + size > 9) ? 9 : col + size;
		}
		else
		{
			startIndexRow = (row - 1 < 0) ? 0 : row - 1;
			startIndexCol = (col - 1 < 0) ? 0 : col - 1;
			endIndexRow = (row + size > 9) ? 9 : row + size;
			endIndexCol = (col + 1 > 9) ? 9 : col + 1;
		}
		
		for (int i = startIndexRow; i <= endIndexRow; i++) {
			for (int j = startIndexCol; j <= endIndexCol; j++) {
				if(shipCoords.contains(new Point(j, i)))
				{
					continue;
				}
				else
				{
					setColor(i, j, new Color(158, 216, 240), true);
				}
			}			
		}
	}
}

@SuppressWarnings("serial")
class EnemyCell extends JPanel{
	
	private int status;
	private boolean locked = false;
		
	public EnemyCell(EnemyGrid grid, int row, int col)
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
                		grid.callShot(row, col);
                		setLocked(true);
                	}
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
