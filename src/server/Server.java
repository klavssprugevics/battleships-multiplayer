package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.Message;
import model.Player;
import model.Shot;

public class Server {
	private static int PORT;
	
	public static ArrayList<ClientHandler> clients = new ArrayList<>();
	public static ArrayList<Player> players = new ArrayList<>();
	
	public static Player currentTurn;
	public static boolean gameStarted = false;
	public static boolean playersConnected = false;
	
	
	public static Message processShot(Shot shot)
	{
		Message message = new Message();
		Player enemy = null;
		boolean hit = false;
		boolean sink = false;
		boolean victory = false;
		
		for (Player p : Server.players)
			if(!p.equals(Server.currentTurn))
				enemy = p;
		
		
		int status = enemy.getPlayerField()[shot.getY()][shot.getX()];
		
		if(status == 0)
		{
			// Miss
			hit = false;
			Server.currentTurn = enemy;
		}
		else
		{
			// Hit
			hit = true;
			enemy.changeStatus(shot.getY(), shot.getX(), -1);
			
			// Parbauda, vai kugis grimst
			if(sink = enemy.sunken(shot.getY(), shot.getX()))
			{
				message.setShip(enemy.getShipByCoordinates(shot.getY(), shot.getX()));
			}
			
			victory = enemy.checkAllSunken();
		}
		
		message.setShot(shot);
		message.setNextTurn(Server.currentTurn.getPlayerName());
		message.setHit(hit);
		message.setSink(sink);
		message.setVictory(victory);
		
		return message;
	}
	

	public static void main(String[] args) throws IOException {
		
		// Ja nav noradits porta nr. tad uzstada default port.
		if(args.length < 1)
			PORT = 8989;
		else
			PORT = Integer.parseInt(args[0]);
		
		
		
		ServerSocket listener = new ServerSocket(PORT);
		System.out.println("Server running. Waiting for connections...");
		
		try
		{
			// Gaida klientu savienojumus
			while(true)
			{
				Socket client = listener.accept();
				System.out.println("New client connected");
				
				// Izveido un saak clientHandler thread
				ClientHandler clientThread = new ClientHandler(client);
				clients.add(clientThread);
				clientThread.start();
			}
		}
		finally
		{
			listener.close();
		}
	}
}
