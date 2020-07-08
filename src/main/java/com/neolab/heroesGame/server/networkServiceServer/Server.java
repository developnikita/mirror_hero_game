package com.neolab.heroesGame.server.networkServiceServer;

import com.neolab.heroesGame.client.ai.Player;

import java.util.Map;

public class Server {
    private Map<Integer, Player> players;

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public void addPlayer(Integer playerId, Player player) {
        this.players.put(playerId, player);
    }

    public static void playingProcess(){

    }
}
