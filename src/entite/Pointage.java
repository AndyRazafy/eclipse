package entite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;

import database.connection.DatabaseConnection;
import database.manager.DatabaseManager;
import outil.Split;

public class Pointage 
{
	private int id;
	private int empId;
	private Time heure;
	private Date date;
	
	public Pointage(int id, int empId, Time heure, Date date)throws Exception{
		super();
		this.setId(id);
		this.setEmpId(empId);
		this.setHeure(heure);
		this.setDate(date);
	}
	
	public Pointage(String empId, String heure, String date)throws Exception{
		super();
		this.setEmpId(empId);
		this.setHeure(heure);
		this.setDate(date);
	}

	public int getId() {
		return id;
	}
	
	public int getEmpId() {
		return empId;
	}
	
	public Time getHeure() {
		return heure;
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
	
	public void setEmpId(String empId)throws SQLException, Exception{
		Connection conn = null;
		String condition = "";
		try{
			if(estCIN(empId)) condition = "emp_cin = '" + empId + "'";
			else condition = "emp_matricule = '" + empId + "'";
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "andy");
			int resultat = (int) DatabaseManager.getEmpLoyeId(condition, conn);
			this.setEmpId(resultat);
		}
		catch(SQLException sqle){
			throw sqle;
		}
		finally{
			if(conn != null) conn.close();
		}
	}
	
	public void setHeure(Time heure) {
		this.heure = heure;
	}
	
	public void setHeure(String heure)throws Exception{
		if(heure.length() > 0 && heure.length() < 13){
			char[] sep = {'.',':','-',' ','/','h'};
			String[] liste = Split.splitString(heure, sep);
			if(liste.length == 4){
				if(liste[3].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
				else if(liste[3].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: Heure.");
				if(checkHeure(liste[0]) && checkHeure(liste[1]) && checkHeure(liste[2])) this.heure = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
			}
			else if(liste.length == 3){
				if(liste[2].compareToIgnoreCase("AM") == 0 || liste[2].compareToIgnoreCase("PM") == 0){
					if(liste[2].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
					else if(liste[2].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: Heure.");
					if(checkHeure(liste[0]) && checkHeure(liste[1])) this.heure = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
				}
				else if(checkHeure(liste[2])){
					if(checkHeure(liste[0]) && checkHeure(liste[1])) this.heure = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
				}
				else throw new Exception("Erreur: heure.");
			}
			else if(liste.length == 2){
				if(checkHeure(liste[0]) && checkHeure(liste[1])) this.heure = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
			}
		}
		else throw new Exception("Erreur: heure.");
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
	
	private boolean checkHeure(String str)throws Exception{
		return Integer.valueOf(str) >= 0 && Integer.valueOf(str) <= 60;
	}
	
	private boolean estCIN(String str){
		return str.matches("\\d+");
	}

	@Override
	public String toString() {
		return "Pointage [id=" + id + ", empId=" + empId + ", heure=" + heure + ", date=" + date + "]";
	}
}
