package data.dto;

import java.io.Serializable;

public class ProduktBatchDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int pbId;
	int receptId;
	int status;
	int oprId;
	int rbId;
	double tara;
	double netto;

	public int getPbId() {
		return pbId;
	}

	public void setPbId(int pbId) {
		this.pbId = pbId;
	}

	// TODO Change to getPrescriptionId
	
	public int getReceptId() {
		return receptId;
	}

	// TODO Change to setPrescriptionId
	
	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOprId() {
		return oprId;
	}

	public void setOprId(int oprId) {
		this.oprId = oprId;
	}

	public int getRbId() {
		return rbId;
	}

	public void setRbId(int rbId) {
		this.rbId = rbId;
	}

	public double getTara() {
		return tara;
	}

	public void setTara(double tara) {
		this.tara = tara;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public String toString(){
		return pbId + " " + receptId + " " + status + " " + oprId + " "+ rbId + ""+ tara+" "+ netto;
	}
}
