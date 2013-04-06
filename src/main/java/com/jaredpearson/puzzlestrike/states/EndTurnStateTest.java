package com.jaredpearson.puzzlestrike.states;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Test;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerGameContext;

public class EndTurnStateTest {

	@Test
	public void testEndTurnStateGoesToNextStateWhenNoWinner() {
		Game game = mock(Game.class);
		Player player = mock(Player.class);
		PlayerGameContext activePlayerGameContext = mock(PlayerGameContext.class);
		when(game.getActivePlayerContext()).thenReturn(activePlayerGameContext);
		
		State nextState = mock(State.class);
		
		EndTurnState endTurnState = new EndTurnState();
		endTurnState.setNextState(nextState);
		
		endTurnState.onEntry(game);
		endTurnState.handle(game, player, null);
		
		verify(game, never()).setWinner(any(Player.class));
		verify(game).setState(nextState);
		verify(game).goToNextPlayer();
	}

	@Test
	public void testEndTurnStateGoesToWinner() {
		Game game = mock(Game.class);
		Player activePlayer = mock(Player.class);
		PlayerGameContext activePlayerGameContext = mock(PlayerGameContext.class);
		when(game.getActivePlayerContext()).thenReturn(activePlayerGameContext);
		when(activePlayerGameContext.getGemPileValue()).thenReturn(10);
		
		Player otherPlayer = mock(Player.class);
		PlayerGameContext otherPlayerGameContext = mock(PlayerGameContext.class);
		when(otherPlayerGameContext.getPlayer()).thenReturn(otherPlayer);
		ArrayList<PlayerGameContext> playerContexts = new ArrayList<PlayerGameContext>();
		playerContexts.add(activePlayerGameContext);
		playerContexts.add(otherPlayerGameContext);
		when(game.getPlayers()).thenReturn(playerContexts);
		
		State endGameState = mock(State.class);
		
		EndTurnState endTurnState = new EndTurnState();
		endTurnState.setEndGameState(endGameState);
		
		endTurnState.onEntry(game);
		endTurnState.handle(game, activePlayer, null);
		
		verify(game).setWinner(otherPlayer);
		verify(game).setState(eq(endGameState));
	}
}
