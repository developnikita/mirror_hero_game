package com.neolab.heroesGame.aditional;

import com.opencsv.CSVWriter;

import java.io.*;

public class StatisticWriter {
    public static final String PLAYER_STATISTIC_FILE_PATH = "src/main/resources/playerStatistic.csv";

    public static void writePlayerWinStatistic(final String winnerPlayerName, final String looserPlayerName) throws IOException {
        final File csvOutputFile = new File(PLAYER_STATISTIC_FILE_PATH);
        if (!csvOutputFile.exists()) {
            initPlayerStatistic();
        }
        final String[] data = {winnerPlayerName, looserPlayerName, "1.0", "0.0"};
        final CSVWriter writer = new CSVWriter(new FileWriter(PLAYER_STATISTIC_FILE_PATH, true));
        writer.writeNext(data);
        writer.close();
    }

    public static void writePlayerDrawStatistic(final String player1, final String player2) throws IOException {
        final File csvOutputFile = new File(PLAYER_STATISTIC_FILE_PATH);
        if (csvOutputFile.exists()) {
            initPlayerStatistic();
        }
        final String[] data = {player1, player2, "0.5", "0.5"};
        final CSVWriter writer = new CSVWriter(new FileWriter(PLAYER_STATISTIC_FILE_PATH, true));
        writer.writeNext(data);
        writer.close();
    }

    private static void initPlayerStatistic() throws IOException {
        final File csvOutputFile = new File(PLAYER_STATISTIC_FILE_PATH);
        final String[] data = {"Player 1", "Player 2", "Player1Point", "Player2Point"};
        final CSVWriter writer = new CSVWriter(new FileWriter(PLAYER_STATISTIC_FILE_PATH));
        writer.writeNext(data);
        writer.close();
    }
}
