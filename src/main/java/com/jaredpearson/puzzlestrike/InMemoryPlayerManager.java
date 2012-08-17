package com.jaredpearson.puzzlestrike;

import java.util.ArrayList;

public class InMemoryPlayerManager implements PlayerManager {
	private ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * {@inheritDoc}
	 */
	public Player getPlayerById(Integer playerId) {
		for(Player player : players) {
			if(player.getId() == playerId) {
				return player;
			}
		}
		return null;
	}
	
	public void insert(Player player) {
		//TODO ensure player is not null
		this.players.add(player);
		
		//TODO generate a new ID
		player.setId(players.size());
	}
}
