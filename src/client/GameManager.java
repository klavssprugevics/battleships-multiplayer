package client;
import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

public class GameManager {
	
	private Client cl;
	private Player player1;
	private Player player2;
	private GameGrid player1Grid;
	private GameGrid player2Grid;
	private int[][] player1Ships;
	private int[][] player2Ships;
	private Vector<Point> player1Hits = new Vector<Point>();
	private Vector<Point> player2Hits = new Vector<Point>();
	private int[] player1LastShot = {-1, -1};
	private int[] player2LastShot = {-1, -1};
	private Player currentTurn;
	private boolean gameOngoing;

	
	public GameManager(Client cl, GameGrid player1Grid, GameGrid player2Grid, Player player1, Player player2)
	{
		this.cl = cl;
		this.player1 = player1;
		this.player2 = player2;
		this.player1Grid = player1Grid;
		this.player2Grid = player2Grid;
		this.player1Ships = player1Grid.returnField();
		this.player2Ships = player2Grid.returnField();
		this.gameOngoing = true;
		this.currentTurn = player1;
		
		// changes enemy grid to white
		for (int i = 0; i < player2Ships.length; i++) {
			for (int j = 0; j < player2Ships[i].length; j++) {
				player2Grid.setColor(i, j, Color.white, false);
			}
		}
		
		player1Grid.setGameManager(this);
		player2Grid.setGameManager(this);
		startGame();
		
	}
	
	public void startGame()
	{
		if(currentTurn.equals(player1))
		{
			player2Grid.setShooting(true);
		}
		else
		{
			
		}

	}
	
	public void shoot(int row, int col)
	{
		
		// function to check if valid shot
		if(currentTurn.equals(player1))
		{
			if(player2Ships[row][col] == 0)
			{
				player2Grid.setColor(row, col, new Color(158, 216, 240), true);
				System.out.println("Miss!");
			}
			else
			{
				player2Grid.setColor(row, col, Color.red, true);
				System.out.println("Hit!");
				player1Hits.add(new Point(col, row));
				checkShipSinked(row, col);
				player2Ships[row][col] = 0;

				System.out.println("Victory: " + checkVictory());
			}
		}
		else
		{
			System.out.println("Enemy turn!");

		}
	
		
//		if(currentTurn.equals(player1))
//			currentTurn = player2;
//		else
//			currentTurn = player1;
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
					player2Grid.setColor(i, j, new Color(158, 216, 240), true);
				}
			}			
		}
	}
	
	public boolean checkShipSinked(int row, int col)
	{
	
		Ship ship = player1.getShipByCoordinates(row, col);
		Vector<Point> shipCoords = ship.getCoordinates();

		boolean sink = true;

		for (Point point : shipCoords) {
			if(!player1Hits.contains(point))
				sink = false;
		}
		
		if(sink)
			drawBoundingBox(ship);
			
		
		return sink;
		

	}
	
	public boolean checkVictory()
	{
		for (int i = 0; i < player1Ships.length; i++) {
			for (int j = 0; j < player1Ships[i].length; j++) {
				if(player2Ships[i][j] != 0)
					return false;			
			}
		}
		
		
//		cl.declareWinner(player1);
		return true;
	}
		
}
