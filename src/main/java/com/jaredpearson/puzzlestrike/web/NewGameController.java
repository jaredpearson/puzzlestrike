package com.jaredpearson.puzzlestrike.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.GameManager;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerManager;

public class NewGameController implements Controller {
	private PlayerManager playerManager = null;
	private GameManager gameManager = null;
	
	public NewGameController(GameManager gameManager, PlayerManager playerManager) {
		this.gameManager = gameManager;
		this.playerManager = playerManager;
	}
	
	public View handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer ownerId = (Integer)request.getSession(true).getAttribute("playerId");
		if(ownerId == null) {
			throw new RuntimeException("No player logged in");
		}
		Player owner = playerManager.getPlayerById(ownerId);
		
		//create the game and add the owner as a player
		Game game = gameManager.createGame(owner);
		game.addPlayer(owner);
		
		//show the home page
		return new JspView("redirect:/home");
	}
}