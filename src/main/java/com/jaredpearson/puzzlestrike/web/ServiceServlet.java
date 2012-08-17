package com.jaredpearson.puzzlestrike.web;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jaredpearson.puzzlestrike.Game;

public class ServiceServlet extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String objectName = httpRequest.getPathInfo().substring(1);
		if(objectName.indexOf("/") > -1) {
			objectName = objectName.substring(0, objectName.indexOf("/"));
		}
		
		if(objectName.equals("Game")) {
			serviceGame(httpRequest, httpResponse);
		} else {
			httpResponse.sendError(404);
		}
	}
	
	public void destroy() {
	}
	
	public void serviceGame(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		ApplicationContext app = (ApplicationContext)getServletContext().getAttribute(ApplicationContext.ATTRIBUTE);
		
		String method = request.getMethod();
		if(method.equals("POST")) {
			Gson gson = new Gson();
			GameCreationRequest gameRequest = gson.fromJson(request.getReader(), GameCreationRequest.class);
			
			Game game = new Game();
			game.setOwner(app.getPlayerManager().getPlayerById(gameRequest.owner));
			app.getGameManager().insert(game);
			
			Map<String, Object> returnObject = new Hashtable<String, Object>();
			returnObject.put("id", game.getId());
			returnObject.put("success", true);
			gson.toJson(returnObject, response.getWriter());
		} else {
			response.sendError(500, "Unsupported method");
		}
	}
	
	private static class GameCreationRequest {
		private Integer owner;
	}
}
