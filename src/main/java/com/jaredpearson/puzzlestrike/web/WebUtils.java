package com.jaredpearson.puzzlestrike.web;

import javax.servlet.http.HttpServletRequest;

import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerManager;

public class WebUtils {
	public static Player getPlayer(PlayerManager playerManager, HttpServletRequest request) {
		if(request.getSession(true).getAttribute("playerId") != null) {
			return playerManager.getPlayerById((Integer)request.getSession(true).getAttribute("playerId"));
		}
		return null;
	}
}