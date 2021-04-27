package client;
import java.awt.Point;

public class Player {

	public static int rows = 10;
	public static int columns = 10;
	private int playerField[][];
	private Ship playerShips[];
	private String playerName;
	
	Player(String name)
	{
		playerName = name;
		playerField =  new int[rows][columns];
		
		for (int i = 0; i < playerField.length; i++) {
			for (int j = 0; j < playerField[i].length; j++) {
				playerField[i][j] = 0;
			}
		}
		
		
	}
	public Ship getShipByCoordinates(int row, int col)
	{
		Ship ship = null;
		for (int i = 0; i < playerShips.length; i++) {
			if(playerShips[i].getCoordinates().contains(new Point(col, row)))
			{
				ship = playerShips[i];
			}
		}
		return ship;
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Ship[] getPlayerShips()
	{
		return playerShips;
	}
	
	public void setPlayerShips(Ship[] playerShips)
	{
		this.playerShips = playerShips;
	}
	
	public int[][] getPlayerField()
	{
		return playerField;
	}
	
	public void setPlayerField(int[][] playerField) {
		this.playerField = playerField;
	}

	public void printPlayerField()
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				System.out.print(playerField[i][j]);
			}
			System.out.println();
		}
		System.out.println("=================================================");
	}


}
