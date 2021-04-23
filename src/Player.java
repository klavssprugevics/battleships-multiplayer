
public class Player {

	public static int rows = 10;
	public static int columns = 10;
	private boolean playerField[][];
	
	private String playerName;
	
	Player(String name)
	{
		playerName = name;
		playerField =  new boolean[rows][columns];
		
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	
	public void getPlayerField()
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				System.out.print(playerField[i][j]);
			}
			System.out.println();
		}
	}
	

	public static void main(String[] args) {
		new Player("Janis").getPlayerField();
		
	}
}
