package client;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Client{

	static int portnummer = 8000;
	private boolean login = false;
	Scanner sc;

	public static void main(String argv[]) throws IOException{
		
		Client client = new Client();
		
		//Making it possible to change to portnumber
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
		
		client.run();
	}

	private void run(){

		String toWeight;
		String fromWeight;
		boolean run;
	
		//Creates connection to the weight
		Socket clientSocket = null;
		BufferedReader inFromUser = null;
		PrintWriter outToServer = null;
		BufferedReader inFromServer = null;
		try{
			clientSocket = new Socket("localhost", portnummer);
			inFromUser = new BufferedReader( new InputStreamReader(System.in));
			outToServer = new PrintWriter(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
		}catch(UnknownHostException e1) {
			System.out.println("Unable to establish connection to weight");
		}catch(IOException e1) {
			System.out.println("Unable to establish connection to weight");
		}

		System.out.println("Please login to use the weight application");
		while(!login){
			login(outToServer, inFromServer);
		}
		
		run = true;

		//The main program being run as long as "run" is true
		try{
			while(run){
				System.out.println("Following commands work:");
				System.out.println("RM to do the RM20 order");
				System.out.println("D followed by letters, for display");
				System.out.println("DW to clean display");
				System.out.println("T to set Tarra");
				System.out.println("S to weight your object");
				System.out.println("B followed by the weight of the item you want to weigh");
				System.out.println("Q to exit");
				toWeight = inFromUser.readLine();

				//sends input to weight
				outToServer.println(toWeight);
				outToServer.flush();

				//the client will receive to inputs before being able to send it again during RM
				if(toWeight.startsWith("RM") || toWeight.startsWith("rm")){
					fromWeight = inFromServer.readLine();
					System.out.println(fromWeight);
				}

				//after having sent the q, the client itself will shutdown
				if(toWeight.equals("q") || toWeight.equals("Q")){
					try {
						System.out.println("Q modtaget - lukker ned");
						clientSocket.close();
						System.in.close();
						System.out.close();
						sc.close();
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

				//Receives input from weight
				fromWeight = inFromServer.readLine();
				System.out.println(fromWeight);	
			}
		}catch(Exception e){
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private void login(PrintWriter outToServer, BufferedReader inFromServer) {
		sc = new Scanner(System.in);		
		
		int id;
		String password;
		String fromServer = null;
		
		System.out.println("Enter your ID below:");
		try{
			id = sc.nextInt();
		}catch(InputMismatchException e){
			System.out.println("Please enter a valid ID (1-99999)");
			return;
		}
		System.out.println("Enter your password below:");
		password = sc.next();
		
		try {
			outToServer.println(id);
			outToServer.println(password);
			outToServer.flush();
			
			fromServer = inFromServer.readLine();
			
			if(fromServer.equals("succes")){
				login = true;
			}else if(fromServer.equals("failure")){
				System.out.println("Wrong login, try again");
				login(outToServer, inFromServer);
			}else{
				System.out.println("error in login (clientside)");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}