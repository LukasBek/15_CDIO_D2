package test;

import client.Client;
import java.io.*;
import java.net.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestClient{
	Socket clientSocket;
	BufferedReader inFromUser;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	
	public TestClient() throws IOException{
		clientSocket = new Socket("localhost", 8000);
		inFromUser = new BufferedReader( new InputStreamReader(System.in));
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
	}

	
	//Kør serveren først
	@Test
	public void DCommand() throws IOException{
		outToServer.writeBytes("D Test" + '\n');
		
		String expectedAnswer = "DB";
		String actualAnswer = inFromServer.readLine();
		
		assertEquals(expectedAnswer, actualAnswer);
	}
	
	@Test
	public void DWCommand() throws IOException{
		outToServer.writeBytes("D Dumbo" + '\n');
		
		String expectedAnswer = "DB";
		String actualAnswer = inFromServer.readLine();
		
		assertEquals(expectedAnswer, actualAnswer);
	}
}


