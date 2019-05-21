package de.uni_koeln.spinfo.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Util {
	
	/**
	 * proofs if all letters in the string are upper case (if so, the hyphen must
	 * not be deleted)
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isAllUpperCase(String string) {

		string = string.replaceAll("-", "");

		int i = 0;
		try {
			// character ist entweder kein Buchstabe oder ein gro�geschriebener Buchstabe
			while (!Character.isLetter(string.charAt(i)) || Character.isUpperCase(string.charAt(i))) {
				i++;
			}
		} catch (StringIndexOutOfBoundsException e) {
			return true;
		}
		return false;
	}
	
	public static void writeNewCoordinations(Map<String, String> possCompoundSplits, File possCompoundsFile) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> e : possCompoundSplits.entrySet()) {
			sb.append(e.getKey() + "|" + e.getValue() + "\n");
		}

		try {
			
			if (!possCompoundsFile.exists()) {
				possCompoundsFile.getParentFile().mkdirs();
				possCompoundsFile.createNewFile();
			}
			FileWriter fw = new FileWriter(possCompoundsFile);
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
