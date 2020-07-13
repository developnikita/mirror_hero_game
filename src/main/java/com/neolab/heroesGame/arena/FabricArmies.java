package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.*;

import java.util.*;

public class FabricArmies {
    private static final long SEED = 5916;
    private static final Random RANDOM = new Random(SEED);

    public static Map<Integer, Army> generateArmies(Integer firstPlayerId,
                                                    Integer secondPlayerId) throws HeroExceptions {
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
    private static Army createArmy() throws HeroExceptions {
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
            heroes.put(key, createDefaultFootman());
        }
        for (SquareCoordinate key : secondLine) {
            heroes.put(key, createSecondLineUnit());
        }
        return new Army(heroes);
    }

    private static SquareCoordinate addWarlord(Map<SquareCoordinate, Hero> heroes, Set<SquareCoordinate> line, Hero warlord) {
        SquareCoordinate temp = line.iterator().next();
        heroes.put(temp, warlord);
        line.remove(temp);
        return temp;
    }

    private static Hero createWarlord() {
        int switcher = RANDOM.nextInt(4);
        if (switcher == 0) {
            return createDefaultWarlordMagician();
        } else if (switcher == 1) {
            return createDefaultWarlordVampire();
        } else {
            return createDefaultWarlordFootman();
        }
    }

    private static Hero createSecondLineUnit() {
        int switcher = RANDOM.nextInt(3) % 3;
        if (switcher == 0) {
            return createDefaultArcher();
        } else if (switcher == 1) {
            return createDefaultMagician();
        } else {
            return createDefaultHealer();
        }
    }

    private static Set<SquareCoordinate> makeLine(Integer y) {
        Set<SquareCoordinate> line = new HashSet<>();
        for (int x = 0; x <= 2; x++) {
            line.add(new SquareCoordinate(x, y));
        }
        return line;
    }

    public static Hero createDefaultFootman() {
        int hp = 170;
        int damage = 50;
        float precision = 0.8f;
        float armor = 0.1f;
        return new Footman(hp, damage, precision, armor);
    }

    public static Hero createDefaultArcher() {
        int hp = 90;
        int damage = 40;
        float precision = 0.85f;
        float armor = 0;
        return new Archer(hp, damage, precision, armor);
    }

    public static Hero createDefaultMagician() {
        int hp = 75;
        int damage = 30;
        float precision = 0.8f;
        float armor = 0;
        return new Magician(hp, damage, precision, armor);
    }

    public static Hero createDefaultHealer() {
        int hp = 75;
        int damage = 40;
        float precision = 1;
        float armor = 0;
        return new Healer(hp, damage, precision, armor);
    }

    public static Hero createDefaultWarlordFootman() {
        int hp = 180;
        int damage = 60;
        float precision = 0.9f;
        float armor = 0.15f;
        return new WarlordFootman(hp, damage, precision, armor);
    }

    public static Hero createDefaultWarlordMagician() {
        int hp = 90;
        int damage = 40;
        float precision = 0.75f;
        float armor = 0.05f;
        return new WarlordMagician(hp, damage, precision, armor);
    }

    public static Hero createDefaultWarlordVampire() {
        int hp = 90;
        int damage = 10;
        float precision = 0.8f;
        float armor = 0.05f;
        return new WarlordVampire(hp, damage, precision, armor);
    }
}
