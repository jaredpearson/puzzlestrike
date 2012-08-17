package com.jaredpearson.puzzlestrike.web.cometd;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.cometd.bayeux.server.BayeuxServer;

public class BayeuxInitializer extends GenericServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
        BayeuxServer bayeux = (BayeuxServer)getServletContext().getAttribute(BayeuxServer.ATTRIBUTE);
        
        //Add services
        new HelloService(bayeux);
        new GameService(bayeux);
    }

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        throw new ServletException();
    }
}
