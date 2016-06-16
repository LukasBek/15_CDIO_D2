package weight;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Weight{

	static double brutto=0;
	static double tara = 0;
	static String inline;
	static String rmReturn;
	static String indtDisp ="";
	static int portdst = 8000;
	static int portnumber;
	static Socket sock;
	static BufferedReader instream;
	static ServerSocket listener;
	static DataOutputStream outstream;
	static int batchNumber;
	static int id;
	static int nextRaavare;

	static Scanner sc = new Scanner(System.in);

	public static void main(String argv[]) throws IOException{

		//This allows you to change the desired port the program will run on. 
		//It will be changed when launching the program through cmd: java -jar *NameOfFile*.jar *DesiredPort (>1024)* 
		if(argv.length > 0){			
			try{		
				int foo = 0;		
				foo = Integer.parseInt(argv[0]);		
				if(foo> 1024 ){		
					portnumber = foo;		
					System.out.println(argv[0]);		
				}		
			}	catch(InputMismatchException e){		
				System.out.println(e);		
			}		
		}		

		listener = new ServerSocket(portdst);

		System.out.println("Venter paa connection paa port "+portdst);
		System.out.println("Indtast eventuel portnummer som 1. argument");
		System.out.println("paa kommando linien foran det port nr.");

		sock = listener.accept();
		instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		outstream = new DataOutputStream(sock.getOutputStream());

		try{
			printmenu();
			//Main while loop which listens to the first user input from the ASE
			while(!(inline=instream.readLine().toUpperCase()).isEmpty()){//her ventes på input
				if(inline.startsWith("RM")){
					indtDisp = inline.substring(3).toLowerCase();
					outstream.writeBytes("RM20A"+"\r\n");
					printmenu();
					rmReturn = sc.nextLine();
					if(rmReturn == null){
						rmReturn = "";
					}
					outstream.writeBytes("RM20B "+ rmReturn +  "\r\n");
					outstream.flush();
					
				}else if(inline.startsWith("D")){
					if(inline.equals("DW")){
						indtDisp="";
					}else{
						indtDisp=(inline.substring(2,inline.length()));//herskalanførselstegn
					}
					printmenu();
					outstream.writeBytes("DB"+"\r\n");
					
				} else if (inline.startsWith("T")){
                    outstream.writeBytes("T S " + (tara) + "\r\n");		//HVOR MANGE SPACE?
                    tara=brutto;
                    printmenu();
                }				
				else if(inline.startsWith("S")){
					printmenu();
					outstream.writeBytes("S S " + (brutto-tara)+"\r\n");
					System.out.println("Brutto - tara: " + brutto + " - " + tara);
					outstream.flush();
				}
				
				else if(inline.startsWith("B")){//denne ordre findes ikke på en fysisk vægt
					try{
						String temp = inline.substring(2,inline.length());
						brutto = brutto + Double.parseDouble(temp);
					}catch(StringIndexOutOfBoundsException e){
						brutto = 0;
					}catch(NumberFormatException e){
						indtDisp = "Forkert vægtinput";
					}
					printmenu();
					 outstream.writeBytes("DB"+"\r\n");
				}
				
				else if((inline.startsWith("Q"))){
					System.out.println("");
					System.out.println("Program stoppet Q modtaget på com port");
					System.in.close();
					System.out.close();
					instream.close();
					outstream.close();
					sc.close();;
					System.exit(0);

				}
				else{
					printmenu();
					outstream.writeBytes("ES"+"\r\n");
				}
				//End of first while loop
			}
		}
		catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
			e.printStackTrace();
		}

	}


	public static void printmenu(){

		System.out.println(" ");
		System.out.println("*************************************************");
		System.out.println("Netto: "+(brutto-tara)+" kg" );
		System.out.println("Instruktions display: "+ indtDisp );
		System.out.println("*************************************************");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Debug info: ");
		System.out.println("Hooked up to "+sock.getInetAddress() );
		System.out.println("Brutto: "+(brutto)+" kg" );
		System.out.println("Streng modtaget: "+inline) ;
		System.out.println("For at taste 'ok' tryk enter");
		System.out.println(" ");
		System.out.println("Denne vægt simulator lytter på ordrene ");
		System.out.println("S, T, D 'TEST', DW, RM20, B og Q ");
		System.out.println("på kommunikation sporten. ");
		System.out.println("******") ;
		System.out.println("Tast RM for at lave RM20-ordren");
		System.out.println("Tast D efterfulgt af en besked til displayet");
		System.out.println("Tast DW for at rense displayet");
		System.out.println("Tast T for tara (svarende til knap tryk på vægt)");
		System.out.println("Tast S for at veje objektet");
		System.out.println("Tast B efterfulgt af en vægt for ny brutto (svarende til at belastningen på vægt ændres)");
		System.out.println("Tast Q for at afslutte program program");
		System.out.print  ("Tast her:");

	}
}