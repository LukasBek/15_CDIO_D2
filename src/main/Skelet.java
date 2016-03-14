package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import controllers.SkeletController;

public class Skelet{

	static ServerSocket listener;
	static double brutto=0;
	static double tara = 0;
	static String inline;
	static String indtDisp ="";
	static int portdst = 8000;
	static Socket sock;
	static BufferedReader instream;
	static DataOutputStream outstream;
	static boolean rm20flag = false;
	

	public static void main(String[]args) throws IOException{

		SkeletController sC = new SkeletController();
//		Scanner sc = new Scanner(System.in);
		
		listener = new ServerSocket(portdst);
		System.out.println("Venter på connection på port "+portdst);
		System.out.println("Indtast eventuel portnummer som 1. argument");
		System.out.println("på kommandolinien foran det port nr");
//		System.out.println("Indtast eventuelle nye portnumer: ");
//		portdst = sc.nextInt();	
//		listener = new ServerSocket(portdst);
		sock = listener.accept();
//		sc.close();
		instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		outstream = new DataOutputStream(sock.getOutputStream());

		printmenu();
		try{
			while(!(inline=instream.readLine().toUpperCase()).isEmpty()){//her ventes på input
				if(inline.startsWith("RM")){
					sC.method();
				}
				else if(inline.startsWith("D")){
					if(inline.equals("DW"))
						indtDisp="";
					else
						indtDisp=(inline.substring(2,inline.length()));//her skal anførselstegn
					printmenu();
					outstream.writeBytes("DB"+"\r\n");
				}
				else if(inline.startsWith("T")){
					outstream.writeBytes("TS"+(tara)+"kg"+"\r\n"); //HVOR MANGE SPACE?
					tara=brutto;
					printmenu();
				}
				else if(inline.startsWith("S")){
					printmenu();
					outstream.writeBytes("SS"+(brutto - tara)+"kg" +"\r\n");//HVOR MANGE
					
				}
				else if(inline.startsWith("B")){//denne ordre findes ikke på en fysisk vægt
					String temp = inline.substring(2,inline.length());
					brutto = Double.parseDouble(temp);
					printmenu();
					outstream.writeBytes("DB "+"\r\n");
				}
				else if((inline.startsWith("Q"))){
					System.out.println("");
					System.out.println("Program stoppet Q modtaget på com port");
					System.in.close();
					System.out.close();
					instream.close();
					outstream.close();
					System.exit(0);
				}
				else{
					printmenu();
					outstream.writeBytes("ES "+" \r\n");
				}
			}
		}
		catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
			e.printStackTrace();
		}
	}
	public static void printmenu(){
		for(int i = 0 ; i<2 ; i++)
			System.out.println(" ");
		System.out.println("*************************************************");
		System.out.println("Netto: "+(brutto - tara)+" kg" );
		System.out.println("Instruktions display: "+ indtDisp );
		System.out.println("*************************************************");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Debug info: ");
		System.out.println("Hooked up to"+sock.getInetAddress() );
		System.out.println("Brutto: "+(brutto)+" kg" );
		System.out.println("Streng modtaget: "+inline) ;
		System.out.println(" ");
		System.out.println("Denne vægt simulator lytter på ordrene ");
		System.out.println("S, T, D'TEST', DW, RM20 8....,BogQ ");
		System.out.println("på kommunikation sporten. ");
		System.out.println("******") ;
		System.out.println("Tast T for tara (svarende til knap tryk på vægt)");
		System.out.println("Tast B for ny brutto (svarende til at belastningen på vægt ændres)");
		System.out.println("Tast Q for at afslutte program program");
		System.out.println("Indtast (T/B/Q for knap tryk/bruttoændring/quit)");
		System.out.print ("Tast her:");
	}
}