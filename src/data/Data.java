package data;

import java.util.ArrayList;

public class Data {

	private ArrayList<Integer> batch = new ArrayList<Integer>();
	private ArrayList<Integer> weight = new ArrayList<Integer>();
	private ArrayList<String> name = new ArrayList<String>();
	
	public Data(){
		for(int i = 0; i<10; i++){
		batch.add(i);
		weight.add((i+5)*7);
		String s="";
		int j = 0;
		char x = 'a';
		for ( ; j<9; x++){
		s=s+x;
		
		j++;
		}
		name.add(s);
		}
	}
	
	private ArrayList<Integer> getBatch(){
		return batch;
	}
	public ArrayList<Integer> getWeight(){
		return weight;
	}
	public ArrayList<String> getName(){
		return name;
	}
}
