package client;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class FriendlyCell extends JPanel{
	
	private int status;
	
	public FriendlyCell(PlayerGrid grid, int row, int col)
	{
		this.status = 0;
		
		setBackground(Color.white);            
    	setBorder(new MatteBorder(1, 1, 1, 1, new Color(79, 183, 227)));
        setPreferredSize(new Dimension(50, 50));
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
