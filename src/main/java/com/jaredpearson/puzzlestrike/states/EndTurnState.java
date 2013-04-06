package com.jaredpearson.puzzlestrike.states;

import java.util.ArrayList;
import java.util.List;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.PlayerGameContext;

/**
 * End of the turn step, which switches to the next player
 * @author jared.pearson
 */
public class EndTurnState extends StateAdapter implements State {
	private State nextState;
	private State endGameState;
	
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public void setEndGameState(State endGameState) {
		this.endGameState = endGameState;
	}
	
	public void onEntry(Game game) {
		
		//if the current player hits the max number in the their gem pile
		//then the player loses and the game is over
		if(game.getActivePlayerContext().getGemPileValue() >= 10) {
			
			//select the players with the lowest gem pile value
			List<PlayerGameContext> potentialWinners = getPlayersWithLowestGemPileValue(game);
			
			//TODO potentialWinners need to take turns until there is a winner
			//if(potentialWinners.size() == 0)
			
			game.setWinner(potentialWinners.get(0).getPlayer());
			
			game.setState(endGameState);
			
		} else {
			game.goToNextPlayer();
			game.setState(nextState);
		}
	}
	
	private List<PlayerGameContext> getPlayersWithLowestGemPileValue(Game game) {
		List<PlayerGameContext> lowestPlayers = new ArrayList<PlayerGameContext>();
		
		int lowestGemPileValue = Integer.MAX_VALUE;
		for(PlayerGameContext playerContext : game.getPlayers()) {
			if(playerContext.getGemPileValue() < lowestGemPileValue) {
				lowestPlayers.clear();
				lowestPlayers.add(playerContext);
				
			} else if(playerContext.getGemPileValue() == lowestGemPileValue) {
				lowestPlayers.add(playerContext);
				
			}
		}
		
		return lowestPlayers;
	}
}