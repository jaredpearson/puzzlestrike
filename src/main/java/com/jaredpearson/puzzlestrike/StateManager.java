package com.jaredpearson.puzzlestrike;

import com.jaredpearson.puzzlestrike.actions.Action;
import com.jaredpearson.puzzlestrike.states.AnteState;
import com.jaredpearson.puzzlestrike.states.CharacterSelectState;
import com.jaredpearson.puzzlestrike.states.CleanupState;
import com.jaredpearson.puzzlestrike.states.EndGameState;
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
		//1. pre-game state
		PreGameState preGameStateDefinition = new PreGameState();
		
		//2. character selection state
		CharacterSelectState characterSelectStateDefinition = new CharacterSelectState();
		preGameStateDefinition.setNextState(characterSelectStateDefinition);
		
		//3. setup player bag state
		SetupPlayerBagState setupPlayerBagStateDefinition = new SetupPlayerBagState();
		characterSelectStateDefinition.setNextState(setupPlayerBagStateDefinition);
		
		// turn loop start
		
		//4. ante
		AnteState anteStateDefinition = new AnteState();
		setupPlayerBagStateDefinition.setNextState(anteStateDefinition);
		
		//5. play action
		PlayActionState playActionStateDefinition = new PlayActionState();
		anteStateDefinition.setNextState(playActionStateDefinition);
		
		//6. cleanup
		CleanupState cleanupStateDefinition = new CleanupState();
		playActionStateDefinition.setNextState(cleanupStateDefinition);
		
		//7. end turn state
		EndTurnState endTurnStateDefinition = new EndTurnState();
		endTurnStateDefinition.setNextState(anteStateDefinition);
		cleanupStateDefinition.setNextState(endTurnStateDefinition);
		
		//turn loop end
		
		//8. end game state
		EndGameState endGameStateDefinition = new EndGameState();
		endTurnStateDefinition.setEndGameState(endGameStateDefinition);
		
		return preGameStateDefinition;
	}
}
