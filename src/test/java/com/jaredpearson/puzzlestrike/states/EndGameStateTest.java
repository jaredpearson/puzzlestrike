package com.jaredpearson.puzzlestrike.states;

import static org.mockito.Mockito.*;

import org.junit.Test;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.Player;

public class EndGameStateTest {

	@Test
	public void testSimpleHandle() {
		Game game = mock(Game.class);
		Player player = mock(Player.class);
		
		EndGameState endGameState = new EndGameState();
		
		endGameState.onEntry(game);
		endGameState.handle(game, player, null);
	}
	
}
