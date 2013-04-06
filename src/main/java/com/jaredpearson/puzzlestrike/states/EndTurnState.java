package com.jaredpearson.puzzlestrike.states;

import com.jaredpearson.puzzlestrike.Game;

/**
 * End of the turn step, which switches to the next player
 * @author jared.pearson
 */
public class EndTurnState extends StateAdapter implements State {
	private State nextState;
	
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public void onEntry(Game game) {
		game.goToNextPlayer();
		game.setState(nextState);
	}
}