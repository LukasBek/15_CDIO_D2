package data.dto;

import java.io.Serializable;

public class ProduktBatchKomponentDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
//	Tabel indhold;
//	
//	produktbatchkomponent
//	
//	pb_id	- 	int
//	rb_id	-	int
//	tara	-	double
//	netto	-	double
//	opr_id	-	int

	private int pb_id;
	private int rb_id;
	private double tara;
	private double netto;
	private int opr_id;
	
	public int getPbId() {
		return pb_id;
	
	}
	public void setPbId(int pb_id) {
		this.pb_id = pb_id;
	
	}
	public int getRbId() {
		return rb_id;
	
	}
	public void setRbId(int rb_id) {
		this.rb_id = rb_id;
	
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
	public int getOprId() {
		return opr_id;
	
	}
	public void setOprId(int opr_id) {
		this.opr_id = opr_id;
	}
}
