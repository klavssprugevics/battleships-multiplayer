package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.Message;

public class ServerConnection extends Thread{

	private Socket server;
	private Client client;
	private Player player;
	private Shot shot;

	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Message message;
	
	private boolean playersConnected = false;
	private boolean playersReady = false;
	private boolean ready = false;
	private boolean gameStarted = false;
	
	public ServerConnection(Socket server, Player player, Client client) throws IOException
	{
		this.server = server;
		this.client = client;
		this.player = player;
		this.out = new ObjectOutputStream(server.getOutputStream());
		this.in = new ObjectInputStream(server.getInputStream());		
	}
	

	
	public void setReady(boolean ready)
	{
		this.ready = ready;
	}
	
	public void sendShot(Shot shot)
	{
		this.shot = shot;
		try
		{
			out.writeObject(this.shot);
		}
		catch(IOException e)
		{
			System.out.println("Error sending shot: " + e.getMessage());
		}
	}
	
	public void closeConnection()
	{
		try {
			in.close();
			out.close();
			server.close();
		}
		catch (IOException e)
		{
			System.out.println("Error closing connection: " + e.getMessage());
		}
	}
	
	public void sendPlayerObject()
	{
		// Tell the server that the next call will be the player object
		
		try
		{
			out.writeObject("nextPlayer");
			out.writeObject(player);
		}
		catch (IOException e)
		{
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
				Object recievedObject = in.readObject();
				
				if(!gameStarted)
				{
					System.out.println("Game not started");
					String serverResponse;
					System.out.println("Listening for server response");
					serverResponse = (String) recievedObject;
					System.out.println("[SERVER]: " + serverResponse);

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
							gameStarted = true;
						}
					}
				}
				else
				{
					
					System.out.println("Trying to get message");
					message = (Message) recievedObject;

					
					// Process message
					client.processMessage(message);
				}

			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				in.close();
				out.close();
				server.close();
			}
			catch(IOException e)
			{
				System.out.println("Error closing socket: " + e.getMessage());
			}
		}
	}
	
}
