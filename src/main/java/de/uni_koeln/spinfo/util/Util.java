package de.uni_koeln.spinfo.util;

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
			// character ist entweder kein Buchstabe oder ein groﬂgeschriebener Buchstabe
			while (!Character.isLetter(string.charAt(i)) || Character.isUpperCase(string.charAt(i))) {
				i++;
			}
		} catch (StringIndexOutOfBoundsException e) {
			return true;
		}
		return false;
	}

}
