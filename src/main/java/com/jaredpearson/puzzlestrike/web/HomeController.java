package com.jaredpearson.puzzlestrike.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.GameManager;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerManager;

public class HomeController implements Controller {
	private GameManager gameManager = null;
	private PlayerManager playerManager = null;
	
	public HomeController(GameManager gameManager, PlayerManager playerManager) {
		this.gameManager = gameManager;
		this.playerManager = playerManager;
	}
	
	public View handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//allow the request to control which player they are
		if(request.getParameter("asPlayer") != null) {
			request.getSession(true).setAttribute("playerId", Integer.parseInt(request.getParameter("asPlayer")));
		}
		
		//get the current player information
		Player player = null;
		if(request.getSession(true).getAttribute("playerId") != null) {
			player = WebUtils.getPlayer(playerManager, request);
		}
		request.setAttribute("player", player);
		
		//get the list of games
		List<Game> games = gameManager.getGames();
		request.setAttribute("games", games);
		
		return new JspView("home");
	}
}