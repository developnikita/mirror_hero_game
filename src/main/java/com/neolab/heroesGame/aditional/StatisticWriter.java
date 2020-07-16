package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.heroes.Archer;

import java.io.*;

public class StatisticWriter {
    public static String playerStatisticFilePath = "playerStatistic.csv";
    public static String unitActionStatisticFilePath = "unitActionStatistic.csv";
    private static boolean playerStatisticFileInit = false;
    private static boolean unitStatisticFileInit = false;

    public static void writePlayerStatistic(final String winnerPlayerName, final String looserPlayerName) {
//        initPlayerStatistic();
        final File csvOutputFile = new File(playerStatisticFilePath);
        final String[] data = {winnerPlayerName, looserPlayerName, winnerPlayerName};
        try (final FileWriter fw = new FileWriter(csvOutputFile, true)) {
            fw.write(convertToCSV(data) + "\n");
        } catch (final IOException e) {
            // TODO: Logger
        }
    }

    public static void writeUnitStatistic(final String unitClass, final Integer action) {
//        initUnitStatistic();
        final File csvOutputFile = new File(unitActionStatisticFilePath);
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

            final String[] splitStr = oldLine.toString().split(",");
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

    private static void initPlayerStatistic() {
        if (!playerStatisticFileInit) {
            final File csvOutputFile = new File(playerStatisticFilePath);
            final String[] data = {"Player 1", "Player 2", "Winner"};
            try (final FileWriter fw = new FileWriter(csvOutputFile)) {
                fw.write(convertToCSV(data) + "\n");
                playerStatisticFileInit = true;
            } catch (final IOException e) {
                // TODO: Logger
            }
        }
    }

    private static void initUnitStatistic() {
        if (!unitStatisticFileInit) {
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
                unitStatisticFileInit = true;
            } catch (final IOException e) {
                // TODO: Logger
            }
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

    public static void main(final String[] args) {
        writePlayerStatistic("ASD", "ddf");
        writePlayerStatistic("432мсф", "фывфыв");

        System.out.println(Archer.class.getSimpleName());

        writeUnitStatistic("Archer", 12);
    }

}
