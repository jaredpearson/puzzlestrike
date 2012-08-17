package com.jaredpearson.puzzlestrike;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private Integer id;
	private Player owner;
	private boolean started;
	private int maxPlayerCount = 2;
	private List<PlayerGameContext> players = new ArrayList<PlayerGameContext>();
	
	private Integer currentPlayerIndex;
	
	private StateDefinition currentState;
	
	public Game() {
	}
	
	public Game(Integer id, Player owner) {
		this.id = id;
		this.owner = owner;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public boolean isOwner(Player player) {
		return this.owner.equals(player);
	}
	
	/**
	 * Returns the maximum number of players allowed in the game.
	 */
	public int getMaxPlayerCount() {
		return maxPlayerCount;
	}
	
	public boolean isFull() {
		return this.players.size() == maxPlayerCount;
	}
	
	public boolean isOpen() {
		return !isFull() && !this.isStarted();
	}
	
	public boolean isStarted() {
		return this.started;
	}
	
	public void addPlayer(Player player) {
		if(isFull()) {
			throw new IllegalStateException("Game is full");
		}
		if(isStarted()) {
			throw new IllegalStateException("Game has already started");
		}
		
		if(player != null) {
			this.players.add(new PlayerGameContext(player));
		}
	}
	
	public int getPlayerCount() {
		return this.players.size();
	}
	
	public boolean isActivePlayer(Player player) {
		return this.isStarted() && getActivePlayerContext().getPlayer().equals(player);
	}
	
	/**
	 * Determines if the specified player is in the game.
	 */
	public boolean containsPlayer(Player player) {
		return this.players.contains(player);
	}
	
	public Player getActivePlayer() {
		return getActivePlayerContext().getPlayer();
	}
	
	public PlayerGameContext getActivePlayerContext() {
		if(!this.isStarted()) {
			throw new IllegalStateException("Game has not been started so no active player has been selected.");
		}
		return this.players.get(currentPlayerIndex);
	}
	
	public Integer getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}
	
	public void setCurrentPlayerIndex(Integer value) {
		this.currentPlayerIndex = value;
	}
	
	public void selectCharacter(Player player, GameCharacter character) {
		getActivePlayerContext().setCharacter(character);
	}
	
	public List<Action> getActions(Player player) {
		if(isActivePlayer(player)) {
			return this.currentState.getActions(this);
		}
		return new ArrayList<Action>(0);
	}
	
	public String getStatus(Player player) {
		if(!isStarted() && this.isOwner(player) && this.isFull()) {
			return "Waiting for another player to join";
		}
		
		if(isActivePlayer(player)) {
			return "";
		} else {
			return "Waiting for " + this.players.get(currentPlayerIndex).getPlayer().getName();
		}
	}
	
	public void start() {
		//TODO: randomly select the first player
		this.currentPlayerIndex = 0;
		this.started = true;

		//create the states that the game will be in
		CharacterSelectStateDefinition characterSelectStateDefinition = new CharacterSelectStateDefinition();
		
		SetupPlayerBagStateDefinition setupPlayerBagStateDefinition = new SetupPlayerBagStateDefinition();
		characterSelectStateDefinition.setNextState(setupPlayerBagStateDefinition);
		
		AnteStateDefinition anteStateDefinition = new AnteStateDefinition();
		setupPlayerBagStateDefinition.setNextState(anteStateDefinition);
		
		PlayActionStateDefinition playActionStateDefinition = new PlayActionStateDefinition();
		anteStateDefinition.setNextState(playActionStateDefinition);
		
		CleanupStateDefinition cleanupStateDefinition = new CleanupStateDefinition();
		playActionStateDefinition.setNextState(cleanupStateDefinition);
		
		EndTurnStateDefinition endTurnStateDefinition = new EndTurnStateDefinition();
		endTurnStateDefinition.setNextState(anteStateDefinition);
		cleanupStateDefinition.setNextState(endTurnStateDefinition);
		
		//the character selection is the starting state
		this.setState(characterSelectStateDefinition);
	}
	
	private void setState(StateDefinition state) {
		this.currentState = state;
		
		//if the state does not have actions, then we need to just handle the state
		if(state.getActions(this).isEmpty()) {
			state.handle(this, (Action)null);
		}
	}
	
	public StateDefinition getActiveState() {
		return currentState;
	}
	
	public void applyAction(Action action) {
		this.currentState.handle(this, action);
	}
	
	private interface Applyable<T> {
		public void apply(T v);
	}
	
	public static class GenericAction implements Action {
		private String name;
		
		public GenericAction(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	public static class ChooseCharacterAction implements Action, Applyable<Game> {
		private Player owner;
		private GameCharacter character;
		
		public ChooseCharacterAction(Player owner, GameCharacter character) {
			this.owner = owner;
			this.character = character;
		}
		
		public String getName() {
			return "Choose Character: " + character.getName();
		}
		
		public void apply(Game game) {
			game.selectCharacter(owner, character);
		}
	}
	
	private static class CharacterSelectStateDefinition implements StateDefinition {
		private StateDefinition nextState;
		
		public void setNextState(StateDefinition nextState) {
			this.nextState = nextState;
		}
		
		public List<Action> getActions(Game game) {
			Player owner = game.getActivePlayer();
			
			List<Action> actions = new ArrayList<Action>();
			actions.add(new ChooseCharacterAction(owner, new GameCharacter("Grave")));
			actions.add(new ChooseCharacterAction(owner, new GameCharacter("Midori")));
			actions.add(new ChooseCharacterAction(owner, new GameCharacter("Setsuki")));
			return actions;
		}
		
		public void handle(Game game, Action action) {
			((ChooseCharacterAction)action).apply(game);
			
			if(game.getCurrentPlayerIndex() < game.getPlayerCount()) {
				game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() + 1);
			} else {
				game.setCurrentPlayerIndex(0);
				game.setState(nextState);
			}
		}
	}
	
	private static class SetupPlayerBagStateDefinition implements StateDefinition {
		private StateDefinition nextState;
		
		public void setNextState(StateDefinition nextState) {
			this.nextState = nextState;
		}
		
		public List<Action> getActions(Game game) {
			return null;
		}
		public void handle(Game game, Action action) {
			
			//create each player's bag
			for(PlayerGameContext playerContext : game.players) {
				if(playerContext.getCharacter().getName().equals("Grave")) {
					playerContext.bag.add(new GenericChip("Reversal"));
					playerContext.bag.add(new GenericChip("Martial Mastery"));
					playerContext.bag.add(new GenericChip("Versatile Style"));
					
				} else if(playerContext.getCharacter().getName().equals("Midori")) {
					playerContext.bag.add(new GenericChip("Dragon Form"));
					playerContext.bag.add(new GenericChip("Rigorous Training"));
					playerContext.bag.add(new GenericChip("Purge Bad Habits"));
					
				} else if(playerContext.getCharacter().getName().equals("Setsuki")) {
					playerContext.bag.add(new GenericChip("Speed of the Fox"));
					playerContext.bag.add(new GenericChip("Bag of Tricks"));
					playerContext.bag.add(new GenericChip("Double-take"));
					
				} 
				
				playerContext.bag.add(new GenericChip("1 Gem"));
				playerContext.bag.add(new GenericChip("1 Gem"));
				playerContext.bag.add(new GenericChip("1 Gem"));
				playerContext.bag.add(new GenericChip("1 Gem"));
				playerContext.bag.add(new GenericChip("1 Gem"));
				playerContext.bag.add(new GenericChip("1 Gem"));
				playerContext.bag.add(new GenericChip("Crash Gem"));
				
				//draw their first hand
				playerContext.drawHandFromBag();
			}
			
			game.setState(nextState);
		}
	}
	
	private static class AnteStateDefinition implements StateDefinition {
		private StateDefinition nextState;
		
		public void setNextState(StateDefinition nextState) {
			this.nextState = nextState;
		}
		
		public List<Action> getActions(Game game) {
			return null;
		}
		
		public void handle(Game game, Action action) {
			//ante a gem to the gem pile
			game.getActivePlayerContext().addToGemPile(new GenericChip("1 Gem"));
			
			game.setState(nextState);
		}
	}
	
	private static class PlayActionStateDefinition implements StateDefinition {
		private StateDefinition nextState;
		
		public void setNextState(StateDefinition nextState) {
			this.nextState = nextState;
		}
		
		public List<Action> getActions(Game game) {
			
			ArrayList<Action> actions = new ArrayList<Action>();
			
			for(Chip chip : game.getActivePlayerContext().getHand()) {
				actions.add(new GenericAction("Play " + chip.getName()));
			}
			
			return actions;
		}
		
		public void handle(Game game, Action action) {
			game.setState(nextState);
		}
	}
	
	private static class CleanupStateDefinition implements StateDefinition {
		private StateDefinition nextState;
		
		public List<Action> getActions(Game game) {
			return null;
		}
		
		public void setNextState(StateDefinition nextState) {
			this.nextState = nextState;
		}
		
		public void handle(Game game, Action action) {
			
			//discard the player's hand
			game.getActivePlayerContext().discardHand();
			
			//draw a new hand
			game.getActivePlayerContext().drawHandFromBag();
			
			game.setState(nextState);
		}
	}
	
	private static class EndTurnStateDefinition implements StateDefinition {
		private StateDefinition nextState;
		
		public List<Action> getActions(Game game) {
			return null;
		}
		
		public void setNextState(StateDefinition nextState) {
			this.nextState = nextState;
		}
		
		public void handle(Game game, Action action) {
			//go to the next person
			if(game.getCurrentPlayerIndex() + 1 >= game.getPlayerCount()) {
				game.setCurrentPlayerIndex(0);
			} else {
				game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() + 1);
			}
			
			game.setState(nextState);
		}
	}
	
	private static class PlayerGameContext {
		private Player player;
		private GameCharacter character;
		private List<Chip> gemPile = new ArrayList<Chip>();
		private List<Chip> bag = new ArrayList<Chip>();
		private List<Chip> hand = new ArrayList<Chip>();
		private List<Chip> discard = new ArrayList<Chip>();
		
		public PlayerGameContext(Player player) {
			this.player = player;
		}
		
		public Player getPlayer() {
			return player;
		}
		
		public GameCharacter getCharacter() {
			return this.character;
		}
		
		public void setCharacter(GameCharacter character) {
			this.character = character;
		}
		
		public List<Chip> getHand() {
			return hand;
		}
		
		public void drawHandFromBag() {
			int handSize = getHandSize();
			
			this.hand = bag.subList(0, (bag.size() < handSize)? bag.size() : handSize);
		}
		
		public void addToGemPile(Chip chip) {
			this.gemPile.add(chip);
		}
		
		public void discardHand() {
			this.discard.addAll(this.hand);
			this.hand.clear();
		}
		
		private int getHandSize() {
			return 5;
		}
	}
	
	private static interface Chip {
		public String getName();
	}
	
	private static class GenericChip implements Chip{
		private String name;
		
		public GenericChip(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	private static class GameCharacter {
		private String name;
		
		public GameCharacter(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}