package test;

import java.io.*;
import java.net.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestClient{
	static Socket clientSocket;
	static BufferedReader inFromUser;
	static DataOutputStream outToServer;
	static BufferedReader inFromServer;
	
	static{
		try {
			clientSocket = new Socket("localhost", 8000);
			inFromUser = new BufferedReader( new InputStreamReader(System.in));
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public TestClient() throws IOException{
		
		
	}
	
	
	//Kør serveren først
	@Test
	public void DCommand() throws IOException{

		outToServer.writeBytes("D Test" + "\r\n");
		outToServer.flush();
		outToServer.writeBytes("D Test2" + "\r\n");
		outToServer.flush();
		String expectedAnswer = "DB";
		String actualAnswer = inFromServer.readLine();
		
		assertEquals(expectedAnswer, actualAnswer);
	}
	
	@Test
	public void DWCommand(){
		
		try {
			outToServer.writeBytes("D Test" + "\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outToServer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Hej");
		String expectedAnswer = "DB";
		String actualAnswer = null;
		try {
			actualAnswer = inFromServer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expectedAnswer, actualAnswer);
	}
}


