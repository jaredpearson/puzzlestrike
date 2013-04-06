package com.jaredpearson.puzzlestrike.states;

import com.jaredpearson.puzzlestrike.ChipPile;
import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.PlayerGameContext;

/**
 * State to setup the player's bag state
 * @author jared.pearson
 */
public class SetupPlayerBagState extends StateAdapter implements State {
	private State nextState;
	
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public void onEntry(Game game) {
		ChipPile pile = game.getChipPile();
		
		//create each player's bag
		for(PlayerGameContext playerContext : game.getPlayers()) {
			playerContext.addToBag(playerContext.getCharacter().getChips());
			playerContext.addToBag(pile.takeGem1());
			playerContext.addToBag(pile.takeGem1());
			playerContext.addToBag(pile.takeGem1());
			playerContext.addToBag(pile.takeGem1());
			playerContext.addToBag(pile.takeGem1());
			playerContext.addToBag(pile.takeGem1());
			playerContext.addToBag(pile.takeCrashGem());
			
			//draw their first hand
			playerContext.drawHandFromBag();
		}
		
		game.setState(nextState);
	}
}