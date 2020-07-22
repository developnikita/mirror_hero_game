package com.neolab.heroesGame.aditional;

import com.opencsv.CSVWriter;

import java.io.*;

public class StatisticWriter {
    public static String playerStatisticFilePath = "src/main/resources/playerStatistic.csv";
    public static String unitActionStatisticFilePath = "src/main/resources/unitActionStatistic.csv";

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
        StringBuilder oldContent = new StringBuilder();
        String oldLine = "";
        try (final BufferedReader reader = new BufferedReader(new FileReader(csvOutputFile))) {
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith(unitClass)) {
                    oldLine = line;
                }
                oldContent.append(line).append("\n");
                line = reader.readLine();
            }

            final String[] splitStr = oldLine.split(",");
            final String newLine = splitStr[0] + "," + ((float) Integer.parseInt(splitStr[1]) + action) / 2.0;
            final String newContent = oldContent.toString().replaceAll(oldLine, newLine);
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
