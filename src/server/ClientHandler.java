package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.Player;
import client.Shot;


public class ClientHandler extends Thread {

	private Socket client;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;

	
	public ClientHandler(Socket clientSocket)
	{
		try
		{
			this.client = clientSocket;
			this.out = new ObjectOutputStream(client.getOutputStream());
			this.in = new ObjectInputStream(client.getInputStream());
			out.writeObject("Succesfully created client thread.");
			
		}
		catch (IOException e)
		{
			System.out.println("Error creating client thread: " + e.getMessage());
		}
	}
	
	public void broadcast(Object message)
	{
		for (ClientHandler clientHandler : Server.clients) {
			try
			{
				clientHandler.getObjectWriter().writeObject(message);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public ObjectOutputStream getObjectWriter() throws IOException
	{
		return out;
	}
	
	public void checkClientCount()
	{
		if(Server.clients.size() < 2)
		{
			broadcast("wait");
		}
		else
		{
			Server.playersConnected = true;
			broadcast("playersConnected");
		}
	}
	
	public void checkPlayerCount()
	{
		if(Server.players.size() < 2)
		{
			broadcast("wait");
		}
		else
		{
			broadcast("playersReady");
			Server.gameStarted = true;
			
			// Player, kas aizpildija pirmais savu laukumu saks pirmais speli
			Server.currentTurn = Server.players.get(0);
			Message msg = new Message();
			msg.setNextTurn(Server.players.get(0).getPlayerName());
			msg.setShot(null);
			msg.setShip(null);
			broadcast(msg);			
		}	
	}
	
	public void run()
	{	
		if(!Server.playersConnected)
			checkClientCount();
		try
		{
			// Gaidam komandas no client
			while(true)
			{
				Object recievedObject = in.readObject();

				// Nosuta/sanem komandas (strings), kas atbild par speles sakumu
				if(!Server.gameStarted)
				{
					
					String request = (String) recievedObject;
					// Kamer nav 2 player - mes apstradajam sadu request
					if(Server.players.size() < 2)
					{
						if(request.contains("nextPlayer"))
						{
							// Parsuta player objektus serverim
							try
							{
								Player currentPlayer = (Player) in.readObject();
								Server.players.add(currentPlayer);
								System.out.println("New player: " + currentPlayer.getPlayerName());
								checkPlayerCount();
							}
							catch (ClassNotFoundException e)
							{
								System.out.println("Error reading player object on serverside");
								e.printStackTrace();
							}
						}
					}
				}
				else
				{
					System.out.println("Recieving shots");
					
					// Nosuta/sanem shots un messages
					// Nolasa shot
					Shot shot = (Shot) recievedObject;
					System.out.println("X: " + shot.getX());
					System.out.println("Y: " + shot.getY());
					
					// Logic
					Message response = Server.processShot(shot);
					broadcast(response);
					
					if(response.isVictory())
					{
						in.close();
						out.close();
						client.close();
						System.exit(0);
					}

				}				
			}
		}
		catch(IOException | ClassNotFoundException e)
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
