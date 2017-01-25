package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import outil.Split;

public class SplitTest 
{
	@Test
	public void testSplitString1() throws Exception{
		String str = "he.l/lo wo'rl!d";
		char[] separator = {'.','/',' ','\'','!'};
		String[] array = Split.splitString(str, separator);
		assertEquals(6, array.length);
	}
	
	@Test
	public void testSplitString2() throws Exception{
		String str = "he.l.lo wo.rl.d";
		char[] separator = {'.'};
		String[] array = Split.splitString(str, separator);
		assertEquals(5, array.length);
	}
	
	@Test
	public void testSetSeparator() throws Exception{
		String str = "hello!word";
		char[] charList = {',','/','!','.'};
		assertEquals('!', Split.setSeparator(str, charList));
	}
}
