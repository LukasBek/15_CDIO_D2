package data;

import java.util.ArrayList;
import java.util.Random;

public class Data {

	private ArrayList<Integer> batch = new ArrayList<Integer>();
	private ArrayList<Integer> weight = new ArrayList<Integer>();
	private ArrayList<String> name = new ArrayList<String>();
	
	public Data(){
		for(int i = 0; i<10; i++){
		batch.add(i);
		weight.add((i+5)*7);
		String s="";
		char x = 'a';
		Random r = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for (int j = 0 ; j<9 ; j++){
		x = alphabet.charAt(r.nextInt(alphabet.length()));
		s=s+x;
		}
		name.add(s);
		}
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
