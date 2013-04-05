package com.jaredpearson.puzzlestrike.states;

import org.junit.Test;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.GameCharacter;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.actions.ChooseCharacterAction;

public class CharacterSelectStateTest {
	
	/**
	 * Guarantee that a player cannot select a character that has already been selected.
	 */
	@Test(expected=IllegalStateException.class)
	public void testCharacterCannotBeSelectedTwice() {
		Game game = new Game();
		
		Player player1 = createMockPlayer();
		game.setOwner(player1);
		game.addPlayer(player1);
		
		Player player2 = createMockPlayer();
		game.addPlayer(player2);
		
		game.start();

		//change the state to be the character selection state
		CharacterSelectState state = new CharacterSelectState();
		state.setNextState(createMockState());
		game.setState(state);
		
		//allow the first player to select the character
		game.applyAction(player1, new ChooseCharacterAction(GameCharacter.withName("Midori")));
		
		//allow the second player to select the same character
		//we expect this step to throw an IllegalStateException
		game.applyAction(player2, new ChooseCharacterAction(GameCharacter.withName("Midori")));
	}
	
	private static Player createMockPlayer() {
		return new Player();
	}
	
	private static State createMockState() {
		return new StateAdapter() {};
	}
}
