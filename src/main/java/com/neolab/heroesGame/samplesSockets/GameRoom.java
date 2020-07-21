package com.neolab.heroesGame.samplesSockets;

import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.serverNetwork.GameServer;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * класс реализующий функционал игровой комнты для двух игроков
 */
public class GameRoom extends Thread{
    private final GameServer gameServer;

    public GameRoom(ConcurrentLinkedQueue<PlayerSocket> serverList) throws Exception {
        final PlayerSocket playerOne = serverList.poll();
        final PlayerSocket playerTwo = serverList.poll();
        gameServer = new GameServer(playerOne, playerTwo);
    }

    @Override
    public void run() {
        try {
            gameServer.gameProcess();
        } catch (IOException | HeroExceptions e) {
            e.printStackTrace();
        }
    }
}
