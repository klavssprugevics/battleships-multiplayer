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

import client.Client;
import model.Ship;

@SuppressWarnings("serial")
public class GameSetupGrid extends JPanel{
	
	private Client ply;
	private SetupCell[][] allCells;
	private int rotation; // 0 - vertical; 1 - horizontal
	private int currentShipIndex; // Which ship (length) is currently being placed
    private int[] shipOrder = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1, 0};
    private Ship[] ships;
    
    
	public GameSetupGrid(Client ply)
	{
		this.ply = ply;
		this.rotation = 1;
		this.currentShipIndex = 0;
		
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        
        // Initialize cells 
        allCells = new SetupCell[10][10];
        ships = new Ship[shipOrder.length - 1];
        
        for (int row = 0; row < allCells.length; row++) {
            for (int col = 0; col < allCells[row].length; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                allCells[row][col] = new SetupCell(this, row, col);
                add(allCells[row][col], gbc);
            }
        }

	}
	
	// Reset 
	public void refreshGrid()
	{
        for (int row = 0; row < allCells.length; row++) {
            for (int col = 0; col < allCells[row].length; col++) {
            	if(allCells[row][col].getStatus() == 0)
            		allCells[row][col].setBackground(new Color(158, 216, 240));
            	else
            		allCells[row][col].setBackground(allCells[row][col].getBackground());
            }
        }
	}

	// Funkcijas kugu zimesanai
	public Vector<Point> drawShipPreview(int row, int col)
	{
		Vector<Point> pointsToDraw = new Vector<Point>();
		
		if(!checkNeighbours(row, col))
			return pointsToDraw;
		
		if(rotation == 0)
		{
			for (int i = 0; i < this.shipOrder[this.currentShipIndex]; i++) {
				pointsToDraw.add(new Point(col, row + i));
			}
		}
		else if(rotation == 1)
		{
			for (int i = 0; i < shipOrder[currentShipIndex]; i++) {
				pointsToDraw.add(new Point(col + i, row));
			}
		}
		
		for (int i = 0; i < pointsToDraw.size(); i++) {
			 allCells[pointsToDraw.get(i).y][pointsToDraw.get(i).x].setBackground(Color.darkGray);
		}
		
		return pointsToDraw;
	}
	
	// When clicked - save ship location
	public boolean addShip(int row, int col)
	{
		Vector<Point> pointsToDraw = new Vector<Point>();
		if(!checkNeighbours(row, col))
			return false;
		
		if(rotation == 0)
		{
			for (int i = 0; i < shipOrder[currentShipIndex]; i++) {
			pointsToDraw.add(new Point(col, row + i));
			}
		}
		else if(rotation == 1)
		{
			for (int i = 0; i < shipOrder[currentShipIndex]; i++) {
			pointsToDraw.add(new Point(col + i, row));
			}
		}

	
		for (int i = 0; i < pointsToDraw.size(); i++) {
			
//				System.out.println("Ship placed!");
				ships[currentShipIndex] = new Ship(col, row, rotation, shipOrder[currentShipIndex]);
				allCells[pointsToDraw.get(i).y][pointsToDraw.get(i).x].setBackground(Color.GRAY);
				allCells[pointsToDraw.get(i).y][pointsToDraw.get(i).x].setStatus(shipOrder[currentShipIndex]);
		}
		
		
		if(currentShipIndex < shipOrder.length - 1)
			currentShipIndex++;	
		
		if(shipOrder[currentShipIndex] == 0)
		{
			
			ply.enableContinueButton();
			ply.editDescription("Press continue...");
		}
		else
		{
			ply.editDescription("Place ship with size: " + shipOrder[currentShipIndex]);
		}
			
		
		
		return true;
	}
	
	
	public Ship[] getShips() {
		return ships;
	}

	// Function checks whether a ship can be drawn/placed at the current location
	public boolean checkNeighbours(int row, int col)
	{
		
		// We cannot place a ship if there is already one there
		if(allCells[row][col].getStatus() != 0)
			return false;
		
		// Calculate if ship placement is out of bounds
		if(rotation == 0 && row + shipOrder[currentShipIndex] - 1 > 9)
		{
			return false;
		}
		else if(rotation == 1 && col + shipOrder[currentShipIndex] - 1 > 9)
		{
			return false;
		}
		
		int startIndexRow;
		int startIndexCol;
		int endIndexRow;
		int endIndexCol;
		
		Vector<Point> newShip = new Vector<Point>();
		
		// Calcualtes bounding box coordinates
		if(rotation == 1)
		{
			startIndexRow = (row - 1 < 0) ? 0 : row - 1;
			startIndexCol = (col - 1 < 0) ? 0 : col - 1;
			endIndexRow = (row + 1 > 9) ? 9 : row + 1;
			endIndexCol = (col + shipOrder[currentShipIndex] > 9) ? 9 : col + shipOrder[currentShipIndex];
			
			for (int i = 0; i < shipOrder[currentShipIndex]; i++) {
				newShip.add(new Point(col + i, row));
			}
		}
		else
		{
			startIndexRow = (row - 1 < 0) ? 0 : row - 1;
			startIndexCol = (col - 1 < 0) ? 0 : col - 1;
			endIndexRow = (row + shipOrder[currentShipIndex] > 9) ? 9 : row + shipOrder[currentShipIndex];
			endIndexCol = (col + 1 > 9) ? 9 : col + 1;
			
			for (int i = 0; i < shipOrder[currentShipIndex]; i++) {
				newShip.add(new Point(col, row + i));
			}
		}
					
		for (int i = startIndexRow; i <= endIndexRow; i++) {
			for (int j = startIndexCol; j <= endIndexCol; j++) {
				
//				System.out.print(i + ":" + j + " ");
				// Parbauda vai punkts nav jaunajam kugim
				if(newShip.contains(new Point(j, i)))
				{
//					System.out.print("SHIP POINT");
					continue;
				}
				

				if(allCells[i][j].getStatus() != 0)
				{
//					System.out.println("Ship in neighbour");
					return false;
				}				
			}
//			System.out.println(" ");
		}

		return true;
		
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getCurrentShipIndex() {
		return currentShipIndex;
	}

	public int[] getShipOrder() {
		return shipOrder;
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


@SuppressWarnings("serial")
class SetupCell extends JPanel{
//	private int row;
//	private int col;
	private int status;
		
	public SetupCell(GameSetupGrid grid, int row, int col)
	{
//		this.row = row;
//		this.col = col;
		this.status = 0;
		
    	// TODO: Function for reset colour to normal
		setBackground(new Color(158, 216, 240));            
    	setBorder(new MatteBorder(1, 1, 1, 1, new Color(79, 183, 227)));
        setPreferredSize(new Dimension(50, 50));
        
        
		addMouseListener(new MouseAdapter() {
	          @Override
	          public void mouseEntered(MouseEvent e) {
	        	  
	        	  grid.drawShipPreview(row, col);
	          }

	          @Override
	          public void mouseExited(MouseEvent e) {
	        	  
	        	  grid.refreshGrid();
	          }
	          
	          @Override
	          public void mousePressed(MouseEvent e)
	          {
                if (e.getButton() == MouseEvent.BUTTON1){
                	grid.addShip(row, col);
                }
                else if (e.getButton() == MouseEvent.BUTTON3)
                {
                    if(grid.getRotation() == 1)
                    	grid.setRotation(0);
                    else
                    	grid.setRotation(1);
                    grid.refreshGrid();
                    grid.drawShipPreview(row, col);
                    
                }
	          }
		});
		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}