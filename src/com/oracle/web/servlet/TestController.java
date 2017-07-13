/**
 * 
 */
package com.oracle.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.web.fmwk.RequestMapping;

/**
 * @author raparash
 *
 */
public class TestController  implements WebController{
	
	@RequestMapping("/test")
	public String demoTest(HttpServletRequest request,HttpServletResponse response){
		return "hello";
	}

	@RequestMapping("/dummy/test1")
	public String testCall(HttpServletRequest request,HttpServletResponse response){
		return "test call method called";
	}
	
}
