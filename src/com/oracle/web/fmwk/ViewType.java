/**
 * 
 */
package com.oracle.web.fmwk;

/**
 * @author raparash
 *
 */
public enum ViewType {

	JSP_VIEW("jsp"), AJAX_VIEW("ajax");
	
	
	
	private String type;
	private ViewType(String type){
		this.setType(type);
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
