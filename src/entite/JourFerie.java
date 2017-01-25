package entite;

import java.sql.Date;
import java.sql.Time;

public class JourFerie 
{
	private int id;
	private Time hDebut;
	private Time hFin;
	private Date date;
	private String motif;
	
	public JourFerie(int id, Time hDebut, Time hFin, Date date, String motif) {
		super();
		this.setId(id);
		this.setHDebut(hDebut);
		this.setHFin(hFin);
		this.setDate(date);
		this.setMotif(motif);
	}

	public int getId() {
		return id;
	}
	
	public Time getHDebut() {
		return hDebut;
	}
	
	public Time getHFin() {
		return hFin;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getMotif() {
		return motif;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setHDebut(Time hDebut) {
		this.hDebut = hDebut;
	}
	
	public void setHFin(Time hFin) {
		this.hFin = hFin;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setMotif(String motif) {
		this.motif = motif;
	}
}
