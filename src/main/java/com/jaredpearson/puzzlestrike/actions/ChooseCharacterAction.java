package com.jaredpearson.puzzlestrike.actions;

import com.jaredpearson.puzzlestrike.GameCharacter;

public class ChooseCharacterAction implements Action {
	private GameCharacter character;
	
	public ChooseCharacterAction(GameCharacter character) {
		this.character = character;
	}
	
	public String getName() {
		return "Choose Character: " + character.getName();
	}
	
	public GameCharacter getCharacter() {
		return character;
	}
}