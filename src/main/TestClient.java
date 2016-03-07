package main;

import java.io.*;
import java.net.*;

public class TestClient{
	
	public static void main(String argv[]) throws Exception{
		
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		Socket clientSocket = new Socket("localhost", 8000);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader   (clientSocket.getInputStream()));
		clientSocket.close();
	}
}