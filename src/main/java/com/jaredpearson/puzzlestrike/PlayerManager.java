package com.jaredpearson.puzzlestrike;

public interface PlayerManager {

	/**
	 * Gets the player with the specified ID.
	 * @param playerId The ID of the owner
	 * @return
	 */
	public abstract Player getPlayerById(Integer playerId);

	/**
	 * Inserts the specified player
	 * @param player
	 */
	public abstract void insert(Player player);
}