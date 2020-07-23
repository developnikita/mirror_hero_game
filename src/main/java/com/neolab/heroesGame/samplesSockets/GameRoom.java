package com.neolab.heroesGame.samplesSockets;

import com.neolab.heroesGame.server.serverNetwork.GameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

/**
 * класс реализующий функционал игровой комнты для двух игроков
 */
public class GameRoom extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameRoom.class);
    private final PlayerSocket playerOne;
    private final PlayerSocket playerTwo;
    private final int countBattles;

    public GameRoom(final Queue<PlayerSocket> serverList, int countBattles){
        playerOne = serverList.poll();
        playerTwo = serverList.poll();
        this.countBattles = countBattles;
    }

    @Override
    public void run() {
        try {
            long start = System.nanoTime();

            for(int i = 0; i < countBattles; i++){
                new GameServer(playerOne, playerTwo).gameProcess();
            }
            long end = System.nanoTime();
            //1 second = 1__000__000__000 nano seconds
            double elapsedTimeInSecond = (double) (end - start)/1__000__000__000;
            LOGGER.warn("Игра длилась {}", elapsedTimeInSecond);

            Server.getCountGameRooms().decrementAndGet();
        } catch (final Exception ex) {
            throw new  IllegalStateException("Игра прервана, внутренняя ошибка сервера");
        }
    }
}
