package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class EnemyCell extends JPanel{
	
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
