package metier;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import database.connection.DatabaseConnection;
import database.manager.DatabaseManager;
import entite.EmploiDuTemps;
import entite.JourFerie;
import entite.Pointage;
import entite.Retard;
import entite.Surplus;

public class Pointer 
{	
	public static boolean pointerEntree(String empId, String heure, String date)throws SQLException, Exception{
		Connection conn = null;
		try{	
			Pointage p = new Pointage(empId, heure, date);
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "andy");
			if(estEntree(p, conn)){
				JourFerie jf = getJourFerie(p.getDate(), conn);
				if(jf != null){
					if((int) (jf.getHDebut().getTime() - p.getHeure().getTime()) > 0){
						ajouterSurplus(p, calculDifference(p.getHeure(), jf.getHDebut()),conn);
					}
					pointer(p, "e", conn);
					return true;
				}
				else{
					EmploiDuTemps edt = DatabaseManager.getEmploiDuTemps(p, jourToString(getJourSemaine(p.getDate())), conn);
					if(edt.getHDebut() != null && edt.getHFin() != null){
						if(enRetard(p, "e",conn)){ 
							ajouterRetard(p, calculDifference(edt.getHDebut(), p.getHeure()), conn);
						}
						else{
							ajouterSurplus(p, calculDifference(p.getHeure(), edt.getHDebut()),conn);
						}
					}
					pointer(p, "e", conn);
					return true;
				}
			}
			else return false;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(conn != null) conn.close();
		}
	}
	
	public static boolean pointerSortie(String empId, String heure, String date)throws Exception{
		Connection conn = null;
		try{	
			Pointage p = new Pointage(empId, heure, date);
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "andy");
			if(!estEntree(p, conn)){
				JourFerie jf = getJourFerie(p.getDate(), conn);
				if(jf != null){
					if((int) (p.getHeure().getTime() - jf.getHFin().getTime()) > 0)
						ajouterSurplus(p, calculDifference(jf.getHFin(), p.getHeure()),conn);
					pointer(p, "s", conn);
					return true;
				}
				else{
					EmploiDuTemps edt = DatabaseManager.getEmploiDuTemps(p, jourToString(getJourSemaine(p.getDate())), conn);
					if(edt.getHDebut() != null && edt.getHFin() != null){
						if(enRetard(p, "",conn)){ 
							ajouterRetard(p, calculDifference(p.getHeure(), edt.getHFin()), conn);
						}
						else{
							ajouterSurplus(p, calculDifference(edt.getHFin(), p.getHeure()), conn);
						}
					}
					pointer(p, "s",  conn);
					return true;
				}
			}
			else return false;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(conn != null) conn.close();
		}
	}
	
	private static void pointer(Pointage p, String situation, Connection conn)throws SQLException, Exception{
		DatabaseManager.insertIntoTable(p, situation, conn);
	}
	
	private static void ajouterRetard(Pointage p, Time retard, Connection conn)throws SQLException, Exception{
		Retard rtd = new Retard(p.getEmpId(), retard, p.getDate());
		DatabaseManager.insertIntoTable(rtd, "", conn);
	}
	
	private static void ajouterSurplus(Pointage p, Time surplus, Connection conn)throws SQLException, Exception{
		Surplus sp = new Surplus(p.getEmpId(), surplus, p.getDate());
		DatabaseManager.insertIntoTable(sp, "", conn);
	}
	
	private static boolean estEntree(Pointage p, Connection conn)throws SQLException, Exception{
		// get last pointage
		Pointage pEntree = DatabaseManager.getLastPointage("e", p,conn); // e = "entree"
		Pointage pSortie = DatabaseManager.getLastPointage("", p,conn); // "" = [s] sortie
		if(pEntree == null) return true;
		else{
			if(pEntree != null && pSortie == null) return false;
			else if(pEntree.getHeure().compareTo(pSortie.getHeure()) == -1) return true;
			else return false;
		}
	}
	
	private static JourFerie getJourFerie(Date d, Connection conn)throws SQLException, Exception{
		JourFerie jf = DatabaseManager.getJourFerie(d, conn);
		return jf;
	}
	
	public static boolean enRetard(Pointage p, String situation, Connection conn)throws SQLException, Exception{
		EmploiDuTemps edt = DatabaseManager.getEmploiDuTemps(p, jourToString(getJourSemaine(p.getDate())), conn);
		if(situation.compareToIgnoreCase("e") == 0){
			if(edt.getHDebut() == null) return false;
			else if(p.getHeure().compareTo(edt.getHDebut()) == 1) return true;
			else return false;
		}
		else{
			if(edt.getHFin() == null) return false;
			else if(p.getHeure().compareTo(edt.getHFin()) == -1) return true;
			else return false;
		}
	}
	
	public static Time calculDifference(Time reel, Time ref)throws Exception{
		SimpleDateFormat sdfm = new SimpleDateFormat("HH:mm:ss");
		sdfm.setTimeZone(TimeZone.getTimeZone("GMT"));
		return Time.valueOf(sdfm.format(new Time(ref.getTime() - reel.getTime())));
	}
	
	public static int getJourSemaine(Date d)throws Exception{
		java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
		cal.setTime(d);
		return cal.get(java.util.Calendar.DAY_OF_WEEK);
	}
	
	public static String jourToString(int jour)throws Exception{
		switch(jour){
			case 1:
				return "dimanche";
			case 2:
				return "lundi";
			case 3:
				return "mardi";
			case 4:
				return "mercredi";
			case 5:
				return "jeudi";
			case 6:
				return "vendredi";
			case 7:
				return "samedi";
			default:
				throw new Exception("outOfBounds");
		}
	}
}
