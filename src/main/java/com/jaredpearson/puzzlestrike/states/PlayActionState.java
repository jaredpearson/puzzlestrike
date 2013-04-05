package com.jaredpearson.puzzlestrike.states;

import java.util.ArrayList;
import java.util.List;

import com.jaredpearson.puzzlestrike.Chip;
import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.actions.Action;

public class PlayActionState extends StateAdapter implements State {
	private State nextState;
	
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public List<Action> getActions(Game game, Player player) {
		
		ArrayList<Action> actions = new ArrayList<Action>();
		
		for(Chip chip : game.getActivePlayerContext().getHand()) {
			actions.add(new GenericAction("Play " + chip.getName()));
		}
		
		return actions;
	}
	
	public void handle(Game game, Player player, Action action) {
		game.setState(nextState);
	}
	
	public static class GenericAction implements Action {
		private String name;
		
		public GenericAction(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}