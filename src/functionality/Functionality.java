package functionality;

import data.DAO;

public class Functionality {

	private DAO D;
	
	public Functionality() {
		D = new DAO();
	}

	public String getBatch(int batchNumber) {
		int index = -1;
		
		for (int i = 0 ; i < D.getSice() ; i++){
			if (D.getBatch(i) == batchNumber){
				index = i;
			}
		}
		
		if (index == -1) return "Invalid Batch Number";
		
		String S1 = D.getName(index);
		String S2 = Integer.toString(D.getWeight(index));
				
		return S1 + " " + S2;		
	}
}
