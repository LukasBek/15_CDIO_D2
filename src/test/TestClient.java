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
		String expectedAnswer = "DB";
		String actualAnswer = inFromServer.readLine();
		
		assertEquals(expectedAnswer, actualAnswer);
	}
	
	@Test
	public void DWCommand(){
		
		try {
			outToServer.writeBytes("DW" + "\r\n");
			outToServer.flush();

			String expectedAnswer = "DB";
			String actualAnswer = inFromServer.readLine();
			assertEquals(expectedAnswer, actualAnswer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void TCommand(){
		
		try {
			outToServer.writeBytes("T" + "\r\n");
			outToServer.flush();

			String expectedAnswer = "TS0.0kg";
			String actualAnswer = inFromServer.readLine();
			assertEquals(expectedAnswer, actualAnswer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void SCommand(){
		
		try {
			outToServer.writeBytes("S" + "\r\n");
			outToServer.flush();

			String expectedAnswer = "SS0.0kg";
			String actualAnswer = inFromServer.readLine();
			assertEquals(expectedAnswer, actualAnswer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void BCommand(){
		
		try {
			outToServer.writeBytes("B 2" + "\r\n");
			outToServer.flush();

			String expectedAnswer = "DB ";
			String actualAnswer = inFromServer.readLine();
			assertEquals(expectedAnswer, actualAnswer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void RMCommand(){
		
		try {
			outToServer.writeBytes("RM" + "\r\n");
			outToServer.flush();

			String expectedAnswer = "RM20 B";
			String actualAnswer = inFromServer.readLine();
			assertEquals(expectedAnswer, actualAnswer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


