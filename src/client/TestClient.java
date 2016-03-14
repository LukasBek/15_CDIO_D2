package client;

import java.io.*;
import java.net.*;

public class TestClient{
	
	public static void main(String argv[]) throws Exception{
		
		
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		Socket clientSocket = new Socket("localhost", 8000);
		String test = inFromUser.readLine();
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(test + '\n');
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader   (clientSocket.getInputStream()));
		String accepted = inFromServer.readLine();
		System.out.println(""+accepted);
		if(test == "q" || test == "Q"){
		clientSocket.close();
		}
	}
}