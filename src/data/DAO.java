package data;

import data.Data;

public class DAO {

	private Data D;

	public DAO(){
	// Hvis der bruges "true" i konstruktøren laves der et standart array
	// der kan arbejdes med, hvis der indsættes "false" er dette array tomt.
		D = new Data(true);
	}
	/**
	 * Gets the batchnumber of the element in the data array
	 * @param index the element number in the data array
	 * @return the batchnumber
	 */
	public int getBatch(int index){
		return D.getElement(index).getBatch();
	}
	/**
	 * Gets the weight of the element in the data array
	 * @param index the element number in the data array
	 * @return the weight
	 */
	public int getWeight(int index){
		return D.getElement(index).getWeight();
	}
	/**
	 * Gets the weight of the element in the data array
	 * @param index the element number in the data array
	 * @return the name
	 */
	public String getName(int index){
		return D.getElement(index).getName();
	}
	/**
	 * Adds a new element into the data array, with the parameters defined in the parameterlist
	 * @param batch the batchnumber of the element
	 * @param weight the weight of the element
	 * @param name the name of the element
	 */
	public void addElement(int batch, int weight, String name){
		D.addElement(batch, weight, name);
	}
	/**
	 * Removes the element on index place in the data array
	 * @param index the element number in the array that will be removed
	 */
	public void removeElement(int index){
		D.removeElement(index);
	}
}