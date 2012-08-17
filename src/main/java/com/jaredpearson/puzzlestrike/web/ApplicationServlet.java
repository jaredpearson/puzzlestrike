package com.jaredpearson.puzzlestrike.web;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jaredpearson.puzzlestrike.PlayerManager;

public class ApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//get the handler associated to the request
		Controller controller = getController(request);
		
		//execute the handler to get the view
		View view = null;
		try {
			view = controller.handle(request, response);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
		
		//forward the user to the view
		if(view == null) {
			throw new RuntimeException("Unknown view");
		} else {
			try {
				view.show(request, response);
			} catch (Exception exc) {
				throw new ServletException(exc);
			}
		}
	}
	
	/**
	 * Gets the controller for the specified request.
	 */
	private Controller getController(HttpServletRequest request) {
		ApplicationContext app = (ApplicationContext)getServletContext().getAttribute(ApplicationContext.ATTRIBUTE);
		String pathInfo = (request.getPathInfo() != null)? request.getPathInfo() : "";
		Controller homeHandler = new HomeController(app.getGameManager(), app.getPlayerManager());
		
		if(pathInfo.equals("/Game/new")) {
			return new NewGameController(app.getGameManager(), app.getPlayerManager());
		} else if (Pattern.matches("^/Game/[0-9]+$", pathInfo)) {
			return new GamePlayController(app.getGameManager(), app.getPlayerManager());
		} else if (Pattern.matches("^/Game/[0-9]+/join$", pathInfo)) {
			return new GameJoinController(app.getGameManager(), app.getPlayerManager());
		} else if (pathInfo.equals("/Player/new")) {
			PlayerManager playerManager = app.getPlayerManager();
			return new NewPlayerController(playerManager);
		} else if (pathInfo.equals("/home")){
			return homeHandler;
		} else {
			return homeHandler;
		}
	}
	
}
