package com.jaredpearson.puzzlestrike.states;

import java.util.ArrayList;
import java.util.List;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.actions.Action;
import com.jaredpearson.puzzlestrike.actions.JoinGameAction;

/**
 * State that the game is in before all players have joined.
 * @author jared.pearson
 */
public class PreGameState extends StateAdapter implements State {
	private State nextState;
	
	public void setNextState(State state) {
		this.nextState = state;
	}
	
	@Override
	public List<Action> getActions(Game game, Player player) {
		List<Action> actions = new ArrayList<Action>(1);
		actions.add(new JoinGameAction());
		return actions;
	}
	
	@Override
	public void handle(Game game, Player player, Action action) {
		if(action instanceof JoinGameAction) {
			game.addPlayer(player);
			
			//go ahead and start the game if it is full
			if(game.isFull()) {
				game.start();
				game.setState(nextState); 
			}
		}
	}
}