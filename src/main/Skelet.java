package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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

		listener = new ServerSocket(portdst);
		System.out.println("Venterpaaconnectionpåport"+portdst);
		System.out.println("Indtasteventuelportnummersom1.argument");
		System.out.println("paakommandolinienforandetportnr");
		sock = listener.accept();
		instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		outstream = new DataOutputStream(sock.getOutputStream());

		printmenu();
		try{
			while(!(inline=instream.readLine().toUpperCase()).isEmpty()){//herventespåinput
				if(inline.startsWith("RM")){
					//ikkeimplimenteret
				}
				else if(inline.startsWith("D")){
					if(inline.equals("DW"))
						indtDisp="";
					else
						indtDisp=(inline.substring(2,inline.length()));//herskalanførselstegn
					printmenu();
					outstream.writeBytes("DB"+"\r\n");
				}
				else if(inline.startsWith("T")){
					outstream.writeBytes("TS"+(tara)+"kg"+"\r\n"); //HVORMANGESPACE?
					tara=brutto;
					printmenu();
				}
				else if(inline.startsWith("S")){
					printmenu();
					outstream.writeBytes("SS"+(brutto - tara)+"kg" +"\r\n");//HVORMANGE
					
				}
				else if(inline.startsWith("B")){//denneordrefindesikkepåenfysiskvægt
					String temp = inline.substring(2,inline.length());
					brutto = Double.parseDouble(temp);
					printmenu();
					outstream.writeBytes("DB"+"\r\n");
				}
				else if((inline.startsWith("Q"))){
					System.out.println("");
					System.out.println("ProgramstoppetQmodtagetpaacom port");
					System.in.close();
					System.out.close();
					instream.close();
					outstream.close();
					System.exit(0);
				}
				else{
					printmenu();
					outstream.writeBytes("ES"+"\r\n");
				}
			}
		}
		catch(Exception e){
			System.out.println("Exception:"+e.getMessage());
		}
	}
	public static void printmenu(){
		for(int i = 0 ; i<2 ; i++)
			System.out.println(" ");
		System.out.println("*************************************************");
		System.out.println("Netto:"+(brutto - tara)+"kg" );
		System.out.println("Instruktionsdisplay:"+ indtDisp );
		System.out.println("*************************************************");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Debuginfo: ");
		System.out.println("Hookedupto"+sock.getInetAddress() );
		System.out.println("Brutto:"+(brutto)+"kg" );
		System.out.println("Strengmodtaget:"+inline) ;
		System.out.println(" ");
		System.out.println("Dennevægtsimulatorlytterpåordrene ");
		System.out.println("S,T,D'TEST',DW,RM208....,BogQ ");
		System.out.println("påkommunikationsporten. ");
		System.out.println("******") ;
		System.out.println("TastTfortara(svarendetilknaptrykpaavegt)");
		System.out.println("TastBfornybrutto(svarendetilatbelastningenpaavegtændres)");
		System.out.println("TastQforatafslutteprogramprogram");
		System.out.println("Indtast(T/B/Qforknaptryk/bruttoændring/quit)");
		System.out.print ("Tasther:");
	}
}