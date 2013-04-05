package com.jaredpearson.puzzlestrike.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.GameManager;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerManager;
import com.jaredpearson.puzzlestrike.actions.Action;

public class GamePlayController extends AbstractGameController implements Controller {
	public GamePlayController(GameManager gameManager, PlayerManager playerManager) {
		super(gameManager, playerManager);
	}
	
	public View handle(HttpServletRequest request, HttpServletResponse response, String actionPath)
			throws Exception {
		Game game = getGame();
		Player player = getCurrentPlayer();
		GamePlayResponse gameResponse = executeAction(game, player, request.getParameter("action"));

		request.setAttribute("game", game);
		request.setAttribute("status", gameResponse.getStatus());
		request.setAttribute("actions", gameResponse.getActions());
		
		return new JspView("/game");
	}
	
	public GamePlayResponse executeAction(Game game, Player player, String selectedActionName) {
		
		//ensure the user is in the game
		if(!game.containsPlayer(player)) {
			throw new IllegalStateException("You are not part of this game");
		}
		
		//execute the action from the player
		if(selectedActionName != null) {
			Action selectedAction = null;
			
			//check the game to see if the action specified is available
			List<Action> previousActions = game.getActions(player);
			for(Action action : previousActions) {
				if(action.getName().equals(selectedActionName)) {
					selectedAction = action;
					break;
				}
			}
			
			if(selectedAction == null) {
				throw new IllegalStateException("Action specified is not valid");
			}
			
			//apply the action to the game
			game.applyAction(player, selectedAction);
		}
		
		return new GamePlayResponse(game, player);
	}
	
	private static class GamePlayResponse {
		private String status;
		private List<Action> actions;
		
		public GamePlayResponse(Game game, Player player) {
			this.status = game.getStatus(player);
			this.actions = game.getActions(player);
		}
		
		public String getStatus() {
			return status;
		}
		
		public List<Action> getActions() {
			return actions;
		}
	}
}
