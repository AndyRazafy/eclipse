package entite;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import outil.Split;

public class RetourConge 
{
	private int id;
	private int congeId;
	private Date dArrivee;
	private Time hArrivee;
	private Date dSaisie;
	
	public RetourConge(int id, int congeId, Date dArrive, Time dHeuree) {
		super();
		this.setId(id);
		this.setCongeId(congeId);
		this.setDArrivee(dArrive);
		this.setHArrivee(hArrivee);
	}
	
	public RetourConge(String dArrive, String dHeuree)throws Exception {
		super();
		this.setDArrivee(dArrive);
		this.setHArrivee(hArrivee);
	}

	public int getId() {
		return id;
	}
	
	public int getCongeId() {
		return congeId;
	}
	
	public Date getDArrive() {
		return dArrivee;
	}
	
	public Time getHArrivee() {
		return hArrivee;
	}
	
	public Date getDSaisie(){
		return this.dSaisie;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCongeId(int congeId) {
		this.congeId = congeId;
	}
	
	public void setDArrivee(Date d) {
		this.dArrivee = d;
	}
	
	public void setDArrivee(String date) throws Exception{
		if(date.length() >= 8 && date.length() <= 11){
			char[] sep = {'.','/','-',' ',':'};
			String[] liste = Split.splitString(date, sep);
			this.dArrivee = Date.valueOf(getAnnee(liste[2]) + "-" + getMois(liste[1]) + "-" + getJour((liste[0])));
		}
		else throw new Exception("Erreur: date debut.");
	}
	
	public void setHArrivee(Time t) {
		this.hArrivee = t;
	}
	
	public void setHArrivee(String heure)throws Exception{
		if(heure.length() > 0 && heure.length() < 13){
			char[] sep = {'.',':','-',' ','/','h'};
			String[] liste = Split.splitString(heure, sep);
			if(liste.length == 4){
				if(liste[3].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
				else if(liste[3].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: Heure.");
				if(checkHeure(liste[0]) && checkHeure(liste[1]) && checkHeure(liste[2])) this.hArrivee = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
			}
			else if(liste.length == 3){
				if(liste[2].compareToIgnoreCase("AM") == 0 || liste[2].compareToIgnoreCase("PM") == 0){
					if(liste[2].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
					else if(liste[2].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: Heure.");
					if(checkHeure(liste[0]) && checkHeure(liste[1])) this.hArrivee = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
				}
				else if(checkHeure(liste[2])){
					if(checkHeure(liste[0]) && checkHeure(liste[1])) this.hArrivee = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
				}
				else throw new Exception("Erreur: Heure.");
			}
			else if(liste.length == 2){
				if(checkHeure(liste[0]) && checkHeure(liste[1])) this.hArrivee = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
			}
		}
		else throw new Exception("Erreur: Heure.");
	}
	
	public void setDSaisie(Date d){
		this.dSaisie = d;
	}
	
	private boolean checkHeure(String str)throws Exception{
		return Integer.valueOf(str) >= 0 && Integer.valueOf(str) <= 60;
	}
	
	private String getJour(String str)throws Exception{
		if(Integer.valueOf(str) > 0 && Integer.valueOf(str) <= Calendar.getInstance().getActualMaximum(5)) return str;
		else throw new Exception("Erreur: jour non valide.");
	}
	
	private String getMois(String str)throws Exception{
		if(str.matches("\\d+")){
			if(Integer.valueOf(str) > 0 && Integer.valueOf(str) < 13) return str;
			else throw new Exception("Erreur: mois non valide.");
		}
		else{
			String[] liste = {"JAN","FEV","MAR","AVR","MAI","JUN","JUI","AOU","SEP","OCT","NOV","DEC"};
			int len = liste.length;
			for(int i = 0;i < len;i++){
				if(str.compareToIgnoreCase(liste[i]) == 0) return String.valueOf(i + 1); 
			}
			throw new Exception("Erreur: mois non valide.");
		}
	}
	
	@SuppressWarnings("deprecation")
	private String getAnnee(String str)throws Exception{
		if(str.length() == 4 && (Integer.valueOf(str) <= Calendar.getInstance().getTime().getYear() + 1900)) return str;
		throw new Exception("Erreur: annee non valide.");
	}
}
