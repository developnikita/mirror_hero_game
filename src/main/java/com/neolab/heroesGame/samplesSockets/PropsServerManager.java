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
    public final Map<Integer, String> mapIdNamePlayers = new HashMap<>();

    public PropsServerManager(){
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными
        Properties prop = new Properties();

        try {
            //обращаемся к файлу и получаем данные
            prop.load(new FileInputStream(PATH_TO_PROPERTIES));
            PORT = Integer.parseInt(prop.getProperty("server.PORT"));
            int playerOneId = Integer.parseInt(prop.getProperty("playerOne.id"));
            int playerTwoId = Integer.parseInt(prop.getProperty("playerTwo.id"));
            String playerOneName = prop.getProperty("playerOne.name");
            String playerTwoName = prop.getProperty("playerTwo.name");

            mapIdNamePlayers.put(playerOneId, playerOneName);
            mapIdNamePlayers.put(playerTwoId, playerTwoName);

        } catch (IOException e) {
            LOGGER.error("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружен");
            e.printStackTrace();
        }
    }
}