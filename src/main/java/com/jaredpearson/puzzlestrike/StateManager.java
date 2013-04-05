package com.jaredpearson.puzzlestrike;

import com.jaredpearson.puzzlestrike.actions.Action;
import com.jaredpearson.puzzlestrike.states.AnteState;
import com.jaredpearson.puzzlestrike.states.CharacterSelectState;
import com.jaredpearson.puzzlestrike.states.CleanupState;
import com.jaredpearson.puzzlestrike.states.EndTurnState;
import com.jaredpearson.puzzlestrike.states.PlayActionState;
import com.jaredpearson.puzzlestrike.states.PreGameState;
import com.jaredpearson.puzzlestrike.states.SetupPlayerBagState;
import com.jaredpearson.puzzlestrike.states.State;

public class StateManager {
	private Game game;
	private State currentState;
	
	public StateManager(Game game) {
		this.game = game;
		
		State startState = initializeState();
		this.setState(startState);
	}
	
	public State getCurrentState() {
		return this.currentState;
	}
	
	public void handle(Player player, Action action) {
		this.getCurrentState().handle(game, player, action);
	}

	public void setState(State state) {
		this.currentState = state;
		
		//execute the state's entry method
		state.onEntry(this.game);
	}
	
	private static State initializeState() {
		//create the states that the game will be in
		PreGameState preGameStateDefinition = new PreGameState();
		
		CharacterSelectState characterSelectStateDefinition = new CharacterSelectState();
		preGameStateDefinition.setNextState(characterSelectStateDefinition);
		
		SetupPlayerBagState setupPlayerBagStateDefinition = new SetupPlayerBagState();
		characterSelectStateDefinition.setNextState(setupPlayerBagStateDefinition);
		
		AnteState anteStateDefinition = new AnteState();
		setupPlayerBagStateDefinition.setNextState(anteStateDefinition);
		
		PlayActionState playActionStateDefinition = new PlayActionState();
		anteStateDefinition.setNextState(playActionStateDefinition);
		
		CleanupState cleanupStateDefinition = new CleanupState();
		playActionStateDefinition.setNextState(cleanupStateDefinition);
		
		EndTurnState endTurnStateDefinition = new EndTurnState();
		endTurnStateDefinition.setNextState(anteStateDefinition);
		cleanupStateDefinition.setNextState(endTurnStateDefinition);
		
		return preGameStateDefinition;
	}
}
