package outil;

import java.util.ArrayList;

public class Split 
{
	// remember to set array's length for very long String
	public static String[] splitString(String str, char[] charList)throws Exception{
		ArrayList<String> array = new ArrayList<String>(50);
		char separator = setSeparator(str, charList);
		int start = 0, end = str.indexOf(separator, start);
		while(end > start){
			array.add(str.substring(start,end));
			start = end + 1;
			separator = setSeparator(str.substring(start), charList);
			end = str.indexOf(separator, start);
		}
		array.add(str.substring(start));
		String[] resultList = new String[array.size()];
		int i=0;
		for(String obj : array){
			resultList[i] = (String) obj;
			i++;
		}
		return resultList;
	}
	
	public static char setSeparator(String str, char[] charList)throws Exception{
		int i = 0, j = 0, limit1 = str.length(), limit2 = charList.length;
		for(i = 0;i < limit1;i++){
			for(j = 0;j < limit2;j++){
				if(str.charAt(i) == charList[j]) return charList[j];
			}
			j = 0;
		}
		return charList[j];
	}

	public static String[] splitString(String str, char separator)throws Exception{
		ArrayList<String> array = new ArrayList<String>(50);
		int start = 0, end = str.indexOf(separator, start);
		while(end > start){
			array.add(str.substring(start,end));
			start = end + 1;
			end = str.indexOf(separator, start);
		}
		array.add(str.substring(start));
		String[] resultList = new String[array.size()];
		int i=0;
		for(String obj : array){
			resultList[i] = (String) obj;
			i++;
		}
		return resultList;
	}
}
