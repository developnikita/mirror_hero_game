package com.neolab.heroesGame.samplesSockets;

import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.serverNetwork.GameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Queue;

/**
 * класс реализующий функционал игровой комнты для двух игроков
 */
public class GameRoom extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameRoom.class);
    private final PlayerSocket playerOne;
    private final PlayerSocket playerTwo;
    private final int countBattles;

    public GameRoom(final Queue<PlayerSocket> queuePlayers, final int countBattles) {
        playerOne = queuePlayers.poll();
        playerTwo = queuePlayers.poll();
        this.countBattles = countBattles;
    }

    @Override
    public void run() {
        try {
            final long start = System.nanoTime();

            for (int i = 0; i < countBattles; i++) {
                final GameServer server = new GameServer(playerOne, playerTwo);
                server.prepareForBattle();
                server.gameProcess();
            }
            final long end = System.nanoTime();
            //1 second = 1__000__000__000 nano seconds
            final double elapsedTimeInSecond = (double) (end - start) / 1__000__000__000;
            LOGGER.warn("Игра длилась {}", elapsedTimeInSecond);

            Server.getCountGameRooms().decrementAndGet();
        } catch (InterruptedException | HeroExceptions | IOException e) {
            LOGGER.warn(e.getMessage());
            throw new IllegalStateException("Игра прервана, внутренняя ошибка сервера");
        }
    }
}
