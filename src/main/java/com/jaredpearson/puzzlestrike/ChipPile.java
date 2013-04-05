package com.jaredpearson.puzzlestrike;

/**
 * Represents a set of chips
 * @author jared.pearson
 */
public class ChipPile {
	private static final Chip GEM1 = new GenericChip("1 Gem");
	private static final Chip CRASH = new GenericChip("Crash Gem");
	
	/**
	 * Takes the chip with the given name from the pile.
	 */
	public Chip takeByName(String name) {
		return new GenericChip(name);
	}
	
	/**
	 * Takes a 1-Gem from the pile.
	 */
	public Chip takeGem1() {
		return GEM1;
	}
	
	/**
	 * Takes a Crash gem from the pile.
	 */
	public Chip takeCrashGem() {
		return CRASH;
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
}
