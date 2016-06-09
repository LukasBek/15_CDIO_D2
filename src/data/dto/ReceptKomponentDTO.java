package data.dto;

import java.io.Serializable;

public class ReceptKomponentDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int recept_id, raavare_id;
	double nom_netto, tolerance;

	// TODO Change to getPrescription_Id

	public ReceptKomponentDTO(){}
	
	public ReceptKomponentDTO(int recept_id, int raavare_id, double nom_netto, double tolerance) {
		this.recept_id = recept_id;
		this.raavare_id = raavare_id;
		this.nom_netto = nom_netto;
		this.tolerance = tolerance;
		
	}

	public int getRecept_Id() {
		return recept_id;
	}

	// TODO Change to setPrescription_Id

	public void setRecept_Id(int recept_id) {
		this.recept_id = recept_id;
	}

	// TODO Change to getCommodity_Id

	public int getRaavare_id() {
		return raavare_id;
	}

	// TODO Change to setCommodity_Id

	public void setRaavare_Id(int raavare_id) {
		this.raavare_id = raavare_id;
	}

	public double getNom_netto() {
		return nom_netto;
	}

	public void setNom_netto(double nom_netto) {
		this.nom_netto = nom_netto;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public String toString(){
		return recept_id + " " +  raavare_id  + " " +nom_netto+" "+ tolerance;
	}
}