package com.jaredpearson.puzzlestrike.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspView implements View {
	private String name;
	
	/**
	 * Represents a view with the specified name.
	 * <p>
	 * The name specified is the name mapped to a JSP file, using the default
	 * "/WEB-INF/views" + name + ".jsp" path.
	 * <p>
	 * If the name begins with "redirect:" then the response is a redirect to the 
	 * path specified after "redirect:"
	 */
	public JspView(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean redirect = this.getName() != null && this.getName().startsWith("redirect:");
		
		if(redirect) {
			String location = getName().substring("redirect:".length());
			if(location.startsWith("/")){
				location = request.getContextPath() + "/app" + location;
			}
			response.sendRedirect(location);
		} else {
			
			String viewPath = createViewPath(getName());
			
			RequestDispatcher rd = request.getRequestDispatcher(viewPath);
			rd.forward(request, response);
		}
	}
	
	private static String createViewPath(String viewName) {
		if(viewName.startsWith("/")) {
			viewName = viewName.substring(1);
		}
		return "/WEB-INF/views/" + viewName + ".jsp";
	}
}