package com.jaredpearson.puzzlestrike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayerGameContext {
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
	
	public void addToBag(Chip chip) {
		this.bag.add(chip);
	}
	
	public void addToBag(Collection<? extends Chip> chips) {
		this.bag.addAll(chips);
	}
	
	public List<Chip> getHand() {
		return Collections.unmodifiableList(hand);
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