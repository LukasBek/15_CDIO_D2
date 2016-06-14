package weight;

import java.io.IOException;
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

	static double brutto=0;
	static double tara = 0;
	static String inline;
	static String indtDisp ="";
	static String extraDisp ="";
	static int portdst = 8000;
	static int batchNumber;
	static int id;
	static int nextRaavare;
	
	static boolean loop1 = true;
	static boolean loop2 = true;
	static boolean loop3 = true;
	
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

		SQLOperatoerDAO odao = new SQLOperatoerDAO();
		SQLProduktBatchDAO pbdao = new SQLProduktBatchDAO();
		SQLReceptDAO receptdao = new SQLReceptDAO();
		SQLRaavareDAO raavaredao = new SQLRaavareDAO();
		SQLRaavareBatchDAO rbdao = new SQLRaavareBatchDAO();

		loginMethods lm = new loginMethods(odao);	
		RaavareMethod rm = new RaavareMethod();

		boolean loggedIn = false;
		
		System.out.println("Log in to use the weight");

		while (!loggedIn){
			id = -1;
			String password = null;

			System.out.print("Indtast dit id: ");
			String userid = sc.nextLine();
			try{
			id = Integer.parseInt(userid);
			}catch(NumberFormatException e){
				System.out.println("Ugyldig indtastning");
				continue;
			}
			
			System.out.print("Indtast adgangskode: ");
			password = sc.nextLine();			
			if(!lm.correctUserPassword(id, password)){
				System.out.println("Forkert login, prøv igen");
			}else{
				loggedIn = true;
				indtDisp = "Velkommen";
				try {
					extraDisp = odao.getOperatoer(id).getOprNavn();
				} catch (DALException e) {
					e.printStackTrace();
				}
			}
		}
		try{
			//Main while loop which listens to the first user input from the ASE
			printmenu(odao, id);

			while(loop1){//waiting for input
				inline=sc.nextLine().toUpperCase();
				if(inline.startsWith("RM")){
					indtDisp="Indtast batchnummer";
					printmenu(odao, id);

					boolean batchCheck = true;
					while(batchCheck){
						try{
							batchNumber = Integer.parseInt(sc.nextLine());
						}catch(NumberFormatException e){
							indtDisp="Indtast et gyldigt batchnummer-id";
							printmenu(odao, id);
							continue;
						}
							nextRaavare = rm.getNextRaavare(batchNumber);
							if(nextRaavare == -1){
								indtDisp = "Dette produktbatch er allerede færdigt eller eksisterer ikke";
								printmenu(odao, id);
							}else{
								ProduktBatchDTO pb = new ProduktBatchDTO();
								pb = pbdao.getProduktBatch(batchNumber);
								pb.setStatus(1);
								pbdao.updateProduktBatch(pb);

								indtDisp = "Sæt en beholder på vægten og herefter tarer for at fortsætte";
								extraDisp = "Recept der skal produceres: " + receptdao.getRecept(pbdao.getProduktBatch(batchNumber).getReceptId()).getReceptName();
								printmenu(odao, id);
								measured = false;

								//First RM20 loop listening weight/resetting of the scale
								while(loop2){
									inline=sc.nextLine().toUpperCase();
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
											}else if(raavareBatch==-3){
												extraDisp = "Det indtastede råvarebatchnummer findes ikke i systemet";
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
										extraDisp = "Første råvare: " + raavaredao.getRaavare(nextRaavare).getrName();
										

										//Second RM20 loop where the actual object gets put on the weight
										while(loop3){
											indtDisp = "Sæt "+ raavareNom + " kg " + raavareNavn + " på vægten. Må kun have en tolerance på " + raavareTol;
											printmenu(odao, id);
											inline=sc.nextLine().toUpperCase();
											if(inline.startsWith("B")){
												try{
													String temp = inline.substring(2,inline.length());
													brutto += Double.parseDouble(temp);
												}catch(StringIndexOutOfBoundsException e){
													System.out.println("Error in second RM20 loop");
												}catch(NumberFormatException e){
													indtDisp = "Forkert vægtinput, prøv igen";
													printmenu(odao, id);
													continue;
												}
												printmenu(odao, id);
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
												extraDisp="Ikke et gyldigt input";
												printmenu(odao, id);
											}
											//End of loop3
										}
									}else{
										extraDisp="Ikke et gyldigt input";
										printmenu(odao, id);
									}
									if(measured){
										break;
									}
									//End of loop2
								}

							}
							batchCheck = false;
						
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
				}
				else if(inline.startsWith("T")){
					tara=brutto;
					printmenu(odao, id);
				}
				else if(inline.startsWith("S")){
					printmenu(odao, id);
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
				}
				else if((inline.startsWith("Q"))){
					System.out.println("");
					System.out.println("Program stoppet Q modtaget på com port");
					System.in.close();
					System.out.close();
					sc.close();;
					System.exit(0);

				}
				else{
					extraDisp="Ikke et gyldigt input";
					printmenu(odao, id);
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
		System.out.println("Brutto: "+(brutto)+" kg" );
		System.out.println("Streng modtaget: "+inline) ;
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