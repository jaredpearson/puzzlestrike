package com.jaredpearson.puzzlestrike.states;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

import com.jaredpearson.puzzlestrike.Chip;
import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerGameContext;
import com.jaredpearson.puzzlestrike.actions.Action;

public class PlayActionStateTest {
	/**
	 * When its the active player's turn, the active player should 
	 * get a list of actions that include to play chips out of their 
	 * hand.
	 */
	@Test
	public void testActivePlayerActionsIncludePlayChips() {
		Game game = mock(Game.class);
		
		Player activePlayer = mock(Player.class);
		when(game.isActivePlayer(activePlayer)).thenReturn(true);
		
		PlayerGameContext playerContext = mock(PlayerGameContext.class);
		when(game.getActivePlayerContext()).thenReturn(playerContext);
		
		ArrayList<Chip> chips = new ArrayList<Chip>();
		chips.add(mock(Chip.class));
		chips.add(mock(Chip.class));
		chips.add(mock(Chip.class));
		chips.add(mock(Chip.class));
		chips.add(mock(Chip.class));
		when(playerContext.getHand()).thenReturn(chips);
		
		PlayActionState state = new PlayActionState();
		List<Action> actions = state.getActions(game, activePlayer);
		
		Assert.assertNotNull("getActions should never be null", actions);
		Assert.assertEquals("Expected the set of actions to be five - one for each chip", 5, actions.size());
	}

	/**
	 * When its the active player's turn, the inactive player shouldn't
	 * be able to see the active player's actions
	 */
	@Test
	public void testInactivePlayerShouldNotSeeActivePlayerActions() {
		Game game = mock(Game.class);
		
		Player inactivePlayer = mock(Player.class);
		when(game.isActivePlayer(inactivePlayer)).thenReturn(false);
		
		PlayActionState state = new PlayActionState();
		List<Action> actions = state.getActions(game, inactivePlayer);
		
		Assert.assertNotNull("getActions should never be null", actions);
		Assert.assertEquals("Expected the set of actions to be empty", 0, actions.size());
	}

}
