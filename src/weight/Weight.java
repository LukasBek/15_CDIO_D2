package weight;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import data.daoimpl.SQLOperatoerDAO;
import data.daoimpl.SQLProduktBatchDAO;
import data.daoimpl.SQLRaavareBatchDAO;
import data.daoimpl.SQLRaavareDAO;
import data.daoimpl.SQLReceptDAO;
import data.daointerface.DALException;
import data.dto.ProduktBatchDTO;
import data.dto.ProduktBatchKomponentDTO;
import data.dto.RaavareBatchDTO;


public class Weight{

	static ServerSocket listener;
	static double brutto=0;
	static double tara = 0;
	static String inline;
	static String indtDisp ="";
	static String extraDisp ="";
	static int portdst = 8000;
	static int batchNumber;
	static int id;
	static int nextRaavare;
	static Socket sock;
	static BufferedReader instream;
	static DataOutputStream outstream;
	static boolean measured = false;

	static Scanner sc = new Scanner(System.in);

	public static void main(String[]args) throws IOException{

		//This allows you to change the desired port the program will run on. 
		//It will be changed when launching the program through cmd: java -jar *NameOfFile*.jar *DesiredPort (>1024)* 
		if(args.length > 0){	
			try{
				int foo = 0;
				foo = Integer.parseInt(args[0]);
				if(foo> 1024 ){
					portdst = foo;
					System.out.println(args[0]);
				}
			}	catch(InputMismatchException e){
				System.out.println(e);
			}
		}

		listener = new ServerSocket(portdst);

		System.out.println("Venter på connection på port "+portdst);
		System.out.println("Indtast eventuel portnummer som 1. argument");
		System.out.println("på kommandolinien foran det port nr");
		sock = listener.accept();
		instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		outstream = new DataOutputStream(sock.getOutputStream());

		SQLOperatoerDAO odao = new SQLOperatoerDAO();
		SQLProduktBatchDAO pbdao = new SQLProduktBatchDAO();
		SQLReceptDAO receptdao = new SQLReceptDAO();
		SQLRaavareDAO raavaredao = new SQLRaavareDAO();
		SQLRaavareBatchDAO rbdao = new SQLRaavareBatchDAO();

		loginMethods lm = new loginMethods(odao);	
		RaavareMethod rm = new RaavareMethod();

		boolean loggedIn = false;

		while (!loggedIn){
			id = -1;
			String password = null;

			String sid = instream.readLine();
			id = Integer.parseInt(sid);

			password = instream.readLine();			

			if(!lm.correctUserPassword(id, password)){
				outstream.writeBytes("failure" + "\r\n");
			}else{
				loggedIn = true;
				outstream.writeBytes("succes" + "\r\n");
				indtDisp = "Welcome";
				try {
					extraDisp = odao.getOperatoer(id).getOprNavn();
				} catch (DALException e) {
					e.printStackTrace();
				}
			}
		}
		//		printmenu(odao, id);
		try{
			//Main while loop which listens to the first user input from the ASE
			while(!(inline=instream.readLine().toUpperCase()).isEmpty()){//waiting for input

				if(inline.startsWith("RM")){
					indtDisp="Indtast batchnummer";
					printmenu(odao, id);
					outstream.writeBytes("RM20 B"+"\r\n");

					boolean batchCheck = true;
					while(batchCheck){

						try{
							batchNumber = sc.nextInt();
							nextRaavare = rm.getNextRaavare(batchNumber);
							if(nextRaavare == -1){
								outstream.writeBytes("RM20 A "+ batchNumber+"\r\n");
								indtDisp = "Dette produktbatch er allerede færdigt!";
								printmenu(odao, id);

							}else{
								outstream.writeBytes("RM20 A "+ batchNumber+"\r\n");

								ProduktBatchDTO pb = new ProduktBatchDTO();
								pb = pbdao.getProduktBatch(batchNumber);
								pb.setStatus(1);
								pbdao.updateProduktBatch(pb);

								indtDisp = "Sæt en beholder på vægten og herefter tarrer den";
								extraDisp = "Recept der skal produceres: " + receptdao.getRecept(pbdao.getProduktBatch(batchNumber).getReceptId()).getReceptName();
								printmenu(odao, id);
								measured = false;

								//First RM20 loop listening weight/resetting of the scale
								while(!(inline=instream.readLine().toUpperCase()).isEmpty()){
									if(inline.startsWith("B")){
										try{
											String temp = inline.substring(2,inline.length());
											brutto = Double.parseDouble(temp);
										}catch(StringIndexOutOfBoundsException e){
											brutto = 0;
										}catch(NumberFormatException e){
											indtDisp = "Forkert vægtinput";
										}
										printmenu(odao, id);
										outstream.writeBytes("DB"+"\r\n");
									}else if (inline.startsWith("T")){

										tara=brutto;
										indtDisp = "Indtast raavarebatchnummer for " + raavaredao.getRaavare(nextRaavare).getrName() + ", med id: " + nextRaavare;
										extraDisp = "Første råvare: " + raavaredao.getRaavare(nextRaavare).getrName();
										printmenu(odao, id);


										int raavareBatch = 0;
										boolean correctRV = false;
										while(!correctRV){
											raavareBatch = rm.measureMethod(sc, nextRaavare);
											if(raavareBatch>0){
												correctRV = true;
											}else if(raavareBatch==-1){
												extraDisp = "Det indtastede er ikke et raavarebatchnummer";
												printmenu(odao, id);
												continue;
											}else if(raavareBatch==-2){
												extraDisp = "Det indtastede raavarebatchnummer består ikke af " + raavaredao.getRaavare(nextRaavare).getrName();
												printmenu(odao, id);
												continue;
											}
										}

										double raavareNom;
										double raavareTol;
										int receptID;
										String raavareNavn;

										receptID = pbdao.getProduktBatch(batchNumber).getReceptId();
										raavareNavn = raavaredao.getRaavare(nextRaavare).getrName();
										raavareNom = receptdao.getReceptKomp(receptID, nextRaavare).getNom_netto();
										raavareTol = receptdao.getReceptKomp(receptID, nextRaavare).getTolerance();

										indtDisp = "Sæt "+ raavareNom + " kg " + raavareNavn + " på vægten. Må kun have en tolerance på " + raavareTol;
										printmenu(odao, id);
										outstream.writeBytes("TS"+(tara)+"kg"+"\r\n"); 

										//Second RM20 loop where the actual object gets put on the weight
										while(!(inline=instream.readLine().toUpperCase()).isEmpty()){
											if(inline.startsWith("B")){
												try{
													String temp = inline.substring(2,inline.length());
													brutto += Double.parseDouble(temp);
												}catch(StringIndexOutOfBoundsException e){
													System.out.println("Error in second RM20 loop. 1");
												}catch(NumberFormatException e){
													indtDisp = "Forkert vægtinput, prøv igen";
													printmenu(odao, id);
													outstream.writeBytes("DB"+"\r\n");
													continue;
												}
												printmenu(odao, id);
												outstream.writeBytes("DB"+"\r\n");

											}else if (inline.startsWith("S")){
												if (brutto-tara <= raavareNom+raavareTol && brutto-tara >= raavareNom-raavareTol){


													ProduktBatchKomponentDTO pbkDTO = new ProduktBatchKomponentDTO();
													pbkDTO.setPbId(batchNumber);
													pbkDTO.setRbId(raavareBatch);
													pbkDTO.setOprId(id);
													pbkDTO.setTara(tara);
													pbkDTO.setNetto(brutto-tara);
													pbdao.createProduktBatchKomponent(pbkDTO);

													double amount;
													RaavareBatchDTO rbDTO = new RaavareBatchDTO();
													rbDTO = rbdao.getRaavareBatch(raavareBatch);
													amount = rbDTO.getMaengde();
													rbDTO.setMaengde(amount-brutto);
													rbdao.updateRaavareBatch(rbDTO);
													indtDisp = "";
													extraDisp = "Din måling er nu registreret";
													outstream.writeBytes("SS"+(brutto - tara)+"kg" +"\r\n");
													brutto = 0;
													tara = 0;
													printmenu(odao, id);
													measured = true;
													break;

												}else{
													indtDisp = "Maalingen ligger ikke inden for tolerancen, prøv igen";
													brutto = tara;
													printmenu(odao, id);
												}
											}else{
												printmenu(odao, id);
												outstream.writeBytes("ES"+"\r\n");
											}
											//End of second RM20 while loop
										}
									}else{
										printmenu(odao, id);
										outstream.writeBytes("ES"+"\r\n");
									}
									if(measured){
										break;
									}
									//End of first RM20 while loop
								}

							}
							batchCheck = false;
						}catch(InputMismatchException e){
							indtDisp="Det indtastede er ikke et batchnummer";
							outstream.writeBytes("RM20 A "+"-1"+"\r\n");

						}
					}printmenu(odao, id);
				}else if(inline.startsWith("D")){
					if(inline.equals("DW"))
						indtDisp="";
					else
						try{
							indtDisp=(inline.substring(2, inline.length()));//her skal anførselstegn
						}catch(StringIndexOutOfBoundsException e){
							indtDisp="";
						}
					printmenu(odao, id);
					outstream.writeBytes("DB"+"\r\n");
				}
				else if(inline.startsWith("T")){
					outstream.writeBytes("TS"+(tara)+"kg"+"\r\n"); 
					tara=brutto;
					printmenu(odao, id);
				}
				else if(inline.startsWith("S")){
					printmenu(odao, id);
					outstream.writeBytes("SS"+(brutto - tara)+"kg" +"\r\n");

				}
				else if(inline.startsWith("B")){//denne ordre findes ikke på en fysisk vægt
					try{
						String temp = inline.substring(2,inline.length());
						brutto = Double.parseDouble(temp);
					}catch(StringIndexOutOfBoundsException e){
						brutto = 0;
					}catch(NumberFormatException e){
						indtDisp = "Forkert vægtinput";
					}
					printmenu(odao, id);
					outstream.writeBytes("DB"+"\r\n");
				}
				else if((inline.startsWith("Q"))){
					System.out.println("");
					System.out.println("Program stoppet Q modtaget på com port");
					System.in.close();
					System.out.close();
					sc.close();
					instream.close();
					outstream.close();
					System.exit(0);

				}
				else{
					printmenu(odao, id);
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


	public static void printmenu(SQLOperatoerDAO odao, int id){

		System.out.println(" ");
		System.out.println("*************************************************");
		System.out.println("Netto: "+(brutto-tara)+" kg" );
		System.out.println("Instruktions display: "+ indtDisp );
		System.out.println(extraDisp);
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