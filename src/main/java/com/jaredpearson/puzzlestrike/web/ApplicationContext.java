package com.jaredpearson.puzzlestrike.web;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.cometd.bayeux.server.BayeuxServer;

import com.jaredpearson.puzzlestrike.Game;
import com.jaredpearson.puzzlestrike.GameManager;
import com.jaredpearson.puzzlestrike.InMemoryGameManager;
import com.jaredpearson.puzzlestrike.InMemoryPlayerManager;
import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerManager;

public class ApplicationContext {
	public static String ATTRIBUTE = "com.jaredpearson.puzzlestrike.web.ApplicationContext";
	
	private PlayerManager playerManager = null;
	private GameManager gameManager = null;
	
	public ApplicationContext(Provider<BayeuxServer> bayeuxServerProvider) {
		this.playerManager = new InMemoryPlayerManager();
		
		//create the game manager
		GameManager memoryGameManager = new InMemoryGameManager();
		BayeuxGameManagerWrapper bayeuxGameManager = new BayeuxGameManagerWrapper(memoryGameManager, bayeuxServerProvider);
		this.gameManager = bayeuxGameManager;
	}
	
	
	public GameManager getGameManager() {
		return gameManager;
	}
	
	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public static interface Provider<T> {
		public T get();
	}
	
	private static class BayeuxGameManagerWrapper implements GameManager {
		private Provider<BayeuxServer> bayeuxServerProvider = null;
		private GameManager gameManager = null;
		
		public BayeuxGameManagerWrapper(GameManager gameManager, Provider<BayeuxServer> bayeuxServerProvider) {
			this.gameManager = gameManager;
			this.bayeuxServerProvider = bayeuxServerProvider;
		}
		
		public Game createGame(Player owner) {
			Game newGame = gameManager.createGame(owner);
			
			notifyOfNewGame(newGame);
			
			return newGame;
		}

		public Game getGameById(Integer gameId) {
			return gameManager.getGameById(gameId);
		}

		public List<Game> getGames() {
			return gameManager.getGames();
		}
		
		private void notifyOfNewGame(Game newGame) {
			BayeuxServer bayeux = bayeuxServerProvider.get();
			
			//create a map of the game properties to be sent back
			Map<String, Object> ownerMap = new Hashtable<String, Object>();
			ownerMap.put("id", newGame.getOwner().getId());
			ownerMap.put("name", newGame.getOwner().getName());
			
			Map<String, Object> gameMap = new Hashtable<String, Object>();
			gameMap.put("id", newGame.getId());
			gameMap.put("owner", ownerMap);
			
			bayeux.createIfAbsent("/newGame");
			bayeux.getChannel("/newGame").publish(null, gameMap, null);
		}

		public void insert(Game game) {
			gameManager.insert(game);
		}
	}
}
