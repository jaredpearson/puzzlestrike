package com.jaredpearson.puzzlestrike.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.GameCharacter;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerGameContext;
import com.jaredpearson.puzzlestrike.actions.Action;
import com.jaredpearson.puzzlestrike.actions.ChooseCharacterAction;

public class CharacterSelectState extends StateAdapter implements State {
	private List<GameCharacter> allCharacters = null;
	private State nextState;
	
	public CharacterSelectState() {
		allCharacters = new ArrayList<GameCharacter>(3);
		allCharacters.add(GameCharacter.withName("Grave"));
		allCharacters.add(GameCharacter.withName("Midori"));
		allCharacters.add(GameCharacter.withName("Setsuki"));
	}
	
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	@Override
	public List<Action> getActions(Game game, Player player) {
		if(!game.isActivePlayer(player)) {
			return Collections.emptyList();
		}
		
		Set<GameCharacter> takenCharacters = getTakenCharacters(game);
		
		List<Action> actions = new ArrayList<Action>();
		for(GameCharacter character : allCharacters) {
			if(takenCharacters.contains(character)) {
				continue;
			}
			
			actions.add(new ChooseCharacterAction(character));
		}
		return actions;
	}
	
	@Override
	public void handle(Game game, Player player, Action action) {
		ChooseCharacterAction characterAction = ((ChooseCharacterAction)action);
		GameCharacter selectedCharacter = characterAction.getCharacter();
		
		//make sure that the character is not already taken
		if(isTaken(game, selectedCharacter)) {
			throw new IllegalStateException("Character has already been selected.");
		}
		
		game.selectCharacter(player, selectedCharacter);
		
		//ask the next player which character they want
		//if no more players, then move to the next state
		if(game.getCurrentPlayerIndex() < game.getPlayerCount() - 1) {
			game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() + 1);
		} else {
			game.setCurrentPlayerIndex(0);
			game.setState(nextState);
		}
	}
	
	/**
	 * Gets the characters that have already been chosen in the game.
	 */
	private Set<GameCharacter> getTakenCharacters(Game game) {
		HashSet<GameCharacter> takenCharacters = new HashSet<GameCharacter>(game.getPlayerCount());
		for(PlayerGameContext gamePlayer : game.getPlayers()) {
			if(gamePlayer.getCharacter() != null) {
				takenCharacters.add(gamePlayer.getCharacter());
			}
		}
		return takenCharacters;
	}
	
	/**
	 * Determines if the specified character is already taken.
	 */
	private boolean isTaken(Game game, GameCharacter character) {
		return getTakenCharacters(game).contains(character);
	}
}