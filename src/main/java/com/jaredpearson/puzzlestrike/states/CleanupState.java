package com.jaredpearson.puzzlestrike.states;

import com.jaredpearson.puzzlestrike.Game;

public class CleanupState extends StateAdapter implements State {
	private State nextState;
	
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public void onEntry(Game game) {
		
		//discard the player's hand
		game.getActivePlayerContext().discardHand();
		
		//draw a new hand
		game.getActivePlayerContext().drawHandFromBag();
		
		game.setState(nextState);
	}
}