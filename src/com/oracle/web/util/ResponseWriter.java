/**
 * 
 */
package com.oracle.web.util;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author raparash
 *
 */
public class ResponseWriter {
	
	private static final Logger LOGGER=Logger.getLogger(ResponseWriter.class.getName());
	
	/**
	 * Method to write to outputStream
	 * @param outputStream
	 * @param content
	 */
	public static void writeOutputStream(OutputStream outputStream, String content){
		BufferedWriter bufferedWriter=null;
		OutputStreamWriter outputStreamWriter=null;
		try{
			LOGGER.log(Level.INFO, "Inside|ResponseWriter|writeOutputStream() ");
			outputStreamWriter=new OutputStreamWriter(outputStream);
			bufferedWriter=new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(content);
			bufferedWriter.flush();
		}catch(Exception ex){
			LOGGER.log(Level.WARNING,"Exception|ResponseWriter|writeOutputStream() ", ex);
		}finally{
			safeClose(bufferedWriter,outputStreamWriter,outputStream);
		}
	}
	
	/**
	 * Safely Closes the closeable streams
	 * 
	 * @param closeables
	 */
	private static void safeClose(Closeable...closeables){
		if(closeables!=null){
			for(Closeable closeable:closeables){
				try {
					closeable.close();
				} catch (Exception e) {
					LOGGER.log(Level.WARNING,"Exception|ResponseWriter|safeClose() ", e);
				}
			}
		}
	}

}
