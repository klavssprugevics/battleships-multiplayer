package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler extends Thread {

	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<ClientHandler> clients;
	
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException
	{
		this.client = clientSocket;
		this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.out = new PrintWriter(client.getOutputStream(), true);
		this.clients = clients;
		out.println("Succesfully created cliend handler thread");
	}
	
	public void run()
	{
		try
		{
			// Gaidam komandas no client
			while(true)
			{
				String request = in.readLine();
				System.out.println(request);
				out.println(request);
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
