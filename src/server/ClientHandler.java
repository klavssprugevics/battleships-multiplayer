package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import client.Player;


public class ClientHandler extends Thread {

	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private ObjectInputStream ois;
	private ArrayList<ClientHandler> clients; 	// clients connected
	private ArrayList<Player> players; 			// players that have setup their ships
	private boolean playersConnected = false;
	private Player currentPlayer;
	
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients, ArrayList<Player> players) throws IOException
	{
		this.client = clientSocket;
		this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.out = new PrintWriter(client.getOutputStream(), true);
		this.ois = new ObjectInputStream(client.getInputStream());
		this.clients = clients;
		this.players = players;
		out.println("Succesfully created cliend handler thread");
		
	}
	
	public void broadcast(String message)
	{
		for (ClientHandler clientHandler : clients) {
			clientHandler.getWriter().println(message);
		}
	}
	
	public PrintWriter getWriter()
	{
		return out;
	}
	
	public void checkClientCount()
	{
		if(clients.size() < 2)
		{
			broadcast("wait");
		}
		else
		{
			playersConnected = true;
			broadcast("playersConnected");
		}
	}
	
	public void checkPlayerCount()
	{
		if(players.size() < 2)
		{
			broadcast("wait");
		}
		else
		{
			broadcast("playersReady");
		}	
	}
	
	public void run()
	{	
		if(!playersConnected)
			checkClientCount();
		try
		{
			// Gaidam komandas no client
			while(true)
			{
				String request = in.readLine();
//				System.out.println(request);
//				out.println(request);
				
				// Kamer nav 2 player - mes apstradajam sadu request
				if(players.size() < 2)
				{
					if(request.contains("nextPlayer"))
					{
						// Parsuta player objektus serverim
						try {
							currentPlayer = (Player) ois.readObject();
							players.add(currentPlayer);
							System.out.println("New player: " + currentPlayer.getPlayerName());
							checkPlayerCount();
						} catch (ClassNotFoundException e) {
							System.out.println("Error reading player object on serverside");
							e.printStackTrace();
						}

					}
				}
					
//				if(request.equals("playersReady?"))
//				{
//					checkPlayerCount();
//				}
				

				
			}
		}
		catch(IOException e)
		{
			System.out.println("Error parsing request " + e.getMessage());
		}
		finally
		{
			// Kad serveris tiek izslegts - aizveram 
			try
			{
				
				in.close();
				out.close();
				client.close();
			}
			catch(IOException e)
			{
				System.out.println("Error closing clientHandler ports " + e.getMessage());
			}
		}
	}
}
