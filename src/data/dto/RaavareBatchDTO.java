package data.dto;

import java.io.Serializable;

public class RaavareBatchDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int rbId;
	int commodityId;
	double amount;

	public int getRbId() {
		return rbId;
	}

	public void setRbId(int rbId) {
		this.rbId = rbId;
	}

	public int getRaavareId() {
		return commodityId;
	}

	public void setRaavareId(int commodityId) {
		this.commodityId = commodityId;
	}

	public double getMaengde() {
		return amount;
	}

	public void setMaengde(double amount) {
		this.amount = amount;
	}
	public String toString(){
		return rbId + " " + commodityId + " " + amount;
	}
}