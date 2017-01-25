package testing;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import org.junit.Test;

import metier.Pointer;
import outil.Split;

public class PointerTest 
{
	@Test
	public void testCalculDifference() throws Exception{
		Time t1 = Time.valueOf("02:30:10"), t2 = Time.valueOf("02:10:00");
		Time result = Pointer.calculDifference(t2, t1);
		assertEquals(Time.valueOf("00:20:10").getTime(), result.getTime());
	}
	
	@Test
	public void testGetJourSemaine() throws Exception{
		Date d = Date.valueOf("2017-07-15");
		assertEquals(7, Pointer.getJourSemaine(d));
	}
	
	@Test
	public void testJourToString() throws Exception{
		assertEquals("lundi", Pointer.jourToString(2));
		assertEquals("mardi", Pointer.jourToString(3));
		assertEquals("mercredi", Pointer.jourToString(4));
		assertEquals("jeudi", Pointer.jourToString(5));
		assertEquals("vendredi", Pointer.jourToString(6));
		assertEquals("samedi", Pointer.jourToString(7));
		assertEquals("dimanche", Pointer.jourToString(1));
	}
}
