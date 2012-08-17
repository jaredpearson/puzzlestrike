package com.jaredpearson.puzzlestrike.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jaredpearson.puzzlestrike.Player;
import com.jaredpearson.puzzlestrike.PlayerManager;

public class NewPlayerController implements Controller {
	private PlayerManager playerManager;
	
	public NewPlayerController(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}
	
	public View handle(HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String name = request.getParameter("name");
		Player player = new Player();
		player.setName(name);
		playerManager.insert(player);
		
		return new JspView("redirect:/home?asPlayer=" + player.getId());
	}

}
