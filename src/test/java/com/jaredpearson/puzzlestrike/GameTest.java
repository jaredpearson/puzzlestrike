package com.jaredpearson.puzzlestrike;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {
	@Test
	public void testGameStart() {
		Game game = new Game();
		
		Player player1 = createMockPlayer();
		game.addPlayer(player1);
		
		Player player2 = createMockPlayer();
		game.addPlayer(player2);
		
		game.start();
		
		Assert.assertNotNull("Expected to be at an active state after the game starts", game.getActiveState());
		Assert.assertNotNull("Expected to have an active player after the game starts", game.getActivePlayerContext());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddPlayerFailOnStartedGame(){
		Game game = new Game();
		
		Player player1 = createMockPlayer();
		game.addPlayer(player1);
		
		Player player2 = createMockPlayer();
		game.addPlayer(player2);
		
		game.start();
		
		Player player3 = createMockPlayer();
		game.addPlayer(player3); //expect to throw IllegalStateException when the game is started
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddPlayerFailOnFullGame(){
		Game game = new Game(); //expect max player count to be 2
		
		Player player1 = createMockPlayer();
		game.addPlayer(player1);
		
		Player player2 = createMockPlayer();
		game.addPlayer(player2);
		
		Player player3 = createMockPlayer();
		game.addPlayer(player3); //expect to throw IllegalStateException when the game is full
	}
	
	private static Player createMockPlayer() {
		return new Player();
	}
}
