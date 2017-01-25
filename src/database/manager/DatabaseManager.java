package database.manager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import entite.Conge;
import entite.EmploiDuTemps;
import entite.Employe;
import entite.JourFerie;
import entite.Pointage;
import entite.Retard;
import entite.Surplus;

public class DatabaseManager 
{
	public static void insertIntoTable(Object obj, String table,Connection conn)throws Exception, SQLException{
		PreparedStatement statement = null;
		Savepoint savepoint = null;
		try{
			if(obj instanceof Employe){
				Employe e = (Employe) obj;
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				statement = conn.prepareStatement("INSERT INTO employe (poste_id,emp_matricule,emp_cin,emp_nom,emp_prenom,emp_naisdate,emp_engdate,emp_adresse) VALUES (?,?,?,?,?,?,?,?)");
				statement.setInt(1, e.getPoste());
				statement.setString(2, e.getMatricule());
				statement.setString(3, e.getCin());
				statement.setString(4, e.getNom());
				statement.setString(5, e.getPrenom());
				statement.setDate(6, e.getNaissance());
				statement.setDate(7, e.getEnregistrement());
				statement.setString(8, e.getAdresse());
				statement.executeUpdate();
				conn.commit();
			}
			else if(obj instanceof Retard){
				Retard r = (Retard) obj;
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				statement = conn.prepareStatement("INSERT INTO retard (emp_id,rtd_heure,rtd_date) VALUES (?,?,?)");
				statement.setInt(1, r.getEmpId());
				statement.setTime(2, r.getDuree());
				statement.setDate(3, r.getDate());
				statement.executeUpdate();
				conn.commit();
			}
			else if(obj instanceof Pointage){
				Pointage p = (Pointage) obj;
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				if(table.compareToIgnoreCase("e") == 0){
					statement = conn.prepareStatement("INSERT INTO pointage_entree (emp_id,pte_heure,pte_date) VALUES (?,?,?)");
				}
				else if(table.compareToIgnoreCase("s") == 0){
					statement = conn.prepareStatement("INSERT INTO pointage_sortie (emp_id,pts_heure,pts_date) VALUES (?,?,?)");
				}
				statement.setInt(1, p.getEmpId());
				statement.setTime(2, p.getHeure());
				statement.setDate(3, p.getDate());
				statement.executeUpdate();
				conn.commit();
			}
			else if(obj instanceof JourFerie){
				JourFerie jf = (JourFerie) obj;
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				statement = conn.prepareStatement("INSERT INTO jour_ferie (jf_hdebut,jf_hfin,jf_date,jf_motif) VALUES (?,?,?,?)");
				statement.setTime(1, jf.getHDebut());
				statement.setTime(2, jf.getHFin());
				statement.setDate(3, jf.getDate());
				statement.setString(4, jf.getMotif());
				statement.executeUpdate();
				conn.commit();
			}
			else if(obj instanceof Surplus){
				Surplus sp = (Surplus) obj;
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				statement = conn.prepareStatement("INSERT INTO surplus (emp_id,plus_heure,plus_date) VALUES (?,?,?)");
				statement.setInt(1, sp.getEmpId());
				statement.setTime(2, sp.getDuree());
				statement.setDate(3, sp.getDate());
				statement.executeUpdate();
				conn.commit();
			}
			else if(obj instanceof Conge){
				Conge c = (Conge) obj;
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				if(table.compareToIgnoreCase("") == 0){
					statement = conn.prepareStatement("INSERT INTO conge (emp_id,\"conge_dDebut\",\"conge_dFin\",\"conge_hDebut\",\"conge_hFin\",\"conge_dSaisie\") VALUES (?,?,?,?,?,current_date)");
					statement.setInt(1, c.getEmpId());
					statement.setDate(2, c.getDDebut());
					statement.setDate(3, c.getDFin());
					statement.setTime(4, c.getHDebut());
					statement.setTime(5, c.getHFin());
					statement.executeUpdate();
					conn.commit();
				}
				else if(table.compareToIgnoreCase("validation_conge") == 0){
					statement = conn.prepareStatement("INSERT INTO validation_conge (conge_id,vc_date) VALUES (?,current_date)");
					statement.setInt(1, c.getId());
					statement.executeUpdate();
					conn.commit();
				}
				else if(table.compareToIgnoreCase("refus_conge") == 0){
					statement = conn.prepareStatement("INSERT INTO refus_conge (conge_id,refus_date) VALUES (?,current_date)");
					statement.setInt(1, c.getId());
					statement.executeUpdate();
					conn.commit();
				}
			}
		}catch(SQLException sqle){
			conn.rollback();
			throw sqle;
		}catch(Exception e){
			conn.rollback(savepoint);
			throw e;
		}
		finally{
			if(statement != null) statement.close();
		}
	}
	
	public static List<Object> find(String column, String condition, String table, Connection conn)throws Exception, SQLException{
		String requestString = "SELECT ";
		
		//Statement statement = conn.createStatement();
		requestString += column;
		
		// set table name
		requestString += " FROM " + table;
		if(condition.length() != 0){
			requestString += " WHERE " + condition;
		}
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(requestString);
		List<Object> list = new ArrayList<Object>(100);
		if(table.compareToIgnoreCase("Pointage") == 0){
			Pointage p;
			while(rs.next()){
				p = new Pointage(rs.getInt(1),
									rs.getInt(2),
									rs.getTime(3),
									rs.getDate(4));
				list.add(p);
			}
		}
		return list;
	}
	
	public static int getEmpLoyeId(String condition, Connection conn)throws SQLException, Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		try{
			statement = conn.prepareStatement("SELECT emp_id FROM employe WHERE " + condition);
			rs = statement.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	public static Employe getEmployeById(int condition, Connection conn)throws SQLException, Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Employe employe = null;
		try{
			statement = conn.prepareStatement("SELECT * FROM employe emp WHERE emp.emp_id = ?");
			statement.setInt(1, condition);
			rs = statement.executeQuery();
			while(rs.next()){
				employe = new Employe(rs.getInt(1),
											rs.getString(3),
											rs.getString(4),
											rs.getString(5),
											rs.getString(6),
											rs.getDate(7),
											rs.getDate(8),
											rs.getString(9));
			}
			return employe;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	public static Pointage getLastPointage(String vue, Pointage ref, Connection conn)throws SQLException, Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Pointage p = null;
		try{
			if(vue.compareToIgnoreCase("e") == 0) statement = conn.prepareStatement("SELECT * FROM last_pointage_entree WHERE (emp_id = ? AND pte_date = ?) LIMIT 1");
			else statement = conn.prepareStatement("SELECT * FROM last_pointage_sortie WHERE (emp_id = ? AND pts_date = ?) LIMIT 1");
			statement.setInt(1, ref.getEmpId());
			statement.setDate(2, ref.getDate());
			rs = statement.executeQuery();
			while(rs.next()){
				p = new Pointage(rs.getInt(1),
								rs.getInt(2),
								rs.getTime(3),
								rs.getDate(4));
			}
			return p;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	public static JourFerie getJourFerie(Date condition, Connection conn)throws SQLException, Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		JourFerie jf = null;
		try{
			statement = conn.prepareStatement("SELECT * FROM jour_ferie WHERE jf_date = ?");
			statement.setDate(1, condition);
			rs = statement.executeQuery();
			while(rs.next()){
				jf = new JourFerie(rs.getInt(1),
								rs.getTime(2),
								rs.getTime(3),
								rs.getDate(4),
								rs.getString(5));
			}
			return jf;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	public static EmploiDuTemps getEmploiDuTemps(Pointage p, String condition, Connection conn)throws SQLException, Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		EmploiDuTemps edt = null;
		try{
			statement = conn.prepareStatement("SELECT ee.emp_id, ee." + condition + " as entree, es." + condition + " as sortie FROM emploi_entree ee, emploi_sortie es WHERE (ee.emp_id = es.emp_id AND ee.emp_id = ?)");
			statement.setInt(1, p.getEmpId());
			rs = statement.executeQuery();
			while(rs.next()){
				edt = new EmploiDuTemps(rs.getInt(1),
								rs.getTime(2),
								rs.getTime(3));
			}
			return edt;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	public static Vector<String> getAllPoste(Connection conn)throws SQLException, Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Vector<String> posteListe = null;
		try{
			statement = conn.prepareStatement("SELECT poste_intitule FROM poste");
			rs = statement.executeQuery();
			posteListe = new Vector<String>(20);
			while(rs.next()){
				posteListe.addElement(rs.getString(1));
			}
			return posteListe;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	public static ArrayList<Employe> getAllEmploye(String condition, Connection conn)throws SQLException, Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Employe> employeListe = null;
		try{
			statement = conn.prepareStatement("SELECT * FROM employe emp, poste WHERE (emp.poste_id = poste.poste_id AND poste.poste_intitule = ?)");
			statement.setString(1, condition);
			rs = statement.executeQuery();
			employeListe = new ArrayList<Employe>(50);
			while(rs.next()){
				Employe emp = new Employe(rs.getInt(1),
											rs.getString(3),
											rs.getString(4),
											rs.getString(5),
											rs.getString(6),
											rs.getDate(7),
											rs.getDate(8),
											rs.getString(9));
				employeListe.add(emp);
			}
			return employeListe;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	public static ArrayList<Conge> getCongeAValider(Connection conn)throws SQLException, Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Conge> congeListe = null;
		try{
			statement = conn.prepareStatement("SELECT * FROM conge c WHERE c.conge_id NOT IN (SELECT conge_id FROM validation_conge) AND c.conge_id NOT IN (SELECT conge_id FROM refus_conge)");
			rs = statement.executeQuery();
			congeListe = new ArrayList<Conge>(50);
			while(rs.next()){
				Conge c = new Conge(rs.getInt(1),
										rs.getInt(2),
										rs.getDate(3),
										rs.getDate(4),
										rs.getTime(5),
										rs.getTime(6),
										rs.getDate(7));
				congeListe.add(c);
			}
			return congeListe;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	public static int getNombreConge(int empId, Connection conn) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		try{
			statement = conn.prepareStatement("SELECT COUNT(c.conge_id) FROM conge c WHERE (c.conge_id IN (SELECT conge_id FROM validation_conge) AND c.conge_id IN (SELECT conge_id FROM retour_conge)) AND c.emp_id = ?");
			statement.setInt(1, empId);
			rs = statement.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	public static int getDerniereConge(int empId, Connection conn) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		try{
			statement = conn.prepareStatement("SELECT COUNT(c.conge_id) FROM conge c WHERE (c.conge_id IN (SELECT conge_id FROM validation_conge) OR c.conge_id IN (SELECT conge_id FROM retour_conge) OR c.conge_id IN (SELECT conge_id FROM refus_conge)) AND c.emp_id = ?");
			statement.setInt(1, empId);
			rs = statement.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(statement != null) statement.close();
			if(rs != null) rs.close();
		}
	}
	
	/*public static void insertIntoTable(String[] list, String tableName, Connection conn)throws Exception, SQLException{
		PreparedStatement statement = null;
		Savepoint savepoint = null;
		try{
			if(tableName.compareToIgnoreCase("contact") == 0){
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				statement = conn.prepareStatement("INSERT INTO " + tableName + " (pat_id, contact_valeur, contact_dateajout) VALUES (?,?,now())");
				statement.setInt(1, Integer.valueOf(list[0]));
				statement.setString(2, list[1]);
				statement.executeUpdate();
				conn.commit();
			}
		}catch(SQLException sqle){
			conn.rollback(savepoint);
			throw sqle;
		}catch(Exception e){
			conn.rollback(savepoint);
			throw e;
		}
		finally{
			if(statement != null) statement.close();
		}
	}
	
	public static void update(Object obj, Connection conn)throws Exception, SQLException{
		PreparedStatement statement = null;
		Savepoint savepoint = null;
		try{
			if(obj instanceof Patient){
				Patient p = (Patient) obj;
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				statement = conn.prepareStatement("UPDATE patient SET pat_nom=?,pat_prenom=?,pat_matricule=?,pat_cin=?,pat_sexe=?,pat_datenaissance=?,pat_adresse=? WHERE pat_id = ?");
				statement.setString(1, p.getNom());
				statement.setString(2, p.getPrenom());
				statement.setString(3, p.getMatricule());
				statement.setString(4, p.getCin());
				statement.setString(5, p.getSexe());
				statement.setDate(6, p.getDateNaissance());
				statement.setString(7, p.getAdresse());
				statement.setInt(8, p.getId());
				statement.executeUpdate();
				conn.commit();
			}
		}catch(SQLException sqle){
			conn.rollback(savepoint);
			throw sqle;
		}catch(Exception e){
			conn.rollback(savepoint);
			throw new SQLException("Sql Exception");
		}
		finally{
			if(statement != null) statement.close();
		}
	}
	
	public static void delete(Object obj, Connection conn)throws Exception, SQLException{
		PreparedStatement statement = null;
		Savepoint savepoint = null;
		try{
			if(obj instanceof Patient){
				Patient p = (Patient) obj;
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				statement = conn.prepareStatement("DELETE FROM patient WHERE pat_id = ?");
				statement.setInt(1, p.getId());
				statement.executeUpdate();
				conn.commit();
			}
		}catch(SQLException sqle){
			conn.rollback(savepoint);
			throw sqle;
		}catch(Exception e){
			conn.rollback(savepoint);
			throw new SQLException("Sql Exception");
		}
		finally{
			if(statement != null) statement.close();
		}
	}
	
	public static void delete(int id, String tableName, Connection conn)throws Exception, SQLException{
		PreparedStatement statement = null;
		Savepoint savepoint = null;
		try{
			if(tableName.compareToIgnoreCase("contact") == 0){
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("Savepoint");
				statement = conn.prepareStatement("DELETE FROM contact WHERE contact_id = ?");
				statement.setInt(1, id);
				statement.executeUpdate();
				conn.commit();
			}
		}catch(SQLException sqle){
			conn.rollback(savepoint);
			throw sqle;
		}catch(Exception e){
			conn.rollback(savepoint);
			throw new SQLException("Sql Exception");
		}
		finally{
			if(statement != null) statement.close();
		}
	}
	
	public static List<Object> find(String column, String condition, String table, Connection conn)throws Exception, SQLException{
		String requestString = "SELECT ";
		
		//Statement statement = conn.createStatement();
		requestString += column;
		
		// set table name
		requestString += " FROM " + table;
		if(condition.length() != 0){
			requestString += " WHERE " + condition;
		}
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(requestString);
		List<Object> list = new ArrayList<Object>(100);
		if(table.compareToIgnoreCase("patient") == 0){
			Patient p;
			while(rs.next()){
				p = new Patient(rs.getInt(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getString(6),
									rs.getDate(7),
									rs.getDate(8),
									rs.getString(9));
				list.add(p);
			}
		}
		if(table.compareToIgnoreCase("docteur") == 0){
			Docteur d;
			while(rs.next()){
				d = new Docteur(rs.getInt(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getString(6),
									rs.getDate(7),
									rs.getDate(8),
									rs.getString(9),
									rs.getInt(10));
				list.add(d);
			}
		}
		return list;
	}
	
	public static List<String> getContactByPatientId(int id, Connection conn)throws Exception{
		PreparedStatement statement = conn.prepareStatement("SELECT contact_valeur FROM CONTACT WHERE PAT_ID = ?");
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();
		List<String> list = new ArrayList<String>(5);
		while(rs.next()){
			list.add(rs.getString(1));
		}
		return list;
	}
	
	public static DocteurDomaine getDomaineById(int id, Connection conn)throws Exception, SQLException{
		String sql = "SELECT * FROM domaine WHERE domaine_id = " + id;
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		rs.next();
		DocteurDomaine domaine = new DocteurDomaine(rs.getInt(1), rs.getString(2));
		return domaine;
	}
	
	public static boolean verifieMatriculeCin(Object obj, Connection conn)throws SQLException, Exception{
		if(obj instanceof Patient){
			Patient p = (Patient) obj;
			PreparedStatement statement = conn.prepareStatement("SELECT count(*) FROM Patient WHERE pat_matricule = ? OR pat_cin = ?");
			statement.setString(1, p.getMatricule());
			statement.setString(2, p.getCin());
			ResultSet rs = statement.executeQuery();
			rs.next();
			return rs.getInt(1) == 0 ;
		}
		else
			return false;
	}*/
}
