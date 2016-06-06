package data.dto;

import java.io.Serializable;

/**
 * Data transfer object for weight measurements
 */

public class WeightDTO implements Serializable{

	private int wID;
	private int opID;
	private double ms;

	/**
	 * The object used to transfer the weight details to the database
	 * @param opID The operator behind the measurement
	 * @param ms the weight of the measurement
	 */
	public WeightDTO(){

	}

	public int getopID(){
		return opID;
	}

	public void setopID(int opID){
		this.opID = opID;
	}

	public double getMS(){
		return ms;
	}

	public void setMS(double ms){
		this.ms = ms;
	}
	public void setWID(int wID){
		this.wID = wID;
	}
	public int getWID(){
		return wID;
	}

	public String toString(){
		return wID + " " + ms + " " + opID;
	}
}