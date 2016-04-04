package client;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;

public class Client{
	
	static int portnummer = 8000;

	public static void main(String argv[]) throws IOException{
		
		
		
		if(argv.length > 0){	
			try{
				int foo = 0;
				foo = Integer.parseInt(argv[0]);
				if(foo> 1024 ){
					portnummer = foo;
					System.out.println(argv[0]);
				}
			}	catch(InputMismatchException e){
				System.out.println(e);
			}
		}

		String toWeight;
		String fromWeight;
		boolean run;

		Socket clientSocket = new Socket("localhost", portnummer);
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));

		run = true;

		try{
			while(run){
				System.out.println("Following commands work:");
				System.out.println("RM to do the RM20 order");
				System.out.println("D followed by letters, for display");
				System.out.println("DW to clean display");
				System.out.println("T to set Tarra");
				System.out.println("S to weight your object");
				System.out.println("B followed by the weight of Lukas tiny dick");
				System.out.println("Q to exit");
				toWeight = inFromUser.readLine();

				outToServer.writeBytes(toWeight + '\n');
				if(toWeight.startsWith("RM") || toWeight.startsWith("rm")){
					fromWeight = inFromServer.readLine();
					System.out.println(fromWeight);
				}

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