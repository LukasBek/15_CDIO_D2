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
		}
		name.add("Salt");
		name.add("Banan");
		name.add("Cola");
		name.add("Paprika");
		name.add("Sukker");
		name.add("Fedt");
		name.add("Galaxy s7 EDGE");
		name.add("iPhone 7");
		name.add("Kuglepen");

	}
	
	public ArrayList<Integer> getBatch(){
		return batch;
	}
	public ArrayList<Integer> getWeight(){
		return weight;
	}
	public ArrayList<String> getName(){
		return name;
	}
}
