/**
 * 
 */
package com.oracle.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author raparash
 *
 */
public class PropertyManager {
	
	private static Map<String,Properties> propertiesMap=new HashMap<String,Properties>();
	
	/**
	 * init method
	 * @param propertiesName
	 */
	private static void init(String propertiesName){
		try{
		if(!propertiesMap.containsKey(propertiesName)){
			Properties properties=new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName));
			propertiesMap.put(propertiesName, properties);
		}
		}catch(Exception e){
			
		}
		
	}
	
	/**
	 * 
	 * @param propertiesName
	 * @return
	 */
	public static String getValue(String propertiesName, String key){
		init(propertiesName);
		return StringUtil.safeTrim(propertiesMap.get(propertiesName).getProperty(key));
	}
	
}
