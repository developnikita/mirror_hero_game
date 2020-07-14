package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.*;

import java.util.*;

public class FactoryArmies {

    private static final long SEED = 5916;
    private static final Random RANDOM = new Random(SEED);

    public static Map<Integer, Army> generateArmies(final Integer firstPlayerId,
                                                    final Integer secondPlayerId) throws HeroExceptions {
        final Map<Integer, Army> armies = new HashMap<>();
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
        final Map<SquareCoordinate, Hero> heroes = new HashMap<>();
        final Hero warlord = createWarlord();
        final SquareCoordinate warlordCoord;
        final Set<SquareCoordinate> firstLine = makeLine(1);
        final Set<SquareCoordinate> secondLine = makeLine(0);
        if (warlord instanceof WarlordFootman){
            warlordCoord = addWarlord(heroes, firstLine, warlord);
        } else{
            warlordCoord = addWarlord(heroes, secondLine, warlord);
        }
        for (final SquareCoordinate key : firstLine) {
            heroes.put(key, createDefaultFootman());
        }
        for (final SquareCoordinate key : secondLine) {
            heroes.put(key, createSecondLineUnit());
        }
        return new Army(heroes);
    }

    private static SquareCoordinate addWarlord(final Map<SquareCoordinate, Hero> heroes, final Set<SquareCoordinate> line, final Hero warlord) {
        final SquareCoordinate temp = line.iterator().next();
        heroes.put(temp, warlord);
        line.remove(temp);
        return temp;
    }

    private static Hero createWarlord() {
        final int switcher = RANDOM.nextInt(4);
        if (switcher == 0) {
            return createDefaultWarlordMagician();
        } else if (switcher == 1) {
            return createDefaultWarlordVampire();
        } else {
            return createDefaultWarlordFootman();
        }
    }

    private static Hero createSecondLineUnit() {
        final int switcher = RANDOM.nextInt(3) % 3;
        if (switcher == 0) {
            return createDefaultArcher();
        } else if (switcher == 1) {
            return createDefaultMagician();
        } else {
            return createDefaultHealer();
        }
    }

    private static Set<SquareCoordinate> makeLine(final Integer y) {
        final Set<SquareCoordinate> line = new HashSet<>();
        for (int x = 0; x <= 2; x++) {
            line.add(new SquareCoordinate(x, y));
        }
        return line;
    }

    public static Hero createDefaultFootman() {
        final int hp = 170;
        final int damage = 50;
        final float precision = 0.8f;
        final float armor = 0.1f;
        return new Footman(hp, damage, precision, armor);
    }

    public static Hero createDefaultArcher() {
        final int hp = 90;
        final int damage = 40;
        final float precision = 0.85f;
        final float armor = 0;
        return new Archer(hp, damage, precision, armor);
    }

    public static Hero createDefaultMagician() {
        final int hp = 75;
        final int damage = 30;
        final float precision = 0.8f;
        final float armor = 0;
        return new Magician(hp, damage, precision, armor);
    }

    public static Hero createDefaultHealer() {
        final int hp = 75;
        final int damage = 40;
        final float precision = 1;
        final float armor = 0;
        return new Healer(hp, damage, precision, armor);
    }

    public static Hero createDefaultWarlordFootman() {
        final int hp = 180;
        final int damage = 60;
        final float precision = 0.9f;
        final float armor = 0.15f;
        return new WarlordFootman(hp, damage, precision, armor);
    }

    public static Hero createDefaultWarlordMagician() {
        final int hp = 90;
        final int damage = 40;
        final float precision = 0.75f;
        final float armor = 0.05f;
        return new WarlordMagician(hp, damage, precision, armor);
    }

    public static Hero createDefaultWarlordVampire() {
        final int hp = 90;
        final int damage = 10;
        final float precision = 0.8f;
        final float armor = 0.05f;
        return new WarlordVampire(hp, damage, precision, armor);
    }
}
