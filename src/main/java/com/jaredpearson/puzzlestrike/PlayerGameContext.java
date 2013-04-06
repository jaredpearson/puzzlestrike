package com.jaredpearson.puzzlestrike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.jaredpearson.puzzlestrike.chips.GemChip;

public class PlayerGameContext {
	private Player player;
	private GameCharacter character;
	private List<GemChip> gemPile = new ArrayList<GemChip>();
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
	
	public void addToGemPile(GemChip chip) {
		this.gemPile.add(chip);
	}
	
	/**
	 * Gets the value of the gem pile
	 */
	public int getGemPileValue() {
		int value = 0;
		for(GemChip chip : this.gemPile) {
			value += chip.getGemValue();
		}
		
		return value;
	}
	
	public void discardHand() {
		this.discard.addAll(this.hand);
		this.hand.clear();
	}
	
	private int getHandSize() {
		return 5;
	}
}