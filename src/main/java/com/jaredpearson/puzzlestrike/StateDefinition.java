package com.jaredpearson.puzzlestrike;

import java.util.List;

public interface StateDefinition {
	public List<Action> getActions(Game game);
	public void handle(Game game, Action action);
}