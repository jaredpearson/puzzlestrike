package com.jaredpearson.puzzlestrike;

import java.util.List;

public interface GameManager {

	public abstract Game getGameById(Integer gameId);

	public abstract Game createGame(Player owner);

	public abstract List<Game> getGames();

	public abstract void insert(Game game);
}