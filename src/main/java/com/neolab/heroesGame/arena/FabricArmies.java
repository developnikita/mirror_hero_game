package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.heroes.*;

import java.util.*;

public class FabricArmies {
    private static final long SEED = 5916;
    private static final Random RANDOM = new Random(SEED);

    public static Map<Integer, Army> generateArmies(Integer firstPlayerId, Integer secondPlayerId) {
        Map<Integer, Army> armies = new HashMap<>();
        armies.put(firstPlayerId, createArmy());
        armies.put(secondPlayerId, createArmy());
        return armies;
    }

    /**
     * Генерируем армию. Варлорд: с шансом 50% файтер, по 25% на магов
     * Первую линию забивает Footman
     * Вторую линию забивает Healer, Magician, Archer с равным шансом
     * Место для варлорда выбирает Set.iterator().next()
     */
    private static Army createArmy() {
        Map<SquareCoordinate, Hero> heroes = new HashMap<>();
        Hero warlord = createWarlord();
        SquareCoordinate warlordCoord;
        Set<SquareCoordinate> firstLine = makeLine(1);
        Set<SquareCoordinate> secondLine = makeLine(0);
        if (warlord.getClass() == WarlordFootman.class) {
            warlordCoord = addWarlord(heroes, firstLine, warlord);
        } else {
            warlordCoord = addWarlord(heroes, secondLine, warlord);
        }
        for (SquareCoordinate key : firstLine) {
            heroes.put(key, createFootman());
        }
        for (SquareCoordinate key : secondLine) {
            heroes.put(key, createSecondLineUnit());
        }
        return new Army(heroes, (IWarlord) warlord, warlordCoord);
    }

    private static SquareCoordinate addWarlord(Map<SquareCoordinate, Hero> heroes, Set<SquareCoordinate> line, Hero warlord) {
        SquareCoordinate temp = line.iterator().next();
        heroes.put(temp, warlord);
        line.remove(temp);
        return temp;
    }

    private static Hero createWarlord() {
        int switcher = RANDOM.nextInt(4) % 3;
        if (switcher == 0) {
            return new WarlordFootman(180, 60, 0.8f, 0.15f);
        } else if (switcher == 1) {
            return new WarlordMagician(90, 40, 0.75f, 0.05f);
        } else {
            return new WarlordVampire(90, 10, 0.8f, 0.05f);
        }
    }

    private static Hero createSecondLineUnit() {
        int switcher = RANDOM.nextInt(3) % 3;
        if (switcher == 0) {
            return new Archer(90, 40, 0.85f, 0);
        } else if (switcher == 1) {
            return new Magician(75, 30, 0.8f, 0);
        } else {
            return new Healer(75, 40, 1, 0);
        }
    }

    private static Hero createFootman() {
        return new Footman(170, 50, 0.8f, 0.1f);
    }

    private static Set<SquareCoordinate> makeLine(Integer y) {
        Set<SquareCoordinate> line = new HashSet<>();
        for (int x = 0; x <= 2; x++) {
            line.add(new SquareCoordinate(x, y));
        }
        return line;
    }
}
