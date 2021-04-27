package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import client.Player;

public class Server {
	private static int PORT;
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ArrayList<Player> players = new ArrayList<>();
	
	public static void addPlayer(Player player)
	{
		players.add(player);
	}
	
	public static void getPlayerNames()
	{
		System.out.println("Players connected: ");
		for (Player player : players) {
			System.out.println(player.getPlayerName());
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		
		// Parbauda, vai serverim noradits porta nr.
		if(args.length < 1)
		{
			System.out.println("Define port number as argument.");
			System.exit(1);
		}
		PORT = Integer.parseInt(args[0]);
		ServerSocket listener = new ServerSocket(PORT);
		System.out.println("Waiting!!!");
		try
		{
			// Gaida klientu savienojumus
			while(true)
			{
				Socket client = listener.accept();
				System.out.println("New client connected");
				
				// Izveido un saak clientHandler thread
				ClientHandler clientThread = new ClientHandler(client, clients);
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
