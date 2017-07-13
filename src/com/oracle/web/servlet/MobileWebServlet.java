package com.oracle.web.servlet;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.oracle.web.fmwk.AnnotationHandler;
import com.oracle.web.fmwk.JsonBean;
import com.oracle.web.fmwk.ModelView;
import com.oracle.web.fmwk.ViewType;
import com.oracle.web.util.ResponseWriter;

/**
 * Servlet implementation class MobileWebServlet
 */

public class MobileWebServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String configFileName;
	private String contextRoot;
	private static final Logger LOGGER = Logger.getLogger(MobileWebServlet.class.getName());

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		configFileName = config.getInitParameter("application-properties");
		contextRoot = config.getInitParameter("context-root");
		AnnotationHandler.init(configFileName);
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletpath = request.getRequestURI().replace("/" + contextRoot, "");
		LOGGER.log(Level.INFO, "Inside|MobileWebServlet|service()| urlPattern= " + servletpath);
		Object object = AnnotationHandler.invokeControllerMethod(servletpath, request, response);
		process(object, request, response);
	}

	private void process(Object object, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (object == null) {
				response.sendError(404);
			} else {
				if (object instanceof ModelView) {
					ModelView mvObject = (ModelView) object;
					LOGGER.log(Level.INFO,
							"Inside|MobileWebServlet|process()| ModelView detected| Type= " + mvObject.getViewType());
					if (mvObject.getViewType() == ViewType.JSP_VIEW) {
						RequestDispatcher dispatcher = request.getRequestDispatcher(mvObject.getView());
						if (!mvObject.getAttributes().isEmpty()) {
							for (Entry<String, Object> entry : mvObject.getAttributes().entrySet()) {
								request.setAttribute(entry.getKey(), entry.getValue());
							}
						}
						dispatcher.forward(request, response);
					} else if (mvObject.getViewType() == ViewType.AJAX_VIEW) {
						ResponseWriter.writeOutputStream(response.getOutputStream(), ajaxOutput(mvObject.getBody()));
					}
				} else {
					ResponseWriter.writeOutputStream(response.getOutputStream(), ajaxOutput(object));
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Exception|MobileWebServlet|process() ", e);
		}
	}

	/**
	 * converts the given object to string or to Json
	 * 
	 * @param result
	 * @return
	 */
	private String ajaxOutput(Object result) {
		String responseText = null;
		try {
			if (result instanceof JsonBean) {
				Gson gsonLib = new Gson();
				responseText = gsonLib.toJson(result);
			} else {
				responseText = String.valueOf(result);
			}
		} catch (Exception ex) {
			LOGGER.log(Level.WARNING, "Exception|MobileWebServlet|ajaxOutput() ", ex);
		}
		return responseText;
	}

}
