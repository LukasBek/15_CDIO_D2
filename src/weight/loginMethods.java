package weight;

import java.util.InputMismatchException;
import java.util.Scanner;

import data.daoimpl.SQLOperatoerDAO;
import data.daointerface.DALException;

public class loginMethods {
	
	SQLOperatoerDAO odao;
	
	public loginMethods(SQLOperatoerDAO odao){
		this.odao = odao;
	}

	public boolean login(){
		int id = 0;
		String password;

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your id below:");
		try{
			id = sc.nextInt();
		}catch(InputMismatchException e){
			System.out.println("Please enter a legit number (between 1-999999:");
			login();
		}
		System.out.println("Enter your password below:");
		password = sc.next();

		if(correctUserPassword(id, password)){
			return true;
		}else{
			System.out.println("Wrong login, try again");
			if(login()){
				return true;
			}
		}
		return false;
	}

	public boolean correctUserPassword(int iD, String password){
		int index = -1;	
		try {
			for (int i = 1 ; i < odao.getOperatoerList().size(); i++){
				if (iD == odao.getOperatoer(i).getOprId()){			
					index = i;	
					break;				
				}		
			}
		} catch (DALException e){			
		}
		try {
			if(odao.getOperatoer(index).getPassword().equals(password)){
				return true;
			}else{
				return false;
			}
		} catch (DALException e) {		
		}
		return false;
	}

}
