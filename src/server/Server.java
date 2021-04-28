package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import client.Player;

public class Server {
	private static int PORT;
	
	public static ArrayList<ClientHandler> clients = new ArrayList<>();
	public static ArrayList<Player> players = new ArrayList<>();
	
	public static Player currentTurn;
	public static boolean gameStarted = false;
	public static boolean playersConnected = false;
	

	public static void main(String[] args) throws IOException {
		
		// Parbauda, vai serverim noradits porta nr.
		if(args.length < 1)
		{
			System.out.println("Define port number as argument.");
			System.exit(1);
		}
		
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
