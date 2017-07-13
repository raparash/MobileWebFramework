/**
 * 
 */
package com.oracle.web.util;

/**
 * @author raparash
 *
 */
public class StringUtil {

	/**
	 * Returns a safelyTrimmed String
	 * @param text
	 * @return
	 */
	public static String safeTrim(String text){
		String val="";
		if(text!=null){
			val=text.trim();
		}
		return val;
	}
}
