package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.enumerations.GameEvent;
import com.opencsv.CSVWriter;

import java.io.*;

public class StatisticWriter {
    public static String playerStatisticFilePath = "playerStatistic.csv";
    public static String armyStatisticFilePath = "armyStatistic.csv";
    public static String unitActionStatisticFilePath = "unitActionStatistic.csv";

    public static void writePlayerWinStatistic(final String winnerPlayerName, final String looserPlayerName) throws IOException {
        final File csvOutputFile = new File(playerStatisticFilePath);
        if (!csvOutputFile.exists()) {
            initPlayerStatistic();
        }
        final String[] data = {winnerPlayerName, looserPlayerName, "1.0", "0.0"};
        final CSVWriter writer = new CSVWriter(new FileWriter(playerStatisticFilePath));
        writer.writeNext(data);
        writer.close();
    }

    public static void writeArmiesWinStatistic(final String firstMoveArmy, final String secondMoveArmy,
                                               final GameEvent winner) {
        final File csvOutputFile = new File(armyStatisticFilePath);
        if (!csvOutputFile.exists()) {
            initArmyStatistic();
        }
        final String[] data = {firstMoveArmy, secondMoveArmy, winner.getDescription()};
        try (final CSVWriter fw = new CSVWriter(new FileWriter(csvOutputFile, true))) {
            fw.writeNext(data);
        } catch (final IOException e) {
            // TODO: Logger
        }
    }

    public static void writePlayerDrawStatistic(final String player1, final String player2) throws IOException {
        final File csvOutputFile = new File(playerStatisticFilePath);
        if (csvOutputFile.exists()) {
            initPlayerStatistic();
        }
        final String[] data = {player1, player2, "0.5", "0.5"};
        final CSVWriter writer = new CSVWriter(new FileWriter(playerStatisticFilePath));
        writer.writeNext(data);
        writer.close();
    }

    public static void writeUnitStatistic(final String unitClass, final Integer action) {
        final File csvOutputFile = new File(unitActionStatisticFilePath);
        if (!csvOutputFile.exists()) {
            initUnitStatistic();
        }
        String oldContent = "";
        String oldLine = "";
        try (final BufferedReader reader = new BufferedReader(new FileReader(csvOutputFile))) {
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith(unitClass)) {
                    oldLine = line;
                }
                oldContent += line + "\n";
                line = reader.readLine();
            }

            final String[] splitStr = oldLine.split(",");
            final String newLine = splitStr[0] + "," + ((float) Integer.parseInt(splitStr[1]) + action) / 2.0;
            final String newContent = oldContent.replaceAll(oldLine, newLine);
            final FileWriter writer = new FileWriter(csvOutputFile);
            writer.write(newContent);
            writer.close();
        } catch (final FileNotFoundException e) {
            // TODO: Logger
        } catch (final IOException e) {
            // TODO: Logger;
        }
    }

    private static void initPlayerStatistic() throws IOException {
        final File csvOutputFile = new File(playerStatisticFilePath);
        final String[] data = {"Player 1", "Player 2", "Player1Point", "Player2Point"};
        final CSVWriter writer = new CSVWriter(new FileWriter(playerStatisticFilePath));
        writer.writeNext(data);
        writer.close();
    }

    private static void initUnitStatistic() {
        final File csvOutputFile = new File(unitActionStatisticFilePath);
        final String[] data = {"Unit", "Action"};
        final String[][] unit = {{"Archer", "0"}, {"Footman", "0"}, {"Healer", "0"}, {"Magician", "0"},
                {"WarlordFootman", "0"}, {"WarlordMagician", "0"}, {"WarlordVampire", "0"}};
        final String[] array = new String[unit.length];
        for (int i = 0; i < unit.length; ++i) {
            array[i] = String.join(",", unit[i]);
        }
        try (final FileWriter fw = new FileWriter(csvOutputFile)) {
            fw.write(convertToCSV(data) + "\n");
            fw.write(String.join("\n", array));
        } catch (final IOException e) {
            // TODO: Logger
        }
    }

    private static void initArmyStatistic() {
        final File csvOutputFile = new File(armyStatisticFilePath);
        final String[] data = {"Army 1", "Army 2", "Is first army win"};
        try (final FileWriter fw = new FileWriter(csvOutputFile)) {
            fw.write(convertToCSV(data) + "\n");
        } catch (final IOException e) {
            // TODO: Logger
        }
    }

    private static String convertToCSV(final String[] data) {
        return String.join(",", data);
    }

    private static String escapeSpecialChars(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return data;
    }
}
