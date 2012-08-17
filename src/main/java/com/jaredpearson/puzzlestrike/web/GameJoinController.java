package com.jaredpearson.puzzlestrike.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.GameManager;
import com.jaredpearson.puzzlestrike.PlayerManager;

public class GameJoinController extends AbstractGameController implements Controller {
	
	public GameJoinController(GameManager gameManager, PlayerManager playerManager) {
		super(gameManager, playerManager);
	}
	
	public View handle(HttpServletRequest request, HttpServletResponse response, String actionPath)
			throws Exception {
		Game game = getGame();
		if(!game.isOpen()) {
			throw new IllegalStateException("Game is currently not open.");
		}
		
		game.addPlayer(getCurrentPlayer());
		
		//go ahead and start the game if it is full
		if(game.isFull()) {
			game.start();
		}
		
		return new JspView("redirect:/Game/" + getGame().getId());
	}

}
