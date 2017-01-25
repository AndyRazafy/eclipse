import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import database.connection.DatabaseConnection;
import database.manager.DatabaseManager;
import entite.Employe;
import entite.Pointage;
import metier.Pointer;

public class test 
{
	public static void main(String[] args)throws Exception{
		//Employe emp = new Employe(1, "EMP0003", "789123456789", "Rakotoson", "Faly", Date.valueOf("1985-10-12"), Date.valueOf("2017-01-14"), "Lot I 32 bis Antanimena Antananarivo");
		//System.out.println(emp);
		/*System.out.println("Connect to database");
		try{
			System.out.println("connecting...");
			Connection conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "andy");
			System.out.println("connected to: " + conn);
			System.out.println("insert data into database");
			DatabaseManager.insertIntoTable(emp, "",conn);
			System.out.println("inserted");
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}*/
		//Pointage p1 = new Pointage("123456789123", "7:55", "14/01.2017");
		//Pointage p2 = new Pointage("123456789123", "6:55", "14/01.2017");
		//System.out.println(new Time(503000));
		//Pointer.pointerEntree("123456789123", "7:50", "23/01.2017");
		//Pointer.pointerSortie("123456789123", "18:55", "16/01.2017");
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date d1 = Date.valueOf("2017-01-25"), d2 = Date.valueOf("2017-01-26");
		Time ref = Time.valueOf("24:00:00"), t1 = Time.valueOf("08:00:00"), t2 = Time.valueOf("20:00:00");
		SimpleDateFormat sdfm = new SimpleDateFormat("HH:mm:ss");
		sdfm.setTimeZone(TimeZone.getTimeZone("GMT"));
		//long l = (d2.getTime() - (Time.valueOf("24:00:00").getTime() - t2.getTime())) - (d1.getTime() - t1.getTime());
		long l = (d2.getTime() - d1.getTime()) - ((Time.valueOf("24:00:00").getTime() - t2.getTime()) + t1.getTime());
		System.out.println((d2.getTime() - d1.getTime())/(1000 * 60 * 60));
		System.out.println(((Time.valueOf("24:00:00").getTime() - t2.getTime()) + t1.getTime())/(1000 * 60 * 60));
		System.out.println(l/(1000 * 60 * 60 * 24) + "j");
		System.out.println(((l/(1000 * 60 * 60)) % (24)) + "h");
	}
}
