package data;

import data.Data;

public class DAO {

	private Data D;

	public DAO(){
		D = new Data();
	}

	public String getName(int batchNumber){
		int index = 0;
		for (int i = 0 ; i < D.getName().size() ; i++){
			if (D.getBatch().get(i) == batchNumber){
				index = i;
			}
		}
		return D.getName().get(index);
	}
	
	public int getWeight(int batchNumber){
		int index = 0;
		for (int i = 0 ; i < D.getName().size() ; i++){
			if (D.getBatch().get(i) == batchNumber){
				index = i;
			}
		}
		return D.getWeight().get(index);
	}
}
