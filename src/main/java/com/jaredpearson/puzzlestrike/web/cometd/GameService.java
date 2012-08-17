package com.jaredpearson.puzzlestrike.web.cometd;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.server.AbstractService;

public class GameService  extends AbstractService {
	
	public GameService(BayeuxServer bayeux) {
		super(bayeux, "puzzlestrike-game");
		//addService("/services/puzzlestrike/newGame", "newGame");
	}
	
	/*
	public void newGame(ServerSession remote, Message message) {
		String playerName = (String)message.getDataAsMap().get("name");
		
		//create the new game
		Game game = Server.getGameManager().createGame(playerName);
		game.addPlayer(playerName);
		
        Map<String, Object> output = new HashMap<String, Object>();
        remote.deliver(getServerSession(), "/open", output, null);
	}
	*/
}
