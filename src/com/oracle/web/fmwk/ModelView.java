/**
 * 
 */
package com.oracle.web.fmwk;

import java.util.HashMap;
import java.util.Map;

/**
 * @author raparash
 *
 */
public class ModelView {
	
	private String view;
	private Map<String,Object> attributes=new HashMap<String,Object>();
	private ViewType viewType;
	private Object body;
	/**
	 * @param view
	 * @param viewType
	 */
	public ModelView(String view, ViewType viewType) {
		super();
		this.view = view;
		this.viewType = viewType;
	}
	/**
	 * @param viewType
	 */
	public ModelView(ViewType viewType) {
		super();
		this.viewType = viewType;
	}
	/**
	 * @return the view
	 */
	public String getView() {
		return view;
	}
	/**
	 * @param view the view to set
	 */
	public void setView(String view) {
		this.view = view;
	}
	/**
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	/**
	 * Adds an attribute
	 * @param key
	 * @param value
	 */
	public void addAttribute(String key,Object value){
		this.attributes.put(key, value);
	}
	/**
	 * @return the viewType
	 */
	public ViewType getViewType() {
		return viewType;
	}
	/**
	 * @param viewType the viewType to set
	 */
	public void setViewType(ViewType viewType) {
		this.viewType = viewType;
	}
	/**
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(Object body) {
		this.body = body;
	}

}
