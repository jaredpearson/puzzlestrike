package com.jaredpearson.puzzlestrike.web;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that redirects to the location specified in the init-param.
 * @author jared.pearson
 */
public class RedirectServlet extends GenericServlet {
	private static final long serialVersionUID = -1177319694396229058L;
	private String location;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		location = config.getInitParameter("location");
		if(location == null || location.trim().length() == 0) {
			throw new ServletException("Location parameter must be specified");
		}
	}
	
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		String contextPath = ((HttpServletRequest)request).getContextPath();
		((HttpServletResponse)response).sendRedirect(contextPath + this.location);
	}

}
