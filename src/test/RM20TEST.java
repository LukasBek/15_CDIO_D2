package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.junit.Test;

public class RM20TEST {
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
			e.printStackTrace();
		}
	}
	
	
	
	
	@Test
	public void RMCommand1(){
		
		try {
			outToServer.writeBytes("RM" + "\r\n");
			outToServer.flush();

			String expectedAnswer = "RM20 B";
			String actualAnswer = inFromServer.readLine();
			assertEquals(expectedAnswer, actualAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void RMCommand2(){
		try {
			//Indtast først batchnummer 2 i serveren
			
			String expectedAnswer = "RM20 A " + "2";
			String actualAnswer = inFromServer.readLine();
			assertEquals(expectedAnswer, actualAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
