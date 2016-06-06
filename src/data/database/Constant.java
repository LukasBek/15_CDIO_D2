package data.database;


// erstat konstanterne nedenfor

public abstract class Constant
{
	public static final String
		server					= "127.0.0.1",  // database-serveren
		database				=  "supermarket",  //"jdbcdatabase", // navnet paa din database = dit studienummer
		username				= "root", // dit brugernavn = dit studienummer 
		password				= ""; // dit password som du har valgt til din database
	
	public static final int
		port					= 3306;
}
