package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection extends Thread{

	private Socket server;
	private Client client;
	private Player player;
	private BufferedReader in;
	private PrintWriter out;
	private ObjectOutputStream oos;
	private boolean playersConnected = false;
	private boolean playersReady = false;
	private boolean ready = false;
	
	public ServerConnection(Socket server, Player player, Client client) throws IOException
	{
		this.server = server;
		this.client = client;
		this.player = player;
		this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		this.out = new PrintWriter(server.getOutputStream(), true);
		this.oos = new ObjectOutputStream(server.getOutputStream());
		
	}
	
//	public void checkPlayersConnected()
//	{
//		out.println("playersConnected?");
//	}
	
	public void checkPlayersReady()
	{
		out.println("playersReady?");
	}
	
	public void setReady(boolean ready)
	{
		this.ready = ready;
	}
	
	public void sendPlayerName()
	{
		out.println("name:" + player.getPlayerName());
	}
	
	public void sendPlayerObject()
	{
		// Tell the server that the next call will be the player object
		out.println("nextPlayer");
		try {
			oos.writeObject(player);
		} catch (IOException e) {
			System.out.println("Error sending Player object");
			e.printStackTrace();
		}

		
	}
	
	
	public void run()
	{
		try
		{
			while(true)
			{
				String serverResponse;
				serverResponse = in.readLine();
				System.out.println("[SERVER]: " + serverResponse);
				
				
				// Apstrada response no servera
				
				
				// Ja visi lietotaji nav pieslegusies - atvert gaidisanas ekranu
				if(!playersConnected)
				{
					if(serverResponse.equals("wait"))
					{
						System.out.println("Waiting for other player...");
						client.openWaitingScreen();
					}
					else if(serverResponse.equals("playersConnected"))
					{
						System.out.println("Players connected.");
						client.closeWaitingScreen();
						playersConnected = true;
						
					}
				}
				
				
				// Ja visi lietotaji pieslegusies, visi speletaji nav gatavi, tacu mes esam gatavi
				// atvert gaidisanas ekranu
				if(playersConnected && !playersReady && ready)
				{
					if(serverResponse.equals("wait"))
					{
						System.out.println("Waiting for other player...");
						client.openWaitingScreen();
					}
					else if(serverResponse.equals("playersReady"))
					{
						System.out.println("Players ready.");
						client.closeWaitingScreen();
						playersReady = true;
					}
				}

				
				if(serverResponse == null) break;
			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			try
			{
				server.close();
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
}
