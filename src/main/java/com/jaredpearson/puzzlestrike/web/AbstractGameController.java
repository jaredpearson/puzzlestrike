package com.jaredpearson.puzzlestrike.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.GameManager;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerManager;

public abstract class AbstractGameController implements Controller {
	private PlayerManager playerManager = null;
	private GameManager gameManager = null;
	private Game game;
	private Player currentPlayer;
	
	public AbstractGameController(GameManager gameManager, PlayerManager playerManager) {
		this.gameManager = gameManager;
		this.playerManager = playerManager;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Game getGame() {
		return game;
	}

	public View handle(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Matcher matcher = Pattern.compile("^/Game/([0-9]+)(/.*)?$").matcher(request.getPathInfo());
		if(!matcher.find()) {
			throw new IllegalStateException("Invalid URL format");
		}
		
		//get the current player
		Player player = WebUtils.getPlayer(playerManager, request);
		if(player == null) {
			throw new IllegalStateException("User is not logged in");
		}
		
		//get the requested game
		Integer gameId = Integer.parseInt(matcher.group(1), 10);
		Game game = gameManager.getGameById(gameId);
		
		this.game = game;
		this.currentPlayer = player;
		
		return handle(request, response, matcher.group(2));
	}
	
	public abstract View handle(HttpServletRequest request, HttpServletResponse response, String actionPath)
		throws Exception;
}