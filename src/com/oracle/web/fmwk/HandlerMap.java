/**
 * 
 */
package com.oracle.web.fmwk;

import com.oracle.web.servlet.WebController;

/**
 * @author raparash
 *
 */
public class HandlerMap {
	private Class<? extends WebController> webControllerClass;
	private String methodName;
	private Class<?>[] methodParams;
	/**
	 * @return the webControllerClass
	 */
	public Class<? extends WebController> getWebControllerClass() {
		return webControllerClass;
	}
	/**
	 * @param webControllerClass the webControllerClass to set
	 */
	public void setWebControllerClass(Class<? extends WebController> webControllerClass) {
		this.webControllerClass = webControllerClass;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the methodParams
	 */
	public Class<?>[] getMethodParams() {
		return methodParams;
	}
	/**
	 * @param methodParams the methodParams to set
	 */
	public void setMethodParams(Class<?>[] methodParams) {
		this.methodParams = methodParams;
	}
	
	

}
