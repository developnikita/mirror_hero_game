package com.neolab.heroesGame.samplesSockets;

import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.serverNetwork.GameServer;

import java.io.IOException;
import java.util.Queue;

/**
 * класс реализующий функционал игровой комнты для двух игроков
 */
public class GameRoom extends Thread {
    private final GameServer gameServer;

    public GameRoom(final Queue<PlayerSocket> serverList) throws Exception {
        final PlayerSocket playerOne = serverList.poll();
        final PlayerSocket playerTwo = serverList.poll();
        gameServer = new GameServer(playerOne, playerTwo);
    }

    @Override
    public void run() {
        try {
            gameServer.gameProcess();
            Server.getCountGameRooms().decrementAndGet();
        } catch (final IOException | HeroExceptions | InterruptedException ex) {
            throw new  IllegalStateException("Игра прервана, внутренняя ошибка сервера");
        }
    }
}
