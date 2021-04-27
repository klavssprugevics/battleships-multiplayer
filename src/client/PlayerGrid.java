package client;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlayerGrid extends JPanel{

	
	private FriendlyCell[][] allCells;
    
	PlayerGrid(int[][] playerField)
	{
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        
        // Initialize cells 
        allCells = new FriendlyCell[10][10];
        
        for (int row = 0; row < allCells.length; row++) {
            for (int col = 0; col < allCells[row].length; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                allCells[row][col] = new FriendlyCell(this, row, col);
                allCells[row][col].setStatus(playerField[row][col]);
                add(allCells[row][col], gbc);
            }
        }
        revealPlayerField();
	}
	
	public void revealPlayerField()
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
	
	public void refreshGrid()
	{
        for (int row = 0; row < allCells.length; row++) {
            for (int col = 0; col < allCells[row].length; col++) {
            	if(allCells[row][col].getStatus() == 0)
            		allCells[row][col].setBackground(allCells[row][col].getBackground());
            }
        }
	}
	
}
