package com.jaredpearson.puzzlestrike.states;

import com.jaredpearson.puzzlestrike.Game;

public class EndTurnState extends StateAdapter implements State {
	private State nextState;
	
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public void onEntry(Game game) {
		//go to the next person
		if(game.getCurrentPlayerIndex() + 1 >= game.getPlayerCount()) {
			game.setCurrentPlayerIndex(0);
		} else {
			game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() + 1);
		}
		
		game.setState(nextState);
	}
}