package com.jaredpearson.puzzlestrike.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.cometd.bayeux.server.BayeuxServer;

public class ApplicationContextFilter implements Filter{
	
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext context = filterConfig.getServletContext();

		context.setAttribute(ApplicationContext.ATTRIBUTE, new ApplicationContext(new BayeuxServerProvider(context)));
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}

	public class BayeuxServerProvider implements ApplicationContext.Provider<BayeuxServer> {
		private ServletContext context;
		
		public BayeuxServerProvider(ServletContext context) {
			this.context = context;
		}
		
		public BayeuxServer get() {
			return (BayeuxServer)context.getAttribute(BayeuxServer.ATTRIBUTE);
		}
	}
}
