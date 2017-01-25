package metier;

import java.sql.Connection;
import java.sql.SQLException;

import database.connection.DatabaseConnection;
import database.manager.DatabaseManager;
import entite.Conge;

public class DemanderConge 
{
	public static void prendreConge(String empId, String dDebut, String dFin, String hDebut, String hFin)throws Exception{
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "itu");
			Conge c = new Conge(empId, dDebut, dFin, hDebut, hFin);
			if(!congeEnAttente(c.getEmpId(), conn)){
				DatabaseManager.insertIntoTable(c, "", conn);
			}
			else throw new Exception("L'employe est en conge");
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
	
	public static boolean dejaEnConge(int empId, Connection conn)throws Exception{
		try{	
			return DatabaseManager.getNombreConge(empId, conn) != 0;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
	}
	
	public static boolean congeEnAttente(int empId, Connection conn)throws Exception{
		try{	
			return DatabaseManager.getNombreConge(empId, conn) != 0;
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
	}
}
