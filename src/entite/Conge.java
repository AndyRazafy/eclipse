package entite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;

import database.connection.DatabaseConnection;
import database.manager.DatabaseManager;
import outil.Split;

public class Conge 
{
	private int id;
	private int empId;
	private Date dDebut;
	private Date dFin;
	private Time hDebut;
	private Time hFin;
	private Date dSaisie;
	
	public Conge(int id, int emp_id, Date dDebut, Date dFin, Time hDebut, Time hFin, Date dSaisie) {
		super();
		this.setId(id);
		this.setEmpId(emp_id);
		this.setDDebut(dDebut);
		this.setDFin(dFin);
		this.setHDebut(hDebut);
		this.setHFin(hFin);
		this.setDSaisie(dSaisie);
	}
	
	public Conge(String emp_id, String dDebut, String dFin, String hDebut, String hFin)throws Exception {
		super();
		this.setEmpId(emp_id);
		this.setDDebut(dDebut);
		this.setDFin(dFin);
		this.setHDebut(hDebut);
		this.setHFin(hFin);
	}

	public int getId() {
		return id;
	}
	
	public int getEmpId() {
		return empId;
	}
	
	public Date getDDebut() {
		return dDebut;
	}
	
	public Date getDFin() {
		return dFin;
	}
	
	public Time getHDebut() {
		return hDebut;
	}
	
	public Time getHFin() {
		return hFin;
	}
	
	public Date getDSaisie(){
		return this.dSaisie;
	}
	
	@SuppressWarnings("deprecation")
	public int getDuree(){
		if(this.dDebut.equals(this.dFin)){
			int jour = 24;
			int hDebut = this.getHDebut().getHours();
			int hFin = jour - this.getHFin().getHours();
			jour -= hDebut; jour -= hFin;
			return jour;
		}
		else{
			int jour = (int) (this.getDFin().getTime() - this.getDDebut().getTime()) / (1000 * 3600);
			int hDebut = this.getHDebut().getHours();
			int hFin = (jour / (jour / 24)) - this.getHFin().getHours();
			jour -= hDebut; jour -= hFin;
			return jour;
		}
	}
	
	public String getDureeToString(){
		return String.valueOf(getDuree() / 24) + "j " + String.valueOf(getDuree() % 24) + "h";
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setEmpId(int emp_id) {
		this.empId = emp_id;
	}
	
	public void setEmpId(String empId)throws SQLException, Exception{
		Connection conn = null;
		String condition = "";
		try{
			if(empId.isEmpty()) throw new Exception("Impossible d'identifier l'employe.");
			else{
				if(estCIN(empId)) condition = "emp_cin = '" + empId + "'";
				else condition = "emp_matricule = '" + empId + "'";
				conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "itu");
				int resultat = (int) DatabaseManager.getEmpLoyeId(condition, conn);
				this.setEmpId(resultat);
			}
		}
		catch(SQLException sqle){
			throw sqle;
		}
		finally{
			if(conn != null) conn.close();
		}
	}
	
	public void setDDebut(Date dDebut) {
		this.dDebut = dDebut;
	}
	
	public void setDDebut(String date)throws Exception{
		if(date.length() >= 8 && date.length() <= 11){
			char[] sep = {'.','/','-',' ',':'};
			String[] liste = Split.splitString(date, sep);
			this.dDebut = Date.valueOf(getAnnee(liste[2]) + "-" + getMois(liste[1]) + "-" + getJour((liste[0])));
		}
		else throw new Exception("Erreur: date debut.");
	}
	
	public void setDFin(Date dFin) {
		this.dFin = dFin;
	}
	
	public void setDFin(String date)throws Exception{
		if(date.length() >= 8 && date.length() <= 11){
			char[] sep = {'.','/','-',' ',':'};
			String[] liste = Split.splitString(date, sep);
			Date tmp = Date.valueOf(getAnnee(liste[2]) + "-" + getMois(liste[1]) + "-" + getJour((liste[0])));
			if(tmp.equals(dDebut) || tmp.after(dDebut)) dFin = tmp;
			else throw new Exception("Date fin non valide.");
		}
		else throw new Exception("Erreur: date fin.");
	}
	
	public void setHDebut(Time hDebut) {
		this.hDebut = hDebut;
	}
	
	public void setHDebut(String heure)throws Exception{
		if(heure.length() > 0 && heure.length() < 13){
			char[] sep = {'.',':','-',' ','/','h'};
			String[] liste = Split.splitString(heure, sep);
			if(liste.length == 4){
				if(liste[3].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
				else if(liste[3].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: Heure debut.");
				if(checkHeure(liste[0]) && checkHeure(liste[1]) && checkHeure(liste[2])) this.hDebut = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
			}
			else if(liste.length == 3){
				if(liste[2].compareToIgnoreCase("AM") == 0 || liste[2].compareToIgnoreCase("PM") == 0){
					if(liste[2].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
					else if(liste[2].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: Heure debut.");
					if(checkHeure(liste[0]) && checkHeure(liste[1])) this.hDebut = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
				}
				else if(checkHeure(liste[2])){
					if(checkHeure(liste[0]) && checkHeure(liste[1])) this.hDebut = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
				}
				else throw new Exception("Erreur: heure debut.");
			}
			else if(liste.length == 2){
				if(checkHeure(liste[0]) && checkHeure(liste[1])) this.hDebut = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
			}
		}
		else throw new Exception("Erreur: Heure debut.");
	}
	
	public void setHFin(Time hFin) {
		this.hFin = hFin;
	}
	
	public void setHFin(String heure)throws Exception{
		if(heure.length() > 0 && heure.length() < 13){
			char[] sep = {'.',':','-',' ','/','h'};
			String[] liste = Split.splitString(heure, sep);
			if(liste.length == 4){
				if(liste[3].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
				else if(liste[3].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: Heure fin.");
				if(checkHeure(liste[0]) && checkHeure(liste[1]) && checkHeure(liste[2])) this.hFin = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
			}
			else if(liste.length == 3){
				if(liste[2].compareToIgnoreCase("AM") == 0 || liste[2].compareToIgnoreCase("PM") == 0){
					if(liste[2].compareToIgnoreCase("PM") == 0) liste[0] = String.valueOf(Integer.valueOf(liste[0]) + 12);
					else if(liste[2].compareToIgnoreCase("AM") != 0) throw new Exception("Erreur: Heure fin.");
					if(checkHeure(liste[0]) && checkHeure(liste[1])) this.hFin = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
				}
				else if(checkHeure(liste[2])){
					if(checkHeure(liste[0]) && checkHeure(liste[1])) this.hFin = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":" + liste[2]);
				}
				else throw new Exception("Erreur: Heure fin.");
			}
			else if(liste.length == 2){
				if(checkHeure(liste[0]) && checkHeure(liste[1])) this.hFin = (Time) Time.valueOf(liste[0] + ":" + liste[1] + ":00");
			}
		}
		else throw new Exception("Erreur: Heure fin.");
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
	
	private boolean estCIN(String str){
		return str.matches("\\d+");
	}
}
