package data;

import java.util.ArrayList;

public class Data {

	private ArrayList<Element> data = new ArrayList<Element>();

	public Data(boolean choice){

		if (choice){
			int[] batchData = {1,2,3,4,5,6,7,8,9};
			int[] weightData = {100,200,300,400,500,600,700,800,900};
			String[] nameData = {"Banan", "Guagamole", "Fanatic", "Kaffe", "JAVA", "Hest", "Ko", "Hane", "SOLE"};

			for (int i = 0 ; i < batchData.length ; i++){

				Element Stuff = new Element(batchData[i], weightData[i], nameData[i]);
				data.add(Stuff);
			}
		}
	}

	public void addElement(int batch, int weight, String name){
		Element Stuff = new Element(batch, weight, name);
		data.add(Stuff);
	}
	public Element getElement(int index){
		return data.get(index);
	}
	public void removeElement(int index){
		data.remove(index);
	}
	public int getSize(){
		return data.size();
	}

	class Element {

		private int batch;
		private int weight;
		private String name;

		public Element(int batch, int weight, String name){

			this.batch = batch;
			this.weight = weight;
			this.name = name;

		}

		public int getBatch(){
			return batch;
		}
		public int getWeight(){
			return weight;
		}
		public String getName(){
			return name;
		}
	}
}
