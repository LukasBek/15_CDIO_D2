package client;

import java.io.*;
import java.net.*;

public class TestClient{

	public static void main(String argv[]) throws IOException{

		String toWeight;
		String fromWeight;
		boolean run;

		Socket clientSocket = new Socket("localhost", 8000);
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));

		run = true;

		try{
			while(run){
				System.out.println("Following commands work:");
				System.out.println("D followed by letters, for display");
				System.out.println("DW to clean display");
				System.out.println("T to set Tarra");
				System.out.println("D followed by letters, for display");
				System.out.println("S to weight your object");
				System.out.println("B followed by the weight of what you want to weight");
				System.out.println("Q to exit");
				toWeight = inFromUser.readLine();
				outToServer.writeBytes(toWeight + '\n');

				if(toWeight.equals("q") || toWeight.equals("Q")){
					try {
						System.out.println("Q modtaget - lukker ned");
						clientSocket.close();
						System.in.close();
						System.out.close();
						outToServer.close();
						inFromServer.close();
						inFromUser.close();
						run = false;
						System.out.println("Nedlukning færdig");
						System.exit(0);
					}catch(Exception e){
						System.out.println("Fejl i nedlukningsproceduren.");
					}
					
				}
				
				fromWeight = inFromServer.readLine();
				System.out.println(fromWeight);	
			}
		}catch(Exception e){
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
}