package entite;

import java.sql.Time;

public class EmploiDuTemps 
{
	private int id;
	private Time hDebut;
	private Time hFin;
	
	public EmploiDuTemps (int id, Time hDebut, Time hFin) {
		super();
		this.setId(id);
		this.setHDebut(hDebut);
		this.setHFin(hFin);
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
	public void setId(int id) {
		this.id = id;
	}
	public void setHDebut(Time hDebut) {
		this.hDebut = hDebut;
	}
	public void setHFin(Time hFin) {
		this.hFin = hFin;
	}

	@Override
	public String toString() {
		return "EmploiDuTemps [id=" + id + ", hDebut=" + hDebut + ", hFin=" + hFin + "]";
	}
}
