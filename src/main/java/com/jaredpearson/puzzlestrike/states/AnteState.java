package com.jaredpearson.puzzlestrike.states;

import com.jaredpearson.puzzlestrike.Game;

public class AnteState extends StateAdapter implements State {
	private State nextState;
	
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public void onEntry(Game game) {
		//ante a gem to the gem pile
		game.getActivePlayerContext().addToGemPile(game.getChipPile().takeGem1());
		
		game.setState(nextState);
	}
}