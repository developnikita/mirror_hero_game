package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.heroes.*;

import java.util.*;

public class FabricArmies {
    private static final long SEED = 5916;
    private static final Random RANDOM = new Random(SEED);

    public static Map<Integer, Army> generateArmyes(Integer firstPlayerId, Integer secondPlayerId) {
        Map<Integer, Army> armies = new HashMap<>();
        armies.put(firstPlayerId, createArmy(firstPlayerId));
        armies.put(secondPlayerId, createArmy(secondPlayerId));
        return armies;
    }

    /**
     * Генерируем армию. Варлорд: с шансом 50% файтер, по 25% на магов
     * Первую линию забивает Footman
     * Вторую линию забивает Healer, Magician, Archer с равным шансом
     * Место для варлорда выбирает Set.iterator().next()
     */
    private static Army createArmy(Integer playerId) {
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();
        Hero warlord = createWarlord(playerId);
        SquareCoordinate warlordCoord;
        Set<SquareCoordinate> firstLine = makeLine(1);
        Set<SquareCoordinate> secondLine = makeLine(2);
        if (warlord.getClass() == WarlordFootman.class) {
            warlordCoord = addWarlord(heroes, firstLine, warlord);
        } else {
            warlordCoord = addWarlord(heroes, secondLine, warlord);
        }
        for (SquareCoordinate key : firstLine) {
            heroes.put(key, createFootman(playerId));
        }
        for (SquareCoordinate key : secondLine) {
            heroes.put(key, createSecondLineUnit(playerId));
        }
        return new Army(heroes, (IWarlord) warlord, warlordCoord);
    }

    private static SquareCoordinate addWarlord(Map<SquareCoordinate, Hero> heroes, Set<SquareCoordinate> line, Hero warlord) {
        SquareCoordinate temp = line.iterator().next();
        heroes.put(temp, warlord);
        line.remove(temp);
        return temp;
    }

    private static Hero createWarlord(Integer armyId) {
        int switcher = RANDOM.nextInt(4) % 3;
        if (switcher == 0) {
            return new WarlordFootman(180, 60, 0.8f, 0.15f, armyId);
        } else if (switcher == 1) {
            return new WarlordMagician(90, 40, 0.75f, 0.05f, armyId);
        } else {
            return new WarlordVampire(90, 10, 0.8f, 0.05f, armyId);
        }
    }

    private static Hero createSecondLineUnit(Integer armyId) {
        int switcher = RANDOM.nextInt(3) % 3;
        if (switcher == 0) {
            return new Archer(90, 40, 0.85f, 0, armyId);
        } else if (switcher == 1) {
            return new Magician(75, 30, 0.8f, 0, armyId);
        } else {
            return new Healer(75, 40, 1, 0, armyId);
        }
    }

    private static Hero createFootman(Integer armyId) {
        return new Footman(170, 50, 0.8f, 0.1f, armyId);
    }

    private static Set<SquareCoordinate> makeLine(Integer x) {
        Set<SquareCoordinate> line = new HashSet<>();
        for (int y = 0; y <= 2; y++) {
            line.add(new SquareCoordinate(x, y));
        }
        return line;
    }
}
