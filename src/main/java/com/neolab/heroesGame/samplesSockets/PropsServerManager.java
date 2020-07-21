package com.neolab.heroesGame.samplesSockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropsServerManager {
    //путь к нашему файлу конфигураций
    private static final String PATH_TO_PROPERTIES = "src/main/resources/server.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsServerManager.class);
    public int PORT = 8081;
    public int MAX_COUNT_PLAYERS;
    public final Map<Integer, String> mapIdNamePlayers = new HashMap<>();

    public PropsServerManager(){
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными
        Properties prop = new Properties();

        try {
            //обращаемся к файлу и получаем данные
            prop.load(new FileInputStream(PATH_TO_PROPERTIES));
            PORT = Integer.parseInt(prop.getProperty("server.PORT"));
            MAX_COUNT_PLAYERS = Integer.parseInt(prop.getProperty("MAX_COUNT_PLAYERS"));
            final int playerOneId = Integer.parseInt(prop.getProperty("playerOne.id"));
            final int playerTwoId = Integer.parseInt(prop.getProperty("playerTwo.id"));
            final int playerThreeId = Integer.parseInt(prop.getProperty("playerThree.id"));
            final int playerFourId = Integer.parseInt(prop.getProperty("playerFour.id"));
            final String playerOneName = prop.getProperty("playerOne.name");
            final String playerTwoName = prop.getProperty("playerTwo.name");
            final String playerThreeName = prop.getProperty("playerThree.name");
            final String playerFourName = prop.getProperty("playerFour.name");

            mapIdNamePlayers.put(playerOneId, playerOneName);
            mapIdNamePlayers.put(playerTwoId, playerTwoName);
            mapIdNamePlayers.put(playerThreeId,playerThreeName);
            mapIdNamePlayers.put(playerFourId, playerFourName);

        } catch (IOException e) {
            LOGGER.error("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружен");
            e.printStackTrace();
        }
    }
}