package entite;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import outil.Split;

public class Retard 
{
	private int id;
	private int empId;
	private Time duree;
	private Date date;
	
	public Retard(int id, int empId, Time duree, Date date) {
		super();
		this.setId(id);
		this.setEmpId(empId);
		this.setDuree(duree);
		this.setDate(date);
	}
	
	public Retard(int empId, Time duree, Date date) {
		super();
		this.setEmpId(empId);
		this.setDuree(duree);
		this.setDate(date);
	}
	
	public Retard(String empId, String duree, String date)throws Exception{
		super();
		this.setEmpId(empId);
		this.setDuree(duree);
		this.setDate(date);
	}

	public int getId() {
		return id;
	}
	
	public int getEmpId() {
		return empId;
	}
	
	public Time getDuree() {
		return duree;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	
	public void setEmpId(String empId) {
		
	}
	
	public void setDuree(Time duree) {
		this.duree = duree;
	}
	
	public void setDuree(String duree)throws Exception{
		if(duree.length() > 0 && duree.length() < 13){
			char[] sep = {'.',':','-',' ','/'};
			String[] liste = Split.splitString(duree, sep);
			System.out.println(liste.length);
			if(liste.length == 4){
				if(liste[3].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
				else if(liste[3].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: duree.");
				if(checkDuree(liste[0]) && checkDuree(liste[1]) && checkDuree(liste[2])) this.duree = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
			}
			else if(liste.length == 3){
				if(liste[2].compareToIgnoreCase("AM") == 0 || liste[2].compareToIgnoreCase("PM") == 0){
					if(liste[2].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
					else if(liste[2].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: duree.");
					if(checkDuree(liste[0]) && checkDuree(liste[1])) this.duree = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
				}
				else if(checkDuree(liste[2])){
					if(checkDuree(liste[0]) && checkDuree(liste[1])) this.duree = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
				}
				else throw new Exception("Erreur: duree.");
			}
			else if(liste.length == 2){
				if(checkDuree(liste[0]) && checkDuree(liste[1])) this.duree = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
			}
		}
		else throw new Exception("Erreur: duree.");
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date)throws Exception{
		if(date.length() >= 8 && date.length() <= 11){
			char[] sep = {'.','/','-',' ',':'};
			String[] liste = Split.splitString(date, sep);
			this.date = Date.valueOf(getAnnee(liste[2]) + "-" + getMois(liste[1]) + "-" + getJour((liste[0])));
		}
		else throw new Exception("Erreur: date.");
	}
	
	private String getJour(String str)throws Exception{
		if(Integer.valueOf(str) > 0 && Integer.valueOf(str) < Calendar.getInstance().getActualMaximum(5)) return str;
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
	
	private boolean checkDuree(String str)throws Exception{
		return Integer.valueOf(str) >= 0 && Integer.valueOf(str) <= 60;
	}
}
