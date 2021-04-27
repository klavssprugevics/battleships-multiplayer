package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection extends Thread{

	private Socket server;
	private Player player;
	private BufferedReader in;
	private PrintWriter out;
	
	public ServerConnection(Socket server, Player player) throws IOException
	{
		this.server = server;
		this.player = player;
		this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		this.out = new PrintWriter(server.getOutputStream(), true);
		
	}
	
	public void sendPlayerName()
	{
		out.println(player.getPlayerName());
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				String serverResponse;
				serverResponse = in.readLine();
				System.out.println(serverResponse);
				
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
