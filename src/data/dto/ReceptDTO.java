package data.dto;

import java.io.Serializable;

public class ReceptDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int prescriptionId;
	private String prescriptionName;

	// TODO Change to getPrescriptionId

	public int getReceptId() {
		return prescriptionId;
	}

	// TODO Change to setPrescriptionId

	public void setReceptId(int receptId) {
		this.prescriptionId = receptId;
	}

	// TODO Change to getPrescriptionName

	public String getReceptName() {
		return prescriptionName;
	}

	// TODO Change to setPrescriptionName

	public void setReceptName(String prescriptionName) {
		this.prescriptionName = prescriptionName;
	}
}