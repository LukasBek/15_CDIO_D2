package weight;

import data.daoimpl.SQLOperatoerDAO;
import data.daointerface.DALException;

public class loginMethods {
	
	SQLOperatoerDAO odao;
	
	public loginMethods(SQLOperatoerDAO odao){
		this.odao = odao;
	}

	public boolean correctUserPassword(int id, String password){
		int index = -1;	
		try {
			for (int i = 1 ; i < odao.getOperatoerList().size(); i++){
				if (id == odao.getOperatoer(i).getOprId()){			
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
