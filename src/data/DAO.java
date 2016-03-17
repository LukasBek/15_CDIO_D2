package data;

import java.util.ArrayList;
import data.Data;

public class DAO {

	private Data D;
	
	public DAO(){
		D = new Data();
	}
	
	public ArrayList<Integer> getBatch(){
		return D.getBatch();
	}
	public ArrayList<Integer> getWeight(){
		return D.getWeight();
	}
	public ArrayList<String> getName(){
		return D.getName();
	}
	
}
