package com.jaredpearson.puzzlestrike.states;

import java.util.List;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.actions.Action;

public interface State {
	
	/**
	 * Gets the list of actions the specified player can perform
	 * on the game.
	 */
	public List<Action> getActions(Game game, Player player);
	
	/**
	 * Executes the specified action within the game
	 */
	public void handle(Game game, Player player, Action action);

	/**
	 * Executed when the state is first entered.
	 */
	public void onEntry(Game game);
}