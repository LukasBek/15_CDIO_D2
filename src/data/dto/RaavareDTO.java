package data.dto;

import java.io.Serializable;

public class RaavareDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int commodityID;
	private String commodityName;
	private String supplier;

	public int getrID() {
		return commodityID;
	}

	public void setrID(int rID) {
		this.commodityID = rID;
	}

	public String getrName() {
		return commodityName;
	}

	public void setrName(String commodityName) {
		this.commodityName = commodityName;
	}

	// TODO Change to getSupplier

	public String getDeliverer() {
		return supplier;
	}

	// TODO Change to setSupplier

	public void setDeliverer(String supplier) {
		this.supplier = supplier;
	}

	public String toString(){
		return commodityID + " " + commodityName + " " + supplier;
	}
}