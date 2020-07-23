package com.neolab.heroesGame.aditional;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.neolab.heroesGame.aditional.StatisticWriter.armyStatisticFilePath;

public class StatisticReader {

    public static Optional<List<String[]>> readArmiesWinStatistic() {
        final File csvInputFile = new File(armyStatisticFilePath);
        if (!csvInputFile.exists()) {
            return Optional.empty();
        }
        List<String[]> result = new ArrayList<>();
        try (final CSVReader reader = new CSVReader(new FileReader(csvInputFile))) {
            result = reader.readAll();
        } catch (final Exception e) {
            // TODO: Logger
        }
        return Optional.ofNullable(result);
    }
}
