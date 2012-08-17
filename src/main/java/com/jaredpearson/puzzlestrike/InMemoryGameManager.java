package com.jaredpearson.puzzlestrike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryGameManager implements GameManager {
	private IdGenerator idGenerator = new IdGenerator();
	private List<Game> games = new ArrayList<Game>();
	
	/**
	 * {@inheritDoc}
	 */
	public Game getGameById(Integer gameId) {
		for(Game game : games) {
			if(game.getId() == gameId) {
				return game;
			}
		}
		
		throw new RuntimeException("Unknown game ID of " + gameId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Game createGame(Player owner) {
		Game game = new Game(idGenerator.getNextId(), owner);
		games.add(game);
		return game;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Game> getGames() {
		return Collections.unmodifiableList(games);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void insert(Game game) {
		if(game.getOwner() == null) {
			throw new RuntimeException();
		}
		
		game.setId(idGenerator.getNextId());
		games.add(game);
	}
	
	private static class IdGenerator {
		private int nextGameId = 0;
		
		public Integer getNextId() {
			return ++nextGameId;
		}
	}
}