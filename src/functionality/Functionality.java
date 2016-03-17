package functionality;

import data.DAO;

public class Functionality {

	private DAO D;
	
	public Functionality() {
		
		D = new DAO();
		
	}

	public String getBatch(int batchNumber) {
		String S1 = D.getName(batchNumber).toString();
		String S2 = Integer.toString(D.getWeight(batchNumber));
				
		return S1 + " " + S2;		
	}
}
